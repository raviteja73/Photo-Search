package com.chsra.photosearch;

public class History {
    private int _id;
    private String searchTerm,date,time;

    public History(String searchTerm,String date,String time){
        this.searchTerm=searchTerm;
        this.date=date;
        this.time=time;
    }

    public History(){
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "_id=" + _id +
                ", searchTerm='" + searchTerm + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
