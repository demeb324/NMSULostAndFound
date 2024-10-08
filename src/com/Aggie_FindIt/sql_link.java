package com.Aggie_FindIt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;


public class sql_link {

    //method to check for user and password
    // 0 for user not found 1 for password incorrect 2 for admin, 3 for student
    public static int login(String userName, String password) {
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/CS_371", "user", "password");
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT password_hash FROM AdminUser WHERE username = \"" + userName + "\""
                                                    + "UNION SELECT password_hash FROM StudentUser WHERE username = \"" + userName + "\"");

            boolean x = res.next();

            if(!x){
                connection.close();
                return 0;
            }
            String passHash = hashPassword(password);
            if((res.getString("password_hash")).equals("0" + passHash)){
                connection.close();
                return 2;
            }
            if((res.getString("password_hash")).equals("1" + passHash)){
                connection.close();
                return 3;
            }

            connection.close();
            return 1;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //true if exists, false if not
    public static boolean userExists(String userName) {
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/CS_371", "user", "password");
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT username FROM AdminUser WHERE username = \"" + userName + "\""
                    + "UNION SELECT username FROM StudentUser WHERE username = \"" + userName + "\"");

            boolean x = res.next();

            if(!x){
                connection.close();
                return false;
            }
            return true;

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

    //adds AdminUser, True if success, False if fail
    public static boolean addAdminUser(String username, String password, String location, String email){
        try{
            if(userExists(username)){
                return false;
            }
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/CS_371", "user", "password");
            Statement stmt = connection.createStatement();
            String password_hash = hashPassword(password);
            StringBuilder query = new StringBuilder();

            query.append("INSERT INTO CS_371.AdminUser (username, password_hash, location, email) VALUES ( ");
            query.append("\"" + username + "\", ");
            query.append("\"0" + password_hash + "\", ");
            query.append("\"" + location + "\", ");
            query.append("\"" + email + "\")");

            stmt.executeUpdate(query.toString());

            return true;

        } catch (Exception e) {
            return false;
//            throw new RuntimeException(e);
        }
    }

    //adds StudentUser, True if success, False if fail
    public static boolean addStudentUser(String username, String password, String email){
        try{
            if (userExists(username)){
                return false;
            }
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/CS_371", "user", "password");
            Statement stmt = connection.createStatement();
            String password_hash = hashPassword(password);
            StringBuilder query = new StringBuilder();

            query.append("INSERT INTO CS_371.StudentUser (username, password_hash, email) VALUES ( ");
            query.append("\"" + username + "\", ");
            query.append("\"1" + password_hash + "\", ");
            query.append("\"" + email + "\")");

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

    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            BigInteger number = new BigInteger(1, encodedHash);
            StringBuilder hexString = new StringBuilder(number.toString(16));
            while (hexString.length() < 64) {
                hexString.insert(0, '0');
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);  // Handle the exception if SHA-256 is not available
        }
    }





    public static void main(String[] args){
        System.out.println(addAdminUser("test","test","cafe","test"));
    }
}
