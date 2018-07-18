package com.example.kashish.lists;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ListActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tn,ta,date,time;
    Bundle bundle;
    Button finishButton,edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent intent=getIntent();
        if(intent.getAction()!=null&&intent.getAction().equals("Alarm")){
            edit=findViewById(R.id.edit);
            edit.setVisibility(View.GONE);
        }
         bundle=intent.getExtras();
        tn=findViewById(R.id.name);
        ta=findViewById(R.id.age);
        date=findViewById(R.id.date);
        time =findViewById(R.id.time);
        finishButton = findViewById(R.id.finish_button);

        tn.setText(bundle.getString(Contract.todo.NAME)+"");
        ta.setText(bundle.getInt(Contract.todo.AGE,0)+"");
        date.setText(bundle.getString(Contract.todo.DATE)+"");
        time.setText(bundle.getString(Contract.todo.TIME)+"");





        finishButton.setOnClickListener(this);

    }

    public void editText(View view){

        Intent intent=new Intent(this,EditActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent,1);
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1 && data!=null) {
            bundle = data.getExtras();
            tn.setText(bundle.getString("name") + "");
            ta.setText(bundle.getInt("age", 0) + "");
            date.setText(bundle.getString(Contract.todo.DATE));
            time.setText(bundle.getString(Contract.todo.TIME));

            setResult(3, data);
            finishButton.setVisibility(View.VISIBLE);
            //finish();
        }
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
