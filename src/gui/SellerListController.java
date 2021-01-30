package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Seller;
import model.services.SellerService;

public class SellerListController implements Initializable, DataChangeListener {
	private SellerService service;

	@FXML
	private TableView<Seller> tableViewDeparment;

	@FXML
	private TableColumn<Seller, Integer> tableColumId;

	@FXML
	private TableColumn<Seller, String> tableColumName;

	@FXML
	private TableColumn<Seller, Seller> tableColumnEDIT;

	@FXML
	private TableColumn<Seller, Seller> tableColumnREMOVE;

	@FXML
	private Button btNew;

	private ObservableList<Seller> obsList;

	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Seller obj = new Seller();
		createDialogForm(obj, "/gui/SellerForm.fxml", parentStage);
	}

	public void setSellerService(SellerService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();

	}

	private void initializeNodes() {
		tableColumId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumName.setCellValueFactory(new PropertyValueFactory<>("name"));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDeparment.prefHeightProperty().bind(stage.heightProperty());// acompanhar a janela
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Servico esta vazio");
		}
		List<Seller> listaa = service.findAll();
		obsList = FXCollections.observableArrayList(listaa);
		tableViewDeparment.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
		
	}

	private void createDialogForm(Seller obj, String absoluteName, Stage parentStage) {
		/*	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			DeparmentFormController controller = loader.getController();
			controller.setDeparment(obj);
			controller.setSellerService(new SellerService());
			controller.subscribeDataChangeListener(this);
			controller.updateFromDate();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre com os dados");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			Alerts.showAlert("Io Exception", "Erro carregando a Pagina", e.getMessage(), AlertType.ERROR);
		}*/
	}

	@Override
	public void onDataChanged() {

		updateTableView();
	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("editar");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/SellerForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}

			
		});
	}
	private void removeEntity(Seller obj) {
		
		Optional<ButtonType> result=Alerts.showConfirmation("Confirmacao" , "Tem certeza ?");
		
		if(result.get()==ButtonType.OK) {
			if(service==null) {
				throw new IllegalStateException("Servico esta vazio");
			}
			try {
				service.remove(obj);
				updateTableView();
			}catch(DbIntegrityException e) {
				Alerts.showAlert("Erro removendo objeto", null, e.getMessage(), AlertType.ERROR);
			}
			
		}
		
	}

}
