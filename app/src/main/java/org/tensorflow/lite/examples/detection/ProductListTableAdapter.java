package org.tensorflow.lite.examples.detection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductListTableAdapter extends RecyclerView.Adapter<ProductListTableAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Product> productList;
    private double totalPrice;

    ProductListTableAdapter(Context context, List<Product> productList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.productList = productList;
        this.totalPrice = 0.00;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.textTitle.setText(product.getProductName());
        holder.textPrice.setText("Price: " + product.getPrice());
        holder.textQuantity.setText("Quantity: " + product.getQuantity());

        double productTotal = product.getPrice() * product.getQuantity();
        totalPrice += productTotal; // Add the product total to the overall total

        String productName = product.getProductName();
        if (productName != null && !productName.isEmpty()) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference("images/" + productName + ".jpg");

            storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                Picasso.get().load(uri.toString()).into(holder.productImageView);
            }).addOnFailureListener(e -> {
                holder.productImageView.setImageResource(R.drawable.place_holder);
            });
        } else {
            holder.productImageView.setImageResource(R.drawable.place_holder);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle, textPrice, textQuantity;
        ImageView productImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textView_name);
            textPrice = itemView.findViewById(R.id.textView_price);
            textQuantity = itemView.findViewById(R.id.textView_quantity);
            productImageView = itemView.findViewById(R.id.productImageView);
        }
    }
}
