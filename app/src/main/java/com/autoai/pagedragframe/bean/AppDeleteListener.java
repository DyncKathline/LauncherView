package com.autoai.pagedragframe.bean;

import com.autoai.pagedragframe.App;

import java.util.List;

public interface AppDeleteListener {
    boolean onAppDeleted(List<App> apps);
}
