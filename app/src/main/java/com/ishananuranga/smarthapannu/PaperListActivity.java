package com.ishananuranga.smarthapannu;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Adapters.PaperListAdapter;
import Interfaces.PaperListClickListener;
import Model.Paper;
import Model.PaperProvider;

public class PaperListActivity extends AppCompatActivity implements PaperListClickListener {

    private List<Paper> papers ;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private Utlis.DataAdapter mDbHelper;
    private static Cursor papersData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_list);

        Intent intent = getIntent();
        String provider_id = intent.getStringExtra("provider_id");

        recyclerView = findViewById(R.id.paper_list_recyclerview);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));

        papers = new ArrayList<>();
        loadPapers(provider_id);

        adapter = new PaperListAdapter(this,papers);
        recyclerView.setAdapter(adapter);


        getWindow().setAllowEnterTransitionOverlap(false);

        Slide slide = new Slide(Gravity.RIGHT);
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        getWindow().setReturnTransition(slide);
    }

    private void loadPapers(String provider_id) {
        mDbHelper = new Utlis.DataAdapter(this);
        mDbHelper.open();

        papersData = mDbHelper.getPapersData(Integer.parseInt(provider_id));

        papersData.moveToFirst();

        do {
            int paper_id = papersData.getInt(papersData.getColumnIndex("Paper_Id"));
            String paper_name = papersData.getString(papersData.getColumnIndex("Name"));
            int paper_year = papersData.getInt(papersData.getColumnIndex("Year"));
            int paper_allocated_time = papersData.getInt(papersData.getColumnIndex("Allocated_Time"));
            int paper_attempts = papersData.getInt(papersData.getColumnIndex("Attempts"));
            int paper_question_count = 36;//papersData.getInt(papersData.getColumnIndex("Question_Count"));

            Paper paper = new Paper(Integer.toString(paper_id),paper_name,Integer.toString(paper_year),Integer.toString(paper_question_count),Integer.toString(paper_attempts),Integer.toString(paper_allocated_time));
            papers.add(paper);
        }while (papersData.moveToNext());

        mDbHelper.close();
    }


    @Override
    public void changeActivity(String paper_id, String paper_name, String paper_year, String question_count, String allocated_time) {
        Intent intent = new Intent(this, ExamModeActivity.class);
        intent.putExtra("paper_id",paper_id);
        intent.putExtra("paper_name",paper_name);
        intent.putExtra("paper_year",paper_year);
        intent.putExtra("question_count",question_count);
        intent.putExtra("allocated_time",allocated_time);
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(intent,bundle);
    }
}
