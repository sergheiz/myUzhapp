package com.example.cityguide.Common.Place;

public class dbPlace {
    String dbtitle,dbdesc,dbmap,dbimgurl;

    dbPlace()
    {

    }
    public dbPlace(String dbtitle, String dbdesc, String dbmap, String dbimgurl) {
        this.dbtitle = dbtitle;
        this.dbdesc = dbdesc;
        this.dbmap = dbmap;
        this.dbimgurl = dbimgurl;
    }

    public String getDBTitle() {
        return dbtitle;
    }

    public void setDBTitle(String dbtitle) {
        this.dbtitle = dbtitle;
    }

    public String getDBDesc() {
        return dbdesc;
    }

    public void setDBDesc(String dbdesc) {
        this.dbdesc = dbdesc;
    }

    public String getDBMap() {
        return dbmap;
    }

    public void setDBMap(String dbmap) {
        this.dbmap = dbmap;
    }

    public String getDBImgurl() {
        return dbimgurl;
    }

    public void setDBImgurl(String dbimgurl) {
        this.dbimgurl = dbimgurl;
    }

}
