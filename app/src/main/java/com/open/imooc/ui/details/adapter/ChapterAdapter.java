package com.open.imooc.ui.details.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.open.imooc.R;
import com.open.imooc.ui.details.model.ChapterModel.DataEntity;
import com.open.imooc.ui.details.model.ChapterModel.DataEntity.MediaEntity;
import com.open.imooc.utils.DateUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xiangzhihong on 2015/12/24 on 11:23.
 */
public class ChapterAdapter extends BaseExpandableListAdapter {

    private Context context;
    //group名称
    private List<DataEntity> groupList;
//    private List<String> groupList ;
    //章节的节的列表
    private List<List<MediaEntity>> childList;

    public ChapterAdapter(Context context, List<DataEntity> groupList,List<List<MediaEntity>> childList) {
        this.context = context;
        this.groupList=groupList;
        this.childList=childList;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHoldView groupHoldView = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.chapter_group_item, null);
            groupHoldView = new GroupHoldView(convertView);
        } else {
            groupHoldView = (GroupHoldView) convertView.getTag();
        }
        DataEntity item = (DataEntity) getGroup(groupPosition);
        String chapterName=item.chapter.name;
        if (!TextUtils.isEmpty(chapterName)) {
            groupHoldView.groupTitle.setText(chapterName);
        }
        notifyDataSetChanged();
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHoldView childHoldView = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.chapter_media_item, null);
            childHoldView = new ChildHoldView(convertView);
//            convertView.setTag(childHoldView);
        } else {
            childHoldView = (ChildHoldView) convertView.getTag();
        }

        MediaEntity mediaEntity= (MediaEntity) getChild(groupPosition,childPosition);
        childHoldView.mediaTitle.setText(mediaEntity.name);
        childHoldView.mediaTime.setText(DateUtils.formatTimer(mediaEntity.duration));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    class GroupHoldView {
        @InjectView(R.id.group_title)
        TextView groupTitle;

        GroupHoldView(View view) {
            ButterKnife.inject(this, view);
            view.setTag(this);
        }

        public void reset() {
            groupTitle.setText("");
        }
    }


    class ChildHoldView {
        @InjectView(R.id.media_image)
        ImageView mediaImage;
        @InjectView(R.id.media_title)
        TextView mediaTitle;
        @InjectView(R.id.media_time)
        TextView mediaTime;

        ChildHoldView(View view) {
            ButterKnife.inject(this, view);
            view.setTag(this);
        }

        public void reset() {
            mediaTitle.setText("");
            mediaTime.setText("00:00");
        }
    }



}
