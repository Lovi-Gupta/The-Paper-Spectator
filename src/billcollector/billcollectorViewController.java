package billcollector;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import dashboard.MySqlConnector;

public class billcollectorViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker dateEnd;

    @FXML
    private DatePicker dateStart;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtMobile;
    
    Connection con;
	PreparedStatement pst;

    @FXML
    void doBillDeatils(ActionEvent event) {
    	try {
    		txtAmount.setText(null);
    		dateEnd.setValue(null);
    		dateStart.setValue(null);
    		pst = con.prepareStatement("SELECT * FROM bills WHERE mobile=? And billstatus=0 order by datefrom DESC");
    		pst.setString(1,txtMobile.getText());
    		ResultSet table = pst.executeQuery();
    		float amount=0;
    		while(table.next()) {
    			java.sql.Date dt = table.getDate("datefrom");
    			dateStart.setValue(dt.toLocalDate());
    			amount += Float.parseFloat(table.getString("bill"));
    		}
    		
    		pst = con.prepareStatement("SELECT * FROM bills WHERE mobile=? And billstatus=0 order by dateto DESC limit 1");
    		pst.setString(1,txtMobile.getText());
    		table = pst.executeQuery();
    		while(table.next()) {
    			java.sql.Date dt = table.getDate("dateto");
    			dateEnd.setValue(dt.toLocalDate());
    		}
    		if(amount==0) {
    			showMsg("No pending bill");
    			return;
    		}
    		txtAmount.setText(String.valueOf(amount));
    	}
    	catch(Exception exp) {
    		System.out.println(exp.toString());
    	}
    }

    @FXML
    void doUpdate(ActionEvent event) {
    	try {
    		String str=txtMobile.getText();
    		char c=str.charAt(0);
    		int x=c;
    		if(x<48 || x>57) {
    			return;
    		}
    		pst=con.prepareStatement("update bills set billstatus=1 where mobile=?");
    		pst.setString(1, str);
    		pst.executeUpdate();
    		
    		showMsg("Saved");
    		
    		txtMobile.setText(null);
    		txtAmount.setText(null);
    		dateStart.getEditor().clear();
    		dateEnd.getEditor().clear();
    	}
    	catch(Exception exp) {
    		System.out.println(exp.toString());
    	}
    	
    	
    }
    
    void showMsg(String msg) {
    	Alert alert=new Alert(AlertType.INFORMATION);
    	alert.setTitle("Alert");    			
    	alert.setHeaderText(msg);
    	alert.showAndWait();
    }

    @FXML
    void initialize() {
    	
    	con=MySqlConnector.doConnect();// same as driver manager class
    	if(con==null)
    		System.out.println("Connection Problem");
    	else
    		System.out.println("Connected");
    	
        assert txtAmount != null : "fx:id=\"txtAmount\" was not injected: check your FXML file 'billcollectorView.fxml'.";
        assert dateEnd != null : "fx:id=\"dateEnd\" was not injected: check your FXML file 'billcollectorView.fxml'.";
        assert dateStart != null : "fx:id=\"dateStart\" was not injected: check your FXML file 'billcollectorView.fxml'.";
        assert txtMobile != null : "fx:id=\"txtMobile\" was not injected: check your FXML file 'billcollectorView.fxml'.";

    }

}
