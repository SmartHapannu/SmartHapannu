package Utlis;
import java.io.IOException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class DataAdapter
{
    protected static final String TAG = "DataAdapter";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private Utlis.AssetDatabaseOpenHelper mDbHelper;

    public DataAdapter(Context context)
    {
        this.mContext = context;
        mDbHelper = new Utlis.AssetDatabaseOpenHelper(mContext);
    }

    public Utlis.DataAdapter createDatabase() throws SQLException
    {
        try
        {
            mDbHelper.createDataBase();
        }
        catch (IOException mIOException)
        {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public Utlis.DataAdapter open() throws SQLException
    {
        try
        {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getWritableDatabase();
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close()
    {
        mDbHelper.close();
    }

    public Cursor getTestData()
    {
        try
        {
            String sql ="SELECT * FROM Providers WHERE Is_Active = 1";

            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur!=null)
            {
                mCur.moveToNext();
            }
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public Cursor getPapersData(int provider_id)
    {
        try
        {
            //String sql ="SELECT p.Paper_Id,p.Name,p.Year,p.Allocated_Time,p.Attempts, COUNT(q.Question_Id) AS Question_Count FROM Papers p, Questions q WHERE p.Provider_Id = " + provider_id + " AND p.Paper_Id = q.Paper_Id ORDER BY p.Paper_Id ASC";
            String sql ="SELECT Paper_Id,Name,Year,Allocated_Time,Attempts FROM Papers WHERE Provider_Id = " + provider_id;

            Cursor mCur = mDb.rawQuery(sql, null);
            /*if (mCur!=null)
            {
                mCur.moveToNext();
            }*/
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public Cursor getQuestionData(int paper_id)
    {
        try
        {
            //String sql ="SELECT p.Paper_Id,p.Name,p.Year,p.Allocated_Time,p.Attempts, COUNT(q.Question_Id) AS Question_Count FROM Papers p, Questions q WHERE p.Provider_Id = " + provider_id + " AND p.Paper_Id = q.Paper_Id ORDER BY p.Paper_Id ASC";
            String sql ="SELECT * FROM Questions WHERE Paper_Id = " + paper_id;

            Cursor mCur = mDb.rawQuery(sql, null);
            /*if (mCur!=null)
            {
                mCur.moveToNext();
            }*/
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public Cursor getAnswerData(int question_id)
    {
        try
        {
            //String sql ="SELECT p.Paper_Id,p.Name,p.Year,p.Allocated_Time,p.Attempts, COUNT(q.Question_Id) AS Question_Count FROM Papers p, Questions q WHERE p.Provider_Id = " + provider_id + " AND p.Paper_Id = q.Paper_Id ORDER BY p.Paper_Id ASC";
            String sql ="SELECT * FROM Answers WHERE Question_Id = " + question_id;

            Cursor mCur = mDb.rawQuery(sql, null);
            /*if (mCur!=null)
            {
                mCur.moveToNext();
            }*/
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public Cursor getNextAttemptNumber(int paperId)
    {
        try
        {
            //String sql ="SELECT p.Paper_Id,p.Name,p.Year,p.Allocated_Time,p.Attempts, COUNT(q.Question_Id) AS Question_Count FROM Papers p, Questions q WHERE p.Provider_Id = " + provider_id + " AND p.Paper_Id = q.Paper_Id ORDER BY p.Paper_Id ASC";
            String sql ="SELECT MAX(Attempt_No) AS count FROM Attempts_Master WHERE Paper_Id = " + paperId;

            Cursor mCur = mDb.rawQuery(sql, null);
            /*if (mCur!=null)
            {
                mCur.moveToNext();
            }*/
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public Cursor getExamNextAttemptNumber(int paperId)
    {
        try
        {
            //String sql ="SELECT p.Paper_Id,p.Name,p.Year,p.Allocated_Time,p.Attempts, COUNT(q.Question_Id) AS Question_Count FROM Papers p, Questions q WHERE p.Provider_Id = " + provider_id + " AND p.Paper_Id = q.Paper_Id ORDER BY p.Paper_Id ASC";
            String sql ="SELECT MAX(Attempt_No) AS count FROM Exam_Attempts WHERE Paper_Id = " + paperId;

            Cursor mCur = mDb.rawQuery(sql, null);
            /*if (mCur!=null)
            {
                mCur.moveToNext();
            }*/
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public int saveAttempt(int Attempt_No, int Correct_Count, int Unanswered_Count, int Full_Count, float Marks, String Mode, int Paper_Id)
    {
        try
        {
            //String sql ="SELECT p.Paper_Id,p.Name,p.Year,p.Allocated_Time,p.Attempts, COUNT(q.Question_Id) AS Question_Count FROM Papers p, Questions q WHERE p.Provider_Id = " + provider_id + " AND p.Paper_Id = q.Paper_Id ORDER BY p.Paper_Id ASC";
            //String sql ="INSERT INTO Leisure_Attempts (Attempt_No,Correct_Count,Unanswered_Count,Full_Count, Marks, Paper_Id) VALUES('"+Attempt_No+"', '"+Correct_Count+"', '"+Unanswered_Count+"', '"+Full_Count+"', '"+Marks+"', '"+Paper_Id+"')";

            ContentValues values = new ContentValues();
            values.put("Attempt_No", Attempt_No);
            values.put("Correct_Count", Correct_Count);
            values.put("Unanswered_Count", Unanswered_Count);
            values.put("Full_Count", Full_Count);
            values.put("Marks", Marks);
            values.put("Mode", Mode);
            values.put("Paper_Id", Paper_Id);

            long newRowId = mDb.insert("Attempts_Master", null, values);

            int rowid= (int) newRowId;

            return rowid;
            //mDb.execSQL(sql);
            /*if (mCur!=null)
            {
                mCur.moveToNext();
            }*/

        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public int saveCategoryAttempt(int Category_Id, int Sub_Category_Id, int Correct_Count, int Unanswered_Count, int Full_Count, int Attempts_Master_Id)
    {
        try
        {
            //String sql ="SELECT p.Paper_Id,p.Name,p.Year,p.Allocated_Time,p.Attempts, COUNT(q.Question_Id) AS Question_Count FROM Papers p, Questions q WHERE p.Provider_Id = " + provider_id + " AND p.Paper_Id = q.Paper_Id ORDER BY p.Paper_Id ASC";
            //String sql ="INSERT INTO Leisure_Attempts (Attempt_No,Correct_Count,Unanswered_Count,Full_Count, Marks, Paper_Id) VALUES('"+Attempt_No+"', '"+Correct_Count+"', '"+Unanswered_Count+"', '"+Full_Count+"', '"+Marks+"', '"+Paper_Id+"')";

            ContentValues values = new ContentValues();
            values.put("Category_Id", Category_Id);
            values.put("Sub_Category_Id", Sub_Category_Id);
            values.put("Correct_Count", Correct_Count);
            values.put("Unanswered_Count", Unanswered_Count);
            values.put("Full_Count", Full_Count);
            values.put("Attempts_Master_Id", Attempts_Master_Id);


            long newRowId = mDb.insert("Attempts_Category", null, values);

            int rowid= (int) newRowId;

            return rowid;
            //mDb.execSQL(sql);
            /*if (mCur!=null)
            {
                mCur.moveToNext();
            }*/

        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public void saveExamAttempt(int Attempt_No, int Correct_Count, int Unanswered_Count, int Full_Count, float Marks, String Mode, int Paper_Id)
    {
        try
        {
            //String sql ="SELECT p.Paper_Id,p.Name,p.Year,p.Allocated_Time,p.Attempts, COUNT(q.Question_Id) AS Question_Count FROM Papers p, Questions q WHERE p.Provider_Id = " + provider_id + " AND p.Paper_Id = q.Paper_Id ORDER BY p.Paper_Id ASC";
            //String sql ="INSERT INTO Leisure_Attempts (Attempt_No,Correct_Count,Unanswered_Count,Full_Count, Marks, Paper_Id) VALUES('"+Attempt_No+"', '"+Correct_Count+"', '"+Unanswered_Count+"', '"+Full_Count+"', '"+Marks+"', '"+Paper_Id+"')";

            ContentValues values = new ContentValues();
            values.put("Attempt_No", Attempt_No);
            values.put("Correct_Count", Correct_Count);
            values.put("Unanswered_Count", Unanswered_Count);
            values.put("Full_Count", Full_Count);
            values.put("Marks", Marks);
            values.put("Mode", Mode);
            values.put("Paper_Id", Paper_Id);

            long newRowId = mDb.insert("Exam_Attempts", null, values);

            int rowid= (int) newRowId;

            //mDb.execSQL(sql);
            /*if (mCur!=null)
            {
                mCur.moveToNext();
            }*/

        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public void addToAttemptsMaster(int attemptNo, int correctCount, int unansweredCount, int fullCount, float marks, String mode, int paperId){
        try
        {
            String sql ="INSERT INTO Attempts_Master (Attempt_No, Correct_Count, Unanswered_Count, Full_Count, Marks, Mode, Paper_Id) VALUES ("+ attemptNo + ","+ correctCount + ","+ unansweredCount + ","+ fullCount + ","+ marks + ","+ mode + ","+ paperId  +")";

            mDb.rawQuery(sql, null);

            //String attemptsMasterId = "SELECT last_insert_rowid()";


        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public Cursor getExameAttempt(int Attempt_No, int Correct_Count, int Unanswered_Count, int Full_Count, float Marks, int Paper_Id)
    {
        try
        {
            //String sql ="SELECT p.Paper_Id,p.Name,p.Year,p.Allocated_Time,p.Attempts, COUNT(q.Question_Id) AS Question_Count FROM Papers p, Questions q WHERE p.Provider_Id = " + provider_id + " AND p.Paper_Id = q.Paper_Id ORDER BY p.Paper_Id ASC";
            String sql ="INSERT INTO Leisure_Attempts (Attempt_No,Correct_Count,Unanswered_Count,Full_Count, Marks, Paper_Id) VALUES('"+Attempt_No+"', '"+Correct_Count+"', '"+Unanswered_Count+"', '"+Full_Count+"', '"+Marks+"', '"+Paper_Id+"')";

            Cursor mCur = mDb.rawQuery(sql, null);
            /*if (mCur!=null)
            {
                mCur.moveToNext();
            }*/
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public Cursor getResultsTest(int paperId)
    {
        try
        {
            //String sql ="SELECT p.Paper_Id,p.Name,p.Year,p.Allocated_Time,p.Attempts, COUNT(q.Question_Id) AS Question_Count FROM Papers p, Questions q WHERE p.Provider_Id = " + provider_id + " AND p.Paper_Id = q.Paper_Id ORDER BY p.Paper_Id ASC";
            String sql ="SELECT * FROM Attempts_Master WHERE Paper_Id = " + paperId;

            Cursor mCur = mDb.rawQuery(sql, null);
            /*if (mCur!=null)
            {
                mCur.moveToNext();
            }*/
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public void deductAttempts(int paperId)
    {
        try
        {
            //String sql ="SELECT p.Paper_Id,p.Name,p.Year,p.Allocated_Time,p.Attempts, COUNT(q.Question_Id) AS Question_Count FROM Papers p, Questions q WHERE p.Provider_Id = " + provider_id + " AND p.Paper_Id = q.Paper_Id ORDER BY p.Paper_Id ASC";
            //String sql ="SELECT * FROM Leisure_Attempts WHERE Paper_Id = " + paperId;
            String sql ="UPDATE Papers SET Attempts = Attempts - 1 WHERE Paper_Id = " + paperId;

            Cursor mCur = mDb.rawQuery(sql, null);
            /*if (mCur!=null)
            {
                mCur.moveToNext();
            }*/
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public Cursor getAttemtedPapers(String mode)
    {
        try
        {
            //String sql ="SELECT p.Paper_Id,p.Name,p.Year,p.Allocated_Time,p.Attempts, COUNT(q.Question_Id) AS Question_Count FROM Papers p, Questions q WHERE p.Provider_Id = " + provider_id + " AND p.Paper_Id = q.Paper_Id ORDER BY p.Paper_Id ASC";
            String sql ="SELECT * FROM Attempts_Master WHERE Mode = '" + mode + "'" ;

            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur!=null)
            {
                mCur.moveToNext();
            }
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public Cursor getPaperById(int paperId)
    {
        try
        {
            //String sql ="SELECT p.Paper_Id,p.Name,p.Year,p.Allocated_Time,p.Attempts, COUNT(q.Question_Id) AS Question_Count FROM Papers p, Questions q WHERE p.Provider_Id = " + provider_id + " AND p.Paper_Id = q.Paper_Id ORDER BY p.Paper_Id ASC";
            //String sql ="SELECT * FROM Leisure_Attempts WHERE Paper_Id = " + paperId;
            String sql ="SELECT Year FROM Papers WHERE Paper_Id = " + paperId;

            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur!=null)
            {
                mCur.moveToNext();
            }
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public Cursor getAttemptedMaster(String mode, int paperId, int attemptId)
    {
        try
        {
            //String sql ="SELECT p.Paper_Id,p.Name,p.Year,p.Allocated_Time,p.Attempts, COUNT(q.Question_Id) AS Question_Count FROM Papers p, Questions q WHERE p.Provider_Id = " + provider_id + " AND p.Paper_Id = q.Paper_Id ORDER BY p.Paper_Id ASC";
            String sql ="SELECT * FROM Attempts_Master WHERE Mode = '" + mode + "' AND Paper_Id = " + paperId + " AND Attempt_No = " + attemptId;

            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur!=null)
            {
                mCur.moveToNext();
            }
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public Cursor getCategoryWiseResults(int attempts_master_id)
    {
        try
        {
            //String sql ="SELECT p.Paper_Id,p.Name,p.Year,p.Allocated_Time,p.Attempts, COUNT(q.Question_Id) AS Question_Count FROM Papers p, Questions q WHERE p.Provider_Id = " + provider_id + " AND p.Paper_Id = q.Paper_Id ORDER BY p.Paper_Id ASC";
            String sql ="SELECT Attempts_Category_Id,Category_Id,Sub_Category_Id,Correct_Count,Unanswered_Count,Full_Count FROM Attempts_Category WHERE Attempts_Master_Id = " + attempts_master_id;

            Cursor mCur = mDb.rawQuery(sql, null);
            /*if (mCur!=null)
            {
                mCur.moveToNext();
            }*/
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public Cursor getSubCategoryName(int sub_category_id)
    {
        try
        {
            //String sql ="SELECT p.Paper_Id,p.Name,p.Year,p.Allocated_Time,p.Attempts, COUNT(q.Question_Id) AS Question_Count FROM Papers p, Questions q WHERE p.Provider_Id = " + provider_id + " AND p.Paper_Id = q.Paper_Id ORDER BY p.Paper_Id ASC";
            String sql ="SELECT * FROM Sub_Lessons WHERE Sub_Lesson_Id = " + Integer.toString(sub_category_id) ;

            Cursor mCur = mDb.rawQuery(sql, null);
            /*if (mCur!=null)
            {
                mCur.moveToNext();
            }*/
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public Cursor getPaperName(int paper_id)
    {
        try
        {
            //String sql ="SELECT p.Paper_Id,p.Name,p.Year,p.Allocated_Time,p.Attempts, COUNT(q.Question_Id) AS Question_Count FROM Papers p, Questions q WHERE p.Provider_Id = " + provider_id + " AND p.Paper_Id = q.Paper_Id ORDER BY p.Paper_Id ASC";
            String sql ="SELECT Name FROM Papers WHERE Paper_Id = " + Integer.toString(paper_id) ;

            Cursor mCur = mDb.rawQuery(sql, null);
            /*if (mCur!=null)
            {
                mCur.moveToNext();
            }*/
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

}