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

/**
 * MAIN ACTIVITY
 * This is the primary view of the application which contains three buttons, a
 * progress bar, and a list view.
 * @author James D. Downer
 * @since October 24, 2018
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "numbers.txt";
    private static final String TAG = "MainActivity";
    private static final int LOCK_TAG_NONE = 0;
    private static final int LOCK_TAG_CREATE = 1;
    private static final int LOCK_TAG_LOAD = 2;
    private static final int LOCK_TAG_CLEAR = 3;
    private ArrayAdapter<Integer> numbersAdapter;
    private int lock;
    private ProgressBar progressBar;

    /**
     * MAIN ACTIVITY : ON CREATE
     * This is effectively the constructor for the activity's view.
     * @param savedInstanceState A bundle containing any previously saved metadata
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // setup the list view and its underlying array
        numbersAdapter = new ArrayAdapter<>(
                getApplicationContext(),
                R.layout.support_simple_spinner_dropdown_item
        );
        ListView listNumbers = findViewById(R.id.listNumbers);
        listNumbers.setAdapter(numbersAdapter);

        // setup the progress bar
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(10);

        // set lock to released
        lock = LOCK_TAG_NONE;
    }

    /**
     * Acquires the thread lock if available.
     * @param tag The tag of the calling process
     * @return Whether or not the lock was successfully acquired
     */
    public boolean acquireLock(int tag) {
        final Context context = getApplicationContext();
        if (lock == LOCK_TAG_NONE) {
            lock = tag;
            return true;
        } else {
            Toast.makeText(
                    context,
                    getResources().getText(R.string.toast_busy),
                    Toast.LENGTH_LONG
            ).show();
            return false;
        }
    }

    /**
     * RELEASE LOCK
     * Releases the thread lock if associated with the calling process.
     * @param tag The tag of the calling process
     */
    public void releaseLock(int tag) {
        if (lock == tag) {
            lock = LOCK_TAG_NONE;
        }
    }

    /**
     * MAIN ACTIVITY : CREATE FILE
     * This is a callback for the "Create" button's onclick() trigger.
     * A file is created, and integers between 1 and 10 (inclusive) are written
     * to the file, pausing for 1/4 second after each write.
     * Each time a number is written, the UI's progress bar is updated.
     * @param view The calling view (e.g. button)
     */
    public void createFile(View view) {
        final Context context = getApplicationContext();
        if (!acquireLock(LOCK_TAG_CREATE)) {
            return;
        }

        new Thread(new Runnable() {
            public void run() {
                FileOutputStream outputStream;
                String toastMessage = "";

                // save the file
                try {
                    outputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);

                    // initialize the progress & bar
                    int count = 0;
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            progressBar.setProgress(0);
                            numbersAdapter.clear();
                        }
                    });

                    // save each number, one at a time
                    for (int i = 1; i <= 10; i++) {
                        String str = String.format(Locale.US, "%d%n", i);
                        outputStream.write(str.getBytes());
                        
                        // update the UI
                        final int c = ++count;
                        MainActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                progressBar.setProgress(c);
                            }
                        });

                        // wait for 1/4 second
                        Thread.sleep(250);
                    }
                    outputStream.close();
                    toastMessage = getResources().getString(R.string.toast_saved);
                } catch (FileNotFoundException fnfe) {
                    toastMessage = getResources().getString(R.string.toast_error_create);
                } catch (IOException ioe) {
                    toastMessage = getResources().getString(R.string.toast_error_write);
                } catch (InterruptedException ie) {
                    Log.d(TAG, ie.getMessage());
                }

                // notify
                final String message = toastMessage;
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    }
                });

                releaseLock(LOCK_TAG_CREATE);
            }
        }).start();
    }

    /**
     * MAIN ACTIVITY : LOAD FILE
     * This is a callback for the "Load" button's onclick() trigger.
     * A file is loaded, and integers are read from the file, pausing for
     * 1/4 second after each write.
     * Each time a number is read, the UI's list view and progress bar are
     * updated.
     * @param view The calling view (e.g. button)
     */
    public void loadFile(View view) {
        final Context context = getApplicationContext();
        if (!acquireLock(LOCK_TAG_LOAD)) {
            return;
        }

        new Thread(new Runnable() {
            public void run() {
                FileInputStream inputStream;
                String toastMessage = "";
                StringBuilder buffer = new StringBuilder();

                // load the file
                try {
                    // initialize the progress & bar
                    int count = 0;
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            progressBar.setProgress(0);
                            numbersAdapter.clear();
                        }
                    });

                    inputStream = context.openFileInput(FILE_NAME);
                    int ch;
                    while ((ch = inputStream.read()) >= 0) {
                        // if inside of a line...
                        if (ch != '\n') {
                            buffer.append((char) ch);
                        // if at the end of a line...
                        } else {
                            // parse the number and clear the buffer
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

                            // wait for 1/4 second
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

                releaseLock(LOCK_TAG_LOAD);
            }
        }).start();
    }

    /**
     * MAIN ACTIVITY : CLEAR LIST
     * This clears the list view in the UI (and its underlying list).
     * @param view The calling view (e.g. button)
     */
    public void clearList(View view) {
        final Context context = getApplicationContext();
        if (!acquireLock(LOCK_TAG_CLEAR)) {
            return;
        }

        // construct the message conditionally
        String msg;
        if (numbersAdapter.getCount() > 0) {
            numbersAdapter.clear();
            msg = getResources().getString(R.string.toast_cleared);
        } else {
            msg = getResources().getString(R.string.toast_no_items);
        }
        
        // reset the progress bar and notify
        progressBar.setProgress(0);
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

        releaseLock(LOCK_TAG_CLEAR);
    }
}