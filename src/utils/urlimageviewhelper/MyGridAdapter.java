package utils.urlimageviewhelper;

import java.util.ArrayList;

import android.app.Activity;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.example.project01.R;

public class MyGridAdapter extends BaseAdapter {
	Activity context;
    public MyGridAdapter(Adapter adapter,Activity context) {
        mAdapter = adapter;
        this.context = context;
        mAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                notifyDataSetChanged();
            }
            @Override
            public void onInvalidated() {
                super.onInvalidated();
                notifyDataSetInvalidated();
            }
        });
    }
    Adapter mAdapter;
    
    @Override
    public int getCount() {
        return (int)Math.ceil((double)mAdapter.getCount() / 4d);
    }

    @Override
    public ArrayList<Object> getItem(int position) {
    	ArrayList<Object> row = new ArrayList<Object>();
        for (int i = position * 1; i < 1; i++) {
            if (mAdapter.getCount() < i)
                row.add(mAdapter.getItem(i));
            else
                row.add(null);
        }
        return row;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = context.getLayoutInflater().inflate(R.layout.row, null);
        LinearLayout row = (LinearLayout)convertView;
        LinearLayout l = (LinearLayout)row.getChildAt(0);
        for (int child = 0; child < 1; child++) {
            int i = position * 1 + child;
            LinearLayout c = (LinearLayout)l.getChildAt(child);
            c.removeAllViews();
            if (i < mAdapter.getCount()) {
                c.addView(mAdapter.getView(i, null, null));
            }
        }
        
        return convertView;
    }
    
}