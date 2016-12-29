package com.tonyhendra.carimasjid;

/**
 * Created by tony on 12/29/16.
 */

public class jadwal {
    private String sholat;
    private String jam;

    public jadwal(String sholat, String jam){
        super();
        this.sholat = sholat;
        this.jam = jam;
    }

    public void setSholat(String sholat) {
        this.sholat = sholat;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getSholat() {
        return sholat;
    }

    public String getJam() {
        return jam;
    }
}
