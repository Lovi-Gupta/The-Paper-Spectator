package customer_manager;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Arrays;
import java.util.ResourceBundle;

import dashboard.MySqlConnector;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;


public class CustomerManagerViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboArea;

    @FXML
    private DatePicker date;

    @FXML
    private ImageView imgPic;

    @FXML
    private ListView<String> listNewsName;

    @FXML
    private ListView<String> listPrice;

    @FXML
    private ListView<String> listSelName;

    @FXML
    private ListView<String> listSelPrice;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtHawkerName;

    @FXML
    private TextField txtMobile;

    @FXML
    private TextField txtName;

    Connection con;
	PreparedStatement pst;
    
    @FXML
    void doDblListAdd(MouseEvent event) {
    	if(event.getClickCount()==2) {
    		listSelName.getItems().add(listNewsName.getSelectionModel().getSelectedItem());
    		
    		int index = listNewsName.getSelectionModel().getSelectedIndex();
    		listSelPrice.getItems().add(listPrice.getItems().get(index));
    	}
    }

    @FXML
    void doDblRemove(MouseEvent event) {
    	if(event.getClickCount()==2)
    	{
    		int index = listSelName.getSelectionModel().getSelectedIndex();
    		
    		listSelName.getItems().remove(index);
    		listSelPrice.getItems().remove(index);
    	}
    }

    @FXML
    void doDelete(ActionEvent event) {
    	try {
    		pst = con.prepareStatement("delete from customers where mobile=?");
    		pst.setString(1,txtMobile.getText());
    		int x = pst.executeUpdate();
    		if(x!=0) {
    			txtMobile.setText(null);
    			txtAddress.setText(null);
    			txtEmail.setText(null);
    			txtHawkerName.setText(null);
    			txtName.setText(null);
    			date.getEditor().clear();
    			comboArea.getSelectionModel().clearSelection();
    			listSelName.getItems().clear();
    			listSelPrice.getItems().clear();
    		}
    	}
    	catch(Exception exp) {
    		System.out.println(exp.toString());
    	}
    }

    @FXML
    void doSave(ActionEvent event) {
    	try {
    		ObservableList<String> str = listSelName.getItems();
    		String spapers="";
    		for(String x : str) {
    			spapers += x+",";
    		}
    		spapers = spapers.substring(0, spapers.length() - 1);
    		
    		ObservableList<String> str2 = listSelPrice.getItems();
    		String sprices ="";
    		for(String x : str2) {
    			sprices += x+",";
    		}
    		sprices = sprices.substring(0, sprices.length() - 1);
    		
    		LocalDate ld = date.getValue();
    		java.sql.Date dt = java.sql.Date.valueOf(ld);
    		pst = con.prepareStatement("insert into customers values(?,?,?,?,?,?,?,?,?)");
    		pst.setString(1, txtMobile.getText());
    		pst.setString(2, txtName.getText());
    		pst.setString(3, spapers);
    		pst.setString(4, sprices);    		
    		pst.setString(5, comboArea.getSelectionModel().getSelectedItem());
    		pst.setString(6, txtHawkerName.getText());
    		pst.setString(7, txtEmail.getText());
    		pst.setString(8, txtAddress.getText());
    		pst.setDate(9, dt);
   
    		int x=pst.executeUpdate();
    		
    	}
    	catch(Exception exp) {
    		System.out.println(exp.toString());
    	}
    }
    
    void showMsg(String msg)
    {
    	Alert alert = new Alert(AlertType.INFORMATION);
//    	Alert alert = new Alert(AlertType.CONFIRMATION);
//    	Alert alert = new Alert(AlertType.WARNING);
    			
    	alert.setTitle("Alert");    			
    	alert.setHeaderText(msg);
    	alert.showAndWait();
    }

    @FXML
    void doSearch(ActionEvent event) {
try {
    		
    		String str = txtMobile.getText();
    		char c = str.charAt(0);
    		int x = c;
    		if(x<48 || x>57) {
    			return;
    		}
    		
			txtAddress.setText(null);
			txtEmail.setText(null);
			txtHawkerName.setText(null);
			txtName.setText(null);
			date.setValue(null);
			comboArea.setValue(null);
    		listSelName.getItems().clear();
			listSelPrice.getItems().clear();
			
    		pst = con.prepareStatement("select * from customers where mobile = ?");
    		pst.setString(1, txtMobile.getText());
    		ResultSet table = pst.executeQuery();
    		while(table.next()) {
    			txtName.setText(table.getString("customer_name"));
    			txtHawkerName.setText(table.getString("hname"));
    			txtEmail.setText(table.getString("email"));
    			txtAddress.setText(table.getString("address"));
    			comboArea.setValue(table.getString("area"));
    			java.sql.Date dt = table.getDate("startdate");
    			date.setValue(dt.toLocalDate());
    			String spapers = table.getString("spaper");
    			String sprices = table.getString("sprice");
    			
    			String[] sa = spapers.split(",");
    			for(String v : sa) {
    				listSelName.getItems().add(v);
    			}
    			
    			sa = sprices.split(",");
    			for(String v : sa) {
    				listSelPrice.getItems().add(v);
    			}
    			
    			
    		}
    	}
    	catch(Exception exp) {
    		System.out.println(exp.toString());
    	}
    }

    @FXML
    void doSelectHawker(ActionEvent event) {
    	try {
    		txtHawkerName.setText("");
    		String area = comboArea.getSelectionModel().getSelectedItem();
    		pst = con.prepareStatement("select hname from hawkermanager where alloarea like ? OR alloarea like ? ");
    		pst.setString(1,area+'%');
    		pst.setString(2,'%'+(','+area)+'%');
    		ResultSet table = pst.executeQuery();
    		while(table.next()) {
    			txtHawkerName.setText(table.getString("hname"));
    		}
    	}
    	catch(Exception exp) {
    		System.out.println(exp.toString());
    	}
    }

    @FXML
    void doUpdate(ActionEvent event) {
    	try {
    		ObservableList<String> str = listSelName.getItems();
    		String spapers="";
    		for(String x : str) {
    			spapers += x+",";
    		}
    		spapers = spapers.substring(0, spapers.length() - 1);
    		
    		ObservableList<String> str2 = listSelPrice.getItems();
    		String sprices ="";
    		for(String x : str2) {
    			sprices += x+",";
    		}
    		sprices = sprices.substring(0, sprices.length() - 1);
    		
    		LocalDate ld = date.getValue();
    		java.sql.Date dt = java.sql.Date.valueOf(ld);
    		pst = con.prepareStatement("update customers set customer_name=?,spaper=?,sprice=?,area=?,hname=?,email=?,address=?,startdate=? where mobile=?");
    		pst.setString(9, txtMobile.getText());
    		pst.setString(1, txtName.getText());
    		pst.setString(2, spapers);
    		pst.setString(3, sprices);    		
    		pst.setString(4, comboArea.getSelectionModel().getSelectedItem());
    		pst.setString(5, txtHawkerName.getText());
    		pst.setString(6, txtEmail.getText());
    		pst.setString(7, txtAddress.getText());
    		pst.setDate(8, dt);
   
    		int x=pst.executeUpdate();
    		
    		if(x!=0) {
    			showMsg("Updated");
    		}
    	}
    	catch(Exception exp) {
    		System.out.println(exp.toString());
    	}
    }

    @FXML
    void doUploadPic(ActionEvent event) {

    }
    
    void doFillLists() {
    	try {
    		pst = con.prepareStatement("select * from papermanager");
    		ResultSet table = pst.executeQuery();
    		while(table.next()) {
    			listNewsName.getItems().add(table.getString("papername"));
    			listPrice.getItems().add(table.getString("price"));
    		}
    	}
    	catch(Exception exp) {
    		System.out.println(exp.toString());
    	}
    	
    }
    
    void doFillAreas() {
    	try{
			pst=con.prepareStatement("select area from hawkermanager");
			ResultSet table=pst.executeQuery(); //array of objects
			while(table.next())
			{
				comboArea.getItems().add(table.getString("area"));
			}
		}	
		catch(Exception exp)
		{
			System.out.println(exp);
		}
    }
    

    @FXML
    void initialize() {
    	
    	con=MySqlConnector.doConnect();
    	if(con==null)
    			System.out.println("Connection Problem");
    	else
    		System.out.println("Connected");
    	
    	doFillLists();
    	doFillAreas();
    	
        assert comboArea != null : "fx:id=\"comboArea\" was not injected: check your FXML file 'CustomerManagerView.fxml'.";
        assert date != null : "fx:id=\"date\" was not injected: check your FXML file 'CustomerManagerView.fxml'.";
        assert imgPic != null : "fx:id=\"imgPic\" was not injected: check your FXML file 'CustomerManagerView.fxml'.";
        assert listNewsName != null : "fx:id=\"listNewsName\" was not injected: check your FXML file 'CustomerManagerView.fxml'.";
        assert listPrice != null : "fx:id=\"listPrice\" was not injected: check your FXML file 'CustomerManagerView.fxml'.";
        assert listSelName != null : "fx:id=\"listSelName\" was not injected: check your FXML file 'CustomerManagerView.fxml'.";
        assert listSelPrice != null : "fx:id=\"listSelPrice\" was not injected: check your FXML file 'CustomerManagerView.fxml'.";
        assert txtAddress != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'CustomerManagerView.fxml'.";
        assert txtEmail != null : "fx:id=\"txtEmail\" was not injected: check your FXML file 'CustomerManagerView.fxml'.";
        assert txtHawkerName != null : "fx:id=\"txtHawkerName\" was not injected: check your FXML file 'CustomerManagerView.fxml'.";
        assert txtMobile != null : "fx:id=\"txtMobile\" was not injected: check your FXML file 'CustomerManagerView.fxml'.";
        assert txtName != null : "fx:id=\"txtName\" was not injected: check your FXML file 'CustomerManagerView.fxml'.";

    }

}
