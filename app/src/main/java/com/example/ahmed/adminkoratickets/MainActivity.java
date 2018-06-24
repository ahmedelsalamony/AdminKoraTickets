package com.example.ahmed.adminkoratickets;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.ahmed.adminkoratickets.adapter.ViewPagerAdapter;
import com.example.ahmed.adminkoratickets.fragments.FragAllUsers;
import com.example.ahmed.adminkoratickets.fragments.FragTicketsMatches;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("KoraTickets");
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        }
        public void setupViewPager(ViewPager viewPager){
                ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
                adapter.addFragment(new FragTicketsMatches(), "مباريات وتذاكر");
                adapter.addFragment(new FragAllUsers(), "المشجعيين");
                viewPager.setAdapter(adapter);

        }
}
