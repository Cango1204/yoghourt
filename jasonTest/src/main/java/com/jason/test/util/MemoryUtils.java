/**
 * @author zhoushengtao
 * @since 2014年5月7日 下午7:02:47
 */

package com.jason.test.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Debug;
import android.os.Debug.MemoryInfo;

import java.util.List;

public class MemoryUtils {

    /**
     * get the application memory by package name
     * 
     * @param context Context
     * @param pkgName Application package name
     * @return the memory size (kb)
     */
    public static int getPackageMemorySize(Context context, String pkgName) {
	ActivityManager activityManager = (ActivityManager) context
		.getSystemService(Context.ACTIVITY_SERVICE);

	int memorySize = 0;

	List<ActivityManager.RunningAppProcessInfo> appProcessList = activityManager
		.getRunningAppProcesses();

	for (int i = 0; i < appProcessList.size(); i++) {
	    RunningAppProcessInfo appProcessInfo = (RunningAppProcessInfo) appProcessList.get(i);
	    // process id
	    int pid = appProcessInfo.pid;
	    // get memory process id
	    int[] memPid = new int[] { pid };

	    // 此MemoryInfo位于android.os.Debug.MemoryInfo包中，用来统计进程的内存信息
	    Debug.MemoryInfo[] memoryInfo = activityManager.getProcessMemoryInfo(memPid);

	    // get procee memory size info
	    int memSize = 0;
	    for (MemoryInfo info : memoryInfo) {
		memSize += info.getTotalPss();
	    }

	    for (String pkg : appProcessInfo.pkgList) {
		if (pkg.equals(pkgName)) {
		    memorySize += memSize;
		}
	    }
	}
	return memorySize;
    }

    /**
     * get the application memory by package name
     * 
     * @param context
     * @param pkgName
     * @return memory size string
     */
    public static String getPackageMemorySizeString(Context context, String pkgName) {
	return StringUtils.kbytes2FileSizeString(getPackageMemorySize(context, pkgName));
    }
}
