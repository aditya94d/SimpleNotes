package com.adityad.notes;

import com.adityad.notes.data.NoteItem;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

public class NoteEditorActivity extends Activity {

	private NoteItem note;
	private Intent i, sendBack;
	private EditText et;

	@Override
	protected void onCreate(Bundle bun) {
		super.onCreate(bun);
		setContentView(R.layout.note_editor);
		note = NoteItem.getNote();
		Intent i = getIntent();
		sendBack = new Intent();
		String key = i.getStringExtra("key");
		String text = i.getStringExtra("text");
		note.setKey(key);
		note.setText(text);

		et = (EditText) findViewById(R.id.noteText);
		et.setText(text);
		et.setSelection(text.length());

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == android.R.id.home) {
			saveAndFinish();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();

		sendBack.putExtra("key", note.getKey());
		sendBack.putExtra("text", et.getText().toString());
		this.setResult(RESULT_OK, sendBack);
		finish();
	}

	private void saveAndFinish() {

		sendBack.putExtra("key", note.getKey());
		sendBack.putExtra("text", et.getText().toString());
		this.setResult(RESULT_OK, sendBack);
		finish();
	}
}
