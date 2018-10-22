package com.example.jwho_.atmapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jwho_.atmapp.AtmMain;
import com.example.jwho_.atmapp.R;
import com.example.jwho_.atmapp.ReservationListPrint;

import java.util.ArrayList;

public class ReservationListAdapter extends BaseAdapter {

    private ArrayList<ListVO> listVO = new ArrayList<ListVO>();
    public ReservationListAdapter(){}

    @Override
    public int getCount() {
        return listVO.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
            convertView = inflater.inflate(R.layout.custom_listview,parent,false);
        }

        final TextView task = (TextView) convertView.findViewById(R.id.task);
        TextView srcAccount = (TextView) convertView.findViewById(R.id.srcAccount);
        final TextView desAccount = (TextView) convertView.findViewById(R.id.desAccount);
        TextView money = (TextView) convertView.findViewById(R.id.money);
        Button deleteTaskButton = (Button)convertView.findViewById(R.id.deleteTask);

        ListVO listViewItem = listVO.get(position);

        task.setText(listViewItem.getTask());
        srcAccount.setText(listViewItem.getSrcAccount());
        desAccount.setText(listViewItem.getDesAccount());
        money.setText(String.valueOf(listViewItem.getMoney()));
        deleteTaskButton.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(context,desAccount.getText(),Toast.LENGTH_SHORT).show();
            }
        }));
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return listVO.get(position);
    }

    public void addVO(String task, String srcAccount, String desAccount, int money) {

        ListVO item = new ListVO();

        item.setTask(task);
        item.setSrcAccount(srcAccount);
        item.setDesAccount(desAccount);
        item.setMoney(money);

        listVO.add(item);
    }
}
