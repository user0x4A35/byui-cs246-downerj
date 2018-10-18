package io.github.user0x4a35.scripturedude;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ViewScriptureActivity extends AppCompatActivity {

    public static final String TAG = "ViewScriptureActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_scripture);

        Intent intent = getIntent();
        String book = intent.getStringExtra(MainActivity.MSG_BOOK);
        String chapter = intent.getStringExtra(MainActivity.MSG_CHAPTER);
        String verse = intent.getStringExtra(MainActivity.MSG_VERSE);

        Log.d(TAG, String.format("Received intent with %s %s:%s",
            book, chapter, verse
        ));

        TextView lblScripture = findViewById(R.id.lblScripture);
        lblScripture.setText(String.format("%s %s:%s",
            book, chapter, verse
        ));
    }
}
