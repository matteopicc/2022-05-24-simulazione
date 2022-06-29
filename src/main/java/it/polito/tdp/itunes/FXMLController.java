/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.itunes;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.itunes.model.Adiacenza;
import it.polito.tdp.itunes.model.Genre;
import it.polito.tdp.itunes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaLista"
    private Button btnCreaLista; // Value injected by FXMLLoader

    @FXML // fx:id="btnMassimo"
    private Button btnMassimo; // Value injected by FXMLLoader

    @FXML // fx:id="cmbCanzone"
    private ComboBox<?> cmbCanzone; // Value injected by FXMLLoader

    @FXML // fx:id="cmbGenere"
    private ComboBox<Genre> cmbGenere; // Value injected by FXMLLoader

    @FXML // fx:id="txtMemoria"
    private TextField txtMemoria; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void btnCreaLista(ActionEvent event) {

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	Genre g = cmbGenere.getValue();
    	if(g == null) {
    		txtResult.appendText("Errore: scegliere un genere dal menu\n");
    		return;
    	}
    	this.model.creaGrafo(g);
    	txtResult.appendText("Grafo creato!\n");
    	txtResult.appendText("#VERTICI:"+this.model.nVertici()+"\n");
    	txtResult.appendText("#ARCHI:"+this.model.nArchi()+"\n");
    	

    }

    @FXML
    void doDeltaMassimo(ActionEvent event) {
    	txtResult.clear();
    	if(this.model.grafoCreato() == false) {
    		txtResult.appendText("Prima di esercitare questa funzione bisogna creare il grafico\n");
    		return;
    	}
    	for(Adiacenza a : this.model.getDeltaMassimo()) {
    		txtResult.appendText(a.toString()+"\n");
    	}
    	
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaLista != null : "fx:id=\"btnCreaLista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnMassimo != null : "fx:id=\"btnMassimo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCanzone != null : "fx:id=\"cmbCanzone\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbGenere != null : "fx:id=\"cmbGenere\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMemoria != null : "fx:id=\"txtMemoria\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	cmbGenere.getItems().addAll(this.model.getGeneri());
    }

}
