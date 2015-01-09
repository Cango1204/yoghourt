package com.example.hbjia.pager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hbjia.http.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerDemo extends FragmentActivity {

    private static int TOTAL_COUNT = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_demo);

        ViewPager vp = (ViewPager) findViewById(R.id.viewPager);
        //pages
        List<Fragment> fragmentList = new ArrayList<Fragment>();

        //titles
        List<String> titleList = new ArrayList<String>();
        for(int i = 0; i < TOTAL_COUNT; i++) {
            ViewPagerFrament viewPagerFrament = new ViewPagerFrament();
            Bundle bundle = new Bundle();
            bundle.putInt("upImageId", 0);
            bundle.putString("text", "Page " + i);
            viewPagerFrament.setArguments(bundle);
            titleList.add("title " + i);
            fragmentList.add(viewPagerFrament);
        }
        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragmentList, titleList));

        PagerTabStrip tabStrip = (PagerTabStrip) findViewById(R.id.pagerTab);
        tabStrip.setTextSpacing(0);
        tabStrip.setTabIndicatorColor(Color.RED);
        tabStrip.setTextColor(Color.RED);
        tabStrip.setClickable(false);
        tabStrip.setTextSpacing(40);
        tabStrip.setBackgroundColor(Color.GRAY);
        tabStrip.setDrawFullUnderline(true);
    }

    class MyPagerAdapter extends FragmentPagerAdapter{

        private List<Fragment> fragmentList;
        private List<String> titleList;

        public MyPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
            super(fm);
            this.fragmentList = fragmentList;
            this.titleList = titleList;
        }

        @Override
        public Fragment getItem(int position) {
            return (fragmentList == null || fragmentList.size() == 0) ? null :
                    fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList == null ? 0 : fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return (titleList.size() > position) ? titleList.get(position) : "";
//            return "";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_pager_demo, menu);
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
}
