package com.example.kashish.lists;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.persistence.room.Room;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {

ArrayList<Expense> expenses=new ArrayList<>();
    ExpenseAdapter adapter;

    int position=0;
    public  static int ADD=1;
    public  static int SHOW=3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView=findViewById(R.id.list);

        adapter= new ExpenseAdapter(this,expenses);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

        TODOopenHelper openHelper= TODOopenHelper.getInstance(getApplicationContext());
        SQLiteDatabase database=openHelper.getReadableDatabase();
        Cursor cursor= database.query(Contract.todo.TABLE_NAME,null,null,null,null,null,null);
         while(cursor.moveToNext()){
             String name= cursor.getString(cursor.getColumnIndex(Contract.todo.NAME));
             int age= cursor.getInt(cursor.getColumnIndex(Contract.todo.AGE));
             String date=cursor.getString(cursor.getColumnIndex(Contract.todo.DATE));
             String time=cursor.getString(cursor.getColumnIndex(Contract.todo.TIME));
             int id= cursor.getInt(cursor.getColumnIndex(Contract.todo.id));
             Expense e= new Expense();
             e.setName(name);
             e.setAge(age);
             e.setDate(date);
             e.setTime(time);
             e.setId(id);
             expenses.add(e);
         }
         cursor.close();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        Intent intent= new Intent(MainActivity.this, AddActivity.class);
        startActivityForResult(intent,ADD);

        return  true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        position=i;
        Intent intent=new Intent(this,ListActivity.class);
        Bundle bundle=new Bundle();

        bundle.putString("name",expenses.get(i).getName());
        bundle.putInt("age",expenses.get(i).getAge());
        bundle.putString(Contract.todo.DATE ,expenses.get(i).getDate());
        bundle.putString(Contract.todo.TIME ,expenses.get(i).getTime());

        intent.putExtras(bundle);
        startActivityForResult(intent,SHOW);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Expense e =new Expense() ;

        ContentValues contentValues=new ContentValues()  ;
        TODOopenHelper openHelper= TODOopenHelper.getInstance(this);
        SQLiteDatabase database=openHelper.getWritableDatabase();

        if( data != null && data.getExtras() != null) {
            Bundle bun = data.getExtras();

            e.setName(bun.getString(Contract.todo.NAME));
            e.setAge(bun.getInt(Contract.todo.AGE));
            e.setTime(bun.getString(Contract.todo.TIME));
            e.setDate(bun.getString(Contract.todo.DATE));


            contentValues.put(Contract.todo.NAME, e.getName());
            contentValues.put(Contract.todo.AGE, e.getAge());
            contentValues.put(Contract.todo.TIME, e.getTime());
            contentValues.put(Contract.todo.DATE, e.getDate());
        }
        if(resultCode==3) {

            Expense updateExpense= expenses.get(position);
            String[] s= {updateExpense.getId()+""};
            database.update(Contract.todo.TABLE_NAME,contentValues,Contract.todo.id+" = ?", s);

            expenses.set(position, e);
            adapter.notifyDataSetChanged();
        }

         if(resultCode==AddActivity.SUBMIT){

            long id= database.insert(Contract.todo.TABLE_NAME,null,contentValues);
            //expenseDAO.addExpense(e);

            e.setId(id);
            expenses.add(e);
            adapter.notifyDataSetChanged();

            Bundle bundle = data.getExtras();


             AlarmManager manager= (AlarmManager) getSystemService(ALARM_SERVICE);

             Intent intent=new Intent(this,AlarmReciever.class);
             intent.putExtra(Contract.todo.NAME,bundle.getString(Contract.todo.NAME));
             intent.putExtra(Contract.todo.AGE,bundle.getInt(Contract.todo.AGE));
             intent.putExtra(Contract.todo.DATE,bundle.getString(Contract.todo.DATE));
             intent.putExtra(Contract.todo.TIME,bundle.getString(Contract.todo.TIME));

             PendingIntent pendingIntent=PendingIntent.getBroadcast(this,(int)id,intent,PendingIntent.FLAG_UPDATE_CURRENT);

             Calendar calendar=Calendar.getInstance();
             calendar.set(bundle.getInt("yy")
                     ,bundle.getInt("mm")
                     ,bundle.getInt("dd")
                     ,bundle.getInt("hh")
                     ,bundle.getInt("mn"));

             Log.i("MainActivity",""+calendar.getTime());
             long epoch=calendar.getTimeInMillis();

             manager.set(AlarmManager.RTC_WAKEUP,epoch,pendingIntent);


        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        TODOopenHelper openHelper=  TODOopenHelper.getInstance(this);
        final SQLiteDatabase database=openHelper.getWritableDatabase();
        final int position=i;
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("confirm delete");
        builder.setMessage("do you want to delete this item");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Expense e= expenses.get(position);
                expenses.remove(position);
               adapter.notifyDataSetChanged();
               String[] s= {e.getId()+""};
                database.delete(Contract.todo.TABLE_NAME,Contract.todo.id+" = ?",s);
            }

        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog= builder.create();
        dialog.show();

        return true;
    }


}
