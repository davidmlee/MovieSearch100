package com.davidmlee.kata.moviesearch100.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.davidmlee.kata.moviesearch100.R;
import com.davidmlee.kata.moviesearch100.controller.MainController;
import com.davidmlee.kata.moviesearch100.core.MyApp;
import com.davidmlee.kata.moviesearch100.core.ScreenMap;
import com.davidmlee.kata.moviesearch100.listadapter.FilmListAdapter;
import com.davidmlee.kata.moviesearch100.util.Util;

public class MainActivity extends AppCompatActivity {
    private EditText search_text;
    RecyclerView recList;
    FilmListAdapter filmListAdapter;

    @Override
    @SuppressWarnings({"deprecation", "NewApi"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setOverflowIcon(getDrawable(R.drawable.ic_more_vert_black));
        } else {
            toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_more_vert_black));
        }
        setSupportActionBar(toolbar);
        MainController.setMainActivity(this);

        search_text = (EditText)findViewById(R.id.search_text);
        Button submit_button = (Button) findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String str_search_text = search_text.getEditableText().toString();
                if (str_search_text.length() == 0) {
                    Toast.makeText(MainActivity.this, R.string.label_search_text_empty, Toast.LENGTH_LONG).show();
                } else {
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            MainController.searchMovies(str_search_text);
                        }
                    }.start();
                }
            }
        });
        recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        filmListAdapter = new FilmListAdapter(MainController.getFilmList());
        recList.setAdapter(filmListAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainController.setMainActivity(null);
    }

    public void resetMovieList() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                filmListAdapter.notifyDataSetChanged();
                recList.invalidate();
                Activity currentlyResumed = ScreenMap.getCurrentResumedActivity();
                if (currentlyResumed != null && currentlyResumed.getLocalClassName().contains(MainActivity.class.getSimpleName())) {
                    Util.hideSoftKeyboard(MainActivity.this);
                    Snackbar.make(recList,
                            MyApp.getStrRes(R.string.label_movie_list_reset)
                            , Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    public void appendToMovieList() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                filmListAdapter.notifyDataSetChanged();
                recList.invalidate();
                Activity currentlyResumed = ScreenMap.getCurrentResumedActivity();
                if (currentlyResumed != null && currentlyResumed.getLocalClassName().contains(MainActivity.class.getSimpleName())) {
                    Util.hideSoftKeyboard(MainActivity.this);
                    Snackbar.make(recList,
                            MyApp.getStrRes(R.string.label_movie_list_appended)
                            , Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    public void displayQueryError(final String errorString) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Activity currentlyResumed = ScreenMap.getCurrentResumedActivity();
                if (currentlyResumed != null && currentlyResumed.getLocalClassName().contains(MainActivity.class.getSimpleName())) {
                    Util.hideSoftKeyboard(MainActivity.this);
                    Toast.makeText(MainActivity.this, errorString, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged (boolean hasFocus) {
        if (hasFocus) {
            // set height of the RecyclerView - begin
            // WRAP_CONTENT causes a RecyclerView not to display the last item.
            // The work-around is to set the height of a RecyclerView programmatically
            DisplayMetrics dm = new DisplayMetrics();
            this.getWindowManager().getDefaultDisplay().getMetrics(dm);

            int[] loc = new int[2];
            recList.getLocationOnScreen(loc); // get the location of the recyclerview
            int distance_to_bottom = dm.heightPixels - loc[1];

            ViewGroup.LayoutParams params = recList.getLayoutParams();
            params.height = distance_to_bottom; // the recyclerview to extend to the bottom of the screen
            recList.requestLayout();
            // set height of the RecyclerView - end
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(MyApp.getStrRes(R.string.app_name));
            alertDialogBuilder
                    .setMessage(MyApp.getStrRes(R.string.label_version) + "  " + Util.getAppVersionString())
                    .setCancelable(true)
                    .setPositiveButton(MyApp.getStrRes(R.string.label_done),
                            new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
