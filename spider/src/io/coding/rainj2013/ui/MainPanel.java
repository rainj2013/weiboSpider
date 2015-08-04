package io.coding.rainj2013.ui;

import io.coding.rainj2013.spider.Spider;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import org.nutz.lang.Strings;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainPanel extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField cookieField;
	private JTextField keyField;
	private Spider spider;
	private JButton btnNewButton;

	/**
	 * Create the panel.
	 */
	public MainPanel() {
		spider = new Spider();
		setLayout(null);
		setBounds(0, 0, 720, 100);
		
		JLabel lblCookie = new JLabel("cookie:");
		lblCookie.setBounds(14, 13, 72, 18);
		add(lblCookie);
		
		cookieField = new JTextField();
		cookieField.setBounds(79, 10, 499, 24);
		add(cookieField);
		cookieField.setText(spider.getCookie());
		cookieField.setColumns(10);
		
		JLabel lblWeiboKey = new JLabel("key:");
		lblWeiboKey.setBounds(33, 58, 45, 18);
		add(lblWeiboKey);
		
		keyField = new JTextField();
		keyField.setBounds(79, 55, 499, 24);
		add(keyField);
		keyField.setColumns(10);
		
		btnNewButton = new JButton("Start");
		btnNewButton.addActionListener(this);
		btnNewButton.setBounds(592, 9, 113, 67);
		add(btnNewButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnNewButton){
			spider.setCookie(cookieField.getText());
			if(Strings.isBlank(keyField.getText())){
				for(int i=30;i>0;i--){
					spider.captureWeibo(i);
				}
			}else{
				spider.captureComment(keyField.getText());
			}
		}
		
	}
	
}
