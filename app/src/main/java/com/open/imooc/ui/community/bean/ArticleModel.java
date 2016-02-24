package com.open.imooc.ui.community.bean;

import com.open.imooc.bean.BaseModel;

import java.util.List;

/**
 * Created by xiangzhihong on 2015/12/18 on 16:21.
 * 文章model
 */
public class ArticleModel extends BaseModel {
    public List<ArticleItem> data;

    public class ArticleItem{
        public int id;
        public String title;
        public String desc;
        public String img;
    }
}
