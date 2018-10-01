package Adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ishananuranga.smarthapannu.R;

import java.util.List;

import Model.Answer;
import Model.Question;

public class QuestionsAdapter1 extends BaseAdapter{

    private Context context;
    private List<Question> questions;

    public QuestionsAdapter1(Context context, List<Question> questions) {
        this.context = context;
        this.questions = questions;
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_list_item_card, parent,false);


        TextView question_id = itemView.findViewById(R.id.text_view_question_number);;
        TextView question_content = itemView.findViewById(R.id.text_view_question);
        ImageView question_image = itemView.findViewById(R.id.image_view_quetion);
        TextView answer_1_content = itemView.findViewById(R.id.text_view_answer1);
        TextView answer_2_content = itemView.findViewById(R.id.text_view_answer2);
        TextView answer_3_content = itemView.findViewById(R.id.text_view_answer3);
        ImageView answer_1_image = itemView.findViewById(R.id.image_view_answer1);
        ImageView answer_2_image = itemView.findViewById(R.id.image_view_answer2);
        ImageView answer_3_image = itemView.findViewById(R.id.image_view_answer3);
        final RadioButton radio_button1 = itemView.findViewById(R.id.radio_button_answer1);
        final RadioButton radio_button2 = itemView.findViewById(R.id.radio_button_answer2);
        final RadioButton radio_button3 = itemView.findViewById(R.id.radio_button_answer3);


        Question question = questions.get(position);

        question_id.setText(Integer.toString(position + 1));

        if (question.getQuestionContent()!=null)
            question_content.setText(question.getQuestionContent());
        else
            question_content.setVisibility(View.GONE);

        if (question.getQuestionImage()!=null) {
            final byte[] image = question.getQuestionImage();
            question_image.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));

        }else
            question_image.setVisibility(View.GONE);

        List<Answer> answers = question.getAnswers();
        //Collections.sort(answers,Answer.AnswerNameComparator);


        if (answers.get(0).getAnswerContent()!=null)
            answer_1_content.setText(answers.get(0).getAnswerName() + ". " + answers.get(0).getAnswerContent());
        else
            answer_1_content.setText(answers.get(0).getAnswerName() + ". ");

        if (answers.get(1).getAnswerContent()!=null)
            answer_2_content.setText(answers.get(1).getAnswerName() + ". " + answers.get(1).getAnswerContent());
        else
            answer_2_content.setText(answers.get(1).getAnswerName() + ". ");

        if (answers.get(2).getAnswerContent()!=null)
            answer_3_content.setText(answers.get(2).getAnswerName() + ". " + answers.get(2).getAnswerContent());
        else
            answer_3_content.setText(answers.get(2).getAnswerName() + ". ");


        if (answers.get(0).getAnswerImage()!=null){
            final byte[] image = answers.get(0).getAnswerImage();
            answer_1_image.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
        }else
            answer_1_image.setVisibility(View.GONE);

        if (answers.get(1).getAnswerImage()!=null){
            final byte[] image = answers.get(1).getAnswerImage();
            answer_2_image.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
        }else
            answer_2_image.setVisibility(View.GONE);

        if (answers.get(2).getAnswerImage()!=null){
            final byte[] image = answers.get(2).getAnswerImage();
            answer_3_image.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
        }else
            answer_3_image.setVisibility(View.GONE);


        radio_button1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radio_button2.setChecked(false);
                    radio_button3.setChecked(false);
                }
            }
        });
        radio_button2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radio_button1.setChecked(false);
                    radio_button3.setChecked(false);
                }
            }
        });
        radio_button3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radio_button1.setChecked(false);
                    radio_button2.setChecked(false);
                }
            }
        });


        return itemView;
    }
}
