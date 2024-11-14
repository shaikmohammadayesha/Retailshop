import java.util.HashMap;
class CustomerService {
    static String name;
    static int tax;
    static int unitPrice;
    static double price;
    static int noOfUnits;
    static double totalPrice = 0;
    static double gst;
    static double totalGst = 0.0;

//Billing method is to give the individual price of the porduct and total price of the purchase.
    static void billing(HashMap<ProductInfo, Integer> detailsOfProductsCustomerPurchased, boolean isDiscountAvailable, int[] taxCat, int maxPrice) {
        //going throught each product purchased by the customer stored inthe details
        for (HashMap.Entry<ProductInfo, Integer> detail : detailsOfProductsCustomerPurchased.entrySet()) {
            name = detail.getKey().name;
            tax = taxCat[detail.getKey().taxIndex - 1];
            unitPrice = detail.getKey().unitPrice;
            noOfUnits = detail.getValue();
            price = unitPrice * noOfUnits;
            //checks if discount is available if then check is it the product with max price.
            if (isDiscountAvailable && price == maxPrice) {
                double discountamount;
                //discountamount can't be more than 200.
                discountamount = Math.min(price * 0.05, 200.0);
                price = price - discountamount;
                gst = (price * tax) /100;

                System.out.println(name + " - total unit " + noOfUnits + " - price applying discount " + price + " gst " + gst + " total " + (price+gst));
            }
            else{
                gst = (price * tax) / 100;
                System.out.println(name + " - total unit " + noOfUnits + " - price  " + price + " gst " + gst + " total " + (price+gst));
            }

            totalGst += gst;
            totalPrice += price;
        }
        System.out.printf("Total price %.2f, total gst %.2f final price %.2f\n", totalPrice, totalGst, totalPrice + totalGst);
        totalPrice = 0.0;
        totalGst = 0.0;
    }



}