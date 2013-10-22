/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package resolutionequation;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author pc
 */
public class ResolvePane extends AnchorPane {

    static private final ObservableList limites = FXCollections.observableArrayList(
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "13", "14", "15");

    public ResolvePane(final int n, EventHandler<ActionEvent> down, final int methode) {

        this.setMaxSize(650, 500);
        this.setMinSize(650, 500);
        this.setId("PaneUP");

        //---- TAB MATRICE    
        final VBox tabG = new VBox();
        tabG.setMaxSize(340, 265);
        tabG.setMinSize(340, 265);
        tabG.setLayoutX(60);
        tabG.setLayoutY(60);


        final GridPane matrice = new GridPane();
        matrice.setMaxSize(n * 40, n * 40);
        matrice.setHgap(10);
        matrice.setVgap(10);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                final int k=j;
                final int l=i;
                matrice.add(new TextField("0"), i, j);
                ((TextField) matrice.getChildren().get(i * n + j)).textProperty().addListener(new ChangeListener<String>() {

                @Override
                public void changed(ObservableValue<? extends String> ov, String oldS, String newS) {
                
                    if(!newS.matches("[0-9]*[.]?[0-9]*")) ((TextField) matrice.getChildren().get(l * n + k)).setText(oldS);
                }
            });
                
               
            }
        }
        tabG.setAlignment(Pos.CENTER);
        tabG.getChildren().addAll(matrice);
        // END TAB MATRICE


        //---- TAB SECOND MEMEBRE
        final VBox tabD = new VBox();
        tabD.setMaxSize(195, 265);
        tabD.setMinSize(195, 265);
        tabD.setLayoutX(400);
        tabD.setLayoutY(60);

        final VBox SecondMembre = new VBox(10);
        SecondMembre.setMaxSize(40, n * 40);
        for (int i = 0; i < n; i++) {
            SecondMembre.getChildren().add(i, new TextField("0"));
            final int j=i;
            ((TextField) SecondMembre.getChildren().get(i)).textProperty().addListener(new ChangeListener<String>() {

                @Override
                public void changed(ObservableValue<? extends String> ov, String oldS, String newS) {
                
                    if(!newS.matches("[0-9]*[.]?[0-9]*")) ((TextField) SecondMembre.getChildren().get(j)).setText(oldS);
                }
            });
        }
        tabD.getChildren().addAll(SecondMembre);
        tabD.setAlignment(Pos.CENTER);
        // END TAB SECOND MEMEBRE  

        // ---- Puissance      
        final ComboBox puiss = ComboBoxBuilder.create().id("glassbtn").items(FXCollections.observableArrayList(limites)).build();
        puiss.setValue(limites.get(0));
        puiss.setPrefSize(50, 20);
        puiss.setLayoutX(360);
        puiss.setLayoutY(337);
        // END Puissance 

        // --- Resoudre bouton       
        final Button resolve = new Button("Résoudre");
        resolve.setId("roundbtn");
        resolve.setPrefSize(80, 30);
        resolve.setLayoutX(520);
        resolve.setLayoutY(333);

        // --- Aleat bouton       
        final Button aleat = new Button("Aléatoire");
        aleat.setId("roundbtn");
        aleat.setPrefSize(80, 30);
        aleat.setLayoutX(430);
        aleat.setLayoutY(333);
        //End Resoudre bouton      

        //---- RESULTAT
        final VBox result = new VBox();
        result.setMaxSize(350, 40);
        result.setMinSize(350, 40);
        result.setLayoutX(50);
        result.setLayoutY(400);

        final HBox vectRes = new HBox(10);
        vectRes.getChildren().add(new Label("("));
        for (int i = 1; i <= n; i++) {
            
          
            vectRes.getChildren().add(i, new TextField("0"));
            ((TextField) vectRes.getChildren().get(i)).setPrefWidth(50);
            ((TextField) vectRes.getChildren().get(i)).setEditable(false);
        }

        vectRes.getChildren().add(new Label(")"));
        vectRes.setAlignment(Pos.CENTER);
        result.getChildren().addAll(vectRes);
        result.setAlignment(Pos.CENTER);
        // END RESULTAT 

        // ---- COUT      
        final TextField cout = new TextField();
        cout.setPrefSize(120, 25);
        cout.setLayoutX(450);
        cout.setLayoutY(410);
        cout.setEditable(false);
        // END coup

        // --- Bouton retour
        final Button retour = new Button("Retour");
        retour.setId("glassbtn");
        retour.setPrefSize(100, 30);
        retour.setLayoutX(540);
        retour.setLayoutY(460);
        retour.setOnAction(down);
        // END bouton retour      



        resolve.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Matrice M = new Matrice(n);
                Vecteur B = new Vecteur(n);
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        M.setij(Double.parseDouble(((TextField) matrice.getChildren().get(j * n + i)).getText()), i, j);
                    }
                }
                for (int i = 0; i < n; i++) {
                    B.seti(Double.parseDouble(((TextField) SecondMembre.getChildren().get(i)).getText()), i);
                }
                Resolution Sys;
                if (methode == 1) {
                    Sys = new GaussResolution(M, B);
                } else if (methode == 2) {
                    Sys = new LUResolution(M, B);
                } else {
                    Sys = new CholeskyResolution(M, B);
                }
                long l1=System.nanoTime();
                boolean b = Sys.Solve(puiss.getSelectionModel().getSelectedIndex() + 1);
                long l2=System.nanoTime();
                cout.setText(String.valueOf((l2-l1)/1000)+" ms");

                if (b) {
                    for (int i = 0; i < n; i++) {
                        ((TextField) vectRes.getChildren().get(i + 1)).setText(String.valueOf(Sys.getSolution().geti(i)));
                    }
                } else {
                    Stage Notif = new Notification();
                    Notif.show();
                }

            }
        });

        aleat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Matrice M = Matrice.aleaMat(n);
                Vecteur B = Vecteur.aleaVect(n);

                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        ((TextField) matrice.getChildren().get(j * n + i)).setText(String.valueOf(M.getij(i, j)));
                    }
                }
                for (int i = 0; i < n; i++) {
                    ((TextField) SecondMembre.getChildren().get(i)).setText(String.valueOf(B.geti(i)));
                }
            }
        });

        this.getChildren().addAll(tabG, tabD, puiss, resolve, aleat, result, cout, retour);
    }
}
