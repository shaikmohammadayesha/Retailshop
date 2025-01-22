/**
 * UIForCustomer : is user interface that handles the prompt for the user and takes inputs related to Customer.
 * and it is also responsible for calling the billing class for generating  bill for each customer.
 * it has:
 *  -no of customers
 *  -customer id
 *  -all the customer details (customer id, has discount on nxt purchase).
 *  -details of products purchased by customers (includes duplicates) it contains product details, no of units customer needs.
 *  -list of all the product names purchased by customer to ensure if  discount is applicable (discount - on purchase of 2 or more different products).
 *  -next discount available -- to make things simple while billing.
 *  -maximum price spent on a product of all the products purchased.
 */

import java.util.*;
public class UIForCustomer {
    private int noOfCustomers;
    String customerId;
    static HashSet<Customer> customerDetails = new HashSet<>();
     List<DetailsOfProductsPurchased> detailsOfProductCustomerPurchased = new ArrayList<>();
    boolean isNextDiscountAvailable;
    double maxPrice = Double.MIN_VALUE;
    private HashSet<String> productNamesToEnsureDiscount = new HashSet<>();
    Scanner scanner = new Scanner(System.in);
    //controls all customers flow: calling for reading customers id, customers purchase, billing.
    UIForCustomer(){
        System.out.println("No of customers.");
        noOfCustomers = scanner.nextInt();
        while(noOfCustomers > 0){
            customerIdDetails();
            customerPurchaseDetails();
            if(!productNamesToEnsureDiscount.isEmpty()) {
                Billing.billing(detailsOfProductCustomerPurchased, checkIsDiscountAvailable(), maxPrice, isNextDiscountAvailable, customerId);
            }
            detailsOfProductCustomerPurchased.clear();
            productNamesToEnsureDiscount.clear();
            noOfCustomers--;
        }

    }
    //prompts customer for customer id and saves the details accordingly.
    private void customerIdDetails(){
        System.out.println("Customer Id:");
        customerId = scanner.next();
        //if the customer not exists in list of customerDetails then add customer
        customerDetails.stream().filter(customer -> customer.getCustomerId().equals(customerId)).findFirst().ifPresentOrElse(customer -> {
            //if exists and has discount on next purchase then inform the customer about the discount.
            if(customer.getisNextDiscountAvailable()){
                System.out.println("You have 50 Rupees Discount on this purchase.");
                isNextDiscountAvailable = true;
            }
            //if exists and has no discount on next purchase.
            else{
                isNextDiscountAvailable = false;
            }
        },
                //if the customer does not exist add the customer details to list of customer details.
                () -> customerDetails.add(new Customer(customerId,false)));
    }
    //handles all the customers purchase.
    private void customerPurchaseDetails(){
        System.out.println("No of products customer needs");
        int noOfProductsPurchased = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the details in the format :" + "\n" + "Product-name, no-of-units" );
        while (noOfProductsPurchased != 0) {
            String[] customerPurchaseDetails = scanner.nextLine().split(",\\s*");
            if (customerPurchaseDetails.length == 2) {
                String name = customerPurchaseDetails[0].toLowerCase();
                int noOfUnits = Integer.parseInt(customerPurchaseDetails[1]);
                checkForAvailabilityOfProductAndStoreDetails(name, noOfUnits);
            }
            noOfProductsPurchased--;
        }
    }
    private void checkForAvailabilityOfProductAndStoreDetails(String name, int noOfUnits){

        UIForProduct.productsInShop.stream().filter(productDetail -> productDetail.name.equals(name) &&  productDetail.unitsAvailable >= noOfUnits).findFirst().ifPresentOrElse(productDetails ->{
            productDetails.unitsAvailable -= noOfUnits;//adjust the remaining units available after product purchase.
            detailsOfProductCustomerPurchased.add(new DetailsOfProductsPurchased(productDetails,noOfUnits));//add the available product into list of products customer purchased.
            productNamesToEnsureDiscount.add(name);//add it to a hashset so that no duplicates are allowed. helps in checking discount available or not.
           if(maxPrice < (noOfUnits * productDetails.unitPrice)) {
               maxPrice = noOfUnits * productDetails.unitPrice;
           }
        },() -> {
            System.out.println("Sorry we are currently short of " + name + ".");// In case of product not available or short of units.
          });
    }
    //separate method to check if discount is available for easy modification in case of change in terms of discount in the future.
    private boolean checkIsDiscountAvailable(){
        //Discount is available if the customer purchases 2 or more different products.
        return  productNamesToEnsureDiscount.size()>=2;
    }

}
