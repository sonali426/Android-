package com.example.hp.blogapplication;


import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


/**
 * Created by HP on 7/13/2017.
 */

public class CustomAdapter extends ArrayAdapter<Product> {

    int groupid;
    ArrayList<Product> records;
    Context context;

    public CustomAdapter(Context context, int vg, int id, ArrayList<Product> records){

        super(context, vg, id, records);
        this.context = context;
        groupid = vg;
        this.records = records;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(groupid, parent, false);
        TextView textname = (TextView)itemView.findViewById(R.id.name);
        textname.setText(records.get(position).getName());
        TextView urlname = (TextView)itemView.findViewById(R.id.url);
        urlname.setText(records.get(position).geturl());
        return itemView;
    }


}
