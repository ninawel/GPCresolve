/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package resolutionequation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author pc
 */
public class Notification extends Stage{

    public Notification() {
   
    
        Label message1 = new Label("Le système que vous avez introduit n'est pas de Cramer");        
        message1.setAlignment(Pos.BASELINE_CENTER);
        message1.setWrapText(true);
        message1.setTextAlignment(TextAlignment.JUSTIFY);
        message1.setPrefWidth(300);

        Button ok_btn = new Button("Ok");
        ok_btn.setPrefSize(70,20);
        ok_btn.setAlignment(Pos.CENTER);

        ok_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                close();
            }
        });

        VBox vb2 = new VBox(30);
        vb2.getChildren().addAll(message1, ok_btn);
        vb2.setAlignment(Pos.CENTER);
        vb2.setStyle(" -fx-background-color: rgb(210,210,210),linear-gradient(white 10%, rgb(210,210,210) 80%);");

        Scene scene = new Scene(vb2, 350, 130);
        this.setScene(scene);
        this.setTitle("Probleme");
        //stage.initOwner(ownerstage);
        this.initModality(Modality.APPLICATION_MODAL);
 }

}
