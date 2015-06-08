package quizify.ajeet_meena.com.quizify.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseOperator
{

    public Database helper;
    Context context;

    public DatabaseOperator(Context context)
    {
        helper = new Database(context);
        this.context = context;

    }

    public long insertData(String question, String answer, int question_number, String[] option)
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Database.QUESTION_TEXT, question);
        contentValues.put(Database.QUESTION_ANSWER, answer);
        contentValues.put(Database.Q_ID, question_number);
        contentValues.put(Database.QUESTION_OPTION1, option[0]);
        contentValues.put(Database.QUESTION_OPTION2, option[1]);
        contentValues.put(Database.QUESTION_OPTION3, option[2]);
        contentValues.put(Database.QUESTION_OPTION4, option[3]);
        contentValues.put(Database.QUESTION_OPTION5, option[4]);

        long id = db.insertWithOnConflict(Database.TABLE_QUESTION_TEXT, null,
                contentValues, SQLiteDatabase.CONFLICT_REPLACE);


        Log.d("checkwa", "checkwa" + id);
        return id;

    }


    public int getQuestionAnswer(int q_id)
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Database.QUESTION_ANSWER};
        Cursor cursor = db.query(Database.TABLE_QUESTION_TEXT, columns,
                Database.Q_ID + "='" + q_id + "'", null, null, null, null);
        int answer = -1;
        while (cursor.moveToNext())
        {
            int index = cursor.getColumnIndex(Database.QUESTION_ANSWER);

            answer = cursor.getInt(index);


        }

        return answer;
    }


    public String getData(int i)
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Database.Q_ID, Database.QUESTION_TEXT};
        Cursor cursor = db.query(Database.TABLE_QUESTION_TEXT, columns,
                Database.Q_ID + "='" + i + "'", null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext())
        {
            int index = cursor.getColumnIndex(Database.QUESTION_TEXT);

            String sQUESTION_TEXT = cursor.getString(index);
            buffer.append(sQUESTION_TEXT);

        }

        Log.d("check check", "check check" + buffer.toString());
        return buffer.toString();
    }

    public int getResponseData(int q_id)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Database.QUESTION_RESPONSE};
        Cursor cursor = db.query(Database.TABLE_QUESTION_TEXT, columns,
                Database.Q_ID + "='" + q_id + "'", null, null, null, null);
        int response = 0;

        try
        {
            cursor.moveToFirst();
            response = cursor.getInt(0);
            Log.d("response", "response" + response);
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return response;

    }

    public long insertResponse(int response, int q_id)
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        long id = 0;

        contentValues.put(Database.QUESTION_RESPONSE, response);


        id = db.update(Database.TABLE_QUESTION_TEXT, contentValues, Database.Q_ID + "=" + q_id, null);

        return id;
    }


    public ArrayList<String> getOptionData(int i)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Database.QUESTION_OPTION1, Database.QUESTION_OPTION2, Database.QUESTION_OPTION3, Database.QUESTION_OPTION4, Database.QUESTION_OPTION5};
        Cursor cursor = db.query(Database.TABLE_QUESTION_TEXT, columns,
                Database.Q_ID + "=" + i + "", null, null, null, null);
        ArrayList<String> string = new ArrayList<String>();

        if (cursor.getCount() > 0)
        {

            cursor.moveToFirst();
            string.add(cursor.getString(0));
            string.add(cursor.getString(1));
            string.add(cursor.getString(2));
            string.add(cursor.getString(3));
            string.add(cursor.getString(4));

        }


        return string;
    }

}
