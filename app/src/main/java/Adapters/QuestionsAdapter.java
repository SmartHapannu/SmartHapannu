package Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ishananuranga.smarthapannu.R;

import java.util.Collections;
import java.util.List;

import Interfaces.CustomItemClickListener;
import Model.Answer;
import Model.Question;

import static android.content.Context.MODE_PRIVATE;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    private Context context;
    private List<Question> questions;
    private CustomItemClickListener listener;
    private String paperId;
    public static boolean resume = false;

    public QuestionsAdapter(Context context, List<Question> questions, String paperId, CustomItemClickListener listener) {
        this.context = context;
        this.questions = questions;
        this.paperId = paperId;
        this.listener = listener;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_list_item_card, parent,false);

        final ViewHolder mViewHolder = new ViewHolder(view);

        RadioButton rb1 = view.findViewById(R.id.radio_button_answer1);
        RadioButton rb2 = view.findViewById(R.id.radio_button_answer2);
        RadioButton rb3 = view.findViewById(R.id.radio_button_answer3);
        RadioButton rb4 = view.findViewById(R.id.radio_button_answer4);
        RadioButton rb5 = view.findViewById(R.id.radio_button_answer5);
        RadioButton rb6 = view.findViewById(R.id.radio_button_answer6);

        final TextView question_no = view.findViewById(R.id.text_view_question_number);

        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringQuestionNumber = question_no.getText().toString();
                listener.onItemClick(v, Integer.parseInt(stringQuestionNumber.substring(0,stringQuestionNumber.length() - 1)));
            }
        });

        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringQuestionNumber = question_no.getText().toString();
                listener.onItemClick(v, Integer.parseInt(stringQuestionNumber.substring(0,stringQuestionNumber.length() - 1)));
            }
        });

        rb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringQuestionNumber = question_no.getText().toString();
                listener.onItemClick(v, Integer.parseInt(stringQuestionNumber.toString().substring(0,stringQuestionNumber.length() - 1)));
            }
        });

        rb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringQuestionNumber = question_no.getText().toString();
                listener.onItemClick(v, Integer.parseInt(stringQuestionNumber.toString().substring(0,stringQuestionNumber.length() - 1)));
            }
        });

        rb5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringQuestionNumber = question_no.getText().toString();
                listener.onItemClick(v, Integer.parseInt(stringQuestionNumber.toString().substring(0,stringQuestionNumber.length() - 1)));
            }
        });

        rb6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringQuestionNumber = question_no.getText().toString();
                listener.onItemClick(v, Integer.parseInt(stringQuestionNumber.toString().substring(0,stringQuestionNumber.length() - 1)));
            }
        });


        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        Question question = questions.get(position);

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        );

        //holder.setIsRecyclable(false);

        holder.question_id.setText(Integer.toString(position + 1) + "&");

        if (question.getQuestionContent()!=null)
            holder.question_content.setText(question.getQuestionContent());
        else
            holder.question_content.setVisibility(View.GONE);

        if (question.getQuestionImage()!=null) {
            final byte[] image = question.getQuestionImage();
            holder.question_image.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));

        }else
            holder.question_image.setVisibility(View.GONE);

        List<Answer> answers = question.getAnswers();
        //Collections.sort(answers,Answer.AnswerNameComparator);

        /*BEGINNING OF TEXT BIND*/

        if(1 <= answers.size()){

            if (answers.get(0).getAnswerContent()!=null)
                holder.answer_1_content.setText(answers.get(0).getAnswerName() + ". " + answers.get(0).getAnswerContent());
            else {
                //holder.answer_1_content.setText(answers.get(0).getAnswerName() + ". ");
                //holder.answer_1_content.setVisibility(View.GONE);
                holder.answer_1_image.setVisibility(View.GONE);

                //((LinearLayout.LayoutParams)holder.answer_1_image.getLayoutParams()).weight = 100;

            }
        } else {

            holder.answer_1_content.setVisibility(View.GONE);
            holder.answer_1_image.setVisibility(View.GONE);

        }

        if(2 <= answers.size()) {

            if (answers.get(1).getAnswerContent()!=null) {
                holder.answer_2_content.setText(answers.get(1).getAnswerName() + ". " + answers.get(1).getAnswerContent());
            }else {
                //holder.answer_2_content.setText(answers.get(1).getAnswerName() + ". ");
                holder.answer_2_image.setVisibility(View.GONE);

                //((LinearLayout.LayoutParams)holder.answer_2_image.getLayoutParams()).weight = 100;
            }

        } else {

            holder.answer_2_content.setVisibility(View.GONE);
            holder.answer_2_image.setVisibility(View.GONE);

        }

        if(3 <= answers.size()) {

            if (answers.get(2).getAnswerContent()!=null) {
                holder.answer_3_content.setText(answers.get(2).getAnswerName() + ". " + answers.get(2).getAnswerContent());
            }else {
                //holder.answer_3_content.setText(answers.get(2).getAnswerName() + ". ");
                holder.answer_3_image.setVisibility(View.GONE);

                //((LinearLayout.LayoutParams)holder.answer_3_image.getLayoutParams()).weight = 100;
            }

        } else {

            holder.answer_3_content.setVisibility(View.GONE);
            holder.answer_3_image.setVisibility(View.GONE);

        }

        if(4 <= answers.size()) {

            if (answers.get(3).getAnswerContent()!=null) {
                holder.answer_4_content.setText(answers.get(3).getAnswerName() + ". " + answers.get(3).getAnswerContent());
            }else {
                //holder.answer_3_content.setText(answers.get(2).getAnswerName() + ". ");
                holder.answer_4_image.setVisibility(View.GONE);

                //((LinearLayout.LayoutParams)holder.answer_3_image.getLayoutParams()).weight = 100;
            }
        } else {

            holder.answer_4_content.setVisibility(View.GONE);
            holder.answer_4_image.setVisibility(View.GONE);

        }

        if(5 <= answers.size()) {

            if (answers.get(4).getAnswerContent()!=null) {
                holder.answer_5_content.setText(answers.get(4).getAnswerName() + ". " + answers.get(4).getAnswerContent());
            }else {
                //holder.answer_3_content.setText(answers.get(2).getAnswerName() + ". ");
                holder.answer_5_image.setVisibility(View.GONE);

                //((LinearLayout.LayoutParams)holder.answer_3_image.getLayoutParams()).weight = 100;
            }

        } else {

            holder.answer_5_content.setVisibility(View.GONE);
            holder.answer_5_image.setVisibility(View.GONE);

        }


        if(6 <= answers.size()) {

            if (answers.get(5).getAnswerContent()!=null) {
                holder.answer_6_content.setText(answers.get(5).getAnswerName() + ". " + answers.get(5).getAnswerContent());
            }else {
                //holder.answer_3_content.setText(answers.get(2).getAnswerName() + ". ");
                holder.answer_6_image.setVisibility(View.GONE);

                //((LinearLayout.LayoutParams)holder.answer_3_image.getLayoutParams()).weight = 100;
            }

        } else {

            holder.answer_6_content.setVisibility(View.GONE);
            holder.answer_6_image.setVisibility(View.GONE);

        }

        /*END OF TEXT BIND*/

        /*BEGINNING OF IMAGE BIND*/

        if(1 <= answers.size()){

            if (answers.get(0).getAnswerImage()!=null){
                final byte[] image = answers.get(0).getAnswerImage();
                holder.answer_1_image.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
            }else {
                holder.answer_1_image.setVisibility(View.GONE);
                //holder.answer_1_layout.getLayoutParams().height= 50;

            }
        }

        if(2 <= answers.size()){

            if (answers.get(1).getAnswerImage()!=null){
                final byte[] image = answers.get(1).getAnswerImage();
                holder.answer_2_image.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
            }else {
                holder.answer_2_image.setVisibility(View.GONE);
                //holder.answer_2_layout.getLayoutParams().height = 50;

            }
        }

        if(3 <= answers.size()){

            if (answers.get(2).getAnswerImage()!=null){
                final byte[] image = answers.get(2).getAnswerImage();
                holder.answer_3_image.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
            }else {
                holder.answer_3_image.setVisibility(View.GONE);
                //holder.answer_3_layout.getLayoutParams().height = 50;
            }
        }


        if(4 <= answers.size()){

            if (answers.get(3).getAnswerImage()!=null){
                final byte[] image = answers.get(3).getAnswerImage();
                holder.answer_4_image.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
            }else {
                holder.answer_4_image.setVisibility(View.GONE);
                //holder.answer_3_layout.getLayoutParams().height = 50;
            }
        }


        if(5 <= answers.size()){

            if (answers.get(4).getAnswerImage()!=null){
                final byte[] image = answers.get(4).getAnswerImage();
                holder.answer_5_image.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
            }else {
                holder.answer_5_image.setVisibility(View.GONE);
                //holder.answer_3_layout.getLayoutParams().height = 50;
            }
        }


        if(6 <= answers.size()){

            if (answers.get(5).getAnswerImage()!=null){
                final byte[] image = answers.get(5).getAnswerImage();
                holder.answer_6_image.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
            }else {
                holder.answer_6_image.setVisibility(View.GONE);
                //holder.answer_3_layout.getLayoutParams().height = 50;
            }
        }


        /*END OF IMAGE BIND*/


        /*DISABLE UNUSED RADIO BUTTONS*/

        if(holder.answer_4_content.getVisibility() == View.GONE && holder.answer_4_image.getVisibility() == View.GONE)
            holder.radio_button4.setVisibility(View.GONE);

        if(holder.answer_5_content.getVisibility() == View.GONE && holder.answer_5_image.getVisibility() == View.GONE)
            holder.radio_button5.setVisibility(View.GONE);

        if(holder.answer_6_content.getVisibility() == View.GONE && holder.answer_6_image.getVisibility() == View.GONE)
            holder.radio_button6.setVisibility(View.GONE);

        /*END OF DISABLE UNUSED RADIO BUTTONS*/

        holder.radio_button1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.radio_button2.setChecked(false);
                    holder.radio_button3.setChecked(false);
                    holder.radio_button4.setChecked(false);
                    holder.radio_button5.setChecked(false);
                    holder.radio_button6.setChecked(false);
                }
            }
        });

        holder.radio_button2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.radio_button1.setChecked(false);
                    holder.radio_button3.setChecked(false);
                    holder.radio_button4.setChecked(false);
                    holder.radio_button5.setChecked(false);
                    holder.radio_button6.setChecked(false);
                }
            }
        });

        holder.radio_button3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.radio_button1.setChecked(false);
                    holder.radio_button2.setChecked(false);
                    holder.radio_button4.setChecked(false);
                    holder.radio_button5.setChecked(false);
                    holder.radio_button6.setChecked(false);
                }
            }
        });

        holder.radio_button4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.radio_button1.setChecked(false);
                    holder.radio_button2.setChecked(false);
                    holder.radio_button3.setChecked(false);
                    holder.radio_button5.setChecked(false);
                    holder.radio_button6.setChecked(false);
                }
            }
        });

        holder.radio_button5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.radio_button1.setChecked(false);
                    holder.radio_button2.setChecked(false);
                    holder.radio_button3.setChecked(false);
                    holder.radio_button4.setChecked(false);
                    holder.radio_button6.setChecked(false);
                }
            }
        });

        holder.radio_button6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.radio_button1.setChecked(false);
                    holder.radio_button2.setChecked(false);
                    holder.radio_button3.setChecked(false);
                    holder.radio_button4.setChecked(false);
                    holder.radio_button5.setChecked(false);
                }
            }
        });

        if (resume){

            SharedPreferences sharedPreferences = context.getSharedPreferences("paper_progress_" + paperId, MODE_PRIVATE);
            int marked_choice = sharedPreferences.getInt(String.valueOf(position + 1), 0);

            if (marked_choice == 1){
                holder.radio_button1.setChecked(true);
            }else if(marked_choice == 2){
                holder.radio_button2.setChecked(true);
            }else if (marked_choice == 3){
                holder.radio_button3.setChecked(true);
            }else if (marked_choice == 4){
                holder.radio_button4.setChecked(true);
            }else if (marked_choice == 5){
                holder.radio_button5.setChecked(true);
            }else if (marked_choice == 6){
                holder.radio_button6.setChecked(true);
            }

        }
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView question_id;
        public TextView question_content;
        public ImageView question_image;
        public TextView answer_1_content;
        public TextView answer_2_content;
        public TextView answer_3_content;
        public TextView answer_4_content;
        public TextView answer_5_content;
        public TextView answer_6_content;
        public ImageView answer_1_image;
        public ImageView answer_2_image;
        public ImageView answer_3_image;
        public ImageView answer_4_image;
        public ImageView answer_5_image;
        public ImageView answer_6_image;
        public RadioButton radio_button1;
        public RadioButton radio_button2;
        public RadioButton radio_button3;
        public RadioButton radio_button4;
        public RadioButton radio_button5;
        public RadioButton radio_button6;
        public LinearLayout answer_1_layout;
        public LinearLayout answer_2_layout;
        public LinearLayout answer_3_layout;

        public ViewHolder(View itemView) {
            super(itemView);

            question_id = itemView.findViewById(R.id.text_view_question_number);
            question_content = itemView.findViewById(R.id.text_view_question);
            question_image = itemView.findViewById(R.id.image_view_quetion);
            answer_1_content = itemView.findViewById(R.id.text_view_answer1);
            answer_2_content = itemView.findViewById(R.id.text_view_answer2);
            answer_3_content = itemView.findViewById(R.id.text_view_answer3);
            answer_4_content = itemView.findViewById(R.id.text_view_answer4);
            answer_5_content = itemView.findViewById(R.id.text_view_answer5);
            answer_6_content = itemView.findViewById(R.id.text_view_answer6);
            answer_1_image = itemView.findViewById(R.id.image_view_answer1);
            answer_2_image = itemView.findViewById(R.id.image_view_answer2);
            answer_3_image = itemView.findViewById(R.id.image_view_answer3);
            answer_4_image = itemView.findViewById(R.id.image_view_answer4);
            answer_5_image = itemView.findViewById(R.id.image_view_answer5);
            answer_6_image = itemView.findViewById(R.id.image_view_answer6);
            radio_button1 = itemView.findViewById(R.id.radio_button_answer1);
            radio_button2 = itemView.findViewById(R.id.radio_button_answer2);
            radio_button3 = itemView.findViewById(R.id.radio_button_answer3);
            radio_button4 = itemView.findViewById(R.id.radio_button_answer4);
            radio_button5 = itemView.findViewById(R.id.radio_button_answer5);
            radio_button6 = itemView.findViewById(R.id.radio_button_answer6);
            /*answer_1_layout = itemView.findViewById(R.id.answer_1_layout);
            answer_2_layout = itemView.findViewById(R.id.answer_2_layout);
            answer_3_layout = itemView.findViewById(R.id.answer_3_layout);*/
        }
    }
}
