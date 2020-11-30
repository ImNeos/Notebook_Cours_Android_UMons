package com.umons.projet.creactif.model;

public class CheckBoxNotesModel
{
    String from_name;
    String itemname;
    boolean isCheckeck;
    long last_date;

    public CheckBoxNotesModel(String from_name, String itemname, boolean isCheckeck, long last_date) {
        this.from_name = from_name;
        this.itemname = itemname;
        this.isCheckeck = isCheckeck;
        this.last_date = last_date;
    }

    public String getFrom_name() {
        return from_name;
    }

    public void setFrom_name(String from_name) {
        this.from_name = from_name;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public boolean isCheckeck() {
        return isCheckeck;
    }

    public void setCheckeck(boolean checkeck) {
        isCheckeck = checkeck;
    }

    public long getLast_date() {
        return last_date;
    }

    public void setLast_date(long last_date) {
        this.last_date = last_date;
    }
}
