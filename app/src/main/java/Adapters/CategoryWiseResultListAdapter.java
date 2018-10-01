package Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ishananuranga.smarthapannu.PaperActivity;
import com.ishananuranga.smarthapannu.R;

import org.w3c.dom.Text;

import java.util.List;

import Interfaces.PaperListClickListener;
import Model.CategoryWiseResult;
import Model.Paper;



public class CategoryWiseResultListAdapter extends RecyclerView.Adapter<CategoryWiseResultListAdapter.ViewHolder> {

    private Context context;
    private List<CategoryWiseResult> categoryWiseResults;
    private Utlis.DataAdapter mDbHelper;
    private static Cursor categoryWiseNameDetailsData;

    public CategoryWiseResultListAdapter(Context context, List<CategoryWiseResult> categoryWiseResults) {
        this.context = context;
        this.categoryWiseResults = categoryWiseResults;
    }

    @NonNull
    @Override
    public CategoryWiseResultListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.more_details_item_card, parent,false);

        return new CategoryWiseResultListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryWiseResultListAdapter.ViewHolder holder, int position) {

        final CategoryWiseResult categoryWiseResult = categoryWiseResults.get(position);

        String sub_category_name = loadSubCategoryNames(categoryWiseResult.getSubCategoryId());

        holder.text_view_sub_category_name.setText(sub_category_name);
        holder.text_view_sub_category_correct_count.setText(Integer.toString(categoryWiseResult.getCorrectCount()));
        holder.text_view_sub_category_incorrect_count.setText(Integer.toString(categoryWiseResult.getIncorrectCount()));
        holder.text_view_sub_category_total_count.setText(Integer.toString(categoryWiseResult.getFullCount()));

        float percentage = ((float)categoryWiseResult.getCorrectCount()/(float)categoryWiseResult.getFullCount()) * (float) 100;

        holder.text_view_sub_category_correct_percentage.setText((Integer.toString(Math.round(percentage))) + "]");
    }



    @Override
    public int getItemCount() {
        return categoryWiseResults.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView text_view_sub_category_name;
        public TextView text_view_sub_category_correct_count;
        public TextView text_view_sub_category_incorrect_count;
        public TextView text_view_sub_category_total_count;
        public TextView text_view_sub_category_correct_percentage;

        public ViewHolder(View itemView) {
            super(itemView);

            text_view_sub_category_name = itemView.findViewById(R.id.text_view_sub_category_name);
            text_view_sub_category_correct_count = itemView.findViewById(R.id.text_view_sub_category_correct_count);
            text_view_sub_category_incorrect_count = itemView.findViewById(R.id.text_view_sub_category_incorrect_count);
            text_view_sub_category_total_count = itemView.findViewById(R.id.text_view_sub_category_total_count);
            text_view_sub_category_correct_percentage = itemView.findViewById(R.id.text_view_sub_category_correct_percentage);
        }
    }


    private String loadSubCategoryNames(int sub_category_id) {
        mDbHelper = new Utlis.DataAdapter(context);
        mDbHelper.open();

        categoryWiseNameDetailsData = mDbHelper.getSubCategoryName(sub_category_id);
        categoryWiseNameDetailsData.moveToFirst();

        String Sub_Category_Name = categoryWiseNameDetailsData.getString(categoryWiseNameDetailsData.getColumnIndex("Sub_Lesson_Name"));

        mDbHelper.close();

        return Sub_Category_Name;
    }


}
