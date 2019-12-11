package distributed.server;

//import distributed.server.Command_handler
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

class ClientHandler implements Runnable
{
    Socket s;
    public ClientHandler(Socket s)
    {
        this.s = s;
    }
    @Override
    public void run()
    {
        try
        {
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            Command_handler cmd_hndlr = new Command_handler();
            while (true)
            {
                if(cmd_hndlr.logged_in_user_email == null)
                    {
                        dos.writeUTF("please choose [login/register]");
                        dos.flush();                        
                        String client_user_input = dis.readUTF();                        
                        dos.writeUTF("next");
                        dos.flush();
                        String [] data;
                        if("login".equals(client_user_input)){                            
                            dos.writeUTF("please enter your email");
                            dos.flush();
                            String email = dis.readUTF();                            
                            dos.writeUTF("next");
                            dos.flush();                            
                            dos.writeUTF("please enter your password");
                            dos.flush();                            
                            dos.writeUTF("next");
                            dos.flush();
                            String password = dis.readUTF();                            
                            data = new String[]{email,password};                            
                            cmd_hndlr.logged_in_user_email=cmd_hndlr.handel_data("login", data);                            
                        }                        
                        else if("register".equals(client_user_input)){
                            dos.writeUTF("please enter your email");
                            dos.flush();
                            String email = dis.readUTF();
                            dos.writeUTF("next");
                            dos.flush();                            
                            dos.writeUTF("please enter your password");
                            dos.flush();                            
                            String password = dis.readUTF();                            
                            dos.writeUTF("next");
                            dos.flush();                            
                            dos.writeUTF("please re-enter your password");
                            dos.flush();
                            String re_password = dis.readUTF();                            
                            data = new String[]{email,password,re_password};                            
                            String str = cmd_hndlr.handel_data("register", data);                            
                            cmd_hndlr.logged_in_user_email=str;                            
                            dos.writeUTF("next");
                            dos.flush();
                        }
                    }
                else if(cmd_hndlr.logged_in_user_email != null)
                {                    
                    dos.writeUTF("please enter command");
                    dos.flush();                    
                    String command = dis.readUTF();
                    if(command.length()>9&&command.substring(0, 8).equals("download")){
                       System.out.println("download checked");
                       dos.writeUTF("recieve");
                       dos.flush();
                       String []down_dtls = command.split(" ");
                       FileInputStream fr = new FileInputStream(cmd_hndlr.driver_facades.logged_in_user_current_path+down_dtls[1]);
                       byte[]b=new byte[2002];
                       fr.read(b,0,b.length);
                       OutputStream os = s.getOutputStream();
                       os.write(b,0,b.length);
                       os.flush();
                       System.out.println("after download");
                    }
                    else if(command.equals("bye")){
                        dos.writeUTF("bye");
                        dos.flush();
                       break;
                    }
                    
                    else
                    {                        
                        String []data = {command};                        
                        String output = cmd_hndlr.handel_data("command", data);                                              
                        dos.writeUTF(output);
                        dos.flush();                        
                    }                                       
                }                
            }            
            dis.close();
            dos.close();
            s.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
public class Server
{
    public static void main(String[] args)
    {
        try
        {
            ServerSocket sv = new ServerSocket(1234);
            System.out.println("Server Running...");
            while (true)
            {
                Socket s = sv.accept();
                System.out.println("Client Accepted...");                
                ClientHandler ch = new ClientHandler(s);
                Thread t = new Thread(ch);
                t.start();
            }
        } catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

}