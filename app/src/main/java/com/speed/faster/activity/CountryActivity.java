package com.speed.faster.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.speed.faster.R;
import com.speed.faster.bean.CountryBean;
import com.speed.faster.bean.CountryListBean;
import com.speed.faster.https.HttpNet;
import com.speed.faster.https.HttpUrl;
import com.speed.faster.https.RequestLinstener;
import com.speed.faster.utils.L;
import com.speed.faster.utils.LoadDialog;
import com.speed.faster.utils.SharedUtils;
import com.speed.faster.utils.ToastUtils;
import com.speed.faster.utils.ToolUtils;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CountryActivity extends BaseActivity {
    @Bind(R.id.listview)
    ListView listview;
    ArrayList<CountryBean> list = new ArrayList<CountryBean>();
    MyAdapter myAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        ButterKnife.bind(this);
        if (SharedUtils.getObject(SharedUtils.COUNTRY_LIST_BEAN) != null) {
            CountryListBean countryListBean = SharedUtils.getObject(SharedUtils.COUNTRY_LIST_BEAN);
            list.clear();
            list = countryListBean.getCountryBeanList();
            listview.setAdapter(new MyAdapter(CountryActivity.this, list));
        }
        initView();
        initData();
    }


    public void initView() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.showMessage(list.get(position).getName());
                SharedUtils.putString("country_code", "");
                SharedUtils.putString("country_code", list.get(position).getCode());
                for (int i = 0; i < list.size(); i++) {
                    list.remove(i);
                }
                Intent it = new Intent();
                setResult(RESULT_OK, it);
                list.clear();
                finish();
            }
        });
    }

    public void initData() {
        HttpNet.httpGet(HttpUrl.LOCALES, CountryActivity.this, null, new RequestLinstener() {
            @Override
            public void OnSuccess(String responseString) {
                try {
                    list.clear();
                    JSONArray jsonArray = new JSONArray(responseString);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        CountryBean countryBean = new CountryBean();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.optString("name");
                        String code = jsonObject.optString("code");
                        L.e("name---" + i + "--" + name);
                        countryBean.setName(name);
                        countryBean.setCode(code);
                        countryBean.setFlag(false);
                        list.add(countryBean);
                    }
                    //初始化页面
                    if (list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getCode().equals(SharedUtils.getString("country_code"))) {
                                list.get(i).setFlag(true);
                            }
                        }
                        //更新本地国家库
                        CountryListBean countryListBean = new CountryListBean();
                        countryListBean.setCountryBeanList(list);
                        SharedUtils.saveObject(SharedUtils.COUNTRY_LIST_BEAN, countryListBean);
//                        是否是第一次
//                        if(SharedUtils.getBoolean("isGetorcreate")){
                        listview.setAdapter(new MyAdapter(CountryActivity.this, list));
//                        }else{
//                            myAdapter.notifyDataSetChanged();
//                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void OnError(String errorString) {
                ToastUtils.showMessage(errorString);
            }
        });

    }

    @OnClick({R.id.back})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }

    }

    class MyAdapter extends BaseAdapter {
        private Context mContext = null;
        ArrayList<CountryBean> listAdapter = new ArrayList<CountryBean>();

        public MyAdapter(Context mContext, ArrayList<CountryBean> list) {
            this.mContext = mContext;
            this.listAdapter = list;
        }

        @Override
        public int getCount() {
            return listAdapter.size();
        }

        @Override
        public CountryBean getItem(int position) {
            return listAdapter.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (null == convertView) {
                viewHolder = new ViewHolder();
                LayoutInflater mInflater = LayoutInflater.from(mContext);
                convertView = mInflater.inflate(R.layout.item_country, null);
                viewHolder.name = (TextView) convertView.findViewById(R.id.name);
                viewHolder.img_country_item = (ImageView) convertView.findViewById(R.id.img_country_item);
                viewHolder.img_show_item = (ImageView) convertView.findViewById(R.id.img_show_item);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //国际化
            String able = getResources().getConfiguration().locale.getCountry();
            L.e("当前手机系统语言-----" + able);
            if ("CN".equals(able)) {
                viewHolder.name.setText(listAdapter.get(position).getName());
            } else {
                viewHolder.name.setText(listAdapter.get(position).getCode());
            }
            viewHolder.img_country_item.setImageBitmap(ToolUtils.getImageFromAssetsFile(mContext, listAdapter.get(position).getCode()));
            if (listAdapter.get(position).isFlag()) {
                viewHolder.img_show_item.setImageResource(R.drawable.check);
                viewHolder.img_show_item.setVisibility(View.VISIBLE);
            }
            return convertView;
        }
    }

    static class ViewHolder {
        TextView name;
        ImageView img_country_item, img_show_item;
    }


}
