/**
 * Customer class will hold the Customers id and whether the customer gets discount on nxt purchase or not.
 * it has
 *  - customer id
 *  -has discount on next purchase or not.
 *  getter methods -> to access the customerid and customer's discount on his nxt purchase.
 *  setter method -> to set the isNextTimeDiscountAvailable.
 */
class Customer {
    private String customerId;
    private boolean isNextTimeDiscountAvailable;
    Customer(String customerId, boolean isNextTimeDiscountAvailable) {
        this.customerId = customerId;
        this.isNextTimeDiscountAvailable = isNextTimeDiscountAvailable;
    }
    String getCustomerId() {
        return customerId;
    }
    boolean getisNextDiscountAvailable() {
        return isNextTimeDiscountAvailable;
    }
    void setIsNextDiscountAvailable(boolean isNextTimeDiscountAvailable){
        this.isNextTimeDiscountAvailable = isNextTimeDiscountAvailable;
    }
}