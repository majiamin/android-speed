package com.speed.faster.activity;

import android.content.res.AssetManager;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.speed.faster.R;
import com.speed.faster.utils.CustomExpandableListView;
import com.speed.faster.utils.ToastUtils;

import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mjm  on 2018/7/11 14:09
 * 域名选择页面----暂时不用
 */
public class HostActivity extends BaseActivity {
    @Bind(R.id.expand_listview)
    CustomExpandableListView expand_listview;

    @Bind(R.id.scrollview)
    ScrollView scrollView;
    public String[] groupStrings = {"西游记", "水浒传", "三国演义", "红楼梦"};
    public String[][] childStrings = {
            {"唐三藏", "孙悟空", "猪八戒", "沙和尚"},
            {"宋江", "林冲", "李逵", "鲁智深"},
            {"曹操", "刘备", "孙权", "诸葛亮", "周瑜"},
            {"贾宝玉", "林黛玉", "薛宝钗", "王熙凤"}
    };
    MyAdapter myAdapter = new MyAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        scrollView.scrollTo(0, 0);
    }

    private void initData() {
        expand_listview.setAdapter(myAdapter);
        //        设置分组项的点击监听事件
        expand_listview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
//                ToastUtils.showMessage(groupStrings[i]);
                // 请务必返回 false，否则分组不会展开
                return false;
            }
        });
        //        设置子选项点击监听事件
        expand_listview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                ToastUtils.showMessage(childStrings[groupPosition][childPosition]);
                return true;
            }
        });
        //扩展事件的监听
        expand_listview.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {
                myAdapter.notifyDataSetChanged();
            }
        });
        expand_listview.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                myAdapter.notifyDataSetChanged();
            }
        });
    }

    class MyAdapter extends BaseExpandableListAdapter {


        @Override
        public void registerDataSetObserver(DataSetObserver dataSetObserver) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

        }

        //        获取分组的个数
        @Override
        public int getGroupCount() {
            return groupStrings.length;
        }

        //        获取指定分组中的子选项的个数
        @Override
        public int getChildrenCount(int groupPosition) {
            return childStrings[groupPosition].length;
        }

        //        获取指定的分组数据
        @Override
        public Object getGroup(int groupPosition) {
            return groupStrings[groupPosition];
        }
        //        获取指定分组中的指定子选项数据

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childStrings[groupPosition][childPosition];
        }

        //        获取指定分组的ID, 这个ID必须是唯一的
        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        //        获取子选项的ID, 这个ID必须是唯一的
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }


        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupViewHolder groupViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(HostActivity.this).inflate(R.layout.item_host, parent, false);
                groupViewHolder = new GroupViewHolder();
                groupViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.label_expand_group);
                groupViewHolder.img_show_item = (ImageView) convertView.findViewById(R.id.img_show_item);
                convertView.setTag(groupViewHolder);
            } else {
                groupViewHolder = (GroupViewHolder) convertView.getTag();
            }
            if (isExpanded) {
                groupViewHolder.img_show_item.setImageResource(R.drawable.show2);
            } else {
                groupViewHolder.img_show_item.setImageResource(R.drawable.show);
            }
            groupViewHolder.tvTitle.setText(groupStrings[groupPosition]);
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildViewHolder childViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(HostActivity.this).inflate(R.layout.item_child_host, parent, false);
                childViewHolder = new ChildViewHolder();
                childViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.label_expand_child);

                convertView.setTag(childViewHolder);
            } else {
                childViewHolder = (ChildViewHolder) convertView.getTag();
            }
            childViewHolder.tvTitle.setText(childStrings[groupPosition][childPosition]);
            return convertView;
        }

        //        指定位置上的子元素是否可选中
        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }

        //        分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们。
        @Override
        public boolean hasStableIds() {
            return true;
        }


        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public void onGroupExpanded(int i) {

        }

        @Override
        public void onGroupCollapsed(int i) {

        }

        @Override
        public long getCombinedChildId(long l, long l1) {
            return 0;
        }

        @Override
        public long getCombinedGroupId(long l) {
            return 0;
        }
    }

    static class GroupViewHolder {
        TextView tvTitle;
        ImageView img_show_item;
    }

    static class ChildViewHolder {
        TextView tvTitle;
    }



}
