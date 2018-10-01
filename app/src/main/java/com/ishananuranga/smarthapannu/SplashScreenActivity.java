package com.ishananuranga.smarthapannu;

import android.annotation.TargetApi;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.ParseException;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIMEOUT = 5000;

    private FirebaseAuth mAuth;
    private Button signInButton;
    private Button createAccountButton;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mAuth = FirebaseAuth.getInstance();

        boolean expired = checkExpiery();

        if (expired){

            Intent loginIntent = new Intent(SplashScreenActivity.this, ExpirationNoticeActivity.class);
            startActivity(loginIntent);
            finish();

        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent loginIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(loginIntent);
                    finish();

                }
            },SPLASH_TIMEOUT);
        }



        /*createAccountButton = findViewById(R.id.btnLoginCreateAccount);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashScreenActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });

        signInButton = findViewById(R.id.btnLogin);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView email = findViewById(R.id.txtLoginEmail);
                TextView password = findViewById(R.id.txtLoginPassword);

                if (!TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(password.getText().toString()))
                    signInUser(email.getText().toString(), password.getText().toString());
                else
                    Toast.makeText(SplashScreenActivity.this, "Username and password should not be empty", Toast.LENGTH_SHORT).show();
            }
        });*/


    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    private boolean checkExpiery() {

        try {

            Calendar.getInstance();
            Calendar calendar;

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            String str1 = "31/12/2018";
            Date expDate = null;

            expDate = formatter.parse(str1);


        calendar = Calendar.getInstance();
            Date today = calendar.getTime();

            if (expDate.compareTo(today)<0)
                return true;
            else
                return false;

        } catch (java.text.ParseException e) {

        }

        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        /*FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null)
        {
            if (currentUser.isEmailVerified())
                updateUI(currentUser);
            else
                Toast.makeText(getApplicationContext(), "Please verify your email",
                        Toast.LENGTH_LONG).show();

        }else{


            Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            //Toast.makeText(this, "You are not logged in", Toast.LENGTH_SHORT).show();

            //String email = findViewById(R.id.txtLoginEmail).toString();
            //String password = findViewById(R.id.txtLoginPassword).toString();

            //signInUser(email, password);
        }*/
    }
    private void updateUI(FirebaseUser currentUser) {
        //Toast.makeText(getApplicationContext(), "User already logged in", Toast.LENGTH_LONG).show();
        //createAccountButton.setVisibility(View.GONE);
        //TODO Connect to MainActivate. Commented below is the correct one for login. Bypassed due to client needs
        //Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        //startActivity(intent);
        //finish();
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /*private void signInUser(final String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information


                            Toast.makeText(getApplicationContext(), "User signed in with " + email,Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Sign in failed. Please retry",
                                    Toast.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });
    }*/
}
