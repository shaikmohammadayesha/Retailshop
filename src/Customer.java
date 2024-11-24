class Customer{
    private String customerId;
   private boolean isNextTimeDiscountAvailable;
    Customer(String customerId, boolean isNextTimeDiscountAvailable){
        this.customerId = customerId;
        this.isNextTimeDiscountAvailable = isNextTimeDiscountAvailable;
    }
    String getCustomerId(){
        return customerId;
    }
    boolean getisNextDiscountAvailable(){
        return isNextTimeDiscountAvailable;
    }
}