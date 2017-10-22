package com.tavsify.berkantkz.klu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Bilgisayar on 14.10.2017.
 */

class KLU_Adapter extends ArrayAdapter<KLU_List>{
    private ArrayList<KLU_List> klist;
    private LayoutInflater vi;
    private int Resource;

    KLU_Adapter(Context context, int resource, ArrayList<KLU_List> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        klist = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // convert view = design
        View v = convertView;
        ViewHolder holder;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.start = (TextView) v.findViewById(R.id.start);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.start.setText(klist.get(position).getStart().replace("00:00:00", "") + " " + klist.get(position).getTitle());

        return v;
    }

    private static class ViewHolder {
        TextView start;
    }
}
