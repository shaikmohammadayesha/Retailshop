/**
 *UIForTax : is user interface that handles the prompt for the user and takes inputs related to tax.
 * it has
 *    -no of tax categories
 *    -array of tax categories
 * it does
 *    -reads  no of categories of tax
 *    -reads tax category values from the user stores in array of tax categories.
 */

import java.util.Scanner;
 class UIForTax {
    private int noOfTaxCategory;
    static int[] taxCategory;
    UIForTax(){
       System.out.println("No of Tax categories: ");
       Scanner scanner = new Scanner(System.in);
       noOfTaxCategory = scanner.nextInt();
       System.out.println("Tax categories: ");
       taxCategory = new int[noOfTaxCategory];
       for (int i = 0; i < noOfTaxCategory; i++) {
          taxCategory[i] = scanner.nextInt();
       }

    }
}
