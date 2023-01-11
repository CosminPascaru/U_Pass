package extraClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class holds the data for a single user
 * It also includes the methods for reading/writing and even converting to the ObeservableList needed by the TableView
 * @author pascaru
 */
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String password;
	private List<UserData> data;
	
	public User() {
		this.data = new ArrayList<UserData>();
	}
	
	public User(String password) {
		this.password = password;
		data = new ArrayList<UserData>();
	}
	
	public User(String password, List<UserData> data) {
		this.password = password;
		this.data = data;
	}

	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<UserData> getData() {
		return this.data;
	}
	
	public void setData(List<UserData> data) {
		this.data = data;
	}
	
	/**
	 * Basic function to write a List of users to a file 
	 * @param file
	 * @param serObj
	 */
	public static void WriteObjectToFile(File file, User serObj) {
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(serObj);
			objectOut.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * This function returns a List of users read from a file
	 * @param file
	 * @return List<User>
	 */
	public static User ReadObjectFromFile(File file) {
		try {
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			User obj = (User) objectIn.readObject();
			objectIn.close();
			return obj;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String toString() {
		String string = password + " \n";
		for(UserData x : data) {
			string = string + x;
		}
		return string;
	}
	
	
	
	/**
	 * Small function to convert from this.data to an ObservableList needed by the TableView
	 * @return ObservableList<TableRow> 
	 */
	public ObservableList<TableRow> convert() {
		ObservableList<TableRow> tableRows = FXCollections.observableArrayList();
		if(this.data != null) {
			for(UserData x : data) {
				TableRow row = new TableRow(x.getTitle(), x.getUsername(), x.getUrl(), x.getNotes(), x.getModifyDate());
				tableRows.add(row);
			}
		}
		return tableRows;
	}
	
	
}