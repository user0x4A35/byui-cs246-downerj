package io.github.user0x4a35.scripturedude;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static final String PREFS = "myPrefs";
    public static final String MSG_BOOK = "io.github.user0x4a35.scripturedude.BOOK";
    public static final String MSG_CHAPTER = "io.github.user0x4a35.scripturedude.CHAPTER";
    public static final String MSG_VERSE = "io.github.user0x4a35.scripturedude.VERSE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadScripture(View view) {
        Context context = this.getApplicationContext();
        SharedPreferences pref = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        EditText txtBook = (EditText) findViewById(R.id.txtBook);
        EditText txtChapter = (EditText) findViewById(R.id.txtChapter);
        EditText txtVerse = (EditText) findViewById(R.id.txtVerse);

        String book = pref.getString(getString(R.string.label_book), "");
        String chapter = pref.getString(getString(R.string.label_chapter), "");
        String verse = pref.getString(getString(R.string.label_verse), "");

        txtBook.setText(book);
        txtChapter.setText(chapter);
        txtVerse.setText(verse);
    }

    public void viewScripture(View view) {
        Intent intent = new Intent(this, ViewScriptureActivity.class);
        EditText txtBook = (EditText) findViewById(R.id.txtBook);
        EditText txtChapter = (EditText) findViewById(R.id.txtChapter);
        EditText txtVerse = (EditText) findViewById(R.id.txtVerse);
        String book = txtBook.getText().toString();
        String chapter = txtChapter.getText().toString();
        String verse = txtVerse.getText().toString();
        intent.putExtra(MSG_BOOK, book);
        intent.putExtra(MSG_CHAPTER, chapter);
        intent.putExtra(MSG_VERSE, verse);

        Log.d(TAG, String.format("About to create intent with %s %s:%s",
            book, chapter, verse
        ));
        startActivity(intent);
    }
}
