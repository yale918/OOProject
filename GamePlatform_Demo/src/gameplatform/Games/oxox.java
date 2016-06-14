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
public class oxox
{
    public boolean flag = false;
    public boolean gameFlag = true;
    public int playerNum =0;
    public oxox(JTextField JTFInput,JTextArea JTAOutput,JButton JBEnd)
    {

        String [] mo = {"石頭","剪刀","布"};
        String [] head = {"←","↑","↓","→"};
        int cpuNum  = 0;
        boolean cpuWin = false;
       
        
        
        JTFInput.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent AE){
                //System.out.println("in action");
                playerNum = Integer.valueOf(JTFInput.getText());
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
        
        
        
        do
        {
            JTAOutput.append("遊戲開始...\n");
           
            do{
                printArray(mo,JTAOutput);
                JTAOutput.append("請出拳：");               
                try
                {
                    while(true){
                        System.out.println("flag = "+flag);
                        if(flag==true)   break;
                    }
                    if(gameFlag == false){
                        JTAOutput.append("\n離開遊戲\n");
                        break;
                    }
                    flag = false;
                    
                    //playerNum = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
                }
                catch(NumberFormatException nfe)
                {
                    System.out.println("例外："+nfe);
                }
                
                           
                cpuNum = rand(3);
                JTAOutput.append("玩家出 "+mo[playerNum]+",  電腦出 "+mo[cpuNum]+"\n");
                if((cpuNum == 0) && (playerNum == 1) || (cpuNum == 1) && (playerNum == 2) || (cpuNum == 2) && (playerNum == 0))
                {
                    cpuWin = true;
                }else if(cpuNum == playerNum) JTAOutput.append("勝負----平手...,請重新猜拳...\n");
                else cpuWin = false;
            }while(cpuNum == playerNum);
            if(cpuWin)
            {
                JTAOutput.append("勝負----電腦猜贏了！\n");
                printArray(head,JTAOutput);
               JTAOutput.append("請選擇要閃躲的方向：");
                try
                {
                    while(true){
                        System.out.println("flag = "+flag);
                        if(flag==true)   break;
                    }
                    if(gameFlag == false){
                        JTAOutput.append("\n離開遊戲\n");
                        break;
                    }
                    flag = false;
                }
                catch(NumberFormatException nfe)
                {
                    System.out.println("例外："+nfe);
                }
                
                cpuNum = rand(4);
                   
                System.out.println("玩家往"+head[playerNum]+"閃, 電腦往"+head[cpuNum]+"指\n");
                if(playerNum == cpuNum){
                    JTAOutput.append("勝負----玩家輸了...");
                }else JTAOutput.append("勝負----玩家閃過了...");
            }
            else
            {
                JTAOutput.append("勝負----玩家猜贏了！\n");
                printArray(head, JTAOutput);
                JTAOutput.append("請選擇要指的方向：");
                try
                {
                    while(true){
                        System.out.println("flag = "+flag);
                        if(flag==true)   break;
                    }
                    if(gameFlag == false){
                        JTAOutput.append("\n離開遊戲\n");
                        break;
                    }
                    flag = false;
                }
                catch(NumberFormatException nfe)
                {
                    JTAOutput.append("例外："+nfe);
                }
                
                cpuNum = rand(4);
                   
                JTAOutput.append("玩家往"+head[playerNum]+"指, 電腦往"+head[cpuNum]+"閃\n");
                if(playerNum == cpuNum){
                    JTAOutput.append("勝負----玩家贏了...");
                }else JTAOutput.append("勝負----被電腦閃過了...");
            }
            JTAOutput.append("\n");           
        }while(true);
           
           
    }
    private static void printArray(String [] str, JTextArea JTAOutput)
    {
        for( int i = 0; i < str.length; i++)
        {
            JTAOutput.append(i+"."+str[i]+"  ");
        }
    }
    private static int rand(int n)
    {
        return (int)(Math.random()*n);
    }       
}

