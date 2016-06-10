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
    //public BufferedReader input;
    public DataInputStream input;
    public String ip = "127.0.0.1";
    public int port = 8883;
    public String serverMessage;
    public String outMessage;


    TcpGameClient() throws Exception{
        socket = new Socket(ip,port);
        input = new DataInputStream(socket.getInputStream());
        //input = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
        output = new DataOutputStream(socket.getOutputStream());
        serverMessage = input.readUTF();
        System.out.println(serverMessage);
        
    }

    public String readLine(){
        return new Scanner(System.in).nextLine();
    }

    public static void main(String args[]) throws Exception{
        new TcpGameClient();

    }

}