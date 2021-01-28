package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;

public class DepartmentListController implements Initializable{
	
	@FXML
	private TableView<Department> tableViewDeparment;

	@FXML
	private TableColumn<Department,Integer> tableColumId;
	
	@FXML
	private TableColumn<Department,String> tableColumName;
	
	@FXML
	private Button btNew;
	
	@FXML
	public void onBtNewAction() {
		System.out.println("teste");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		
		
	}
	private void initializeNodes() {
		tableColumId.setCellFactory(new PropertyValueFactory("id"));
		tableColumName.setCellFactory(new PropertyValueFactory("name"));
		
		Stage stage=(Stage)Main.getMainScene().getWindow();
		tableViewDeparment.prefHeightProperty().bind(stage.heightProperty());
	}

}
