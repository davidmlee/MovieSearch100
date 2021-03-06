package com.davidmlee.kata.moviesearch100.view;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.davidmlee.kata.moviesearch100.R;
import com.davidmlee.kata.moviesearch100.controller.MovieDetailController;
import com.davidmlee.kata.moviesearch100.core.MyApp;
import com.davidmlee.kata.moviesearch100.models.FilmDetailEntity;
import com.davidmlee.kata.moviesearch100.util.Util;

/**
 * MainActivity of the app
 */
public class MovieDetailActivity extends AppCompatActivity {
    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    private ScrollView detailScrollView;

    @Override
    @SuppressWarnings({"deprecation", "NewApi"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setOverflowIcon(getDrawable(R.drawable.ic_more_vert_black));
        } else {
            toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_more_vert_black));
        }
        setSupportActionBar(toolbar);

        detailScrollView = (ScrollView)findViewById(R.id.detail_scrollview);
        FilmDetailEntity fde = MovieDetailController.getDetail();
        ((TextView)findViewById(R.id.tv_title_field)).setText(fde.getTitle());
        ((TextView)findViewById(R.id.tv_overview_field)).setText(fde.getOverview());
        ((TextView)findViewById(R.id.tv_release_date_field)).setText(fde.getReleaseDate());
        ((TextView)findViewById(R.id.tv_status_field)).setText(fde.getStatus());
        ((TextView)findViewById(R.id.tv_adult_field)).setText(fde.getAdult() ? MyApp.getStrRes(R.string.label_yes) : MyApp.getStrRes(R.string.label_no));
        ((TextView)findViewById(R.id.tv_video_field)).setText(fde.getVideo() ? MyApp.getStrRes(R.string.label_yes) : MyApp.getStrRes(R.string.label_no));
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
            detailScrollView.getLocationOnScreen(loc); // get the location
            int distance_to_bottom = dm.heightPixels - loc[1];

            ViewGroup.LayoutParams params = detailScrollView.getLayoutParams();
            params.height = distance_to_bottom; // the ScrollView to extend to the bottom of the screen
            detailScrollView.requestLayout();
            // set height of the ScrollView - end
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
