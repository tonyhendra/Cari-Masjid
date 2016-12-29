package com.tonyhendra.carimasjid;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tony on 12/29/16.
 */

public class jadwaladapter extends ArrayAdapter<jadwal> {
    Context context;
    int layoutResourceId;
    ArrayList<jadwal> data = new ArrayList<jadwal>();

    public jadwaladapter(Context context, int layoutResourceId,ArrayList<jadwal> data)
    {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        jadwalHolder holder = null;

        if (row == null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new jadwalHolder();
            holder.tv_sholat = (TextView) row.findViewById(R.id.textView1);
            holder.tv_jam = (TextView) row.findViewById(R.id.textView2);
            row.setTag(holder);
        }
        else
        {
            holder = (jadwalHolder) row.getTag();
        }
        jadwal user = data.get(position);
        holder.tv_sholat.setText(user.getSholat());
        holder.tv_jam.setText(user.getJam());
//        holder.textLocation.setText(user.getLocation());
//        holder.btnEdit.setOnClickListener(new OnClickListener()
//        {
//
//            @Override
//            public void onClick(View v)
//            {
//                // TODO Auto-generated method stub
//                Log.i("Edit Button Clicked", "**********");
//                Toast.makeText(context, "Edit button Clicked",
//                        Toast.LENGTH_LONG).show();
//            }
//        });
//        holder.btnDelete.setOnClickListener(new OnClickListener()
//        {
//
//            @Override
//            public void onClick(View v)
//            {
//                // TODO Auto-generated method stub
//                Log.i("Delete Button Clicked", "**********");
//                Toast.makeText(context, "Delete button Clicked",
//                        Toast.LENGTH_LONG).show();
//            }
//        });
        return row;

    }

    static class jadwalHolder
    {
        TextView tv_sholat;
        TextView tv_jam;
    }
}
