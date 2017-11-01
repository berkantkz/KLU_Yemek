package com.tavsify.berkantkz.klu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

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

        new JSONAsyncTask().execute("https://bitbucket.org/berkantkz/misc/raw/5d1245ac3643455ee2f97bc2593c89e0aabe9747/klu_yemek.json");

        final GridView listView = (GridView) findViewById(R.id.lv);

        adapter = new KLU_Adapter(getApplicationContext(), R.layout.row, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(list.get(position).getStart().replace("00:00:00","") + " " + list.get(position).getTitle())
                        .setMessage(list.get(position).getAciklama() + "\n\n @berkantkz")
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Hakkında")
                    .setMessage("Kullanılan liste Kırklareli Üniversitesi resmi web sayfasından alınmıştır. Uygulama kaynağını bu adreste bulabilirsiniz: https://github.com/berkantkz/KLU_Yemek \n\n - Berkant Korkmaz, berkantkz")
                    .setPositiveButton("TAMAM",null)
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }
}
