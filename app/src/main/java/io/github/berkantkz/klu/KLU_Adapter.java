package io.github.berkantkz.klu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import io.github.berkantkz.klu.R;

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

        holder.start.setText(klist.get(position).getStart().replace("2018","").replace("00:00:00","").replace("-01-"," Ocak ").replace("-02-", " Şubat ").replace("-03-"," Mart ").replace("-04-", " Nisan ").replace("-05-", " Mayıs ").replace("-06-"," Haziran ").replace("-07-", " Temmuz ").replace("-08-", " Ağustos ").replace("-09-", " Eylül ").replace("-10-" ," Ekim ").replace("-11-", " Kasım ").replace("-12-", " Aralık ").toUpperCase() + " " + klist.get(position).getTitle());

        return v;
    }

    private static class ViewHolder {
        TextView start;
    }
}
