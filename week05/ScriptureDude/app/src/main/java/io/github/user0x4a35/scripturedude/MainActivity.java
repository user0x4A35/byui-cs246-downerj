package io.github.user0x4a35.scripturedude;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String MSG_BOOK = "io.github.user0x4a35.scripturedude.BOOK";
    public static final String MSG_CHAPTER = "io.github.user0x4a35.scripturedude.CHAPTER";
    public static final String MSG_VERSE = "io.github.user0x4a35.scripturedude.VERSE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        startActivity(intent);
    }
}
