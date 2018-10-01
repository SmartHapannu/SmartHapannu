package com.ishananuranga.smarthapannu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button createAccountButton;
    private EditText name;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mAuth = FirebaseAuth.getInstance();

        createAccountButton = findViewById(R.id.btnCreateAccount);
        password = findViewById(R.id.txtCreateAccountPassword);
        confirmPassword = findViewById(R.id.txtCreateAccountPassword);
        
        
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(password.getText().toString().length()<=8 &&!isValidPassword(password.getText().toString())) {
                    Toast.makeText(CreateAccountActivity.this, "Password must contain minimum 8 characters at least 1 Alphabet, 1 Number and 1 Special Character", Toast.LENGTH_SHORT).show();

                }else if (!password.getText().toString().equals(confirmPassword.getText().toString())){
                    Toast.makeText(CreateAccountActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                }else{

                    String pw = password.getText().toString();
                    String cpw = confirmPassword.getText().toString();

                    Log.v("password_", pw);
                    Log.v("conf_password_", cpw);


                    name = findViewById(R.id.txtCreateAccountName);
                    email = findViewById(R.id.txtCreateAccountEmail);
                    password = findViewById(R.id.txtCreateAccountPassword);
                    confirmPassword = findViewById(R.id.txtCreateAccountConfirmPassword);

                    if(!TextUtils.isEmpty(email.getText().toString()) || !TextUtils.isEmpty(password.getText().toString()) || !TextUtils.isEmpty(confirmPassword.getText().toString())){

                        createUser(getApplicationContext(),email.getText().toString(),password.getText().toString());

                        //Toast.makeText(CreateAccountActivity.this, "Username and password should not be empty", Toast.LENGTH_SHORT).show();

                    } else if(!password.getText().toString().equals(confirmPassword.getText().toString())){

                        Toast.makeText(getApplicationContext(),"Passwords does not match",Toast.LENGTH_LONG).show();
                    }else{
                        createUser(getApplicationContext(),email.getText().toString(),password.getText().toString());

                    }
                }

                

            }
        });
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }


    protected void createUser(final Context context, final String email, String password){
        try{
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //Log.d(TAG, "createUserWithEmail:success");
                                Toast.makeText(context, "User created with " + email,
                                        Toast.LENGTH_LONG).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                sendEmail(user);
                                updateUI(user);
                            } else {
                                try {
                                    throw task.getException();
                                }catch (FirebaseAuthWeakPasswordException ex){
                                    Toast.makeText(context,"Weak Password please enter at least 6 characters",Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                // If sign in fails, display a message to the user.

                                Toast.makeText(context, "Authentication failed. Please try again",
                                        Toast.LENGTH_LONG).show();
                                updateUI(null);
                            }

                            // ...
                        }
                    });

        }catch (Exception ex){

        }


    }

    private void sendEmail(FirebaseUser user) {
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CreateAccountActivity.this, "Verification email sent", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateUI(Object o) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
        //Toast.makeText(this,"Destroyed",Toast.LENGTH_LONG).show();
    }
}
