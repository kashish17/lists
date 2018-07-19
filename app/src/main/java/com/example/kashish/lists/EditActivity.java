package com.example.kashish.lists;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity {

    Boolean ifFilter=false;
    EditText en,ea,date,time;
    String timeExpense,dateExpense;
    Intent intent;
    Bundle bundle;
    int year,month,day,hour,min;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        intent=getIntent();
        bundle=intent.getExtras();
        en=findViewById(R.id.nameE);
        ea=findViewById(R.id.ageE);
        date=findViewById(R.id.dateE);
        time=findViewById(R.id.timeE);

        if(intent.getAction()!=null){
            en.setText(intent.getStringExtra(Intent.EXTRA_TEXT));
            ifFilter=true;

        }else {
            en.setText(bundle.getString(Contract.todo.NAME));
            ea.setText(bundle.getInt(Contract.todo.AGE) + "");
            date.setText(bundle.getString(Contract.todo.DATE) + "");
            time.setText(bundle.getString(Contract.todo.TIME) + "");

        }

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime();
            }
        });
    }
        private void setTime() {

            Calendar calendar = Calendar.getInstance();
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            min = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(EditActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    timeExpense = hourOfDay + ":" + minute;
                    time.setText(timeExpense);

                    EditActivity.this.hour = hourOfDay;
                    EditActivity.this.min = minute;

                }
            },hour,min,false);

            timePickerDialog.show();
        }

        private void setDate() {


            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(EditActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    int tempMonth = month + 1;
                    dateExpense = dayOfMonth + "/" + tempMonth + "/" + year;
                    date.setText(dateExpense);

                    EditActivity.this.day = dayOfMonth;
                    EditActivity.this.month  = month;
                    EditActivity.this.year  = year;

                }
            },year,month,day);

            datePickerDialog.show();
        }
    private boolean validateFields() {

        String name = en.getText().toString().trim();
        String age = ea.getText().toString().trim();
        String dateS = date.getText().toString();
        String timeS = time.getText().toString();

        if (name.isEmpty()) {

            en.setError("Enter title");
            return false;
        }

        if (age.isEmpty()) {

            ea.setError("Enter age");
            return false;
        }

        if (dateS.isEmpty()) {

            date.setError("Select date");
            return false;
        }
        return  true;
    }

        public void submit(View view) {

            if (validateFields()) {
                Intent intent = new Intent();
                Bundle data = new Bundle();
                data.putString("name", en.getText().toString());
                data.putInt("age", Integer.parseInt(ea.getText().toString()));
                data.putString(Contract.todo.DATE, date.getText().toString());
                data.putString(Contract.todo.TIME, time.getText().toString());
                intent.putExtras(data);
                if (ifFilter) {
                    ContentValues contentValues=new ContentValues()  ;
                    TODOopenHelper openHelper= TODOopenHelper.getInstance(this);
                    SQLiteDatabase database=openHelper.getWritableDatabase();


                    contentValues.put(Contract.todo.NAME, en.getText().toString());
                    contentValues.put(Contract.todo.AGE, ea.getText().toString());
                    contentValues.put(Contract.todo.TIME, time.getText().toString());
                    contentValues.put(Contract.todo.DATE, date.getText().toString());

                    long id= database.insert(Contract.todo.TABLE_NAME,null,contentValues);
                    //expenseDAO.addExpense(e);



                    Bundle bundle = intent.getExtras();


                    AlarmManager manager= (AlarmManager) getSystemService(ALARM_SERVICE);

                    Intent intentA=new Intent(this,AlarmReciever.class);
                    intentA.putExtra(Contract.todo.NAME,bundle.getString(Contract.todo.NAME));
                    intentA.putExtra(Contract.todo.AGE,bundle.getInt(Contract.todo.AGE));
                    intentA.putExtra(Contract.todo.DATE,bundle.getString(Contract.todo.DATE));
                    intentA.putExtra(Contract.todo.TIME,bundle.getString(Contract.todo.TIME));
                    intentA.putExtra(Contract.todo.id,bundle.getInt(Contract.todo.id));

                    PendingIntent pendingIntent=PendingIntent.getBroadcast(this,(int)id,intentA,PendingIntent.FLAG_UPDATE_CURRENT);

                    Calendar calendar=Calendar.getInstance();
                    calendar.set(bundle.getInt("yy")
                            ,bundle.getInt("mm")
                            ,bundle.getInt("dd")
                            ,bundle.getInt("hh")
                            ,bundle.getInt("mn"));

                    Log.i("MainActivity",""+calendar.getTime());
                    long epoch=calendar.getTimeInMillis();

                    manager.set(AlarmManager.RTC_WAKEUP,epoch,pendingIntent);

                    finish();


                } else {
                    setResult(3, intent);
                    finish();
                }
            }
            }
        }

