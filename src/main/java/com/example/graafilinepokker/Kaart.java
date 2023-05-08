package com.example.graafilinepokker;

public class Kaart implements Comparable<Kaart> {
    private String mast;
    private int tugevus;

    public Kaart(String mast, int tugevus) {
        this.mast = mast;
        this.tugevus = tugevus;
    }

    public String getMast() {
        return mast;
    }

    public int getTugevus() {
        return tugevus;
    }

    @Override
    public int compareTo(Kaart o) {
        return Integer.compare(tugevus, o.tugevus);
    }

    //https://stackoverflow.com/questions/17519019/java-arraylist-indexof-finding-object-index
    public boolean equals(Object o) {
        if(!(o instanceof Kaart)) return false;
        //muudab objekti kaardiks
        Kaart muu = (Kaart) o;
        return (mast == muu.mast && tugevus == muu.tugevus);
    }

    public String toString() {
        String liik = "";
        if (tugevus > 10) {
            switch (tugevus) {
                case 11:
                    liik = "poiss";
                    break;
                case 12:
                    liik = "emand";
                    break;
                case 13:
                    liik = "kuningas";
                    break;
                case 14:
                    liik = "äss";
            }
        } else {
            liik = Integer.toString(tugevus);
        }

        return mast + " " + liik;
    }
}
