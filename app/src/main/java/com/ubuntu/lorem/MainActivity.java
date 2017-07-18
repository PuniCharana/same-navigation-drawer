package com.ubuntu.lorem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Fragment 1");

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navigationView.getMenu().getItem(position).setChecked(true);
                switch (position){
                    case 0:
                        getSupportActionBar().setTitle("Fragment 1");
                        break;
                    case 1:
                        getSupportActionBar().setTitle("Fragment 2");
                        break;
                    case 2:
                        getSupportActionBar().setTitle("Fragment 3");
                        break;
                    case 3:
                        getSupportActionBar().setTitle("Fragment 4");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FirstFragment());
        adapter.addFragment(new SecondFragment());
        adapter.addFragment(new ThirdFragment());
        adapter.addFragment(new FourthFragment());
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Load previously selected page
        SharedPreferences prefs = getSharedPreferences("PREVIOUS_PAGE", MODE_PRIVATE);
        int selectedTab = prefs.getInt("selectedTab", 0); //0 is the default value.
        viewPager.setCurrentItem(selectedTab, true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Save current page position
        SharedPreferences.Editor editor = getSharedPreferences("PREVIOUS_PAGE", MODE_PRIVATE).edit();
        editor.putInt("selectedTab", viewPager.getCurrentItem());
        editor.commit();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_fragment1) {
            viewPager.setCurrentItem(0, false);
        } else if (id == R.id.nav_fragment2) {
            viewPager.setCurrentItem(1, false);
        } else if (id == R.id.nav_fragment3) {
            viewPager.setCurrentItem(2, false);
        } else if (id == R.id.nav_fragment4) {
            viewPager.setCurrentItem(3, false);
        } else if (id == R.id.nav_manage) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
