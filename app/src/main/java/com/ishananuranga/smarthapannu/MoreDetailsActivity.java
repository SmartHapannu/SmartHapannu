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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Adapters.CategoryWiseResultListAdapter;
import Adapters.PaperListAdapter;
import Model.CategoryWiseResult;
import Model.Paper;

public class MoreDetailsActivity extends AppCompatActivity {

    private List<CategoryWiseResult> categoryWiseResults;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private Utlis.DataAdapter mDbHelper;
    private static Cursor categoryWiseDetailsData;
    private static Cursor categoryWiseNameDetailsData;
    private TextView paperName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_details);

        Intent intent = getIntent();
        String attempt_master_id = intent.getStringExtra("attempt_master_id");
        String paper_id = intent.getStringExtra("paper_id");

        paperName = findViewById(R.id.text_view_paper_name);
        recyclerView = findViewById(R.id.cat_wise_result_list_recyclerview);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));

        categoryWiseResults = new ArrayList<>();
        loadCategoryWiseResults(attempt_master_id);

        adapter = new CategoryWiseResultListAdapter(this,categoryWiseResults);
        recyclerView.setAdapter(adapter);

        paperName.setText(loadPaperName(Integer.parseInt(paper_id)));


        getWindow().setAllowEnterTransitionOverlap(false);

    }

    private void loadCategoryWiseResults(String attempt_master_id) {
        mDbHelper = new Utlis.DataAdapter(this);
        mDbHelper.open();

        categoryWiseDetailsData = mDbHelper.getCategoryWiseResults(Integer.parseInt(attempt_master_id));

        categoryWiseDetailsData.moveToFirst();

        do {
            int Category_Id = categoryWiseDetailsData.getInt(categoryWiseDetailsData.getColumnIndex("Category_Id"));
            int Sub_Category_Id = categoryWiseDetailsData.getInt(categoryWiseDetailsData.getColumnIndex("Sub_Category_Id"));
            int Correct_Count = categoryWiseDetailsData.getInt(categoryWiseDetailsData.getColumnIndex("Correct_Count"));
            int Full_Count = categoryWiseDetailsData.getInt(categoryWiseDetailsData.getColumnIndex("Full_Count"));
            int Unanswered_Count = categoryWiseDetailsData.getInt(categoryWiseDetailsData.getColumnIndex("Unanswered_Count"));
            int Incorrect_Count = Full_Count - (Unanswered_Count + Correct_Count);

            CategoryWiseResult categoryWiseResult = new CategoryWiseResult(Category_Id, Sub_Category_Id, Correct_Count, Incorrect_Count, Unanswered_Count, Full_Count);

            categoryWiseResults.add(categoryWiseResult);
        }while (categoryWiseDetailsData.moveToNext());

        mDbHelper.close();
    }

    private String loadPaperName(int paper_id){

        mDbHelper = new Utlis.DataAdapter(this);
        mDbHelper.open();

        categoryWiseDetailsData = mDbHelper.getPaperName(paper_id);

        categoryWiseDetailsData.moveToFirst();

        String paper_name = categoryWiseDetailsData.getString(categoryWiseDetailsData.getColumnIndex("Name"));

        return paper_name;

    }



}
