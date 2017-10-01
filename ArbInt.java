import java.util.*;
import java.lang.Math;

public class ArbInt {

    public int remain;
    public ArrayList<Integer>  maxIntCount = new ArrayList<Integer>();
    public boolean negative;
    private final String maxInt = "2147483647";

    public ArbInt (String num) {
        maxIntCount.add(0);
        negative = num.charAt(0) == '-' ? true : false;
        num = negative ? num.substring(1) : num ;
        while (num != "") {
            try {
                int rem = new Integer(num);
                remain = rem;
                num = "";
            } catch (NumberFormatException e) {
                //System.out.println(num);
                int maxCountMulti = num.length() - 11;
                maxCountMulti = maxCountMulti >= 0 ? maxCountMulti : 0;
                maxCountMulti = maxCountMulti <= 9 ? maxCountMulti : 9;
                maxIntCount = addToMaxIntCount(maxIntCount,
                                               new Double(Math.pow(10,maxCountMulti)).intValue());
                System.out.println("Size: " + maxIntCount.size());
                num = getRem(num, maxCountMulti);
                System.out.println("Num: " + num);
            }
        }
    }

    /* public ArbInt (int rem) {
        this.maxIntCount = null;
        this.remain = rem;
    }

    public ArbInt (ArbInt  maxIntCount, int rem) {
        this.maxIntCount = maxIntCount;
        this.remain = rem;
        }*/

    private ArrayList<Integer> addToMaxIntCount
        (ArrayList<Integer> maxIntCount, int num) {
        int last = maxIntCount.size()-1;
        int newInt = maxIntCount.get(last);
        System.out.println("newInt1: " + newInt);
        newInt += num;
        System.out.println("newInt2: " + newInt);
        if (newInt > 0) {
            maxIntCount.set(last, newInt);
            return maxIntCount;
        }  else {
            System.out.println("a");
            int left = 2147483647 - maxIntCount.get(last);
            System.out.println("left: " + left);
            maxIntCount.set(last, 2147483647);
            maxIntCount.add(num-left);
            return maxIntCount;
        }
    }
                    

    private String getRem (String num, int maxCountMulti) {
        String newMaxInt = maxInt;
        for(int i = 0; i < maxCountMulti; i++) {
            newMaxInt += "0";
        }
        //System.out.println(num);
        //System.out.println(newMaxInt);
        String newNum = "";
        boolean carry = false;
        for(int p = 0; p < newMaxInt.length(); p++) {
            int numDigit = new Integer(num.substring(num.length() - (p+1),
                                                     num.length() - p));
            int intDigit = !carry ? new Integer(
                             newMaxInt.substring(newMaxInt.length() - (p+1),
                                              newMaxInt.length() - p )) :
                new Integer(newMaxInt.substring(newMaxInt.length() - (p+1),
                                             newMaxInt.length() - p)) + 1;
            int result = numDigit - intDigit;
            String digit = result < 0 ? String.valueOf(10 + result) :
                String.valueOf(result);
            //System.out.println(numDigit + " " +  intDigit + " " + result
            //                  + " " + digit);
            carry = result < 0 ? true : false;
            newNum = digit + newNum;
        }
        int i = (num.length() - newMaxInt.length());
        while(carry && i > 0) {
            int digit = new Integer(num.substring(i-1, i));
            //System.out.println(digit);
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
        if(newNum.charAt(0) == '0') {
            newNum = newNum.replaceFirst("[0]+","");
        }
        System.out.println("Newnum : " + newNum);
        return newNum;
    }

    /*public ArbInt add(ArbInt secondVal) {
        ArbInt result = new ArbInt(this.maxIntCount, this.remain);
        result.value[0] += secondVal.value[0];
        try{
            int rem = result.value[1] + secondVal.value[1];
            result.value[1] = rem;
        } catch (NumberFormatException e) {
            String firstValue = String.valueOf(result.value[1]);
            String secondValue = String.valueOf(secondVal.value[1]);
            String combinedString = combineString(firstValue, secondValue);
            ArbInt combineRem = new ArbInt(combinedString);
            result.value[0] += combineRem.value[0];
            result.value[1] = combineRem.value[1];
        }
        return result;
    }

    public ArbInt subtract(ArbInt secondVal) {
        ArbInt result = new ArbInt(this.value[0], this.value[1]);
        //TODO : Get this working once support for negative ArbInt's
        // is done.
        return result;
        }*/

    private String combineString(String first, String second) {
        String result = "";
        int carry = 0;
        int smallLen = first.length() > second.length() ? second.length() :
            first.length();
        boolean firstSmall = first.length() > second.length() ? false : true;
        for(int i = 0; i < smallLen; i++) {
            int firstDigit = new Integer
                (first.substring(first.length()-(i+1),first.length()-i));
            int secondDigit = new Integer
                (second.substring(second.length()-(i+1), second.length()-i))
                + carry;
            int newDigit = firstDigit + secondDigit;
            if (newDigit > 9) {
                carry = 1;
                result = String.valueOf(newDigit - 10) + result;
            } else {
                carry = 0;
                result = String.valueOf(newDigit) + result;
            }
        }
        int end;
        try {
            end = firstSmall ?
            new Integer(second.substring(0, second.length()-first.length())) :
            new Integer(first.substring(0, first.length()-second.length()));
        } catch (NumberFormatException e) {
            end = 0;
        }
        if (carry == 1) {
            end++;
            result = String.valueOf(end) + result;
        } else if (end != 0){
            result = String.valueOf(end) + result;
        }
        return result;
    }
    
    /*public String toString() {
        ArrayList<String> multiValues = new ArrayList<>();
        String maxIntCount = String.valueOf(value[0]);
        for(int i = 0; i < maxIntCount.length(); i++) {
            int firstDigit = new Integer
                (maxIntCount.substring(maxIntCount.length()-(i+1),
                                       maxIntCount.length() - i));
            for(int j = 0; j < maxInt.length(); j++) {
                int secondDigit = new Integer
                    (maxInt.substring(maxInt.length()-(j+1),
                                      maxInt.length() - j));
                String multiVal = String.valueOf(firstDigit*secondDigit);
                for(int k = 0; k < i+j; k++) {
                    multiVal += "0";
                }
                multiValues.add(multiVal);
            }
        }
        String result = multiValues.get(0);
        for(int i = 1; i < multiValues.size(); i++) {
            result = combineString(result, multiValues.get(i));
        }
        result = combineString(result, String.valueOf(value[1]));
        if(negative) {
            result = "-" + result;
        }
        return result;
        }*/
}
