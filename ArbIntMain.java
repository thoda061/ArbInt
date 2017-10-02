import java.math.BigInteger;

public class ArbIntMain {

    public static void main(String[]args) {
        ArbInt ai = new ArbInt("12345678901234567890123");
        ArbInt ai2 = new ArbInt("765432109877");
        ArbInt ai3 = new ArbInt("1234567890123");
        ArbInt ai4 = ai.add(ai2);
        ArbInt ai5 = ai.subtract(ai3);
        ArbInt ai6 = new ArbInt("-12345678");
        ArbInt ai7 = new ArbInt("12345679");
        
        System.out.println(ai);
        System.out.println(ai2);
        System.out.println(ai3);
        System.out.println(ai4);
        System.out.println(ai5);
        System.out.println(ai.subtract(ai4));
        System.out.println(ai6.subtract(ai7));
    }
}