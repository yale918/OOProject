package ffinal;
import java.io.*;

public class oxox
{
    public static void main(String [] args)
    {
        String [] mo = {"���Y","�ŤM","��"};
        String [] head = {"��","��","��","��"};
        int playerNum = 0,cpuNum  = 0;
        boolean cpuWin = false;
       
        do
        {
            System.out.println("�C���}�l...\n");
           
            do{
                printArray(mo);
                System.out.print("�ХX���G");               
                try
                {
                    playerNum = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
                }
                catch(NumberFormatException nfe)
                {
                    System.out.println("�ҥ~�G"+nfe);
                }
                catch(IOException e)
                {
                    System.out.println("�ҥ~�G"+e);
                }
                           
                cpuNum = rand(3);
                System.out.println("���a�X "+mo[playerNum]+",  �q���X "+mo[cpuNum]+"\n");
                if((cpuNum == 0) && (playerNum == 1) || (cpuNum == 1) && (playerNum == 2) || (cpuNum == 2) && (playerNum == 0))
                {
                    cpuWin = true;
                }else if(cpuNum == playerNum) System.out.println("�ӭt----����...,�Э��s�q��...\n");
                else cpuWin = false;
            }while(cpuNum == playerNum);
            if(cpuWin)
            {
                System.out.println("�ӭt----�q���qĹ�F�I\n");
                printArray(head);
                System.out.print("�п�ܭn�{������V�G");
                try
                {
                    playerNum = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
                }
                catch(NumberFormatException nfe)
                {
                    System.out.println("�ҥ~�G"+nfe);
                }
                catch(IOException e)
                {
                    System.out.println("�ҥ~�G"+e);
                }
                cpuNum = rand(4);
                   
                System.out.println("���a��"+head[playerNum]+"�{, �q����"+head[cpuNum]+"��\n");
                if(playerNum == cpuNum){
                    System.out.println("�ӭt----���a��F...");
                }else System.out.println("�ӭt----���a�{�L�F...");
            }
            else
            {
                System.out.println("�ӭt----���a�qĹ�F�I\n");
                printArray(head);
                System.out.print("�п�ܭn������V�G");
                try
                {
                    playerNum = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
                }
                catch(NumberFormatException nfe)
                {
                    System.out.println("�ҥ~�G"+nfe);
                }
                catch(IOException e)
                {
                    System.out.println("�ҥ~�G"+e);
                }
                cpuNum = rand(4);
                   
                System.out.println("���a��"+head[playerNum]+"��, �q����"+head[cpuNum]+"�{\n");
                if(playerNum == cpuNum){
                    System.out.println("�ӭt----���aĹ�F...");
                }else System.out.println("�ӭt----�Q�q���{�L�F...");
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