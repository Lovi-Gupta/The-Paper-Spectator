module news_paper_agency {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	requires java.sql;
	requires javafx.graphics;
	
	opens application to javafx.graphics, javafx.fxml;
	opens paper_master to javafx.graphics, javafx.fxml;
	opens hawker_manager to javafx.graphics, javafx.fxml;
	opens customer_manager to javafx.graphics, javafx.fxml;
	opens billgenerator to javafx.graphics, javafx.fxml;
	opens billcollector to javafx.graphics, javafx.fxml;
	opens billboard to javafx.graphics, javafx.fxml;
	opens aboutUs to javafx.graphics, javafx.fxml;
	opens adminlogin to javafx.graphics, javafx.fxml;
	opens dashboard to javafx.graphics, javafx.fxml;

	
}
