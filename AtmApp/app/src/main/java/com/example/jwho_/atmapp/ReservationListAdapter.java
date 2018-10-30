package com.example.jwho_.atmapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;


public class ReservationListAdapter extends BaseAdapter {
    private ArrayList<ReservationWork> listVO = new ArrayList<>();
    private String url = "http://35.200.117.1:8080/control.jsp";
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

        TextView no         = convertView.findViewById(R.id.no);
        TextView type       = convertView.findViewById(R.id.task);
        TextView srcAccount = convertView.findViewById(R.id.srcAccount);
        TextView dstAccount = convertView.findViewById(R.id.desAccount);
        TextView amount     = convertView.findViewById(R.id.money);
        TextView carNumber  = convertView.findViewById(R.id.carNumber);

        ReservationWork listViewItem = listVO.get(position);

        no          .setText(String.valueOf(listViewItem.getNo()));
        type        .setText(listViewItem.getType());
        srcAccount  .setText(listViewItem.getSrcAccount());
        dstAccount  .setText(listViewItem.getDstAccount());
        amount      .setText(listViewItem.getAmount());
        carNumber   .setText(listViewItem.getCarNumber());

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

    public void addVO(ReservationWork work) {
        listVO.add(work);
    }

    public String getNo(int position) { return listVO.get(position).getNo(); }
}


