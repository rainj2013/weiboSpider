package io.coding.rainj2013.bean;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table("user")
public class User {
	@Name
	private String userId;
	@Column
	private String nick;
	@Column
	private String gender;
	@Column
	private String city;
	@Column
	private Date birthday;
	@Column
	private String sexori;
	@Column
	private String single;
	@Column
	private String school;
	@Column
	private String company;
	@Column
	private String soureId;
	@Column
	private int depth;
	@Column
	private String vInfo;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getSexori() {
		return sexori;
	}
	public void setSexori(String sexori) {
		this.sexori = sexori;
	}
	public String getSingle() {
		return single;
	}
	public void setSingle(String single) {
		this.single = single;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getSoureId() {
		return soureId;
	}
	public void setSoureId(String soureId) {
		this.soureId = soureId;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public String getvInfo() {
		return vInfo;
	}
	public void setvInfo(String vInfo) {
		this.vInfo = vInfo;
	}
	public User(String userId, String nick, String gender, String city,
			Date birthday, String sexori, String single, String school,
			String company,String vInfo) {
		super();
		this.userId = userId;
		this.nick = nick;
		this.gender = gender;
		this.city = city;
		this.birthday = birthday;
		this.sexori = sexori;
		this.single = single;
		this.school = school;
		this.company = company;
		this.vInfo = vInfo;
	}
	public User(){}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", nick=" + nick + ", gender="
				+ gender + ", city=" + city + ", birthday=" + birthday
				+ ", sexori=" + sexori + ", single=" + single + ", school="
				+ school + ", company=" + company + ", soureId=" + soureId
				+ ", depth=" + depth + ", vInfo=" + vInfo + "]";
	}
}
