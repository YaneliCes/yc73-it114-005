package M2;

import java.util.Arrays;

public class Problem2 {
    public static void main(String[] args) {
        //Don't edit anything here
        double[] a1 = new double[]{10.001, 11.591, 0.011, 5.991, 16.121, 0.131, 100.981, 1.001};
        double[] a2 = new double[]{1.99, 1.99, 0.99, 1.99, 0.99, 1.99, 0.99, 0.99};
        double[] a3 = new double[]{0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01};
        double[] a4 = new double[]{10.01, -12.22, 0.23, 19.20, -5.13, 3.12};
        
        getTotal(a1);
        getTotal(a2);
        getTotal(a3);
        getTotal(a4);
    }
    static void getTotal(double[] arr){
        System.out.println("Processing Array:" + Arrays.toString(arr));
        double total = 0;
        String totalOutput = "";
        //hint: use the arr variable; don't diretly use the a1-a4 variables
        //TODO add/edit code here
        /*
         * yc73
         * 9-22-23
         * Attemtping to output the sum of the array (with 2 decimal places)
         */
        //for loop iterates through each number in the array
        for (double num : arr){
            //the value of 'num' is added to the 'total'
            //accumulating the sum of all the 'num' in the array
            total += num;
       }
        //set the double to a string variable
        //TODO ensure rounding is to two decimal places (i.e., 0.10, 0.01, 1.00)
        //the 'total' is formated as a string (String.format) with 2 decimal places ("%.2f")
        //the formatted string is assigned to 'totalOutput'
        totalOutput = String.format("%.2f", total);
        //end add/edit section
        System.out.println("Total is " + totalOutput);
        System.out.println("End process");
    }
    
}
