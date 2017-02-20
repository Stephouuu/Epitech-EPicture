package fr.epicture.epicture.api.flickr;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Application database
 */
public class MySQLite extends SQLiteOpenHelper {

    public static final String ACCOUNT_TABLE_NAME = "Todo";

    public static final String TODO_COL_ID = "ID";
    public static final int TODO_NUM_COL_ID = 0;

    public static final String TODO_COL_TITLE = "title";
    public static final int TODO_NUM_COL_TITLE = 1;

    public static final String TODO_COL_CONTENT = "content";
    public static final int TODO_NUM_COL_CONTENT = 2;

    public static final String TODO_COL_DUE_DATE = "due_date";
    public static final int TODO_NUM_COL_DUE_DATE = 3;

    public static final String TODO_COL_FLAG_STATUS = "status";
    public static final int TODO_NUM_COL_FLAG_STATUS = 4;


    private static final String TODO_TABLE = "CREATE TABLE " + TODO_TABLE_NAME + " ("
            + TODO_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TODO_COL_TITLE + " TEXT NOT NULL, "
            + TODO_COL_CONTENT + " TEXT NOT NULL, "
            + TODO_COL_DUE_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP, "
            + TODO_COL_FLAG_STATUS + " INTEGER DEFAULT 0);";

    /**
     * @param context Le contexte
     * @param name Le nom du fichier de la BDD
     * @param factory Personnalisation de la classe Cursor
     * @param version La version de la base de donnée
     */
    public MySQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TODO_TABLE_NAME + ";");
        onCreate(db);
    }

}
