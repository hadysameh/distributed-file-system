/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributed.server;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author hady
 */
public class Driver_facades {
    
    private Driver driver = new Driver();
    public final String system_root_path ="F:/4th cse asu/distributed project/project_actual_storage/";
    public String logged_in_user_current_path = null;/*it's a full path & must always ends with /*/
    public String logged_in_user_home_path = null;
    
    public boolean create_user_roots(String email){
           boolean b1 = driver.create_folder(system_root_path+email);
           boolean b2 =false;
           
           if(b1)
           {
                System.out.println("files created");
                b2= driver.create_folder(system_root_path+email+"\\home");
           }                                
           
           return b2&&b1;
    }
    
    public String[] ls(){
        String[] str = driver.ls(logged_in_user_current_path);        
        return driver.ls(logged_in_user_current_path);
    }
    
    public String pwd()/*needs to be fixed*/
    {
//        System.out.println(logged_in_user_current_path);
        
        String str;
        str = logged_in_user_current_path.replaceAll(logged_in_user_home_path, "home/");
        if(str.contains("\\")){
            str = str.replace("\\", "/").replaceAll(logged_in_user_home_path, "home/");
        }
        System.out.println(str);        
        return str;
    }
    
    public boolean mkdir(String folder_name)
    {
//        string files_list = driver.ls();
        if(!driver.is_thing_exist_in_dir(logged_in_user_current_path, folder_name)){
            return driver.create_folder(logged_in_user_current_path+folder_name);
        }
        return false;        
    }
    
    public boolean rmdir(String folder_name)
    {
        System.out.println("rmdir from driver facades");
        
        if(driver.is_thing_exist_in_dir(logged_in_user_current_path, folder_name)){
            
            System.out.println("file in "+logged_in_user_current_path);
            
            return driver.delete_folder(logged_in_user_current_path+folder_name);
        }
        return false;
    }
    
    public boolean rm(String file_name){
        if(driver.is_thing_exist_in_dir(logged_in_user_current_path, file_name)){
            return driver.delete_folder(logged_in_user_current_path+file_name);
        }
        return false;
    }
    
    public boolean rnm(String old_name,String new_name){
        if(driver.is_thing_exist_in_dir(logged_in_user_current_path, old_name)){
           return driver.rename(logged_in_user_current_path , old_name, new_name);
        }
        return false;
    }
    
    public boolean mv(String new_path,String file_name){
        /*the new path is from the home foler*/
        if(driver.is_thing_exist_in_dir(logged_in_user_current_path, file_name))
        {
            /*first check the new path where if it's from home root or where*/
            if(new_path.length()>=5&&new_path.substring(0, 5).equals("home/")){
//                System.out.println("if path");
                if(driver.is_path_exist(logged_in_user_home_path.subSequence(0, logged_in_user_home_path.length()-5)+"/"+new_path)){
//                    System.out.println("move full path path exist");
                    return driver.move(logged_in_user_current_path, logged_in_user_home_path.subSequence(0, logged_in_user_home_path.length()-5)+"/"+new_path, file_name);
                }
            }            
            else
            {                
                if(driver.is_path_exist(logged_in_user_current_path+new_path))
                {                                    
                    return driver.move(logged_in_user_current_path,logged_in_user_current_path+new_path, file_name);
                }                                                
            }
            return driver.move(logged_in_user_current_path, new_path, file_name);            
        }
        return false;
    }
    
    public boolean cp(String file_name,String paste_path) throws IOException{
        if(driver.is_thing_exist_in_dir(logged_in_user_current_path, file_name)){/*check the file first*/
            System.out.println("check");
            if(paste_path.length()>5&&paste_path.substring(0, 5).equals("home/")){   
                System.out.println("if path");
                if(driver.is_path_exist(logged_in_user_home_path.subSequence(0, logged_in_user_home_path.length()-5)+"/"+paste_path)){
                    System.out.println("paste path exist");
                    /*error in 116*/
//                    String str = logged_in_user_current_path+"/"+paste_path;

                    String str =logged_in_user_home_path.subSequence(0, logged_in_user_home_path.length()-5)+"/"+paste_path+"/";
                    
                    File file_in_path = new File(logged_in_user_current_path+file_name);
                    File paste_file_path = new File(str+file_name);
                    driver.copy(file_in_path, paste_file_path);
                    return true;
                }
             }
             else{
                 System.out.println(logged_in_user_current_path+"/"+paste_path);
                 if(driver.is_path_exist(logged_in_user_current_path+paste_path))
                 { 
                    System.out.println("normal path");
                    String str =logged_in_user_current_path + paste_path+"/";
                    File file_in_path = new File(logged_in_user_current_path+file_name);
                    File paste_file_path = new File(str+file_name);
                    driver.copy(file_in_path, paste_file_path);
                    return true;
                 }
             }            
        }
        
        
        return false;
    }
    
    public boolean cd(String command){
        String []cmd_dtls = command.split(" ");
        try{
//            System.out.println(cmd_dtls[0]);
//            System.out.println(cmd_dtls[1]);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        if(cmd_dtls.length==2){
            if(cmd_dtls[1].equals(".."))
            {
                System.out.println("cd ..");
                if(!logged_in_user_current_path.equals(logged_in_user_home_path))
                {
                    File f = new File(logged_in_user_current_path);
                    logged_in_user_current_path=f.getParentFile().getAbsolutePath()+"/";
//                    System.out.println(logged_in_user_current_path);
                    return true;
                }
                return false;
            }
            else if(cmd_dtls[1].length()>5&&cmd_dtls[1].substring(0, 5).equals("home/"))
            {
//                System.out.println("cd path");
                if(driver.is_path_exist(logged_in_user_home_path.replaceAll("home/", cmd_dtls[1]))){
                    logged_in_user_current_path = logged_in_user_home_path.replaceAll("home/", cmd_dtls[1]+"/"); 
                    return true;
                }
                return false;
            }
            else{
//                System.out.println("cd foler");
//                System.out.println(logged_in_user_current_path);
                if(driver.is_path_exist(logged_in_user_current_path + cmd_dtls[1])){
//                    System.out.println(logged_in_user_current_path);
                    logged_in_user_current_path = logged_in_user_current_path +/* "/" +*/cmd_dtls[1]+"/"; 
//                    System.out.println(logged_in_user_current_path);
                    return true;
                }
                
                return false;
                
            }
            
        }
        else if(cmd_dtls.length==1&&cmd_dtls[0].length()==2)
            {
                System.out.println("cd only");
                logged_in_user_current_path=logged_in_user_home_path;
                return true;
            }
        
        return false;
    }
    
    public static void main (String [] str007) throws IOException{
        
    }
}
