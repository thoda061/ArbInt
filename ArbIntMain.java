import java.math.BigInteger;

public class ArbIntMain {

    public static void main(String[]args) {
        ArbInt ai = new ArbInt("12345678901234567890123");
        ArbInt ai2 = new ArbInt("765432109877");
        ArbInt ai3 = ai.add(ai2);
        
        System.out.println(ai);
        System.out.println(ai2);
        System.out.println(ai3);
    }
}