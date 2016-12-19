package com.chsra.photosearch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.net.ssl.HttpsURLConnection;

public class SearchActivity extends AppCompatActivity implements ImageCardAdapter.CallbackInterface {
    ImageView searchButton;
    EditText searchText;
    TextView download, viewAll, emptyText;
    DatabaseDataManager databaseDataManager;
    RecyclerView recyclerView, historyRecyclerView;
    LinearLayoutManager layoutManager, historylayoutManager;
    ArrayList <History> historyList;
    ListView topThreeListView;
    SharedPreferences sharedPreferences;
    public static final String MYPREFERENCES = "mypref";
    SharedPreferences.Editor editor;
    TimeZone tz = TimeZone.getDefault();
    Calendar calendar = Calendar.getInstance(tz);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //setting the color of status bar
        getWindow().setStatusBarColor(getResources().getColor(R.color.myDarkColor));

        Drawable drawable = toolbar.getOverflowIcon();
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable.mutate(), Color.WHITE);
            toolbar.setOverflowIcon(drawable);
        }


        //initializing database data manager
        databaseDataManager = new DatabaseDataManager(this);

        //initializing search layout components
        searchText = (EditText) findViewById(R.id.searchText);
        searchButton = (ImageView) findViewById(R.id.searchButton);
        searchButton.setImageResource(R.drawable.search_button);

        //initializing view all edit text
        viewAll = (TextView) findViewById(R.id.viewAll);
        emptyText = (TextView) findViewById(R.id.emptyText);

        sharedPreferences = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        searchText.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        if (cs.equals("")) { // for backspace
                            return cs;
                        }
                        if (cs.toString().matches("[a-zA-Z ]+")) {
                            return cs;
                        }
                        Toast.makeText(getApplicationContext(), "Search term should only contain alphabets", Toast.LENGTH_SHORT).show();
                        return "";
                    }
                }
        });

        final String searchTerm = sharedPreferences.getString("searchTerm", null);
        if (searchTerm != null) {
            new getImage().execute(searchTerm.replace(" ", "%20"));
        }

        if (databaseDataManager.getCompleteHistory().size() >= 3) {
            viewAll.setVisibility(View.VISIBLE);
        } else {
            viewAll.setVisibility(View.INVISIBLE);
        }

        if (databaseDataManager.getCompleteHistory().size() == 0) {
            emptyText.setText("Your first 3 search list is empty");
            emptyText.setVisibility(View.VISIBLE);
        }

        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this, HistoryActivity.class));
                overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
            }
        });

        //click function for search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchText.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please enter text to search", Toast.LENGTH_SHORT).show();
                } else {
                    new getFinalImage().execute(searchText.getText().toString().replace(" ", "%20"));
                }
            }
        });

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!(s.equals(""))) {
                    new getImage().execute(searchText.getText().toString().replace(" ", "%20"));
                }
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.image_cardView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        topThreeListView = (ListView) findViewById(R.id.searchTopThreeListView);

        if (databaseDataManager.getHistoryAscending().size() != 0) {
            //emptyHistory.setVisibility(View.INVISIBLE);
            SearchTopThreeAdapter adapter = new SearchTopThreeAdapter(SearchActivity.this, R.layout.history_layout, databaseDataManager.getHistoryAscending());
            topThreeListView.setAdapter(adapter);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu); //Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    class getImage extends AsyncTask < String, Integer, ArrayList < String >> {
        @Override
        protected ArrayList < String > doInBackground(String...params) {
            String query = "https://en.wikipedia.org/w/api.php?action=query&generator=search&format=json&exintro&exsentences=1&exlimit=max&gsrlimit=1&gsrsearch=hastemplate:Birth_date_and_age+" + params[0] + "&pithumbsize=230&pilimit=max&prop=pageimages%7Cextracts";
            URL url = null;
            HttpsURLConnection conn = null;
            try {
                url = new URL(query);
                conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                int responseCode = conn.getResponseCode();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in .readLine()) != null) {
                    response.append(inputLine);
                } in .close();
                return JSONUtility.ImageJSONParser.getSearchImage(response.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("testMal", String.valueOf(e));
            } catch (IOException e) {
                Log.d("testEx", String.valueOf(e));
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList < String > result) {
            super.onPostExecute(result);
            if (result != null) {
                Log.d("testResult", result.toString());
                ArrayList <Image> images = new ArrayList < > ();
                Image image = new Image();
                image.setImageUrl(result.get(0));
                image.setIntroduction(result.get(1));
                image.setImageText(result.get(2).toUpperCase());
                images.add(image);
                ImageCardAdapter adapter = new ImageCardAdapter(SearchActivity.this, images);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }



    class getFinalImage extends AsyncTask < String, Integer, ArrayList < String >> {
        @Override
        protected ArrayList < String > doInBackground(String...params) {
            String query = "https://en.wikipedia.org/w/api.php?action=query&generator=search&format=json&exintro&exsentences=1&exlimit=max&gsrlimit=1&gsrsearch=hastemplate:Birth_date_and_age+" + params[0] + "&pithumbsize=220&pilimit=max&prop=pageimages%7Cextracts";
            URL url = null;
            HttpsURLConnection conn = null;
            try {
                url = new URL(query);
                conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                int responseCode = conn.getResponseCode();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in .readLine()) != null) {
                    response.append(inputLine);
                } in .close();
                return JSONUtility.ImageJSONParser.getSearchImage(response.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("testMal", String.valueOf(e));
            } catch (IOException e) {
                Log.d("testEx", String.valueOf(e));
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList < String > result) {
            super.onPostExecute(result);
            if (result != null) {
                databaseDataManager.saveSearchInfo(new History(
                        searchText.getText().toString(),
                        String.valueOf(new SimpleDateFormat("MM/dd/yyyy").format(new Date())),
                        String.valueOf(new SimpleDateFormat("HH:mm aa").format(calendar.getTime()))
                ));

                editor.putString("searchTerm", searchText.getText().toString());
                editor.commit();

                ArrayList <Image> images = new ArrayList < > ();
                Image image = new Image();
                image.setImageUrl(result.get(0));
                image.setIntroduction(result.get(1));
                image.setImageText(result.get(2).toUpperCase());
                images.add(image);
                ImageCardAdapter adapter = new ImageCardAdapter(SearchActivity.this, images);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                finish();
                startActivity(getIntent());
            } else {

                ArrayList <Image> images = new ArrayList < > ();
                Image image = new Image();
                images.add(image);
                image.setImageUrl("http://epochcoffee.com/wp-content/themes/seller/assets/images/placeholder2.jpg");
                image.setIntroduction("No Description avaliable");
                ImageCardAdapter adapter = new ImageCardAdapter(SearchActivity.this, images);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }


}