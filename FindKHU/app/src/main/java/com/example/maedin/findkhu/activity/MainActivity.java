package com.example.maedin.findkhu.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maedin.findkhu.fragment.CenterInfo;
import com.example.maedin.findkhu.fragment.CompleteBoard;
import com.example.maedin.findkhu.fragment.Home;
import com.example.maedin.findkhu.fragment.LostBoard;
import com.example.maedin.findkhu.fragment.MyPage;
import com.example.maedin.findkhu.R;
import com.example.maedin.findkhu.fragment.NoticeView;
import com.example.maedin.findkhu.fragment.SearchInput;
import com.example.maedin.findkhu.fragment.ViewDetail;
import com.example.maedin.findkhu.item.InfoItem;
import com.example.maedin.findkhu.item.MemberInfoItem;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    private DrawerLayout mDrawerLayout;

    MemberInfoItem memberInfoItem;
    TextView user_name;
    Button btn_logout;

    View hview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        memberInfoItem = ((MyApp)getApplication()).getMemberInfoItem();



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

        hview = navigationView.getHeaderView(0);
        user_name = (TextView) hview.findViewById(R.id.txt_userName);
        user_name.setText(memberInfoItem.user_name + " 님");

        btn_logout = (Button) hview.findViewById(R.id.btn_logout) ;
        btn_logout.setOnClickListener(this);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main, new Home())
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for(Fragment fragment : getSupportFragmentManager().getFragments()){
            fragment.onActivityResult(requestCode, resultCode, data);
        }
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

       FragmentManager manager = getSupportFragmentManager();

        switch (item.getItemId()) {
            case R.id.navigation_item_lost:
                ((MyApp)getApplication()).setPostSelect(1);
                manager.beginTransaction().replace(R.id.content_main, new LostBoard()).commit();
                break;

            case R.id.navigation_item_find:
                ((MyApp)getApplication()).setPostSelect(2);
                manager.beginTransaction().replace(R.id.content_main, new LostBoard()).commit();
                break;

            case R.id.navigation_item_complete:
                manager.beginTransaction().replace(R.id.content_main, new CompleteBoard()).commit();
                break;

            case R.id.navigation_item_center:
                manager.beginTransaction().replace(R.id.content_main, new CenterInfo()).commit();
                break;

            case R.id.navigation_home:
                manager.beginTransaction().replace(R.id.content_main, new Home()).commit();
                break;

            case R.id.navigation_mypage:
                manager.beginTransaction().replace(R.id.content_main, new MyPage()).commit();
                break;

            case R.id.navigation_notice:
                manager.beginTransaction().replace(R.id.content_main, new NoticeView()).commit();
                break;

            case R.id.navigation_search:
                manager.beginTransaction().replace(R.id.content_main, new SearchInput()).commit();
                break;
        }

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawer(GravityCompat.START);//Drawer를 닫음
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

    public void replaceDetail(InfoItem item)
    {
        ViewDetail detail = new ViewDetail();
        detail.setInfoItem(item);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, detail).commit();
    }

    public void replaceFragment(Fragment fragment)
    {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, fragment).commit();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_logout)
        {
            finish();
        }
    }
}
