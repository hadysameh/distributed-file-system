/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributed.server;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static jdk.nashorn.internal.objects.NativeRegExp.source;

/**
 *
 * @author hady
 */
public class Driver {
    
    Driver(){
        
    }
    
    public boolean is_thing_exist_in_dir(String path,String folder_name){
        String[] file_list=this.ls(path);
        for (String file_list1 : file_list) {
            if (folder_name.equals(file_list1)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean is_path_exist(String path){
        File file = new File(path);
        return file.exists();
    }
    
    public boolean create_folder(String path){        
        File file = new File(path);
        boolean bool = file.mkdir();        
        return bool;
    }
    
    public boolean delete_folder(String path){        
        File file = new File(path);
        boolean bool = file.delete();        
        return bool;
    }
    
    public String[] ls(String path){
//        System.out.println("ls from driver");
        File file = new File(path);
//        String s=f.listFiles()[2].getName();
        String[] names = new String[file.listFiles().length];
        for (int i = 0; i < file.listFiles().length; i++) {
           names[i] = file.listFiles()[i].getName();
//           System.out.println(names[i]);
//           System.out.println(i);
        }        
        return names;
    }
    
    public boolean rename(String dir_path,String old_name,String new_name){
        
        File file = new File(dir_path+"/"+old_name);
        // File (or directory) with new name
        File file2 = new File(dir_path+"/"+new_name);
        
        boolean success = file.renameTo(file2);
        
        return success;
    }
    
    public boolean move(String old_dir_path,String new_dir_path,String file_name){
        File file = new File(old_dir_path+"/"+file_name);
        
        if(file.renameTo(new File(new_dir_path+"/"+file_name))){
            return true;
        }
        return false;
    }
    
    public boolean copy(File source, File dest) throws IOException{
        Path p =Files.copy(source.toPath(), dest.toPath());
        if(p!= null){
            return true;
        }
        return false;
    }
    
    public static void main (String [] str) throws IOException{
        
        String str1 = "wxyz12";       
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
        System.out.println(result);
    }    
}
