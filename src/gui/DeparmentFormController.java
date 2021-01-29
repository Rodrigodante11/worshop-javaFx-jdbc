package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.entities.Department;
import model.exceptions.ValidationException;
import model.services.DepartmentService;

public class DeparmentFormController implements Initializable{
	private Department entity;
	
	private DepartmentService service;
	
	private List<DataChangeListener> dataChangeListers=new ArrayList<>();

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
	public void setDepartmentService(DepartmentService service) {
		this.service=service;
	}
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListers.add(listener);
	}
	
	
	
	@FXML
	public void onBtSaveAction(ActionEvent event){
		if(entity==null) {
			throw new IllegalStateException("Campo vazio");
		}
		if(service==null) {
			throw new IllegalStateException("Service estava nulo");
		}
		try {
			entity=getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		}catch(ValidationException e) {
			setErrorMessage(e.getErrors());
		}catch(DbException e) {
			Alerts.showAlert("Erro salvando objeto", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notifyDataChangeListeners() {
		for(DataChangeListener listener :dataChangeListers)
		{
			listener.onDataChanged();
		}
		
	}
	private Department getFormData() {
		Department obj=new Department();
		
		 ValidationException exception=new ValidationException("Erro de validacao");
		
		obj.setId(Utils.tryParseToint(txtId.getText()));
		
		if(txtName.getText()==null || txtName.getText().trim().equals("")) {
			exception.addError("name", "Campo nao pode fica vazio");
		}
		obj.setName(txtName.getText());
		
		if(exception.getErrors().size()>0) {
			throw exception;
		}
		return obj;
	}
	@FXML
	public void onBtCancelAction(ActionEvent event){
		
		Utils.currentStage(event).close();
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
	
	private void setErrorMessage(Map<String,String>  errors) {
		Set<String> fields=errors.keySet();
		
		if(fields.contains("name")) {
			labelErrorName.setText(errors.get("name"));
		}
	}

}
