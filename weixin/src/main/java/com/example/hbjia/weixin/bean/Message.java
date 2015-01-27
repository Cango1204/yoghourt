package com.example.hbjia.weixin.bean;

import java.io.Serializable;

import com.example.hbjia.weixin.PushApplication;
import com.example.hbjia.weixin.utils.SharePreferenceUtil;
import com.google.gson.annotations.Expose;

public class Message implements Serializable
{
	private static final long serialVersionUID = 1L;
	@Expose
	private String userId;
	@Expose
	private String channelId;
	@Expose
	private String nickname;
	@Expose
	private int headIcon;
	@Expose
	private long timeSamp;
	@Expose
	private String message;
	/**
	 * ���˵�һ�μ���ʱ����㲥����ֶΣ���ֵΪhello
	 */
	@Expose
	private String hello;
	/**
	 * ���յ����˵�helloʱ���Զ��ظ����ֶ���ֵΪworld
	 */
	@Expose
	private String world;
	


	public Message(long time_samp, String message)
	{
		super();
		SharePreferenceUtil util = PushApplication.getInstance().getSpUtil();
		this.userId = util.getUserId();
		this.channelId = util.getChannelId();
		this.nickname = util.getNick();
		this.headIcon = util.getHeadIcon();
		this.timeSamp = time_samp;
		this.message = message;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getChannelId()
	{
		return channelId;
	}

	public void setChannelId(String channelId)
	{
		this.channelId = channelId;
	}

	public String getNickname()
	{
		return nickname;
	}

	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}

	public int getHeadIcon()
	{
		return headIcon;
	}

	public void setHeadIcon(int headIcon)
	{
		this.headIcon = headIcon;
	}

	public long getTimeSamp()
	{
		return timeSamp;
	}

	public void setTimeSamp(long timeSamp)
	{
		this.timeSamp = timeSamp;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getHello()
	{
		return hello;
	}

	public void setHello(String hello)
	{
		this.hello = hello;
	}

	public String getWorld()
	{
		return world;
	}

	public void setWorld(String world)
	{
		this.world = world;
	}

}
