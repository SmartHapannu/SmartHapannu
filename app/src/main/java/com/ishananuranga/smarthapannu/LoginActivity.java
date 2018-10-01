package com.ishananuranga.smarthapannu;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button signInButton;
    private Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        checkEmailVerified();

        createAccountButton = findViewById(R.id.btnLoginCreateAccount);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });

        signInButton = findViewById(R.id.btnLogin);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView email = findViewById(R.id.txtCreateAccountEmail);
                TextView password = findViewById(R.id.txtCreateAccountPassword);

                if (!TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(password.getText().toString())) {

                    signInUser(email.getText().toString(), password.getText().toString());

                }else
                    Toast.makeText(LoginActivity.this, "Username and password should not be empty", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void checkEmailVerified() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null)
        {
            if (currentUser.isEmailVerified())
                updateUI(currentUser);
            else
                Toast.makeText(getApplicationContext(), "Please verify your email and try again!",
                        Toast.LENGTH_LONG).show();

        }else{

            Toast.makeText(this, "You are not logged in", Toast.LENGTH_SHORT).show();

        }
    }


    private void updateUI(FirebaseUser currentUser) {
        //Toast.makeText(getApplicationContext(), "User already logged in", Toast.LENGTH_LONG).show();
        //createAccountButton.setVisibility(View.GONE);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void signInUser(final String email, String password) {



        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information



                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user.isEmailVerified()) {
                                updateUI(user);
                                Toast.makeText(getApplicationContext(), "User signed in with " + email,Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "Please verify your email",
                                        Toast.LENGTH_LONG).show();
                            }


                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Sign in failed. Please retry",
                                    Toast.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

        moveTaskToBack(true);
    }
}
