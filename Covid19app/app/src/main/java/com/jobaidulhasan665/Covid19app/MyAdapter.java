package com.jobaidulhasan665.Covid19app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable {

    List<CovidCountry> covidCountry;
    List<CovidCountry> covidCountriesfull;
    Context context;
    static ClickListener clickListener;
    MyAdapter(List<CovidCountry> covidCountry,Context context)
    {
        this.context=context;
        this.covidCountry=covidCountry;
        covidCountriesfull=new ArrayList<>(covidCountry);
    }
    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.recycle_view_design,parent,false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        CovidCountry covidCountrs=covidCountry.get(position);
        holder.country.setText(covidCountrs.getCountry());
        holder.total_death.setText(Integer.toString(covidCountrs.getTotal_death()));
        Glide.with(context).
                load(covidCountrs.getFlage()).
                apply(new RequestOptions().override(65,35)).into(holder.country_flag);
    }
    @Override
    public int getItemCount() {
        return covidCountry.size();
    }

    @Override
    public Filter getFilter() {
        return covidCountryFilter;
    }
    private Filter covidCountryFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CovidCountry> filterdCovidCountry=new ArrayList<>();

            if(constraint==null || constraint.length()==0)
            {
                filterdCovidCountry.addAll(covidCountriesfull);
            }
            else{
                String FiltrPatter=constraint.toString().toLowerCase().trim();
                for(CovidCountry itemCovidCountry:covidCountriesfull)
                {
                    if(itemCovidCountry.getCountry().toLowerCase().contains(FiltrPatter))
                    {
                        filterdCovidCountry.add(itemCovidCountry);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filterdCovidCountry;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            covidCountry.clear();
            covidCountry.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView country,total_death;
        ImageView country_flag;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            country= itemView.findViewById(R.id.country);
            total_death=itemView.findViewById(R.id.death);
            country_flag=itemView.findViewById(R.id.img);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            clickListener.OnitemClick(getAdapterPosition(),v);
        }
    }

   public interface ClickListener
    {
        void OnitemClick(int position,View view);
    }
    public  void OnItemClickListener(ClickListener clickListener)
    {
        MyAdapter.clickListener=clickListener;
    }
}
