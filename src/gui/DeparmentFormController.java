package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DeparmentFormController implements Initializable{
	private Department entity;

	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	public void setDeparment(Department entity) {
		this.entity=entity;
	}
	
	@FXML
	public void onBtSaveAction(){
		System.out.println("save");
	}
	
	@FXML
	public void onBtCancelAction(){
		System.out.println("cancel");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFielInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 60);
	}
	public void updateFromDate() {
		if(entity==null) {
			throw new IllegalStateException("Enridade esta vazia");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}

}
