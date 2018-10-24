package io.github.user0x4a35.asyncexample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "numbers.txt";
    private static final String TAG = "MainActivity";
    private ArrayAdapter<Integer> numbersAdapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numbersAdapter = new ArrayAdapter<Integer>(
                getApplicationContext(),
                R.layout.support_simple_spinner_dropdown_item
        );
        ListView listNumbers = findViewById(R.id.listNumbers);
        listNumbers.setAdapter(numbersAdapter);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(10);
    }

    public void createFile(View view) {
        final Context context = getApplicationContext();
        new Thread(new Runnable() {
            public void run() {
                FileOutputStream outputStream;
                String toastMessage;

                // save the file
                try {
                    outputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                    int count = 0;
                    progressBar.setProgress(count);
                    for (int i = 1; i <= 10; i++) {
                        String str = String.format(Locale.US, "%d%n", i);
                        outputStream.write(str.getBytes());
                        progressBar.setProgress(++count);
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException ie) {
                            Log.d(TAG, ie.getMessage());
                        }
                    }
                    outputStream.close();
                    toastMessage = getResources().getString(R.string.toast_saved);
                } catch (FileNotFoundException fnfe) {
                    toastMessage = getResources().getString(R.string.toast_error_create);
                } catch (IOException ioe) {
                    toastMessage = getResources().getString(R.string.toast_error_write);
                }

                // notify on success or fail
                final String message = toastMessage;
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).start();
    }

    public void loadFile(View view) {
        final Context context = getApplicationContext();
        new Thread(new Runnable() {
            public void run() {
                FileInputStream inputStream;
                String toastMessage = "";
                StringBuilder buffer = new StringBuilder();
                boolean loadSuccess;

                // load the file
                try {
                    inputStream = context.openFileInput(FILE_NAME);
                    int ch;
                    int count = 0;
                    progressBar.setProgress(count);
                    while ((ch = inputStream.read()) >= 0) {
                        if (ch != '\n') {
                            buffer.append((char) ch);
                        } else {
                            // get the number
                            String strNum = buffer.toString();
                            int num = Integer.parseInt(strNum);
                            buffer.delete(0, buffer.length());

                            // update the UI
                            final int c = ++count;
                            final int n = num;
                            MainActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    progressBar.setProgress(c);
                                    numbersAdapter.add(n);
                                }
                            });
                            Thread.sleep(250);
                        }
                    }
                    inputStream.close();
                    toastMessage = getResources().getString(R.string.toast_loaded);
                } catch (FileNotFoundException fnfe) {
                    toastMessage = getResources().getString(R.string.toast_error_no_file);
                } catch (IOException e) {
                    toastMessage = getResources().getString(R.string.toast_error_read);
                } catch (InterruptedException ie) {
                    Log.d(TAG, ie.getMessage());
                    return;
                } catch (NumberFormatException nfe) {
                    String msg = getResources().getString(R.string.toast_error_nan);
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                }

                // notify
                final String message = toastMessage;
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).start();
    }

    public void clearList(View view) {
        final Context context = getApplicationContext();
        String msg;
        if (numbersAdapter.getCount() > 0) {
            numbersAdapter.clear();
            msg = getResources().getString(R.string.toast_cleared);
        } else {
            msg = getResources().getString(R.string.toast_no_items);
        }
        progressBar.setProgress(0);
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}