package com.phone.base.common.utils;

import java.util.List;

/**
 * description ：
 * author : 
 * email : @waclighting.com.cn
 * date : 2020/4/27
 */

public class GroupItem<T> {
    private String groupName;
    private List<T> items;

    public GroupItem(String groupName, List<T> items) {
        this.groupName = groupName;
        this.items = items;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
