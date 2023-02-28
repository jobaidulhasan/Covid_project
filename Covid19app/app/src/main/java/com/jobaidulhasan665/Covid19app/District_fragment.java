package com.jobaidulhasan665.Covid19app;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class District_fragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private List<CovidCountry> covidCountries;
    public static int setscrolposition;
    private DataBase dataBase;
    private SQLiteDatabase sqLiteDatabase;
    private MyAdapter myAdapter;
    static final  String TAG= ComponentName.class.getSimpleName();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_district_fragment, container, false);
        setHasOptionsMenu(true);
        recyclerView=root.findViewById(R.id.recylview);
        progressBar=root.findViewById(R.id.progres);
        getActivity().setTitle("  Country ");
        dataBase=new DataBase(getActivity());
        sqLiteDatabase=dataBase.getWritableDatabase();
        getDataOnServer();
        return root;
    }

    public void showRecycleView()
    {
        myAdapter=new MyAdapter(covidCountries,getActivity());
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.scrollToPosition(setscrolposition);
        myAdapter.notifyDataSetChanged();

        myAdapter.OnItemClickListener(new MyAdapter.ClickListener() {
            @Override
            public void OnitemClick(int position, View view) {
                CovidCountry covidCountrydetails=covidCountries.get(position);
                setscrolposition=position;
                Intent intent=new Intent(getActivity(),DeferenceConutryInformation.class);
                intent.putExtra("lastUpdate",covidCountrydetails.getLastUpdate());
                intent.putExtra("img",covidCountrydetails.getFlage());
                intent.putExtra("country_name",covidCountrydetails.getCountry());
                intent.putExtra("total_cases",covidCountrydetails.getTotal_cases());
                intent.putExtra("total_death",covidCountrydetails.getTotal_death());
                intent.putExtra("total_active",covidCountrydetails.getActive());
                intent.putExtra("toatl_recoverd",covidCountrydetails.getRecover());
                intent.putExtra("today_cases",covidCountrydetails.getToday_cases());
                intent.putExtra("today_death",covidCountrydetails.getToday_death());
                intent.putExtra("caseOnperMilion",covidCountrydetails.getCases_per_millio());
                intent.putExtra("deathOnPerMilion",covidCountrydetails.getDeath_per_millio());
                intent.putExtra("cretecal",covidCountrydetails.getCretical());
                intent.putExtra("total_test",covidCountrydetails.getTotal_test());
                startActivity(intent);
            }
        });
    }
    private void getDataOnServer() {
        RequestQueue queue=Volley.newRequestQueue(getActivity());
        String url="https://corona.lmao.ninja/v2/countries";
        covidCountries=new ArrayList<>();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                if(response!=null) {
                    Log.e(TAG, "OnRespons" + response);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            JSONObject countryInfo=jsonObject.getJSONObject("countryInfo");
                                covidCountries.add(new CovidCountry(jsonObject.getString("country"),
                                        countryInfo.getString("flag"),
                                        jsonObject.getString("recovered"),
                                        jsonObject.getString("cases"),
                                        jsonObject.getString("active"),
                                        jsonObject.getString("todayCases"),
                                        jsonObject.getString("todayDeaths"),
                                        jsonObject.getString("casesPerOneMillion"),
                                        jsonObject.getString("deathsPerOneMillion"),
                                        jsonObject.getString("tests"),
                                        jsonObject.getString("critical"),
                                        jsonObject.getInt("deaths"),
                                        jsonObject.getLong("updated")
                                        ));
                        }
                        Collections.sort(covidCountries, new Comparator<CovidCountry>() {
                            @Override
                            public int compare(CovidCountry o1, CovidCountry o2) {
                                if(o1.getTotal_death() > o2.getTotal_death())
                                {
                                    return -1;
                                }
                                else if(o1.getTotal_death() < o2.getTotal_death())
                                {
                                    return 1;
                                }
                                else
                                {
                                    return 0;
                                }
                            }
                        });
                        showRecycleView();
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
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
        });
        queue.add(stringRequest);
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu,menu);
        MenuItem searcItem=menu.findItem(R.id.option_searchview);
        SearchView searchView=new SearchView(getActivity());
        searchView.setQueryHint("Search Country Name");
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(myAdapter!=null)
                {
                    myAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        searcItem.setActionView(searchView);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
