package com.example.graafilinepokker;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;

public class RahaKast extends Shape {
    public Label raha;
    public Circle rahaRingTaust;
    public Circle rahaRingKesk;
    public Rectangle rahaRingKast;

    public Label paljuRahaOn;
    RahaKast() {
        this.paljuRahaOn = new Label("00000");
        this.paljuRahaOn.setFont(new Font("Comic Sans MS",25));

        this.raha = new Label("$");
        this.raha.setFont(new Font("Comic Sans MS",40));

        this.rahaRingTaust = new Circle(30);
        this.rahaRingTaust.setFill(Color.DARKRED);

        this.rahaRingKesk = new Circle(25);
        this.rahaRingKesk.setFill(Color.BLANCHEDALMOND);

        this.rahaRingKast = new Rectangle(160,60);
        this.rahaRingKast.setFill(Color.WHITE);
        this.rahaRingKast.setArcHeight(60);
        this.rahaRingKast.setArcWidth(60);
    }

    public void setPaiknevus(double x, double y) {
        this.raha.setLayoutX(x+2.5);
        this.raha.setLayoutY(y);
        this.rahaRingTaust.setCenterX(x+15);
        this.rahaRingTaust.setCenterY(y+30);
        this.rahaRingKesk.setCenterX(x+15);
        this.rahaRingKesk.setCenterY(y+30);
        this.rahaRingKast.setLayoutX(x-15);
        this.rahaRingKast.setLayoutY(y);
        this.paljuRahaOn.setLayoutX(x+50);
        this.paljuRahaOn.setLayoutY(y+10);
    }

    public void setRaha(int arv) {
        int pikkus = (String.valueOf(arv)).length();
        if (pikkus > 5) return;
        this.paljuRahaOn.setText("0".repeat(5-pikkus)+arv);
    }
}
