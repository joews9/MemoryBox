package com.event.joe.myapplication;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.view.menu.MenuView;
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

import com.event.joe.myapplication.com.event.joe.Memory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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
        String name = getIntent().getExtras().getString("name");
        TextView tv = (TextView) findViewById(R.id.tvName);
        tv.setText(name);
        /**
         * NExt Step
         */

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
            editor.remove("username");
            editor.commit();
            startActivity(intent);
            finish();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
