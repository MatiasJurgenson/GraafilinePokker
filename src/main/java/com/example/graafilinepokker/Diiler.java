package com.example.graafilinepokker;

import java.util.*;

public class Diiler {
    private List<Kaart[]> kätes = new ArrayList<>();
    private List<Integer> raha = new ArrayList<>();
    private Kaardipakk kaardipakk;
    private List<Kaart> laual = new ArrayList<>();
    private int laualRaha;

    private boolean[] mängus;

    Diiler(Kaardipakk kaardipakk) {
        this.kaardipakk = kaardipakk;
        this.laualRaha = 0;
    }

    public List<Kaart[]> alustaRaundi(int mängijateArv) {
        List<Kaart> pakk = kaardipakk.getPakk();
        Random random = new Random();
        this.mängus = new boolean[mängijateArv];
        // List laud on kahemõõtmeline, kus iga alamjärjend tähistab ühte mängijat.
        // Iga mängija saab kaks kaarti.
        for (int i = 0; i < mängijateArv; i++) {
            Kaart[] mängija = new Kaart[2];
            for (int j = 0; j < 2; j++) {
                Kaart suvaline = pakk.get(random.nextInt(pakk.size()));
                while (this.laual.contains(suvaline)) {
                    suvaline = pakk.get(random.nextInt(pakk.size()));
                }
                mängija[j] = suvaline;
                this.laual.add(suvaline);
            }
            this.kätes.add(mängija);
        }
        //iga mängija saab mängu alguses 1000 ühikut raha.
        for (int i = 0; i < mängijateArv; i++) {
            this.raha.add(2000);
        }

        //lisab iga mängija mängu

        for (int i = 0; i < mängijateArv; i++) {
            this.mängus[i] = true;
        }
        //tagastab listi kõigi mängijatega.
        return kätes;
    }

    public void resetAll() {
        this.kätes.clear();
        this.laual.clear();
    }

    public void fold(int mängija) {
        this.mängus[mängija-1] = false;
    }

    public void lisaLauale(int mitu) {
        //lisab lauale nii mitu kaarti palju vaja
        List<Kaart> pakk = kaardipakk.getPakk();
        Random random = new Random();
        Kaart suvaline = pakk.get(random.nextInt(pakk.size()));
        for (int i = 0; i < mitu; i++) {
            while (this.laual.contains(suvaline)) {
                suvaline = pakk.get(random.nextInt(pakk.size()));
            }
            this.laual.add(suvaline);
            System.out.println("Lauda tuli: " + suvaline);
        }
    }

    public List<Kaart> getLaual() {
        //tagastab laual olevad kaardid
        return laual;
    }

    public void getMängijaKäsi() {
        System.out.print(this.kätes.get(0)[0] + ", " + this.kätes.get(0)[1] + "\n");
    }

    public List<Integer> getRaha() {
        //tagastab mängijate raha.
        return raha;
    }

    public int getLaualRaha() {
        return laualRaha;
    }

    public void lisaLaualeRaha(int raha) {
        this.laualRaha += raha;
    }

    public void panustaRaha(int mängija, int panus) {
        this.lisaLaualeRaha(panus);
        this.raha.set(mängija-1, this.raha.get(mängija-1)-panus);
    }

    public void käed() {
        //väljastab kõikide mängijate käed
        System.out.println("Sinu käsi on: " + Arrays.toString(kätes.get(0)));
        for (int i = 1; i < kätes.size(); i++) {
            System.out.println(i + 1 + ". mängija käes on " + Arrays.toString(kätes.get(i)));
        }
    }

    public List<Kaart> eemaldaDuublid(List<Kaart> temp) {
        for (int k = 0; k < 2; k++) {
            for (int j = 1; j < temp.size() - 1; j++) {
                if (temp.get(j).getTugevus() == temp.get(j + 1).getTugevus() || temp.get(j).getTugevus() == temp.get(j - 1).getTugevus()) {
                    temp.remove(j);
                }
            }
        }
        return temp;
    }

    public List<Kaart> käedLauaga(int mängija) {
        //tagastab antud mängija käe koos laua kaartidega.
        List<Kaart> temp = new ArrayList<>();
        Kaart[] mängijaKäsi = this.kätes.get(mängija);
        for (int i = 0; i < 2; i++) {
            temp.add(mängijaKäsi[i]);
        }
        List<Kaart> laualeTulnudKaardid = (this.laual.subList(8, this.laual.size()));
        for (int i = 0; i < laualeTulnudKaardid.size(); i++) {
            Kaart tempKaart = laualeTulnudKaardid.get(i);
            temp.add(tempKaart);
        }
        Collections.sort(temp);
        return temp;
    }

    public List<Boolean> pair() {
        //käib kõik mängijad ükshaaval läbi
        //teen tühja väärtuste järjendi, kui ei ole paari on false
        // kui on siis true
        List<Boolean> väärtused = new ArrayList<>();

        for (int i = 0; i < this.kätes.size(); i++) {
            if (!this.mängus[i]) {
                väärtused.add(false);
                continue;
            }
            List<Kaart> temp = this.käedLauaga(i);
            Collections.sort(temp);
            //kontrollin kas on kaks ühesugust kaarti
            for (int j = 0; j < temp.size()-1; j++) {
                if (temp.get(j).getTugevus() == temp.get(j+1).getTugevus()) {
                    väärtused.add(true);
                    break;
                }
                if (j == temp.size()-2) {
                    väärtused.add(false);
                }
            }
        }
        return väärtused;
    }

    public List<Boolean> twoPair() {
        List<Boolean> väärtused = new ArrayList<>();
        //kontrolling kas on üks paar
        List<Boolean> kasOnÜks = this.pair();
        //kontrollin kas on kaks paari
        for (int i = 0; i < kasOnÜks.size(); i++) {
            if (!this.mängus[i]) {
                väärtused.add(false);
                continue;
            }
            if (!kasOnÜks.get(i)) {
                väärtused.add(false);
            } else {
                List<Kaart> temp = this.käedLauaga(i);
                Collections.sort(temp);
                //kui on üks paar kontrollin kas on teine paar
                //eemaldan esimese
                for (int j = 0; j < temp.size()-1; j++) {
                    if (temp.get(j).getTugevus() == temp.get(j+1).getTugevus()) {
                        temp.remove(j);
                        temp.remove(j);
                        break;
                    }
                }
                //kontrollin kas allesjäänudest on paari
                for (int j = 0; j < temp.size()-1; j++) {
                    if (temp.get(j).getTugevus() == temp.get(j+1).getTugevus()) {
                        väärtused.add(true);
                        break;
                    }
                    if (j == temp.size()-2) {
                        väärtused.add(false);
                    }
                }
            }
        }
        return väärtused;
    }

    public List<Boolean>  threeOfAKind() {
        List<Boolean> väärtused = new ArrayList<>();
        for (int i = 0; i < this.kätes.size(); i++) {
            if (!this.mängus[i]) {
                väärtused.add(false);
                continue;
            }
            List<Kaart> temp = this.käedLauaga(i);
            for (int j = 0; j < temp.size()-2; j++) {
                if(temp.get(j).getTugevus() == temp.get(j+1).getTugevus() & temp.get(j+1).getTugevus() == temp.get(j+2).getTugevus()) {
                    väärtused.add(true);
                    break;
                }
                if (j == temp.size()-3) {
                    väärtused.add(false);
                }
            }
        }
        return väärtused;
    }

    public List<Boolean> fourOfAKind() {
        List<Boolean> väärtused = new ArrayList<>();
        for (int i = 0; i < this.kätes.size(); i++) {
            if (!this.mängus[i]) {
                väärtused.add(false);
                continue;
            }
            List<Kaart> temp = this.käedLauaga(i);
            for (int j = 0; j < temp.size()-3; j++) {
                if(temp.get(j).getTugevus() == temp.get(j+1).getTugevus()
                        & temp.get(j+1).getTugevus() == temp.get(j+2).getTugevus()
                        & temp.get(j+2).getTugevus() == temp.get(j+3).getTugevus())
                {
                    väärtused.add(true);
                    break;
                }
                if (j == temp.size()-4) {
                    väärtused.add(false);
                }
            }
        }
        return väärtused;
    }

    public List<Boolean> straight() {
        List<Boolean> väärtused = new ArrayList<>();
        for (int i = 0; i < this.kätes.size(); i++) {
            if (!this.mängus[i]) {
                väärtused.add(false);
                continue;
            }
            List<Kaart> temp = this.käedLauaga(i);
            temp = this.eemaldaDuublid(temp);
            if (temp.size()<5) {väärtused.add(false); continue;}
            for (int j = 0; j < temp.size()-4; j++) {
                if( temp.get(j).getTugevus() == temp.get(j+1).getTugevus()-1 &
                temp.get(j+2).getTugevus()-2 == temp.get(j).getTugevus() &
                temp.get(j+3).getTugevus()-3 == temp.get(j).getTugevus() &
                temp.get(j+4).getTugevus()-4 == temp.get(j).getTugevus() ) {
                    väärtused.add(true);
                    break;
                }
                if(j == temp.size()-5) {
                    väärtused.add(false);
                }
            }
        }
        return väärtused;
    }

    public List<Boolean> flush(){
        List<Boolean> väärtused = new ArrayList<>();
        HashMap<String, Integer> mastid = new HashMap<>();
        for (int i = 0; i < this.kätes.size(); i++) {
            if (!this.mängus[i]) {
                väärtused.add(false);
                continue;
            }
            mastid.put("ärtu", 0);
            mastid.put("ruutu", 0);
            mastid.put("risti", 0);
            mastid.put("poti", 0);
            List<Kaart> temp = this.käedLauaga(i);
            for (int j = 0; j < this.käedLauaga(i).size(); j++) {
                mastid.put(temp.get(j).getMast(), mastid.get(temp.get(j).getMast())+1);
            }
            if (mastid.get("ärtu") >= 5) {
                väärtused.add(true);
            } else if (mastid.get("ruutu") >= 5) {
                väärtused.add(true);
            } else if (mastid.get("poti") >= 5) {
                väärtused.add(true);
            } else if (mastid.get("risti") >= 5) {
                väärtused.add(true);
            } else {väärtused.add(false);}
        }
        return väärtused;
    }
    // tagastab mängijate suurimate kaartide tugevused. Saab kasutada kickerina.
    public List<Integer> highCard() {
        List<Integer> suurimad = new ArrayList<>();
        for (int i = 0; i < this.kätes.size(); i++) {
            if (!this.mängus[i]) {
                suurimad.add(0);
                continue;
            }
            List<Kaart> temp = this.käedLauaga(i);
            Collections.sort(temp);
            suurimad.add(temp.get(temp.size()-1).getTugevus());
        }
        return suurimad;
    }

    public List<Boolean> fullHouse() {
        List<Boolean> väärtused = new ArrayList<>();
        for (int i = 0; i < this.kätes.size(); i++) {
            if (!this.mängus[i]) {
                väärtused.add(false);
                continue;
            }
            List<Kaart> temp = this.käedLauaga(i);
            if (this.threeOfAKind().get(i)) {
                //eemaldan kolmiku
                for (int j = 0; j < temp.size()-3; j++) {
                    if (temp.get(j).getTugevus() == temp.get(j+1).getTugevus() &
                    temp.get(j+1).getTugevus() == temp.get(j+2).getTugevus()) {
                        for (int k = 0; k < 3; k++) {
                            temp.remove(j);
                        }
                        break;
                    }
                }
                if(this.twoPair().get(i)) {
                    väärtused.add(true);
                } else { väärtused.add(false);}
            } else {väärtused.add(false);}
        }
        return väärtused;
    }
    private List<Kaart> eemaldaErinevadAbi(List<Kaart> temp) {
        HashMap<String, Integer> mastid = new HashMap<>();
        mastid.put("ärtu", 0);
        mastid.put("ruutu", 0);
        mastid.put("risti", 0);
        mastid.put("poti", 0);
        for (int i = 0; i < temp.size(); i++) {
            mastid.put(temp.get(i).getMast(), mastid.get(temp.get(i).getMast()) + 1);
        }
        if (mastid.get("ärtu") >= 5) {
            for (int j = 0; j < 2; j++) {
                for (int i = 0; i < temp.size(); i++) {
                    if (!temp.get(i).getMast().equals("ärtu")) {
                        temp.remove(i);
                    }
                }
            }
            return temp;
        }
        else if (mastid.get("risti") >= 5) {
            for (int j = 0; j < 2; j++) {
                for (int i = 0; i < temp.size(); i++) {
                    if (!temp.get(i).getMast().equals("risti")) {
                        temp.remove(i);
                    }
                }
            }
            return temp;
        }
        else if (mastid.get("poti") >= 5) {
            for (int j = 0; j < 2; j++) {
                for (int i = 0; i < temp.size(); i++) {
                    if (!temp.get(i).getMast().equals("poti")) {
                        temp.remove(i);
                    }
                }
            }
            return temp;
        }
        else if (mastid.get("ruutu") >= 5) {
            for (int j = 0; j < 2; j++) {
                for (int i = 0; i < temp.size(); i++) {
                    if (!temp.get(i).getMast().equals("ruutu")) {
                        temp.remove(i);
                    }
                }
            }
            return temp;
        }
        return null;
    }
    public List<Boolean> straightFlush() {
        List<Boolean> väärtused = new ArrayList<>();
        for (int i = 0; i < this.kätes.size(); i++) {
            if (!this.mängus[i]) {
                väärtused.add(false);
                continue;
            }
            List<Kaart> temp = this.käedLauaga(i);
            // kontrollin kas on yldse mõtet jamada
            if (this.straight().get(i) & this.flush().get(i)) {
                //kontrollin kas eemaldades kõik peale flushi on veel viis kaarti
                if (this.eemaldaErinevadAbi(temp) == null) {
                    System.out.println("ERROR01 midagi läks nihu.");
                } else {
                    if(this.eemaldaErinevadAbi(temp).size()>= 5) {
                        //kui on alles üle nelja kaardi.
                        //eemaldan allesjäänud duublid (kui neid on).
                        temp = this.eemaldaDuublid(temp);
                        if (temp.size()>=5) {
                            //lõpuks kontrollin kas allesjäänud kaardid on õiges järjestuses.
                            for (int j = 0; j < temp.size()-1; j++) {
                                if (temp.get(j).getTugevus() == temp.get(j+1).getTugevus()-1) {
                                    if (j == temp.size()-2) {
                                        väärtused.add(true);
                                        break;
                                    }
                                    continue;
                                }
                                väärtused.add(false);
                                break;
                            }
                        } else {väärtused.add(false);}
                    } else {väärtused.add(false);}
                }
            } else { väärtused.add(false);}
        }
        return väärtused;
    }

    public List<Boolean> royalStraightFlush() {
        List<Boolean> väärtused = new ArrayList<>();
        for (int i = 0; i < this.kätes.size(); i++) {
            if (!this.mängus[i]) {
                väärtused.add(false);
                continue;
            }
            List<Kaart> temp = this.käedLauaga(i);
            //kontrollin kas on on flush ja straight.
            if (this.straightFlush().get(i)) {
                //kui on siis eemaldan kõik kaardid mis ei ole straighti mastist, ning
                //eemaldan kõik duublid.
                temp = this.eemaldaErinevadAbi(temp);
                temp = this.eemaldaDuublid(temp);
                List<Integer> tugevused = new ArrayList<>();
                for (int j = 0; j < temp.size(); j++) {
                    tugevused.add(temp.get(j).getTugevus());
                }
                //kui on vähem kui vajaminev arv kaarte katkestan.
                if (temp.size() < 5) {
                    väärtused.add(false);
                    continue;
                } else {
                    //kontrollin kas suurim kaart on äss ja kas ta on ikka tempis.
                    if (this.highCard().get(i) == 14 & tugevused.contains(14)) {
                        väärtused.add(true);
                    } else {
                        väärtused.add(false);
                    }
                }
            } else {väärtused.add(false);}
        }
        return väärtused;
    }
}

