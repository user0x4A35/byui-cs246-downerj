package io.github.user0x4a35.scripturedude;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ViewScriptureActivity extends AppCompatActivity {

    public static final String TAG = "ViewScriptureActivity";
    public static final String PREFS = "myPrefs";
    private String book;
    private String chapter;
    private String verse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_scripture);

        Intent intent = getIntent();
        book = intent.getStringExtra(MainActivity.MSG_BOOK);
        chapter = intent.getStringExtra(MainActivity.MSG_CHAPTER);
        verse = intent.getStringExtra(MainActivity.MSG_VERSE);

        Log.d(TAG, String.format("Received intent with %s %s:%s",
            book, chapter, verse
        ));

        TextView lblScripture = findViewById(R.id.lblScripture);
        lblScripture.setText(String.format("%s %s:%s",
            book, chapter, verse
        ));
    }

    public void saveScripture(View view) {
        Context context = this.getApplicationContext();
        SharedPreferences pref = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = pref.edit();
        prefEditor.putString(getString(R.string.label_book), book);
        prefEditor.putString(getString(R.string.label_chapter), chapter);
        prefEditor.putString(getString(R.string.label_verse), verse);
        prefEditor.apply();

        Toast toast = Toast.makeText(this, "Scripture Saved!", Toast.LENGTH_LONG);
        toast.show();
    }
}
