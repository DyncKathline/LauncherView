package com.autoai.pagedragframe;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.autoai.pagedrag.bean.DataComparator;
import com.autoai.pagedrag.bean.PageData;
import com.autoai.pagedrag.views.DragViewPager;
import com.autoai.pagedragframe.bean.AppDeleteListener;
import com.autoai.pagedragframe.bean.AppUpdateListener;
import com.autoai.pagedragframe.drag.dragimp.ViewPagerDragListenerImp;
import com.autoai.pagedragframe.test.drag.MyAdapter;
import com.autoai.pagedragframe.viewpager.RecycleViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//public class MainActivity extends AppCompatActivity {
//
//    private ArrayList<TestBean> data;
//    private RecycleViewPager vp;
//    int[] color_array = {Color.DKGRAY, Color.YELLOW, Color.BLUE,
//            Color.CYAN, Color.GRAY, Color.RED, Color.GREEN, Color.MAGENTA, Color.WHITE, Color.LTGRAY};
//    Random r = new Random();
//    private ViewPagerDragListenerImp dragListener;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        initData();
//
//        vp = (RecycleViewPager) this.findViewById(R.id.vp);
//
//        dragListener = new ViewPagerDragListenerImp(vp);
//        //边界宽度定义
//        dragListener.setLeftOutZone(100);
//        dragListener.setRightOutZone(100);
//        GridPagerAdapter adapter = new GridPagerAdapter(this, data, dragListener);
//        vp.setAdapter(adapter);
//        vp.setOnDragListener(dragListener);
//    }
//
//    private void initData() {
//        final ArrayList<TestBean> colors = new ArrayList<>();
//        for (int i = 0; i < 24; i++) {
//            colors.add(new TestBean(color_array[r.nextInt(color_array.length)], i));
//        }
//        data = new ArrayList<>(colors);
//
//    }
//
//    public void insert(View view) {
//    }
//
//    public void remove(View view) {
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        dragListener.release();
//    }
//}

public class MainActivity extends AppCompatActivity {

    private DragViewPager vp;
    Random r = new Random();
    private ViewPagerDragListenerImp dragListener;
    private PageData<App> pageData;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();

        vp = (DragViewPager) this.findViewById(R.id.vp);

        myAdapter = new MyAdapter(this, pageData);
        //边界宽度定义
        vp.setLeftOutZone(100);
        vp.setRightOutZone(100);
        vp.setViewPagerHelper(myAdapter);
        vp.setAdapter(myAdapter);
    }

    private void initData() {
        AppManager.getInstance(this).init();
        AppManager.getInstance(this).addUpdateListener(new AppUpdateListener() {
            @Override
            public boolean onAppUpdated(List<App> apps) {
                for (int i = 0; i < apps.size(); i++) {
                    apps.get(i).id = i;
                }
                pageData = new PageData<App>(4, 4, apps) {
                    @Override
                    public boolean areItemsTheSame(App oldData, App newData) {
                        return oldData.hashCode() == newData.hashCode();
                    }

                    @Override
                    public boolean areContentsTheSame(App oldData, App newData) {
                        return oldData._packageName.equals(newData._packageName);
                    }

                    @Override
                    public Object getChangePayload(App oldData, App newData) {
                        return newData._label;
                    }

                    @Override
                    public int getDataPosition(List<App> allData, App newData) {
                        return allData.indexOf(newData);
                    }
                };
                myAdapter.setPageData(pageData);
                return true;
            }
        });
        AppManager.getInstance(this).addDeleteListener(new AppDeleteListener() {
            @Override
            public boolean onAppDeleted(List<App> apps) {
                for (int i = 0; i < apps.size(); i++) {
                    apps.get(i).id = i;
                }
                pageData = new PageData<App>(4, 4, apps) {
                    @Override
                    public boolean areItemsTheSame(App oldData, App newData) {
                        return oldData.hashCode() == newData.hashCode();
                    }

                    @Override
                    public boolean areContentsTheSame(App oldData, App newData) {
                        return oldData._packageName.equals(newData._packageName);
                    }

                    @Override
                    public Object getChangePayload(App oldData, App newData) {
                        return newData._label;
                    }

                    @Override
                    public int getDataPosition(List<App> allData, App newData) {
                        return allData.indexOf(newData);
                    }
                };
                myAdapter.setPageData(pageData);
                return true;
            }
        });
    }
}
