/**
 * ProductInfo : is to store product details (1.name of the product, 2.tax index that it belongs, 3.unit price of product and 4.Units of product available)
 * it has
 *  -product name.
 *  -tax index to determine the tax category it belongs to.
 *  -unit price of the product.
 *  -units of that product available in shop.
 */

class ProductInfo {
    String name;
    int taxIndex;
    double unitPrice;
    int unitsAvailable;
    ProductInfo(String name, int index, double price, int unitsAvailable) {
        this.name = name;
        this.taxIndex = index;
        this.unitPrice = price;
        this.unitsAvailable = unitsAvailable;
    }
}