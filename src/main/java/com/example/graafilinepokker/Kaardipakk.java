package com.example.graafilinepokker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kaardipakk {
    private List<Kaart> pakk = new ArrayList<>();

    static void genereeriMast(List<Kaart> pakk, String mast) {
        //genereerib masti kõik tugevused
        for (int i = 2; i < 15; i++) {
            pakk.add(new Kaart(mast, i));
        }
    }

    public void genereeriTavaPakk() {
        genereeriMast(pakk, "ärtu");
        genereeriMast(pakk, "poti");
        genereeriMast(pakk, "ruutu");
        genereeriMast(pakk, "risti");
    }

    public int indexOf(Kaart o) {
        return pakk.indexOf(o);
    }

    //kaardi eemalda pakist
    public void eemalda(Kaart kaart) {
        pakk.remove(kaart);
    }
    public List<Kaart> getPakk() {
        return pakk;
    }
}