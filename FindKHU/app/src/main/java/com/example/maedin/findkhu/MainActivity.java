package com.example.maedin.findkhu;

import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); //activity actionbar로 대체

        //ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //drawerLayout 지정
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //NavigationView 지정, 클릭시 setNavigationItemSelected로 이동
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main, new Home())
                .commit();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        item.setChecked(true);

        int id = item.getItemId();
       // FragmentManager manager = getFragmentManager();



        switch (id) {
            case R.id.navigation_item_lost:

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main, new LostBoard())
                        .commit();
                break;

            case R.id.navigation_item_find:
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main, new FindBoard())
                        .commit();
                break;

            case R.id.navigation_item_complete:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                break;

            case R.id.navigation_item_center:   //분실물 센터 버튼 눌리면 실행

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main, new CenterInfo())
                        .commit();

               // manager.beginTransaction().replace(R.id.content_main, new CenterInfo()).commit();
                break;

            case R.id.navigation_home:
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main, new Home())
                        .commit();
                break;

            case R.id.navigation_mypage:
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main, new Home())
                        .commit();
                break;

            case R.id.navigation_notice:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                break;

            case R.id.navigation_search:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);//Drawer를 닫음
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
