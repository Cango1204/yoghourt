/**
 * @author zhoushengtao
 * @since 2014年5月21日 下午6:17:10
 */

package com.jason.test;

import com.jason.test.adapter.AppinfoAdapter;
import com.jason.test.service.FloatWindowService;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends Activity {

    private ListView mListView;
    private List<ApplicationInfo> mApplicationInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	mApplicationInfos = queryAllApps(getApplicationContext());

	mListView = (ListView) findViewById(R.id.appinfo_listview);
	new Thread() {
	    public void run() {
		mListView
			.setAdapter(new AppinfoAdapter(getApplicationContext(), mApplicationInfos));
	    }
	}.start();

	mListView.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
		new AlertDialog.Builder(MainActivity.this)
			.setTitle(android.R.string.dialog_alert_title)
			.setMessage(
				getString(R.string.start_monitoring)
					+ mApplicationInfos.get(position).loadLabel(
						getPackageManager()))
			.setPositiveButton(android.R.string.ok, new OnClickListener() {
			    public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(MainActivity.this,
					FloatWindowService.class);
				intent.putExtra("pkgName",
					mApplicationInfos.get(position).packageName);
				startService(intent);
			    }
			}).setNegativeButton(android.R.string.cancel, null).show();
	    }
	});

    }

    
    
    
    public List<ApplicationInfo> queryAllApps(Context context) {

	PackageManager pm = context.getPackageManager();
	// 查询所有已经安装的应用程序
	return pm.getInstalledApplications(PackageManager.GET_INSTRUMENTATION);
    }

}
