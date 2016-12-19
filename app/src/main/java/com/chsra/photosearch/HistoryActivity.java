package com.chsra.photosearch;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class HistoryActivity extends AppCompatActivity {

    ListView topThreeListView;
    DatabaseDataManager databaseDataManager;
    TextView emptyHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setStatusBarColor(getResources().getColor(R.color.myDarkColor));

        Drawable drawable = toolbar.getOverflowIcon();
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable.mutate(), Color.WHITE);
            toolbar.setOverflowIcon(drawable);
        }

        final Drawable backArrow = getResources().getDrawable(R.drawable.ic_chevron_left_white_36dp);
        backArrow.setColorFilter(getResources().getColor(R.color.backArrowColor), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(backArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        //instantiating database datamanager
        databaseDataManager = new DatabaseDataManager(this);

        //instantiating list view
        topThreeListView = (ListView) findViewById(R.id.historyListView);
        emptyHistory = (TextView) findViewById(R.id.emptyHistory);

        if (databaseDataManager.getCompleteHistory().size() != 0) {
            emptyHistory.setVisibility(View.INVISIBLE);

            //customized adapter for history complete history list view
            CompleteHistoryAdapter adapter = new CompleteHistoryAdapter(this, R.layout.history_layout, databaseDataManager.getCompleteHistory());

            //setting adapter to list view
            topThreeListView.setAdapter(adapter);
        } else {
            emptyHistory.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu); //Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.clearHistory:
                databaseDataManager.deleteHistory();
                topThreeListView.setVisibility(View.INVISIBLE);
                emptyHistory.setVisibility(View.VISIBLE);
                return true;

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }
}