package com.example.weatherapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {
    FeedReaderDbHelper dbHelper;
    RecyclerView recyclerView;
    ArrayList<DataModel> mData = new ArrayList<>();
    CustomAdapter adapter;
    private ConnectivityManager.NetworkCallback connectivityManagerCallback;
    private static final String DEBUG_TAG = "NetworkCallbackMessage";

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        if (!Common.getConnectivityState(MainActivity.this, connectivityManager)) {
            setContentView(R.layout.check_internet_layout);
        } else {
            setContentView(R.layout.activity_main);

            recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://www.gazzetta.gr/";

            StringRequest stringRequest = new StringRequest(
                    Request.Method.GET, url, response -> {
                        InputSource is = new InputSource();
                        is.setCharacterStream(new StringReader(response));

                        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder dBuilder;
                        Document doc = null;

                        try {
                            dBuilder = dbFactory.newDocumentBuilder();
                            doc = dBuilder.parse(is);
                        } catch (IOException | SAXException | ParserConfigurationException e) {
                            e.printStackTrace();
                        }

                        ContentValues values = new ContentValues();
                        dbHelper = new FeedReaderDbHelper(getApplicationContext());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        assert doc != null;
                        NodeList itemNodeList = doc.getElementsByTagName("item");
                        for (int i = 0; i < itemNodeList.getLength(); i++) {
                            String title = "";
                            String short_description = "";
                            String description = "";
                            String pubDate = "";
                            String image_main = "";
                            String xml_link = "";
                            String main_category = "";

                            Node itemNode = itemNodeList.item(i);
                            if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element element2 = (Element) itemNode;
                                title = element2.getElementsByTagName("title").item(0).getTextContent();
                                description = element2.getElementsByTagName("description").item(0).getTextContent();
                                pubDate = element2.getElementsByTagName("pubDate").item(0).getTextContent();
                                image_main = extractImageUrl(description);
                                xml_link = element2.getElementsByTagName("link").item(0).getTextContent();
                                main_category = element2.getElementsByTagName("category").item(0).getTextContent();

                                values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, title);
                                values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION, description);
                                values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_PUBDATE, pubDate);
                                values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_IMAGEMAIN, image_main);
                                values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_XMLLINK, xml_link);
                                values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_MAINCATEGORY, main_category);

                                long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
                            }
                        }

                        db.close();
                        dbHelper.close();

                        adapter = new CustomAdapter(mData, MainActivity.this);
                        recyclerView.setAdapter(adapter);
                    }, error -> {
                Toast.makeText(MainActivity.this, "Error loading the feed", Toast.LENGTH_SHORT).show();
                Log.d(DEBUG_TAG, error.toString());
            });

            queue.add(stringRequest);
        }
    }

    private String extractImageUrl(String description) {
        String imageUrl = "";
        Pattern pattern = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
        Matcher matcher = pattern.matcher(description);
        if (matcher.find()) {
            imageUrl = matcher.group(1);
        }
        return imageUrl;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, NewsDetailsActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_top_stories) {
            // Handle top stories action
            return true;
        } else if (id == R.id.action_science_technology) {
            // Handle science and technology action
            return true;
        } else if (id == R.id.action_lifestyle) {
            // Handle lifestyle action
            return true;
        } else if (id == R.id.action_economy) {
            // Handle economy action
            return true;
        } else if (id == R.id.action_business) {
            // Handle business action
            return true;
        } else if (id == R.id.action_health) {
            // Handle health action
            return true;
        } else if (id == R.id.action_entertainment) {
            // Handle entertainment action
            return true;
        } else if (id == R.id.action_search) {
            // Handle search action
            return true;
        } else if (id == R.id.action_refresh) {
            // Handle refresh action
            return true;
        } else if (id == R.id.action_logout) {
            // Handle logout action
            return true;
        } else if (id == R.id.action_exit) {
            // Handle exit action
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
