package hu.nati.appok.todolist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TODODBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todolist.db";
    private static final int DATABASE_VERSION = 1;

    public TODODBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE todoitem (id INTEGER PRIMARY KEY AUTOINCREMENT, todo TEXT, comment TEXT, priority INTEGER, status INTEGER, date TEXT)");
        db.execSQL("INSERT INTO todoitem (todo, comment, priority, status, date) VALUES ('Farsangi jelmezt készíteni', 'Venni hozzá szalagot', 1, 0, '2019.01.29.')");
        db.execSQL("INSERT INTO todoitem (todo, comment, priority, status, date) VALUES ('Sütit sütni a záróbulira', 'Kell aszatlt gyümölcs', 2, 0, '2019.01.23.')");
        db.execSQL("INSERT INTO todoitem (todo, comment, priority, status, date) VALUES ('Kimosni a kabátom', '', 1, 0, '2019.01.30.')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE wishitem");
        onCreate(db);
    }
}
