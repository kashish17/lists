package com.example.kashish.lists;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TODOopenHelper extends SQLiteOpenHelper {

    public static int VERSION=1;
    private TODOopenHelper(Context context) {
        super(context, Contract.todo.TABLE_NAME, null, VERSION);
    }

    private static TODOopenHelper openHelper;

    public static TODOopenHelper getInstance(Context context){
        if(openHelper==null){
            openHelper=new TODOopenHelper(context.getApplicationContext());
        }
        return openHelper;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String todosql="CREATE TABLE "+Contract.todo.TABLE_NAME +
                       " ( "+Contract.todo.id +" INTEGER PRIMARY KEY AUTOINCREMENT , "+ Contract.todo.NAME +
                       " TEXT , "+ Contract.todo.AGE+" INTEGER , "+ Contract.todo.DATE+" TEXT , "+ Contract.todo.TIME +" TEXT );";

        sqLiteDatabase.execSQL(todosql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
