import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
public class BigInt {

// =============================================================================
// Variables
// =============================================================================

private boolean isNegative;
private ArrayList<Integer> bigNum = new ArrayList<Integer>();


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

public BigInt(BigInt aNumber){
        this.bigNum = aNumber.bigNum;
        this.isNegative = aNumber.isNegative;
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

private int compareBigInts(BigInt aNumber, BigInt other){
        this.addZerosIfNeeded(other);
        int signComparision = Boolean.compare(!aNumber.isNegative, !other.isNegative);
        if ( signComparision!=0) {
                removeZeros(this);
                removeZeros(other);
                return signComparision;
        }
        else{
                for(int i = aNumber.bigNum.size()-1; i > 0; i--) {
                        int comparison = aNumber.isNegative ?
                                         Integer.compare(other.bigNum.get(i), aNumber.bigNum.get(i))    :
                                         Integer.compare(aNumber.bigNum.get(i), other.bigNum.get(i));
                        if ( comparison != 0 ) {
                                removeZeros(this);
                                removeZeros(other);
                                return comparison;
                        }
                }

        }
        return 0; // are the same
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
//        System.out.println("sign removed");
        aNumber = sb.deleteCharAt(0).toString();
        return aNumber;
}

@Override
public String toString(){
        ArrayList<Integer> temp = this.bigNum;
        String s = new String();
        s = temp.stream().map(Object:: toString).collect(Collectors.joining(""));
        StringBuilder stringbuilder1 = new StringBuilder(s).reverse();
        s = stringbuilder1.toString();
        s = s.replaceFirst("^0+(?!$)", "");

        if (s.matches("0")) {
                return "0";
        }
        if (this.isNegative == true) {
                s = "-" + s;
        }

        return s;


}

private void addZerosIfNeeded(BigInt this, BigInt other){
        int numberOfZeros = 0;

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
}

private void removeZeros(BigInt aNumber){
        if (aNumber.toString() != "0") {
                Collections.reverse(aNumber.bigNum);
                Iterator<Integer> it = aNumber.bigNum.iterator();
                int i = 0;
                while (it.hasNext() && it.next() == 0) {
                        ++i;
                }
                aNumber.bigNum.subList(0, i).clear();
                Collections.reverse(aNumber.bigNum);
        }
}

// =============================================================================
// Subtraction
// =============================================================================

public BigInt subtract(BigInt other){
// check and add zeros if needed
        this.addZerosIfNeeded(other);

        if (other.isNegative && this.isNegative == true) {
                if (compareBigInts(this,other) == -1) {
                        addZerosIfNeeded(other);
                        BigInt result = new BigInt(createDifference(this, other).toString().replaceAll("[\\[\\], ]", ""));
                        return result;
                }

                else{
                        addZerosIfNeeded(other);
                        BigInt result = new BigInt(createDifference(other, this).toString().replaceAll("[\\[\\], ]", ""));
                        return result;
                }
        }
        else if (this.isNegative == false && other.isNegative == true) {
                BigInt result = new BigInt(createSum(this, other).toString().replaceAll("[\\[\\], ]", ""));
                return result;
        }
        else if (this.isNegative == true && other.isNegative == false) {
                BigInt result = new BigInt("-"+createSum(this, other).toString().replaceAll("[\\[\\], ]", ""));
                return result;
        }
        else {
                BigInt result = new BigInt(createDifference(this, other).toString().replaceAll("[\\[\\], ]", ""));
                return result;
        }

}

private ArrayList<Integer> createDifference(BigInt aNumber, BigInt other ){

        ArrayList<Integer> tempArrayList = new ArrayList<Integer>();
        int tempInt = 0;
        for(int i = (aNumber.bigNum.size()-1); i >=0; i--) {
                tempInt = aNumber.bigNum.get(i) - other.bigNum.get(i);
                tempArrayList.add(tempInt);
                tempInt = 0;
        }
//        System.out.println(tempArrayList);
        for(int i = (tempArrayList.size()-1); i >= 1; i--) {
                if (tempArrayList.get(i) < 0) {
                        tempArrayList.set(i,tempArrayList.get(i) + 10);
                        tempArrayList.set(i - 1,tempArrayList.get(i - 1) - 1 );
//                        System.out.println(tempArrayList.get(i));
                }

        }
//        System.out.println(tempArrayList.get(0));
//        System.out.println(tempArrayList);
        return tempArrayList;
}

// =============================================================================
// Addition
// =============================================================================
public BigInt add(BigInt other){
// check and add zeros if needed
        this.addZerosIfNeeded(other);
//end check
//check case
        if (other.isNegative && this.isNegative == true) {
                if (other.bigNum.toString().equals("[0]") && this.bigNum.toString().equals("[0]")) {
                        BigInt result = new BigInt(createSum(this, other).toString().replaceAll("[\\[\\], ]", ""));
                        return result;
                }
                else{
                        BigInt result = new BigInt("-"+createSum(this, other).toString().replaceAll("[\\[\\], ]", ""));
                        return result;
                }
        }

        else if (this.isNegative == false && other.isNegative == true) {
                BigInt result = new BigInt(createDifference(this, other).toString().replaceAll("[\\[\\], ]", ""));
                return result;
        }
        else if (this.isNegative == true && other.isNegative == false) {
                BigInt result = new BigInt(createDifference(other, this).toString().replaceAll("[\\[\\], ]", ""));
                return result;
        }
        else{
                BigInt result = new BigInt(createSum(this, other).toString().replaceAll("[\\[\\], ]", ""));
                return result;
        }

}
//end check

private ArrayList<Integer> createSum(BigInt aNumber, BigInt other ){
        ArrayList<Integer> tempArrayList = new ArrayList<Integer>();
        int tempInt = 0;
        for(int i = (aNumber.bigNum.size()-1); i >=0; i--) {
                tempInt = aNumber.bigNum.get(i) + other.bigNum.get(i);
                tempArrayList.add(tempInt);
                tempInt = 0;
        }
//     System.out.println(tempArrayList);

        for(int i = (tempArrayList.size()-1); i >= 1; i--) {
                if (tempArrayList.get(i) > 9) {
                        tempArrayList.set(i,tempArrayList.get(i) - 10);
                        tempArrayList.set(i - 1,tempArrayList.get(i - 1) + 1 );
//                        System.out.println(tempArrayList.get(i));
                }

        }
        //      System.out.println(tempArrayList.get(0));
        if (tempArrayList.get(0) > 9) {
                tempArrayList.set(0,tempArrayList.get(0) - 10);
                tempArrayList.add(0,1);

        }
        //      System.out.println(tempArrayList);
        return tempArrayList;
}

// =============================================================================
// Multiply
// =============================================================================
public BigInt multiply(BigInt other){
        this.addZerosIfNeeded(other);
        if ((this.isNegative == false && other.isNegative == true) || (this.isNegative == true && other.isNegative == false)) {
                BigInt result = new BigInt("-"+createProduct(this, other).toString().replaceAll("[\\[\\], ]", ""));
                removeZeros(this);
                removeZeros(other);
                return result;
        }
        BigInt result = new BigInt(createProduct(this, other).toString().replaceAll("[\\[\\], ]", ""));
        removeZeros(this);
        removeZeros(other);
        return result;
}

private ArrayList<Integer> createProduct(BigInt aNumber, BigInt other ){
        ArrayList<Integer> finalProduct = new ArrayList<Integer>();
        int skipDigit =0;
        for (int i = 0; i < (other.bigNum.size() + aNumber.bigNum.size()); i++) {
                finalProduct.add(0);
        }
        for (int i = 0; i < other.bigNum.size(); i++) {
                ArrayList<Integer> middleProduct = new ArrayList<Integer>();
                int remainder = 0;
                int roughproduct = 0;
                for (int j = 0; j < aNumber.bigNum.size(); j++) {
                        roughproduct = other.bigNum.get(i) * aNumber.bigNum.get(j) + calculateCarry(roughproduct);
                        remainder = calculateRemainder(roughproduct);
                        middleProduct.add(remainder);
                        if (calculateCarry(roughproduct) > 0 && j == aNumber.bigNum.size()-1) {
                                middleProduct.add(calculateCarry(roughproduct));
                        }
                }
//                System.out.println(middleProduct.toString());
                addToFinalProduct(middleProduct, finalProduct, skipDigit).toString();
                skipDigit += 1;
        }
        Collections.reverse(finalProduct);
        return finalProduct;
}

private Integer calculateCarry(int roughproduct){
        int carry = 0;
        while (roughproduct > 9) {
                roughproduct -= 10;
                carry += 1;
        }
        return carry;
}

private Integer calculateRemainder(int roughproduct){
        while (roughproduct > 9) {
                roughproduct -= 10;
        }
        return roughproduct;
}

private ArrayList<Integer> addToFinalProduct(ArrayList<Integer> middleProduct, ArrayList<Integer> finalProduct,int skipDigit){
        for(int i = 0; i < middleProduct.size(); i++) {
                finalProduct.set(i + skipDigit, finalProduct.get(i + skipDigit) + middleProduct.get(i));
        }
//        System.out.print(finalProduct.toString());
        for(int i = 0; i < finalProduct.size(); i++) {
                if (finalProduct.get(i) > 9) {
                        finalProduct.set(i + 1, finalProduct.get(i+1) + calculateCarry(finalProduct.get(i)));
                        finalProduct.set(i, calculateRemainder(finalProduct.get(i)));
                }
        }
        return finalProduct;
}

// =============================================================================
// Divide
// =============================================================================

public BigInt divideBy(BigInt other){


        BigInt zero = new BigInt("0");

        if (this.toString().equals(zero.toString()) || this.bigNum.size() < other.bigNum.size()) {
                return zero;
        }
        else if (other.toString().equals(zero.toString())) {
                System.out.println("You cannot divide by zero");
                return zero;
        }

        else if ((this.isNegative == false && other.isNegative == true) || (this.isNegative == true && other.isNegative == false)) {
                this.addZerosIfNeeded(other);
                BigInt result = new BigInt("-"+createQuotient(this, other).toString());
                return result;
        }
        return createQuotient(this, other);
}


private BigInt createQuotient (BigInt aNumber, BigInt other){

        BigInt dividend = new BigInt(aNumber);
        BigInt divisor = new BigInt(other);

        dividend.isNegative = false;
        divisor.isNegative = false;
        BigInt finalQoutient = new BigInt("0");
        BigInt one = new BigInt("1");
        BigInt zero = new BigInt("0");
        if(compareBigInts(dividend, divisor) == 0) {
                return one;
        }
        else{
                while(compareBigInts(dividend, divisor) == 1) {
                        BigInt two = one;
                        BigInt lastTwo = new BigInt("0");
                        BigInt temp = divisor;
                        BigInt lastTemp = new BigInt("0");
                        while(compareBigInts(dividend, temp) == 1) {
                                lastTwo = two;
                                lastTemp = temp;

                                if (two == one) {
                                        two = two.add(one);
                                }
                                else{
                                        two = two.add(two);
                                }
                                temp = divisor.multiply(two);
                        }

                        finalQoutient = finalQoutient.add(lastTwo);
                        dividend = dividend.subtract(lastTemp);
                }
                if (dividend.toString().equals(divisor.toString())) {
                        finalQoutient =  finalQoutient.add(one);
                }
        }

        return finalQoutient;
}

// =============================================================================
// Modulo
// =============================================================================

public BigInt modulus(BigInt other){
        BigInt zero = new BigInt("0");


        if (this.divideBy(other).toString().equals(zero.toString())) {
                return zero;
        }
        else{
                return this.subtract(other.multiply(this.divideBy(other)));
        }
}

public static void main(String[] args){
        BigInt b1;
        BigInt b2;
        BigInt b3;
        b1 = new BigInt("100");
        b2 = new BigInt("5");
        b3 = b1.divideBy(b2);
        System.out.println("quotient b3 is " + b1 +" / " + b2 + " = " + b3);
}
}
