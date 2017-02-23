package fr.epicture.epicture.api.imgur.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.epicture.epicture.api.imgur.ImgurAccount;

public class ImgurDatabase {
    private static final int VERSION_BDD = 1;
    public static final String BDD_NAME = "imgur_account.db";

    private Context context;
    private SQLiteDatabase database;
    private ImgurSQLite mySQLite;

    public ImgurDatabase(Context context) {
        mySQLite = new ImgurSQLite(context, BDD_NAME, null, VERSION_BDD);
        this.context = context;
    }

    public void open() {
        database = mySQLite.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public ImgurAccount insertAccount(ImgurAccount item) {
        List<ImgurAccount> result = toImgurAccounts("SELECT * FROM " + ImgurSQLite.TABLE_NAME
                + " WHERE " + ImgurSQLite.ACCOUNT_COL_ID + " = '" + item.id + "'");

        if (result.size() == 0) {
            ContentValues values = new ContentValues();
            values.put(ImgurSQLite.ACCOUNT_COL_REFRESH_TOKEN, item.getRefreshToken());
            values.put(ImgurSQLite.ACCOUNT_COL_ID, item.getID());
            values.put(ImgurSQLite.ACCOUNT_COL_USERNAME, item.getUsername());
            item.setNsid((int) database.insert(ImgurSQLite.TABLE_NAME, null, values));
        }
        return (item);
    }

    public int updateAccount(ImgurAccount item) {
        ContentValues values = new ContentValues();
        values.put(ImgurSQLite.ACCOUNT_COL_REFRESH_TOKEN, item.getRefreshToken());
        values.put(ImgurSQLite.ACCOUNT_COL_ID, item.getID());
        values.put(ImgurSQLite.ACCOUNT_COL_USERNAME, item.getUsername());

        return (database.update(ImgurSQLite.TABLE_NAME, values, ImgurSQLite.ACCOUNT_COL_DB_ID + " = " + item.getNsid(), null));
    }

    public int deleteAccount(int dbid) {
        return (database.delete(ImgurSQLite.TABLE_NAME, ImgurSQLite.ACCOUNT_COL_DB_ID + " = " + dbid, null));
    }

    public List<ImgurAccount> getAccounts() {
        return (toImgurAccounts("SELECT * FROM " + ImgurSQLite.TABLE_NAME));
    }

    private List<ImgurAccount> toImgurAccounts(String query) {
        List<ImgurAccount> results = new ArrayList<>();
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                results.add(cursorToImgurAccount(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return (results);
    }

    private ImgurAccount cursorToImgurAccount(Cursor cursor) {
        try {
            return new ImgurAccount(
                    cursor.getInt(ImgurSQLite.ACCOUNT_NUM_COL_DB_ID),
                    cursor.getString(ImgurSQLite.ACCOUNT_NUM_COL_ID),
                    cursor.getString(ImgurSQLite.ACCOUNT_NUM_COL_USERNAME),
                    cursor.getString(ImgurSQLite.ACCOUNT_NUM_COL_REFRESH_TOKEN),
                    cursor.getString(ImgurSQLite.ACCOUNT_NUM_COL_ACCESS_TOKEN),
                    Long.valueOf(cursor.getString(ImgurSQLite.ACCOUNT_NUM_COL_ACCESS_TOKEN_DATETIME)),
                    Long.valueOf(cursor.getString(ImgurSQLite.ACCOUNT_NUM_COL_ACCESS_TOKEN_DURATION)));
        } catch (InstantiationException e) {
            System.err.println("Error : Could not instantiate Imgur account from database.");
            e.printStackTrace();
        }
        return null;
    }

}
