package view;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;

public class Course {
    private final Pane pane;
    private double currentY;
    private ArrayList<String> unHandelMessage = new ArrayList<>();
    private ArrayList<Label> mustBeRePlacedLabels = new ArrayList<>();
    private ArrayList<String> mustBeRePlacedLabelString = new ArrayList<>();
    private ArrayList<Label> mustBeRemoved = new ArrayList<>();
    private ArrayList<Label> activeLabels = new ArrayList<>();

    public Course(Pane pane, double currentY) {
        this.pane = pane;
        this.currentY = currentY;
    }


    public double getCurrentY() {
        return currentY;
    }

    public void setCurrentY(double currentY) {
        this.currentY = currentY;
    }

    public Pane getPane() {
        return pane;
    }

    public ArrayList<String> getUnHandelMessage() {
        return unHandelMessage;
    }

    public void setUnHandelMessage(ArrayList<String> unHandelMessage) {
        this.unHandelMessage = unHandelMessage;
    }

//    public void setMustBeRePlacedLabel(HashMap<Label, String> mustBeRePlacedLabel) {
//        this.mustBeRePlacedLabel = mustBeRePlacedLabel;
//    }

    public void setMustBeRemoved(ArrayList<Label> mustBeRemoved) {
        this.mustBeRemoved = mustBeRemoved;
    }


    public ArrayList<Label> getMustBeRemoved() {
        return mustBeRemoved;
    }

    public ArrayList<Label> getActiveLabels() {
        return activeLabels;
    }

    public ArrayList<Label> getMustBeRePlacedLabels() {
        return mustBeRePlacedLabels;
    }

    public void setMustBeRePlacedLabels(ArrayList<Label> mustBeRePlacedLabels) {
        this.mustBeRePlacedLabels = mustBeRePlacedLabels;
    }

    public ArrayList<String> getMustBeRePlacedLabelString() {
        return mustBeRePlacedLabelString;
    }

    public void setMustBeRePlacedLabelString(ArrayList<String> mustBeRePlacedLabelString) {
        this.mustBeRePlacedLabelString = mustBeRePlacedLabelString;
    }
}
