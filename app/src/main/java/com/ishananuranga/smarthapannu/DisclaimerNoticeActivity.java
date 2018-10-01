package com.ishananuranga.smarthapannu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class DisclaimerNoticeActivity extends AppCompatActivity {

    private Button nxtButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclaimer_notice);

        nxtButton = findViewById(R.id.btnNext);

        nxtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Connect to Login Activity. Commented below is the correct one for login. Bypassed due to client needs
                //Intent loginIntent = new Intent(DisclaimerNoticeActivity.this, LoginActivity.class);
                //startActivity(loginIntent);
                //finish();

                Intent loginBypassedIntent = new Intent(DisclaimerNoticeActivity.this, MainActivity.class);
                startActivity(loginBypassedIntent);
                finish();
            }
        });


    }
}
