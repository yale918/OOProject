/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameplatform;

import java.io.*;
import java.net.*;
import java.util.*;


public class TcpGameClient{
    public Socket socket;
    public DataOutputStream output;
    public BufferedReader input;
    public String ip = "127.0.0.1";
    public int port = 8881;
    public String serverMessage;
    public String outMessage;


    TcpGameClient() throws Exception{
        socket = new Socket(ip,port);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new DataOutputStream(socket.getOutputStream());
        serverMessage = input.readLine();
        System.out.println(serverMessage);
        /*
        while(true){
            outMessage = readLine();
            if(!outMessage.equals("ov") ){
                output.writeBytes(outMessage+"\n");
                //System.out.println("hello");
            }
            else {
                output.writeBytes(outMessage+"\n");
                break;
            }
        }
        System.out.println("socket terminated");
        input.close();
        output.close();
        socket.close();
        */
    }

    public String readLine(){
        return new Scanner(System.in).nextLine();
    }

    public static void main(String args[]) throws Exception{
        new TcpGameClient();

    }

}