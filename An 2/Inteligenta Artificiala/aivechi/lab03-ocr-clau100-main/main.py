import math
import cv2
import textdistance
from azure.cognitiveservices.vision.computervision import ComputerVisionClient
from azure.cognitiveservices.vision.computervision.models import OperationStatusCodes
from azure.cognitiveservices.vision.computervision.models import VisualFeatureTypes
from msrest.authentication import CognitiveServicesCredentials
from array import array
import os
from PIL import Image, ImageDraw
import sys
import time
from super_image import EdsrModel, ImageLoader
def get_auth() -> ComputerVisionClient:
    try:
        subscription_key = os.environ["VISION_KEY"]
        endpoint = os.environ["VISION_ENDPOINT"]
        return ComputerVisionClient(endpoint, CognitiveServicesCredentials(subscription_key))
    except KeyError:
        print("Nu au fost setate variabilele 'VISION_KEY' sau 'VISION_ENDPOINT'")
        print("Seteaza-le si dupa ruleaza din nou")
        exit()


def process_image(image_path: str, computer_vision_client: ComputerVisionClient) -> list[str]:
    img = open(image_path, "rb")

    read_response = computer_vision_client.read_in_stream(
        image=img,
        mode="Printed",
        raw=True
    )

    operation_id = read_response.headers['Operation-Location'].split('/')[-1]
    while True:
        read_result = computer_vision_client.get_read_result(operation_id)
        if read_result.status not in ['notStarted', 'running']:
            break
        time.sleep(1)

    result = []
    if read_result.status == OperationStatusCodes.succeeded:
        for text_result in read_result.analyze_result.read_results:
            for line in text_result.lines:
                result.append(line.text)

    return result


def check_result_wer(result: list[str], ground_truth: list[str]) -> int:
    return sum(i == j for i, j in zip(result, ground_truth))


def read_truth(file: str) -> list[str]:
    with open(file, "r") as f:
        lines = [line.strip() for line in f.readlines()]
    return lines


def check_result_cer(result: list[str], ground_truth: list[str]) -> float:
    nr_total = sum(len(line) for line in ground_truth)
    erori = 0

    for gt_line, result_line in zip(ground_truth, result):
        erori_per_linie = sum(1 for gt_char, result_char in zip(gt_line, result_line) if gt_char != result_char)
        erori += erori_per_linie

    return erori / nr_total


def hamming_distance(ground_truth_line: str, result_line: str) -> int:
    if len(ground_truth_line) != len(result_line):
        raise ValueError("Liniile trebuie sa aibe lungimi egale!")

    return sum(1 for gt_char, result_char in zip(ground_truth_line, result_line) if gt_char != result_char)


def compute_hamming_distance(ground_truth: list[str], result: list[str]) -> int:
    if len(ground_truth) != len(result):
        raise ValueError("Nu s-au citit toate liniile!")

    return sum(hamming_distance(gt_line, result_line) for gt_line, result_line in zip(ground_truth, result))


def compute_jaro_winkler_distance(result: list[str], ground_truth: list[str]) -> float:
    if len(ground_truth) != len(result):
        raise ValueError("Nu s-au citit toate liniile")
    distanta_totala = sum(textdistance.jaro_winkler(ground_truth_line, result_line) for ground_truth_line, result_line in zip(ground_truth, result))
    return distanta_totala / len(ground_truth) # normalizam dupa nr de linii


def solve1(image: str, ground_truth: str, client: ComputerVisionClient) -> None:
    result = process_image("images/" + image, client)
    truth = read_truth("actual_texts/" + ground_truth)
    rating_wer = check_result_wer(result, truth)
    rating_cer = check_result_cer(result, truth)
    try:
        rating_ham = compute_hamming_distance(result, truth)
    except ValueError:
        rating_ham = math.inf
    try:
        rating_jaro_winkler = compute_jaro_winkler_distance(result, truth)
    except ValueError:
        rating_jaro_winkler = math.inf
    print(f"Imaginea {image}:")
    print(result)
    print("==========================")
    print("Ground truth:")
    print(truth)
    print("==========================")
    print(f"Rating(WER): {rating_wer}")
    print("==========================")
    print(f"Rating(CER): {rating_cer}")
    print("==========================")
    print(f"Hamming distance: {rating_ham}")
    print("==========================")
    print(f"Jaro-Winkler distance: {rating_jaro_winkler}")
    print("++++++++++++++++++++++++++")


def read_bounding_boxes(border: str) -> list[list[tuple[float, float]]]:
    bboxes = []
    with open("actual_borders/" + border, "r") as f:
        lines = f.readlines()
        for line in lines:
            line = line.strip()
            line = line.replace('[', '').replace(']', '').replace('(', '').replace(')', '')
            coords = line.split(', ')
            bbox = [(float(coords[i]), float(coords[i+1])) for i in range(0, len(coords), 2)]
            bboxes.append(bbox)
    return bboxes


def solve2(image: str, border: str, client: ComputerVisionClient) -> None:
    img = open("images/" + image, "rb")

    bboxes = read_bounding_boxes(border)

    read_response = client.read_in_stream(
        image=img,
        mode="Printed",
        raw=True
    )
    operation_id = read_response.headers['Operation-Location'].split('/')[-1]
    while True:
        read_result = client.get_read_result(operation_id)
        if read_result.status not in ['notStarted', 'running']:
            break
        time.sleep(1)

    if read_result.status == OperationStatusCodes.succeeded:
        for text_result in read_result.analyze_result.read_results:
            print(f"Imaginea {image}:")
            imagine = Image.open("images/" + image)
            draw = ImageDraw.Draw(imagine)
            coverage = 0
            for line, actual_border in zip(text_result.lines, bboxes):
                colturi = [(line.bounding_box[i], line.bounding_box[i+1]) for i in range(0, len(line.bounding_box), 2)]
                draw.polygon(colturi, outline="red")

                actual_text_size = (actual_border[1][0] - actual_border[0][0]) * (actual_border[2][1] - actual_border[0][1])
                ai_text_size = (colturi[1][0] - colturi[0][0]) * (colturi[2][1] - colturi[0][1])

                coverage += ai_text_size / actual_text_size
            coverage /= len(bboxes) # normalizam cu nr de linii
            print(f"IA a acoperit localizat in proportie de {coverage * 100:.2f}% textul din imagine")
            imagine.show()


def solve3(image: str, ground_truth: str, client: ComputerVisionClient) -> None:
    # incercam sa dam "enhance" la imagine, convertind-o in gri
    # img = cv2.imread("images/" + image)
    # img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    # img = cv2.equalizeHist(img)

    # de-noise
    # img = cv2.GaussianBlur(img, (5, 5), 0)

    # adaptive threshold
    # img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    # img = cv2.adaptiveThreshold(img, 255, cv2.ADAPTIVE_THRESH_MEAN_C, cv2.THRESH_BINARY, 11, 2)

    # sharpen the edges
    # img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    # edges = cv2.Laplacian(img, cv2.CV_8U, ksize=3)
    # edges = cv2.resize(edges, (img.shape[1], img.shape[0]))
    # edges_inv = cv2.bitwise_not(edges)
    # img = cv2.addWeighted(img, 1, edges_inv, 1, 0)

    # super-image enhancer
    img = Image.open("images/" + image)
    model = EdsrModel.from_pretrained('eugenesiow/edsr-base', scale=2)
    inputs = ImageLoader.load_image(img)
    preds = model(inputs)

    folder = "enhanced"

    ImageLoader.save_image(preds, folder + "/" + image)
    ImageLoader.save_compare(inputs, preds, folder + "/" + image + "_comparare.png")

    if not os.path.exists(folder):
        os.makedirs(folder)

    # cv2.imwrite(folder + "/" + image, img)
    print("3. Optimizare")
    result = process_image(folder + "/" + image, client)
    truth = read_truth("actual_texts/" + ground_truth)
    rating_wer = check_result_wer(result, truth)
    rating_cer = check_result_cer(result, truth)
    try:
        rating_ham = compute_hamming_distance(result, truth)
    except ValueError:
        rating_ham = math.inf
    try:
        rating_jaro_winkler = compute_jaro_winkler_distance(result, truth)
    except ValueError:
        rating_jaro_winkler = math.inf
    print(f"Imaginea {image}:")
    print(result)
    print("==========================")
    print("Ground truth:")
    print(truth)
    print("==========================")
    print(f"Rating(WER): {rating_wer}")
    print("==========================")
    print(f"Rating(CER): {rating_cer}")
    print("==========================")
    print(f"Hamming distance: {rating_ham}")
    print("==========================")
    print(f"Jaro-Winkler distance: {rating_jaro_winkler}")
    print("++++++++++++++++++++++++++")


if __name__ == '__main__':
    client = get_auth()
    images = [f for f in os.listdir("images/")]
    truths = [f for f in os.listdir("actual_texts/")]
    borders = [f for f in os.listdir("actual_borders/")]
    for image, truth, border in zip(images, truths, borders):
        # solve1(image, truth, client)
        # solve2(image, border, client)
        solve3(image, truth, client)
