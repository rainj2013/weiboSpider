package io.coding.rainj2013.bean;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table("comment")
public class Comment {
	
	@Name
	private String _key;
	@Column
	private String nick;
	@Column
	private String context;
	@Column
	private int attitude;
	@Column
	private Date date;
	@Column
	private String _from;
	@Column
	private String weiboKey;
	
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
	public String getWeiboKey() {
		return weiboKey;
	}
	public void setWeiboKey(String weiboKey) {
		this.weiboKey = weiboKey;
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	public Comment(String weiboKey,String key, String nick, String context, int attitude,
			Date date, String from) {
		super();
		this.weiboKey = weiboKey;
		this._key = key;
		this.nick = nick;
		this.context = context;
		this.attitude = attitude;
		this.date = date;
		this._from = from;
	}
	
	public Comment() {
		super();
	}
	@Override
	public String toString() {
		return "Comment [_key=" + _key + ", nick=" + nick + ", context="
				+ context + ", attitude=" + attitude + ", date=" + date
				+ ", _from=" + _from + ", weiboKey=" + weiboKey + "]";
	}
}
