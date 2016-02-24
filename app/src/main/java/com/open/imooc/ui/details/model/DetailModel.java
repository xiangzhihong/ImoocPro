package com.open.imooc.ui.details.model;

import com.open.imooc.bean.BaseModel;

import java.util.List;

/**
 * Created by xiangzhihong on 2015/12/25 on 11:28.
 */
public class DetailModel extends BaseModel {

    public List<Detail> data;

    public class Detail {
        public String course_name;
        public String course_des;
        public String finished;
        public List<Teacher> teacher_list;
    }

    public class Teacher {
        public int uid;
        public String nickname;
        public String aboutme;
        public String pic;
        public int sex;
        public int is_v;


    }
}
