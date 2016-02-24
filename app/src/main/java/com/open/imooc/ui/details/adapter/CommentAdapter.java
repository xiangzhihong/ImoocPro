package com.open.imooc.ui.details.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.open.imooc.R;
import com.open.imooc.base.adapter.BasicAdapter;
import com.open.imooc.base.imageCache.ImageCache;
import com.open.imooc.ui.details.model.CommentModel.CommentItem;
import com.open.imooc.widght.MCIconFontView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CommentAdapter extends BaseAdapter {

    private Activity context;
    private List<CommentItem> commentList;

    public CommentAdapter(Activity context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.comment_item_layout, null);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        initItemView(viewHolder, getItem(position));
        return convertView;
    }

    private void initItemView(ViewHolder viewHolder, CommentItem item) {
       String defaulUrl="http://img.mukewang.com/543005110001025601000100-370-370.jpg";
        ImageCache.display(context, /*item.img*/defaulUrl, viewHolder.headImg);
        viewHolder.userName.setText(item.nickname);
        viewHolder.time.setText(item.create_time);
        viewHolder.content.setText(item.description);
        viewHolder.praisedNum.setText(item.support_num);
    }

    static class ViewHolder {
        @InjectView(R.id.head_img)
        ImageView headImg;
        @InjectView(R.id.userName)
        TextView userName;
        @InjectView(R.id.time)
        TextView time;
        @InjectView(R.id.content)
        TextView content;
        @InjectView(R.id.praisedNum)
        TextView praisedNum;
        @InjectView(R.id.praised_icon)
        MCIconFontView praisedIcon;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
            view.setTag(this);
        }

        public void reset() {
            userName.setText("");
            time.setText("");
            content.setText("");
            praisedNum.setText("");
            headImg.setBackgroundResource(R.drawable.personal_default_user_icon);
        }
    }

    public void setList(List<CommentItem> list) {
        this.commentList = list;
    }
}
