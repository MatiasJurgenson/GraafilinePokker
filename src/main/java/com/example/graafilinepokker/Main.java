package com.example.graafilinepokker;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URI;
import java.util.*;
import java.util.List;

public class Main extends Application {

    boolean folditud = false;
    int bettitud = 0;
    int mituRaundiOnJäänud = 3;
    public int blind = 1;
    public Kaardipakk kaardipakk = new Kaardipakk();
    public Diiler diiler = new Diiler(kaardipakk);
    List<Integer> mängijateRahad = diiler.getRaha();
    RahaKast mängijaRaha = new RahaKast();
    RahaKast arvutiRaha2 = new RahaKast();
    RahaKast arvutiRaha1 = new RahaKast();
    RahaKast arvutiRaha3 = new RahaKast();
    RahaKast arvutiRaha4 = new RahaKast();
    RahaKast paljuMängusOn = new RahaKast();
    public List<RahaKast> rahaKastid = new ArrayList<>();

    boolean kasMängOnLäbi;

    public int viimaneBet = 200;

    Random random = new Random();

    //vajalikud asjad (moodle järgi)
    //
    //todo Programm peaks olema kasutatav ilma eriliste eelteadmisteta
    //todo Programm peab mingid andmed kirjutama faili ja neid failist ka lugema
    //todo Erinditöötluse abil tagada, et toimuks mõistlik reageerimine (vähemalt mõnedele) kasutaja ekslikele tegevustele
    //todo Programmi akna suurust muutes peab kuvatu mõistlikult muutuma.
    public void panustaBlindid(int smallBlindMängija, int bigBlindMängija) {
        System.out.println("Small blind: " + blind + ". mängija, panus: 100");
        diiler.panustaRaha(blind,100);
        rahaKastid.get(blind-1).paljuRahaOn.setText(String.valueOf(diiler.getMängijaRaha(1)));
        System.out.println("Big blind: " + (blind+1) + ".mängija, panus: 200");
        diiler.panustaRaha(blind+1, 200);
        rahaKastid.get(blind).setRaha(diiler.getMängijaRaha(blind));
        paljuMängusOn.setRaha(diiler.getLaualRaha());
    }


    @Override
    public void start(Stage pealava) {
        rahaKastid.add(mängijaRaha);
        rahaKastid.add(arvutiRaha1);
        rahaKastid.add(arvutiRaha2);
        rahaKastid.add(arvutiRaha3);
        rahaKastid.add(arvutiRaha4);
        rahaKastid.add(paljuMängusOn);
        Pane root = new Pane();
        Scene scene = new Scene(root);

        pealava.setHeight(600);
        pealava.setWidth(1000);
        pealava.setTitle("easyPokker");

        //nupud
        Rectangle backRuut = new Rectangle(300,75);
        backRuut.setArcWidth(5.0);
        backRuut.setArcHeight(5.0);
        backRuut.setX(350);
        backRuut.setY(175);
        backRuut.setFill(Color.WHITESMOKE);
        Label alusta = new Label("Alusta");
        alusta.setFont(new Font("Comic Sans MS",40));
        alusta.setLayoutY(180);
        alusta.setLayoutX(440);

        Rectangle backRuut2 = new Rectangle(300,75);
        backRuut2.setArcWidth(5.0);
        backRuut2.setArcHeight(5.0);
        backRuut2.setX(350);
        backRuut2.setY(270);
        backRuut2.setFill(Color.WHITESMOKE);
        Label juhised = new Label("Juhised");
        juhised.setFont(new Font("Comic Sans MS",40));
        juhised.setLayoutX(420);
        juhised.setLayoutY(275);

        Rectangle backRuut3 = new Rectangle(300,75);
        backRuut3.setArcWidth(5.0);
        backRuut3.setArcHeight(5.0);
        backRuut3.setX(350);
        backRuut3.setY(365);
        backRuut3.setFill(Color.WHITESMOKE);
        Label quit = new Label("Lahku");
        quit.setFont(new Font("Comic Sans MS",40));
        quit.setLayoutY(370);
        quit.setLayoutX(440);

        Label pealkiri = new Label("Pokker");
        pealkiri.setTextFill(Color.FLORALWHITE);
        pealkiri.setFont(new Font("Comic Sans MS",100));
        pealkiri.setLayoutY(25);
        pealkiri.setLayoutX(350);

        root.getChildren().addAll(pealkiri,backRuut,backRuut2,backRuut3,alusta, quit, juhised);;
        root.setBackground(new Background(
                new BackgroundFill(Color.DARKGREEN, new CornerRadii(0), new Insets(0))));
        pealava.setScene(scene);
        pealava.show();
        pealava.setResizable(false);

        //kui vajutadakse nuppu siis toimub vahetus (kordinaatidega tehes tulevad buggid, nt: lauale vajutades sai mängust lahkuda)
        alusta.setOnMousePressed(event -> {
                // siit edasi läheb mänguekraan ja loogika
                root.getChildren().removeAll(pealkiri,backRuut,backRuut2,backRuut3,alusta, quit, juhised);
                Rectangle lauaRing = new Rectangle(600,300);
                lauaRing.setArcWidth(300);
                lauaRing.setArcHeight(300);
                lauaRing.setX(200);
                lauaRing.setY(140);
                lauaRing.setFill(Color.WHITESMOKE);
                Rectangle lauaRing2 = new Rectangle(575,275);
                lauaRing2.setArcWidth(285);
                lauaRing2.setArcHeight(285);
                lauaRing2.setX(212.5);
                lauaRing2.setY(152.5);
                lauaRing2.setFill(Color.ROSYBROWN);

                //esimene mängija
                KaardiKujutis kaart1 = new KaardiKujutis();
                kaart1.setPaiknevus(100,100);
                KaardiKujutis kaart2 = new KaardiKujutis();
                kaart2.setPaiknevus(15,100);

                //teine mängija
                KaardiKujutis kaart3 = new KaardiKujutis();
                kaart3.setPaiknevus(815,100);
                KaardiKujutis kaart4 = new KaardiKujutis();
                kaart4.setPaiknevus(900,100);

                //kolmas mängija
                KaardiKujutis kaart5 = new KaardiKujutis();
                kaart5.setPaiknevus(815,400);
                KaardiKujutis kaart6 = new KaardiKujutis();
                kaart6.setPaiknevus(900,400);

                //neljas mängija
                KaardiKujutis kaart7 = new KaardiKujutis();
                kaart7.setPaiknevus(100,400);
                KaardiKujutis kaart8 = new KaardiKujutis();
                kaart8.setPaiknevus(15,400);

                //inimese kaardid
                KaardiKujutis kaart9 = new KaardiKujutis();
                kaart9.setPaiknevus(420,450);
                KaardiKujutis kaart10 = new KaardiKujutis();
                kaart10.setPaiknevus(505,450);

                List<KaardiKujutis> kaardiKujutised = new ArrayList<>(List.of(new KaardiKujutis[]{kaart9, kaart10, kaart1, kaart2, kaart3, kaart4, kaart5, kaart6, kaart7, kaart8}));

                //rahakastid
                mängijaRaha.setPaiknevus(435,350);
                mängijaRaha.rahaRingTaust.setFill(Color.DARKCYAN);
                arvutiRaha1.setPaiknevus(235,65);
                arvutiRaha2.setPaiknevus(635,65);
                arvutiRaha3.setPaiknevus(235,455);
                arvutiRaha4.setPaiknevus(635,455);
                paljuMängusOn.setPaiknevus(435,275);
                paljuMängusOn.rahaRingTaust.setFill(Color.IVORY);

                //call bet fold
                Label call = new Label("Call");
                call.setFont(new Font("Comic Sans MS",30));
                Rectangle callBack = new Rectangle(100,50);
                callBack.setFill(Color.BLANCHEDALMOND);
                call.setLayoutX(425);
                call.setLayoutY(75);
                callBack.setLayoutX(400);
                callBack.setLayoutY(72);
                callBack.setArcWidth(50);
                callBack.setArcHeight(50);

                Label bet = new Label("Bet");
                bet.setFont(new Font("Comic Sans MS",30));
                Rectangle betBack = new Rectangle(100,50);
                betBack.setFill(Color.BLANCHEDALMOND);
                bet.setLayoutX(530);
                bet.setLayoutY(75);
                betBack.setLayoutX(505);
                betBack.setLayoutY(72);
                betBack.setArcWidth(50);
                betBack.setArcHeight(50);

                Label fold = new Label("Fold");
                fold.setFont(new Font("Comic Sans MS",30));
                Rectangle foldBack = new Rectangle(100,50);
                foldBack.setFill(Color.BLANCHEDALMOND);
                fold.setLayoutX(470);
                fold.setLayoutY(20);
                foldBack.setLayoutX(450);
                foldBack.setLayoutY(18);
                foldBack.setArcWidth(50);
                foldBack.setArcHeight(50);

                //giga objektide kogum
                root.getChildren().addAll(lauaRing, lauaRing2, kaart1.kaardiTaust, kaart1.kaardiSisemus, kaart2.kaardiTaust, kaart2.kaardiSisemus,
                        kaart3.kaardiTaust, kaart3.kaardiSisemus, kaart4.kaardiTaust, kaart4.kaardiSisemus, kaart5.kaardiTaust, kaart5.kaardiSisemus,
                        kaart6.kaardiTaust, kaart6.kaardiSisemus, kaart7.kaardiTaust, kaart7.kaardiSisemus, kaart8.kaardiTaust, kaart8.kaardiSisemus,
                        kaart9.kaardiTaust, kaart9.kaardiSisemus, kaart10.kaardiTaust, kaart10.kaardiSisemus, kaart9.tekst, kaart10.tekst, kaart8.tekst,
                        kaart7.tekst, kaart6.tekst, kaart5.tekst, kaart4.tekst, kaart3.tekst, kaart2.tekst, kaart1.tekst,
                        mängijaRaha.rahaRingKast, mängijaRaha.rahaRingTaust, mängijaRaha.rahaRingKesk, mängijaRaha.raha, mängijaRaha.paljuRahaOn,
                        arvutiRaha1.rahaRingKast, arvutiRaha1.rahaRingTaust, arvutiRaha1.rahaRingKesk, arvutiRaha1.raha, arvutiRaha1.paljuRahaOn,
                        arvutiRaha2.rahaRingKast, arvutiRaha2.rahaRingTaust, arvutiRaha2.rahaRingKesk, arvutiRaha2.raha, arvutiRaha2.paljuRahaOn,
                        arvutiRaha3.rahaRingKast, arvutiRaha3.rahaRingTaust, arvutiRaha3.rahaRingKesk, arvutiRaha3.raha, arvutiRaha3.paljuRahaOn,
                        arvutiRaha4.rahaRingKast, arvutiRaha4.rahaRingTaust, arvutiRaha4.rahaRingKesk, arvutiRaha4.raha, arvutiRaha4.paljuRahaOn,
                        paljuMängusOn.rahaRingKast, paljuMängusOn.rahaRingTaust, paljuMängusOn.rahaRingKesk, paljuMängusOn.raha, paljuMängusOn.paljuRahaOn,
                        callBack, call,foldBack, fold, betBack, bet);

                kaardipakk.genereeriTavaPakk();
                diiler.alustaRaundi(5);

                //panen mängija kaardid ümberpööratud asendisse.
                //teised keerab kui raund on läbi, keegi paneb foldi.
                String mängijaKaardid = diiler.getMängijaKäsi();
                System.out.println(mängijaKaardid);
                String[] esimene = mängijaKaardid.split(" ");
                System.out.println(Arrays.toString(esimene));
                kaart9.pööraÜmber(esimene[0].toUpperCase()+" "+esimene[1].substring(0,1).toUpperCase());
                kaart10.pööraÜmber(esimene[2].toUpperCase()+" "+esimene[3].substring(0,1).toUpperCase());

                //lisan kõigi raha
                System.out.println(mängijateRahad);

                //mängijaraha
                mängijaRaha.setRaha(mängijateRahad.get(0));
                //1. arvuti raha
                arvutiRaha1.setRaha(mängijateRahad.get(1));
                //2. arvuti raha
                arvutiRaha2.setRaha(mängijateRahad.get(2));
                //3. arvuti raha
                arvutiRaha3.setRaha(mängijateRahad.get(3));
                //4. arvuti raha
                arvutiRaha4.setRaha(mängijateRahad.get(4));

                //panustan blindid
                panustaBlindid(1,2);
                

                //while true
            //spikker
            //KaardiKujutised listis on mängija index 8-9;
            //Rahakastis on mängija index 0;
            //mängijateRahad on mängija index 0;
            //diiler.mängus on mängioja index 0;

            fold.setOnMousePressed(eventFold -> {
                if (kasMängOnLäbi) {
                    return;
                }
                int viimaneMängija = 0;
                folditud = true;
                diiler.fold(1);
                //teeb nii mitu raundi mitu jäänud on
                while (mituRaundiOnJäänud != 0 && diiler.kasMängusOnKedagi()) {
                    //igale mängijale valib tegevuse ja teostab selle.
                    for (int i = 0; i < diiler.mängus.length; i++) {
                        int mituMängus = 0;
                        for (int k = 0; k < diiler.mängus.length; k++) {
                            if (diiler.mängus[k]) mituMängus += 1;
                        }
                        //kui antud mängija on mängus veel.
                        if (diiler.mängus[i]) {
                            int foldCallRaise = random.nextInt(3);
                            if (foldCallRaise == 0 && mituMängus == 1) {
                                foldCallRaise = 1;
                            }
                            //kui fold
                            if (foldCallRaise == 0) {
                                viimaneMängija = i;
                                diiler.fold(i+1);
                            } else if (foldCallRaise == 1) {
                                //kui on Call.
                                if (viimaneBet > diiler.getMängijaRaha(i+1)) {
                                    diiler.panustaRaha(i+1, diiler.getMängijaRaha(i+1));
                                    continue;
                                }
                                diiler.panustaRaha(i+1,viimaneBet);
                            } else if (foldCallRaise == 2) {
                                int panus = random.nextInt((200+viimaneBet - 200) + 1) + 200;
                                while (panus > diiler.getMängijaRaha(i+1)) {
                                    panus = random.nextInt((200+viimaneBet - 200) + 1) + 200;
                                }
                                diiler.panustaRaha(i+1, panus);
                                viimaneBet = panus;
                            }
                        }
                    }
                    mituRaundiOnJäänud -= 1;
                }

                List<Boolean> kesVõitis = kontrollija(diiler);
                if (kesVõitis == null) {
                    diiler.mängus[viimaneMängija] = true;
                    kesVõitis = kontrollija(diiler);
                }
                for (int i = 0; i < kesVõitis.size(); i++) {
                    if (kesVõitis.get(i)) {
                        diiler.lisaMängijaleVõidud(i);
                    }
                    kasMängOnLäbi = true;
                }
                diiler.lisaLauale(5-diiler.getLaual().size());
                KaardiKujutis lauaKaart1 = new KaardiKujutis();
                lauaKaart1.setPaiknevus(295,166);
                KaardiKujutis lauaKaart2 = new KaardiKujutis();
                lauaKaart2.setPaiknevus(380,166);
                KaardiKujutis lauaKaart3 = new KaardiKujutis();
                lauaKaart3.setPaiknevus(465,166);
                KaardiKujutis lauaKaart4 = new KaardiKujutis();
                lauaKaart4.setPaiknevus(550,166);
                KaardiKujutis lauaKaart5 = new KaardiKujutis();
                lauaKaart5.setPaiknevus(635,166);

                root.getChildren().addAll(lauaKaart1.kaardiTaust,lauaKaart1.kaardiSisemus,lauaKaart2.kaardiTaust,lauaKaart2.kaardiSisemus,
                        lauaKaart3.kaardiTaust,lauaKaart3.kaardiSisemus,lauaKaart4.kaardiTaust,lauaKaart4.kaardiSisemus, lauaKaart5.kaardiTaust,
                        lauaKaart5.kaardiSisemus,lauaKaart1.tekst,lauaKaart2.tekst,lauaKaart3.tekst,lauaKaart4.tekst,lauaKaart5.tekst);

                List<Kaart> laual = diiler.getLaual();
                String[] esimeneLaual = laual.get(laual.size() - 5).toString().split(" ");
                lauaKaart1.pööraÜmber(esimeneLaual[0].toUpperCase()+" "+esimeneLaual[1].substring(0,1).toUpperCase());
                String[] teineLaual = laual.get(laual.size() - 4).toString().split(" ");
                lauaKaart2.pööraÜmber(teineLaual[0].toUpperCase()+" "+teineLaual[1].substring(0,1).toUpperCase());
                String[] kolmasLaual = laual.get(laual.size() - 3).toString().split(" ");
                lauaKaart3.pööraÜmber(kolmasLaual[0].toUpperCase()+" "+kolmasLaual[1].substring(0,1).toUpperCase());
                String[] neljasLaual = laual.get(laual.size() - 2).toString().split(" ");
                lauaKaart4.pööraÜmber(neljasLaual[0].toUpperCase()+" "+neljasLaual[1].substring(0,1).toUpperCase());
                String[] viiesLaual = laual.get(laual.size() - 1).toString().split(" ");
                lauaKaart5.pööraÜmber(viiesLaual[0].toUpperCase()+" "+viiesLaual[1].substring(0,1).toUpperCase());

                for (int i = 0; i < diiler.kätes.size(); i++) {
                    // kaart9, kaart10, kaart1, kaart2, kaart3, kaart4, kaart5, kaart6, kaart7, kaart8
                    List<Kaart> töödeldavadKaardid = new ArrayList<>(List.of(diiler.kätes.get(i)));
                    String[] esimeneKaart = töödeldavadKaardid.get(0).toString().split(" ");
                    String[] teineKaart = töödeldavadKaardid.get(1).toString().split(" ");
                    kaardiKujutised.get(i*2).pööraÜmber(esimeneKaart[0].substring(0,1).toUpperCase() + " " + esimeneKaart[1].substring(0,1).toUpperCase());
                    kaardiKujutised.get(i*2+1).pööraÜmber(teineKaart[0].substring(0,1).toUpperCase() + " " + teineKaart[1].substring(0,1).toUpperCase());
                }

                for (int i = 1; i < rahaKastid.size()-1; i++) {
                    rahaKastid.get(i).setRaha(mängijateRahad.get(i));
                }
                paljuMängusOn.setRaha(diiler.getLaualRaha());
            });

                //todo call kui mingi npc otsusutab panust suurendada
                //todo bet suurendab betti

            bet.setOnMousePressed(eventFold -> {
                if (kasMängOnLäbi) {
                    return;
                }
                //bettimise sisetamine
                Label viimaneBetLabel = new Label("Viimane bet: " + viimaneBet);
                viimaneBetLabel.setFont(new Font("Comic Sans MS",30));
                viimaneBetLabel.setLayoutY(230);
                viimaneBetLabel.setLayoutX(380);
                viimaneBetLabel.setTextFill(Color.WHITE);

                Rectangle panustusAken = new Rectangle(250, 150, 500, 300);
                panustusAken.setFill(Color.DARKGREEN);
                panustusAken.setArcHeight(100);
                panustusAken.setArcWidth(100);
                panustusAken.setStroke(Color.SNOW);
                panustusAken.setStrokeWidth(10);

                TextField bettiSisestus = new TextField();
                bettiSisestus.setLayoutX(430);
                bettiSisestus.setLayoutY(320);
                bettiSisestus.setBorder(Border.stroke(Color.WHITE));

                Text tekst = new Text("sisestage panuse suurus");
                tekst.setFont(new Font("Comic Sans MS",30));
                tekst.setLayoutX(340);
                tekst.setLayoutY(300);
                tekst.setFill(Color.WHITESMOKE);

                Label panusta = new Label("Bet");
                panusta.setFont(new Font("Comic Sans MS",30));
                Rectangle panustaBack = new Rectangle(100,50);
                panustaBack.setFill(Color.BLANCHEDALMOND);
                panusta.setLayoutX(475);
                panusta.setLayoutY(363);
                panustaBack.setLayoutX(450);
                panustaBack.setLayoutY(360);
                panustaBack.setArcWidth(50);
                panustaBack.setArcHeight(50);

                root.getChildren().addAll(panustusAken, bettiSisestus, tekst, panustaBack, panusta, viimaneBetLabel);



                //todo leida viis asutaja käsu ootamiseks


                panusta.setOnMousePressed(eventPanusta -> {
                    if (Integer.parseInt(bettiSisestus.getText()) > diiler.getMängijaRaha(1) || Integer.parseInt(bettiSisestus.getText()) < viimaneBet) {
                    } else {
                    diiler.panustaRaha(1, Integer.parseInt(bettiSisestus.getText()));

                    root.getChildren().removeAll(panustusAken, bettiSisestus, tekst, panusta, panustaBack, viimaneBetLabel);
                    paljuMängusOn.setRaha(diiler.getLaualRaha());
                    mängijaRaha.setRaha(diiler.getMängijaRaha(1) - Integer.parseInt(bettiSisestus.getText()));

                    if (bettitud == 0) {
                        mituRaundiOnJäänud -= 1;
                        //peale esimest bettimist
                        diiler.lisaLauale(5);

                        //laua kaardid
                        KaardiKujutis lauaKaart1 = new KaardiKujutis();
                        lauaKaart1.setPaiknevus(295,166);
                        KaardiKujutis lauaKaart2 = new KaardiKujutis();
                        lauaKaart2.setPaiknevus(380,166);
                        KaardiKujutis lauaKaart3 = new KaardiKujutis();
                        lauaKaart3.setPaiknevus(465,166);
                        KaardiKujutis lauaKaart4 = new KaardiKujutis();
                        lauaKaart4.setPaiknevus(550,166);
                        KaardiKujutis lauaKaart5 = new KaardiKujutis();
                        lauaKaart5.setPaiknevus(635,166);

                        root.getChildren().addAll(lauaKaart1.kaardiTaust,lauaKaart1.kaardiSisemus,lauaKaart2.kaardiTaust,lauaKaart2.kaardiSisemus,
                                lauaKaart3.kaardiTaust,lauaKaart3.kaardiSisemus,lauaKaart4.kaardiTaust,lauaKaart4.kaardiSisemus, lauaKaart5.kaardiTaust,
                                lauaKaart5.kaardiSisemus,lauaKaart1.tekst,lauaKaart2.tekst,lauaKaart3.tekst,lauaKaart4.tekst,lauaKaart5.tekst);

                        List<Kaart> laual = diiler.getLaual();


                        //esimese 3 kaardi ümberpööramine
                        String[] esimeneLaual = laual.get(laual.size() - 5).toString().split(" ");
                        System.out.println(Arrays.toString(esimeneLaual));
                        lauaKaart1.pööraÜmber(esimeneLaual[0].toUpperCase()+" "+esimeneLaual[1].substring(0,1).toUpperCase());
                        String[] teineLaual = laual.get(laual.size() - 4).toString().split(" ");
                        lauaKaart2.pööraÜmber(teineLaual[0].toUpperCase()+" "+teineLaual[1].substring(0,1).toUpperCase());
                        String[] kolmasLaual = laual.get(laual.size() - 3).toString().split(" ");
                        lauaKaart3.pööraÜmber(kolmasLaual[0].toUpperCase()+" "+kolmasLaual[1].substring(0,1).toUpperCase());

                        bettitud++;

                    }
                    }

                });

            });
        });

        juhised.setOnMousePressed(event -> {
            System.out.println("Juhised");
            root.getChildren().remove(0,root.getChildren().size());
            Rectangle tagasiNupp = new Rectangle(100,50);
            tagasiNupp.setArcHeight(50);
            tagasiNupp.setArcWidth(50);
            tagasiNupp.setLayoutX(450);
            tagasiNupp.setLayoutY(470);
            tagasiNupp.setFill(Color.WHITE);
            tagasiNupp.setStroke(Color.BLACK);
            tagasiNupp.setStrokeWidth(5);
            Label tagasiNuppTekst = new Label("Tagasi");
            tagasiNuppTekst.setFont(new Font("Comic Sans MS",25));
            tagasiNuppTekst.setLayoutX(462);
            tagasiNuppTekst.setLayoutY(475);

            KaardiKujutis demoKaart = new KaardiKujutis();
            demoKaart.setPaiknevus(20,20);
            KaardiKujutis demoKaart2 = new KaardiKujutis();
            demoKaart2.setPaiknevus(105, 20);
            demoKaart2.pööraÜmber("Ä T");

            Rectangle loetavuseKast = new Rectangle(750,400);
            loetavuseKast.setFill(Color.WHITE);
            loetavuseKast.setLayoutY(25);
            loetavuseKast.setLayoutX(210);
            loetavuseKast.setArcWidth(100);
            loetavuseKast.setArcHeight(100);

            Label kaartideTekst = new Label("Kaartidel tähistab esimene sümbol '♥' kaardi masti, teine sümbol 'T' tähistab\nkaardi tugevust." +
                    "Et panust suurendada vajutada nupule BET, et panust hoida\nvajutada nupule CALL, et raundist välja jääda vajutada FOLD." +
                    " Et lugeda\nkombinatsioonide kohta vajutada        (avab interneti akna.)");
            Label hyperLink = new Label("siia.");
            hyperLink.setLayoutY(119);
            hyperLink.setLayoutX(535);
            hyperLink.setFont(new Font("Comic Sans MS", 20));
            kaartideTekst.setFont(new Font("Comic Sans MS", 20));
            kaartideTekst.setLayoutX(225);
            kaartideTekst.setLayoutY(35);

            hyperLink.setOnMousePressed(event3 -> {
                try {
                    Desktop desktop = java.awt.Desktop.getDesktop();
                    URI oURL = new URI("https://et.wikipedia.org/wiki/Pokkerik%C3%A4te_tugevusj%C3%A4rjestus");
                    desktop.browse(oURL);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            Label callDemo = new Label("Call");
            callDemo.setFont(new Font("Comic Sans MS",30));
            Rectangle callBackDemo = new Rectangle(100,50);
            callBackDemo.setFill(Color.BLANCHEDALMOND);
            callDemo.setLayoutX(73);
            callDemo.setLayoutY(142);
            callBackDemo.setLayoutX(50);
            callBackDemo.setLayoutY(140);
            callBackDemo.setArcWidth(50);
            callBackDemo.setArcHeight(50);

            Label betDemo = new Label("Bet");
            betDemo.setFont(new Font("Comic Sans MS",30));
            Rectangle betBackDemo = new Rectangle(100,50);
            betBackDemo.setFill(Color.BLANCHEDALMOND);
            betDemo.setLayoutX(73);
            betDemo.setLayoutY(202);
            betBackDemo.setLayoutX(50);
            betBackDemo.setLayoutY(200);
            betBackDemo.setArcWidth(50);
            betBackDemo.setArcHeight(50);

            Label foldDemo = new Label("Fold");
            foldDemo.setFont(new Font("Comic Sans MS",30));
            Rectangle foldBackDemo = new Rectangle(100,50);
            foldBackDemo.setFill(Color.BLANCHEDALMOND);
            foldDemo.setLayoutX(70);
            foldDemo.setLayoutY(262);
            foldBackDemo.setLayoutX(50);
            foldBackDemo.setLayoutY(260);
            foldBackDemo.setArcWidth(50);
            foldBackDemo.setArcHeight(50);

            root.getChildren().addAll(loetavuseKast, tagasiNupp, tagasiNuppTekst, demoKaart2.kaardiTaust, demoKaart2.kaardiSisemus, demoKaart2.tekst,
                    demoKaart.kaardiTaust, demoKaart.kaardiSisemus, demoKaart.tekst, kaartideTekst, foldBackDemo, foldDemo, callBackDemo, callDemo, betBackDemo, betDemo,
                    hyperLink);

            tagasiNuppTekst.setOnMousePressed(event2 -> {
                root.getChildren().remove(0,root.getChildren().size());
                root.getChildren().addAll(pealkiri,backRuut,backRuut2,backRuut3,alusta, quit, juhised);
            });

        });

        quit.setOnMousePressed(event -> {
            Platform.exit();
        });


        /*
        //mängimise osa
        Scanner scan = new Scanner(System.in);

        System.out.println("Lihtne Pokker\n" + tühik);
        mäng:
        while (true) {
            String valik = "Valiku tegemiseks sisestage number: ";

            System.out.println("1. Mängu reeglid\n2. Alusta mängimist\n3. Lahku\n" + tühik);
            System.out.println(valik);

            int vastus = scan.nextInt();
            System.out.println(tühik);
            switch (vastus) {
                case 1:
                    System.out.println("Mängu reeglid");
                    System.out.println("");
                    System.out.println("1. Mängu võitmine");
                    //System.out.println("2. panustamine");
                    System.out.println("3. Tagasi\n" + tühik);
                    System.out.println(valik);
                    int reegel = scan.nextInt();
                    System.out.println(tühik);
                    switch (reegel) {
                        case 1:
                            selgitused:
                            while (true) {
                                System.out.println("Mängu võitmine\n\n" +
                                        "Mängu võidab see kellel on kõige tugevam käsi\n" +
                                        "käte tugevused on tugevaimast nõrgemani on järgmised");
                                System.out.println("Käte selgitused\n");
                                System.out.println(
                                        "1. Kuninglik mastirida\n" +
                                                "2. Mastirida\n" +
                                                "3. Nelik\n" +
                                                "4. Maja\n" +
                                                "5. Mast\n" +
                                                "6. Rida\n" +
                                                "7. Kolmik\n" +
                                                "8. Kaks paari\n" +
                                                "9. Üks paar\n" +
                                                "10. Kõrge kaart\n" +
                                                "11. tagasi\n" +
                                                tühik);
                                System.out.println(valik);
                                int selgitus = scan.nextInt();
                                System.out.println(tühik);
                                switch (selgitus) {

                                    //Kuninglik mastirida (Royal Straight Flush)
                                    case 1:
                                        System.out.println("Kuninglik mastirida\n");
                                        System.out.println("Ühest mastist äss, kuningas, emand, poiss ja kümme");
                                        System.out.println(tühik);
                                        System.out.println("tagasiminekuks sisestage suvaline number: ");
                                        int arv = scan.nextInt();
                                        break;

                                    //Mastirida (Straight Flush)
                                    case 2:
                                        System.out.println("Mastirida\n");
                                        System.out.println("Mastirea moodustavad viis järjestikust ühest mastist kaarti");
                                        System.out.println(tühik);
                                        System.out.println("tagasiminekuks sisestage suvaline number: ");
                                        int arv10 = scan.nextInt();
                                        break;

                                    //Nelik (Four Of A Kind)
                                    case 3:
                                        System.out.println("Nelik\n");
                                        System.out.println("Neli samase tugevusega kaarti");
                                        System.out.println(tühik);
                                        System.out.println("tagasiminekuks sisestage suvaline number: ");
                                        int arv11 = scan.nextInt();
                                        break;

                                    //Maja (Full House)
                                    case 4:
                                        System.out.println("Maja\n");
                                        System.out.println("Maja koosneb kolmikust ja paarist");
                                        System.out.println(tühik);
                                        System.out.println("tagasiminekuks sisestage suvaline number: ");
                                        int arv13 = scan.nextInt();
                                        break;

                                    //Mast (Flush)
                                    case 5:
                                        System.out.println("Mast\n");
                                        System.out.println("Viis ühest mastist kaarti");
                                        System.out.println(tühik);
                                        System.out.println("tagasiminekuks sisestage suvaline number: ");
                                        int arv2 = scan.nextInt();
                                        break;
                                    //Rida (Straight)
                                    case 6:
                                        System.out.println("Rida\n");
                                        System.out.println("Viis järjestikust kaarti. Näiteks kolm, neli, viis, kuus ja seitse");
                                        System.out.println(tühik);
                                        System.out.println("tagasiminekuks sisestage suvaline number: ");
                                        int arv3 = scan.nextInt();
                                        break;
                                    //Kolmik (Three Of A Kind)
                                    case 7:
                                        System.out.println("Kolmik\n");
                                        System.out.println("Kolm samase tugevusega kaarti");
                                        System.out.println(tühik);
                                        System.out.println("tagasiminekuks sisestage suvaline number: ");
                                        int arv4 = scan.nextInt();
                                        break;
                                    //Kaks paari (Two Pair)
                                    case 8:
                                        System.out.println("Kaks paari\n");
                                        System.out.println("Tugevaimad kaks võimalikku paari oleksid äss, äss ja kuningas, kuningas");
                                        System.out.println(tühik);
                                        System.out.println("tagasiminekuks sisestage suvaline number: ");
                                        int arv5 = scan.nextInt();
                                        break;
                                    //Üks paar (One Pair)
                                    case 9:
                                        System.out.println("Üks paar\n");
                                        System.out.println("Kaks samase tugevusega kaarti");
                                        System.out.println(tühik);
                                        System.out.println("tagasiminekuks sisestage suvaline number: ");
                                        int arv6 = scan.nextInt();
                                        break;

                                    //Kõrge kaart (High card)
                                    case 10:
                                        System.out.println("Kõrge kaart\n");
                                        System.out.println("Kõrgeim käesolev kaart");
                                        System.out.println(tühik);
                                        System.out.println("tagasiminekuks sisestage suvaline number: ");
                                        int arv17 = scan.nextInt();
                                        break;

                                    case 11:
                                        break selgitused;
                                }
                            }
                        case 2:
                            break;
                    }

                    break;

                //MÄNGU CASE

                case 2:
                    //lisada mängu osa
                    pealava.show();
                    Random random = new Random();
                    int mängijateArv = 0;
                    while (mängijateArv < 4) {
                        System.out.print("Sisesta mängijate arv: ");
                        mängijateArv = scan.nextInt();
                    }
                    Kaardipakk kaardipakk = new Kaardipakk();
                    kaardipakk.genereeriTavaPakk();
                    Diiler diiler = new Diiler(kaardipakk);
                    diiler.alustaRaundi(mängijateArv);
                    //mäng
                    //hetkel teen nii, et iga arvuti mängija betib random amounti.
                    System.out.println("\nLaua miinimum on 200.");
                    int blind = 0;
                    System.out.println("Small blind: " + blind + ". mängija, panus: 100");
                    diiler.panustaRaha(1,100);
                    System.out.println("Big blind: " + (blind+1) + ".mängija, panus: 200");
                    diiler.panustaRaha(2, 200);
                    System.out.println();
                    int viimanePanus = 200;
                    //esimese raundi panused
                    for (int i = 3; i <= mängijateArv; i++) {
                        int foldBetRaise = random.nextInt(3);
                        //raise
                        if (foldBetRaise == 0) {
                            if (i == 3) {
                                int panus = random.nextInt((200+viimanePanus - 200) + 1) + 200;
                                diiler.panustaRaha(i, panus);
                                System.out.println(i + ". mängija tõstis panust.");
                                System.out.println(i + ". mängija panustas: " + panus);
                                viimanePanus = panus;
                                continue;
                            }
                            int panus = random.nextInt((200)+1) + viimanePanus;
                            diiler.panustaRaha(i, panus);
                            System.out.println(i + ". mängija tõstis panust.");
                            System.out.println(i + ". mängija panustas: " + panus);
                            viimanePanus = panus;
                        }
                        //call
                        if (foldBetRaise == 1) {
                            diiler.panustaRaha(i, viimanePanus);
                            System.out.println(i + ". mängija jätkas panust.");
                            System.out.println(i + ". mängija panustas: " + viimanePanus);
                        }
                        //fold
                        if (foldBetRaise == 2) {
                            diiler.fold(i);
                            System.out.println(i + ". mängija pani foldi.");
                        }

                    }
                    System.out.print("\nSinu käsi: ");
                    diiler.getMängijaKäsi();
                    System.out.println("Sinu raha: " + diiler.getRaha().get(0));

                    System.out.println("""
                               Mida soovid teha:\s
                               1 - fold
                               2 - mängi edasi""");
                    int foldCallBet = scan.nextInt();

                    if (foldCallBet == 1) {
                        diiler.fold(1);
                        // siit edasi tuleb järgmised raundid automaatselt, ilma mängijata
                    } else if (foldCallBet == 2) {
                        diiler.lisaLauale(3);
                        // siit edasi tuleb raundid koos mängijaga.
                    }
                    /*else if (diiler.highCard().contains(true)) {
                        List<Boolean> võitjad = diiler.highCard();
                        väljastaVõitja(võitjad);
                        System.out.println("Kõrge kaart");
                    }

                    System.out.println(tühik);
                    break;
                case 3:
                    System.out.println("Aitäh, Mängige jälle!");
                    break mäng;

            }
        }
        */
    }

    /* kui tulevikus edasi teha

    Üks paar (One Pair) -ile kicker
    Kaks paari (Two Pair) -ile kicker
    ja teised täpsustavad asjad võiduolukordadele

    panustamine
    */

    /* reeglid
     * mängu võitmine
     * mängu võidab see kellel on kõige tugevam käsi käes
     * käte tugevused on tegevaimast nõrgemani on järgmised
     * https://et.wikipedia.org/wiki/Pokkerik%C3%A4te_tugevusj%C3%A4rjestus
     * panustamine // lisab hiljem
     */

    /*
    (done)    Programm käsitleb mingit (inimlikku) tegevust, näiteks mängimist
    (done)    Programm peab kasutajalt midagi küsima (võib-olla ka korduvalt)
    (done)    Programm peaks olema kasutatav ilma, et programmi kohta oleks erilisi eelteadmisi
    (done)    Programm peab sisaldama juhusliku suuruse kasutamist (soovitavalt klassi Random abil)
    (done)    Programm peab koosnema mitmest klassist (sh. peaklass)
    (yes)     Programm peab olema rühmaliikmete endi kirjutatud
        Programm peab olema mõistlikult kommenteeritud.
    (kinda)    Programm ei tohiks olla liiga keeruline
    (done)    Kasutajaga suhtlemine peaks olema väga elementaarselt kujundatud. Vältida (veel) graafilist kasutajaliidest.
            Ekraanile kuvatav info (ka näiteks mänguseis) võib olla tekstiridadena (System.out.println).
     */

    public static void väljastaVõitja(List<Boolean> võitjad) {
        System.out.println(võitjad);
        int võitjateArv = 0;
        List<Integer> võitjateIndex = new ArrayList<>();
        for (int i = 0; i < võitjad.size(); i++) {
            if (võitjad.get(i)) {
                võitjateIndex.add(i);
                võitjateArv++;
            }
        }

        if (võitjateArv > 1) {
            System.out.println("Mäng jäi viiki mängijate poolt: ");
            for (int i = 0; i < võitjateArv; i++) {
                System.out.println((võitjateIndex.get(i)+1) + ". mängija.");
            }
            System.out.println("viigi põhjus: ");
        } else {
            System.out.println("Mängu võitis: " + (võitjateIndex.get(0)+1) + ". mängija.");
            System.out.println("Võidu põhjus: ");
        }
    }
    public static List<Boolean> kontrollija(Diiler diiler) {
        List<Boolean> võitjad;
        if (diiler.royalStraightFlush().contains(true)) {
            võitjad = diiler.royalStraightFlush();
            väljastaVõitja(võitjad);
            System.out.println("Kuninglik mastirida");
            return võitjad;

        } else if (diiler.straightFlush().contains(true)) {
            võitjad = diiler.straightFlush();
            väljastaVõitja(võitjad);
            System.out.println("Mastirida");
            return võitjad;

        } else if (diiler.fourOfAKind().contains(true)) {
            võitjad = diiler.fourOfAKind();
            väljastaVõitja(võitjad);
            System.out.println("Nelik");
            return võitjad;

        } else if (diiler.fullHouse().contains(true)) {
            võitjad = diiler.fullHouse();
            väljastaVõitja(võitjad);
            System.out.println("Maja");
            return võitjad;

        } else if (diiler.flush().contains(true)) {
            võitjad = diiler.flush();
            väljastaVõitja(võitjad);
            System.out.println("Mast");
            return võitjad;

        } else if (diiler.straight().contains(true)) {
            võitjad = diiler.straight();
            väljastaVõitja(võitjad);
            System.out.println("Rida");
            return võitjad;

        } else if (diiler.threeOfAKind().contains(true)) {
            võitjad = diiler.threeOfAKind();
            väljastaVõitja(võitjad);
            System.out.println("Kolmik");
            return võitjad;
        } else if (diiler.twoPair().contains(true)) {
            võitjad = diiler.twoPair();
            väljastaVõitja(võitjad);
            System.out.println("Kaks paar");
            return võitjad;
        } else if (diiler.pair().contains(true)) {
            võitjad = diiler.pair();
            väljastaVõitja(võitjad);
            System.out.println("üks paar");
            return võitjad;
        } else {
            List<Integer> kaardid = diiler.highCard();
            System.out.println(kaardid);
            int suurim = 0;
            int suurimIndeks = 0;
            for (int i = 0; i < kaardid.size(); i++) {
                if (kaardid.get(i) > suurim) {
                    suurim = kaardid.get(i);
                    suurimIndeks = i;
                }
            }
            List<Boolean> temp = new ArrayList<>();
            diiler.mängus[suurimIndeks] = true;
            for (int i = 0; i < diiler.mängus.length; i++) {
                temp.add(diiler.mängus[i]);
            }
            return temp;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}

/* test tsükkel ära eemalda pls
                    //oke
                    while (!diiler.royalStraightFlush().contains(true)) {
                        diiler.käed();
                        diiler.resetAll();
                        diiler.alustaRaundi(4);
                        diiler.lisaLauale(5);
                        for (int i = 0; i < 4; i++) {
                            System.out.println(diiler.käedLauaga(i));
                        }
                        System.out.println(diiler.royalStraightFlush());
                    } */