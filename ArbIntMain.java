import java.util.*;

public class ArbIntMain {

    public static void main(String[]args) {
        //int i = new Integer("123456789");
        ArbInt ai = new ArbInt("12345678901234567890123");
        ArbInt ai2 = new ArbInt("75345345990");
        //ArbInt ai3 = ai.add(ai2);
        //System.out.println(ai.combineString("1234567", "67575"));
        /*for(int j : ai.value) {
            System.out.println(j);
        }
        System.out.println(ai + " + " + ai2 + " = " + ai3);*/

        for(int i : ai.maxIntCount) {
            System.out.println("maxIntCount " + i);
        }
        System.out.println("Size " + ai.maxIntCount.size());
        System.out.println("Rem " + ai.remain);

        for(int i  : ai2.maxIntCount) {
            System.out.println("maxIntCount " + i);
        }
        System.out.println("Rem " + ai2.remain);
    }
}
