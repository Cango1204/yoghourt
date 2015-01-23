package com.csdn.hbjia.com.csdn.hbjia.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.text.TextUtils;

/**
 * 
 * @author zhy
 * 
 */
public class AppUtil
{
	/**
	 * ����������ͻ�ȡ�ϴθ��µ�ʱ��
	 * 
	 * @param newType
	 * @return
	 */
	public static String getRefreashTime(Context context, int newType)
	{
		String timeStr = PreferenceUtil.readString(context, "NEWS_" + newType);
		if (TextUtils.isEmpty(timeStr))
		{
			return getCurrentTime();
		}
		return timeStr;
	}
	
	
	/**
	 * ����������������ϴθ��µ�ʱ��
	 * 
	 * @param newType
	 * @return
	 */
	public static void setRefreashTime(Context context, int newType)
	{
		PreferenceUtil.write(context, "NEWS_" + newType, getCurrentTime());
	}

    public static String getCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }
}
