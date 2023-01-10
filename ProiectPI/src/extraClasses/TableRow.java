package extraClasses;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class is just a data type needed for the TableView
 * @author pascaru
 */
public class TableRow {
	private SimpleStringProperty title;
	private SimpleStringProperty username;
	private SimpleStringProperty url;
	private SimpleStringProperty notes;
	private SimpleStringProperty lastModified;
	
	public TableRow() {
	}
	public TableRow(String title, String username, String url, String notes, String lastModified) {
		this.setTitle(new SimpleStringProperty(title));
		this.setUsername(new SimpleStringProperty(username));
		this.setUrl(new SimpleStringProperty(url));
		this.setNotes(new SimpleStringProperty(notes));
		this.lastModified = new SimpleStringProperty(lastModified);
	}
	public StringProperty titleProperty() {
		return getTitle();
	}
	public StringProperty usernameProperty() {
		return getUsername();
	}
	public StringProperty urlProperty() {
		return getUrl();
	}
	public StringProperty notesProperty() {
		return getNotes();
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
	public SimpleStringProperty getTitle() {
		return title;
	}
	public void setTitle(SimpleStringProperty title) {
		this.title = title;
	}
	public SimpleStringProperty getUsername() {
		return username;
	}
	public void setUsername(SimpleStringProperty username) {
		this.username = username;
	}
	public SimpleStringProperty getUrl() {
		return url;
	}
	public void setUrl(SimpleStringProperty url) {
		this.url = url;
	}
	public SimpleStringProperty getNotes() {
		return notes;
	}
	public void setNotes(SimpleStringProperty notes) {
		this.notes = notes;
	}
}
