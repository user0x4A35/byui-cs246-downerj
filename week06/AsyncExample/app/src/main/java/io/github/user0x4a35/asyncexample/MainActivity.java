package io.github.user0x4a35.asyncexample;

import android.app.Activity;
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
import java.lang.ref.WeakReference;
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
    private WeakReference<ArrayAdapter<Integer>> numbersAdapterRef;
    private WeakReference<ProgressBar> progBarRef;
    private WeakReference<Context> ctxRef;
    private ProcessLock lock;
    public String tempDude;

    /**
     * MAIN ACTIVITY : ON CREATE
     * This is effectively the constructor for the activity's view.
     * @param savedInstanceState A bundle containing any previously saved metadata
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ctxRef = new WeakReference<>(getApplicationContext());
        
        // setup the list view and its underlying array
        ArrayAdapter<Integer> numbersAdapter = new ArrayAdapter<>(
                getApplicationContext(),
                R.layout.support_simple_spinner_dropdown_item
        );
        ListView listNumbers = findViewById(R.id.listNumbers);
        listNumbers.setAdapter(numbersAdapter);
        numbersAdapterRef = new WeakReference<>(numbersAdapter);

        // setup the progress bar
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progBarRef = new WeakReference<>(progressBar);
        progressBar.setMax(10);

        // set lock to released
        lock = new ProcessLock();
    }

    /**
     * MAIN ACTIVITY : NOTIFY USER
     * This creates a Toast on the UI thread.
     * @param stringResID The resource ID of the message string
     */
    public void notifyUser(int stringResID) {
        final int MSG_ID = stringResID;
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                Context context = ctxRef.get();
                if (context == null) {
                    return;
                }

                Toast.makeText(
                        context,
                        getResources().getText(MSG_ID),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    /**
     * MAIN ACTIVITY : ADD NUMBER TO NUMBER LIST
     * This adds a number to the activity's number list on the UI thread.
     * @param num The number to be added
     */
    public void addNumberToList(int num) {
        final int NUM = num;
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                ArrayAdapter<Integer> numbersAdapter = numbersAdapterRef.get();
                if (numbersAdapter == null) {
                    return;
                }
                numbersAdapter.add(NUM);
            }
        });
    }

    /**
     * MAIN ACTIVITY : CLEAR NUMBER LIST
     * This clears the activity's number list on the UI thread.
     */
    public void clearNumberList() {
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                ArrayAdapter<Integer> numbersAdapter = numbersAdapterRef.get();
                if (numbersAdapter == null) {
                    return;
                }
                numbersAdapter.clear();
            }
        });
    }

    /**
     * MAIN ACTIVITY : UPDATE PROGRESS BAR
     * This updates the activity's progress bar on the UI thread.
     * @param progress The progress count to update with
     */
    public void updateProgressBar(int progress) {
        final int PROGRESS = progress;
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                ProgressBar progressBar = progBarRef.get();
                if (progressBar == null) {
                    return;
                }

                progressBar.setProgress(PROGRESS);
            }
        });
    }

    /**
     * MAIN ACTIVITY : CLEAR PROGRESS BAR
     * (Alias) This clears the progress bar on the UI thread.
     */
    public void clearProgressBar() {
        updateProgressBar(0);
        Context c = getApplicationContext();
        Activity a = (Activity) c;

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
        final int LOCK_ID = lock.acquire();
        if (LOCK_ID == 0) {
            notifyUser(R.string.toast_error_busy);
            return;
        }

        new Thread(new Runnable() {
            public void run() {
                FileOutputStream outputStream;

                Context context = ctxRef.get();

                // create the file
                try {
                    if (context == null) {
                        lock.release(LOCK_ID);
                        return;
                    }
                    outputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                } catch (FileNotFoundException fnfe) {
                    notifyUser(R.string.toast_error_create);
                    Log.d(TAG, fnfe.getMessage());
                    lock.release(LOCK_ID);
                    return;
                }

                // initialize the progress & bar
                int count = 0;
                clearProgressBar();
                clearNumberList();

                // save each number, one at a time
                for (int i = 1; i <= 10; i++) {
                    try {
                        String str = String.format(Locale.US, "%d%n", i);
                        outputStream.write(str.getBytes());

                        // update the UI
                        updateProgressBar(++count);

                        // wait for 1/4 second
                        Thread.sleep(250);
                    } catch (InterruptedException ie) {
                        Log.d(TAG, ie.getMessage());
                    } catch (IOException ioe) {
                        notifyUser(R.string.toast_error_write);
                        Log.d(TAG, ioe.getMessage());
                        lock.release(LOCK_ID);
                        return;
                    }
                }

                // close the file
                try {
                    outputStream.close();
                    notifyUser(R.string.toast_saved);
                } catch (IOException ioe) {
                    notifyUser(R.string.toast_error_write);
                    Log.d(TAG, ioe.getMessage());
                }

                lock.release(LOCK_ID);
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
        final int LOCK_ID = lock.acquire();
        if (LOCK_ID == 0) {
            notifyUser(R.string.toast_error_busy);
            return;
        }

        new Thread(new Runnable() {
            public void run() {
                FileInputStream inputStream;
                StringBuilder buffer = new StringBuilder();

                // initialize the progress & bar
                int count = 0;
                clearProgressBar();
                clearNumberList();

                // open the file
                try {
                    Context context = ctxRef.get();
                    if (context == null) {
                        lock.release(LOCK_ID);
                        return;
                    }
                    inputStream = context.openFileInput(FILE_NAME);
                } catch (FileNotFoundException fnfe) {
                    notifyUser(R.string.toast_error_no_file);
                    Log.d(TAG, fnfe.getMessage());
                    lock.release(LOCK_ID);
                    return;
                }

                // load the file
                int ch = 0;
                do {
                    try {
                        ch = inputStream.read();
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
                            updateProgressBar(++count);
                            addNumberToList(num);

                            // wait for 1/4 second
                            Thread.sleep(250);
                        }
                    } catch (IOException ioe) {
                        notifyUser(R.string.toast_error_read);
                        Log.d(TAG, ioe.getMessage());
                        lock.release(LOCK_ID);
                        return;
                    } catch (NumberFormatException nfe) {
                        notifyUser(R.string.toast_error_nan);
                        Log.d(TAG, nfe.getMessage());
                    } catch (InterruptedException ie) {
                        Log.d(TAG, ie.getMessage());
                    }
                } while (ch >= 0);

                // close the file
                try {
                    inputStream.close();
                    notifyUser(R.string.toast_loaded);
                } catch (IOException ioe) {
                    notifyUser(R.string.toast_error_read);
                    Log.d(TAG, ioe.getMessage());
                }

                lock.release(LOCK_ID);
            }
        }).start();
    }

    /**
     * MAIN ACTIVITY : CLEAR LIST
     * This clears the list view in the UI (and its underlying list).
     * @param view The calling view (e.g. button)
     */
    public void clearList(View view) {
        final int LOCK_ID = lock.acquire();
        if (LOCK_ID == 0) {
            notifyUser(R.string.toast_error_busy);
            return;
        }

        ProgressBar progressBar = progBarRef.get();
        if (progressBar != null) {
            progressBar.setProgress(0);
        }

        ArrayAdapter<Integer> numbersAdapter = numbersAdapterRef.get();
        if (numbersAdapter != null) {
            // clear and/or construct a message conditionally
            if (numbersAdapter.getCount() > 0) {
                numbersAdapter.clear();
                notifyUser(R.string.toast_cleared);
            } else {
                notifyUser(R.string.toast_no_items);
            }
        }

        lock.release(LOCK_ID);
    }
}