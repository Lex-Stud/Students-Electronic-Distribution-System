package org.example;
import java.util.*;

public class Curs {
    private String nume;
    private String tip;
    private int capacitate;
    private  ArrayList<Student> studenti = new ArrayList<Student>();
    
    public Curs() {}

    public Curs(String tip, String nume, int capacitate) {
        this.tip = tip;
        this.nume = nume;
        this.capacitate = capacitate;
    }

    public String getNume() {
        return nume;
    }

    public String getTip() {
        return tip;
    }

    public int getCapacitate() {
        return capacitate;
    }

    public ArrayList<Student> getStudenti() {
        return studenti;
    }

    public void setStudenti(ArrayList<Student> studenti) {
        this.studenti = studenti;
    }

    public int getNrStudenti() {
        return studenti.size();
    }
}
