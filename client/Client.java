/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributed.client;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author hady
 */
public class Client {
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        try
        {
            Socket s = new Socket("127.0.0.1", 1234);            
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());           
            while (true) 
            {                          
                String server_msg = dis.readUTF();
                System.out.println(server_msg);                
                String user_input="";                
                user_input+=sc.nextLine();
                if(user_input.length()>8&&user_input.substring(0, 8).equals("download")){
                    String []down_dtls = user_input.split(" ");
                    dos.writeUTF(user_input);                
                    dos.flush();   
                    String msg_form_srvr = dis.readUTF();
                    if(msg_form_srvr.equals("recieve")){                                                
                        byte []b = new byte[2002];
                        InputStream is = s.getInputStream();
                        String file_path ="";
                        for(int i =2;i<down_dtls.length;i++){
                            if(i<down_dtls.length-1){
                                file_path+=down_dtls[i]+" ";
                            }else{
                                file_path+=down_dtls[i];
                            }                            
                        }
                        System.out.println(file_path+"/"+down_dtls[1]);
                        FileOutputStream fr = new FileOutputStream(file_path+"/"+down_dtls[1]);
                        is.read(b,0,b.length);
                        fr.write(b,0,b.length);
                        System.out.println("after download");
                        continue;
                    }
                    else{
                        String result = dis.readUTF();
                        System.out.println(result);
                    }
                    
                    
                }
                dos.writeUTF(user_input);                
                dos.flush();                
                String server_output = dis.readUTF();                
                if(server_output.equals("bye")){
                    break;
                }
                else if(server_output.equals("next"))
                {
                    continue;
                }
                else{
                    System.out.println(server_output);                    
                }                
            }            
            dis.close();
            dos.close();
            s.close();            
        } 
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }  
}
