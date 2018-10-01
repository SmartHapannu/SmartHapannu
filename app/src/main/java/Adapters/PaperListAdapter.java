package Adapters;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
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
import Model.Paper;

public class PaperListAdapter extends RecyclerView.Adapter<PaperListAdapter.ViewHolder> {

    private Context context;
    private List<Paper> papers;

    public PaperListAdapter(Context context, List<Paper> papers) {
        this.context = context;
        this.papers = papers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.paper_list_item_card, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Paper paper = papers.get(position);

        holder.paper_id.setText(paper.getPaperId());
        holder.paper_name.setText(paper.getPaperName());
        holder.paper_year.setText(paper.getPaperYear());
        holder.paper_questions_count.setText("m%Yak .Kk " + paper.getPaperQuestionCount());
        holder.paper_allocated_time.setText("ld,h úkdä " + paper.getPaperAllocatedTime());
        //holder.paper_attempts.setText("jdr .Kk " + paper.getPaperAttempts());
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView paper_id_text_view = v.findViewById(R.id.text_view_paper_id);
                String paper_id = paper_id_text_view.getText().toString();

                PaperListClickListener listener = (PaperListClickListener) context;
                listener.changeActivity(paper_id, paper.getPaperName(), paper.getPaperYear(), paper.getPaperQuestionCount(), paper.getPaperAllocatedTime());
            }
        });
    }



    @Override
    public int getItemCount() {
        return papers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView paper_id;
        public TextView paper_name;
        public TextView paper_year;
        public TextView paper_questions_count;
        public TextView paper_allocated_time;
        public TextView paper_attempts;
        public CardView card_view;

        public ViewHolder(View itemView) {
            super(itemView);

            paper_id = itemView.findViewById(R.id.text_view_paper_id);
            paper_name = itemView.findViewById(R.id.text_view_paper_name);
            paper_year = itemView.findViewById(R.id.text_view_paper_year);
            paper_questions_count = itemView.findViewById(R.id.text_view_questions_count);
            paper_allocated_time = itemView.findViewById(R.id.text_view_allocated_time);
            paper_attempts = itemView.findViewById(R.id.text_view_attempts);
            card_view = itemView.findViewById(R.id.card_view_paper_item);
        }
    }
}
