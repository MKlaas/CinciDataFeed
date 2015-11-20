package com.example.chilipepper.finalgooglemapsproject;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ListActivity {

    private static String url = "https://data.cincinnati-oh.gov/resource/rg6p-b3h3.json";

    private static final String inspectionType = "insp_subtype";
    private static final String violationDescr = "violation_description";
    private static final String businessName = "business_name";

    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();

    ListView lv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        new ProgressTask(MainActivity.this).execute();
    }


    private class ProgressTask extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog dialog;

        public ProgressTask(ListActivity activity) {

            Log.i("1", "Called");
            context = activity;
            dialog = new ProgressDialog(context);
        }

        private Context context;

        protected void onPreExecute() {
            this.dialog.setMessage("Progress start");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            ListAdapter adapter = new SimpleAdapter(context, jsonlist, R.layout.list_activity, new String[] {inspectionType, violationDescr, businessName}, new int[] { R.id.inspectionType, R.id.violationDescr, R.id.businessName });
            setListAdapter(adapter);
            lv = getListView();

        }

        protected Boolean doInBackground(final String... args) {

            JSONParser jParser = new JSONParser();
            JSONArray json = jParser.getJSONFromUrl(url);

            for (int i = 0; i < json.length(); i++) {

                try {
                    JSONObject c = json.getJSONObject(i);
                    String vinspectionType = c.getString(inspectionType);

                    String vviolationDescr = c.getString(violationDescr);
                    String vbusinessName = c.getString(businessName);

                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put(inspectionType, vinspectionType);
                    map.put(violationDescr, vviolationDescr);
                    map.put(businessName, vbusinessName);


                    jsonlist.add(map);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            return null;

        }

    }



}