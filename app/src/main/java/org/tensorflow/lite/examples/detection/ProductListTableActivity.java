package org.tensorflow.lite.examples.detection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductListTableActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProductListTableAdapter adapter;
    List<Product> productList;
    TextView totalTextView;
    Button payButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list_table);

        productList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductListTableAdapter(this, productList);
        recyclerView.setAdapter(adapter);

        totalTextView = findViewById(R.id.total);
        payButton = findViewById(R.id.button);

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductListTableActivity.this, PaymentSuccessActivity.class));


            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("products_cart").child("products");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    Product product = productSnapshot.getValue(Product.class);
                    productList.add(product);
                }

                adapter.notifyDataSetChanged();

                double totalPrice = calculateTotalPrice(productList);
                totalTextView.setText("Total: " + totalPrice + "LKR");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductListTableActivity.this, PaymentSuccessActivity.class);
                double totalPrice = calculateTotalPrice(productList);
                intent.putExtra("TOTAL_PRICE", totalPrice);
                startActivity(intent);
            }
        });

    }

    private double calculateTotalPrice(List<Product> products) {
        double total = 0.0;
        for (Product product : products) {
            total += product.getPrice() * product.getQuantity();
        }
        return total;
    }
}
