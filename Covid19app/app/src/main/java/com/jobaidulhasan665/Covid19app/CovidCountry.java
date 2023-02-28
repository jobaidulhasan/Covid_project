package com.jobaidulhasan665.Covid19app;

public class CovidCountry {
    String country,total_cases,Flage,recover,active,today_cases,today_death,cretical,cases_per_millio,death_per_millio,total_test;
    int total_death;
    long lastUpdate;
    public CovidCountry(String country,
                        String flage,
                        String recover,
                        String total_cases,
                        String active,
                        String today_cases,
                        String today_death,
                        String cases_per_millio,
                        String death_per_millio,
                        String total_test,
                        String cretical,
                        int total_death,
                        long lastUpdate) {
        this.country = country;
        this.Flage = flage;
        this.total_cases=total_cases;
        this.recover = recover;
        this.active = active;
        this.today_cases = today_cases;
        this.today_death = today_death;
        this.cretical = cretical;
        this.cases_per_millio = cases_per_millio;
        this.death_per_millio = death_per_millio;
        this.total_test = total_test;
        this.total_death = total_death;
        this.lastUpdate=lastUpdate;
    }

    public String getCountry() {
        return country;
    }
    public String getTotal_cases() {
        return total_cases;
    }

    public String getFlage() {
        return Flage;
    }

    public String getRecover() {
        return recover;
    }

    public String getActive() {
        return active;
    }


    public String getToday_cases() {
        return today_cases;
    }

    public String getToday_death() {
        return today_death;
    }

    public String getCretical() {
        return cretical;
    }

    public String getCases_per_millio() {
        return cases_per_millio;
    }

    public String getDeath_per_millio() {
        return death_per_millio;
    }

    public String getTotal_test() {
        return total_test;
    }

    public int getTotal_death() {
        return total_death;
    }
    public long getLastUpdate()
    {
        return lastUpdate;
    }
}
