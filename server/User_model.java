/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributed.server;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author hady
 */
public class User_model extends Base_model{
    private final String table_name = "users";
    
    public String email;
    public String password;
    
    private final String[] attributes= {"email","password"};
    
    private String[] values= new String[2];
    
    User_model()throws SQLException{
        
    }
    
    User_model(String email,String password)throws SQLException{
        this.email=email;
        
        this.password =password;
        
        values= new String[]{this.email , this.password};
        
        
    }

    public boolean check_email(String email) throws SQLException{
        ResultSet rs;
        
        rs= this.select_where(table_name, "*", "email = "+ this.add_single_qoutes(email));
        
        return rs.next();
    }
    
    public boolean check_password_for_email(String email,String password) throws SQLException{
        ResultSet rs;
        
        rs= this.select_where(table_name, "*", "email = " + this.add_single_qoutes(email) +" and password = "+this.add_single_qoutes(password));
        
        return rs.next();
    }
    
   public boolean save() throws SQLException{
       this.values= new String[]{this.email,this.password};
//       System.out.println(table_name);
       int n = this.insert(table_name, this.attributes, this.values);
       if (n > 0){
           return true;
       }
       else{
           return false;
       }
    }
    
    public static void main(String[] s) throws SQLException{
        
        User_model test = new User_model("ffffffff","sssssssssssssss");
        
        if(test.check_password_for_email("ssss","ssssssss5")){
             System.out.println("yes email");
        }
        else{
             System.out.println("no");
        }
        
//       test.email="ffffffff";
//       test.password="sssssssssssss";
        
        if(test.save()){
            System.out.println("yes saved");
        }
    }
}
