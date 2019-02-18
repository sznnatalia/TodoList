package hu.nati.appok.todolist.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hu.nati.appok.todolist.model.Item;

public class TodoDAO {
    private TODODBHelper helper;


    public TodoDAO(Context context) {
        helper = new TODODBHelper(context);

    }

    public List<Item> getAllItems() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM todoitem", null);

        List<Item> items = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String todo = cursor.getString(cursor.getColumnIndex("todo"));
            String comment = cursor.getString(cursor.getColumnIndex("comment"));
            int status = cursor.getInt(cursor.getColumnIndex("status"));
            int prior = cursor.getInt(cursor.getColumnIndex("priority"));
            String date = cursor.getString(cursor.getColumnIndex("date"));


           Item item = new Item(id, todo, comment, status, prior, date );
            items.add(item);
            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        return items;

    }

    public void deleteItem(Item item) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("todoitem", "id =?", new String[]{(item.getId() + "")});
        db.close();

    }

    public int saveItem(Item item) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("todo", item.getTodo());
        cv.put("comment", item.getComment());
        cv.put("priority", item.getPrior());
        cv.put("date", item.getDate());
        cv.put("status", item.getStatus());

        if (item.getId() == -1) {//új elem felvétele

            long id = db.insert("todoitem", null, cv);
            return (int) id;
        } else {
            db.update("todoitem", cv, "id=?", new String[]{(item.getId() + "")});
        }


        db.close();

        return -1;
    }

    public void deleteAllItems() {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("DELETE FROM todoitem");
        db.close();

    }
}
