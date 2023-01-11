package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

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
	private	AnchorPane connectPane;
	
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
	
	@FXML
	private PasswordField showPasswordField;
	
	@FXML
	private Button showPasswordButton;
	
	@FXML
	private TextField connectDatabase;
	@FXML
	private TextField connectUser;
	@FXML
	private PasswordField connectPassword;
	@FXML
	private Label connectWarning;
	
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
		connectPane.setVisible(false);
		connectPane.setDisable(true);
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
		connectWarning.setVisible(false);
		showPasswordField.setVisible(false);
		showPasswordField.setDisable(true);
		passwordText.setVisible(true);
		passwordText.setDisable(false);
		addPassword.setVisible(false);
		addPassword.setDisable(true);
		addPasswordText.setVisible(true);
		addPasswordText.setDisable(false);
		showPasswordField.setVisible(true);
		showPasswordField.setDisable(false);
		passwordText.setVisible(false);
		passwordText.setDisable(true);
		titleLabel.setText("Your account!");
		connectWarning.setText("There was an error! Check the login credentials");
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
		menuAdd.setDisable(true);
		menuMod.setDisable(true);
		menuDel.setDisable(true);
		entryPasswordButton.setGraphic(lupa);
		showPasswordButton.setGraphic(lupa);
		
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
        		showPasswordField.setText(getPassword(selectedUsername, selectedTitle));
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
	
	static String dbAdress;
	static String dbUser;
	static String dbPassword;
	
	/**
	 * This function will handle the connection to a database 
	 * Atm it just skips to the login screen
	 * @param event
	 */
	public void connectClick(ActionEvent event) {
		usernameText.setText(null);
		passwordText.setText(null);
		showPasswordField.setText(null);
		urlText.setText(null);
		notesText.setText(null);
		titleLabel.setText("Your account!");
        fileSelectPane.setDisable(true);
		fileSelectPane.setVisible(false);
		connectPane.setVisible(true);
		connectPane.setDisable(false);
		loginPane.setVisible(false);
		loginPane.setDisable(true);
		mainSplitPane.setVisible(false);
		mainSplitPane.setDisable(true);
		
		
		mainAnchorPane.requestFocus();
	}
	
	public static Connection conn;
	public static Statement stmt;
	public static Boolean connected = false;
	
	public void connectClick2(ActionEvent event) {
		dbAdress = "jdbc:mysql://localhost:3306/" + connectDatabase.getText();
		dbUser = connectUser.getText();
		dbPassword = connectPassword.getText();
		Boolean nullCheck;
		try {
			conn = DriverManager.getConnection(dbAdress, dbUser, dbPassword);
			
			stmt = conn.createStatement();
			
			ResultSet set = stmt.executeQuery("SELECT * FROM accounts, meta_data\r\n"
					+ "WHERE accounts.acc_id = meta_data.acc_id;");
			userContent = new User();
			String tempPassword;
			while(set.next()) {
				tempPassword = decryptPassword(set.getString("acc_pswd"));
				userContent.getData().add(new UserData(set.getString("acc_title"), set.getString("acc_uname"), tempPassword, set.getString("acc_url"), set.getString("acc_notes"), set.getString("m_date")));
			}
			
			connectDatabase.setText(null);
			connectUser.setText(null);
			connectPassword.setText(null);
			
			connected = true;
			initTable();
			
			
		} catch (Exception e) {
			connectWarning.setVisible(true);
			mainAnchorPane.requestFocus();
			//return;
			//e.printStackTrace();
			
		}
		
		
		
	}
	
	
	
	/**
	 * It handles file creation and some initialization
	 * @param event
	 */
	public void createFileClick(ActionEvent event) {
		connected = false;
		usernameText.setText(null);
		passwordText.setText(null);
		showPasswordField.setText(null);
		urlText.setText(null);
		notesText.setText(null);
		titleLabel.setText("Your account!");
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
	    		connectPane.setVisible(false);
	    		connectPane.setDisable(true);
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
	public void openFileClick(ActionEvent event){
		connected = false;
		titleLabel.setText("Your account!");
		usernameText.setText(null);
		passwordText.setText(null);
		showPasswordField.setText(null);
		urlText.setText(null);
		notesText.setText(null);
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
		    connectPane.setVisible(false);
    		connectPane.setDisable(true);
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
		mainAnchorPane.requestFocus();
		
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
		menuAdd.setDisable(false);
		menuMod.setDisable(false);
		menuDel.setDisable(false);
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
		menuAdd.setDisable(true);
		menuMod.setDisable(true);
		menuDel.setDisable(true);
		showPasswordClick(new ActionEvent());
		dataPane.setDisable(true);
		dataPane.setVisible(false);
		entryPane.setDisable(false);
		entryPane.setVisible(true);
		entryLabel.setText("Add a new entry");
		mod = false;
	}
	
	public void WriteObjectToDatabase() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException{
		try {
			stmt.execute("SET FOREIGN_KEY_CHECKS = 0;");
			stmt.executeUpdate("TRUNCATE accounts;");
			stmt.execute("SET FOREIGN_KEY_CHECKS = 1;");
			stmt.executeUpdate("TRUNCATE meta_data;");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();/////////////////////////////////////////////////////////////////////////////////
		}
		int index = 1;
		String tempPassword;
		for(UserData x : userContent.getData()) {
			try {
				tempPassword = encryptPassword(x.getPassword());
				stmt.executeUpdate("INSERT INTO accounts (acc_title, acc_uname, acc_pswd, acc_url, acc_notes)\r\n"
						+ "VALUES ('" + x.getTitle() + "', '" + x.getUsername() + "', '" + tempPassword + "', '" + x.getUrl() + "', '" + x.getNotes() + "');");
				stmt.executeUpdate("INSERT INTO meta_data (acc_id, m_date)\r\n"
						+ "VALUES ('" + index + "', '" + x.getModifyDate() + "');");
				index++;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();/////////////////////////////////////////////////////////////////////////////
			}
			
		}
	}
	
	public void delClick(ActionEvent event) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException{
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
				showPasswordField.setText(null);
				passwordText.setText(null);
				urlText.setText(null);
				notesText.setText(null);
				TableRows.remove(delete);
				userContent.getData().remove(moreDelete);
				if(connected) {
					WriteObjectToDatabase();
				}
				else {
					User.WriteObjectToFile(selectedFile, userContent);
				}
			}
		}
	}
	
	
	
	public void modClick(ActionEvent event) {
		menuAdd.setDisable(true);
		menuMod.setDisable(true);
		menuDel.setDisable(true);
		dataPane.setDisable(true);
		dataPane.setVisible(false);
		entryPane.setDisable(false);
		entryPane.setVisible(true);
		entryLabel.setText("Modify this entry");
		addTitle.setText(selectedTitle);
		addUsername.setText(selectedUsername);
		addPassword.setText(selectedPassword);
		addPasswordText.setText(selectedPassword);
		addUrl.setText(selectedUrl);
		addNotes.setText(selectedNotes);
		mod = true;
	}
	
	public void entryOkClick(ActionEvent event) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
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
				userContent.getData().add(new UserData(entryTitle, entryUsername, entryPassword, entryUrl, entryNotes, entryDate));
				TableRow row = new TableRow(entryTitle, entryUsername, entryUrl, entryNotes, entryDate);
				TableRows.add(row);
				addTitle.setText(null);
				addUsername.setText(null);
				menuAdd.setDisable(false);
				menuMod.setDisable(false);
				menuDel.setDisable(false);
				addPassword.setText(null);
				addPasswordText.setText(null);
				addUrl.setText(null);
				addNotes.setText(null);
				titleLabel.setText("Your account!");
				usernameText.setText(null);
				passwordText.setText(null);
				showPasswordField.setText(null);
				urlText.setText(null);
				notesText.setText(null);
				dataPane.setDisable(false);
				dataPane.setVisible(true);
				entryPane.setDisable(true);
				entryPane.setVisible(false);
				entryLabel.setText("Add a new entry");
				mainAnchorPane.requestFocus();
				table.getSelectionModel().clearSelection();
				if(connected) {
					WriteObjectToDatabase();
				}
				else {
					User.WriteObjectToFile(selectedFile, userContent);
				}
			}
		}
		else {
			entryTitle = addTitle.getText();
			entryUsername = addUsername.getText();
			if(addPassword.isVisible()) {
				entryPassword = addPasswordText.getText();
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
				addPasswordText.setText(null);
				menuAdd.setDisable(false);
				menuMod.setDisable(false);
				menuDel.setDisable(false);
				titleLabel.setText("Your account!");
				usernameText.setText(null);
				passwordText.setText(null);
				showPasswordField.setText(null);
				urlText.setText(null);
				notesText.setText(null);
				addUrl.setText(null);
				addNotes.setText(null);
				dataPane.setDisable(false);
				dataPane.setVisible(true);
				entryPane.setDisable(true);
				entryPane.setVisible(false);
				entryLabel.setText("Add a new entry");
				table.getSelectionModel().clearSelection();
				mainAnchorPane.requestFocus();
				if(connected) {
					WriteObjectToDatabase();
				}
				else {
					User.WriteObjectToFile(selectedFile, userContent);
				}
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
		menuAdd.setDisable(false);
		menuMod.setDisable(false);
		menuDel.setDisable(false);
		dataPane.setDisable(false);
		dataPane.setVisible(true);
		entryPane.setDisable(true);
		entryPane.setVisible(false);
		
		titleLabel.setText("Your account!");
		
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
	
	public void showPasswordClick(ActionEvent event) {
		if(showPasswordField.isVisible()) {
			passwordText.setText(showPasswordField.getText());
			showPasswordField.setVisible(false);
			showPasswordField.setDisable(true);
			passwordText.setVisible(true);
			passwordText.setDisable(false);
		}
		else {
			showPasswordField.setText(passwordText.getText());
			showPasswordField.setVisible(true);
			showPasswordField.setDisable(false);
			passwordText.setVisible(false);
			passwordText.setDisable(true);
		}
		mainAnchorPane.requestFocus();
	}
	
	public void GitClick(ActionEvent event) throws MalformedURLException, IOException, URISyntaxException{
		Desktop.getDesktop().browse(new URL("https://github.com/CosminPascaru/U_Pass").toURI());
	}
	
	public static String encrypt(String algorithm, String input, SecretKey key,
		    IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
		    InvalidAlgorithmParameterException, InvalidKeyException,
		    BadPaddingException, IllegalBlockSizeException {
		    
		    Cipher cipher = Cipher.getInstance(algorithm);
		    cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		    byte[] cipherText = cipher.doFinal(input.getBytes());
		    return Base64.getEncoder()
		        .encodeToString(cipherText);
		}
	
	public static String decrypt(String algorithm, String cipherText, SecretKey key, IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
		    InvalidAlgorithmParameterException, InvalidKeyException,
		    BadPaddingException, IllegalBlockSizeException {
		    
		    Cipher cipher = Cipher.getInstance(algorithm);
		    cipher.init(Cipher.DECRYPT_MODE, key, iv);
		    byte[] plainText = cipher.doFinal(Base64.getDecoder()
		        .decode(cipherText));
		    return new String(plainText);
	}
	
	public static IvParameterSpec generateIv() {
	    byte[] iv = new byte[16];
	    new SecureRandom().nextBytes(iv);
	    return new IvParameterSpec(iv);
	}
	
	public static SecretKey getKeyFromPassword(String password, String salt)
		    throws NoSuchAlgorithmException, InvalidKeySpecException {
		    
		    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
		    SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
		    return secret;
		}
	
	String salt = "87626358";
	String encryptionPassword = "this is an actual encryption password";
	byte[] iv = { 0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 0, 6, 0, 7, 0, 8 };
	IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
	
	
	public String encryptPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
		 SecretKey key = getKeyFromPassword(encryptionPassword,salt);
		 String cipherText = encrypt("AES/CBC/PKCS5Padding", password, key, ivParameterSpec);
		 return cipherText;
	}
	
	public String decryptPassword(String fakePassword) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
		SecretKey key = getKeyFromPassword(encryptionPassword,salt);
		String decryptedCipherText = decrypt("AES/CBC/PKCS5Padding", fakePassword, key, ivParameterSpec);
		return decryptedCipherText;
	}
}
