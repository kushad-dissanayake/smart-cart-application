package org.tensorflow.lite.examples.detection;

import android.graphics.Bitmap;

public class ProductListTableData {
    private Bitmap productImage;
    private String detectedLabels;
    private String price;
    private String quantity;

    private String imageURL;

    public ProductListTableData(Bitmap productImage, String detectedLabels, String price, String quantity) {
        this.productImage = productImage;
        this.detectedLabels = detectedLabels;
        this.price = price;
        this.quantity = quantity;
    }

    public Bitmap getProductImage() {
        return productImage;
    }

    public String getDetectedLabels() {
        return detectedLabels;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }
}