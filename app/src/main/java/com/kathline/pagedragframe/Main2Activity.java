package com.kathline.pagedragframe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.kathline.pagedrag.bean.PageData;
import com.kathline.pagedrag.views.DragViewPager;
import com.kathline.pagedragframe.interfaces.AppDeleteListener;
import com.kathline.pagedragframe.interfaces.AppUpdateListener;

import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private DragViewPager vp;
    private PageData<App> pageData;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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

