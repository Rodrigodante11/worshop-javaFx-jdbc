package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable{
	private DepartmentService service;
	
	@FXML
	private TableView<Department> tableViewDeparment;

	@FXML
	private TableColumn<Department,Integer> tableColumId;
	
	@FXML
	private TableColumn<Department,String> tableColumName;
	
	@FXML
	private Button btNew;
	
	private ObservableList<Department> obsList;
	
	@FXML
	public void onBtNewAction() {
		System.out.println("teste");
	}
	public void setDepartmentService(DepartmentService service) {
		this.service=service;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		
		
	}
	private void initializeNodes() {
		tableColumId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		Stage stage=(Stage)Main.getMainScene().getWindow();
		tableViewDeparment.prefHeightProperty().bind(stage.heightProperty());//acompanhar a janela
	}
	
	public void updateTableView() throws IllegalAccessException {
		if(service ==null)
		{
			throw new IllegalAccessException("Servico esta vazio");
		}
		List<Department> listaa=service.findAll();
		obsList=FXCollections.observableArrayList(listaa);
		tableViewDeparment.setItems(obsList);
	}

}
