package com.example.kashish.lists;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    public static int SUBMIT=2;

    EditText en,ea,es,date,time;
    int year,month,day;
    int hour,min;
    public  static int dialog_id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        en = findViewById(R.id.nameA);
        ea = findViewById(R.id.ageA);
        date=findViewById(R.id.dateEditText);
        time=findViewById(R.id.timeEditText);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        int tempMonth = month+1;
                        // dateExpense = dayOfMonth + "/" + tempMonth + "/" + year;
                        date.setText(dayOfMonth + "/" + tempMonth + "/" + year);

                        AddActivity.this.day = dayOfMonth;
                        AddActivity.this.month  = month;
                        AddActivity.this.year  = year;

                    }
                },year,month,day);

                datePickerDialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                min = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        time.setText(hourOfDay + ":" + minute);

                        AddActivity.this.hour = hourOfDay;
                        AddActivity.this.min = minute;

                    }
                },hour,min,false);

                timePickerDialog.show();
            }
        });




    }



    public  void  submitA (View view){
        Bundle bundle=new Bundle();
        bundle.putString(Contract.todo.NAME,en.getText().toString());
        bundle.putInt(Contract.todo.AGE,Integer.parseInt(ea.getText().toString()));
        bundle.putString(Contract.todo.DATE ,date.getText().toString());
        bundle.putString(Contract.todo.TIME ,time.getText().toString());
        bundle.putInt("yy",year);
        bundle.putInt("mm",month);
        bundle.putInt("dd",day);
        bundle.putInt("hh",hour);
        bundle.putInt("mn",min);

            Intent intent= new Intent();
            intent.putExtras(bundle);
            setResult(SUBMIT,intent);
            finish();
        }
}
