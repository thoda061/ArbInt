import java.util.*;

public class ArbInt {

    public int[] value;

    public ArbInt (String num) {
        int size = 0;
        while (num != "") {
            try {
                int rem = new Integer(num);
                size++;
                value = new int[size];
                for(int i = 0; i < (size - 1); i++) {
                    value[i] = 2147483647;
                }
                value[size-1] = rem;
                num = "";
            } catch (NumberFormatException e) {
                //System.out.println(num);
                size++;
                num = getRem(num);
            }
        }
    }

    public String getRem (String num) {
        String maxInt = "2147483647";
        String newNum = "";
        boolean carry = false;
        for(int p = 0; p < maxInt.length(); p++) {
            int numDigit = new Integer(num.substring(num.length() - (p+1),
                                                     num.length() - p));
            int intDigit = !carry ? new Integer(
                             maxInt.substring(maxInt.length() - (p+1),
                                              maxInt.length() - p )) :
                new Integer(maxInt.substring(maxInt.length() - (p+1),
                                             maxInt.length() - p)) + 1;
            int result = numDigit - intDigit;
            String digit = result < 0 ? String.valueOf(10 + result) :
                String.valueOf(result);
            System.out.println(numDigit + " " +  intDigit + " " + result
                              + " " + digit);
            carry = result < 0 ? true : false;
            newNum = digit + newNum;
        }
        int i = num.length() - maxInt.length();
        while(carry && i > 0) {
            int digit = new Integer(num.substring(i, i + 1));
            System.out.println(digit);
            if(digit == 0) {
                i--;
                newNum = "9" + newNum;
            } else {
                newNum = String.valueOf(digit - 1) + newNum;
                carry = false;
                i--;
            }
        }
        newNum = num.substring(0, i) + newNum;
        System.out.println(newNum);
        return newNum;
    }
}
