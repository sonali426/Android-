package com.example.hp.blogapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.ListView;
import android.widget.ArrayAdapter;import android.widget.ListView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.ArrayList;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    Activity context;
    HttpPost httppost;
    StringBuffer buffer;
    HttpResponse response;
    HttpClient httpclient;
    ProgressDialog pd;
    CustomAdapter adapter;
    ListView listview;
    ArrayList<Product> records;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        records = new ArrayList<Product>();
        listview = (ListView) findViewById(R.id.simplelistview);
        adapter = new CustomAdapter(context, R.layout.activity_listview, R.id.name,
                records);
        listview.setAdapter(adapter);

    }

    public void onStart() {

        super.onStart();
        BackTask bt = new BackTask();
        bt.execute();

    }

    private class BackTask extends AsyncTask<Void, Void, Void> {
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
            pd.setTitle("retrieving data");
            pd.setMessage("Please wait");
            pd.setCancelable(true);
            pd.setIndeterminate(true);
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            InputStream is = null;
            String result = "";
            try {
                httpclient = new DefaultHttpClient();
                httppost = new HttpPost("http://192.168.1.100/getitems.php");
                response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();

            } catch (Exception e) {
                if (pd != null)

                    pd.dismiss(); //close the dialog if error occurs

                Log.e("ERROR", e.getMessage());
            }
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line+"\n");
                }
                is.close();
                result = sb.toString();
            }catch(Exception e){
                Log.e("ERROR", "Error converting result "+e.toString());

            }
            try{
                result = result.substring(result.indexOf("["));
                JSONArray jarray = new JSONArray(result);
                for(int i = 0; i < jarray.length(); i++){
                    JSONObject jobject = jarray.getJSONObject(i);
                    Product p = new Product();
                    p.setName(jobject.getString("url"));
                    p.seturl(jobject.getString("title"));
                    records.add(p);
                }

            }catch(Exception e){
                Log.e("ERROR", "Error pasting data "+e.toString());

            }

            return null;
        }
        protected void onPostExecute(Void result){
            if(pd!=null) pd.dismiss(); //close dialog

            Log.e("size", records.size() + "");

            adapter.notifyDataSetChanged();
        }
    }
}

