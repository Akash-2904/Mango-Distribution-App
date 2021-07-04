package com.ajit.mangodistributionsystem.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ajit.mangodistributionsystem.R;
import com.ajit.mangodistributionsystem.pojos.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tapadoo.alerter.Alerter;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtName;
    private EditText edtPhoneNumber;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtConfirmPassword;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar_register);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("REGISTER");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference("/Main").child("/Users");

        edtName = findViewById(R.id.edt_name_register);
        edtPhoneNumber = findViewById(R.id.edt_phone_number_register);
        edtEmail = findViewById(R.id.edt_email_register);
        edtPassword = findViewById(R.id.edt_password_register);
        edtConfirmPassword = findViewById(R.id.edt_confirm_password_register);

        findViewById(R.id.btn_login_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = edtName.getText().toString().trim();
                String phoneNumber = edtPhoneNumber.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String confirmPassword = edtConfirmPassword.getText().toString().trim();

                if (!name.equals("") && name != null && !name.isEmpty()) {

                    if (!phoneNumber.equals("") && phoneNumber != null && !phoneNumber.isEmpty()) {

                        if (phoneNumber.length() == 10) {

                            if (!email.equals("") && email != null && !email.isEmpty()) {

                                if (!password.equals("") && password != null && !password.isEmpty()) {

                                    if (password.equals(confirmPassword)) {

                                        User user = new User(name, phoneNumber, email, password);
                                        registerUser(user);

                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Passwords Don't Match", Toast.LENGTH_SHORT).show();
                                        edtPassword.setText("");
                                        edtConfirmPassword.setText("");
                                    }

                                } else {
                                    Toast.makeText(RegisterActivity.this, "Password Cannot be Empty", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(RegisterActivity.this, "Email Cannot be Empty", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(RegisterActivity.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(RegisterActivity.this, "Phone Number Cannot be Empty", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "Name Is Empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void registerUser(User user) {

        databaseReference.child(user.getPhoneNumber()).setValue(user);

        Alerter.create(RegisterActivity.this)
                .setTitle("Registered Successfully!")
                .setText("You are registered successfully now you can Login")
                .setIcon(R.drawable.ic_done)
                .setBackgroundColorRes(R.color.colorAccent)
                .setDuration(3000)
                .enableVibration(true)
                .enableSwipeToDismiss()
                .show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finishAffinity();
            }
        }, 1000);

        clearViews();
    }

    private void clearViews() {
        edtName.setText("");
        edtPhoneNumber.setText("");
        edtEmail.setText("");
        edtPassword.setText("");
        edtConfirmPassword.setText("");
    }
}