import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int noOfTaxCategory = scanner.nextInt();

        int[] taxCategory = new int[noOfTaxCategory];
        for (int i = 0; i < noOfTaxCategory; i++) {
            taxCategory[i] = scanner.nextInt();
        }
        //hashset of type ProductInfo to store the product details.
        HashSet<ProductInfo> products = new HashSet<>();

        int noOfProductsInShop = scanner.nextInt();
        // to read the new line character as next we are using nextLine().
        scanner.nextLine();
//reading each product and storing in the arraylist - products.
        while (noOfProductsInShop != 0) {
            String[] prodDetails = scanner.nextLine().split(",\\s*");
            if (prodDetails.length == 3) {
                String name = prodDetails[0].toLowerCase();
                int taxIndex = Integer.parseInt(prodDetails[1]);
                int unitPrice = Integer.parseInt(prodDetails[2]);
                products.add(new ProductInfo(name, taxIndex, unitPrice));
            }
            noOfProductsInShop--;
        }
        // noOfCustomers - no of customers.
        int noOfCustomers = scanner.nextInt();
        boolean isDiscntAvail;
        //Details - Contails all the ProductInfo as key and no of units the customer pursed.
        HashMap<ProductInfo, Integer> detailsOfProductCustomerPurchased = new HashMap<>();
        //reading each customer purchase
        while (noOfCustomers > 0) {
            //noOfProductsPurchased -> no of products customer bought.Allows duplicates.
            int noOfProductsPurchased = scanner.nextInt();
            //same as above to read the nextLine character.
            scanner.nextLine();
            //for each customer setting the maxPrice to the minimum integer value.
            int maxPrice = Integer.MIN_VALUE;
            //reading  each product, customer purchased
            while (noOfProductsPurchased > 0) {
                String[] custDetails = scanner.nextLine().split(",\\s*");
                if (custDetails.length == 2) {
                    String name = custDetails[0].toLowerCase();
                    int noOfUnits = Integer.parseInt(custDetails[1]);
//check for each productinfo that matches with customers purchase and stores in the Details the quantity purchased
                    if (products.contains(name)) {
                        for (ProductInfo eachProduct : products) {
                            if (eachProduct.name.equals(name)) {

                                if (detailsOfProductCustomerPurchased.containsKey(eachProduct)) {
                                    detailsOfProductCustomerPurchased.put(eachProduct, detailsOfProductCustomerPurchased.get(eachProduct) + noOfUnits);
                                } else {
                                    detailsOfProductCustomerPurchased.put(eachProduct, noOfUnits);
                                }

                                break;
                            }
                        }
                    }
                }
            }
                isDiscntAvail = detailsOfProductCustomerPurchased.size() >= 2;
                CustomerService.billing(detailsOfProductCustomerPurchased, isDiscntAvail, taxCategory, maxPrice);
                CustomerService.billing();
                CustomerService.resetValues();
                noOfCustomers--;
        }//end of all the customers.
            scanner.close();
    }
}