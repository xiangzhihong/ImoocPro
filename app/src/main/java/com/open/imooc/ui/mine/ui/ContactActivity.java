package com.open.imooc.ui.mine.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.open.imooc.R;
import com.open.imooc.bean.ContactModel;
import com.open.imooc.bean.SortModel;
import com.open.imooc.ui.mine.adapter.SortAdapter;
import com.open.imooc.utils.AsyncTaskBase;
import com.open.imooc.utils.CharacterParser;
import com.open.imooc.utils.ConstactUtil;
import com.open.imooc.utils.PinyinComparator;
import com.open.imooc.widght.ClearEditText;
import com.open.imooc.widght.LoadingView;
import com.open.imooc.widght.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class ContactActivity extends Activity {
    @InjectView(R.id.back)
    ImageButton back;
    @InjectView(R.id.clear_edit)
    ClearEditText clearEdit;
    @InjectView(R.id.contact_listview)
    ListView contactListview;
    @InjectView(R.id.dialog)
    TextView dialog;
    @InjectView(R.id.sidebar)
    SideBar sideBar;
    @InjectView(R.id.loading)
    LoadingView loading;

    private Context mContext;
    private SortAdapter adapter;
//    private Map<String, String> callRecords;
    private List<ContactModel> contactInfoList;
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;
    private ArrayList<SortModel> dataArrayList;
    private SortModel model = null;
    private PinyinComparator pinyinComparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_contact);
        ButterKnife.inject(this);
        mContext = ContactActivity.this;
        initData();
    }

    private void clickitem() {
        dataArrayList = new ArrayList<SortModel>();
        adapter = new SortAdapter(mContext, dataArrayList);
        contactListview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                model = (SortModel) adapter.getItem(position - 1);
            }
        });

    }

    private void initData() {
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        sideBar.setTextView(dialog);
        dataArrayList = new ArrayList<SortModel>();
        adapter = new SortAdapter(mContext, dataArrayList);
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @SuppressLint("NewApi")
            @Override
            public void onTouchingLetterChanged(String s) {
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    contactListview.setSelection(position);
                }
            }
        });

        contactListview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Toast.makeText(mContext,
                        "用户名："+((SortModel)adapter.getItem(position)).getName()+","+"号码："+((SortModel)adapter.getItem(position)).getPhoneNum(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        new AsyncTaskConstact(loading).execute(0);
    }

    private class AsyncTaskConstact extends AsyncTaskBase {
        public AsyncTaskConstact(LoadingView loadingView) {
            super(loadingView);
        }
        @Override
        protected Integer doInBackground(Integer... params) {
            int result = -1;
            contactInfoList= ConstactUtil.getPhoneContacts(mContext);
            result = 1;
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {

            super.onPostExecute(result);
            if (result == 1) {
                List<String> constact = new ArrayList<String>();
                for(ContactModel bean:contactInfoList){
                    String key = bean.contactName;
                    constact.add(key);
                }
                String[] names = new String[]{};
                names = constact.toArray(names);
                SourceDateList = filledData(names);
                Collections.sort(SourceDateList, pinyinComparator);
                adapter = new SortAdapter(mContext, SourceDateList);
                contactListview.setAdapter(adapter);
                // 根据输入框输入值的改变来过滤搜索
                clearEdit.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        filterData(s.toString());
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
            }
        }
    }


    private List<SortModel> filledData(String[] date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < date.length; i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1
                        || characterParser.getSelling(name).startsWith(
                        filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }

    @OnClick(R.id.back)
    public void back(){
        finish();
    }

}
