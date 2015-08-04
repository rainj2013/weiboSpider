package io.coding.rainj2013.spider;

import io.coding.rainj2013.Appcontext;
import io.coding.rainj2013.bean.Comment;
import io.coding.rainj2013.bean.Weibo;
import io.coding.rainj2013.util.DateUtil;
import io.coding.rainj2013.util.HttpUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.nutz.dao.impl.NutDao;
import org.nutz.lang.Strings;

public class Spider {
	private static String chartset = "utf-8";
	private String cookie = "SUB=_2A254umhsDeTxGedH4lMT8SnLyzuIHXVYRQgkrDV6PUJbrdAKLU_WkW2PMoeAAq56cjk7pIX8BcXE5KGjFw..; gsid_CTandWM=4uYi2adb13bYaGzeXTEiQ8m0pbp;";
	Calendar calendar = Calendar.getInstance();
	private static NutDao dao = Appcontext.getDao();

	public void captureWeibo(int page) {
		HttpUtil.getHeader().set("cookie", cookie);

		try {

			String html = HttpUtil.get("weibo.cn/pub/topmblog?page=" + page
					+ "&vt=4&PHPSESSID=", chartset);
			Document doc = Jsoup.parse(html);
			Iterator<Element> iterator = doc.select(".c").iterator();

			String nick;
			String context;
			int attitude;
			int repost;
			int comment;
			Date date;
			String from;
			String key;

			int index = 1;
			while (iterator.hasNext() && index < 11) {
				index++;
				Element weiboElement = iterator.next();

				nick = weiboElement.select(".nk").text();

				context = weiboElement.select(".ctt").text();
				context = context.substring(1);

				attitude = Integer.parseInt(weiboElement
						.select("[href~=attitude]").text().split("\\[")[1]
						.split("]")[0]);

				repost = Integer.parseInt(weiboElement.select("[href~=repost]")
						.text().split("\\[")[1].split("]")[0]);

				comment = Integer.parseInt(weiboElement
						.select("[href~=comment]").text().split("\\[")[1]
						.split("]")[0]);

				String temp = weiboElement.select(".ct").text();
				from = "未知来源";
				if (temp.split("来自").length > 1)
					from = temp.split("来自")[1];

				if (temp.split("来自")[0].contains("今天")
						|| temp.split("来自")[0].contains("刚刚")
						|| temp.split("来自")[0].contains("分钟前")) {
					date = new Date();
				} else {
					String time = temp.split("来自")[0].replace("月", "-")
							.replace("日", "");
					time = calendar.get(1) + "-" + time;
					date = DateUtil.parse(time);
				}

				key = weiboElement.attr("id").split("_")[1];

				Weibo weibo = new Weibo(key, nick, context, attitude, repost,
						comment, date, from);

				try {
					dao.insert(weibo);
					System.out.println("插入 :" + weibo);
				} catch (Exception e) {
					dao.update(weibo);
					System.out.println("更新 :" + weibo);
				} finally {
					System.out.println("抓取评论...");
					if (weibo.getComment() > 0) {
						captureComment(weibo.get_key());
					}
				}

				// System.out.println(weibo);
				/*
				 * System.out.println("key:" + key + " " + nick + ":" + context
				 * + " 点赞:[" + attitude + "] " + "转发:[" + repost + "] 评论:[" +
				 * comment + "] " + date + " 来自" + from);
				 */
			}

		} catch (Exception e) {
			System.out.println("连接超时");
		}

	}

	public void captureComment(String weibo_key) {
		String url = "weibo.cn/comment/" + weibo_key;
		HttpUtil.getHeader().set("cookie", cookie);
		try {

			String html = HttpUtil.get(url, chartset);
			Document doc = Jsoup.parse(html);
			int pages = Integer.parseInt(doc.select("[name=mp]").attr("value"));

			String nick;
			String context;
			int attitude;
			Date date;
			String from;
			String key;

			for (int page = pages; page >0; page--) {
				Thread.sleep(1500);
				url = "weibo.cn/comment/" + weibo_key + "?page=" + page;
				html = HttpUtil.get(url, chartset);
				doc = Jsoup.parse(html);
				Elements comments = doc.select(".c");

				for (int i = 0; i < comments.size(); i++) {
					Element commentElement = comments.get(i);

					if (Strings.isEmpty(commentElement.select(
							"[href~=attitude]").text())) {
						continue;
					}

					nick = commentElement.select("a").text().split(" ")[0];
					context = commentElement.select(".ctt").text().substring(1);
					attitude = Integer.parseInt(commentElement
							.select("[href~=attitude]").text().split("\\[")[1]
							.split("]")[0]);
					// 昵称、内容、点赞

					String temp = commentElement.select(".ct").text();
					from = "未知来源";
					if (temp.split("来自").length > 1)
						from = temp.split("来自")[1];

					if (temp.split("来自")[0].contains("今天")
							|| temp.split("来自")[0].contains("刚刚")
							|| temp.split("来自")[0].contains("分钟前")) {
						date = new Date();
					} else {
						String time = temp.split("来自")[0].replace("月", "-")
								.replace("日", "");
						time = calendar.get(1) + "-" + time;
						date = DateUtil.parse(time);
					}
					// 来源和时间

					key = commentElement.attr("id").split("_")[1];

					Comment comment = new Comment(weibo_key, key, nick,
							context, attitude, date, from);
					try {
						dao.insert(comment);
						System.out.println("插入 :" + comment);
					} catch (Exception e) {
						System.out.println("已经存在");
						continue;
					}
				}
			}

		} catch (Exception e) {
			System.out.println("连接超时");
		}
	}

/*	public static void main(String[] args) {
		Spider spider = new Spider();
		for (int i = 1; i <= 30; i++) {
			spider.captureWeibo(i);
		}
	}*/
	
	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
}
