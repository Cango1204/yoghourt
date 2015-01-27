package com.example.hbjia.weixin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.android.pushservice.PushManager;
import com.example.hbjia.weixin.bean.ChatMessage;
import com.example.hbjia.weixin.bean.Message;
import com.example.hbjia.weixin.bean.User;
import com.example.hbjia.weixin.client.PushMessageReceiver;
import com.example.hbjia.weixin.utils.SharePreferenceUtil;
import com.example.hbjia.weixin.utils.T;
import com.example.hbjia.weixin.utils.TimeUtil;
import com.jauker.widget.BadgeView;

/**
 * �����б����
 * 
 * @author zhy
 * 
 */
public class MainTabFriends extends Fragment implements PushMessageReceiver.onNewFriendListener,
        PushMessageReceiver.onNewMessageListener
{

	public static final String TAG = MainTabFriends.class.getSimpleName();

	/**
	 * �ṩδ����Ϣ���µĻص�����������һ������Ϣ�����û�����鿴ĳ���û�����Ϣ
	 * 
	 * @author zhy
	 * 
	 */
	public interface OnUnReadMessageUpdateListener
	{
		void unReadMessageUpdate(int count);
	}

	/**
	 * �洢userId-������Ϣ�ĸ���
	 */
	public Map<String, Integer> mUserMessages = new HashMap<String, Integer>();
	/**
	 * δ����Ϣ����
	 */
	private int mUnReadedMsgs;

	private ListView mFrineds;
	private View mEmptyView;
	/**
	 * ���е��û�
	 */
	private List<User> mDatas;
	/**
	 * ������
	 */
	private FriendsListAdapter mAdapter;

	private PushApplication mApplication;

	private LayoutInflater mInflater;
	private SharePreferenceUtil mSpUtils;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Log.e(TAG, "onCreate");


		mInflater = LayoutInflater.from(getActivity());
		mApplication = (PushApplication) this.getActivity().getApplication();
		mAdapter = new FriendsListAdapter();
		mDatas = mApplication.getUserDB().getUser();
		mSpUtils = PushApplication.getInstance().getSpUtil();

		// ��ȡ��ݿ������е��û��Լ�δ����Ϣ����
		mUserMessages = mApplication.getMessageDB().getUserUnReadMsgs(
				mApplication.getUserDB().getUserIds());
		for (Integer val : mUserMessages.values())
		{
			mUnReadedMsgs += val;
		}

	}

	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater
				.inflate(R.layout.main_tab_weixin, container, false);
		mFrineds = (ListView) view.findViewById(R.id.id_listview_friends);
		mEmptyView = inflater.inflate(R.layout.no_zuo_no_die, container, false);
		mFrineds.setEmptyView(mEmptyView);
		mFrineds.setAdapter(mAdapter);

		notifyUnReadedMsg();

		mFrineds.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				String userId = mDatas.get(position).getUserId();
				if (userId.equals(mSpUtils.getUserId()))
				{
					T.showShort(getActivity(), "���ܺ��Լ��������");
					return;
				}

				if (mUserMessages.containsKey(userId))
				{
					Integer val = mUserMessages.get(userId);
					mUnReadedMsgs -= val;
					mUserMessages.remove(userId);
					mAdapter.notifyDataSetChanged();
					notifyUnReadedMsg();

				}

				Intent intent = new Intent(getActivity(),
						ChattingActivity.class);
				intent.putExtra("userId", mDatas.get(position).getUserId());
				startActivity(intent);
			}

		});
		return view;
	}

	/**
	 * �ص�δ����Ϣ����
	 */
	private void notifyUnReadedMsg()
	{
		if (getActivity() instanceof OnUnReadMessageUpdateListener)
		{
			OnUnReadMessageUpdateListener listener = (OnUnReadMessageUpdateListener) getActivity();
			listener.unReadMessageUpdate(mUnReadedMsgs);
		}
	}

	@Override
	public void onResume()
	{
		super.onResume();
		Log.e(TAG, "onResume");
		// �ص�δ����Ϣ����ĸ���
		notifyUnReadedMsg();
		// ���������ѵļ���
		PushMessageReceiver.friendListeners.add(this);
		// ��������Ϣ�ļ���
		PushMessageReceiver.msgListeners.add(this);

		if (!PushManager.isPushEnabled(getActivity()))
			PushManager.resumeWork(getActivity());
		// �����û��б�
		mDatas = mApplication.getUserDB().getUser();
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onPause()
	{
		super.onPause();
		Log.e(TAG, "onPause");
		/**
		 * ��onPauseʱ��ȡ�����
		 */
		PushMessageReceiver.friendListeners.remove(this);
		PushMessageReceiver.msgListeners.remove(this);
	}

	/**
	 * �յ�����Ϣʱ
	 */
	@Override
	public void onNewMessage(Message message)
	{
		// ������Լ����͵ģ���ֱ�ӷ���
		if (message.getUserId() == mSpUtils.getUserId())
			return;
		// �����û��Ѿ���δ����Ϣ������δ����Ϣ�ĸ���֪ͨ����δ����Ϣ�ӿڣ����notifyDataSetChanged
		String userId = message.getUserId();
		if (mUserMessages.containsKey(userId))
		{
			mUserMessages.put(userId, mUserMessages.get(userId) + 1);
		} else
		{
			mUserMessages.put(userId, 1);
		}
		mUnReadedMsgs++;
		notifyUnReadedMsg();
		// ����������Ϣ���д洢
		ChatMessage chatMessage = new ChatMessage(message.getMessage(), true,
				userId, message.getHeadIcon(), message.getNickname(), false,
				TimeUtil.getTime(message.getTimeSamp()));
		mApplication.getMessageDB().add(userId, chatMessage);
		// ֪ͨlistview��ݸı�
		mAdapter.notifyDataSetChanged();
	}

	/**
	 * ���������ѵ�����֪ͨ
	 */
	@Override
	public void onNewFriend(User u)
	{
		Log.e(TAG, "get a new friend :" + u.getUserId() + " , " + u.getNick());
		mDatas.add(u);
		mAdapter.notifyDataSetChanged();
	}

	private class FriendsListAdapter extends BaseAdapter
	{
		@Override
		public int getCount()
		{
			return mDatas.size();
		}

		@Override
		public Object getItem(int position)
		{
			return mDatas.get(position);
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			User user = mDatas.get(position);
			String userId = user.getUserId();

			ViewHolder holder = null;
			if (convertView == null)
			{
				convertView = mInflater.inflate(
						R.layout.main_tab_weixin_info_item, parent, false);
				holder = new ViewHolder();
				holder.mNickname = (TextView) convertView
						.findViewById(R.id.id_nickname);
				holder.mUserId = (TextView) convertView
						.findViewById(R.id.id_userId);
				holder.mWapper = (RelativeLayout) convertView
						.findViewById(R.id.id_item_ly);
				convertView.setTag(holder);
			} else
			{
				holder = (ViewHolder) convertView.getTag();
			}

			// �������µ���Ϣ��������BadgeView
			if (mUserMessages.containsKey(userId))
			{
				if (holder.mBadgeView == null)
					holder.mBadgeView = new BadgeView(mApplication);
				holder.mBadgeView.setTargetView(holder.mWapper);
				holder.mBadgeView.setBadgeGravity(Gravity.CENTER_VERTICAL
						| Gravity.RIGHT);
				holder.mBadgeView.setBadgeMargin(0, 0, 8, 0);
				holder.mBadgeView.setBadgeCount(mUserMessages.get(userId));
			} else
			{
				if (holder.mBadgeView != null)
					holder.mBadgeView.setVisibility(View.GONE);
			}

			holder.mNickname.setText(mDatas.get(position).getNick());
			holder.mUserId.setText(userId);

			return convertView;
		}

		private final class ViewHolder
		{
			TextView mNickname;
			TextView mUserId;
			RelativeLayout mWapper;
			BadgeView mBadgeView;
		}

	}

}
