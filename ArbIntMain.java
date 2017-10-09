import java.math.BigInteger;
import java.util.Scanner;

public class ArbIntMain {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String line;

        while (sc.hasNextLine()) {
            line = sc.nextLine();

            if (!line.isEmpty() && !line.matches("^#.*")) {

                String[] tokens = line.split(" ");

                if (tokens.length > 3) {
                    System.out.println(line);
                    System.out.println("# Syntax error");
                    System.exit(1);
                }

                ArbInt firstToken = new ArbInt(tokens[0]);

                String secondToken = tokens[1];

                ArbInt thirdToken = new ArbInt(tokens[2]);

                System.out.println(line);
                switch (secondToken) {
                case "+":
                    System.out.println("# " + firstToken.add(thirdToken));
                    break;
                case "-":
                    System.out.println("# " + firstToken.subtract(thirdToken));
                    break;
                case "*":
                    System.out.println("# " + firstToken.multiply(thirdToken));
                    break;
                case "/":
                    System.out.println("Not yet supported");            
                    break;
                case "=":
                    System.out.println("# " + firstToken.compare(thirdToken, 0));
                    break;
                case "<":
                    System.out.println("# " + firstToken.compare(thirdToken, 1));
                    break;
                case ">":
                    System.out.println("# " + firstToken.compare(thirdToken, 2));
                    break;
                case "gcd":
                    System.out.println("Not yet supported");            
                    break;
                default:
                    System.out.println("Syntax error");
                    break;
                }
                System.out.println();
            }
        }

        sc.close();

        /*
        ArbInt ai = new ArbInt("12345678901234567890123");
        ArbInt ai2 = new ArbInt("765432109877");
        ArbInt ai3 = new ArbInt("4641975304");
        ArbInt ai4 = ai3.add(ai3);
        ArbInt ai5 = ai4.add(new ArbInt("12345679"));
        ArbInt ai6 = new ArbInt("-12345678");
        ArbInt ai7 = new ArbInt("12345679");
        ArbInt ai8 = new ArbInt("0");
        ArbInt ai9 = new ArbInt("5");
        ArbInt ai10 = new ArbInt("9");
        
        System.out.println(ai);
        System.out.println(ai2);
        System.out.println(ai3);
        System.out.println(ai4);
        System.out.println(ai4.remain);
        System.out.println(ai5);
        System.out.println(ai.subtract(ai4));
        System.out.println(ai6.subtract(ai7));
        System.out.println(ai7.remain);
        System.out.println(ai7.maxIntCount.isEmpty());
        System.out.println(ai8.compare(ai8, 0));
        System.out.println(ai7.multiply(ai7));*/
    }
}
