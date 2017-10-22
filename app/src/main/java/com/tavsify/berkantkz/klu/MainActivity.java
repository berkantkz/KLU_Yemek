package com.tavsify.berkantkz.klu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    static ArrayList<KLU_List> list;
    KLU_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();

        new JSONAsyncTask().execute("https://bitbucket.org/berkantkz/misc/raw/1e941ab3b694e4db3ca21d0defcf82e2d4b5e60d/klu_yemek.json");

        final GridView listView = (GridView) findViewById(R.id.lv);

        adapter = new KLU_Adapter(getApplicationContext(), R.layout.row, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(list.get(position).getStart().replace("00:00:00","") + " " + list.get(position).getTitle())
                        .setMessage(list.get(position).getAciklama())
                        .setPositiveButton("TAMAM",null)
                        .show();
            }
        });

    }

    private class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressBar pb = (ProgressBar) findViewById(R.id.pb);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                if (response.getStatusLine().getStatusCode() == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    JSONArray jarray = new JSONArray(data);

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        KLU_List KLUList = new KLU_List();

                        KLUList.setId(object.getString("id"));
                        KLUList.setTitle(object.getString("title"));
                        KLUList.setAciklama(object.getString("aciklama"));
                        KLUList.setStart(object.getString("start"));

                        list.add(KLUList);

                    }
                    return true;
                }

            } catch ( IOException | JSONException e) {
                e.printStackTrace();
            }
            return false;

        }
        protected void onPostExecute(Boolean result) {
            adapter.notifyDataSetChanged();
            pb.setVisibility(View.GONE);
        }
    }
}
