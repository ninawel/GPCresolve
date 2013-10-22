/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package resolutionequation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBuilder;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author NiGhThUnTeR
 */
public class ResolutionEquation extends Application{
    
    static private final ObservableList degrees = FXCollections.observableArrayList(
            "1", "2", "3", "4", "5", "6", "7", "8");
    private AnchorPane PaneDown;
    private AnchorPane PaneUP;
    private Rectangle clipRect;
    private Timeline timelineUp;
    private Timeline timelineDown;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        int n = 5;

        final AnchorPane entete = new AnchorPane();
        entete.setPrefHeight(120);
        entete.setPrefWidth(650);
        entete.setLayoutX(0);
        entete.setLayoutY(0);
        entete.setId("header");


        PaneDown = new AnchorPane();
        PaneDown.setMaxSize(650, 500);
        PaneDown.setMinSize(650, 500);
        PaneDown.setId("PaneDOWN");



        // Rectangle du degré
        final Label degreLabel = new Label("DEGRE DU SYSTEME");
        degreLabel.setId("labeltext");



        final ComboBox degreFiled = ComboBoxBuilder.create().id("glassbtn").items(FXCollections.observableArrayList(degrees)).build();
        degreFiled.setValue(degrees.get(0));
        degreFiled.setMaxSize(90, 50);


        final VBox degrebox = new VBox(10);
        degrebox.getChildren().addAll(degreLabel, degreFiled);
        degrebox.setAlignment(Pos.CENTER);


        // End Rectangle du degré    
        //Buttons       
        final VBox buttons = new VBox(20);
        final Button Gaussbtn = new Button("GAUSS");
        Gaussbtn.setPrefSize(150, 50);
        Gaussbtn.setId("btnhome");

        final Button LUbtn = new Button("P L U");
        LUbtn.setPrefSize(150, 50);
        LUbtn.setId("btnhome");
        final Button Cholbtn = new Button("CHOLESKY");
        Cholbtn.setPrefSize(150, 50);
        Cholbtn.setId("btnhome");

        buttons.getChildren().addAll(degrebox, Gaussbtn, LUbtn, Cholbtn);
        buttons.setLayoutX(450);
        buttons.setLayoutY(90);
        //End BUTTONS

        PaneDown.getChildren().add(buttons);

        final StackPane stack = new StackPane();
        stack.getChildren().addAll(PaneDown);
        final VBox container = new VBox(0);
        container.setId("container");
        container.getChildren().addAll(entete, stack);
        final StackPane root = new StackPane();
        root.getChildren().addAll(container);
        final Scene scn = new Scene(root, 650, 620);
        primaryStage.setScene(scn);
        primaryStage.setTitle("GPC Resolve");
        scn.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.show();

        final EventHandler<ActionEvent> down = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                timelineUp.play();
                System.out.println(stack.getChildren().size());

            }
        };

        final EventHandler<ActionEvent> up = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                Node o;
                if (stack.getChildren().size() > 1) {
                    o = stack.getChildren().remove(1);
                }
                int methode=1;
                if(t.getSource().equals(Gaussbtn)) methode=1;
                if(t.getSource().equals(LUbtn)) methode=2;
                if(t.getSource().equals(Cholbtn)) methode=3;
               
                PaneUP = new ResolvePane(degreFiled.getSelectionModel().getSelectedIndex() + 1, down,methode);
                stack.getChildren().add(1, PaneUP);
                setAnimation();
                timelineDown.play();
            }
        };

        Gaussbtn.setOnAction(up);
        LUbtn.setOnAction(up);
        Cholbtn.setOnAction(up);

    }

    private void setAnimation() {
        // Cacher le PaneUP
        
        clipRect = new Rectangle();
        clipRect.setWidth(650);
        clipRect.setHeight(0);
        clipRect.translateYProperty().set(500);
        PaneUP.setClip(clipRect);
        PaneUP.translateYProperty().set(-500);

        timelineDown = new Timeline();
        timelineUp = new Timeline();
        

        // Slide en descente
        timelineDown.setCycleCount(1);
        timelineDown.setAutoReverse(true);
        final KeyValue kvDwn1 = new KeyValue(clipRect.heightProperty(), 500);
        final KeyValue kvDwn2 = new KeyValue(clipRect.translateYProperty(), 0);
        final KeyValue kvDwn3 = new KeyValue(PaneUP.translateYProperty(), 0);
        final KeyFrame kfDwn = new KeyFrame(Duration.millis(400),kvDwn1, kvDwn2, kvDwn3);
        timelineDown.getKeyFrames().add(kfDwn);

        // Slide en remonté
        timelineUp.setCycleCount(1);
        timelineUp.setAutoReverse(true);
        final KeyValue kvUp1 = new KeyValue(clipRect.heightProperty(), 0);
        final KeyValue kvUp2 = new KeyValue(clipRect.translateYProperty(), 500);
        final KeyValue kvUp3 = new KeyValue(PaneUP.translateYProperty(), -500);
        final KeyFrame kfUp = new KeyFrame(Duration.millis(400), kvUp1, kvUp2, kvUp3);
        timelineUp.getKeyFrames().add(kfUp);
    }
}
