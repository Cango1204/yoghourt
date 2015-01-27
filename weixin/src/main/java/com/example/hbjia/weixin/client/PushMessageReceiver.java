package com.example.hbjia.weixin.client;

import java.util.ArrayList;
import java.util.List;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.frontia.api.FrontiaPushMessageReceiver;
import com.example.hbjia.weixin.MainActivity;
import com.example.hbjia.weixin.PushApplication;
import com.example.hbjia.weixin.R;
import com.example.hbjia.weixin.bean.ChatMessage;
import com.example.hbjia.weixin.bean.Message;
import com.example.hbjia.weixin.bean.User;
import com.example.hbjia.weixin.dao.UserDB;
import com.example.hbjia.weixin.utils.NetUtil;
import com.example.hbjia.weixin.utils.PreUtils;
import com.example.hbjia.weixin.utils.SendMsgAsyncTask;
import com.example.hbjia.weixin.utils.SharePreferenceUtil;
import com.example.hbjia.weixin.utils.T;
import com.example.hbjia.weixin.utils.TimeUtil;

public class PushMessageReceiver extends FrontiaPushMessageReceiver
{
	public static final int NOTIFY_ID = 0x000;

	public static int mNewNum = 0;// ֪ͨ������Ϣ��Ŀ����ֻ������һ��ȫ�ֱ�����

	public static final String TAG = PushMessageReceiver.class.getSimpleName();

	public static ArrayList<onNewMessageListener> msgListeners = new ArrayList<onNewMessageListener>();
	public static ArrayList<onNewFriendListener> friendListeners = new ArrayList<onNewFriendListener>();
	public static ArrayList<onNetChangeListener> netListeners = new ArrayList<onNetChangeListener>();
	public static ArrayList<onBindListener> bindListeners = new ArrayList<onBindListener>();

	public static interface onNewMessageListener
	{
		public abstract void onNewMessage(Message message);
	}

	public static interface onNewFriendListener
	{
		public abstract void onNewFriend(User u);
	}

	public static interface onNetChangeListener
	{
		public abstract void onNetChange(boolean isNetConnected);
	}

	public static interface onBindListener
	{
		public abstract void onBind(String userId, int errorCode);
	}

	@Override
	public void onBind(final Context context, int errorCode, String appid,
			String userId, String channelId, String requestId)
	{
		String responseString = "onBind errorCode=" + errorCode + " appid="
				+ appid + " userId=" + userId + " channelId=" + channelId
				+ " requestId=" + requestId;
		Log.e(TAG, responseString);

		if (errorCode == 0)
		{
			SharePreferenceUtil util = PushApplication.getInstance()
					.getSpUtil();
			util.setAppId(appid);
			util.setChannelId(channelId);
			util.setUserId(userId);
			util.setTag("��Ů");
		} else
		// ���������������
		{
			if (NetUtil.isNetConnected(context))
			{

				T.showLong(context, "����ʧ�ܣ���������...");
				new Handler().postDelayed(new Runnable()
				{
					@Override
					public void run()
					{
						PushManager.startWork(context,
								PushConstants.LOGIN_TYPE_API_KEY,
								PushApplication.API_KEY);
					}
				}, 2000);// ��������¿�ʼ��֤
			} else
			{
				T.showLong(context, R.string.net_error_tip);
			}
		}
		// �ص�����
		for (int i = 0; i < bindListeners.size(); i++)
			bindListeners.get(i).onBind(userId, errorCode);
	}

	private void showNotify(Message message)
	{
		mNewNum++;
		// ����֪ͨ��
		PushApplication application = PushApplication.getInstance();

		int icon = R.drawable.copyright;
		CharSequence tickerText = message.getNickname() + ":"
				+ message.getMessage();
		long when = System.currentTimeMillis();
		Notification notification = new Notification(icon, tickerText, when);
		notification.flags = Notification.FLAG_NO_CLEAR;
		// ����Ĭ������
		// notification.defaults |= Notification.DEFAULT_SOUND;
		// �趨��(���VIBRATEȨ��)
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notification.contentView = null;

		Intent intent = new Intent(application, MainActivity.class);
		// �����֪ͨʱ��������ԭ�е�Activity��٣�����ʵ��һ��
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent contentIntent = PendingIntent.getActivity(application, 0,
				intent, 0);
		notification.setLatestEventInfo(PushApplication.getInstance(),
				application.getSpUtil().getNick() + " (" + mNewNum + "������Ϣ)",
				tickerText, contentIntent);
		application.getNotificationManager().notify(NOTIFY_ID, notification);// ֪ͨһ�²Ż���ЧŶ
	}

	@Override
	public void onMessage(Context context, String message,
			String customContentString)
	{
		String messageString = "�յ���Ϣ message=\"" + message
				+ "\" customContentString=" + customContentString;
		Log.e(TAG, messageString);
		Message receivedMsg = PushApplication.getInstance().getGson()
				.fromJson(message, Message.class);
		// �Խ��յ�����Ϣ���д���
		parseMessage(receivedMsg);

	}

	/**
	 * ��Ϣ��1��Я��hello����ʾ���˼��룬Ӧ���Զ��ظ�һ��world ��Ϣ�� 2����ͨ��Ϣ��
	 * 
	 * @param msg
	 */
	private void parseMessage(Message msg)
	{
		String userId = msg.getUserId();
		// �Լ�����Ϣ
		if (userId
				.equals(PushApplication.getInstance().getSpUtil().getUserId()))
			return;
		if (checkHasNewFriend(msg) || checkAutoResponse(msg))
			return;
		// ��ͨ��Ϣ
		UserDB userDB = PushApplication.getInstance().getUserDB();
		User user = userDB.selectInfo(userId);
		// ©��֮��
		if (user == null)
		{
			user = new User(userId, msg.getChannelId(), msg.getNickname(), 0, 0);
			userDB.addUser(user);
			// ֪ͨ��������
			for (onNewFriendListener listener : friendListeners)
				listener.onNewFriend(user);
		}
		if (msgListeners.size() > 0)
		{// �м����ʱ�򣬴�����ȥ
			for (int i = 0; i < msgListeners.size(); i++)
				msgListeners.get(i).onNewMessage(msg);
		} else
		// ��ǰû���κμ�������̨״̬
		{
			// ����������Ϣ���д洢
			ChatMessage chatMessage = new ChatMessage(msg.getMessage(), true,
					userId, msg.getHeadIcon(), msg.getNickname(), false,
					TimeUtil.getTime(msg.getTimeSamp()));
			PushApplication.getInstance().getMessageDB()
					.add(userId, chatMessage);
			showNotify(msg);
		}
	}

	/**
	 * ����Ƿ����Զ��ظ�
	 * 
	 * @param msg
	 */
	private boolean checkAutoResponse(Message msg)
	{
		String world = msg.getWorld();
		String userId = msg.getUserId();
		if (!TextUtils.isEmpty(world))
		{
			User u = new User(userId, msg.getChannelId(), msg.getNickname(),
					msg.getHeadIcon(), 0);
			PushApplication.getInstance().getUserDB().addUser(u);// �������º���
			// ֪ͨ��������
			for (onNewFriendListener listener : friendListeners)
				listener.onNewFriend(u);

			return true;
		}
		return false;
	}

	/**
	 * ����Ƿ������˼���
	 * 
	 * @param msg
	 */
	private boolean checkHasNewFriend(Message msg)
	{
		String userId = msg.getUserId();
		String hello = msg.getHello();
		// ���˷��͵���Ϣ
		if (!TextUtils.isEmpty(hello))
		{
			Log.e("checkHasNewFriend", msg.getUserId());

			// ����
			User u = new User(userId, msg.getChannelId(), msg.getNickname(),
					msg.getHeadIcon(), 0);
			PushApplication.getInstance().getUserDB().addUser(u);// �������º���
			T.showShort(PushApplication.getInstance(), u.getNick() + "����");

			// �����˻ظ�һ��Ӧ��
			Message message = new Message(System.currentTimeMillis(), "");
			message.setWorld("world");
			new SendMsgAsyncTask(PushApplication.getInstance().getGson()
					.toJson(message), userId);
			// ֪ͨ��������
			for (onNewFriendListener listener : friendListeners)
				listener.onNewFriend(u);

			return true;
		}

		return false;
	}

	@Override
	public void onNotificationClicked(Context context, String title,
			String description, String customContentString)
	{

		String notifyString = "֪ͨ��� title=\"" + title + "\" description=\""
				+ description + "\" customContent=" + customContentString;
		Log.e(TAG, notifyString);

	}

	@Override
	public void onSetTags(Context context, int errorCode,
			List<String> sucessTags, List<String> failTags, String requestId)
	{
		String responseString = "onSetTags errorCode=" + errorCode
				+ " sucessTags=" + sucessTags + " failTags=" + failTags
				+ " requestId=" + requestId;
		Log.e(TAG, responseString);

	}

	@Override
	public void onDelTags(Context context, int errorCode,
			List<String> sucessTags, List<String> failTags, String requestId)
	{
		String responseString = "onDelTags errorCode=" + errorCode
				+ " sucessTags=" + sucessTags + " failTags=" + failTags
				+ " requestId=" + requestId;
		Log.e(TAG, responseString);

	}

	@Override
	public void onListTags(Context context, int errorCode, List<String> tags,
			String requestId)
	{
		String responseString = "onListTags errorCode=" + errorCode + " tags="
				+ tags;
		Log.e(TAG, responseString);

	}

	@Override
	public void onUnbind(Context context, int errorCode, String requestId)
	{
		String responseString = "onUnbind errorCode=" + errorCode
				+ " requestId = " + requestId;
		Log.e(TAG, responseString);

		// ��󶨳ɹ�������δ��flag��
		if (errorCode == 0)
		{
			PreUtils.unbind(context);
		}
	}

}
