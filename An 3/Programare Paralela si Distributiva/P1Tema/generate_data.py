import random

def generate_test_data(countries, problems, min_participants, max_participants):
    # Set up random generators
    random_device = random.SystemRandom()  # Provides better randomness than random module
    participants_dist = lambda: random_device.randint(min_participants, max_participants)
    score_dist = lambda: random_device.randint(0, 100)
    fraud_dist = lambda: random_device.randint(1, 100)

    for country in countries:
        filename = f"{country}.txt"
        with open(filename, "w") as out_file:
            for p in range(1, problems + 1):
                participants = participants_dist()
                for i in range(1, participants + 1):
                    random_chance = fraud_dist()
                    if random_chance <= 10:
                        # Skip fraudulent participant
                        continue
                    elif random_chance <= 12:
                        # Add a participant with a fraudulent score of -1
                        out_file.write(f"{i} -1 {country}\n")
                    else:
                        # Add a normal participant with a valid score
                        out_file.write(f"{i} {score_dist()} {country}\n")
        print(f"Test data generated successfully and written to {filename}")

# Example usage
countries = ["Romania", "France", "USA", "Germany", "Italy"]
problems = 5  # Number of problems
min_participants = 5  # Minimum participants per problem
max_participants = 20  # Maximum participants per problem

generate_test_data(countries, problems, min_participants, max_participants)