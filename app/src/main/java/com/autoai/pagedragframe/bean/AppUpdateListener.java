package com.autoai.pagedragframe.bean;

import com.autoai.pagedragframe.App;

import java.util.List;

public interface AppUpdateListener {
    boolean onAppUpdated(List<App> apps);
}
