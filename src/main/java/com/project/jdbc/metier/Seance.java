package com.project.jdbc.metier;

/**
 * Created by marti on 24/01/2017.
 */
public class Seance {

    private int sid;
    private Cours cid;
    private Formation fid;
    private Salle salle;


    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public Formation getFid() {
        return fid;
    }

    public void setFid(Formation fid) {
        this.fid = fid;
    }

    public Cours getCid() {
        return cid;
    }

    public void setCid(Cours cid) {
        this.cid = cid;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }
}