package com.open.imooc.widght;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.open.imooc.R;
import com.open.imooc.utils.Utils;

//时间选择器
public class DateSelecter{

	private OnGetDateListener onGetDateListener = null;
	private DatePicker datePicker = null;
	private PopupWindow pw = null;
	private Activity context = null;
	private LayoutParams lp = null;
    private TextView tv;

	public DateSelecter(Activity context) {
		this.context = context;
		init();
	}
	
	public void show(View v){
		lp.alpha = 0.6f;
		context.getWindow().setAttributes(lp);
		pw.showAtLocation(v, Gravity.BOTTOM, 0, 0);
	}


	private void init(){
		lp = context.getWindow().getAttributes();
		
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.date_selector_layout, null);
		
		datePicker = (DatePicker) view.findViewById(R.id.datePicker);

		view.findViewById(R.id.cancel).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pw.dismiss();
			}
		});
		view.findViewById(R.id.determine).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(onGetDateListener != null){
					String year = datePicker.getYear()+"";
					String month = (datePicker.getMonth()+1) +"";
					if(!TextUtils.isEmpty(month) && month.length() < 2){
						month = "0"+month;
					}
					String day = datePicker.getDayOfMonth()+"";
					if(!TextUtils.isEmpty(day) && day.length() < 2){
						day = "0"+day;
					}
					onGetDateListener.onGetDate(year,month,day,tv);
				}
				pw.dismiss();
			}
		});
		
		
		
		pw = new PopupWindow(view, LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT, false);
		pw.setSoftInputMode(LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		
		pw.setAnimationStyle(R.style.dateSelectorStyle);
		pw.setBackgroundDrawable(new BitmapDrawable());
		
		pw.setOutsideTouchable(false);
		pw.setFocusable(true);
		pw.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				lp.alpha = 1.0f;
				context.getWindow().setAttributes(lp);
				pw.dismiss();
			}
		});

    }
    
    public void updateDate(String dateStr,TextView tv){
    	this.tv=tv;
    	Calendar calendar = Calendar.getInstance();
    	if(!TextUtils.isEmpty(dateStr)){
    		Date date = Utils.toDate(dateStr);
    		calendar.setTime(date);
    		datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		}
    }
    public void setOnGetDateListener(OnGetDateListener onGetDateListener){
    	this.onGetDateListener = onGetDateListener;
    }
    public interface OnGetDateListener{
    	 void onGetDate(String year, String month, String day, TextView tv);
    }
}


