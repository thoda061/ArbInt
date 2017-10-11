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
                int maxCountMulti = num.length() - 11;
                maxCountMulti = maxCountMulti >= 0 ? maxCountMulti : 0;
                maxCountMulti = maxCountMulti <= 9 ? maxCountMulti : 9;
                maxIntCount = addToMaxIntCount(maxIntCount,
                                               new Double
                                               (Math.pow
                                                (10,maxCountMulti)).intValue());
                num = getRem(num, maxCountMulti);
            }
        }
        if(maxIntCount.size() == 1 && maxIntCount.get(0) == 0) {
            maxIntCount.remove(0);
        }
    }

    public ArbInt () {
    }


    private ArrayList<Integer> addToMaxIntCount
        (ArrayList<Integer> oldMaxIntCount, int num) {
        if(oldMaxIntCount.isEmpty()) {
            oldMaxIntCount.add(0);
        }
        int last = oldMaxIntCount.size()-1;
        int newInt = oldMaxIntCount.get(last);
        newInt += num;
        if (newInt > 0) {
            oldMaxIntCount.set(last, newInt);
            return oldMaxIntCount;
        }  else {
            int left = 2147483647 - oldMaxIntCount.get(last);
            oldMaxIntCount.set(last, 2147483647);
            oldMaxIntCount.add(num-left);
            return oldMaxIntCount;
        }
    }
                    

    private String getRem (String num, int maxCountMulti) {
        String newMaxInt = maxInt;
        for(int i = 0; i < maxCountMulti; i++) {
            newMaxInt += "0";
        }
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
            carry = result < 0 ? true : false;
            newNum = digit + newNum;
        }
        int i = (num.length() - newMaxInt.length());
        while(carry && i > 0) {
            int digit = new Integer(num.substring(i-1, i));
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
        return newNum;
    }

    public ArbInt add(ArbInt other) {
        ArbInt newInt = new ArbInt();
        boolean negNum = negative || other.negative;
        if(negative && other.negative) {
            other.negative = false;
            this.negative = false;
            newInt = this.add(other);
            newInt.negative = true;
            other.negative = true;
            this.negative = true;
        } else if(other.negative) {
            other.negative = false;
            newInt = this.subtract(other);
            other.negative = true;
        } else if(negative) {
            negative = false;
            newInt = other.subtract(this);
            negative = true;
        }
        if(negNum) {
            return newInt;
        }
        int thisMaxIntCount = 
                !maxIntCount.isEmpty() ? maxIntCount.size() - 1 : 0;
        int otherMaxIntCount = 
            !other.maxIntCount.isEmpty() ? other.maxIntCount.size() - 1 : 0;
        int totalMaxIntCount = thisMaxIntCount + otherMaxIntCount;
        for(int i = totalMaxIntCount; i > 0; i--) {
            newInt.maxIntCount.add(2147483647);
        }
        if(maxIntCount.size() > 0 && other.maxIntCount.size() > 0) {
            try {
                int newLastCell = other.maxIntCount.get(
                    other.maxIntCount.size()-1) + maxIntCount.get(
                    maxIntCount.size()-1);
                newInt.maxIntCount.add(newLastCell);    
            } catch (NumberFormatException e) {
                String lastCell = combineString(
                    String.valueOf(maxIntCount.get(maxIntCount.size()-1)),
                    String.valueOf(other.maxIntCount.get
                                   (other.maxIntCount.size()-1)));
                lastCell = getRem(lastCell, 0);
                newInt.maxIntCount.add(2147483647);
                newInt.maxIntCount.add(new Integer(lastCell));
            }
        } else if(maxIntCount.size() > 0) {
            newInt.maxIntCount.add(maxIntCount.get(maxIntCount.size() - 1));
        } else if(other.maxIntCount.size() > 0) {
            newInt.maxIntCount.add(other.maxIntCount.get
                                   (other.maxIntCount.size() - 1));
        }
        int newRem = other.remain + remain;
        if (newRem > 0) {
            newInt.remain = newRem;
        } else {
            String rem = combineString(String.valueOf(remain),
                    String.valueOf(other.remain));
            newInt.remain = new Integer(getRem(rem, 0));
            newInt.maxIntCount = addToMaxIntCount(newInt.maxIntCount, 1);
        }
        newInt.negative = false;
        return newInt;
    }

    public ArbInt subtract(ArbInt other) {
        ArbInt newInt = new ArbInt();
        boolean negNum = negative || other.negative;
        if(negative && other.negative) {
            this.negative = false;
            other.negative = false;
            newInt = other.subtract(this);
            this.negative = true;
            other.negative = true;
        } else if (negative) {
            this.negative = false;
            newInt = this.add(other);
            newInt.negative = true;
            this.negative = true;
        } else if (other.negative) {
            other.negative = false;
            newInt = this.add(other);
            other.negative = true;
        }
        if(negNum) {
            return newInt;
        }
        int newLastCell = 0;
        int thisMaxIntCount = !maxIntCount.isEmpty() ? maxIntCount.size() - 1 :
                0;
        int otherMaxIntCount = !other.maxIntCount.isEmpty() ? 
                other.maxIntCount.size() - 1 : 0;
        int newMaxIntCount = thisMaxIntCount - otherMaxIntCount;
        newInt.negative = newMaxIntCount < 0;
        for(int i = Math.abs(newMaxIntCount); i > 0; i--) {
            newInt.maxIntCount.add(2147483647);
        }
        if(!maxIntCount.isEmpty() && !other.maxIntCount.isEmpty()) {
            newLastCell = maxIntCount.get(maxIntCount.size()-1) -
                    other.maxIntCount.get(other.maxIntCount.size()-1);
            if(newLastCell < 0) {
                if(!newInt.maxIntCount.isEmpty()) {
                    if(newInt.negative) {
                        newInt.maxIntCount.add(Math.abs(newLastCell));
                    } else {
                        newInt.maxIntCount.set(
                        newInt.maxIntCount.size()-1, 2147483647 + newLastCell);
                    }
                } else {
                    newInt.maxIntCount.add(Math.abs(newLastCell));
                    newInt.negative = true;
                }
            } else {
                if(!newInt.maxIntCount.isEmpty()) {
                    if(newInt.negative) {
                        newInt.maxIntCount.set(
                        newInt.maxIntCount.size()-1, 2147483647 - newLastCell);
                    } else {
                        newInt.maxIntCount.add(newLastCell);
                    }
                } else {
                    newInt.maxIntCount.add(newLastCell);
                }
            }
        }
        int rem = remain - other.remain;
        if(rem < 0) {
            if(newInt.maxIntCount.isEmpty()) {
                newInt.remain = Math.abs(rem);
                newInt.negative = true;
            } else {
                if(newInt.negative) {
                    newInt.remain = Math.abs(rem);
                } else {
                    newInt.remain = 2147483647 + rem;
                    int oldVal = newInt.maxIntCount.get(newInt.maxIntCount.size()-1);
                    newInt.maxIntCount.set(newInt.maxIntCount.size()-1, oldVal - 1);
                }    
            }
        } else {
            if(newInt.negative) {
                newInt.remain = 2147483647 - rem;
                int oldVal = newInt.maxIntCount.get(newInt.maxIntCount.size()-1);
                newInt.maxIntCount.set(newInt.maxIntCount.size()-1, oldVal - 1);
            } else {
                newInt.remain = rem;
            }
        }
        return newInt;
    }

    public ArbInt multiply (ArbInt other) {
        ArbInt result;
        ArbInt halveOther;
        ArbInt zero = new ArbInt("0");
        if(other.compare(zero, 0) || this.compare(zero, 0)) {
            return zero;
        }
        if (!other.compare(new ArbInt("1"), 0)) {
            halveOther = other.halve();
            if(other.remain % 2 != 0) {
                result = this.multiply(halveOther);
                result = result.add(result);
                result = result.add(this);
            } else {
                result = this.multiply(halveOther);
                result = result.add(result);
            }
        } else {
            result = this;
        }
        return result;
    }

    public ArbInt divide (ArbInt other) {
        ArbInt result = new ArbInt("0");
        ArbInt doubleOther = new ArbInt(other.toString());
        ArbInt prv = new ArbInt("0");
        ArbInt zero = new ArbInt("0");
        ArbInt one = new ArbInt("1");
        while (doubleOther.compare(this, 1)) {
            if(result.compare(zero, 0)) {
                result = result.add(one);
            } else {
                result = result.add(result);
            }
            prv = new ArbInt(doubleOther.toString());
            doubleOther = doubleOther.add(doubleOther);
        }
        if(doubleOther.compare(this, 0)) {
            if(result.compare(zero, 0)) {;
                result = result.add(one);
            } else {
                result = result.add(result);
            }
            return result;
        } else {
            if(result.compare(zero, 0)) {
                return result;
            }
            result = result.add((this.subtract(prv)).divide(other));
        }
        return result;
    }

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
        if(first.length() == second.length() && carry == 1) {
            return "1" + result;
        }
        int end;
        try {
            end = firstSmall ?
            new Integer(second.substring(0, second.length()-first.length())) :
            new Integer(first.substring(0, first.length()-second.length()));
        } catch (NumberFormatException e) {
            end = 0;
        }
        if (carry == 1 && end != 0) {
            end++;
            result = String.valueOf(end) + result;
        } else if (end != 0){
            result = String.valueOf(end) + result;
        }
        if(end == 0) {
            String endString = firstSmall ?
                second.substring(0, second.length() - first.length()) :
                first.substring(0, first.length() - second.length());
            int i = endString.length()-1;
            while(i >= 0 && carry != 0) {
                if(endString.charAt(i) != '9') {
                    char c = endString.charAt(i);
                    c += 1;
                    endString = endString.substring(0, i)
                        + c + endString.substring(i+1);
                    i = -1;
                    carry = 0;
                } else {
                    endString = endString.substring(0, i)
                        + "0" + endString.substring(i+1);
                    i--;
                }
                if(i < 0 && carry != 0) {
                    endString = "1" + endString;
                    carry = 0;
                }
            }
            result = endString + result;
        }
        return result;
    }
    
    public ArbInt halve() {
        ArbInt newInt = new ArbInt();
        newInt.negative = negative;
        int maxIntCellCount = maxIntCount.isEmpty() ? 0:maxIntCount.size() - 1;
        if(maxIntCellCount%2 == 0) {
            for(int i = 0; i < maxIntCellCount/2; i++) {
                newInt.maxIntCount.add(2147483647);
            }
        } else {
            for(int i = 0; i < maxIntCellCount/2 - 1; i++) {
                newInt.maxIntCount.add(2147483647);
            }
            newInt.maxIntCount.add(1073741823);
        }
        int lastCell  = maxIntCount.isEmpty() ? 0 :
                maxIntCount.get(maxIntCount.size()-1);
        if(lastCell != 0) {
            newInt.maxIntCount = addToMaxIntCount(
                    newInt.maxIntCount, lastCell/2);
        }
        newInt.remain = remain/2;
        return newInt;
    }
    
    public boolean compare (ArbInt other, int type) {
        switch (type) {
            case 0:
                if(other.negative != negative) {
                    return false;
                }
                if(maxIntCount.size() != other.maxIntCount.size()) {
                    return false;
                } else {
                    if(!maxIntCount.isEmpty()) {
                        if(!maxIntCount.get(maxIntCount.size()-1).equals(
                           other.maxIntCount.get
                           (other.maxIntCount.size()-1))){
                            return false;
                        }
                    }
                    if(remain == other.remain) {
                        return true;
                    } else {
                        return false;
                    }
                }
            
            case 1:
                if(negative && !other.negative) {
                    return true;
                }
                if(!negative && other.negative) {
                    return false;
                }
                
                if(negative && other.negative) {
                    this.negative = false;
                    other.negative = false;
                    boolean result =  compare(other, 2);
                    this.negative = true;
                    this.negative = true;
                    return result;
                }
                if(maxIntCount.size() < other.maxIntCount.size()) {
                    return true;
                } else if (maxIntCount.size() > other.maxIntCount.size()) {
                    return false;
                } else {
                    if(!maxIntCount.isEmpty()) {
                        if(maxIntCount.get(maxIntCount.size()-1) <
                           other.maxIntCount.get
                           (other.maxIntCount.size()-1)) {
                            return true;
                        } else if(maxIntCount.get(maxIntCount.size()-1) >
                                  other.maxIntCount.get
                                  (other.maxIntCount.size()-1)) {
                            return false;
                        }
                    }
                    if(remain < other.remain) {
                        return true;
                    } else {
                        return false;
                    }
                }
            
            case 2:
                if(!negative && other.negative) {
                    return true;
                }
                if(negative && !other.negative) {
                    return false;
                }
                if(negative && other.negative) {
                    this.negative = false;
                    other.negative = false;
                    boolean result =  compare(other, 1);
                    this.negative = true;
                    this.negative = true;
                    return result;
                }
                if(maxIntCount.size() > other.maxIntCount.size()) {
                    return true;
                } else if (maxIntCount.size() < other.maxIntCount.size()) {
                    return false;
                } else {
                    if(!maxIntCount.isEmpty()) {
                        if(maxIntCount.get(maxIntCount.size()-1) >
                           other.maxIntCount.get
                           (other.maxIntCount.size()-1)) {
                            return true;
                        } else if(maxIntCount.get(maxIntCount.size()-1) <
                                  other.maxIntCount.get
                                  (other.maxIntCount.size()-1)) {
                            return false;
                        }
                    }
                    if(remain > other.remain) {
                        return true;
                    } else {
                        return false;
                    }
                }
            
            default: return false;
        }
    }
    
    @Override
    public String toString() {
        ArrayList<String> multiValues = new ArrayList<>();
        String maxIntCountSize = String.valueOf(maxIntCount.size()-1);
        maxIntCountSize = maxIntCountSize.charAt(0) == '-' ? "0" :
            maxIntCountSize;
        String cellValue = "4611686014132420609";
        String result = "0";
        if(!maxIntCountSize.equals("0")) {
            for(int i = 0; i < maxIntCountSize.length(); i++) {
                int firstDigit = new Integer
                    (maxIntCountSize.substring(maxIntCountSize.length()-(i+1),
                                           maxIntCountSize.length() - i));
                for(int j = 0; j < cellValue.length(); j++) {
                    int secondDigit = new Integer
                        (cellValue.substring(cellValue.length()-(j+1),
                                          cellValue.length() - j));
                    String multiVal = String.valueOf(firstDigit*secondDigit);
                    if(!multiVal.equals("0")) {
                        for(int k = 0; k < i+j; k++) {
                            multiVal += "0";
                        }
                        multiValues.add(multiVal);
                    }
                }
            }
            result = multiValues.get(0);
            for(int i = 1; i < multiValues.size(); i++) {
                result = combineString(result, multiValues.get(i));
            }
        }
        multiValues = new ArrayList<>();
        if (!maxIntCount.isEmpty()) {
            String lastMaxIntCount = String.valueOf
                (maxIntCount.get(maxIntCount.size()-1));
            for(int i = 0; i < lastMaxIntCount.length(); i++) {
                int firstDigit = new Integer
                    (lastMaxIntCount.substring(lastMaxIntCount.length()-(i+1),
                                               lastMaxIntCount.length() - i));
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
            for(int i = 0; i < multiValues.size(); i++) {
                result = combineString(result, multiValues.get(i));
            }
        }
        result = combineString(result, String.valueOf(remain));
        if(result.charAt(0) == '0') {
            result = result.replaceFirst("[0]+","");
        }
        if(negative) {
            result = "-" + result;
        }
        if(result.equals("")) {
            result = "0";
        }
        return result;
    }
}
