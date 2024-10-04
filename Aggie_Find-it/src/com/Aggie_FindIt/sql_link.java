package com.Aggie_FindIt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class sql_link {

    //method to check for user and password
    // 0 for user not found 1 for password incorrect 2 for all good
    public static int login(String userName, String passHash) {
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/CS_371", "user", "password");
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM AdminUser A, StudentUser S \n WHERE A.username = \"" + userName + "\" or S.username = \"" + userName + "\"");

            boolean x = res.next();

            if(!x){
                connection.close();
                return 0;
            }
            if((res.getString("password_hash")).equals(passHash)){
                connection.close();
                return 2;
            }
            connection.close();
            return 1;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //takes column names as input and forms database query, returns string with info
    //NOTE: Do not share id with user, unnecessary.
    public static String itemSearch(String item_name, String description, String building, String category){

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/CS_371", "user", "password");
            Statement stmt = connection.createStatement();

            StringBuilder query = new StringBuilder();
            boolean and = false;
            query.append("SELECT * FROM Items \n WHERE ");
            if(!item_name.isEmpty()){
                query.append("item_name = \"" + item_name + "\" ");
                and = true;
            }
            if(!description.isEmpty()){
                if (and){
                    query.append("AND");
                }
                query.append("description = \"" + description + "\" ");

                and = true;
            }
            if(!building.isEmpty()){
                if (and){
                    query.append("AND ");
                }
                query.append("building = \"" + building + "\" ");

                and = true;
            }
            if(!category.isEmpty()){
                query.append("category = \"" + category + "\"");
            }

            ResultSet res = stmt.executeQuery(query.toString());

            StringBuilder ans = new StringBuilder();

            while (res.next()){
                ans.append((res.getString("item_id") + ", "));
                ans.append((res.getString("item_name") + ", "));
                ans.append((res.getString("description") + ", "));
                ans.append((res.getString("building") + ", "));
                ans.append((res.getString("category") + "\n"));
            }

            connection.close();
            return ans.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //adds item, True if success, False if fail
    public static boolean addItem(String item_name, String description, String building, String category){
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/CS_371", "user", "password");
            Statement stmt = connection.createStatement();

            StringBuilder query = new StringBuilder();
            boolean and = false;

            query.append("INSERT INTO CS_371.Items (item_name, description, building, category) VALUES ( ");
            query.append("\"" + item_name + "\", ");
            query.append("\"" + description + "\", ");
            query.append("\"" + building + "\", ");
            query.append("\"" + category + "\")");

            stmt.executeUpdate(query.toString());

            return true;

        } catch (Exception e) {
            return false;
//            throw new RuntimeException(e);
        }
    }

    //adds User, True if success, False if fail
    public static boolean addUser(String user_type, String username, String password_hash, String email_or_location){
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/CS_371", "user", "password");
            Statement stmt = connection.createStatement();

            StringBuilder query = new StringBuilder();
            String col = "";

            if(user_type.equals("Admin")){ col = "location";}
            else{ col = "email";}
            query.append("INSERT INTO CS_371."+user_type+"User (username, password_hash, "+col+") VALUES ( ");
            query.append("\"" + username + "\", ");
            query.append("\"" + password_hash + "\", ");
            query.append("\"" + email_or_location + "\")");

            stmt.executeUpdate(query.toString());

            return true;

        } catch (Exception e) {
            return false;
//            throw new RuntimeException(e);
        }
    }

    // Removes item from database, true if success, false if not
    public static boolean removeItem(String item_id){
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/CS_371", "user", "password");
            Statement stmt = connection.createStatement();

            StringBuilder query = new StringBuilder();
            query.append("DELETE FROM CS_371.Items WHERE (item_id = "+item_id+")");

            stmt.executeUpdate(query.toString());

            return true;

        } catch (Exception e) {
            return false;
//            throw new RuntimeException(e);
        }
    }

    //adds item, True if success, False if fail
    public static boolean editItem(String item_id, String item_name, String description, String building, String category) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/CS_371", "user", "password");
            Statement stmt = connection.createStatement();

            StringBuilder query = new StringBuilder();
            boolean and = false;
            query.append("UPDATE CS_371.Items SET ");
            if (!item_name.isEmpty()) {
                query.append("item_name = \"" + item_name + "\" ");
                and = true;
            }
            if (!description.isEmpty()) {
                if (and) {
                    query.append(", ");
                }
                query.append("description = \"" + description + "\" ");
                and = true;
            }
            if (!building.isEmpty()) {
                if (and) {
                    query.append(", ");
                }
                query.append("building = \"" + building + "\" ");
                and = true;
            }
            if (!category.isEmpty()) {
                if (and) {
                    query.append(", ");
                }
                query.append("category = \"" + category + "\" ");
            }

            query.append(" WHERE (item_id = " + item_id + ")");

            stmt.executeUpdate(query.toString());

            return true;

        } catch (Exception e) {
            return false;
//            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
    }
}
