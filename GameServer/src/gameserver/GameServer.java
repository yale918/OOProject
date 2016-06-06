
package gameserver;
import java.io.*;
import java.net.*;
import java.util.*;


public class GameServer {
    
    
    
    
    public static void main(String[] args) throws Exception{
        int port = 8881;
        ServerSocket server = new ServerSocket(port);   System.out.println("server run on: "+port);
        while(true){
            Socket socket = server.accept();
            System.out.println("new connection from"+socket.getRemoteSocketAddress());
            new Thread(new Task(socket) {}).start();
        }
    }
    
    static class Task implements Runnable{
        public Socket socket;
        public BufferedReader input;
        public DataOutputStream output;
        public String clientMessage;
        
        public Task(Socket socket){
            this.socket = socket;
        }
        public void run(){
            //System.out.println("in run");
            try{
                socketHandler();
            }
            catch(IOException IOE){
                System.out.println(IOE);
            }

        }
        private void socketHandler() throws IOException{
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new DataOutputStream(socket.getOutputStream());
            //System.out.println("in socketHandler");
            welcomeMessage();
            while(true){
                clientMessage = input.readLine();
                if(!clientMessage.equals("ov")){
                    System.out.println("["+socket.getRemoteSocketAddress()+"]: "+ clientMessage);
                    output.writeBytes(clientMessage);
                }else{
                    System.out.println("["+socket.getRemoteSocketAddress()+"] 已斷線" );
                    break;
                }
            }
        }
        
        public void welcomeMessage() throws IOException{
            output.writeBytes("welcome to GameServer\n");
        }
    }
    
}
