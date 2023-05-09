package com.example.graafilinepokker;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;

public class Main extends Application {

    @Override
    public void start(Stage pealava) {

        //varastatud siit: https://coderslegacy.com/java/switch-between-scenes-in-javafx/
        VBox layout = new VBox();
        VBox layout2 = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout2.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 300);
        Scene scene2 = new Scene(layout2, 300, 300);

        Label label1 = new Label("This is the First Scene");
        Label label2 = new Label("This is the Second Scene");

        Button button = new Button("Forward");
        button.setOnAction(e -> pealava.setScene(scene2));

        Button button2 = new Button("Backwards");
        button2.setOnAction(e -> pealava.setScene(scene));

        TextField text = new TextField();
        text.setMaxWidth(100);

        layout.getChildren().addAll(label1, button);
        layout2.getChildren().addAll(label2, button2, text);

        pealava.setTitle("CodersLegacy");
        pealava.setScene(scene);
        pealava.show();
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
    public static void kontrollija(Diiler diiler) {
        if (diiler.royalStraightFlush().contains(true)) {
            List<Boolean> võitjad = diiler.royalStraightFlush();
            väljastaVõitja(võitjad);
            System.out.println("Kuninglik mastirida");

        } else if (diiler.straightFlush().contains(true)) {
            List<Boolean> võitjad = diiler.straightFlush();
            väljastaVõitja(võitjad);
            System.out.println("Mastirida");

        } else if (diiler.fourOfAKind().contains(true)) {
            List<Boolean> võitjad = diiler.fourOfAKind();
            väljastaVõitja(võitjad);
            System.out.println("Nelik");

        } else if (diiler.fullHouse().contains(true)) {
            List<Boolean> võitjad = diiler.fullHouse();
            väljastaVõitja(võitjad);
            System.out.println("Maja");

        } else if (diiler.flush().contains(true)) {
            List<Boolean> võitjad = diiler.flush();
            väljastaVõitja(võitjad);
            System.out.println("Mast");

        } else if (diiler.straight().contains(true)) {
            List<Boolean> võitjad = diiler.straight();
            väljastaVõitja(võitjad);
            System.out.println("Rida");

        } else if (diiler.threeOfAKind().contains(true)) {
            List<Boolean> võitjad = diiler.threeOfAKind();
            väljastaVõitja(võitjad);
            System.out.println("Kolmik");

        } else if (diiler.twoPair().contains(true)) {
            List<Boolean> võitjad = diiler.twoPair();
            väljastaVõitja(võitjad);
            System.out.println("Kaks paar");

        } else if (diiler.pair().contains(true)) {
            List<Boolean> võitjad = diiler.pair();
            väljastaVõitja(võitjad);
            System.out.println("üks paar");
        }
    }

    public static void main(String[] args) {
        launch();

        String tühik = "----------------\n";

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
                    } */

                System.out.println(tühik);
                break;
                    case 3:
                    System.out.println("Aitäh, Mängige jälle!");
                    break mäng;

                    }
            }
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