package io.coding.rainj2013.bean;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table("weibo")
public class Weibo {
	
	@Name
	private String _key;
	@Column
	private String nick;
	@Column
	private String context;
	@Column
	private int attitude;
	@Column
	private int repost;
	@Column
	private int comment;
	@Column
	private Date date;
	@Column
	private String _from;
	
	
	public Weibo(String key, String nick, String context, int attitude,
			int repost, int comment, Date date, String from) {
		super();
		this._key = key;
		this.nick = nick;
		this.context = context;
		this.attitude = attitude;
		this.repost = repost;
		this.comment = comment;
		this.date = date;
		this._from = from;
	}

	public Weibo() {
		super();
	}

	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public int getAttitude() {
		return attitude;
	}
	public void setAttitude(int attitude) {
		this.attitude = attitude;
	}
	public int getRepost() {
		return repost;
	}
	public void setRepost(int repost) {
		this.repost = repost;
	}
	public int getComment() {
		return comment;
	}
	public void setComment(int comment) {
		this.comment = comment;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public String get_key() {
		return _key;
	}

	public void set_key(String _key) {
		this._key = _key;
	}

	public String get_from() {
		return _from;
	}

	public void set_from(String _from) {
		this._from = _from;
	}

	@Override
	public String toString() {
		return "Weibo [key=" + _key + ", nick=" + nick + ", context=" + context
				+ ", attitude=" + attitude + ", repost=" + repost
				+ ", comment=" + comment + ", date=" + date + ", from=" + _from
				+ "]";
	}
	
}
