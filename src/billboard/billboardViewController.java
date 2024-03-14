package billboard;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import dashboard.MySqlConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class billboardViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckBox chkpaid;

    @FXML
    private CheckBox chkpending;

    @FXML
    private TableView<BillBean> tbldata;

    @FXML
    private TextField txtAmt;

    @FXML
    private TextField txtMobile;
    
    ObservableList<BillBean> ary = FXCollections.observableArrayList();
    
    Connection con;
	PreparedStatement pst;
	
	ObservableList<BillBean> doFetchPaid() {
    	try {
    		ary.clear();
    		pst = con.prepareStatement("select * from bills where billstatus=1 ORDER BY datefrom DESC");
    		ResultSet table = pst.executeQuery();
    		float total=0;
    		while(table.next()) {
    			String mobile=table.getString("mobile");
    			String datefrom=String.valueOf(table.getDate("datefrom"));
    			String dateto=String.valueOf(table.getDate("dateto"));
    			String bill=table.getString("bill");
    			String billstatus="Paid";
    			
    			total += table.getFloat("bill");
    			
    			BillBean ref = new BillBean(mobile, datefrom, dateto, bill, billstatus);
    			ary.add(ref);
    		}
    		txtAmt.setText(String.valueOf(total));
    	}
    	catch(Exception exp) {
    		System.out.println(exp.toString());
    	}
    	return ary;
    }
    ObservableList<BillBean> doFetchPending(){
    	try {
    		ary.clear();
    		pst = con.prepareStatement("select * from bills where billstatus=0 ORDER BY datefrom DESC");
    		ResultSet table = pst.executeQuery();
    		float total=0;
    		while(table.next()) {
    			String mobile=table.getString("mobile");
    			String datefrom=String.valueOf(table.getDate("datefrom"));
    			String dateto=String.valueOf(table.getDate("dateto"));
    			String bill=table.getString("bill");
    			String billstatus="Pending";
    			
    			total += table.getFloat("bill");
    			
    			BillBean ref = new BillBean(mobile, datefrom, dateto, bill, billstatus);
    			ary.add(ref);
    		}
    		txtAmt.setText(String.valueOf(total));
    	}
    	catch(Exception exp) {
    		System.out.println(exp.toString());
    	}
    	return ary;
    }
    ObservableList<BillBean> doFetchAll(){
    	try {
    		ary.clear();
    		pst = con.prepareStatement("select * from bills ORDER BY datefrom DESC");
    		ResultSet table = pst.executeQuery();
    		float total=0;
    		while(table.next()) {
    			String mobile=table.getString("mobile");
    			String datefrom=String.valueOf(table.getDate("datefrom"));
    			String dateto=String.valueOf(table.getDate("dateto"));
    			String bill=table.getString("bill");
    			int status=table.getInt("billstatus");
    			String billstatus;
    			if(status==1)
    				billstatus="Paid";
    			else
    				billstatus="Pending";
    			
    			total += table.getFloat("bill");
    			
    			BillBean ref = new BillBean(mobile, datefrom, dateto, bill, billstatus);
    			ary.add(ref);
    		}
    		txtAmt.setText(String.valueOf(total));
    	}
    	catch(Exception exp) {
    		System.out.println(exp.toString());
    	}
    	return ary;
    }
    ObservableList<BillBean> doFetchMobile(){
    	try {
    		ary.clear();
    		pst = con.prepareStatement("select * from bills where mobile=? ORDER BY datefrom DESC");
    		pst.setString(1, txtMobile.getText());
    		ResultSet table = pst.executeQuery();
    		float total=0;
    		while(table.next()) {
    			String mobile=table.getString("mobile");
    			String datefrom=String.valueOf(table.getDate("datefrom"));
    			String dateto=String.valueOf(table.getDate("dateto"));
    			String bill=table.getString("bill");
    			int status=table.getInt("billstatus");
    			String billstatus;
    			if(status==1)
    				billstatus="Paid";
    			else
    				billstatus="Pending";
    			
    			total += table.getFloat("bill");
    			
    			BillBean ref = new BillBean(mobile, datefrom, dateto, bill, billstatus);
    			ary.add(ref);
    		}
    		txtAmt.setText(String.valueOf(total));
    	}
    	catch(Exception exp) {
    		System.out.println(exp.toString());
    	}
    	return ary;
    }


    @FXML
    void doExport(ActionEvent event) {
    	try {
    		writeExcel();
//    		txtPname.setText("Exported to excel....");
    		System.out.println("Exported");
    	}
    	catch(Exception exp) {
    		System.out.println(exp.toString());
    	}
    }
    public void writeExcel() throws IOException {
    	Writer writer = null;
    	try {
    		File file = new File("F:\\ecllipse-workspace\\news_paper_agency\\src\\billboard\\Users.csv");
    		writer = new BufferedWriter(new FileWriter(file));
    		String text="Mobile,Starting Date,Ending Date,Amount,Bill Status\n";
    		writer.write(text);
    		for(BillBean p : ary) {
    			text = p.getMobile() +"," + p.getDatefrom() + "," + p.getDateto()+ "," + p.getBill() +"," + p.getBillstatus() + "\n";
    			writer.write(text);
    		}
    	}
    	catch(Exception exp) {
    		exp.printStackTrace();
    	}
    	finally {
    		writer.flush();
    		writer.close();
    	}
    }

    @FXML
    void doSearch(ActionEvent event) {
    	tbldata.getColumns().clear();
    	TableColumn<BillBean,String> mobile = new TableColumn<BillBean,String>("Mobile");
    	mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    	mobile.setMinWidth(111);
    	
    	TableColumn<BillBean,String> datefrom = new TableColumn<BillBean,String>("Starting Date");
    	datefrom.setCellValueFactory(new PropertyValueFactory<>("datefrom"));
    	datefrom.setMinWidth(110);
    	
    	TableColumn<BillBean,String> dateto = new TableColumn<BillBean,String>("Ending Date");
    	dateto.setCellValueFactory(new PropertyValueFactory<>("dateto"));
    	dateto.setMinWidth(110);
    	
    	TableColumn<BillBean,String> bill = new TableColumn<BillBean,String>("Amount");
    	bill.setCellValueFactory(new PropertyValueFactory<>("bill"));
    	bill.setMinWidth(102);
    	
    	TableColumn<BillBean,String> billstatus = new TableColumn<BillBean,String>("Bill Status");
    	billstatus.setCellValueFactory(new PropertyValueFactory<>("billstatus"));
    	billstatus.setMinWidth(91);
    	
    	tbldata.getColumns().clear();
    	tbldata.getColumns().add(mobile);
    	tbldata.getColumns().add(datefrom);
    	tbldata.getColumns().add(dateto);
    	tbldata.getColumns().add(bill);
    	tbldata.getColumns().add(billstatus);
    	if(chkpaid.isSelected() && chkpending.isSelected())
    		tbldata.setItems(doFetchAll());
    	else if(chkpaid.isSelected())
    		tbldata.setItems(doFetchPaid());
    	else if(chkpending.isSelected())
    		tbldata.setItems(doFetchPending());
    	else {
    		tbldata.setItems(null);
    		txtAmt.setText(null);
    	}
    	
    }

    @FXML
    void dohistory(ActionEvent event) {
    	TableColumn<BillBean,String> mobile = new TableColumn<BillBean,String>("Mobile");
    	mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    	mobile.setMinWidth(111);
    	
    	TableColumn<BillBean,String> datefrom = new TableColumn<BillBean,String>("Starting Date");
    	datefrom.setCellValueFactory(new PropertyValueFactory<>("datefrom"));
    	datefrom.setMinWidth(110);
    	
    	TableColumn<BillBean,String> dateto = new TableColumn<BillBean,String>("Ending Date");
    	dateto.setCellValueFactory(new PropertyValueFactory<>("dateto"));
    	dateto.setMinWidth(110);
    	
    	TableColumn<BillBean,String> bill= new TableColumn<BillBean,String>("Amount");
    	bill.setCellValueFactory(new PropertyValueFactory<>("bill"));
    	bill.setMinWidth(106);
    	
    	TableColumn<BillBean,String> billstatus = new TableColumn<BillBean,String>("Bill Status");
    	billstatus.setCellValueFactory(new PropertyValueFactory<>("billstatus"));
    	billstatus.setMinWidth(91);
    	
    	tbldata.getColumns().clear();
    	tbldata.getColumns().add(mobile);
    	tbldata.getColumns().add(datefrom);
    	tbldata.getColumns().add(dateto);
    	tbldata.getColumns().add(bill);
    	tbldata.getColumns().add(billstatus);
    	
    	tbldata.setItems(doFetchMobile());
    	
    }
    
    
    @FXML
    void initialize() {
    	
    	con=MySqlConnector.doConnect();// same as driver manager class
    	if(con==null)
    		System.out.println("Connection Problem");
    	else
    		System.out.println("Connected");
    	
        assert chkpaid != null : "fx:id=\"chkpaid\" was not injected: check your FXML file 'billboardView.fxml'.";
        assert chkpending != null : "fx:id=\"chkpending\" was not injected: check your FXML file 'billboardView.fxml'.";
        assert tbldata != null : "fx:id=\"tbldata\" was not injected: check your FXML file 'billboardView.fxml'.";
        assert txtAmt != null : "fx:id=\"txtAmt\" was not injected: check your FXML file 'billboardView.fxml'.";
        assert txtMobile != null : "fx:id=\"txtMobile\" was not injected: check your FXML file 'billboardView.fxml'.";

    }

}
