package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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
	public void onBtNewAction(ActionEvent event) {
	   Stage parentStage=Utils.currentStage(event);
	   createDialogForm("/gui/DeparmentForm.fxml",parentStage);
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
	private void createDialogForm(String absoluteName,Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane=loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre com os dados do departamento");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
			
			
		
			
		}catch(IOException e)
		{
			Alerts.showAlert("Io Exception", "Erro carregando a Pagina", e.getMessage(), AlertType.ERROR);
		}
	}

}
