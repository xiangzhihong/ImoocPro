package com.open.imooc.ui.cource.bean;

import com.open.imooc.bean.BaseModel;

import java.util.List;

/**
 * Created by xiangzhihong on 2015/12/14 on 15:42.
 */
public class CourceBanner extends BaseModel {
    public List<BannerItem> data;

    public class BannerItem{
       public int id;
        public int type;
        public int type_id;
        public String name;
        public String pic;
        public String links;
    }
}
