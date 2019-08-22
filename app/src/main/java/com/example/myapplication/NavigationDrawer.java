package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPreferences2 = getSharedPreferences("num", Context.MODE_PRIVATE);
        String  phone3 = sharedPreferences2.getString("Phone", "");
        if(phone3.equals("")) {
            Intent intent=new Intent(NavigationDrawer.this, otpsignin.class );
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        fragment=new viewimage();
        uploadfragment(fragment);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void uploadfragment(Fragment fragment)
    {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager(); // A FragmentManager manages Fragments in Android, specifically it handles transactions between fragments. A transaction is a way to add, replace, or remove fragments.


            FragmentTransaction ft = fragmentManager.beginTransaction(); //As said before a FragmentTransaction gives us methods to add, replace, or remove fragments in Android. It gives us an interface for interacting with fragments.


            ft.replace(R.id.screen_area, fragment);  // replace the current fragment "fragment" on the layout with id: R.id.screen_area
            ft.commit();

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.viewProject) {
            fragment=new viewimage();


        } else if (id == R.id.AddProject) {
               fragment=new rentit();
        } else if (id == R.id.Profile) {

        } else if (id == R.id.Settings) {

        } else if (id == R.id.nav_share) {

        }
        else if(id==R.id.Logout)
        {
            SharedPreferences preferences = getSharedPreferences("num", Context.MODE_PRIVATE);
            preferences.edit().remove("Phone").commit();
            Intent intent =new Intent(NavigationDrawer.this, otpsignin.class);
            startActivity(intent);
            finish();
        }
        uploadfragment(fragment);


            DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
