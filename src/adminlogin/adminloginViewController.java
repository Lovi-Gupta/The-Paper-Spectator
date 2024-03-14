package adminlogin;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class adminloginViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;
    
    private String txtuser="lovigupta";
    
    private String txtpass="mypass";

    @FXML
    void doLogin(ActionEvent event) {
    	if(txtUsername.getText().equals(txtuser)&& txtPassword.getText().equals(txtpass)) {
    		try{
        		Parent root=FXMLLoader.load(getClass().getResource("/dashboard/dashboardView.fxml")); 
    			Scene scene = new Scene(root);
    			Stage stage=new Stage();
    			stage.setScene(scene);
    			stage.show();
    			
    			//to hide the opened window
    			 
    			Scene scene1=(Scene)txtPassword.getScene();
    			   scene1.getWindow().hide();
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    	}
    	else {
    		showMsg("Wrong Passcode!");
    	}
    }
    
    void showMsg(String msg)
    {
    	Alert alert = new Alert(AlertType.INFORMATION);
//    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	//Alert alert = new Alert(AlertType.WARNING);
    			
    	alert.setTitle("Alert");
    	//alert.setTitle(null);
    			
    	alert.setHeaderText(msg);
//    	alert.setContentText(msg);

    	alert.showAndWait();
    }
    
    
    @FXML
    void initialize() {
        assert txtPassword != null : "fx:id=\"txtPassword\" was not injected: check your FXML file 'adminloginView.fxml'.";
        assert txtUsername != null : "fx:id=\"txtUsername\" was not injected: check your FXML file 'adminloginView.fxml'.";

    }

}
