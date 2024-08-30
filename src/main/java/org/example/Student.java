package org.example;
import java.util.ArrayList;

public class Student {
    private String name;
    private String invatamant;
    private String curs;
    private double medie;
    private ArrayList<String> preferinte = new ArrayList<String>();


    public Student(String name) {
        this.name = name;
    }

    public Student(String name, double medie) {
        this.name = name;
        this.medie = medie;
    }
    
    public String getName() {
        return name;
    }

    public double getMedie() {
        return medie;
    }

    public void setMedie(double medie) {
        this.medie = medie;
    }

    public ArrayList<String> getPreferinte() {
        return preferinte;
    }

    public void setCurs(String curs) {
        this.curs = curs;
    }

    public String getCurs() {
        return curs;
    }

    public void addPreferinta(String preferinta) {
        preferinte.add(preferinta);
    }

    public void setInvatamant(String invatamant) {
        this.invatamant = invatamant;
    }

    public String getInvatamant() {
        return invatamant;
    }
}
