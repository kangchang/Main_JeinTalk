package test;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class USER implements Serializable{
	private static final long serialVersionUID = 1L;
	private String message ="";
	private String name="";
	
	
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage() {
		return this.message;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	
	public String toString() {
		LocalTime now = LocalTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("a K:mm:ss");
		String formateNow = now.format(formatter);
//		return  this.name + " : " + this.message + "            (" +formateNow + ")";
		return  "(" +formateNow + ") " + this.name + " : " + this.message;
	}
}