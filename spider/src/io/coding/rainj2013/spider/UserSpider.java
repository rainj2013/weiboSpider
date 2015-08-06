package io.coding.rainj2013.spider;

import io.coding.rainj2013.Appcontext;
import io.coding.rainj2013.bean.User;
import io.coding.rainj2013.util.DateUtil;
import io.coding.rainj2013.util.HttpUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.nutz.dao.Cnd;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.pager.Pager;
import org.nutz.http.HttpException;
import org.nutz.lang.Strings;

public class UserSpider {
	private static String chartset = "utf-8";
	private String cookie = "这里填写手机新浪网（weibo.cn）的cookie";
	Calendar calendar = Calendar.getInstance();
	private static NutDao dao = Appcontext.getDao();

	// 抓取本cookie用户专用 = =其实这是一开始拿自己账号资料页面写的方法测试才发现其他人的资料网页结果稍有不同，舍不得删留着吧，虽然并没什么x用
	public User captureself(String userId) {
		String url = "weibo.cn/" + userId + "/info";
		HttpUtil.getHeader().set("cookie", cookie);
		String html = HttpUtil.get(url, chartset);
		Document doc = Jsoup.parse(html);
		Elements elements = doc.select(".c");

		String nick = null;
		String gender = null;
		String city = null;
		Date birthday = null;
		String sexori = null;
		String single = null;
		String school = "";
		String company = "";
		String vInfo = null;

		// 信息抓取
		for (Element element : elements) {
			// 基本信息
			if (elementContains(element, "[href~=nick]")) {

				// 每个条目
				String[] infos = element.html().split("<br />");

				for (String info : infos) {
					String temp = info.split(":")[1];

					if (info.contains("nick")) {
						nick = temp;
					} else if (info.contains("gender")) {
						gender = temp;
					} else if (info.contains("city")) {
						city = temp;
					} else if (info.contains("birth")) {
						birthday = DateUtil.parse(temp.trim() + " 00:00");
					} else if (info.contains("sexori")) {
						sexori = temp;
					} else if (info.contains("single")) {
						single = temp;
					}

				}// 条目循环结束
			}// 基本信息结束

			// 教育信息
			else if (elementContains(element, "[href~=scho]")) {
				Elements schools = element.select("a");
				for (Element schoolEle : schools) {
					school += schoolEle.text() + " ";
				}
			}

			// 工作信息
			else if (elementContains(element, "[href~=comp]")) {
				Elements schools = element.select("a");
				for (Element schoolEle : schools) {
					company += schoolEle.text() + " ";
				}
			}

			// 其他无关div
			else {
				continue;
			}
		}// 信息抓取结束

		User user = new User(userId, nick, gender, city, birthday, sexori,
				single, school, company, vInfo);
		return user;
	}

	public User captureUser(String userId) {
		String url = "weibo.cn/" + userId + "/info";
		HttpUtil.getHeader().set("cookie", cookie);
		String html = HttpUtil.get(url, chartset);
		Document doc = Jsoup.parse(html);
		Elements elements = doc.select(".c");

		String nick = null;
		String gender = null;
		String vInfo = null;
		String city = null;
		Date birthday = null;
		String sexori = null;
		String single = null;
		String school = "";
		String company = "";

		// 信息抓取
		for (Element element : elements) {
			// 基本信息
			if (element.text().contains("昵称")) {

				// 拆分每个条目
				String[] infos = element.html().split("<br />");

				for (String info : infos) {
					String temp;
					if (info.contains(":"))
						temp = info.split(":")[1];
					else
						temp = info.split("：")[1];

					if (info.contains("昵称:")) {
						nick = temp;
					} else if (info.contains("性别:")) {
						gender = temp;
					} else if (info.contains("地区:")) {
						city = temp;
					} else if (info.contains("生日:")) {
						birthday = DateUtil.parse(temp.trim() + " 00:00");
					} else if (info.contains("性取向:")) {
						sexori = temp;
					} else if (info.contains("感情状况:")) {
						single = temp;
					} else if (info.contains("认证:")) {
						vInfo = temp;
					}

				}// 条目循环结束
			}// 基本信息结束

			// 无太明显标识可以区分教育信息和工作信息，只好暂时这样了
			else if (element.text().contains("·")) {
				// 教育信息
				if (element.text().contains("级")) {
					school = element.text();
					// 工作信息
				} else {
					company = element.text();
				}
			}

			// 其他无关div
			else {
				continue;
			}
		}// 信息抓取结束

		User user = new User(userId, nick, gender, city, birthday, sexori,
				single, school, company, vInfo);
		return user;
	}

	public void captureFollowUsers(User user){
		HttpUtil.getHeader().set("cookie", cookie);
		String html = HttpUtil.get("weibo.cn/" + user.getUserId() + "/follow",
				chartset);
		Document doc = Jsoup.parse(html);
		// 最大页数
		int pages = Integer.parseInt(doc.select("[name=mp]").attr("value"));

		String uid;
		for (int page = 1; page <= pages; page++) {// 关注的人翻页

			html = HttpUtil.get("weibo.cn/" + user.getUserId()
					+ "/follow?page=" + page, chartset);
			doc = Jsoup.parse(html);
			Elements elements = doc.select("[valign~=top]");

			// 循环本页内每个被关注的人
			for (Element element : elements) {

				// 被关注的人信息div
				if (elementContains(element, "[href~=attention]")) {
					uid = element.select("[href~=attention]").attr("href")
							.split("=")[1].split("&")[0];

					User _user = captureUser(uid);
					_user.setSoureId(user.getSoureId());
					_user.setDepth(user.getDepth() + 1);
					
					try{
					dao.insert(_user);
					}catch(Exception e){
						System.out.println("抓取出错");
					}

					// 无关div
				} else {
					continue;
				}

			}// 循环本页结束
		}// 翻页结束
	}

	public void capture(String soureId, int startDepth, int maxDepth)
			throws InstantiationException, IllegalAccessException, HttpException{
		// 如果开始深度为0，先抓取本身
		if (startDepth == 0) {
			User user = captureUser(soureId);
			user.setSoureId(soureId);
			user.setDepth(0);
			try{
				dao.insert(user);
			}catch(Exception e){
				System.out.println("抓取出错");
			}
		}

		// 从开始深度到最大深度循环
		for (int depth = startDepth; depth <= maxDepth; depth++) {

			int count = dao.count(User.class, Cnd
					.where("soureId", "=", soureId).and("depth", "=", depth));
			System.out.println("深度为：" + depth + "的用户共有：" + count + "个");
			Pager pager = Pager.class.newInstance();
			pager.setRecordCount(count);
			pager.setPageSize(100);
			int pages = count / 100 + 1;

			// 该深度的所有用户分页查询，分别抓取该深度所有用户的下一层用户信息
			for (int pn = 1; pn <= pages; pn++) {
				pager.setPageNumber(pn);
				List<User> users = dao.query(User.class,
						Cnd.where("soureId", "=", soureId), pager);
				for (User user : users) {
					captureFollowUsers(user);
					try {
						Thread.sleep(10*1000);
						//休息一下 =。=别被抓了
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}// 本层深度抓取结束

		}// 到最大深度抓取结束
	}

	// 辅助方法，判断某个节点内是否有指定内容；key格式为CSS选择器格式
	private boolean elementContains(Element element, String key) {
		return !Strings.isBlank(element.select(key).text());
	}

	public static void main(String[] args) {
		UserSpider spider = new UserSpider();
		try {
			spider.capture("1930170175", 0, 0);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			System.out.println("连接超时");
		}catch (Exception e) {
			System.out.println("抓取出错");
		}
		

	}
}
