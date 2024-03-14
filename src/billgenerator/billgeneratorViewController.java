package billgenerator;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import dashboard.MySqlConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class billgeneratorViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboMobile;

    @FXML
    private DatePicker dateEnd;

    @FXML
    private DatePicker dateStart;

    @FXML
    private TextField txtMissed;

    @FXML
    private TextField txtPaper;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtTotal;

    @FXML
    private TextField txtTotalAmount;
    
    Connection con;
    PreparedStatement pst;

    @FXML
    void doFetch(ActionEvent event) {
    	try {
    		txtTotalAmount.setText(null);
    		String str = comboMobile.getSelectionModel().getSelectedItem();
    		char c=str.charAt(0);
    		int x=c;
    		if(x<48 || x>57) {
    			return;
    		}
    		
    		pst = con.prepareStatement("select * from customers where mobile=?");
    		pst.setString(1, str);
    		ResultSet table = pst.executeQuery();
    		while(table.next()) {
    			txtPaper.setText(table.getString("spaper"));
    			txtPrice.setText(table.getString("sprice"));
    		}
    		String [] arr=txtPrice.getText().split(",");
    		
    		float amount=0;
    		for(String a : arr) {
    			amount+=Float.parseFloat(a);
    		}
    		txtTotal.setText(String.valueOf(amount));
    	}
    	catch(Exception exp) {
    		System.out.println(exp.toString());
    	}
    	
    }
    
    @FXML
    void doLastBill(ActionEvent event) {
    	try {
    		String str = comboMobile.getSelectionModel().getSelectedItem();
    		char c = str.charAt(0);
    		int z = c;
    		if(z<48 || z>57) {
    			return;
    		}
    		
    		pst = con.prepareStatement("Select * FROM bills WHERE mobile=? ORDER BY dateto DESC LIMIT 1");
    		pst.setString(1,comboMobile.getSelectionModel().getSelectedItem());
    		ResultSet table = pst.executeQuery();
    		int count=0;
    		while(table.next()) {
    			count++;
    		}
    		if(count==0) {
    			pst = con.prepareStatement("select * from customers where mobile=?");
    			pst.setString(1,comboMobile.getSelectionModel().getSelectedItem());
    			ResultSet set = pst.executeQuery();
    			while(set.next()) {
    				java.sql.Date dt = set.getDate("startdate");
    				dateStart.setValue(dt.toLocalDate());
    			}
    		}
    		else {
    			pst = con.prepareStatement("Select * FROM bills WHERE mobile=? ORDER BY dateto DESC LIMIT 1");
        		pst.setString(1,comboMobile.getSelectionModel().getSelectedItem());
        		table = pst.executeQuery();

    			while(table.next()) {
    				java.sql.Date dt = table.getDate("dateto");
//    				System.out.println(dt.toLocalDate());
    				dateStart.setValue(dt.toLocalDate());
    			}
    		}
    	}
    	catch(SQLException exp){
    		System.out.println(exp.toString());
    	}
    }

    @FXML
    void doSave(ActionEvent event) {
    	try {
    		String str = comboMobile.getSelectionModel().getSelectedItem();
    		char c=str.charAt(0);
    		int z=c;
    		
    		if(z<48 || z>57) {
    			return;
    		}
    		
    		LocalDate ld1=dateStart.getValue();
    		java.sql.Date startingDate =java.sql.Date.valueOf(ld1);
    		
    		LocalDate ld2=dateEnd.getValue();
    		java.sql.Date endDate =java.sql.Date.valueOf(ld2);
    		
    		float daysDifference = Float.parseFloat(String.valueOf(ChronoUnit.DAYS.between(ld1, ld2)));
    	    float missedDays = Float.parseFloat(txtMissed.getText());
    	    float totalCost = Float.parseFloat(txtTotal.getText());

    	    float x = (daysDifference - missedDays) * totalCost;
    	    txtTotalAmount.setText(String.valueOf(x));
    		
    		pst = con.prepareStatement("insert into bills(mobile,datefrom,dateto,bill) values(?,?,?,?)");
    		pst.setString(1,comboMobile.getSelectionModel().getSelectedItem());
    		pst.setDate(2,startingDate);
    		pst.setDate(3, endDate);
    		pst.setString(4,txtTotalAmount.getText());
    		
    		pst.executeUpdate();
    		
    		dateEnd.getEditor().clear();
    		dateStart.getEditor().clear();
    		txtTotal.setText(null);
    		txtMissed.setText(null);
    		txtPaper.setText(null);
    		txtPrice.setText(null);
    		
    	}
    	catch(Exception exp) {
    		System.out.println(exp.toString());
    	}
    }
    
    void doFillMobile() {
    	try {
    		pst = con.prepareStatement("select * from customers");
    		ResultSet table = pst.executeQuery();
    		while(table.next()) {
    			comboMobile.getItems().add(table.getString("mobile"));
    		}
    	}
    	catch(Exception exp) {
    		System.out.println(exp.toString());
    	}
    }
    
    @FXML
    void initialize() {
    	
    	con=MySqlConnector.doConnect();// same as driver manager class
    	if(con==null)
    		System.out.println("Connection Problem");
    	else
    		System.out.println("Connected");
    	
    	doFillMobile();
    	
        assert comboMobile != null : "fx:id=\"comboMobile\" was not injected: check your FXML file 'billgeneratorView.fxml'.";
        assert dateEnd != null : "fx:id=\"dateEnd\" was not injected: check your FXML file 'billgeneratorView.fxml'.";
        assert dateStart != null : "fx:id=\"dateStart\" was not injected: check your FXML file 'billgeneratorView.fxml'.";
        assert txtMissed != null : "fx:id=\"txtMissed\" was not injected: check your FXML file 'billgeneratorView.fxml'.";
        assert txtPaper != null : "fx:id=\"txtPaper\" was not injected: check your FXML file 'billgeneratorView.fxml'.";
        assert txtPrice != null : "fx:id=\"txtPrice\" was not injected: check your FXML file 'billgeneratorView.fxml'.";
        assert txtTotal != null : "fx:id=\"txtTotal\" was not injected: check your FXML file 'billgeneratorView.fxml'.";
        assert txtTotalAmount != null : "fx:id=\"txtTotalAmount\" was not injected: check your FXML file 'billgeneratorView.fxml'.";

    }

}
