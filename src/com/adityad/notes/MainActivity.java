package com.adityad.notes;

import java.util.List;

import com.adityad.notes.data.NoteDataSource;
import com.adityad.notes.data.NoteItem;
import com.adityad.notes.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	private static final int EDITOR_ACTIVITY_REQUEST = 101;
	private static final int MENU_DELETE_ID = 102;
	private int currentNoteId;
	private NoteDataSource datasource;
	List<NoteItem> notesList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		registerForContextMenu(getListView());
		datasource = new NoteDataSource(this);

		refresh();

	}

	private void refresh() {

		notesList = datasource.findAll();
		ArrayAdapter<NoteItem> adapter = new ArrayAdapter<NoteItem>(this,
				R.layout.list_item, notesList);
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater opMenu = getMenuInflater();
		opMenu.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.addNote) {

			createNote();

		}
		
		else if (item.getItemId() == R.id.addSpeech) {

			createNote();

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		editNote(position);
		super.onListItemClick(l, v, position, id);

	}

	private void createNote() {
		// TODO Auto-generated method stub
		NoteItem note = NoteItem.getNote();

		Intent intent = new Intent(MainActivity.this, NoteEditorActivity.class);
		intent.putExtra("key", note.getKey());
		intent.putExtra("text", note.getText());
		startActivityForResult(intent, 101);

	}

	private void editNote(int position) {
		// TODO Auto-generated method stub
		NoteItem note = notesList.get(position);
		Intent intent = new Intent(MainActivity.this, NoteEditorActivity.class);
		intent.putExtra("key", note.getKey());
		intent.putExtra("text", note.getText());
		startActivityForResult(intent, EDITOR_ACTIVITY_REQUEST);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK && requestCode == EDITOR_ACTIVITY_REQUEST) {

			NoteItem note = new NoteItem();
			note.setKey(data.getStringExtra("key"));
			note.setText(data.getStringExtra("text"));
			
			if(data.getStringExtra("text").toString().trim().length() !=0)
			datasource.update(note);
			refresh();
		}

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		currentNoteId = (int) info.id;
		menu.add(0, MENU_DELETE_ID, 0, "Delete");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		if (item.getItemId() == MENU_DELETE_ID) {
			NoteItem note = notesList.get(currentNoteId);
			datasource.remove(note);
			refresh();
		}
		return super.onContextItemSelected(item);

	}

}
