package M2;

import java.util.Arrays;

public class Problem1 {
    public static void main(String[] args) {
        //Don't edit anything here
        int[] a1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] a2 = new int[]{0, 1, 3, 5, 7, 9, 2, 4, 6, 8, 10};
        int[] a3 = new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        int[] a4 = new int[]{0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10};
        
        processArray(a1);
        processArray(a2);
        processArray(a3);
        processArray(a4);
    }
    static void processArray(int[] arr){
        System.out.println("Processing Array:" + Arrays.toString(arr));
        System.out.println("Odds output:");
        //hint: use the arr variable; don't diretly use the a1-a4 variables
        //TODO add/edit code here
        /*
         * yc73
         * 9-22-23
         * Attemtping to only output odd values from the arrays
         */
        //for loop iterates through the array
        for (int checkNum : arr) {
            //checks if the current 'checkNum' is odd
            //it divides 'checkNum' by 2, if the remainder is not 0 then it is odd
            if (checkNum % 2 != 0) {
                //if the condition is true, it will print each odd number
                System.out.print(checkNum + " ");
            }
        }
        
        //end add/edit section
        System.out.println();
        System.out.println("End process");
    }
    
}
