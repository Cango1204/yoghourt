/**
 * @author zhoushengtao
 * @since 2014年5月20日 上午9:49:52
 */

package com.jason.test.adapter;

import com.jason.test.R;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AppinfoAdapter extends BaseAdapter {
    private List<ApplicationInfo> mApplicationInfos;
    private Context mContext;

    public AppinfoAdapter(Context context, List<ApplicationInfo> applicationInfos) {
	mApplicationInfos = applicationInfos;
	mContext = context;
    }

    @Override
    public int getCount() {
	return mApplicationInfos.size();
    }

    @Override
    public Object getItem(int position) {
	return mApplicationInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
	return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	if (convertView == null) {
	    convertView = LayoutInflater.from(mContext).inflate(R.layout.appinfo_item, null);
	}

	ImageView logoImageView = (ImageView) convertView.findViewById(R.id.appinfo_logo_img);
	TextView titleTextView = (TextView) convertView.findViewById(R.id.appinfo_title_text);
	TextView pkgTextView = (TextView) convertView.findViewById(R.id.appinfo_pkg_text);

	logoImageView.setImageDrawable(mApplicationInfos.get(position).loadIcon(
		mContext.getPackageManager()));
	titleTextView.setText(mApplicationInfos.get(position).loadLabel(
		mContext.getPackageManager()));
	pkgTextView.setText(mApplicationInfos.get(position).packageName);
	return convertView;
    }

}
