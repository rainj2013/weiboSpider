package io.coding.rainj2013.ui;

import io.coding.rainj2013.spider.UserSpider;
import io.coding.rainj2013.spider.WeiboSpider;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.nutz.http.HttpException;
import org.nutz.lang.Strings;

public class MainPanel extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField cookieField;
	private JTextField keyField;
	private WeiboSpider weiboSpider;
	private UserSpider userSpider;
	private JButton btnNewButton;
	private JTextField minDepthField;
	private JTextField maxDepthField;
	private JButton btnCaptureUser;

	/**
	 * Create the panel.
	 */
	public MainPanel() {
		weiboSpider = new WeiboSpider();
		userSpider = new UserSpider();
		setLayout(null);
		setBounds(0, 0, 720, 159);
		
		JLabel lblCookie = new JLabel("cookie:");
		lblCookie.setBounds(14, 13, 72, 18);
		add(lblCookie);
		
		cookieField = new JTextField();
		cookieField.setBounds(79, 10, 418, 24);
		add(cookieField);
		cookieField.setText(weiboSpider.getCookie());
		cookieField.setColumns(10);
		
		JLabel lblWeiboKey = new JLabel("key:");
		lblWeiboKey.setBounds(33, 58, 45, 18);
		add(lblWeiboKey);
		
		keyField = new JTextField();
		keyField.setBounds(79, 55, 418, 24);
		add(keyField);
		keyField.setColumns(10);
		
		btnNewButton = new JButton("capture weibo");
		btnNewButton.addActionListener(this);
		btnNewButton.setBounds(511, 9, 194, 67);
		add(btnNewButton);
		
		btnCaptureUser = new JButton("capture user");
		btnCaptureUser.addActionListener(this);
		btnCaptureUser.setBounds(511, 83, 194, 67);
		add(btnCaptureUser);
		
		JLabel lblStartDepth = new JLabel("min depth");
		lblStartDepth.setBounds(14, 107, 86, 18);
		add(lblStartDepth);
		
		minDepthField = new JTextField();
		minDepthField.setBounds(101, 104, 86, 24);
		add(minDepthField);
		minDepthField.setColumns(10);
		
		JLabel lblMaxDepth = new JLabel("max depth");
		lblMaxDepth.setBounds(311, 107, 86, 18);
		add(lblMaxDepth);
		
		maxDepthField = new JTextField();
		maxDepthField.setColumns(10);
		maxDepthField.setBounds(411, 104, 86, 24);
		add(maxDepthField);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnNewButton){
			weiboSpider.setCookie(cookieField.getText());
			if(Strings.isBlank(keyField.getText())){
				for(int i=30;i>0;i--){
					weiboSpider.captureWeibo(i);
				}
			}else{
				weiboSpider.captureComment(keyField.getText());
			}
		}else if(e.getSource()==btnCaptureUser){
			try {
				userSpider.capture(keyField.getText(), Integer.parseInt(minDepthField.getText()), Integer.parseInt(maxDepthField.getText()));
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (HttpException e1) {
				System.out.println("连接超时");
			}catch (Exception e1) {
				System.out.println("抓取出错");
			}
		}
		
	}
}
