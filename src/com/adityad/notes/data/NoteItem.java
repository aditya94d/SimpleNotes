package com.adityad.notes.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoteItem {

	private String key;
	private String text;
	
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public static NoteItem getNote(){
		
		Locale locale = new Locale("en_US");
		Locale.setDefault(locale);
		
		String pattern = "yyyy-MM-dd HH:mm:ss Z";
		
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		
		NoteItem note = new NoteItem();
		note.setKey(format.format(new Date()));
		note.setText("");
		return note;
		
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getText();
	}
}
