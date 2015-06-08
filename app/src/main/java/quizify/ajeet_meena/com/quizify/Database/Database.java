package quizify.ajeet_meena.com.quizify.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import quizify.ajeet_meena.com.quizify.Utilities.Message;

public class Database extends SQLiteOpenHelper
{


    public static final String TABLE_QUESTION_TEXT = "Aptitude";


    public static final String Q_ID = "_id";
    public static final String QUESTION_TEXT = "Question";
    public static final String QUESTION_ANSWER = "Answer";
    public static final String QUESTION_RESPONSE = "Response";
    public static final String QUESTION_OPTION1 = "Option1";
    public static final String QUESTION_OPTION2 = "Option2";
    public static final String QUESTION_OPTION3 = "Option3";
    public static final String QUESTION_OPTION4 = "Option4";
    public static final String QUESTION_OPTION5 = "Option5";


    public static final String CREATE_TABLE_QUESTION_DATABASE = "CREATE TABLE "
            + TABLE_QUESTION_TEXT
            + " ("
            + Q_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUESTION_TEXT
            + " VARCHAR(255), " + QUESTION_OPTION1
            + " VARCHAR(255), "
            + QUESTION_OPTION2
            + " VARCHAR(255), "
            + QUESTION_OPTION3
            + " VARCHAR(255), "
            + QUESTION_OPTION4
            + " VARCHAR(255), "
            + QUESTION_OPTION5
            + " VARCHAR(255), "

            + QUESTION_RESPONSE
            + " INTEGER, "
            + QUESTION_ANSWER + " VARCHAR(255));";


    public static final String DROP_TABLE_QUESTION = "DROP TABLE IF EXISTS "
            + TABLE_QUESTION_TEXT;


    public static final String DATABASE_QUESTION = "ExamDatabase";
    public static final int DATABASE_VERSION = 17;


    private Context context;

    public Database(Context context)
    {
        super(context, DATABASE_QUESTION, null, DATABASE_VERSION);
        this.context = context;
        // TODO Auto-generated constructor stub

    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        // TODO Auto-generated method stub
        try
        {
            db.execSQL(CREATE_TABLE_QUESTION_DATABASE);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            Message.message(context, "" + e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // TODO Auto-generated method stub
        try
        {
            db.execSQL(DROP_TABLE_QUESTION);
            onCreate(db);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            Message.message(context, "" + e);
        }
    }
}