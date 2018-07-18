package com.example.kashish.lists;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends ArrayAdapter{

    List<Expense> items;
    LayoutInflater inflator;
    public ExpenseAdapter(@NonNull Context context, List<Expense> items) {
        super(context, 0,items);
        inflator= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items=items;

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View output = convertView;
        if(output==null) {
             output=  inflator.inflate(R.layout.row_layout, parent, false);
                    TextView textName = output.findViewById(R.id.name);
                    TextView textage = output.findViewById(R.id.age);
                   TextView textTime=output.findViewById(R.id.expenseTime);
                   TextView textDate=output.findViewById(R.id.expenseDate);
                    ListViewHolder listViewHolder=new ListViewHolder();
                    listViewHolder.tname=textName;
                    listViewHolder.tage=textage;
                    listViewHolder.date=textDate;
                    listViewHolder.time=textTime;

                    output.setTag(listViewHolder);  //??
                    }
               ListViewHolder listViewHolder =(ListViewHolder)output.getTag();
        Expense expense= items.get(position);
        listViewHolder.tname.setText(expense.getName()+"");
        listViewHolder.tage.setText(expense.getAge()+"");
        listViewHolder.date.setText(expense.getDate()+"");
        listViewHolder.time.setText(expense.getTime()+"");


        return output;
    }
}
