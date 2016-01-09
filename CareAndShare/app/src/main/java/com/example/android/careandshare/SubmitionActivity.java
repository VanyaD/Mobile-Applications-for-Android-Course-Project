package com.example.android.careandshare;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SubmitionActivity extends AppCompatActivity {
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submition);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter adapter = new NavigationPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    public void GoToCameraPage(View view) {
        int current = viewPager.getCurrentItem();
        viewPager.setCurrentItem(current + 1);
    }

    public void GoToDetailsPage(View view) {
        int current = viewPager.getCurrentItem();
        viewPager.setCurrentItem(current + 1);
    }

    private class NavigationPagerAdapter extends FragmentPagerAdapter {
        public NavigationPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0: fragment = new SubmitionActivityFragment(); break;
                case 1: fragment = new CameraFragment(); break;
                case 2: fragment = new DetailsFragment(); break;
            }
            return fragment;
        }
    }
}
