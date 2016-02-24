package com.open.imooc.ui.details.model;

import com.open.imooc.bean.BaseModel;

import java.util.List;

/**
 * Created by xiangzhihong on 2015/12/24 on 16:43.
 */
public class CommentModel extends BaseModel {

    public List<CommentItem> data;

    public class CommentItem{
        public String id;
        public String uid;
        public String nickname;
        public String img;
        public String description;
        public String create_time;
        public String support_num;
        public int is_support;
        public String media_id;
        public String chapter_seq;
        public String media_title;
        public String media_seq;
    }
}
