package Fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ishananuranga.smarthapannu.R;

import java.util.ArrayList;
import java.util.List;

import Model.Paper;
import Utlis.DataAdapter;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ProgressFragment extends Fragment {


    private Utlis.DataAdapter mDbHelper;
    private static Cursor attemptData;
    private List list;
    private List<Paper> paperList;
    private Spinner yearSpinner;
    private static Cursor attemptedPaperData;
    private static Cursor PaperData;
    private Spinner paperYearSpinner;
    private Spinner attemptSpinner;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String mode;

    private TextView txtTotMarks;
    private TextView tvTotQuestion;
    private TextView txtAnsQuestions;
    private TextView txtUnAnsQuestions;

    private Cursor PaperAttemptsData;

    private Button btnGetProgress;
    private int paperId;




    public ProgressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);

        radioGroup = view.findViewById(R.id.rgMode);
        checkRadioButtonMode(view);

        txtTotMarks = view.findViewById(R.id.txtTotMarks);
        tvTotQuestion = view.findViewById(R.id.tvTotQuestion);
        txtAnsQuestions = view.findViewById(R.id.txtAnsQuestions);
        txtUnAnsQuestions = view.findViewById(R.id.txtUnansQuestions);

        txtTotMarks.setText("0");
        tvTotQuestion.setText("0");
        txtAnsQuestions.setText("0");
        txtUnAnsQuestions.setText("0");

        loadPaperYears(view, mode);

        btnGetProgress = view.findViewById(R.id.btnGetProgress);
        btnGetProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    mDbHelper = new DataAdapter(getContext());
                    mDbHelper.open();

                    int attemptNumber = Integer.parseInt(attemptSpinner.getSelectedItem().toString());



                    PaperAttemptsData = mDbHelper.getAttemptedMaster(mode, 11, attemptNumber);

                    //PaperAttemptsData.moveToFirst();

                    int correct_count;
                    int unanswered_count;
                    int full_count;
                    double marks;
                    int intMarks;


                    do {
                        correct_count = PaperAttemptsData.getInt(PaperAttemptsData.getColumnIndex("Correct_Count"));
                        unanswered_count = PaperAttemptsData.getInt(PaperAttemptsData.getColumnIndex("Unanswered_Count"));
                        full_count = PaperAttemptsData.getInt(PaperAttemptsData.getColumnIndex("Full_Count"));
                        marks = PaperAttemptsData.getDouble(PaperAttemptsData.getColumnIndex("Marks"));

                        intMarks = (int) Math.round(marks);




                    }while (PaperAttemptsData.moveToNext());

                    int fds = (int) Math.round(intMarks);// .toString(marks);
                    String dga = Integer.toString(fds);

                    String correctCount = Integer.toString(correct_count);
                    String unansweredCount = Integer.toString(unanswered_count);
                    String fullCount = Integer.toString(full_count);
                    int answeredAmount = full_count - unanswered_count;
                    String answeredCount = Integer.toString(answeredAmount);

                    txtTotMarks.setText(dga);
                    tvTotQuestion.setText(fullCount);
                    txtAnsQuestions.setText(answeredCount);
                    txtUnAnsQuestions.setText(unansweredCount);

                    mDbHelper.close();

                }catch (Exception ex){
                    Toast.makeText(getActivity(), "දත්ත නොමැත", Toast.LENGTH_SHORT).show();
                }




            }
        });

        return view;
    }

    private void checkRadioButtonMode(View view) {

        RadioButton rbl = view.findViewById(R.id.rbLeisure);
        RadioButton rbm = view.findViewById(R.id.rbExam);

        if (rbl.isChecked()){
            mode = "leisure";
        }else{
            mode = "exam";
        }

    }

    private void loadPaperYears(View view, String mode) {
        mDbHelper = new DataAdapter(getContext());
        mDbHelper.open();

        attemptedPaperData = mDbHelper.getAttemtedPapers(mode);


        //attemptedPaperData.moveToFirst();
        //PaperData.moveToFirst();

        paperYearSpinner = view.findViewById(R.id.spYear);
        attemptSpinner = view.findViewById(R.id.spAttempt);
        ArrayAdapter<String> adapter;
        ArrayList<String> paperIds = new ArrayList<>();
        ArrayList<String> paperYears = new ArrayList<>();

        if (attemptedPaperData.getCount() == 0) {

        } else {

            try {

                attemptedPaperData = mDbHelper.getAttemtedPapers(mode);

                do {
                    int paper_id = attemptedPaperData.getInt(attemptedPaperData.getColumnIndex("Paper_Id"));
                    if (!paperIds.contains(String.valueOf(paper_id))) {
                        paperIds.add(String.valueOf(paper_id));
                    }


                } while (attemptedPaperData.moveToNext());

                for (String paperId : paperIds) {

                    PaperData = mDbHelper.getPaperById(Integer.parseInt(paperId));

                    do {
                        int paper_year = PaperData.getInt(PaperData.getColumnIndex("Year"));
                        if (!paperYears.contains(String.valueOf(paper_year))) {
                            paperYears.add(String.valueOf(paper_year));
                        }


                    } while (PaperData.moveToNext());
                }

            } catch (Exception e) {

                Toast.makeText(getActivity(), "දත්ත නොමැත", Toast.LENGTH_SHORT).show();
            }


            attemptedPaperData = mDbHelper.getAttemtedPapers(mode);

            do {
                int paper_id = attemptedPaperData.getInt(attemptedPaperData.getColumnIndex("Paper_Id"));
                if (!paperIds.contains(String.valueOf(paper_id))) {
                    paperIds.add(String.valueOf(paper_id));
                }


            } while (attemptedPaperData.moveToNext());

            for (String paperId : paperIds) {

                PaperData = mDbHelper.getPaperById(Integer.parseInt(paperId));

                do {
                    int paper_year = PaperData.getInt(PaperData.getColumnIndex("Year"));
                    if (!paperYears.contains(String.valueOf(paper_year))) {
                        paperYears.add(String.valueOf(paper_year));
                    }


                } while (PaperData.moveToNext());
            }


            adapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, paperYears);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            paperYearSpinner.setAdapter(adapter);

            //TODO get attempts from
            ArrayList<String> attemptms = new ArrayList<>();
            attemptms.add("1");
            attemptms.add("2");
            attemptms.add("3");
            attemptms.add("4");
            attemptms.add("5");

            adapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, attemptms);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            attemptSpinner.setAdapter(adapter);

            mDbHelper.close();
        }
    }

    private void loadResults() {

        mDbHelper = new Utlis.DataAdapter(getActivity());
        mDbHelper.createDatabase();
        mDbHelper.open();

        //TODO implement method for exams too
        attemptData = mDbHelper.getResultsTest(1);

        attemptData.moveToFirst();

        list = new ArrayList();

        do {
            int attempts_master_id = attemptData.getInt(attemptData.getColumnIndex("Attempts_Master_Id"));
            int attempt_no = attemptData.getInt(attemptData.getColumnIndex("Attempt_No"));
            int correct_count = attemptData.getInt(attemptData.getColumnIndex("Correct_Count"));
            int unanswered_count = attemptData.getInt(attemptData.getColumnIndex("Unanswered_Count"));
            int full_count = attemptData.getInt(attemptData.getColumnIndex("Full_Count"));
            double marks = attemptData.getDouble(attemptData.getColumnIndex("Marks"));
            int mode = attemptData.getInt(attemptData.getColumnIndex("Mode"));
            int paper_id = attemptData.getInt(attemptData.getColumnIndex("Paper_Id"));


            list.add(attempts_master_id);
            list.add(attempt_no);
            list.add(correct_count);
            list.add(unanswered_count);
            list.add(full_count);
            list.add(marks);
            list.add(mode);
            list.add(paper_id);

        }while (attemptData.moveToNext());

        mDbHelper.close();

    }

}
