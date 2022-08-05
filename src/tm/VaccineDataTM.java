package tm;

import javafx.scene.Scene;

public class VaccineDataTM {

    private String vaccine;
    private String dose;
    private String date;
    private String moh;

    public VaccineDataTM(String vaccine) {
    }

    public VaccineDataTM(String vaccine, String dose, String date, String moh) {
        this.vaccine = vaccine;
        this.dose = dose;
        this.date = date;
        this.moh = moh;
    }

    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMoh() {
        return moh;
    }

    public void setMoh(String moh) {
        this.moh = moh;
    }
}
