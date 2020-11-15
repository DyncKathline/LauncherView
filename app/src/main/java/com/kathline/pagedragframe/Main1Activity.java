package com.kathline.pagedragframe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.kathline.pagedrag2.dragimp.ViewPagerDragListenerImp;
import com.kathline.pagedrag2.viewpager.RecyclerViewPager;
import com.kathline.pagedragframe.interfaces.AppDeleteListener;
import com.kathline.pagedragframe.interfaces.AppUpdateListener;

import java.util.ArrayList;
import java.util.List;

public class Main1Activity extends AppCompatActivity {

    private ArrayList<App> data;
    private RecyclerViewPager vp;
    private ViewPagerDragListenerImp dragListener;
    private GridPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        initData();

        vp = (RecyclerViewPager) this.findViewById(R.id.vp);

        dragListener = new ViewPagerDragListenerImp(vp);
        //边界宽度定义
        dragListener.setLeftOutZone(100);
        dragListener.setRightOutZone(100);
        data = new ArrayList<>(new ArrayList<App>());
        adapter = new GridPagerAdapter(this, data, dragListener);
        vp.setAdapter(adapter);
        vp.setOnDragListener(dragListener);
    }

    private void initData() {
        AppManager.getInstance(this).init();
        AppManager.getInstance(this).addUpdateListener(new AppUpdateListener() {
            @Override
            public boolean onAppUpdated(List<App> apps) {
                data = new ArrayList<>(apps);
                adapter.setData(data);
                return true;
            }
        });
        AppManager.getInstance(this).addDeleteListener(new AppDeleteListener() {
            @Override
            public boolean onAppDeleted(List<App> apps) {
                data = new ArrayList<>(apps);
                adapter.setData(data);
                return true;
            }
        });
    }

    public void insert(View view) {
    }

    public void remove(View view) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dragListener.release();
    }
}
