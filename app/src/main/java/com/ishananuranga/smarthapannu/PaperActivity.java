package com.ishananuranga.smarthapannu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import Adapters.QuestionsAdapter;
import Interfaces.CustomItemClickListener;
import Model.Answer;
import Model.CategoryWiseResult;
import Model.CategorywiseIterator;
import Model.Question;

public class
PaperActivity extends AppCompatActivity {

    private List<Question> questions;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private Utlis.DataAdapter mDbHelper;
    private static Cursor questionsData;
    private static Cursor answersData;
    private static Cursor attemptData;
    private Map<String,Integer> lessons;
    private Map<String,Integer> subLessons;

    private TextView paperName;
    private TextView paperYear;
    private TextView questionCount;
    private TextView allocatedTime;

    private TextView remainingTime;

    private Button submit_button;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String MODE;

    private List<CategoryWiseResult> categoryWiseResults = new ArrayList<CategoryWiseResult>();
    //private CategoryWiseResult[] categoryWiseResults;

    private String paper_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper);

        remainingTime = findViewById(R.id.text_view_remaining_time);



        Intent intent = getIntent();
        paper_id = intent.getStringExtra("paper_id");
        String paper_name = intent.getStringExtra("paper_name");
        String paper_year = intent.getStringExtra("paper_year");
        String question_count = intent.getStringExtra("question_count");
        String allocated_time = intent.getStringExtra("allocated_time");
        final String paper_mode = intent.getStringExtra("paper_mode");

        new CountDownTimer(2460000, 1000) {

            public void onTick(long millisUntilFinished) {
                int minutes = (int) (millisUntilFinished / 60000);
                remainingTime.setText("b;sß ld,h úkdä " + minutes );

            }

            public void onFinish() {
                if (paper_mode.equals("exam")){
                    submitPaper(paper_id);
                    remainingTime.setText("ld,h wjidkhs!");
                }
            }

        }.start();

        paperName = findViewById(R.id.text_view_paper_paper_name);
        paperYear = findViewById(R.id.text_view_paper_paper_year);
        questionCount = findViewById(R.id.text_view_paper_questions_count);
        allocatedTime = findViewById(R.id.text_view_paper_allocated_time);
        MODE = paper_mode;

        paperName.setText(paper_name);
        paperYear.setText(paper_year);
        questionCount.setText("m%Yak .Kk " + question_count);
        allocatedTime.setText("ld,h úkdä " + allocated_time);

        recyclerView = findViewById(R.id.recyclerview_questions);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));

        sharedPreferences = getSharedPreferences("paper_progress_" + paper_id,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        checkPaperInitializationStatus();
        setPaperMode();

        questions = new ArrayList<>();
        loadQuestions(paper_id);
        loadCategoryWiseInfo(questions);


        if (MODE.toLowerCase().equals("exam"))
            remainingTime.setTextColor(getResources().getColor(R.color.exam_mode_timer_color));
            remainingTime.setTextSize(18);



        adapter = new QuestionsAdapter(this, questions, paper_id, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                questions.get(position - 1).getAnswers();

                RadioButton rb1 = v.findViewById(R.id.radio_button_answer1);
                RadioButton rb2 = v.findViewById(R.id.radio_button_answer2);
                RadioButton rb3 = v.findViewById(R.id.radio_button_answer3);
                RadioButton rb4 = v.findViewById(R.id.radio_button_answer3);
                RadioButton rb5 = v.findViewById(R.id.radio_button_answer3);
                RadioButton rb6 = v.findViewById(R.id.radio_button_answer3);

                Boolean answer1_is_true = questions.get(position -1).getAnswers().get(0).getIsCorrect() == 1;
                Boolean answer2_is_true = questions.get(position -1).getAnswers().get(1).getIsCorrect() == 1;
                Boolean answer3_is_true = questions.get(position -1).getAnswers().get(2).getIsCorrect() == 1;

                if (rb1!=null && rb1.isChecked())
                    editor.putInt(String.valueOf(position), 1);
                else if (rb2!=null && rb2.isChecked())
                    editor.putInt(String.valueOf(position), 2);
                else if (rb3!=null && rb3.isChecked())
                    editor.putInt(String.valueOf(position), 3);
                else if (rb4!=null && rb4.isChecked())
                    editor.putInt(String.valueOf(position), 4);
                else if (rb5!=null && rb5.isChecked())
                    editor.putInt(String.valueOf(position), 5);
                else if (rb6!=null && rb6.isChecked())
                    editor.putInt(String.valueOf(position), 6);

                /*if (rb1!=null && rb1.isChecked() && answer1_is_true)
                    editor.putInt(String.valueOf(position), 1);
                else if (rb2!=null && rb2.isChecked() && answer2_is_true)
                    editor.putInt(String.valueOf(position), 2);
                else if (rb3!=null && rb3.isChecked() && answer3_is_true)
                    editor.putInt(String.valueOf(position), 3);*/

                editor.commit();

            }
        }) ; //new QuestionsAdapter(this,questions);

        recyclerView.setAdapter(adapter);
        //recyclerView.getRecycledViewPool().setMaxRecycledViews(, 0);
        //recyclerView.getRecycledViewPool().setMaxRecycledViews();

        Slide slide = new Slide(Gravity.LEFT);
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        getWindow().setExitTransition(slide);


        submit_button = findViewById(R.id.submit_button);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitPaper(paper_id);

            }
        });



        }

    private void submitPaper(String paper_id) {
        int correct_count = 0;
        int unanswered_count = questions.size();
        int all_count = questions.size();

        lessons = new HashMap<>();
        subLessons = new HashMap<>();


        //String[] unique = Arrays.stream().distinct().toArray(String[]::new);

        Map<String,?> keys = sharedPreferences.getAll();

        for(Map.Entry<String,?> entry : keys.entrySet()){

            if (!entry.getKey().equals("initialized") && !entry.getKey().equals("paper_mode")){

                String category = questions.get(Integer.parseInt(entry.getKey()) - 1).getLessonId();
                String subCategory = questions.get(Integer.parseInt(entry.getKey()) - 1).getSubLessonId();

                Question question  = questions.get(Integer.parseInt(entry.getKey()) - 1);

                /*Boolean answer1_is_true = questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().size() >= 1? questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().get(0).getIsCorrect() == 1 : false;
                Boolean answer2_is_true = questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().size() >= 2? questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().get(1).getIsCorrect() == 1 : false;
                Boolean answer3_is_true = questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().size() >= 3? questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().get(2).getIsCorrect() == 1 : false;
                Boolean answer4_is_true = questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().size() >= 4? questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().get(3).getIsCorrect() == 1 : false;
                Boolean answer5_is_true = questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().size() >= 5? questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().get(4).getIsCorrect() == 1 : false;
                Boolean answer6_is_true = questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().size() >= 6? questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().get(5).getIsCorrect() == 1 : false;*/

                Boolean answer1_is_true = false;
                Boolean answer2_is_true = false;
                Boolean answer3_is_true = false;
                Boolean answer4_is_true = false;
                Boolean answer5_is_true = false;
                Boolean answer6_is_true = false;


                if(questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().size() == 1){

                    answer1_is_true = questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().get(0).getIsCorrect() == 1;

                }else if(questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().size() == 2){

                    answer1_is_true = questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().get(0).getIsCorrect() == 1;
                    answer2_is_true = questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().get(1).getIsCorrect() == 1;

                }else if(questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().size() == 3){

                    answer1_is_true = questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().get(0).getIsCorrect() == 1;
                    answer2_is_true = questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().get(1).getIsCorrect() == 1;
                    answer3_is_true = questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().get(2).getIsCorrect() == 1;


                }else if(questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().size() == 4){

                    answer1_is_true = questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().get(0).getIsCorrect() == 1;
                    answer2_is_true = questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().get(1).getIsCorrect() == 1;
                    answer3_is_true = questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().get(2).getIsCorrect() == 1;
                    answer4_is_true = questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().get(3).getIsCorrect() == 1;

                }else if(questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().size() == 5){

                    answer1_is_true = questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().get(0).getIsCorrect() == 1;
                    answer2_is_true = questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().get(1).getIsCorrect() == 1;
                    answer3_is_true = questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().get(2).getIsCorrect() == 1;
                    answer4_is_true = questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().get(3).getIsCorrect() == 1;
                    answer5_is_true = questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().get(4).getIsCorrect() == 1;


                }else if(questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().size() == 6){

                    answer1_is_true = questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().get(0).getIsCorrect() == 1;
                    answer2_is_true = questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().get(1).getIsCorrect() == 1;
                    answer3_is_true = questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().get(2).getIsCorrect() == 1;
                    answer4_is_true = questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().get(3).getIsCorrect() == 1;
                    answer5_is_true = questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().get(4).getIsCorrect() == 1;
                    answer6_is_true = questions.get(Integer.parseInt(entry.getKey()) - 1).getAnswers().get(5).getIsCorrect() == 1;

                }


                if (entry.getValue().toString().equals("1") && answer1_is_true) {

                    /*if (lessons.get(category) == null)
                        lessons.put(category,  1);
                    else
                        lessons.put(category, lessons.get(category) + 1);

                    if (subLessons.get(subCategory) == null)
                        subLessons.put(subCategory, 1);
                    else
                        subLessons.put(subCategory, subLessons.get(subCategory) + 1);*/

                    //lessons.put(category, lessons.get(category) + 1);
                    //subLessons.put(subCategory, lessons.get(subCategory) + 1);
                    addToCorrectCategoryWiseCount(question);
                    correct_count++;

                }else if (entry.getValue().toString().equals("2") && answer2_is_true) {

                    /*if (lessons.get(category) == null)
                        lessons.put(category,  1);
                    else
                        lessons.put(category, lessons.get(category) + 1);

                    if (subLessons.get(subCategory) == null)
                        subLessons.put(subCategory, 1);
                    else
                        subLessons.put(subCategory, subLessons.get(subCategory) + 1);*/

                    //subLessons.put(subCategory, lessons.get(subCategory) + 1);
                    addToCorrectCategoryWiseCount(question);
                    correct_count++;

                }else if (entry.getValue().toString().equals("3") && answer3_is_true){

                    /*if (lessons.get(category) == null)
                        lessons.put(category,  1);
                    else
                        lessons.put(category, lessons.get(category) + 1);

                    if (subLessons.get(subCategory) == null)
                        subLessons.put(subCategory, 1);
                    else
                        subLessons.put(subCategory, subLessons.get(subCategory) + 1);*/

                    //subLessons.put(subCategory, lessons.get(subCategory) + 1);
                    addToCorrectCategoryWiseCount(question);
                    correct_count++;

                }else if (entry.getValue().toString().equals("4") && answer4_is_true){

                    /*if (lessons.get(category) == null)
                        lessons.put(category,  1);
                    else
                        lessons.put(category, lessons.get(category) + 1);

                    if (subLessons.get(subCategory) == null)
                        subLessons.put(subCategory, 1);
                    else
                        subLessons.put(subCategory, subLessons.get(subCategory) + 1);*/

                    //subLessons.put(subCategory, lessons.get(subCategory) + 1);
                    addToCorrectCategoryWiseCount(question);
                    correct_count++;

                }else if (entry.getValue().toString().equals("5") && answer5_is_true){

                    /*if (lessons.get(category) == null)
                        lessons.put(category,  1);
                    else
                        lessons.put(category, lessons.get(category) + 1);

                    if (subLessons.get(subCategory) == null)
                        subLessons.put(subCategory, 1);
                    else
                        subLessons.put(subCategory, subLessons.get(subCategory) + 1);*/

                    //subLessons.put(subCategory, lessons.get(subCategory) + 1);
                    addToCorrectCategoryWiseCount(question);
                    correct_count++;

                }else if (entry.getValue().toString().equals("6") && answer6_is_true){

                    /*if (lessons.get(category) == null)
                        lessons.put(category,  1);
                    else
                        lessons.put(category, lessons.get(category) + 1);

                    if (subLessons.get(subCategory) == null)
                        subLessons.put(subCategory, 1);
                    else
                        subLessons.put(subCategory, subLessons.get(subCategory) + 1);*/

                    //subLessons.put(subCategory, lessons.get(subCategory) + 1);
                    addToCorrectCategoryWiseCount(question);
                    correct_count++;

                }else{

                    /*if (lessons.get(category) == null)
                        lessons.put(category,  1);
                    else
                        lessons.put(category, lessons.get(category) + 1);

                    if (subLessons.get(subCategory) == null)
                        subLessons.put(subCategory, 1);
                    else
                        subLessons.put(subCategory, subLessons.get(subCategory) + 1);*/

                    addToIncorrectCategoryWiseCount(question);
                    //subLessons.put(subCategory, lessons.get(subCategory) + 1);
                }

                unanswered_count--;
            }

        }

        mDbHelper = new Utlis.DataAdapter(getApplicationContext());
        mDbHelper.open();

        int attemptNumber =  getNextAttemptNumber(Integer.parseInt(paper_id));
        int savedMasterId = saveAttemptDetails(++attemptNumber, correct_count, unanswered_count, all_count, MODE, Integer.parseInt(paper_id));
        deductAttemptCount(Integer.parseInt(paper_id));

        saveAttemptCategoryDetails(savedMasterId);




                /*for (int childCount = recyclerView.getChildCount(), i = 0; i < childCount; ++i) {
                    final RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(recyclerView.getChildAt(i));

                    questions.get(i).getAnswers();

                    View view = recyclerView.getChildAt(i);
                    RadioButton radio1 = view.findViewById(R.id.radio_button_answer1);
                    RadioButton radio2 = view.findViewById(R.id.radio_button_answer2);
                    RadioButton radio3 = view.findViewById(R.id.radio_button_answer3);

                    Boolean answer1_is_true = questions.get(i).getAnswers().get(0).getIsCorrect() == 1;
                    Boolean answer2_is_true = questions.get(i).getAnswers().get(1).getIsCorrect() == 1;
                    Boolean answer3_is_true = questions.get(i).getAnswers().get(2).getIsCorrect() == 1;

                    if (radio1.isChecked() && answer1_is_true)
                        correct_count++;
                    else if (radio2.isChecked() && answer2_is_true)
                        correct_count++;
                    else if (radio3.isChecked() && answer3_is_true) correct_count++;

                }*/


        editor.clear();
        editor.commit();
        Intent intent = new Intent(getApplication(), ResultsActivity.class);
        intent.putExtra("correct_count",Integer.toString(correct_count) );
        intent.putExtra("incorrect_count",Integer.toString(all_count - (correct_count +unanswered_count) ) );
        intent.putExtra("unanswered_count",Integer.toString(unanswered_count) );
        intent.putExtra("all_count", Integer.toString(all_count));
        intent.putExtra("paper_id", paper_id);

        intent.putExtra("attempts_master_id", Integer.toString(savedMasterId) );

        intent.putExtra("fraction_correct_count", Integer.toString( categoryWiseResults.get(0).getCorrectCount()));
        intent.putExtra("addition_correct_count", Integer.toString( categoryWiseResults.get(1).getCorrectCount() ));
        intent.putExtra("subtract_correct_count", Integer.toString( categoryWiseResults.get(2).getCorrectCount() ));
        intent.putExtra("divide_correct_count", Integer.toString( categoryWiseResults.get(3).getCorrectCount() ));
        intent.putExtra("fraction_total_count", Integer.toString( categoryWiseResults.get(0).getFullCount()));
        intent.putExtra("addition_total_count", Integer.toString( categoryWiseResults.get(1).getFullCount()));
        intent.putExtra("subtract_total_count", Integer.toString( categoryWiseResults.get(2).getFullCount()));
        intent.putExtra("divide_total_count", Integer.toString( categoryWiseResults.get(3).getFullCount()));

        float baseMark = ((float) correct_count/(float) all_count);
        int mark =  Math.round(((float) correct_count/(float) all_count)   * 100);

        intent.putExtra("mark", Integer.toString(mark));

        startActivity(intent);
        finish();
    }

    private void addToCorrectCategoryWiseCount(Question question) {
        for (CategoryWiseResult categoryWiseResult: categoryWiseResults) {
            if (categoryWiseResult.getCategoryId() == Integer.parseInt(question.getLessonId()) && categoryWiseResult.getSubCategoryId() == Integer.parseInt(question.getSubLessonId())){
                categoryWiseResult.setCorrectCount(categoryWiseResult.getCorrectCount() + 1);
            }
        }
    }

    private void addToIncorrectCategoryWiseCount(Question question) {
        for (CategoryWiseResult categoryWiseResult: categoryWiseResults) {
            if (categoryWiseResult.getCategoryId() == Integer.parseInt(question.getLessonId()) && categoryWiseResult.getSubCategoryId() == Integer.parseInt(question.getSubLessonId())){
                categoryWiseResult.setIncorrectCount(categoryWiseResult.getIncorrectCount() + 1);
            }
        }
    }


    private void loadCategoryWiseInfo(List<Question> questions) {

        CategorywiseIterator categorywiseIterator = new CategorywiseIterator(categoryWiseResults);

        for (Question question: questions) {

            if(categorywiseIterator.getCategorywiseList().size() != 0){
                boolean flag =false;
                CategoryWiseResult tempCategoryWiseResult = null;


                for(Iterator<CategoryWiseResult> it = categorywiseIterator.iterator(); it.hasNext(); ) {
                    CategoryWiseResult categoryWiseResult = it.next();

                    int categoryId = categoryWiseResult.getCategoryId();
                    int subCategoryId = categoryWiseResult.getSubCategoryId();

                    int questionCategoryId = Integer.parseInt(question.getLessonId());
                    int questionSubCategoryId = Integer.parseInt(question.getSubLessonId());

                    if(categoryId == questionCategoryId && subCategoryId == questionSubCategoryId){
                        categoryWiseResult.setFullCount(categoryWiseResult.getFullCount() + 1);
                        flag = true;
                        break;
                    }

                    tempCategoryWiseResult = new CategoryWiseResult(questionCategoryId,questionSubCategoryId,0,0,0,1);

                }

                if(!flag){
                    categoryWiseResults.add(tempCategoryWiseResult);
                    categorywiseIterator.setCategorywiseList(categoryWiseResults);
                    flag = false;
                }

            }else{
                categoryWiseResults.add(new CategoryWiseResult(Integer.parseInt(question.getLessonId()) ,Integer.parseInt(question.getSubLessonId()),0,0,0, 1));
                categorywiseIterator.setCategorywiseList(categoryWiseResults);
            }
        }
    }

    private void deductAttemptCount(int paperId) {
        mDbHelper = new Utlis.DataAdapter(this);
        mDbHelper.open();

        mDbHelper.deductAttempts(paperId);

    }

    private int saveAttemptDetails(int attemptNumber, int correct_count, int unanswered_count, int all_count, String paperMode, int paper_id) {

        float marks = (float) (correct_count * 100 /all_count) ;

        mDbHelper = new Utlis.DataAdapter(this);
        mDbHelper.open();

        int  attemptsMasterId = mDbHelper.saveAttempt(attemptNumber,correct_count,unanswered_count,all_count,marks, paperMode, paper_id);

        return attemptsMasterId;
    }

    private void saveAttemptCategoryDetails(int attemptsMasterId) {

        mDbHelper = new Utlis.DataAdapter(this);
        mDbHelper.open();
        for (CategoryWiseResult categoryWiseResult: categoryWiseResults) {
            if (categoryWiseResult.getFullCount() != categoryWiseResult.getCorrectCount() + categoryWiseResult.getIncorrectCount()){
                categoryWiseResult.setUnansweredCount(categoryWiseResult.getFullCount() - (categoryWiseResult.getCorrectCount() + categoryWiseResult.getIncorrectCount()) );
            }

            int categoryAttemptId = mDbHelper.saveCategoryAttempt(categoryWiseResult.getCategoryId(), categoryWiseResult.getSubCategoryId(),categoryWiseResult.getCorrectCount(), categoryWiseResult.getUnansweredCount(), categoryWiseResult.getFullCount(), attemptsMasterId);
        }



    }



    private int getNextAttemptNumber(int paperId) {

        mDbHelper = new Utlis.DataAdapter(this);
        mDbHelper.open();

        attemptData = mDbHelper.getNextAttemptNumber(paperId);

        attemptData.moveToFirst();

        int currentMaxAttempts = attemptData.getInt(attemptData.getColumnIndex("count"));


        mDbHelper.close();

        return currentMaxAttempts++;
    }



    private void setPaperMode() {
        editor = sharedPreferences.edit();
        editor.putString("paper_mode", MODE);
        editor.commit();
    }

    private void checkPaperInitializationStatus() {

        if(!sharedPreferences.contains("initialized")){
            editor = sharedPreferences.edit();

            //Indicate that the default shared prefs have been set
            editor.putBoolean("initialized", true);
            editor.putString("paper_mode", MODE);

            editor.commit();
        }else{
            boolean fa = sharedPreferences.getBoolean("initialized",false);
            if (!sharedPreferences.getBoolean("initialized",false)){
                editor = sharedPreferences.edit();

                //Indicate that the default shared prefs have been set
                editor.putBoolean("initialized", true);
                editor.putString("paper_mode", MODE);
                editor.commit();
            }


           QuestionsAdapter.resume = true;

        }
    }

    private void loadQuestions(String paper_id) {

        mDbHelper = new Utlis.DataAdapter(this);
        mDbHelper.open();

        questionsData = mDbHelper.getQuestionData(Integer.parseInt(paper_id));

        questionsData.moveToFirst();

        do {
            int question_id = questionsData.getInt(questionsData.getColumnIndex("Question_Id"));
            String question_content = questionsData.getString(questionsData.getColumnIndex("Question_Content"));
            byte[] question_image = questionsData.getBlob(questionsData.getColumnIndex("Question_Image"));
            int lesson_id = questionsData.getInt(questionsData.getColumnIndex("Lesson_Id"));
            int subLessonId = questionsData.getInt(questionsData.getColumnIndex("Sub_Lesson_Id"));

            List<Answer> answers = new ArrayList<>();

            answersData = mDbHelper.getAnswerData(question_id);

            answersData.moveToFirst();

            do {
                int answer_id = answersData.getInt(answersData.getColumnIndex("Answer_Id"));
                String answer_name = answersData.getString(answersData.getColumnIndex("Answer_Name"));
                String answer_content = answersData.getString(answersData.getColumnIndex("Answer_Content"));
                byte[] answer_image = answersData.getBlob(answersData.getColumnIndex("Answer_Image"));
                int is_correct = answersData.getInt(answersData.getColumnIndex("Is_Correct"));

                Answer answer = new Answer(Integer.toString(answer_id),answer_name,answer_content,answer_image,is_correct,Integer.toString(question_id));
                answers.add(answer);
            }while (answersData.moveToNext());

            Question question = new Question(Integer.toString(question_id),question_content,question_image,paper_id,Integer.toString(lesson_id),Integer.toString(subLessonId),answers);
            questions.add(question);
        }while (questionsData.moveToNext());

        mDbHelper.close();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
