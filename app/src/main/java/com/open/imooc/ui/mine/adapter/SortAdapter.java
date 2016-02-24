package com.open.imooc.ui.mine.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.open.imooc.R;
import com.open.imooc.bean.SortModel;


public class SortAdapter extends BaseAdapter implements SectionIndexer{
	private List<SortModel> list = null;
	private Context mContext;
	
	public SortAdapter(Context mContext, List<SortModel> list) {
		this.mContext = mContext;
		this.list = list;
	}
	public void updateListView(List<SortModel> list){
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup viewGroup) {
		ViewHolder viewHolder = null;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.fragment_constacts_item, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.name);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
			viewHolder.ibCallPhone=(TextView) view.findViewById(R.id.invest);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		SortModel mContent = list.get(position);
		int section = getSectionForPosition(position);
		if(position == getPositionForSection(section)){
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContent.getSortLetters());
		}else{
			viewHolder.tvLetter.setVisibility(View.GONE);
		}
		viewHolder.tvTitle.setText(list.get(position).getName());
		viewHolder.ibCallPhone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				String tel="13764659765";
//				Uri uri = Uri.parse("tel:" +tel);
//				Intent intent=new Intent(Intent.ACTION_DIAL, uri);
//				intent.putExtra("message", "您好，这是测试的数据");
//				mContext.startActivity(intent);
			}
		});
		return view;
	}
	


	final static class ViewHolder {
		TextView tvLetter;
		TextView tvTitle;
		TextView ibCallPhone;
	}

	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		
		return -1;
	}
	private String getAlpha(String str) {
		String  sortStr = str.trim().substring(0, 1).toUpperCase();
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}
}