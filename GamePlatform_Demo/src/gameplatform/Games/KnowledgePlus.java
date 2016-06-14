/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameplatform.Games;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class KnowledgePlus {
    public String answer;
    public boolean flag = false;
    public boolean gameFlag = true;
    public KnowledgePlus(JTextField JTFInput,JTextArea JTAOutput,JButton JBEnd ) throws IOException {
        JTAOutput.append("比大小遊戲：(6次自動結束)");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        JTFInput.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent AE){
                //System.out.println("in action");
                answer = JTFInput.getText();
                JTFInput.setText("");
                flag = true;
                //System.out.println("flag = "+flag);
            }
        });
        JBEnd.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent AE){
                flag = true;
                gameFlag = false;
            }
        });
        
        
        
        String string = "";
        int point = 0;
        int times = 0;
        do {
            int random = (int) (Math.random() * 50 + 1);
            int comparison = (int) (Math.random() * 50 + 1);
            JTAOutput.append("\n猜比"+random+"大還是小？大輸入0，小輸入1：");

            while(true){
                System.out.println("flag = "+flag);
                if(flag==true)   break;
            }
            if(gameFlag == false){
                JTAOutput.append("\n成績結算\n");
                break;
            }
            flag = false;
            //System.out.println("hey");
            //
            if (comparison == random) {
                JTAOutput.append("結果是一樣，再來一次！");
            } else if (comparison < random && answer.equals("1")) {
                JTAOutput.append("您猜比"+random+"小，結果是..."+comparison+"，恭喜你答對囉！" );
                JTAOutput.append("獲得五分");
                point += 5;
                times++;
            } else if (comparison < random && answer.equals("0")) {
                JTAOutput.append("您猜比"+random+"小，結果是..."+comparison+"，噢答錯囉！");
                JTAOutput.append("Sorry~~扣三分");
                point = point - 3;
                times++;
            } else if (comparison > random && answer.equals("0")) {
                JTAOutput.append("您猜比"+random+"大，結果是..."+comparison+"，恭喜你答對囉！");
                JTAOutput.append("獲得五分");
                point += 5;
                times++;
            } else if (comparison > random && answer.equals("1")) {
                JTAOutput.append("您猜比"+random+"大，結果是..."+comparison+"，噢答錯囉！");
                JTAOutput.append("Sorry~~扣三分");
                point = point - 3;
                times++;
            }
        } while (times < 6);
        JTAOutput.append("*** 你的總分是:" + point+" ***");

    }

}

