package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

/**
 * This is the controller class, it has all the important functions
 * @author pascaru
 */
public class Controller implements Initializable{
	
	/**
	 * This class is needed to use the table [not sure if it can be moved in a separate file, will try later]
	 * @author pascaru
	 */
	public class tableRow{
		private SimpleStringProperty title;
		private SimpleStringProperty username;
		private SimpleStringProperty url;
		private SimpleStringProperty notes;
		private SimpleStringProperty lastModified;
		public tableRow(String title, String username, String url, String notes, String lastModified) {
			this.title = new SimpleStringProperty(title);
			this.username = new SimpleStringProperty(username);
			this.url = new SimpleStringProperty(url);
			this.notes = new SimpleStringProperty(notes);
			this.lastModified = new SimpleStringProperty(lastModified);
		}
		public StringProperty titleProperty() {
			return title;
		}
		public StringProperty usernameProperty() {
			return username;
		}
		public StringProperty urlProperty() {
			return url;
		}
		public StringProperty notesProperty() {
			return notes;
		}
		public StringProperty lastModifiedProperty() {
			return lastModified;
		}
		public void setTitle(String title) {
			this.title = new SimpleStringProperty(title);
		}
		public void setUsername(String username) {
			this.username = new SimpleStringProperty(username);
		}
		public void setUrl(String url) {
			this.url = new SimpleStringProperty(url);
		}
		public void setNotes(String notes) {
			this.notes = new SimpleStringProperty(notes);
		}
		public void setLastModified(String lastModified) {
			this.lastModified = new SimpleStringProperty(lastModified);
		}
	}
	
	// This is stuff that needed to be injected from the FXML file
	
	@FXML
	private AnchorPane mainAnchorPane;
	
	@FXML
	private SplitPane mainPane;
	
	@FXML
	private AnchorPane loginPane;
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Button loginButton;
	@FXML
	private Button newUserButton;
	
	@FXML
	private AnchorPane fileSelectPane;
	@FXML
	private Button connectButton;
	@FXML
	private Button createButton;
	@FXML
	private Button openButton;
	
	@FXML
	private SplitPane mainSplitPane;
	
	@FXML 
	private TableView<tableRow> table;
	@FXML
	private TableColumn<tableRow, String> title;
	@FXML
	private TableColumn<tableRow, String> username;
	@FXML
	private TableColumn<tableRow, String> url;
	@FXML
	private TableColumn<tableRow, String> notes;
	@FXML
	private TableColumn<tableRow, String> lastModified;
	
	@FXML
	private Label titleLabel;
	@FXML
	private TextField usernameText;
	@FXML
	private TextField passwordText;
	@FXML
	private TextField urlText;
	@FXML
	private TextArea notesText;
	
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	// Initializes some stuff. Visibility and some things for the table
		fileSelectPane.setDisable(false);
		fileSelectPane.setVisible(true);
		loginPane.setVisible(false);
		loginPane.setDisable(true);
		mainSplitPane.setVisible(false);
		mainSplitPane.setDisable(true);
        title.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        username.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        url.setCellValueFactory(cellData -> cellData.getValue().urlProperty());
        notes.setCellValueFactory(cellData -> cellData.getValue().notesProperty());
        lastModified.setCellValueFactory(cellData -> cellData.getValue().lastModifiedProperty());
        
        table.getSelectionModel().selectedItemProperty().addListener((o, oldSelection, newSelection) -> { 
        	titleLabel.setText(newSelection.title.getValue());
        	usernameText.setText(newSelection.username.getValue());
        	
        	urlText.setText(newSelection.url.getValue());
        	notesText.setText(newSelection.notes.getValue());
        });
	}
	
	public void tableClick(ActionEvent event) {
		
	}
	
	/**
	 * This function will handle the connection to a database 
	 * @param event
	 */
	public void connectClick(ActionEvent event) {
		
		
		//some stuff here
		
		
		fileSelectPane.setVisible(false);
		fileSelectPane.setDisable(true);
		loginPane.setVisible(true);
		loginPane.setDisable(false);
		mainAnchorPane.requestFocus();
	}
	
	/**
	 * It handles file creation
	 * @param event
	 */
	public void createFileClick(ActionEvent event) {
		FileChooser createFile = new FileChooser();
		createFile.setTitle("Create a new file");
		createFile.setInitialDirectory(new File(System.getProperty("user.home") + File.separator + "Documents"));
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PSWD files (*.pswd)", "*.pswd");
		createFile.getExtensionFilters().add(extFilter);
		File selectedFile = createFile.showSaveDialog(null);
		
		if (selectedFile != null) {
	    	System.out.println("created a new file : " + selectedFile.getAbsolutePath());
	    	try {
	            selectedFile.createNewFile();
	            fileSelectPane.setDisable(true);
	    		fileSelectPane.setVisible(false);
	    		loginPane.setVisible(true);
	    		loginPane.setDisable(false);
	    		mainAnchorPane.requestFocus();
	         } catch (IOException ex) {
	            ex.printStackTrace();
	         }
	    }
		else {
			//System.out.println("there was some kind of error");
		}
		
		//some more stuff around here probably
		
		/*fileSelectPane.setDisable(true);
		fileSelectPane.setVisible(false);
		loginPane.setVisible(true);
		loginPane.setDisable(false);*/
		mainAnchorPane.requestFocus();
	}

	/**
	 * And this one handles file opening
	 * @param event
	 */
	public void openFileClick(ActionEvent event) {
		FileChooser openFile = new FileChooser();
		openFile.setTitle("Open an existing file");
		openFile.setInitialDirectory(new File(System.getProperty("user.home") + File.separator + "Documents"));
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PSWD files (*.pswd)", "*.pswd");
		openFile.getExtensionFilters().add(extFilter);
		
		File selectedFile = openFile.showOpenDialog(null);
		
	    if (selectedFile != null) {
	    	System.out.println("opened a new file:" + selectedFile.getAbsolutePath());
	    	//need to add more stuff here
	    }
	    else {
			System.out.println("there was some kind of error");
		}
	    
	    //some more stuff around here probably
	    
	    fileSelectPane.setDisable(true);
	    fileSelectPane.setVisible(false);
		loginPane.setVisible(true);
		loginPane.setDisable(false);
	    mainAnchorPane.requestFocus();
	}

	public void loginClick(ActionEvent event) {
		String username = usernameField.getText();
		String password = passwordField.getText();
		
		if(!username.isEmpty() && !password.isEmpty()) {
			System.out.println("uss: " + username);
			System.out.println("pswd: " + password);
			//some checks here
		}
		else {
			System.out.println("there was some kind of error");
		}
		
		loginPane.setVisible(false);
		loginPane.setDisable(true);
		mainSplitPane.setVisible(true);
		mainSplitPane.setDisable(false);
		mainAnchorPane.requestFocus();
	}
	
	public void newUserClick(ActionEvent event) {
		String username = usernameField.getText();
		String password = passwordField.getText();
		
		if(!username.isEmpty() && !password.isEmpty()) {
			System.out.println("uss: " + username);
			System.out.println("pswd: " + password);
			//some checks here
		}
		else {
			System.out.println("there was some kind of error");
		}
		
		loginPane.setVisible(false);
		loginPane.setDisable(true);
		mainSplitPane.setVisible(true);
		mainSplitPane.setDisable(false);
		mainAnchorPane.requestFocus();
	}
	

	
	/**
	 * uhm, just to test the table
	 * @param event
	 */
	public void addDummyRow(ActionEvent event) {
		
		 ObservableList<tableRow> tableRows = FXCollections.observableArrayList(
	            new tableRow("title1", "username1", "url1", "note1", "date1"),
	            new tableRow("title2", "username2", "url2", "note2", "date2"),
	            new tableRow("title3", "username3", "url3", "note3", "date3"),
	            new tableRow("title4", "username4", "url4", "note4", "date4"),
	            new tableRow("title5", "username5", "url5", "note5", "date5"),
	            new tableRow("title6", "username6", "url6", "note6", "date6"),
	            new tableRow("title7", "username7", "url7", "note7", "date7"),
	            new tableRow("title8", "username8", "url8", "note8", "date8")
	    );
		table.setItems(tableRows);
	}
	
}
