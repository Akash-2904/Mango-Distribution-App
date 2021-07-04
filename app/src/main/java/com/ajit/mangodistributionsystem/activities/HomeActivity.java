package com.ajit.mangodistributionsystem.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ajit.mangodistributionsystem.R;
import com.ajit.mangodistributionsystem.pojos.Mango;
import com.ajit.mangodistributionsystem.viewholders.MangoViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Select Mango");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("/Main").child("/Mangoes");

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        FirebaseRecyclerAdapter<Mango, MangoViewHolder> adapter = new FirebaseRecyclerAdapter<Mango, MangoViewHolder>(
                Mango.class,
                R.layout.mango_recycler_layout,
                MangoViewHolder.class,
                databaseReference
        ) {
            @Override
            protected void populateViewHolder(MangoViewHolder viewHolder, final Mango model, int position) {

                viewHolder.mangoName.setText(model.getMangoName());

                Picasso.with(HomeActivity.this)
                        .load(model.getImageUrl())
                        .placeholder(R.drawable.app_logo)
                        .error(R.drawable.app_logo)
                        .fit()
                        .into(viewHolder.mangoImage);

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(HomeActivity.this, DetailsActivity.class);
                        intent.putExtra("data", model);
                        startActivity(intent);
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}