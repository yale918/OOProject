
package �׷��K�X;
import java.lang.*;
import javax.swing.*;
import java.util.*;
public class test {
	

	
	 public static void main(String[] arg) 
	  {
	  System.out.println("�׷��K�Xgame start"+"  "+"�q���۰ʡG0000�A�C���������O�G888");
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
	    System.out.println(min+"��"+max);
	     }
	     else if(random_no>a){
	    min=a;
	    System.out.println(min+"��"+max);
	     }
	   else{
	    System.out.println("���ߧA�q��F");
	    System.out.println();
	    break; 
	     }
	   System.out.println("�п�J"+min+"��"+max+"���Ʀr");

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
	  System.out.println("�׷��K�X��"+a);
	  System.out.println("�A�`�@�q�F"+i+"��");
	  conclusion(i);
	  System.exit(0);
	 }

	 public static void extra_1(int a,int min,int max)
	 {
	  if(a<min||a>max){
	   System.out.println("���~�I������J1��100�������Ʀr�A�Э��s��J�I");
	   System.out.println();
	   game();
	  }
	  }

	 public static void extra_2(int a,int min,int max)
	  {
	  if(a>max||a<min){
	   System.out.println("�A����J���Ʀr�W�X"+min+"��"+max+"�����A�Э��s��J�I");
	  } 
	  }

	  public static void conclusion(int i)
	  {
	  if(i==1)
	   System.out.println("�ӯ��աI���H�{��");
	  else if(i>1&&i<=3)
	   System.out.println("������I");
	  else if(i>=4&&i<=6)
	     System.out.println("��O�����q�q�ա�");
	  else if(i>=7&&i<=9)
	     System.out.println("�^�a�h�m�m�a�I");
	  else
	     System.out.println("�̲�~��������A�j�I"); 
	  }
	 
	 public static void start()
	  {
	  try
	  { 
	   game();
	  }
	  catch(InputMismatchException e)
	  {
	   System.out.println("���~�I"+e.getMessage()+"�A���O�Ʀr�I�Ʀr�W�X�d��I���i�ťաI");
	   System.out.println("���s�C��...");
	   System.out.println();
	   System.out.println("�׷��K�Xgame start"+"  "+"�C���������O�G888");
	   System.out.println();
	   start();
	  }
	  }
	}

