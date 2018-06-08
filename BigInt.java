import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
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
// Constructors
// =============================================================================

public BigInt(){
        isNegative = false;
        this.bigNum.add(0);
}

public BigInt(String aNumber){
        isNegative(aNumber);

        if (isNegative == true || plusSignDetected(aNumber) == true) {
                aNumber = removeSign(aNumber);
                if (isInteger(aNumber) && whiteSpaceDetected(aNumber) == false) {
                        createArrayList(aNumber);
                }
                else{
                        throw new IsNotInteger("Please remove special characters");
                }
        }

        else {
                if (isInteger(aNumber) && whiteSpaceDetected(aNumber) == false) {
                        createArrayList(aNumber);
                }
                else {
                        throw new IsNotInteger("Please remove special characters");
                }
        }
}


@Override
public String toString(){
        ArrayList<Integer> temp = this.bigNum;
        String s = new String();

        Collections.reverse(temp);

      s = temp.stream().map(Object:: toString).collect(Collectors.joining(""));
      return  s.replaceFirst("^0+(?!$)", "");

}

public BigInt add(BigInt other){
int numberOfZeros = 0;

// check and add zeros if needed
        if (this.bigNum.size()-1 > other.bigNum.size()-1) {
                numberOfZeros = this.bigNum.size() - other.bigNum.size();
                for (int i=0; i < numberOfZeros; i++) {
                        other.bigNum.add(0);
                }
        }
        else if (other.bigNum.size()-1 > this.bigNum.size()-1) {
                numberOfZeros = other.bigNum.size() - this.bigNum.size();
                for (int i=0; i < numberOfZeros; i++) {
                        this.bigNum.add(0);
                }
        }


        ArrayList<Integer> sum = createSum(createTempArrayList(this, other));
        BigInt result = new BigInt(sum.toString().replaceAll("[\\[\\], ]", ""));
        return result;
}

private ArrayList<Integer> createTempArrayList(BigInt aNumber, BigInt other ){

        ArrayList<Integer> tempArrayList = new ArrayList<Integer>();
        int tempInt = 0;
        for(int i = (aNumber.bigNum.size()-1); i >=0; i--) {
                tempInt = aNumber.bigNum.get(i) + other.bigNum.get(i);
                System.out.println(tempInt);
                tempArrayList.add(tempInt);
                tempInt = 0;
        }
        return tempArrayList;
}

private ArrayList<Integer> createSum(ArrayList<Integer> tempArrayList){
        for(int i = (tempArrayList.size()-1); i >= 1; i--) {
                if (tempArrayList.get(i) > 9) {
                        tempArrayList.set(i,tempArrayList.get(i) - 10);
                        tempArrayList.set(i - 1,tempArrayList.get(i - 1) + 1 );
                        System.out.println(tempArrayList.get(i));
                }

        }
        System.out.println(tempArrayList.get(0));
        if (tempArrayList.get(0) > 9) {
                tempArrayList.set(0,tempArrayList.get(0) - 10);
                tempArrayList.add(0,1);

        }
        return tempArrayList;
}

// =============================================================================
// Checks
// =============================================================================

private void isNegative(String aNumber){
        if(aNumber.charAt(0) == '-') {
                isNegative = true;
                //  System.out.println("isNegative = true");
        }
        else {
                isNegative = false;
                //  System.out.println("isNegative = false");
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

private String removeSign(String aNumber){
        StringBuilder sb = new StringBuilder(aNumber);
        System.out.println("sign removed");
        aNumber = sb.deleteCharAt(0).toString();
        return aNumber;


}

// =============================================================================
// TODO Future Code
// =============================================================================

//  private addNumbers(BigInt other){
//
//          if (other.bigNum && this.bigNum == (isNegative == true) || other.bigNum && this.bigNum == (isNegative == false) ) {
//                  add(this.bigNum, other.bigNum);
//          }
//          else if (this.bigNum >= other.bigNum){
//                  subtract(this.bigNum, other.bigNum);
//          }
//          else{
//                  stubtract(other.bigNum, this.bigNum);
//          }
//  }

//
// public divideNumbers(){
//
// }



}
