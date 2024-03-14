package hawker_manager;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dashboard.MySqlConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class HawkerManagerViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboName;
    
    @FXML
    private ComboBox<String> comboArea;

    @FXML
    private ImageView imgPic;
    
    @FXML
    private Label lblPath;

    @FXML
    private Label lblResp;

    @FXML
    private TextField txtAadhaar;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtAlloArea;

    @FXML
    private TextField txtMobile;
    
    Connection con;
    PreparedStatement pst;
    @FXML
    void doDelete(ActionEvent event) 
    {
    	try {
        	String selname = comboName.getValue();
        	pst=con.prepareStatement("delete from hawkermanager where hname=?");
        	pst.setString(1, selname);
        	int count=pst.executeUpdate();
        	if(count!=0)
        		lblResp.setText(count+ " Records Deleted");
        	
        	else
        		lblResp.setText("Invalid ID");
        	}
        	catch(Exception exp)
        	{
        		System.out.println(exp.toString());
        	}
    }

    @FXML
    void doEnroll(ActionEvent event) 
    {
    	String name=comboName.getValue().toString();
    	String mobile=txtMobile.getText();
    	String address=txtAddress.getText();
    	String area=comboArea.getValue().toString();
    	String alloarea=comboArea.getValue().concat(area);
    	
    	String aadhaar=txtAadhaar.getText();
    	String path=lblPath.getText();
    	
    	
    	try {
    		pst=con.prepareStatement("insert into hawkermanager values(?,?,?,?,?,?,?)");
    		pst.setString(1, name);
    		pst.setString(2, mobile);
    		pst.setString(3, address);
    		pst.setString(4, area);
    		pst.setString(5, alloarea);
    		pst.setString(6, aadhaar);
    		pst.setString(7, path);
    		int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                lblResp.setText("Enrollment successful");

            } else {
                lblResp.setText("Enrollment failed");
            }
    	}
    	catch(Exception exp) {
    		System.out.println(exp);
    	}
    	 
    }

    @FXML
    void doNew(ActionEvent event) 
    {
    	comboName.getSelectionModel().clearSelection();
    	comboArea.getSelectionModel().clearSelection();
    	txtAadhaar.clear();
    	txtAddress.clear();
    	txtMobile.clear();
    	txtAlloArea.clear();
    	lblPath.setText("noPic.jpg");
    	lblResp.setText("New Form");
    }

    @FXML
    void doSearch(ActionEvent event)
    {
    	try {
	    	 pst = con.prepareStatement("SELECT * FROM hawkermanager WHERE hname = ?");
	         String name = comboName.getSelectionModel().getSelectedItem();
	         pst.setString(1, name);
	
	         ResultSet resultSet = pst.executeQuery();
	         if (resultSet.next()) {
	             String sname = resultSet.getString("hname");
	             String mobile = resultSet.getString("mobile");
	             String address = resultSet.getString("address");
	             String Area = resultSet.getString("area");
	             String Aadhaar=resultSet.getString("aadhaarno");
	             String alloareas = resultSet.getString("alloarea");
	             String picPath = resultSet.getString("picpath");
	
	             txtMobile.setText(mobile);
	             txtAddress.setText(address);
	             comboArea.setValue(Area);
	             txtAlloArea.setText(alloareas);
	             txtAadhaar.setText(Aadhaar);
	             lblPath.setText(picPath);
	             imgPic.setImage(new Image(new FileInputStream(picPath)));
	
	             System.out.println("Name: " + sname);
	             System.out.println("Mobile: " + mobile);
	             System.out.println("Address: " + address);
	             System.out.println("Area: " + Area);
	             System.out.println("Aadhaar no.: "+ Aadhaar );
	             System.out.println("Alloareas: " + alloareas);
	             System.out.println("Pic Path: " + picPath);
         }
	         else {
             System.out.println("No records found");
         }
     } catch (Exception e) {
         e.printStackTrace();
         System.out.println("Search failed");
     }
    }

    @FXML
    void doUpdate(ActionEvent event) 
    {
    	String name=comboName.getValue().toString();
    	String mobile=txtMobile.getText();
    	String address=txtAddress.getText();
    	String area=comboArea.getValue().toString();
    	String alloarea=comboArea.getValue().concat(area);
    	String aadhaar=txtAadhaar.getText();
    	String path=lblPath.getText();

        try {
            String query = "UPDATE hawkermanager SET mobile = ?, address = ?, area = ?, alloareas = ?,aadhaarno=?,picpath=? WHERE hname = ?";
            pst = con.prepareStatement(query);
            
            pst.setString(7, name);
    		pst.setString(1, mobile);
    		pst.setString(2, address);
    		pst.setString(3, area);
    		pst.setString(4, alloarea);
    		pst.setString(5, aadhaar);
    		pst.setString(6, path);
            

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                lblResp.setText("Update successful");
            } else {
                lblResp.setText("Update failed");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            lblResp.setText("Update failed");
        }
    }

    @FXML
    void doUploadPic(ActionEvent event) {
    	//to choose file from File Manager
    	FileChooser fileChooser = new FileChooser();
    	 fileChooser.setTitle("Open Resource File");
    	 fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
    	 File selectedFile = fileChooser.showOpenDialog(null);
    	 
    	 if (selectedFile != null) {
    		 lblPath.setText(selectedFile.getPath());
    		 Image img=new Image(selectedFile.getPath());
    		imgPic.setImage(img);
    	 }
    	 
    	 else {
    		 lblPath.setText("noPic.jpg");
    	 }

    }
    
    void doFillRolls() 
    {
    	try {
    		pst = con.prepareStatement("select distinct hname FROM hawkermanager");
            ResultSet table = pst.executeQuery();
            while(table.next()) {
            	String name = table.getString("hname");
            	comboName.getItems().add(name);
            }
            
           //=========================
          pst = con.prepareStatement("select distinct area FROM hawkermanager");
          ResultSet table1 = pst.executeQuery();
          while(table1.next()) {
          	String areas = table1.getString("area");
          	comboArea.getItems().add(areas);
          }
            		
    	}
    	catch(Exception exp) {
    		System.out.println(exp);
    	}
       
    }

    @FXML
    void initialize() {
    	
    	lblPath.setText("noPic.jpg");
    	
    	con=MySqlConnector.doConnect();
    	if(con==null)
    		System.out.println("Connection Problem");
    	else
    		System.out.println("Connected");
    	
    	doFillRolls();
    	comboArea.getSelectionModel();
    	
        assert comboName != null : "fx:id=\"comboName\" was not injected: check your FXML file 'HawkerManagerView.fxml'.";
        assert imgPic != null : "fx:id=\"imgPic\" was not injected: check your FXML file 'HawkerManagerView.fxml'.";
        assert txtAadhaar != null : "fx:id=\"txtAadhaar\" was not injected: check your FXML file 'HawkerManagerView.fxml'.";
        assert txtAddress != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'HawkerManagerView.fxml'.";
        assert txtAlloArea != null : "fx:id=\"txtAlloArea\" was not injected: check your FXML file 'HawkerManagerView.fxml'.";
        assert comboArea != null : "fx:id=\"comboArea\" was not injected: check your FXML file 'HawkerManagerView.fxml'.";
        assert txtMobile != null : "fx:id=\"txtMobile\" was not injected: check your FXML file 'HawkerManagerView.fxml'.";
        
        

    }

}
