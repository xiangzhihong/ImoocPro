package com.open.imooc.ui.community.bean;

import com.open.imooc.bean.BaseModel;

import java.util.List;

/**
 * Created by xiangzhihong on 2015/12/21 on 11:39.
 */
public class ActiveModel extends BaseModel {
    public List<ActiveItem> data;

    public class ActiveItem{
        public int id;
        public String name;
        public String start_time;
        public String end_time;
        public String create_time;
        public String uid;
        public String update_time;
        public String hits;
        public String links;
        public String pic;
        public String description;
        public String istop;
        public String status;
        public String share;
    }
}
