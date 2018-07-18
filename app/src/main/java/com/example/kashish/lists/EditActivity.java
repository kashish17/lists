package com.example.kashish.lists;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity {

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

        en.setText(bundle.getString(Contract.todo.NAME));
        ea.setText(bundle.getInt(Contract.todo.AGE)+"");
        date.setText(bundle.getString(Contract.todo.DATE)+"");
        time.setText(bundle.getString(Contract.todo.TIME)+"");

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

        public void submit(View view){

        Intent intent=new Intent();
        Bundle data =new Bundle();
        data.putString("name",en.getText().toString());
        data.putInt("age",Integer.parseInt(ea.getText().toString()));
        data.putString(Contract.todo.DATE ,date.getText().toString());
        data.putString(Contract.todo.TIME ,time.getText().toString());
        intent.putExtras(data);
        setResult(2,intent);
        finish();
    }
}
