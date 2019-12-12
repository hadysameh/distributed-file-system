/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributed.server;

import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author hady
 */
public class Command_handler {
    private User_model user;
    public Driver_facades driver_facades;
    public String logged_in_user_email = null;
    
    Command_handler() throws SQLException{
         user= new User_model();
         driver_facades = new Driver_facades();
         
    }
    
    public String handel_data(String type,String[] data) throws SQLException, IOException{
        if(type.equals("register")){
            return register(data);
        }
        else if(type.equals("login")){
            return login(data);
        }
        else if(type.equals("command")){
            String command_data = data[0];
            System.out.println("command_data "+command_data);
            if(command_data.length()>=2&&command_data.equals("ls")){
//                System.out.println(driver_facades.ls()[0]); 
                
//                System.out.println(String.join(" ", driver_facades.ls()));
                
                if(driver_facades.ls()==null){
                    System.out.println("empty"); 
                }
                
                return String.join(" ", driver_facades.ls());
//                return driver_facades.ls()[0];
            }
            else if(command_data.length()>=2&&command_data.equals("pwd")){
                return driver_facades.pwd();
                
            }
            else if(command_data.length()>=2&&command_data.substring(0, 2).equals("cd"))
            {                
                if(driver_facades.cd(command_data)){
                    return "success";
                }
                
            }
            else if(command_data.length()>5&&command_data.substring(0, 5).equals("rmdir"))
            {
                System.out.println("hi from rmdir");                
                if(driver_facades.rmdir(command_data.substring(6,command_data.length()))){
                    System.out.println("file found from rmdir");
                    return "success";
                }else{
                    return "faild";
                }
            }
            else if(command_data.length()>2&&command_data.substring(0, 2).equals("rm")){
                
                if(driver_facades.rm(command_data.replaceAll("rm ", ""))){
                    return "success";
                }else{
                    System.out.println("test rmdir in rm");
                    return "faild";
                }
            }            
            else if(command_data.length()>2&&command_data.substring(0, 2).equals("mv")){
                System.out.println("hello from move3");
                String[] comand_to_array =  command_data.split(" ");
                                
                if(driver_facades.mv(comand_to_array[2], comand_to_array[1])){
                    return "success";
                }else{
                    System.out.println("test rmdir in mv");
                    return "faild";
                }
            }
            else if(command_data.length()>2&&command_data.substring(0, 2).equals("cp")){
                String[] comand_to_array =  command_data.split(" ");
//                System.out.println("copy from cmd_hndlr");
//                System.out.println(comand_to_array[2]);
                if(driver_facades.cp(comand_to_array[1],comand_to_array[2])){
                    return "success";
                }else{
                    System.out.println("test rmdir in cp");
                    return "faild";
                }
            }
            
            else if(command_data.length()>3&&command_data.substring(0, 3).equals("rnm")){
                
                String[] comand_to_array =  command_data.split(" ");
//                System.out.println(comand_to_array[1]);
                
//                System.out.println(comand_to_array[2]);
                
                if(driver_facades.rnm(comand_to_array[1], comand_to_array[2])){
                    return "success";
                }else{
                    System.out.println("test rmdir in rnm");
                    return "faild";
                }
            }
            else if(command_data.length()>5&&command_data.substring(0, 5).equals("mkdir"))
            {
                
                System.out.println("hello form mkdir");
                System.out.println(command_data.substring(6,command_data.length()));
                if(driver_facades.mkdir(command_data.substring(6,command_data.length()))){
//                    System.out.println("after create dir_name");
                    return "success";
                }
                else{
                    System.out.println("test rmdir in mkdir");
                    return "faild";
                }
            }
                       
            else{
               System.out.println("rmdir faild");  
                return "faild"; 
            }
        }
        else{
                System.out.println("rmdir faild"); 
                return "faild"; 
            }
        System.out.println("rmdir faild"); 
        return "faild"; 
    }
    
    private String register(String[] data) throws SQLException{
//        System.out.println("inside register");
        System.out.println(data[1]);
        System.out.println(data[2]);
        if(data[1].equals(data[2])){
//            System.out.println(data[2]);
            if(!user.check_email(data[0])){
//                System.out.println("email  checked");
                user.email=data[0];
//                user.password=data[1];
                user.password="";
                String str1 = data[1];       
                char chr;
                String result="";
                int a;        
                for(int i =0 ; i<str1.length();i++)
                {
                    chr=str1.charAt(i);
                    a=(int)chr;
                    chr = (char)(a+1);
                    result+=String.valueOf(chr);
                }        
//                System.out.println(result);
                user.password=result;
                
                if(user.save()){
                    
                    if(driver_facades.create_user_roots(data[0]))
                    {
//                        System.out.println("registe successful");
                        driver_facades.logged_in_user_current_path=driver_facades.system_root_path+data[0]+"/home/";
                        driver_facades.logged_in_user_home_path=driver_facades.system_root_path+data[0]+"/home/";
                        logged_in_user_email=data[0];
                        return data[0];
                    }
                    else
                    {
                        return "user roots wasn't created"; 
                    }
                }
                else
                {
                    return "user data isn't saaved!"; 
                }           
            }
            else{
                return "this email already existed"; 
            }
        }
        else
        {
           return "Please re-enter your data carefuly!"; 
        }        
//        return "Please re-enter your data carefuly!";         
    }
    
    private String login(String[] data) throws SQLException{
        if(user.check_email(data[0])){
                String str1 = data[1];       
                char chr;
                String result="";
                int a;        
                for(int i =0 ; i<str1.length();i++)
                {
                    chr=str1.charAt(i);
                    a=(int)chr;
                    chr = (char)(a+1);
                    result+=String.valueOf(chr);
                }     
            if(user.check_password_for_email(data[0], result)){
                
                driver_facades.logged_in_user_current_path=driver_facades.system_root_path+data[0]+"/home/";
                
                driver_facades.logged_in_user_home_path=driver_facades.system_root_path+data[0]+"/home/";
                
                logged_in_user_email=data[0];
                
//                System.out.println(driver_facades.logged_in_user_current_path);
                
                return data[0];
            }
            else{
                return null;
            }
        }
        else{
            return null;
        }                        
    }
    
    public String get_current_path(){
    
        return driver_facades.logged_in_user_current_path;
    }
    
    public static void main(String[] s) throws SQLException{
    
    }        
}
