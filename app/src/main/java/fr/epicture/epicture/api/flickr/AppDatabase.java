package fr.epicture.epicture.api.flickr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import fr.epicture.epicture.database.MySQLite;
import fr.todolist.todolist.interfaces.SearchInterface;
import fr.todolist.todolist.utils.AlertInfo;
import fr.todolist.todolist.utils.DateTimeManager;
import fr.todolist.todolist.utils.SortingInfo;
import fr.todolist.todolist.utils.TodoItemFilter;
import fr.todolist.todolist.utils.TodoItemInfo;

/**
 * Created by Stephane on 16/01/2017.
 */

public class AppDatabase {

    private static final int VERSION_BDD = 1;
    public static final String BDD_NAME = "account.db";


    private Context context;
    private SQLiteDatabase database;
    private MySQLite mySQLite;

    public AppDatabase(Context context) {
        mySQLite = new MySQLite(context, BDD_NAME, null, VERSION_BDD);
        this.context = context;
    }

    public void open() {
        database = mySQLite.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public TodoItemInfo insertItem(TodoItemInfo item) {
        ContentValues values = new ContentValues();
        values.put(MySQLite.TODO_COL_TITLE, item.title);
        values.put(MySQLite.TODO_COL_CONTENT, item.content);
        values.put(MySQLite.TODO_COL_DUE_DATE, item.dateTime);
        values.put(MySQLite.TODO_COL_FLAG_STATUS, item.status.getValue());

        item.id = database.insert(MySQLite.TODO_TABLE_NAME, null, values);
        return (item);
    }


    /**
     * Update a To doItemInfo in the database
     *
     * @param item The item
     * @return The number of row reached
     */
    public int updateItem(TodoItemInfo item) {
        item.dateTime = DateTimeManager.formatDateTime(item.year, item.month, item.day, item.hour, item.minute);

        ContentValues values = new ContentValues();
        values.put(MySQLite.TODO_COL_TITLE, item.title);
        values.put(MySQLite.TODO_COL_CONTENT, item.content);
        values.put(MySQLite.TODO_COL_DUE_DATE, item.dateTime);
        values.put(MySQLite.TODO_COL_FLAG_STATUS, item.status.getValue());

        return (database.update(MySQLite.TODO_TABLE_NAME, values, MySQLite.TODO_COL_ID + " = " + item.id, null));
    }

    public int deleteItem(long id) {
        return (database.delete(MySQLite.TODO_TABLE_NAME, MySQLite.TODO_COL_ID + " = " + id, null));
    }

    public int deleteAlert(int id) {
        return (database.delete(MySQLite.ALARM_TABLE_NAME, MySQLite.ALARM_COL_ID + " = " + id, null));
    }


    @Override
    public List<TodoItemInfo> getItemsByDueDate(SortingInfo.Type date) {
        String orderDate = (date == SortingInfo.Type.Ascendant) ? "ASC" : "DESC";
        return (getTodoItemResult("SELECT * FROM " + MySQLite.TODO_TABLE_NAME + " ORDER BY " + MySQLite.TODO_COL_FLAG_STATUS
                + " DESC, " + MySQLite.TODO_COL_DUE_DATE + " " + orderDate + ";"));
    }

    @Override
    public List<TodoItemInfo> getItemsByTitle(String toSearch, SortingInfo.Type date) {
        String orderDate = (date == SortingInfo.Type.Ascendant) ? "ASC" : "DESC";
        return (getTodoItemResult("SELECT * FROM " + MySQLite.TODO_TABLE_NAME + " WHERE " + MySQLite.TODO_COL_TITLE + " LIKE '%" + toSearch + "%'"
                + " ORDER BY " + MySQLite.TODO_COL_FLAG_STATUS + " DESC, " + MySQLite.TODO_COL_DUE_DATE + " " + orderDate + ";"));
    }

    @Override
    public List<TodoItemInfo> getItemsByContent(String toSearch, SortingInfo.Type date) {
        String orderDate = (date == SortingInfo.Type.Ascendant) ? "ASC" : "DESC";
        return (getTodoItemResult("SELECT * FROM " + MySQLite.TODO_TABLE_NAME + " WHERE " + MySQLite.TODO_COL_CONTENT + " LIKE '%" + toSearch + "%'"
                + " ORDER BY " + MySQLite.TODO_COL_FLAG_STATUS + " DESC," + MySQLite.TODO_COL_DUE_DATE + " " + orderDate + ";"));
    }

    @Nullable
    public TodoItemInfo getItemByID(int id) {
        List<TodoItemInfo> list = getTodoItemResult("SELECT * FROM " + MySQLite.TODO_TABLE_NAME + " WHERE " + MySQLite.TODO_COL_ID + " ='" + id + "';");
        return ((list.size() > 0) ? list.get(0) : null);
    }

    private List<TodoItemInfo> getTodoItemResult(String query) {
        List<TodoItemInfo> results = new ArrayList<>();
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                results.add(cursorToTodoItemInfo(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return (results);
    }


    private TodoItemInfo cursorToTodoItemInfo(Cursor cursor) {
        TodoItemInfo item = new TodoItemInfo();
        item.id = cursor.getInt(MySQLite.TODO_NUM_COL_ID);
        item.title = cursor.getString(MySQLite.TODO_NUM_COL_TITLE);
        item.content = cursor.getString(MySQLite.TODO_NUM_COL_CONTENT);
        item.dateTime = cursor.getString(MySQLite.TODO_NUM_COL_DUE_DATE);

        int status = cursor.getInt(MySQLite.TODO_NUM_COL_FLAG_STATUS);
        if (status == TodoItemInfo.Status.ToDo.getValue()) {
            item.status = TodoItemInfo.Status.ToDo;
        } else if (status == TodoItemInfo.Status.Done.getValue()) {
            item.status = TodoItemInfo.Status.Done;
        } else if (status == TodoItemInfo.Status.Overdue.getValue()) {
            item.status = TodoItemInfo.Status.Overdue;
        }

        item = DateTimeManager.retrieveDateTime(item, item.dateTime);

        item.remind = cursor.getInt(MySQLite.TODO_NUM_COL_REMIND) == 1;
        item.nbRecurrence = cursor.getInt(MySQLite.TODO_NUM_COL_NB_RECURRENCE);
        item.intervalRecurrence = cursor.getLong(MySQLite.TODO_NUM_COL_INTERVAL);
        item.intervalType = cursor.getString(MySQLite.TODO_NUM_COL_INTERVAL_TYPE);
        item.nbBaseRecurrence = cursor.getInt(MySQLite.TODO_NUM_COL_BASE_NB_RECURRENCE);
        item.priority = cursor.getInt(MySQLite.TODO_NUM_COL_PRIORITY);
        item.photos = cursor.getString(MySQLite.TODO_NUM_COL_ILLUSTRATIONS);

        return (item);
    }
}
