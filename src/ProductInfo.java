//This class ProductInfo is to store the product details name of the product, tax index that it belongs and the unit price of the product.
//This class is used to create object and used as a dataType.

class ProductInfo{
 String name;
 int taxIndex;
 int unitPrice;


ProductInfo(String name, int index, int price){
this.name = name;
this.taxIndex = index;
this.unitPrice = price;
}

}