package paper_master;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import dashboard.MySqlConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PprMasterViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private Label lblResp;

    @FXML
    private ComboBox<String> comboPaper;

    @FXML
    private TextField txtPrice;
    
    Connection con;
    PreparedStatement pst;
    
    @FXML
    void doClear(ActionEvent event) 
    {
    	 comboPaper.getSelectionModel().clearSelection();
    	    txtPrice.clear();
    	    lblResp.setText("Papers clear");
    }
    

    @FXML
    void doAvail(ActionEvent event) 
    {
    	String  papr = comboPaper.getSelectionModel().getSelectedItem();
        float prc = Float.parseFloat(txtPrice.getText());
        
        try {
                pst = con.prepareStatement("insert into papermanager value(?,?)");
                pst.setString(1, papr);
                pst.setFloat(2,prc);
                pst.executeUpdate();
                System.out.println(papr+","+prc);
                lblResp.setText("Successfully Saved");
         } 
         catch (SQLException ex) 
         {
            	ex.printStackTrace();
         }
            
    }

    @FXML
    void doEdit(ActionEvent event) 
    {
    	String  papr = comboPaper.getSelectionModel().getSelectedItem();
        float prc = Float.parseFloat(txtPrice.getText());
        
        try {
                pst = con.prepareStatement("update papermanager set price=? where papername=?");
                pst.setString(2, papr);
                pst.setFloat(1,prc);
                int count=pst.executeUpdate();
        		if(count!=0) {
        			lblResp.setText("Records Updated "+ count);
        		}
        		else {
        			lblResp.setText("Invalid ID");
        		}
              
         } 
         catch (SQLException ex) 
         {
            	ex.printStackTrace();
         }
    }
    
    
    
    @FXML
    void doFillRoll(ActionEvent event) 
    {
    	String Item=comboPaper.getSelectionModel().getSelectedItem();
    	comboPaper.setAccessibleText(Item);
    }

    @FXML
    void doEliminate(ActionEvent event)
    {
    	String  papr=comboPaper.getSelectionModel().getSelectedItem();
	    try {
		     pst=con.prepareStatement("delete from papermanager where papername=?");
		     pst.setString(1,papr);
		     
		     int count=pst.executeUpdate();
	    		if(count!=0) {
	    			lblResp.setText("Record Deleted "+ count);
	    		}
	    		else {
	    			lblResp.setText("Invalid ID");
	    		}
	    }
	    catch(Exception exp) {
	    	System.out.println(exp);
	    }
    }

    @FXML
    void doSearch(ActionEvent event) 
    {
    	String combo=comboPaper.getSelectionModel().getSelectedItem();
    	if(combo.equals("Dainik Jagran"))
    	{
    		txtPrice.setText("4.5");
    	}
    	if(combo.equals("The Tribune"))
    	{
    		txtPrice.setText("5");
    	}
    	if(combo.equals("Punjabi Tribune"))
    	{
    		txtPrice.setText("3.5");
    	}
    	if(combo.equals("Punjab kesari"))
    	{
    		txtPrice.setText("5");
    	}
    	if(combo.equals("Jagbani"))
    	{
    		txtPrice.setText("4");
    	}
    	if(combo.equals("Ajit"))
    	{
    		txtPrice.setText("4.5");
    	}
    	if(combo.equals("Azad Soch"))
    	{
    		txtPrice.setText("5");
    	}
    }

    @FXML
    void initialize() {
    	
    	con=MySqlConnector.doConnect();// same as driver manager class
    	if(con==null)
    		System.out.println("Connection Problem");
    	else
    		System.out.println("Connected");
    	
    	ArrayList<String>items=new ArrayList<String>(Arrays.asList("Dainik Jagran","The Tribune","Punjabi Tribune","Punjab kesari","Jagbani","Ajit","Azad Soch"));
    	comboPaper.getItems().addAll(items);
    	
        assert comboPaper != null : "fx:id=\"comboPaper\" was not injected: check your FXML file 'PprMasterView.fxml'.";
        assert txtPrice != null : "fx:id=\"txtPrice\" was not injected: check your FXML file 'PprMasterView.fxml'.";

    }

}
