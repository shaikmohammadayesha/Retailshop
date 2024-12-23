import java.util.*;

class Main {
    public static void main(String[] args) {
        int noOfTaxCategory;
        int[] taxCategory;
        int noOfProductsInShop;
        //hashset of type ProductInfo to store the product details.
        HashSet<ProductInfo> products = new HashSet<>();
        int noOfCustomers;

        HashSet<Customer> customerDetails = new HashSet<>();
        boolean isDiscntAvail;
        boolean isNextDiscountAvailable = false;
        System.out.println("No of Tax categories: ");
        Scanner scanner = new Scanner(System.in);

        noOfTaxCategory = scanner.nextInt();
        System.out.println("Tax categories: ");
        taxCategory = new int[noOfTaxCategory];
        for (int i = 0; i < noOfTaxCategory; i++) {
            taxCategory[i] = scanner.nextInt();
        }

        System.out.println("No of products.");

        noOfProductsInShop = scanner.nextInt();
        // to read the new line character as next we are using nextLine().
        scanner.nextLine();
//reading each product and storing in the arraylist - products.

        System.out.println("Name-of-the-product, tax-category, Unit-Price, No-of-units-available");
        while (noOfProductsInShop != 0) {
            String[] prodDetails = scanner.nextLine().split(",\\s*");
            if (prodDetails.length == 4) {
                String name = prodDetails[0].toLowerCase();
                int taxIndex = Integer.parseInt(prodDetails[1]);
                int unitPrice = Integer.parseInt(prodDetails[2]);
                int unitsAvailable = Integer.parseInt(prodDetails[3]);
                products.add(new ProductInfo(name, taxIndex, unitPrice, unitsAvailable));
            }
            noOfProductsInShop--;
        }
        // noOfCustomers - no of customers.
        System.out.println("No of customers.");
        noOfCustomers = scanner.nextInt();

        //reading each customer purchase
        while (noOfCustomers > 0) {

            System.out.println("Customer Id:");
            //noOfProductsPurchased -> no of products customer bought.Allows duplicates.
            String customerId = scanner.next();
            scanner.nextLine();
            if (customerDetails.stream().noneMatch(customer -> customer.getCustomerId().equals(customerId))) {
                customerDetails.add(new Customer(customerId, false));
            }
            Optional<Boolean> searchForNextDiscountAvailable = customerDetails.stream().filter(customer -> customer.getCustomerId().equals(customerId)).map(Customer::getisNextDiscountAvailable).findFirst();
            isNextDiscountAvailable = searchForNextDiscountAvailable.orElse(false);
            if(isNextDiscountAvailable){
                System.out.println("Note: 50 Rupees will be deducted from the final amount.");
            }
            System.out.println("No of products customer needs");
            int noOfProductsPurchased = scanner.nextInt();

            scanner.nextLine();
            //Details - Contails all the ProductInfo as key and no of units the customer pursed.
            HashMap<ProductInfo, Integer> detailsOfProductCustomerPurchased = new HashMap<>();
            //for each customer setting the maxPrice to the minimum integer value.
            int maxPrice = Integer.MIN_VALUE;
            //reading  each product, customer purchased
            System.out.println("Enter the details in the format :" + "\n" + "Product-name, no-of-units" );
            while (noOfProductsPurchased > 0) {
                String[] custPurchaseDetails = scanner.nextLine().split(",\\s*");
                if (custPurchaseDetails.length == 2) {
                    String name = custPurchaseDetails[0].toLowerCase();
                    int noOfUnits = Integer.parseInt(custPurchaseDetails[1]);
//check for each productinfo that matches with customers purchase and stores in the Details the quantity purchased

                    for (ProductInfo eachProduct : products) {
                        if (eachProduct.name.equals(name) && eachProduct.unitsAvailable >= noOfUnits) {
                            //check if the customer is asking for the same product. if yes then add the units to the old one no of units purchased.
                            if (detailsOfProductCustomerPurchased.containsKey(eachProduct)) {
                                detailsOfProductCustomerPurchased.put(eachProduct, detailsOfProductCustomerPurchased.get(eachProduct) + noOfUnits);
                            } else {
                                detailsOfProductCustomerPurchased.put(eachProduct, noOfUnits);
                            }
                            eachProduct.unitsAvailable -= noOfUnits;
                            int price = detailsOfProductCustomerPurchased.get(eachProduct) * eachProduct.unitPrice;
                            if (maxPrice < price) {
                                maxPrice = price;
                            }
                            break;
                        }
                        else if (eachProduct.name.equals(name) && eachProduct.unitsAvailable < noOfUnits) {
                            System.out.println("Sorry we only have " + eachProduct.unitsAvailable + " of " + name);
                            break;
                        }

                    }


                } else {
                    System.out.println("Please Enter in the format 'NAME-OF-THE-PRODUCT, NO-OF-UNITS'");
                }
                noOfProductsPurchased--;
            }
            isDiscntAvail = detailsOfProductCustomerPurchased.size() >= 2;
            if(isNextDiscountAvailable){
                System.out.println("Note: 50 Rupees have been deducted from the final amount.");
            }
            //here we are calling the billing method in the CustomerService class to calculate the amount and to set if discount for next time is available or not.
            isNextDiscountAvailable = CustomerService.billing(detailsOfProductCustomerPurchased, isDiscntAvail, taxCategory, maxPrice, isNextDiscountAvailable);
            if(isDiscntAvail){
                System.out.println("Note: The final bill is after applying the discount.");
            }
            if(isNextDiscountAvailable){
                System.out.println("Note: Dear customer you have a discount on your next purchase of '50 Rupees'");
            }

            customerDetails.add(new Customer(customerId, isNextDiscountAvailable));
            detailsOfProductCustomerPurchased.clear();

            noOfCustomers--;
        }//end of all the customers.
        scanner.close();
    }
}