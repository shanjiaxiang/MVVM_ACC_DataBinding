package com.jorchi.arc.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.jorchi.arc.R;
import com.jorchi.arc.data.local.db.AppDatabaseManager;
import com.jorchi.arc.data.local.db.entity.Girl;
import com.jorchi.arc.data.remote.RemoteDataSource;
import com.jorchi.arc.ui.fragment.GirlListFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    private List<Fragment>  mFragmentList;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new GirlListFragment());
        mFragmentList.add(new GirlListFragment());
        mViewPager = findViewById(R.id.vp_home);
        mViewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager()));

        BottomNavigationBar navigationBar = findViewById(R.id.bottom_navigation_bar);
        navigationBar.setTabSelectedListener(new MainOnTabSelctedListener());
        navigationBar.addItem(new BottomNavigationItem(R.drawable.ic_favorite, "Girl"))
                .addItem(new BottomNavigationItem(R.drawable.ic_grade, "Zhihu"))
                .initialise();

        Toolbar toolbar = findViewById(R.id.tool_bar);
        initToolbar(toolbar, false, "Test");
    }
    protected void initToolbar(Toolbar toolbar, boolean setDisplayHomeAsUpEnabled, String title) {
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(setDisplayHomeAsUpEnabled);
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    private class MainOnTabSelctedListener implements BottomNavigationBar.OnTabSelectedListener{

        @Override
        public void onTabSelected(int position) {
            mViewPager.setCurrentItem(position);
        }

        @Override
        public void onTabUnselected(int position) {

        }

        @Override
        public void onTabReselected(int position) {

        }
    }

    private class MainFragmentPagerAdapter extends FragmentPagerAdapter{

        public MainFragmentPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

    //        Girl girl = new Girl();
//        girl.set_id(1+"");
//        girl.setDesc("desc2");
//        List<Girl> list = new ArrayList<>();
//        list.add(girl);
//        manager = AppDatabaseManager.getInstance(this);
//        manager.insertGirlList(list);
////        if ( manager.getAllGirls().getValue() == null) {
////            Log.d("shan", "null .....");
////            return;
////        }
//        manager.getAllGirls().observe(this, new Observer<List<Girl>>() {
//            @Override
//            public void onChanged(List<Girl> girls) {
//                if (girls == null) return;
//                for (int i = 0; i < girls.size(); i++) {
//                    Log.d("shan", girls.get(i).get_id()+"");
//                    Log.d("shan", girls.get(i).getDesc());
//                    Log.d("shan",  "extra:"+girls.get(i).getPublishedAt()+"");
//                }
//            }
//        });
//        girl = new Girl();
//        girl.set_id(4+"");
//        girl.setDesc("desc4");
//        list.add(girl);
//        manager.insertGirlList(list);
//
//        Log.d("shan", "after delete:");
//        manager.deleteGirlById("4");
//
//        RemoteDataSource.getInstance(this).getGirlList(1);
}
