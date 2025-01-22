/**
 * UIForProduct : is user interface that handles the prompt for the user and takes inputs related to products.
 * it has
 *  -no of products available in the shop.
 *  -all the product details.
 */

import java.util.HashSet;
import java.util.Scanner;
class UIForProduct {
    private int noOfProductsInShop;
    static HashSet<ProductInfo> productsInShop = new HashSet<>();
    UIForProduct(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("No of products :");
        noOfProductsInShop = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Name-of-the-product, tax-category, Unit-Price, No-of-units-available");
        while (noOfProductsInShop != 0) {
            //temporary array variable to read the whole input as String then split the details and assign them accordingly.
            String[] prodDetails = scanner.nextLine().split(",\\s*");
            if (prodDetails.length == 4) {
                String name = prodDetails[0].toLowerCase();
                int taxIndex = Integer.parseInt(prodDetails[1]);
                double unitPrice = Double.parseDouble(prodDetails[2]);
                int unitsAvailable = Integer.parseInt(prodDetails[3]);
                productsInShop.add(new ProductInfo(name, taxIndex, unitPrice, unitsAvailable));
            }
            noOfProductsInShop--;
        }
    }

}
