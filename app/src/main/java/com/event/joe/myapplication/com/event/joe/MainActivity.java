package com.event.joe.myapplication.com.event.joe;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.event.joe.myapplication.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMemorySetListener {

    private static final String MEMORY_DATE = "memoryDate";
    private static final String MEMORY_CATEGORY = "memoryCategory";
    private static final String MEMORY_LOCATION = "memoryLocation";
    private static final String MEMORY_DESCRIPTION = "memoryDescription";
    private static final String MEMORY_IMAGE = "memoryImage";
    private static final String MEMORY_TITLE = "memoryTitle";
    private static final String MEMORY_ID = "memoryID";

    private Memory memory;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment hf = new HomeFragment();
        fragmentTransaction.replace(R.id.fragmentPlaceHolder, hf);
        fragmentTransaction.commit();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

/*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        Menu menu = navigationView.getMenu();
        MenuItem nav_username = menu.findItem(R.id.currentName);
        String name = getIntent().getExtras().getString("name");
        nav_username.setTitle(name);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {


        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /**
         * NExt Step
         */
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (id == R.id.btnAbout) {
            AboutFragment af = new AboutFragment();
            fragmentTransaction.replace(R.id.fragmentPlaceHolder, af);
            fragmentTransaction.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (id == R.id.navTimeline) {
            HomeFragment hf = new HomeFragment();
            fragmentTransaction.replace(R.id.fragmentPlaceHolder, hf);
            fragmentTransaction.commit();
        } else if (id == R.id.navSearch) {
            SearchFragment hf = new SearchFragment();
            fragmentTransaction.replace(R.id.fragmentPlaceHolder, hf);
            fragmentTransaction.commit();
        } else if (id == R.id.navAddMemory) {
            AddMemoryFragment hf = new AddMemoryFragment();
            fragmentTransaction.replace(R.id.fragmentPlaceHolder, hf);
            fragmentTransaction.commit();
        } else if (id == R.id.navCategories) {
            CategoryFragment hf = new CategoryFragment();
            fragmentTransaction.replace(R.id.fragmentPlaceHolder, hf);
            fragmentTransaction.commit();
        } else if (id == R.id.logOut) {
            Intent intent = new Intent(MainActivity.this, LogInActivity.class);
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();
            editor.remove("name");
            editor.remove("userID");
            editor.remove("username");
            editor.commit();
            startActivity(intent);
            finish();
        } else if (id == R.id.currentName) {
            UserDetailsFragment udf = new UserDetailsFragment();
            fragmentTransaction.replace(R.id.fragmentPlaceHolder, udf);
            fragmentTransaction.commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void editMemory(Memory memory) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MemoryEditFragment mef = new MemoryEditFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MEMORY_DATE, memory.getMemoryDate());
        bundle.putString(MEMORY_LOCATION, memory.getLocation());
        bundle.putString(MEMORY_CATEGORY, memory.getCategory());
        bundle.putString(MEMORY_DESCRIPTION, memory.getDescription());
        bundle.putString(MEMORY_IMAGE, memory.getImageResource());
        bundle.putString(MEMORY_TITLE, memory.getTitle());
        bundle.putString(MEMORY_ID, memory.getId());
        mef.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragmentPlaceHolder, mef);
        fragmentTransaction.commit();
    }

    @Override
    public void editMemoryComplete() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment hf = new HomeFragment();
        fragmentTransaction.replace(R.id.fragmentPlaceHolder, hf);
        fragmentTransaction.commit();
    }

    @Override
    public void setNewName(String name) {
        Menu menu = navigationView.getMenu();
        MenuItem nav_username = menu.findItem(R.id.currentName);
        nav_username.setTitle(name);
    }

    @Override
    public void viewMemoryDetails(Memory memory) {
        this.memory = memory;
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MemoryViewFragment mvf = new MemoryViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MEMORY_LOCATION, memory.getLocation());
        bundle.putString(MEMORY_DATE, memory.getMemoryDate());
        bundle.putString(MEMORY_TITLE, memory.getTitle());
        bundle.putString(MEMORY_DESCRIPTION, memory.getDescription());
        bundle.putString(MEMORY_IMAGE, memory.getImageResource());
        mvf.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragmentPlaceHolder, mvf);
        fragmentTransaction.commit();
    }
}
