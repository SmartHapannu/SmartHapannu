package com.ishananuranga.smarthapannu;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    private TextView correctCount;
    private TextView incorrectCount;
    private TextView unansweredCount;
    private TextView allCount;
    private TextView mainMark;
    private TextView feedBack;
    private ImageView smileyFace;
    private Button btnMoreDetails;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        final String correct_count = intent.getStringExtra("correct_count");
        String incorrect_count = intent.getStringExtra("incorrect_count");
        final String unanswered_count = intent.getStringExtra("unanswered_count");
        final String all_count = intent.getStringExtra("all_count");
        final String paper_id = intent.getStringExtra("paper_id");
        String mark = intent.getStringExtra("mark");
        final String attempt_master_id = intent.getStringExtra("attempts_master_id");

        final String fraction_correct_count = intent.getStringExtra("fraction_correct_count");
        final String addition_correct_count = intent.getStringExtra("addition_correct_count");
        final String subtract_correct_count = intent.getStringExtra("subtract_correct_count");
        final String divide_correct_count = intent.getStringExtra("divide_correct_count");
        final String fraction_total_count =intent.getStringExtra("fraction_total_count");
        final String addition_total_count =intent.getStringExtra("addition_total_count");
        final String subtract_total_count = intent.getStringExtra("subtract_total_count");
        final String divide_total_count  =intent.getStringExtra("divide_total_count");




        sharedPreferences = getSharedPreferences("paper_progress_" + paper_id,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean("initialized", false);
        editor.commit();

        correctCount = findViewById(R.id.text_view_correct_count);
        incorrectCount = findViewById(R.id.text_view_incorrect_count);
        unansweredCount = findViewById(R.id.text_view_unanswered_count);
        allCount = findViewById(R.id.text_view_all_count);
        mainMark = findViewById(R.id.text_view_marks);
        feedBack = findViewById(R.id.text_view_rating);
        smileyFace = findViewById(R.id.img_view_smiley);
        btnMoreDetails = findViewById(R.id.btnMoreDetails);

        correctCount.setText(correct_count);
        incorrectCount.setText(incorrect_count);
        unansweredCount.setText(unanswered_count);
        allCount.setText(all_count);
        mainMark.setText(mark + "%");

        if (Integer.parseInt(mark) >= 80){

            feedBack.setText("b;d fyd|hs");
            smileyFace.setImageResource(R.drawable.best);

        } else if (Integer.parseInt(mark) >= 60){

            feedBack.setText("fyd|hs");
            smileyFace.setImageResource(R.drawable.good);

        } else if (Integer.parseInt(mark) >= 40){

            feedBack.setText("idudkHhs' Wkkaÿ jkak");
            smileyFace.setImageResource(R.drawable.normal);

        } else {

            feedBack.setText(";j;a Wkkaÿ jkak");
            smileyFace.setImageResource(R.drawable.try_again);

        }

        btnMoreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MoreDetailsActivity.class);
                intent.putExtra("fraction_correct_count",fraction_correct_count);
                intent.putExtra("addition_correct_count",addition_correct_count);
                intent.putExtra("subtract_correct_count",subtract_correct_count);
                intent.putExtra("divide_correct_count",divide_correct_count );
                intent.putExtra("fraction_total_count",fraction_total_count);
                intent.putExtra("addition_total_count",addition_total_count);
                intent.putExtra("subtract_total_count",subtract_total_count);
                intent.putExtra("divide_total_count",divide_total_count);
                intent.putExtra("attempt_master_id",attempt_master_id);
                intent.putExtra("paper_id",paper_id);

                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
