
package 終極密碼;
import java.lang.*;
import javax.swing.*;
import java.util.*;
public class test {
	

	
	 public static void main(String[] arg) 
	  {
	  System.out.println("終極密碼game start"+"  "+"電腦自動：0000，遊戲結束指令：888");
	  System.out.println();
	  start();
	 }

	 public static void game() throws InputMismatchException
	 {
	  int random_no=(int)(Math.random()*100+1);
	  Scanner scanner=new Scanner(System.in);
	  int a=scanner.nextInt(),b=a;

	  if(b==0000){
	   a=(int)(Math.random()*100+1);
	   System.out.println(a);
	  }
	  else if(a==760322){
	   a=random_no;
	  }
	  else if(a==888){
	   System.exit(0);
	  }
	  
	  int i,min=1,max=100;
	  extra_1(a,min,max);

	  for(i=1;i<max;i++)
	  {
	   if(max<a){
	    a=max;
	     }
	     else if(a<min){
	    a=min;
	     }
	     else if(random_no<a){
	    max=a;
	    System.out.println(min+"到"+max);
	     }
	     else if(random_no>a){
	    min=a;
	    System.out.println(min+"到"+max);
	     }
	   else{
	    System.out.println("恭喜你猜對了");
	    System.out.println();
	    break; 
	     }
	   System.out.println("請輸入"+min+"到"+max+"的數字");

	   if(b==0000){
	    a=(int)(Math.random()*(max-min+1)+min);
	    System.out.println(a);
	   }
	   else if(a==760322){
	    a=random_no;
	   }
	   else if(a==888){
	    System.exit(0);
	   }
	   else{
	    a=scanner.nextInt();
	   }
	   extra_2(a,min,max);
	  }
	  System.out.println("終極密碼為"+a);
	  System.out.println("你總共猜了"+i+"次");
	  conclusion(i);
	  System.exit(0);
	 }

	 public static void extra_1(int a,int min,int max)
	 {
	  if(a<min||a>max){
	   System.out.println("錯誤！必須輸入1到100之間的數字，請重新輸入！");
	   System.out.println();
	   game();
	  }
	  }

	 public static void extra_2(int a,int min,int max)
	  {
	  if(a>max||a<min){
	   System.out.println("你的輸入的數字超出"+min+"到"+max+"之間，請重新輸入！");
	  } 
	  }

	  public static void conclusion(int i)
	  {
	  if(i==1)
	   System.out.println("太神啦！神人認證");
	  else if(i>1&&i<=3)
	   System.out.println("不錯喔！");
	  else if(i>=4&&i<=6)
	     System.out.println("實力普普通通啦～");
	  else if(i>=7&&i<=9)
	     System.out.println("回家多練練吧！");
	  else
	     System.out.println("傻眼~閉眼都比你強！"); 
	  }
	 
	 public static void start()
	  {
	  try
	  { 
	   game();
	  }
	  catch(InputMismatchException e)
	  {
	   System.out.println("錯誤！"+e.getMessage()+"，不是數字！數字超出範圍！不可空白！");
	   System.out.println("重新遊戲...");
	   System.out.println();
	   System.out.println("終極密碼game start"+"  "+"遊戲結束指令：888");
	   System.out.println();
	   start();
	  }
	  }
	}

