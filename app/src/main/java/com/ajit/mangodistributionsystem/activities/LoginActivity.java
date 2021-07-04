package com.ajit.mangodistributionsystem.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ajit.mangodistributionsystem.R;
import com.ajit.mangodistributionsystem.pojos.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText edtPhoneNumber;
    private EditText edtPassword;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseReference = FirebaseDatabase.getInstance().getReference("/Main").child("/Users");

        edtPhoneNumber = findViewById(R.id.edt_phone_number_login);
        edtPassword = findViewById(R.id.edt_password_login);


        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phoneNumber = edtPhoneNumber.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (!phoneNumber.equals("") && phoneNumber != null && !phoneNumber.isEmpty()) {

                    if (!password.equals("") && password != null && !password.isEmpty()) {

                        login(phoneNumber, password);

                    } else {
                        Toast.makeText(LoginActivity.this, "Password Cannot be Empty", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "PhoneNumber Cannot be Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void login(final String phoneNumber, final String password) {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(phoneNumber).exists()) {

                    User user = dataSnapshot.child(phoneNumber).getValue(User.class);

                    if (password.equals(user.getPassword())) {

                        Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                        SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("logged_in", true);
                        editor.apply();

                        SharedPreferences sharedPreferencesPhoneNumber = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editorPhoneNumber = sharedPreferencesPhoneNumber.edit();
                        editorPhoneNumber.putString("phone_number", user.getPhoneNumber());
                        editorPhoneNumber.apply();

                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finishAffinity();

                    } else {
                        Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "User not found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}