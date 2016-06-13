/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameplatform.Games;
import java.io.*;

public class oxox
{
    public oxox()
    {

        String [] mo = {"石頭","剪刀","布"};
        String [] head = {"←","↑","↓","→"};
        int playerNum = 0,cpuNum  = 0;
        boolean cpuWin = false;
       
        do
        {
            System.out.println("遊戲開始...\n");
           
            do{
                printArray(mo);
                System.out.print("請出拳：");               
                try
                {
                    playerNum = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
                }
                catch(NumberFormatException nfe)
                {
                    System.out.println("例外："+nfe);
                }
                catch(IOException e)
                {
                    System.out.println("例外："+e);
                }
                           
                cpuNum = rand(3);
                System.out.println("玩家出 "+mo[playerNum]+",  電腦出 "+mo[cpuNum]+"\n");
                if((cpuNum == 0) && (playerNum == 1) || (cpuNum == 1) && (playerNum == 2) || (cpuNum == 2) && (playerNum == 0))
                {
                    cpuWin = true;
                }else if(cpuNum == playerNum) System.out.println("勝負----平手...,請重新猜拳...\n");
                else cpuWin = false;
            }while(cpuNum == playerNum);
            if(cpuWin)
            {
                System.out.println("勝負----電腦猜贏了！\n");
                printArray(head);
                System.out.print("請選擇要閃躲的方向：");
                try
                {
                    playerNum = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
                }
                catch(NumberFormatException nfe)
                {
                    System.out.println("例外："+nfe);
                }
                catch(IOException e)
                {
                    System.out.println("例外："+e);
                }
                cpuNum = rand(4);
                   
                System.out.println("玩家往"+head[playerNum]+"閃, 電腦往"+head[cpuNum]+"指\n");
                if(playerNum == cpuNum){
                    System.out.println("勝負----玩家輸了...");
                }else System.out.println("勝負----玩家閃過了...");
            }
            else
            {
                System.out.println("勝負----玩家猜贏了！\n");
                printArray(head);
                System.out.print("請選擇要指的方向：");
                try
                {
                    playerNum = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
                }
                catch(NumberFormatException nfe)
                {
                    System.out.println("例外："+nfe);
                }
                catch(IOException e)
                {
                    System.out.println("例外："+e);
                }
                cpuNum = rand(4);
                   
                System.out.println("玩家往"+head[playerNum]+"指, 電腦往"+head[cpuNum]+"閃\n");
                if(playerNum == cpuNum){
                    System.out.println("勝負----玩家贏了...");
                }else System.out.println("勝負----被電腦閃過了...");
            }
            System.out.println('\n');           
        }while(true);
           
           
    }
    private static void printArray(String [] str)
    {
        for( int i = 0; i < str.length; i++)
        {
            System.out.print(i+"."+str[i]+"  ");
        }
    }
    private static int rand(int n)
    {
        return (int)(Math.random()*n);
    }       
}

