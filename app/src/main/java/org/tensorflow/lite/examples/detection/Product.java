package org.tensorflow.lite.examples.detection;

public class Product {

    private String imageName;
    private String productName;
    private Long price;
    private int quantity;

    private String imageURL;

    public Product() {
    }

    public Product(String imageName, String productName, Long price, int quantity) {
        this.imageName = imageName;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
