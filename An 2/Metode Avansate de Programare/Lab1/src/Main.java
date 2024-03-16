// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static NumarComplex parseComplexNumber(String number) {
        String regex = "([-+]?[0-9]*\\.?[0-9]+)\\s*([-+])\\s*([-+]?[0-9]*\\.?[0-9]+)i";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);

        if(matcher.matches()){
            double re = Double.parseDouble(matcher.group(1));
            double im = Double.parseDouble(matcher.group(3));
            if(matcher.group(2).equals("-")){
                im = -im;
            }
            return new NumarComplex(re, im);
        }
        else{
            throw new IllegalArgumentException("Sirul nu reprezinta un numar complex valid!\n");
        }

    }

    private static Operatie getOperatie(String operator){
        switch(operator){
            case "+":
                return new Operatie.Adunare();
            case "-":
                return new Operatie.Scadere();
            case "*":
                return new Operatie.Inmultire();
            case "/":
                return new Operatie.Impartire();
            default:
                throw new IllegalArgumentException("Operatorul nu este valid!\n" + operator);
        }
    }

    public static void main(String[] args) {
        System.out.print("Utilizare: a+bi + c+di + e+fi\n");

//        String ceapa = "3+5i";
//        System.out.println(parseComplexNumber(ceapa));

        Scanner scanner = new Scanner(System.in);
        try {
            //String[] expresie = scanner.nextLine().split(" ");

            String[] expresie = args;

            if (expresie.length % 2 != 1 || expresie.length < 3) {
                throw new IllegalArgumentException("Expresia nu este valida!\n" + expresie);
            }

            NumarComplex nr1 = parseComplexNumber(expresie[0]);

            NumarComplex nr2 = parseComplexNumber(expresie[2]);

            String operator = expresie[1];

            Operatie operatie1 = getOperatie(operator);

            ExpressionEvaluator evaluator = new ExpressionEvaluator(operatie1);

            NumarComplex rezultat = evaluator.evaluate(nr1, nr2);

            if (expresie.length > 3) {
                try {
                    NumarComplex nr3 = parseComplexNumber(expresie[4]);

                    String operator2 = expresie[3];

                    Operatie operatie2 = getOperatie(operator2);

                    ExpressionEvaluator evaluator2 = new ExpressionEvaluator(operatie2);

                    rezultat = evaluator2.evaluate(rezultat, nr3);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Expresia nu este valida!\n" + expresie);
                }

            }

            System.out.print("Rezultatul este: " + rezultat);
        }
        catch(Exception e){
            System.out.print(e.getMessage());
        }
        finally {
            scanner.close();
        }
        System.out.print("\nExiting...\n");
    }
}
