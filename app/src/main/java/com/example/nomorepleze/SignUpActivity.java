package com.example.nomorepleze;


import android.content.Intent;
import android.media.MediaCodec;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private EditText emailId, password, numberphone, fullname;
    private Button btnSignUp;
    private TextView txtSignIn;
    private FirebaseAuth mFirebaseAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        mFirebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar_signup);
        progressBar.setVisibility(View.GONE);
        emailId = findViewById(R.id.email_signup);
        password = findViewById(R.id.password_signup);
        numberphone = findViewById(R.id.phone_signup);
        fullname = findViewById(R.id.name_signup);
        txtSignIn = findViewById(R.id.signin_signup);
        btnSignUp = findViewById(R.id.signup_signup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                registerUser();
            }
        });

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mFirebaseAuth.getCurrentUser() != null)
        {

        }
    }

    private void registerUser() {
        final String email = emailId.getText().toString().trim();
        String pass = password.getText().toString().trim();
        final String phone = numberphone.getText().toString().trim();
        final String name = fullname.getText().toString().trim();

        if (email.isEmpty()){
            emailId.setError("Please enter your email!");
            emailId.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailId.setError("Please enter a valid email!");
            emailId.requestFocus();
            return;
        }

        if (pass.isEmpty()) {
            password.setError("Please enter your password!");
            password.requestFocus();
            return;
        }

        if (pass.length() < 6){
            password.setError("Password should be at least 6 characters long!");
        }

        if (name.isEmpty()) {
            fullname.setError("Please enter your full name!");
            fullname.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            numberphone.setError("Please enter your phone number!");
            numberphone.requestFocus();
            return;
        }

        if (phone.length() < 9 || phone.length() > 11) {
            numberphone.setError("Enter a valid phone number!");
            numberphone.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mFirebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.INVISIBLE);
                        if (task.isSuccessful())
                        {
                            User user = new User(
                                    name,
                                    phone,
                                    email
                            );

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    if (task.isSuccessful()){
                                        Toast.makeText(SignUpActivity.this, "Registration Success!", Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {

                                    }
                                }
                            });

                            finish();
                        }
                        else {
                            Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
