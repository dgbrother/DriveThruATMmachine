package com.example.jwho_.atmapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.jwho_.atmapp.R;
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

        TextView task = (TextView) convertView.findViewById(R.id.task);
        TextView account = (TextView) convertView.findViewById(R.id.account);
        TextView money = (TextView) convertView.findViewById(R.id.money);

        ListVO listViewItem = listVO.get(position);

        task.setText(listViewItem.getTitle());
        account.setText(listViewItem.getContext());
        money.setText(listViewItem.getMoney());
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

    public void addVO(String task, String account, int money) {

        ListVO item = new ListVO();

        item.setTitle(task);
        item.setContext(account);
        item.setMoney(money);

        listVO.add(item);
    }
}
