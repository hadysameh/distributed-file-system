/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributed.server;

import com.mysql.jdbc.Statement;
import com.mysql.jdbc.Connection;
import java.sql.SQLException;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.util.Arrays;

/**
 *
 * @author hady
 */
public class Base_model {
    
    private Statement statement;
    private Connection conn;
    
    Base_model() throws SQLException{
        Database db = new Database();
        this.conn = db.openConnection();   
    }
    /*deletes the [ and ]*/
    private String handle_array_to_string(String[] str)
    {        
        return Arrays.toString(str).substring(1, Arrays.toString(str).length() - 1);        
    }
    
    private String[] handle_values_for_insert_func(String[] str){
        for(int i = 0 ;i<str.length;i++){
            str[i]="'"+str[i]+"'";
        }
        return str;
    }
    
    protected String add_single_qoutes(String str){
        return "'"+str+"'";
    }
  
    public int insert(String table_name,String attr[],String values[]) throws SQLException{
        Statement stmt = (Statement) this.conn.createStatement();
        String query;
        query = "INSERT INTO "+ table_name + " ( " + this.handle_array_to_string(attr)+ " ) " +"VALUES" + " ( "+ this.handle_array_to_string(handle_values_for_insert_func(values)) +" ) ;";
//        System.out.println(query);
//        ResultSet rs;   
        int n= stmt.executeUpdate (query);
        return n;
    }
    
    //table_name will be provided in the model class
    public ResultSet select_where(String table_name , String target , String condition)throws SQLException{
        Statement stmt = (Statement) conn.createStatement();
        ResultSet rs;        
//        System.out.println("SELECT "+ target +" FROM " + table_name +" WHERE " + condition);
        rs = stmt.executeQuery("SELECT "+ target +" FROM " + table_name +" WHERE " + condition );
        return rs;
     }
     
    public ResultSet select(String table_name,String target)throws SQLException{
        Statement stmt = (Statement) conn.createStatement();
        ResultSet rs;        
        rs = stmt.executeQuery("SELECT "+ target +" FROM " + table_name  );
        return rs;
     }
    
//    @SuppressWarnings("empty-statement")
    public int update_where(String table_name, String target ,String new_value, String condition) throws SQLException{
        Statement stmt = (Statement) this.conn.createStatement();
        String query;
        query = "UPDATE "+ table_name + " SET "+ target+" = "+ new_value +" where " + condition ;
//        System.out.println(query);
//        ResultSet rs;   
        int n =stmt.executeUpdate (query);
        return n;
    }
           
    public int delete_where(String tabel_name ,String condition) throws SQLException{
        Statement stmt = (Statement) this.conn.createStatement();
        String query;
        String table_name;
        query = "DELETE FROM "+ tabel_name + " WHERE " + condition +";" ;
//        System.out.println(query);
//        ResultSet rs;   
//        System.out.println(stmt.executeUpdate (query));
        int number_of_deleted_elements =stmt.executeUpdate (query);
        return number_of_deleted_elements;
    }
    
//    public void test(){
//        System.out.println("test");
//    }
    
    
    public static void main(String[] s) throws SQLException{
//        
        Base_model test = new Base_model();
//        
//        ResultSet rs =test.select_where("files", "name", "directory_id = 3 and id = 3");
        String attr[]= {"name","directory_id"};
////        
        String values[]= {"test","2"};
////        
        test.insert("files", attr, values);
////        test.update_where("files", "directory_id", "1", "2", "name = show");
////        
////        test.delete_where("files", "id", "1");
////        ResultSet rs = test.select("files", "id");
////        
//        if(!rs.next() ){
//            System.out.println("shit");
//        }
//        while ( rs.next() ) {
//            
//            String lastName = rs.getString("name");
//            
//            String password = rs.getString("id");
//            
//            if(password == null)
//            System.out.println("shit");
//            
//            System.out.println(lastName);
////            
//        }
//
//
    }
    
}
