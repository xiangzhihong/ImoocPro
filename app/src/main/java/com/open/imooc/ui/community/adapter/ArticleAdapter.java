package com.open.imooc.ui.community.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.open.imooc.R;
import com.open.imooc.base.adapter.BasicAdapter;
import com.open.imooc.base.imageCache.ImageCache;
import com.open.imooc.ui.community.bean.ArticleModel.ArticleItem;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ArticleAdapter extends BasicAdapter<ArticleItem> {

    private Activity context;

    public ArticleAdapter(Activity context) {
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.article_item_layout, null);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        initItemView(viewHolder, getItem(position));
        return convertView;
    }

    private void initItemView(ViewHolder viewHolder, ArticleItem item) {
        ImageCache.display(context, item.img, viewHolder.articleImage);
        viewHolder.articleTitle.setText(item.title);
        viewHolder.articleDesc.setText(item.desc);
    }

    static class ViewHolder {
        @InjectView(R.id.article_title)
        TextView articleTitle;
        @InjectView(R.id.article_image)
        ImageView articleImage;
        @InjectView(R.id.article_desc)
        TextView articleDesc;
        @InjectView(R.id.content_layout)
        RelativeLayout contentLayout;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
            view.setTag(this);
        }

        public void reset() {
            articleTitle.setText("");
            articleDesc.setText("");
            articleImage.setBackgroundResource(R.drawable.article_item_default);
        }
    }
}
