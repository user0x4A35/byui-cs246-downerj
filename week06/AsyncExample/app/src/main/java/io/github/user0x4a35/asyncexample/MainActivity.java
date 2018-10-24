package io.github.user0x4a35.asyncexample;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "numbers.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void createFile(View view) {
        final Context context = getApplicationContext();
        new Thread(new Runnable() {
            public void run() {
                FileOutputStream outputStream;
                String toastMessage;

                try {
                    outputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                    for (int i = 1; i <= 10; i++) {
                        String str = String.format(Locale.US, "%d%n", i);
                        outputStream.write(str.getBytes());
                        Thread.sleep(250);
                    }
                    outputStream.close();
                    toastMessage = "File saved";
                } catch (FileNotFoundException fnfe) {
                    toastMessage = "File not found";
                } catch (IOException ioe) {
                    toastMessage = "Error writing to file";
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                    return;
                }

                final String message = toastMessage;
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).start();
    }
}