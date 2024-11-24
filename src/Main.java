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
        HashSet<Customer> customerDetails = new HashSet<>();

        //reading each customer purchase
        while (noOfCustomers > 0) {
            //noOfProductsPurchased -> no of products customer bought.Allows duplicates.
            String customerId = scanner.next();
            scanner.nextLine();
            if(customerDetails.stream().noneMatch(customer -> customer.getCustomerId().equals(customerId))){
                customerDetails.add(new Customer(customerId, false));
            }

            int noOfProductsPurchased = scanner.nextInt();

            scanner.nextLine();
            //for each customer setting the maxPrice to the minimum integer value.
            int maxPrice = Integer.MIN_VALUE;
            //reading  each product, customer purchased
            while (noOfProductsPurchased > 0) {
                String[] custPurchaseDetails = scanner.nextLine().split(",\\s*");
                if (custPurchaseDetails.length == 2) {
                    String name = custPurchaseDetails[0].toLowerCase();
                    int noOfUnits = Integer.parseInt(custPurchaseDetails[1]);
//check for each productinfo that matches with customers purchase and stores in the Details the quantity purchased

                        for (ProductInfo eachProduct : products) {
                            if (eachProduct.name.equals(name)) {

                                if (detailsOfProductCustomerPurchased.containsKey(eachProduct)) {
                                    detailsOfProductCustomerPurchased.put(eachProduct, detailsOfProductCustomerPurchased.get(eachProduct) + noOfUnits);
                                }
                                else {
                                    detailsOfProductCustomerPurchased.put(eachProduct, noOfUnits);
                                }
                                int price = detailsOfProductCustomerPurchased.get(eachProduct) * eachProduct.unitPrice;
                                if(maxPrice < price){
                                 maxPrice = price;
                                }
                                break;
                            }

                        }


                }
                else{
                    System.out.println("Please Enter in the format 'NAME-OF-THE-PRDOCUT, NO-OF-UNITS'");
                }
                noOfProductsPurchased--;
            }
                isDiscntAvail = detailsOfProductCustomerPurchased.size() >= 2;
                Optional<Boolean> searchForNextDiscountAvailable = customerDetails.stream().filter(customer -> customer.getCustomerId().equals(customerId)).map(Customer::getisNextDiscountAvailable).findFirst();
                boolean isNextDiscountAvailable = searchForNextDiscountAvailable.orElse(false);
                isNextDiscountAvailable = CustomerService.billing(detailsOfProductCustomerPurchased, isDiscntAvail, taxCategory, maxPrice, isNextDiscountAvailable);
                customerDetails.add(new Customer(customerId, isNextDiscountAvailable));
                detailsOfProductCustomerPurchased.clear();

                noOfCustomers--;
        }//end of all the customers.
            scanner.close();
    }
}