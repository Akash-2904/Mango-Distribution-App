package com.ajit.mangodistributionsystem.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ajit.mangodistributionsystem.R;
import com.ajit.mangodistributionsystem.pojos.Mango;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.tapadoo.alerter.Alerter;

public class DetailsActivity extends AppCompatActivity {

    private EditText edtQuantity;
    private DatabaseReference databaseReference;
    private String child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Order");

        final Mango data = (Mango) getIntent().getSerializableExtra("data");

        getSupportActionBar().setTitle(data.getMangoName() + " Details");

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        child = sharedPreferences.getString("phone_number", "N/A");

        databaseReference = FirebaseDatabase.getInstance().getReference("/Main").child("/Orders");

        FloatingActionButton fab = findViewById(R.id.fab);
        TextView txtName = findViewById(R.id.txt_name_details);
        TextView txtPrice = findViewById(R.id.txt_price_details);
        TextView txtDescription = findViewById(R.id.txt_description_details);
        edtQuantity = findViewById(R.id.edt_quantity_details);
        ImageView mangoImage = findViewById(R.id.detail_image_view);

        Picasso.with(this)
                .load(data.getImageUrl())
                .placeholder(R.drawable.app_logo)
                .error(R.drawable.app_logo)
                .fit()
                .into(mangoImage);

        edtQuantity.setText(data.getQuantity());
        txtName.setText(data.getMangoName());
        txtDescription.setText(data.getDescription());
        txtPrice.setText(data.getPrice());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String quantity = edtQuantity.getText().toString().trim();

                if (!quantity.equals("") && quantity != null && !quantity.isEmpty()) {

                    data.setQuantity(quantity);
                    databaseReference.child("/" + child).setValue(data);

                    Alerter.create(DetailsActivity.this)
                            .setTitle("Booking Placed!")
                            .setText("Your Delivery will come soon")
                            .setIcon(R.drawable.ic_done)
                            .setBackgroundColorRes(R.color.colorAccent)
                            .setDuration(3000)
                            .enableVibration(true)
                            .enableSwipeToDismiss()
                            .show();


                } else {
                    Toast.makeText(DetailsActivity.this, "Quantity Cannot be Empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}