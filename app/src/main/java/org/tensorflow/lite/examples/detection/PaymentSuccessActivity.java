package org.tensorflow.lite.examples.detection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PaymentSuccessActivity extends AppCompatActivity {

    private Button loginButton;
    private double totalPrice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_succed);

        loginButton = findViewById(R.id.loginButton);

        totalPrice = getIntent().getDoubleExtra("TOTAL_PRICE", 0.0);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProductsCartNode();

                saveSalesDataToDatabase();

                goToViewPagerActivity();
            }
        });
    }

    private void deleteProductsCartNode() {
        DatabaseReference productsCartReference = FirebaseDatabase.getInstance().getReference("products_cart");
        productsCartReference.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error != null) {

                }
            }
        });
    }

    private void saveSalesDataToDatabase() {
        DatabaseReference shopSalesReference = FirebaseDatabase.getInstance().getReference("shop_sales");

        // Increment the sales counter
        DatabaseReference salesCounterRef = shopSalesReference.child("sales_counter");
        salesCounterRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long salesCount = (Long) dataSnapshot.getValue();
                if (salesCount == null) {
                    salesCount = 0L;
                }
                salesCount++;

                salesCounterRef.setValue(salesCount);

                String salesKey = "sales " + salesCount;

                DatabaseReference newSalesReference = shopSalesReference.child(salesKey);
                newSalesReference.child("total").setValue(totalPrice);
                newSalesReference.child("date").setValue(getFormattedDate(System.currentTimeMillis()));
                newSalesReference.child("time").setValue(getFormattedTime(System.currentTimeMillis()))
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {

                            } else {

                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle onCancelled event
            }
        });
    }

    private String getFormattedDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date(timestamp);
        return sdf.format(date);
    }

    private String getFormattedTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        Date date = new Date(timestamp);
        return sdf.format(date);
    }

    private void goToViewPagerActivity() {
        Intent intent = new Intent(PaymentSuccessActivity.this, ViewPagerActivity.class);
        startActivity(intent);
        finish();
    }
}
