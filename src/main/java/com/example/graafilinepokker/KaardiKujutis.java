package com.example.graafilinepokker;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class KaardiKujutis extends Rectangle {

    public Rectangle kaardiTaust;
    public Rectangle kaardiSisemus;
    public Label tekst;

    KaardiKujutis() {
        this.kaardiTaust = new Rectangle(75,100);
        this.kaardiSisemus = new Rectangle(65,90);
        this.kaardiSisemus.setArcHeight(25);
        this.kaardiSisemus.setArcWidth(25);
        this.kaardiTaust.setArcHeight(25);
        this.kaardiTaust.setArcWidth(25);
        this.kaardiSisemus.setFill(Color.DARKRED);
        this.kaardiTaust.setFill(Color.BLANCHEDALMOND);
        this.tekst = new Label("");
    }
    public void setPaiknevus(int x, int y) {
        this.kaardiTaust.setLayoutX(x);
        this.kaardiTaust.setLayoutY(y);
        this.kaardiSisemus.setLayoutX(x+5);
        this.kaardiSisemus.setLayoutY(y+5);

    }

    public void pööraÜmber(String mastJaTugevus) {
        this.tekst.setText(mastJaTugevus);
        this.kaardiSisemus.setFill(Color.BLANCHEDALMOND);
        this.kaardiTaust.setFill(Color.DARKRED);
        this.tekst.setLayoutX(this.kaardiSisemus.getLayoutX()+8);
        this.tekst.setLayoutY(this.kaardiSisemus.getLayoutY()+22);
        this.tekst.setFont(new Font("Comic Sans MS",30));
    }


}
