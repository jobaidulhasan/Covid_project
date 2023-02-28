package com.jobaidulhasan665.Covid19app;


import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
public class Home_fragment extends Fragment  {
    private  DataBase dataBase;

    private TextView total_confirmed,total_death,total_recoverd,total_active,today_total_confirmed,today_tota_death,lastUpdate;
    private  ProgressBar process;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_home_fragment, container, false);


        /////////////Add intersting ads----------------------------------------

        getActivity().setTitle("Home");
        total_confirmed=root.findViewById(R.id.total_confirmed);
        lastUpdate=root.findViewById(R.id.lastUpdate);
        total_death=root.findViewById(R.id.total_deaths);
        total_recoverd=root.findViewById(R.id.total_recovered);
        total_active=root.findViewById(R.id.total_active);
        today_tota_death=root.findViewById(R.id.today_death);
        today_total_confirmed=root.findViewById(R.id.today_confirmed);
        process=root.findViewById(R.id.progress_var);
        dataBase=new DataBase(getActivity());
        dataBase.getWritableDatabase();
        Cursor cursor=dataBase.display();
        if(cursor.getCount()!=0) {
            String set_cases="";
            String set_death="";
            String set_recoverd="";
            String set_active="";
            String set_today_cases="";
            String set_today_death="";
            long set_last_update=0;
            while (cursor.moveToNext()) {
                set_cases=cursor.getString(1);
                set_death=cursor.getString(2);
                set_recoverd=cursor.getString(3);
                set_active=cursor.getString(4);
                set_today_cases=cursor.getString(5);
                set_today_death=cursor.getString(6);
                set_last_update= Long.parseLong(cursor.getString(7));
            }
            total_confirmed.setText(set_cases);
            total_death.setText(set_death);
            total_recoverd.setText(set_recoverd);
            total_active.setText(set_active);
            today_total_confirmed.setText(set_today_cases);
            today_tota_death.setText(set_today_death);
            lastUpdate.setText("Last Updated\n"+DeferenceConutryInformation.getDate(set_last_update));
        }
        getData();
        return root;
    }
    public void getData() {
        RequestQueue queue= Volley.newRequestQueue(getActivity());
        String url="https://corona.lmao.ninja/v2/all";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                process.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject=new JSONObject(response.toString());
                    total_confirmed.setText(jsonObject.getString("cases"));
                    today_total_confirmed.setText(jsonObject.getString("todayCases"));
                    total_death.setText(jsonObject.getString("deaths"));
                    today_tota_death.setText(jsonObject.getString("todayDeaths"));
                    total_recoverd.setText(jsonObject.getString("recovered"));
                    total_active.setText(jsonObject.getString("active"));
                    lastUpdate.setText("Last Updated\n"+DeferenceConutryInformation.getDate(jsonObject.getLong("updated")));
                    ///////////////send Data On Shearpreparance Database----------------->
                    String today_total_cases=jsonObject.getString("todayCases");
                    String total_case=jsonObject.getString("cases");
                    String total_death=jsonObject.getString("deaths");
                    String today_total_death=jsonObject.getString("todayDeaths");
                    String total_recover=jsonObject.getString("recovered");
                    String total_active=jsonObject.getString("active");
                    long last_update=jsonObject.getLong("updated");
                    if(DataBase.home_num==1) {
                        dataBase.insert_data(total_case, total_death, total_recover, total_active, today_total_cases, today_total_death,last_update);
                        DataBase.home_num=DataBase.home_num+1;
                    }
                    else{
                         dataBase.update(total_case,total_death,total_recover,total_active,today_total_cases,today_total_death,last_update);

                    }
                    ////////////////////////////////////////////////////////////////////////
                } catch ( JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(DataBase.home_num==1)
                {
                    AlertDialog.Builder message=new AlertDialog.Builder(getContext());
                    message.setMessage("Please Check Your Internet Connection");
                    message.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog=message.create();
                    alertDialog.show();
                }
                else{
                    process.setVisibility(View.GONE);
                }
            }
        });
        queue.add(stringRequest);
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
