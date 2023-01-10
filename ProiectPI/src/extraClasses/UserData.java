package extraClasses;

import java.io.Serializable;

/**
 * This class just holds the data for a single account
 * @author pascaru
 */
public class UserData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String title;
	private String username;
	private String password;
	private String url;
	private String notes;
	private String modifyDate;
	
	public UserData() {
	}

	public UserData(String title, String username, String password, String url, String notes, String modifyDate) {
		this.title = title;
		this.username = username;
		this.password = password;
		this.url = url;
		this.notes = notes;
		this.modifyDate = modifyDate;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getModifyDate() {
		return modifyDate;
	}
	
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
