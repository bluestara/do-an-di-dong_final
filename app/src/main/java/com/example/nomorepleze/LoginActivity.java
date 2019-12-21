package com.example.nomorepleze;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText emailId, password;
    private Button btnSignIn;
    private TextView txtSignUp;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        txtSignUp = findViewById(R.id.forget_login);
        btnSignIn = findViewById(R.id.signin_login);


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mUser = mFirebaseAuth.getCurrentUser();
                if (mUser != null)
                {
                    finish();
                }
                else {

                }
            }
        };


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailId.getText().toString();
                String pass = password.getText().toString();

                if (email.isEmpty()){
                    emailId.setError("Please enter your email!");
                    emailId.requestFocus();
                    return;
                }

                if (pass.isEmpty()) {
                    password.setError("Please enter your password!");
                    password.requestFocus();
                    return;
                }


                mFirebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Intent on = new Intent(LoginActivity.this, AccountActivity.class);
                            //startActivity(on);
                            Toast.makeText(LoginActivity.this, "Congratulation <3 Login Successful.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Login Error! Please Login Again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSignup = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intentSignup);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
