package database;

import java.util.Calendar;

/**
 * Created by Milica on 12-Feb-17.
 */

// This is  a model and it contains all the data needed to calculate

public class Visit {

    private String visitId;
    private long entryDate;
    private long exitDate;
    private String country;
    private String desc;
    // private boolean stillInCountry;

    public Visit(String visitId, long entryDate, long exitDate, String country, String desc){
        this.setVisitId(visitId);
        this.setEntryDate(entryDate);
        this.setExitDate(exitDate);
        this.setCountry(country);
        this.setDesc(desc);
    }

    public Visit(){

    }


    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public long getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(long entryDate) {
        this.entryDate = entryDate;
    }

    public long getExitDate() {
        return exitDate;
    }

    public void setExitDate(long exitDate) {
        this.exitDate = exitDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
