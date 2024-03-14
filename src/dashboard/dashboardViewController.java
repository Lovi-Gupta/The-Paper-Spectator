package dashboard;

import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class dashboardViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private Button btnlogout;

    @FXML
    void doLogout(ActionEvent event) {
    	try{
    		Parent root=FXMLLoader.load(getClass().getResource("/adminlogin/adminloginView.fxml")); 
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
						 
			Scene scene1=(Scene)btnlogout.getScene();
			scene1.getWindow().hide();
			 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void openAboutUs(ActionEvent event) {
    	try{
    		Parent root=FXMLLoader.load(getClass().getResource("/aboutUs/aboutUsView.fxml")); 
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void openBillCollector(ActionEvent event) {
    	try{
    		Parent root=FXMLLoader.load(getClass().getResource("/billcollector/billcollectorView.fxml")); 
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void openBillGenerator(ActionEvent event) {
    	try{
    		Parent root=FXMLLoader.load(getClass().getResource("/billgenerator/billgeneratorView.fxml")); 
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void openBillStatus(ActionEvent event) {
    	try{
    		Parent root=FXMLLoader.load(getClass().getResource("/billboard/billboardView.fxml")); 
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

    }

    @FXML
    void openCustomerManager(ActionEvent event) {
    	try{
    		Parent root=FXMLLoader.load(getClass().getResource("/customer_manager/CustomerManagerView.fxml")); 
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void openCustomerSearch(ActionEvent event) {

    }

    @FXML
    void openDisplayHawker(ActionEvent event) {

    }

    @FXML
    void openHawkerManager(ActionEvent event) {
    	try{
    		Parent root=FXMLLoader.load(getClass().getResource("/hawker_manager/HawkerManagerView.fxml")); 
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void openPaperMaster(ActionEvent event) {
    	try{
    		Parent root=FXMLLoader.load(getClass().getResource("/paper_master/PprMasterView.fxml")); 
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

    }

    @FXML
    void initialize() {

    }

}
