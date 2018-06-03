import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Collections;
public class BigInt {

// =============================================================================
// Variables
// =============================================================================

boolean isNegative;
ArrayList<Integer> bigNum = new ArrayList<Integer>();

// =============================================================================
// Checks
// =============================================================================

private void isNegative(String aNumber){
        if(aNumber.charAt(0) == '-') {
                isNegative = true;
                System.out.println("isNegative = true");
        }
        else {
                isNegative = false;
                System.out.println("isNegative = false");
        }
}

private boolean isInteger(String aNumber){
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(aNumber);
        boolean b = m.matches();

        if(b) {
                return true;
        }
        else {
                return false;
        }

}

private static boolean plusSignDetected(String aNumber){
        if(aNumber.charAt(0) == '+') {
                System.out.println("'+' detected");
                return true;
        }
        else{
                return false;
        }
}

private static boolean whiteSpaceDetected(String aNumber){
        Pattern p = Pattern.compile("\\s");
        Matcher m = p.matcher(aNumber);
        boolean b = m.matches();

        if(b) {
                return true;
        }
        else {
                return false;
        }
}

// =============================================================================
// Operations
// =============================================================================

private void createArrayList(String aNumber){

        for(int i=(aNumber.length() - 1); i >= 0; i--) {
                this.bigNum.add(Character.getNumericValue(aNumber.charAt(i)));
        }

}

private static String toString(ArrayList<Integer> bigNum){


        ArrayList<Integer> temp = bigNum;

        Collections.reverse(temp);

        String s = new String();
        return s = temp.stream().map(Object:: toString).collect(Collectors.joining(""));

}

private static String removeSign(String aNumber){
        StringBuilder sb = new StringBuilder(aNumber);
        System.out.println("sign removed");
        aNumber = sb.deleteCharAt(0).toString();
        return aNumber;


}

private String process(String aNumber){
        isNegative(aNumber);

        if (isNegative == true || plusSignDetected(aNumber) == true) {
                aNumber = removeSign(aNumber);
                if (isInteger(aNumber) || whiteSpaceDetected(aNumber)) {
                        createArrayList(aNumber);
                        return toString(bigNum);

                }
                else{
                        throw new IsNotInteger("Please remove special characters");
                }
        }

        else {
                if (isInteger(aNumber) || whiteSpaceDetected(aNumber)) {
                        createArrayList(aNumber);
                        return toString(bigNum);
                }
                else {
                        throw new IsNotInteger("Please remove special characters");
                }
        }
}

// =============================================================================
// TODO Future Code
// =============================================================================

// private addNumbers(BigInt other){
//         if (other.bigNum && this.bigNum == isNegative || other.bigNum && this.bigNum == isNegative) {
//                 add(this.bigNum, other.bigNum);
//         }
//
//         elseif (this.bigNum>=other.bigNum){
//                 subtract(this.bigNum, other.bigNum);
//
//                 else{
//                         stubtract(other.bigNum, this.bigNum);
//                 }
//         }
// }
//
// Greatest absolute value to calculate sign at the end
// }
// public multiplyNumbers(){
//
// }
//
// public divideNumbers(){
//
// }

// =============================================================================
// Excecution
// =============================================================================

public static void main (String args[]){
        Scanner sc = new Scanner(System.in);
        BigInt firstNumber = new BigInt();
        System.out.println(firstNumber.process(sc.next()));

}



}
