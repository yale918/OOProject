
package gameplatform.Games;


import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class Memory extends JFrame{
	JPanel contentpane;
	
	public Memory(){//一開始的介面
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(300,100,600,600);
		setTitle("叫我記憶王");
		
		contentpane = new JPanel();
		setContentPane(contentpane);
		contentpane.setLayout(null);
		
		JLabel title = new JLabel("新手作品請多多包涵");
		title.setBounds(225,30,200,100);
		contentpane.add(title);
		
		JButton start = new JButton("開始遊戲");
		start.setBounds(188,100,180,50);
		contentpane.add(start);
		start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int n = JOptionPane.showConfirmDialog(null,"確定準備好了？","問題",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE); 
				if(n==0){
				  setVisible(false);
				  JOptionPane.showConfirmDialog(null,"歡迎光臨!","安安",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE);
				  new newframe();
				}else if(n==1){
				  JOptionPane.showMessageDialog(null, "掰掰<3","其實不難",JOptionPane.QUESTION_MESSAGE);
				  dispose();
				}
			}
		});
		
		JButton backgroundblue = new JButton("背景顏色:藍色");
		backgroundblue.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent b){
				contentpane.setBackground(Color.BLUE);
			}
		});
		backgroundblue.setBounds(188,170,180,30);
		contentpane.add(backgroundblue);
		
		JButton backgroundred = new JButton("背景顏色:紅色");
		backgroundred.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent r){
				contentpane.setBackground(Color.RED);
			}
		});
		backgroundred.setBounds(188,200,180,30);
		contentpane.add(backgroundred);
		
		JButton backgroundyellow = new JButton("背景顏色:黃色");
		backgroundyellow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent y){
				contentpane.setBackground(Color.YELLOW);
			}
		});
		backgroundyellow.setBounds(188,230,180,30);
		contentpane.add(backgroundyellow);
		
		JButton backgroundwhite = new JButton("背景顏色:白色");
		backgroundwhite.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent w){
				contentpane.setBackground(Color.WHITE);
			}
		});
		backgroundwhite.setBounds(188,260,180,30);
		contentpane.add(backgroundwhite);
		
		JButton background = new JButton("不要背景顏色");
		background.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent w){
				contentpane.setBackground(null);
			}
		});
		background.setBounds(188,290,180,30);
		contentpane.add(background);
		
		JButton end = new JButton("離開遊戲");
		end.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent end){
				JOptionPane.showMessageDialog(null, "再見~有空再來玩唷~~", "掰掰",JOptionPane.INFORMATION_MESSAGE);
				dispose();	
			}
		});
		end.setBounds(188,350,180,50);
		contentpane.add(end);
		
		setVisible(true);
	}
}

class newframe extends JFrame{
	JPanel pane;
	ImageIcon title = new ImageIcon("./src/Img/title.png");
	ImageIcon back = new ImageIcon("./src/Img/back.jpg");
	ImageIcon[] images = new ImageIcon[5];
	JButton[] buttons = new JButton[10]; 
	JButton memorydone,memory,first,second;
	JLabel label = new JLabel();
	JLabel timecount;//顯示翻牌幾次的值
	String s,ff;
	int pushnum = 0,sum=0;//計算翻牌次數
	
	public newframe(){//遊戲介面
		int x=0,y=0;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(300,100,600,600);
		setTitle("叫我記憶王");
		pane = new JPanel();
		setContentPane(pane);
		pane.setLayout(null);
		
		Image image = title.getImage();//標題圖
		Image newimage = image.getScaledInstance(300,150,Image.SCALE_SMOOTH);
		ImageIcon newtitle = new ImageIcon(newimage);
		label.setBounds(150,20,300,150);
		label.setIcon(newtitle);
		pane.add(label);
		
		Image image0 = back.getImage();//卡片蓋上的圖
		Image newimage0 = image0.getScaledInstance(90,120,Image.SCALE_SMOOTH);
		final ImageIcon newback = new ImageIcon(newimage0);
		
		memory = new JButton("新局開始");
		memory.setBounds(250,125,100,25);
		pane.add(memory);
		
		timecount = new JLabel();//記錄翻牌次數
		timecount.setBounds(55,100,200,100);
		pane.add(timecount);
		
		for(int i=0;i<images.length;i++){//存遊戲要配對的圖片
			images[i]=new ImageIcon("./src/Img/"+(String.valueOf(i))+".jpg");
			images[i].setImage(images[i].getImage().getScaledInstance(90,120,Image.SCALE_DEFAULT));
		}
		
		memory.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				sum=0;//總翻牌次數歸零
				timecount.setText("總翻牌次數:"+String.valueOf(sum));
				int[] photonumber = new int[]{1,1,2,2,3,3,4,4,5,5};//建立有數字內容的陣列為每個圖的編號
				int n=0;
				int lastest=photonumber.length-1;//最後一個數的位置
				int[] take = new int[10];//為了想每次圖片位置不相同所以用空陣列來隨機放前面的數字
				for(int i=0;i<photonumber.length;i++){//一個一個跑
					n =(int)(Math.random()*(lastest));//0~8取隨機亂數
					take[i] = photonumber[n];//將隨機抽出的圖片代表號依序一個一個放入空陣列中
					photonumber[n] = photonumber[lastest];//放入的刪掉後讓最後面的補進去
					lastest--;//長度減一
					buttons[i].setActionCommand(String.valueOf(take[i]));//設定按鈕的指令跟圖代表號一樣
					buttons[i].setToolTipText(String.valueOf(i));//按鈕代表編號
					buttons[i].setIcon(newback);//新局開始按鈕被按後卡片按鈕先顯示的圖
					buttons[i].setEnabled(true);//卡片按鈕有效
				}

				
			}
		});

		for(int i=0;i<buttons.length;i++){//一個一個跑

			buttons[i] = new JButton();//按鈕排版
			buttons[i].setBounds(55+x*100,180+y*130,90,120);
			buttons[i].setIcon(newback);
			buttons[i].setEnabled(false);//新局開始按鈕未被按前卡片按鈕無效
			x++;
			if(i%5==4){
			y++;
			x=0;
			}
			pane.add(buttons[i]);//將按鈕加入
			
			
		buttons[i].addActionListener(new ActionListener(){//設定每個按鈕
			public void actionPerformed(ActionEvent e){
				pushnum++;//要用的按下次數之後用來比較兩張卡片按紐
				sum++;//要顯示的按下次數
				if(pushnum==1){//第一次按的按鈕
				
				ff = e.getActionCommand();//取得按鈕指令藉以得知第一次按下的圖代表號
				first = (JButton)e.getSource();//取得第一個按下的按鈕
				buttons[Integer.parseInt(first.getToolTipText())].setIcon(images[Integer.parseInt(ff)-1]);
				//將按鈕設定顯示圖案
				}else if(pushnum==2){//第二次按的按鈕
				
				s = e.getActionCommand();//取得按鈕指令
				second = (JButton)e.getSource();//取得第二次按下的按鈕
				buttons[Integer.parseInt(second.getToolTipText())].setIcon(images[Integer.parseInt(s)-1]);
				//將按鈕設定顯示圖案
				
				if(ff.equals(s) && first!=second){//判斷兩個按鈕的圖案是否一致
					first.setEnabled(false);//若一致便讓按鈕失效
					second.setEnabled(false);
				}else{
					first.setIcon(newback);//否則就變回原本的卡片背面圖
					second.setIcon(newback);
				}
			  ff="";//歸零
			  s="";
			  pushnum=0;
			}
				timecount.setText("總翻牌次數:"+String.valueOf(sum));//顯示總翻牌次數
		  }
		 });
		}

				
	setVisible(true);
	}
}
