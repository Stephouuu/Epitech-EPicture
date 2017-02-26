package fr.epicture.epicture.api.imgur.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ImgurSQLite extends SQLiteOpenHelper {

    // ========================================================================
    // STATIC FIELDS
    // ========================================================================

    public static final String TABLE_NAME = "account";

    public static final String ACCOUNT_COL_DB_ID = "DB_ID";
    public static final int ACCOUNT_NUM_COL_DB_ID = 0;

    public static final String ACCOUNT_COL_ID = "ID";
    public static final int ACCOUNT_NUM_COL_ID = 1;

    public static final String ACCOUNT_COL_USERNAME = "USERNAME";
    public static final int ACCOUNT_NUM_COL_USERNAME = 2;

    public static final String ACCOUNT_COL_REFRESH_TOKEN = "REFRESH_TOKEN";
    public static final int ACCOUNT_NUM_COL_REFRESH_TOKEN = 3;

    public static final String ACCOUNT_COL_ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final int ACCOUNT_NUM_COL_ACCESS_TOKEN = 4;

    public static final String ACCOUNT_COL_ACCESS_TOKEN_DATETIME = "ACCESS_TOKEN_DATETIME";
    public static final int ACCOUNT_NUM_COL_ACCESS_TOKEN_DATETIME = 5;

    public static final String ACCOUNT_COL_ACCESS_TOKEN_DURATION = "ACCESS_TOKEN_DURATION";
    public static final int ACCOUNT_NUM_COL_ACCESS_TOKEN_DURATION = 6;

    private static final String TODO_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + ACCOUNT_COL_DB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ACCOUNT_COL_ID + " TEXT NOT NULL,"
            + ACCOUNT_COL_USERNAME + " TEXT NOT NULL, "
            + ACCOUNT_COL_REFRESH_TOKEN + " TEXT, "
            + ACCOUNT_COL_ACCESS_TOKEN + " TEXT NOT NULL, "
            + ACCOUNT_COL_ACCESS_TOKEN_DATETIME + " TEXT NOT NULL, "
            + ACCOUNT_COL_ACCESS_TOKEN_DURATION + " TEXT NOT NULL);";

    // ========================================================================
    // CONSTRUCTOR
    // ========================================================================

    public ImgurSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // ========================================================================
    // METHODS
    // ========================================================================

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_NAME + ";");
        onCreate(db);
    }
}