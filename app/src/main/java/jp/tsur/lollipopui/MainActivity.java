package jp.tsur.lollipopui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;


public class MainActivity extends ActionBarActivity {

    private static final String[] MENU = {"normal", "ActivityOptions.makeSceneTransitionAnimation"};
    private static final String IMAGE_URL = "http://tsur.jp/img/nekouo.png";

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(mDrawerToggle);
        ListView drawerList = (ListView) findViewById(R.id.drawer);

        final ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MENU);
        drawerList.setAdapter(adapter);

        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                drawerLayout.closeDrawers();
                Intent intent;
                switch (position) {
                    case 0:
                        intent = new Intent(MainActivity.this, RecyclerViewActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this, RecyclerViewActivity.class);
                        // Check if we're running on Android 5.0 or higher
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            // Call some material design APIs here
                            startActivity(intent,
                                    ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                        } else {
                            // Implement this feature without material design
                            startActivity(intent);
                        }
                }
            }
        });

        ImageView imageView = (ImageView) findViewById(R.id.image_view);
        Picasso.with(this).load(IMAGE_URL).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageActivity.launch(MainActivity.this, findViewById(R.id.image_view), IMAGE_URL);
            }
        });

        findViewById(R.id.recycler_view_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecyclerViewActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

}
