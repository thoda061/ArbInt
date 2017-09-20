import java.util.*;

public class ArbIntMain {

    public static void main(String[]args) {
        //int i = new Integer("123456789");
        ArbInt ai = new ArbInt("134567891000");
        for(int j : ai.value) {
            System.out.println(j);
        }
        //System.out.println(i);
    }
}
