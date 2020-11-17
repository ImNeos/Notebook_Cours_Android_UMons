package com.umons.projet.creactif.model;

public class NoteListObject
{
    String name;
    String date;
    int type;
    int color;

    public NoteListObject(String name, String date, int type, int color) {
        this.name = name;
        this.date = date;
        this.type = type;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
