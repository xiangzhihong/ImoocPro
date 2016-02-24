package com.open.imooc.ui.cource.bean;

import com.open.imooc.bean.BaseModel;

import java.util.List;

/**
 * Created by xiangzhihong on 2015/12/15 on 16:14.
 */
public class CourceList extends BaseModel {
    public List<CourceData> data;

    public class CourceData{
        public int id;
        public String name;
        public String pic;
        public String desc;
        public int is_learned;
        public int company_id;
        public int numbers;
        public long update_time;
        public int coursetype;
        public long duration;
        public int finished;
        public int is_follow;
        public int max_chapter_seq;
        public int max_media_seq;
    }
}
