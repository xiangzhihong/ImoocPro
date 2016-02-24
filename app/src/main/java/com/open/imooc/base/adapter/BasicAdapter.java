/*
    The Android Not Open Source Project
    Copyright (c) 2014-11-14 wangzheng <iswangzheng@gmail.com>

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    @author wangzheng  DateTime 2014-11-14
 */

package com.open.imooc.base.adapter;

import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicAdapter<T> extends BaseAdapter {
    private List<T> mList = null;

    public BasicAdapter() {
        super();
        mList = new ArrayList<T>();
    }

    public BasicAdapter(List<T> list) {
        super();
        mList = list;
    }

    public void setList(List<T> list) {
        if (list != null) {
            mList = list;
            notifyDataSetChanged();
        }
    }

    public boolean addList(List<T> list) {
        if (list != null && list.size() > 0) {
            mList.addAll(list);
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    public boolean add(T t) {
        if (t != null) {
            mList.add(t);
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    public boolean add(int position, T t) {
        if (t != null && getCount() >= position) {
            mList.add(position, t);
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    public void remove(T t) {
        if (mList.remove(t)) {
            notifyDataSetChanged();
        }
    }

    public void remove(List<T> list) {
        if (mList.remove(list)) {
            notifyDataSetChanged();
        }
    }

    public void remove(int index) {
        if(index >= 0 && index < mList.size()){
            mList.remove(index);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        if (mList != null) {
            mList.clear();
            notifyDataSetChanged();
        }
    }

    public List<T> getList() {
        return mList;
    }

    @Override
    public int getCount() {
        if(mList!=null){
            return mList.size() ;
        }else
        return 0;
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    public T getLastItem() {
        if (mList.size() > 0) {
            return mList.get(mList.size() - 1);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}


