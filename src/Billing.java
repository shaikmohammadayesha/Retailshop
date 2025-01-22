/**
 * Billing : used for providing billing service to the customer.
 * It also handles deducting discount amount.
 * It is also responsible for setting  customer details -> if a customer has discount on his/her next purchase.
 */

import java.util.List;

class Billing{
    private static String name;
    private static int tax;
    private static double unitPrice;
    private static double price;
    private static int noOfUnits;
    private static double totalPrice;
    private static double gst;
    private static double totalGst;
     private static double amountToPay;

    //Billing method is to give the individual price of the product and total price of the purchase.
    static void billing(List<DetailsOfProductsPurchased> detailsOfProductsCustomerPurchased, boolean isDiscountAvailable,double maxPrice, boolean isNextDiscountAvailable, String customerId) {
        totalPrice = 0.0;
        totalGst = 0.0;
        //going through each product details from the list(detailsOfProductsCustomerPurchased)
        for (DetailsOfProductsPurchased details : detailsOfProductsCustomerPurchased){
            name = details.productInfo.name;
            tax = UIForTax.taxCategory[details.productInfo.taxIndex - 1];
            unitPrice = details.productInfo.unitPrice;
            noOfUnits = details.noOfUnits;
            price = unitPrice * noOfUnits;
            //if found the product with high amount & if discount is available then apply discount.
            if(price == maxPrice && isDiscountAvailable){
                applyDiscount();
            }
            else {
                gst = (price * tax) / 100;
                System.out.println(name + " - total unit " + noOfUnits + " - price  " + price + " gst " + gst + " total " + (price + gst));
            }
            totalGst += gst;
            totalPrice += price;
            amountToPay = totalGst+totalPrice;
        }
        generateTotalbill(isNextDiscountAvailable);
        //after finishing generating bill, setting the discount available for next purchase based on amount to pay on current purchase.
        UIForCustomer.customerDetails.stream().filter(customer -> customer.getCustomerId().equals(customerId)).findFirst().ifPresent(customer -> {
           customer.setIsNextDiscountAvailable(amountToPay>=1000.0);
           //discount is available if the amount to pay is or equal to 1000.
        });
    }

    private static void applyDiscount(){
        //discount amount can't be more than 200.
        double discountAmount = Math.min(price * 0.50, 200.0);
        price = price - discountAmount;
        gst = (price * tax) / 100;
        System.out.println(name + " - total unit " + noOfUnits + " - price after applying discount " + price + " gst " + gst + " total " + (price + gst));
    }
    private static void generateTotalbill(boolean isNextDiscountAvailable){
        if (isNextDiscountAvailable) {
            amountToPay -= 50;
            System.out.printf("Total price %.2f, total gst %.2f final price %.2f - 50 = %.2f\n", totalPrice, totalGst, totalPrice + totalGst, amountToPay);
        }
        else {
            System.out.printf("Total price %.2f, total gst %.2f final price %.2f\n", totalPrice, totalGst, amountToPay);
        }
    }
}