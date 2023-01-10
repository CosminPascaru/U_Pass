package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import extraClasses.TableRow;
import extraClasses.User;
import extraClasses.UserData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * This is the controller class, it has all the important functions
 * @author pascaru
 */
public class Controller implements Initializable{

	
	// This is stuff that needed to be injected from the FXML file
	
	@FXML
	private AnchorPane mainAnchorPane;
	
	@FXML
	private SplitPane mainPane;
	
	@FXML
	private Button menuAdd;
	@FXML
	private Button menuDel;
	@FXML
	private Button menuMod;
	
	@FXML
	private AnchorPane loginPane;
	@FXML
	private Label loginLabel;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Button loginButton;
	@FXML
	private Button newUserButton;
	@FXML
	private Label loginWarning;
	
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
	private TableView<TableRow> table;
	@FXML
	private TableColumn<TableRow, String> title;
	@FXML
	private TableColumn<TableRow, String> username;
	@FXML
	private TableColumn<TableRow, String> url;
	@FXML
	private TableColumn<TableRow, String> notes;
	@FXML
	private TableColumn<TableRow, String> lastModified;
	
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
	
	@FXML
	private AnchorPane entryPane;
	@FXML
	private AnchorPane dataPane;
	
	@FXML
	private TextField addTitle;
	@FXML
	private TextField addUsername;
	@FXML
	private PasswordField addPassword;
	@FXML
	private TextField addPasswordText;
	@FXML
	private TextField addUrl;
	@FXML
	private TextArea addNotes;
	@FXML
	private Label entryLabel;
	@FXML
	private Button entryOk;
	@FXML
	private Button entryCancel;
	@FXML
	private Button entryPasswordButton;
	
	
	
	File selectedFile;
	static User userContent;
	static Boolean newFile;
	ObservableList<TableRow> TableRows = FXCollections.observableArrayList();
	static String entryTitle;
	static String entryUsername;
	static String entryPassword;
	static String entryUrl;
	static String entryNotes;
	static String entryDate;
	
	static String selectedTitle;
	static String selectedUsername;
	static String selectedPassword;
	static String selectedUrl;
	static String selectedNotes;
	
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	// Initializes some stuff. Visibility and some things for the table
		fileSelectPane.setDisable(false);
		fileSelectPane.setVisible(true);
		loginPane.setVisible(false);
		loginPane.setDisable(true);
		mainSplitPane.setVisible(false);
		mainSplitPane.setDisable(true);
		dataPane.setDisable(false);
		dataPane.setVisible(true);
		entryPane.setDisable(true);
		entryPane.setVisible(false);
		loginWarning.setVisible(false);
		addPassword.setVisible(true);
		addPassword.setDisable(false);
		addPasswordText.setVisible(false);
		addPasswordText.setDisable(true);
		loginWarning.setText("Wrong password!");
		entryLabel.setText("Add a new entry");
		
		ImageView plus = new ImageView(new Image("Plus.png"));
		plus.setFitHeight(20);
		plus.setPreserveRatio(true);
		ImageView minus = new ImageView(new Image("Minus.png"));
		minus.setFitHeight(20);
		minus.setPreserveRatio(true);
		ImageView modify = new ImageView(new Image("Creion.png"));
		modify.setFitHeight(20);
		modify.setPreserveRatio(true);
		ImageView lupa = new ImageView(new Image("Lupa.png"));
		lupa.setFitHeight(20);
		lupa.setPreserveRatio(true);
		
		menuAdd.setGraphic(plus);
		menuDel.setGraphic(minus);
		menuMod.setGraphic(modify);
		entryPasswordButton.setGraphic(lupa);
		
        title.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        username.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        url.setCellValueFactory(cellData -> cellData.getValue().urlProperty());
        notes.setCellValueFactory(cellData -> cellData.getValue().notesProperty());
        lastModified.setCellValueFactory(cellData -> cellData.getValue().lastModifiedProperty());
        
        table.getSelectionModel().selectedItemProperty().addListener((o, oldSelection, newSelection) -> { 
        	try {
        		titleLabel.setText(newSelection.getTitle().getValue());
        		usernameText.setText(newSelection.getUsername().getValue());
        		selectedTitle = titleLabel.getText();
        		selectedUsername = usernameText.getText();
        		passwordText.setText(getPassword(selectedUsername, selectedTitle));
        		urlText.setText(newSelection.getUrl().getValue());
        		notesText.setText(newSelection.getNotes().getValue());
        		selectedPassword = passwordText.getText();
        		selectedUrl = urlText.getText();
        		selectedNotes = notesText.getText();
        		
        	}catch(Exception e) {
        		//aici e un null pointer exception pe care vreau sal ignor
        		//in momentul in care se sterge ceva din tabel, se face un clearSelection(),
        		//care face newSelection sa fie null
        		//asa ca, newSelection.getChestie o sa creeze o exceptie
        		//nu afecteaza cu nimic, ar trebui rescris listeneru asta dar nu stiu cum :)
        	} 
        });
	}
	
	public String getPassword(String username, String title) {
		for(UserData x : userContent.getData()) {
			if(x.getUsername().equals(username) && x.getTitle().equals(title)) {
				
				return x.getPassword();
			}
		}
		return null;
	}
	
	public void addData() {
		TableRow row = new TableRow(entryTitle, entryUsername, entryUrl, entryNotes, entryDate);
		TableRows.add(row);
		//table.setItems(TableRows);
	}
	
	public void tableClick(ActionEvent event) {
		
	}
	
	
	/**
	 * This function will handle the connection to a database 
	 * Atm it just skips to the login screen
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
	 * It handles file creation and some initialization
	 * @param event
	 */
	public void createFileClick(ActionEvent event) {
		loginWarning.setVisible(false);
		FileChooser createFile = new FileChooser();
		createFile.setTitle("Create a new file");
		createFile.setInitialDirectory(new File(System.getProperty("user.home") + File.separator + "Documents"));
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PSWD files (*.pswd)", "*.pswd");
		createFile.getExtensionFilters().add(extFilter);
		selectedFile = createFile.showSaveDialog(null);
		
		if (selectedFile != null) {
	    	try {
	            selectedFile.createNewFile();
	            fileSelectPane.setDisable(true);
	    		fileSelectPane.setVisible(false);
	    		loginPane.setVisible(true);
	    		loginPane.setDisable(false);
	    		mainSplitPane.setVisible(false);
	    		mainSplitPane.setDisable(true);
	    		loginLabel.setText("Create a new password");
	    		mainAnchorPane.requestFocus();
	    		passwordField.setText(null);
	    		userContent = new User();
	    		newFile = true;
	         } catch (IOException ex) {
	            ex.printStackTrace();
	         }
	    }
		mainAnchorPane.requestFocus();
	}
	
	/**
	 * And this one handles file opening and the reading
	 * @param event
	 * @throws URISyntaxException 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public void openFileClick(ActionEvent event) throws MalformedURLException, IOException, URISyntaxException {
		
		loginWarning.setVisible(false);
		FileChooser openFile = new FileChooser();
		openFile.setTitle("Open an existing file");
		openFile.setInitialDirectory(new File(System.getProperty("user.home") + File.separator + "Documents"));
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PSWD files (*.pswd)", "*.pswd");
		openFile.getExtensionFilters().add(extFilter);
		
		selectedFile = openFile.showOpenDialog(null);
		
	    if (selectedFile != null) {
	    	fileSelectPane.setDisable(true);
		    fileSelectPane.setVisible(false);
			loginPane.setVisible(true);
			loginPane.setDisable(false);
			mainSplitPane.setVisible(false);
			mainSplitPane.setDisable(true);
		    mainAnchorPane.requestFocus();
		    newFile = false;
		    passwordField.setText(null);
		    userContent = new User();
		    userContent = User.ReadObjectFromFile(selectedFile);
	    }
	    mainAnchorPane.requestFocus();
	}
	
	/**
	 * Checks the password and then gives some data to "userContent".
	 * It also initializes the table with the selected data
	 * @param event
	 */
	public void loginClick(ActionEvent event) {
		String password = passwordField.getText();
		if(!newFile) {
			if(loginCheck(password)) {
				initTable();
			}
			else {
				loginWarning.setText("Wrong password!");
				loginWarning.setVisible(true);
				mainAnchorPane.requestFocus();
			}
		}
		else {
			if(!password.isEmpty()) {
				if(password.length() < 8) {
					loginWarning.setText("Password is too short!");
					loginWarning.setVisible(true);
				}
				else {
					userContent.setPassword(password);
				User.WriteObjectToFile(selectedFile, userContent);
				initTable();
				}
			}
		}
		
	}
	
	public static Boolean loginCheck(String password) {
		if(!password.isEmpty()) {
			if(userContent.getPassword().equals(password)) {
				return true;
			}
		}		
		return false;
	}
	
	/**
	 * it converts from userContent to the TableRows and it adds the rows to the TableView
	 */
	public void initTable() {
		loginPane.setVisible(false);
		loginPane.setDisable(true);
		mainSplitPane.setVisible(true);
		mainSplitPane.setDisable(false);
		loginWarning.setVisible(false);
		mainAnchorPane.requestFocus();
		
		TableRows = userContent.convert();
		table.setItems(TableRows);
	}

	public Boolean mod;
	
	public void addClick(ActionEvent event){
		
		dataPane.setDisable(true);
		dataPane.setVisible(false);
		entryPane.setDisable(false);
		entryPane.setVisible(true);
		mod = false;
	}
	
	public void delClick(ActionEvent event) {
		TableRow delete = new TableRow();
		UserData moreDelete = new UserData();
		if(selectedTitle != null && selectedUsername != null) {
			for(TableRow z : TableRows) {
				if(selectedUsername.equals(z.getUsername().getValue()) && selectedTitle.equals(z.getTitle().getValue())) {
					delete = z;
				}
			}
			if(delete != null) {
				titleLabel.setText("Your account!");
				table.getSelectionModel().clearSelection();
				for(UserData x : userContent.getData()) {
					if(selectedTitle.equals(x.getTitle()) && selectedUsername.equals(x.getUsername())) {
						moreDelete = x;
					}
				}
				selectedTitle = null;
				selectedUsername = null;
				usernameText.setText(selectedUsername);
				passwordText.setText(null);
				urlText.setText(null);
				notesText.setText(null);
				TableRows.remove(delete);
				userContent.getData().remove(moreDelete);
				User.WriteObjectToFile(selectedFile, userContent);
			}
		}
	}
	
	
	
	public void modClick(ActionEvent event) {
		dataPane.setDisable(true);
		dataPane.setVisible(false);
		entryPane.setDisable(false);
		entryPane.setVisible(true);
		entryLabel.setText("Modify this entry");
		addTitle.setText(selectedTitle);
		addUsername.setText(selectedUsername);
		addPassword.setText(selectedPassword);
		addUrl.setText(selectedUrl);
		addNotes.setText(selectedNotes);
		mod = true;
	}
	
	public void entryOkClick(ActionEvent event) {
		if(mod == false) {
			entryTitle = addTitle.getText();
			entryUsername = addUsername.getText();
			if(addPassword.isVisible()) {
				entryPassword = addPassword.getText();
			}
			else {
				entryPassword = addPasswordText.getText();
			}
			entryUrl = addUrl.getText();
			entryNotes = addNotes.getText();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			entryDate = dtf.format(now).toString();
			
			if(entryUsername.isEmpty() || entryPassword.isEmpty() || entryUsername.isBlank() || entryPassword.isBlank()) {
				entryLabel.setText("You must enter at both an username and a password");
			}
			else {
				for(UserData x : userContent.getData()) {
					if(entryTitle.equals(x.getTitle()) || entryUsername.equals(x.getUsername())) {
						entryLabel.setText("A similar entry already exists");
						return;
					}
				}
				System.out.print(entryPassword);
				userContent.getData().add(new UserData(entryTitle, entryUsername, entryPassword, entryUrl, entryNotes, entryDate));
				TableRow row = new TableRow(entryTitle, entryUsername, entryUrl, entryNotes, entryDate);
				TableRows.add(row);
				addTitle.setText(null);
				addUsername.setText(null);
				addPassword.setText(null);
				addUrl.setText(null);
				addNotes.setText(null);
				dataPane.setDisable(false);
				dataPane.setVisible(true);
				entryPane.setDisable(true);
				entryPane.setVisible(false);
				entryLabel.setText("Add a new entry");
				mainAnchorPane.requestFocus();
				table.getSelectionModel().clearSelection();
				User.WriteObjectToFile(selectedFile, userContent);
			}
		}
		else {
			entryTitle = addTitle.getText();
			entryUsername = addUsername.getText();
			if(addPassword.isVisible()) {
				entryPassword = addPassword.getText();
			}
			else {
				entryPassword = addPasswordText.getText();
			}
			entryUrl = addUrl.getText();
			entryNotes = addNotes.getText();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			entryDate = dtf.format(now).toString();
			if(entryUsername.isEmpty() || entryPassword.isEmpty() || entryUsername.isBlank() || entryPassword.isBlank()) {
				entryLabel.setText("You must enter at both an username and a password");
			}
			else {
				
				delClick(new ActionEvent());
				userContent.getData().add(new UserData(entryTitle, entryUsername, entryPassword, entryUrl, entryNotes, entryDate));
				TableRow row = new TableRow(entryTitle, entryUsername, entryUrl, entryNotes, entryDate);
				TableRows.add(row);
				addTitle.setText(null);
				addUsername.setText(null);
				addPassword.setText(null);
				addUrl.setText(null);
				addNotes.setText(null);
				dataPane.setDisable(false);
				dataPane.setVisible(true);
				entryPane.setDisable(true);
				entryPane.setVisible(false);
				entryLabel.setText("Add a new entry");
				table.getSelectionModel().clearSelection();
				mainAnchorPane.requestFocus();
				User.WriteObjectToFile(selectedFile, userContent);
			}
		}
		mod = false;
	}
	
	public void entryCancelClick(ActionEvent event) {
		addTitle.setText(null);
		addUsername.setText(null);
		addPassword.setText(null);
		addPassword.setText(null);
		addNotes.setText(null);
		
		dataPane.setDisable(false);
		dataPane.setVisible(true);
		entryPane.setDisable(true);
		entryPane.setVisible(false);
		
		mainAnchorPane.requestFocus();
	}	
	
	public void entryPasswordClick(ActionEvent event) {
		if(addPassword.isVisible()) {
			addPasswordText.setText(addPassword.getText());
			addPassword.setVisible(false);
			addPassword.setDisable(true);
			addPasswordText.setVisible(true);
			addPasswordText.setDisable(false);
		}
		else {
			addPassword.setText(addPasswordText.getText());
			addPassword.setVisible(true);
			addPassword.setDisable(false);
			addPasswordText.setVisible(false);
			addPasswordText.setDisable(true);
		}
		
		mainAnchorPane.requestFocus();
		
	}
	
	public void GitClick(ActionEvent event) throws MalformedURLException, IOException, URISyntaxException{
		Desktop.getDesktop().browse(new URL("https://github.com/CosminPascaru/U_Pass").toURI());
	}
	
	
}
