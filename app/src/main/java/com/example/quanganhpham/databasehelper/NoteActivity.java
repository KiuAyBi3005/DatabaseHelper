package com.example.quanganhpham.databasehelper;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {
    public static final long NEW_NOTE = -1;
    public static final String ID = "ID";
    private DatabaseHelper db;
    private Note note;
    private EditText edit_title;
    private EditText edit_content;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        context = this;
        db = new DatabaseHelper(context);
        connectView();
        getInfo();
    }

    private void connectView() {
        edit_title = (EditText) findViewById(R.id.edit_title);
        edit_content = (EditText) findViewById(R.id.edit_content);
    }

    private void getInfo() {
        long id = getIntent().getLongExtra(ID, NEW_NOTE);
        if (id != NEW_NOTE) {
            String sql = "SELECT * FROM " + DatabaseHelper.DATABASE_TABLE + " WHERE " + DatabaseHelper.KEY_ID_NOTE + " = " + id;
            note = db.getNote(sql);
        }
        if (note != null) {
            edit_title.setText(note.getTitle());
            edit_content.setText(note.getContent());
        } else {
            edit_title.setText(" ");
            edit_content.setText(" ");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                save();
                break;
            case R.id.menu_delete:
                delete();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void save() {
        String title = edit_title.getText().toString().trim();
        String content = edit_content.getText().toString().trim();
        String notify = null;
        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(content)) {
            notify = "Note empty, don't save";
        } else {
            if (note == null) {
                Note note = new Note();
                note.setTitle(title).setContent(content);
                if (db.insertNote(note) > 0) {
                    notify = "Add success";
                } else {
                    notify = "Add fail";
                }
            } else {
                note.setTitle(title).setContent(content);
                if (db.updateNote(note)) {
                    notify = "Update success";
                } else {
                    notify = "Update fail";
                }
            }
        }
        Toast.makeText(context, notify, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void delete() {
        String title = edit_title.getText().toString().trim();
        String content = edit_content.getText().toString().trim();
        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(content)) {
            finish();
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
            builder.setTitle(R.string.delete).setIcon(R.mipmap.ic_launcher).setMessage("Do you want delete note");
            builder.setPositiveButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
        }
    }

    private void deleteNote() {
        if (note != null) {
            String where = DatabaseHelper.KEY_ID_NOTE + " = " + note.getId();
            String notify = "Delete success!";
            if (!db.deleteNote(where)) {
                notify = "Delete failt!";
            }
            Toast.makeText(context, notify, Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        save();
    }

}

