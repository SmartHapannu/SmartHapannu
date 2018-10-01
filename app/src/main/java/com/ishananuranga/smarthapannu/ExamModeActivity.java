package com.ishananuranga.smarthapannu;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ExamModeActivity extends AppCompatActivity {

    private Button leisureModeButton;
    private Button examModeButton;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_mode);

        Intent intent = getIntent();
        final String paper_id = intent.getStringExtra("paper_id");
        final String paper_name = intent.getStringExtra("paper_name");
        final String paper_year = intent.getStringExtra("paper_year");
        final String question_count = intent.getStringExtra("question_count");
        final String allocated_time = intent.getStringExtra("allocated_time");

        leisureModeButton = findViewById(R.id.leisureModeButton);
        examModeButton = findViewById(R.id.examModeButton);

        sharedPreferences = getSharedPreferences("paper_progress_" + paper_id,MODE_PRIVATE);

         leisureModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferences!=null && sharedPreferences.contains("initialized")) {
                    Log.d("", "");

                    boolean initStatus = sharedPreferences.getBoolean("initialized", false);
                    String mode = sharedPreferences.getString("paper_mode", "none");

                    if (initStatus && mode.equals("exam")){
                        android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(ExamModeActivity.this);
                        mBuilder.setTitle("Alert");
                        mBuilder.setMessage("Exam mode හි අසම්පූර්ණ උත්සාහ වාරයක් තිබේ! එය ඉවත් වනු ඇත");
                        mBuilder.setPositiveButton("එකඟයි", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                sharedPreferences.edit().clear().commit();
                                startPaper();

                            }
                        });
                        mBuilder.setNegativeButton("එකඟ නොවේ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        /*mBuilder.setNeutralButton("Neutral", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });*/

                        android.support.v7.app.AlertDialog alertDialog = mBuilder.create();
                        alertDialog.show();



                    }else {
                        //sharedPreferences.edit().clear().commit();
                        startPaper();
                    }

                }else{

                    startPaper();
                }







                /*Intent intent = new Intent(getApplicationContext(), PaperActivity.class);
                intent.putExtra("paper_id",paper_id);
                intent.putExtra("paper_name",paper_name);
                intent.putExtra("paper_year",paper_year);
                intent.putExtra("question_count",question_count);
                intent.putExtra("allocated_time",allocated_time);
                intent.putExtra("paper_mode","leisure");

                Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(ExamModeActivity.this).toBundle();
                startActivity(intent,bundle);*/
            }

            private void startPaper() {
                Intent intent = new Intent(getApplicationContext(), PaperActivity.class);
                intent.putExtra("paper_id",paper_id);
                intent.putExtra("paper_name",paper_name);
                intent.putExtra("paper_year",paper_year);
                intent.putExtra("question_count",question_count);
                intent.putExtra("allocated_time",allocated_time);
                intent.putExtra("paper_mode","leisure");

                Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(ExamModeActivity.this).toBundle();
                startActivity(intent,bundle);
            }
        });

        examModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferences!=null && sharedPreferences.contains("initialized")) {

                    boolean initStatus = sharedPreferences.getBoolean("initialized", false);
                    String mode = sharedPreferences.getString("paper_mode", "none");

                    if (initStatus && mode.equals("leisure")){

                        android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(ExamModeActivity.this);
                        mBuilder.setTitle("Alert");
                        mBuilder.setMessage("Leisure mode හි අසම්පූර්ණ උත්සාහ වාරයක් තිබේ! එය ඉවත් වනු ඇත");
                        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                sharedPreferences.edit().clear().commit();
                                startPaper();

                            }
                        });
                        mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        /*mBuilder.setNeutralButton("Neutral", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });*/

                        android.support.v7.app.AlertDialog alertDialog = mBuilder.create();
                        alertDialog.show();

                        //Toast.makeText(ExamModeActivity.this, "Existing leisure mode exists! it will be erased", Toast.LENGTH_SHORT).show();

                    }else {
                        //sharedPreferences.edit().clear().commit();
                        startPaper();
                    }
                }else{

                    startPaper();
                }


            }

            private void startPaper() {
                Intent intent = new Intent(getApplicationContext(), PaperActivity.class);
                intent.putExtra("paper_id",paper_id);
                intent.putExtra("paper_name",paper_name);
                intent.putExtra("paper_year",paper_year);
                intent.putExtra("question_count",question_count);
                intent.putExtra("allocated_time",allocated_time);
                intent.putExtra("paper_mode","exam");

                Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(ExamModeActivity.this).toBundle();
                startActivity(intent,bundle);
            }
        });

    }
}
