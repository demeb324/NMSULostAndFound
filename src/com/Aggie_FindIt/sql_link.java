package com.Aggie_FindIt;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Calendar;

import java.util.Properties;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class sql_link{

    private static String loadDatabasePassword() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(".secrets/dbsecret.properties")) {
            properties.load(input);
            return properties.getProperty("db_password");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load database password", e);
        }
    }

    private static MongoClient createConnection() {
        String dbPassword = loadDatabasePassword();
        String connectionString = "mongodb+srv://iiiwidner:" + dbPassword + "@cs371.uotmw.mongodb.net/?retryWrites=true&w=majority&appName=CS371";
        return MongoClients.create(connectionString);
    }

    public static int login(String userName, String password) {
        try (MongoClient mongoClient = createConnection()) {
            MongoDatabase db = mongoClient.getDatabase("CS371");

            MongoCollection<Document> adminUsers = db.getCollection("AdminUser");
            MongoCollection<Document> studentUsers = db.getCollection("StudentUser");

            Document adminUser = adminUsers.find(Filters.eq("username", userName)).first();
            Document studentUser = studentUsers.find(Filters.eq("username", userName)).first();

            if (adminUser == null && studentUser == null) {
                return 0; // User not found
            }

            String passHash = hashPassword(password);
            if (adminUser != null && adminUser.getString("password_hash").equals("0" + passHash)) {
                return 2; // Admin login
            }
            if (studentUser != null && studentUser.getString("password_hash").equals("1" + passHash)) {
                return 3; // Student login
            }

            return 1; // Password incorrect
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean userExists(String userName) {
        try (MongoClient mongoClient = createConnection()) {
            MongoDatabase db = mongoClient.getDatabase("CS371");

            MongoCollection<Document> adminUsers = db.getCollection("AdminUser");
            MongoCollection<Document> studentUsers = db.getCollection("StudentUser");

            Document adminUser = adminUsers.find(Filters.eq("username", userName)).first();
            Document studentUser = studentUsers.find(Filters.eq("username", userName)).first();

            return adminUser != null || studentUser != null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String itemSearch(String item_name, String description, String building, String category) {
        try (MongoClient mongoClient = createConnection()) {
            MongoDatabase db = mongoClient.getDatabase("CS371");
            MongoCollection<Document> items = db.getCollection("Items");

            Document filter = new Document();
            if (!item_name.isEmpty()) filter.append("item_name", item_name);
            if (!description.isEmpty()) filter.append("description", description);
            if (!building.isEmpty()) filter.append("building", building);
            if (!category.isEmpty()) filter.append("category", category);

            StringBuilder result = new StringBuilder();
            for (Document item : items.find(filter)) {
                ObjectId id = item.getObjectId("_id"); // Get the ObjectId
                result.append(id.toString()).append(", ");
                result.append(item.getString("item_name")).append(", ");
                result.append(item.getString("description")).append(", ");
                result.append(item.getString("building")).append(", ");
                result.append(item.getString("category")).append(", ");
                result.append(item.getDate("time")).append("\n");
            }
            return result.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean addItem(String item_name, String description, String building, String category) {
        try (MongoClient mongoClient = createConnection()) {
            MongoDatabase db = mongoClient.getDatabase("CS371");
            MongoCollection<Document> items = db.getCollection("Items");

            Document newItem = new Document("item_name", item_name)
                    .append("description", description)
                    .append("building", building)
                    .append("category", category)
                    .append("time", new Date());

            items.insertOne(newItem);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean addAdminUser(String username, String password, String location, String email) {
        if (userExists(username)) return false;

        try (MongoClient mongoClient = createConnection()) {
            MongoDatabase db = mongoClient.getDatabase("CS371");
            MongoCollection<Document> adminUsers = db.getCollection("AdminUser");

            String password_hash = "0" + hashPassword(password);
            Document newAdminUser = new Document("username", username)
                    .append("password_hash", password_hash)
                    .append("location", location)
                    .append("email", email);

            adminUsers.insertOne(newAdminUser);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean addStudentUser(String username, String password, String email) {
        if (userExists(username)) return false;

        try (MongoClient mongoClient = createConnection()) {
            MongoDatabase db = mongoClient.getDatabase("CS371");
            MongoCollection<Document> studentUsers = db.getCollection("StudentUser");

            String password_hash = "1" + hashPassword(password);
            Document newStudentUser = new Document("username", username)
                    .append("password_hash", password_hash)
                    .append("email", email);

            studentUsers.insertOne(newStudentUser);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean removeItem(String item_id) {
        try (MongoClient mongoClient = createConnection()) {
            MongoDatabase db = mongoClient.getDatabase("CS371");
            MongoCollection<Document> items = db.getCollection("Items");

            items.deleteOne(Filters.eq("item_id", item_id));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean editItem(String item_id, String item_name, String description, String building, String category) {
        try (MongoClient mongoClient = createConnection()) {
            MongoDatabase db = mongoClient.getDatabase("CS371");
            MongoCollection<Document> items = db.getCollection("Items");

            Document updateFields = new Document();
            if (!item_name.isEmpty()) updateFields.append("item_name", item_name);
            if (!description.isEmpty()) updateFields.append("description", description);
            if (!building.isEmpty()) updateFields.append("building", building);
            if (!category.isEmpty()) updateFields.append("category", category);

            items.updateOne(Filters.eq("item_id", item_id), new Document("$set", updateFields));
            return true;
        } catch (Exception e) {
            return false;
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
            throw new RuntimeException(e);
        }
    }

    public static String getRecentItems() {
        try (MongoClient mongoClient = createConnection()) {
            MongoDatabase db = mongoClient.getDatabase("CS371");
            MongoCollection<Document> items = db.getCollection("Items");

            // Set up time filter for the past 24 hours
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR, -24);
            Date last24Hours = calendar.getTime();

            // Query for items added in the last 24 hours
            StringBuilder result = new StringBuilder();
            for (Document item : items.find(Filters.gte("time", last24Hours))) {
                result.append("Item Name: ").append(item.getString("item_name")).append(", ");
                result.append("Description: ").append(item.getString("description")).append(", ");
                result.append("Building: ").append(item.getString("building")).append(", ");
                result.append("Category: ").append(item.getString("category")).append(", ");
                result.append("Time: ").append(item.getDate("time")).append("\n");
            }

            return result.length() > 0 ? result.toString() : "No recent items found.";
        } catch (Exception e) {
            throw new RuntimeException("Error fetching recent items", e);
        }
    }

    public static boolean addRequest(String name, String studentId, String email, 
                                 String itemName, String itemDescription, 
                                 String category, String location, Date time) {
        try (MongoClient mongoClient = createConnection()) {
            MongoDatabase db = mongoClient.getDatabase("CS371");
            MongoCollection<Document> requests = db.getCollection("Request");
            
            Document newRequest = new Document("name", name)
                    .append("studentId", studentId)
                    .append("email", email)
                    .append("itemName", itemName)
                    .append("itemDescription", itemDescription)
                    .append("category", category)
                    .append("location", location)
                    .append("time", time)
                    .append("status", 0);//0 is no action, 1 is found, 2 is not found
            
            requests.insertOne(newRequest);
            return true; 
        } catch (Exception e) {
            e.printStackTrace();
            return false; 
        }
    }

    public static String getRequests() {
        try (MongoClient mongoClient = createConnection()) {
            MongoDatabase db = mongoClient.getDatabase("CS371");
            MongoCollection<Document> requests = db.getCollection("Request");
    
            Document filter = new Document(); // Empty filter retrieves all documents
    
            StringBuilder result = new StringBuilder();
            for (Document request : requests.find(filter)) {
                ObjectId id = request.getObjectId("_id");
                String name = request.getString("name");
                String studentId = request.getString("studentId");
                String email = request.getString("email");
                String itemName = request.getString("itemName");
                String description = request.getString("itemDescription");
                String category = request.getString("category");
                String location = request.getString("location");
                Date time = request.getDate("time");
                int status = request.getInteger("status", 0);
    
                // Format each request's details
                result.append(id).append("\n")
                      .append("Name: ").append(name).append("\n")
                      .append("Student ID: ").append(studentId).append("\n")
                      .append("Email: ").append(email).append("\n")
                      .append("Item Name: ").append(itemName).append("\n")
                      .append("Description: ").append(description).append("\n")
                      .append("Category: ").append(category).append("\n")
                      .append("Location: ").append(location).append("\n")
                      .append("Time: ").append(time).append("\n")
                      .append("Status: ").append(status == 0 ? "No action" 
                                      : status == 1 ? "Found" : "Not Found")
                      .append("\n\n");
            }
    
            return result.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching requests: " + e.getMessage(), e);
        }
    }
    
    

    public static boolean markRequest(String requestId, int status) {
        try (MongoClient mongoClient = createConnection()) {
            MongoDatabase db = mongoClient.getDatabase("CS371");
            MongoCollection<Document> requests = db.getCollection("Requests");
    
            // Create the update document
            Document updateFields = new Document();
            updateFields.append("status", status);
    
            // Update the request by its ID
            requests.updateOne(new Document("_id", new ObjectId(requestId)), new Document("$set", updateFields));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    

    public static String getRecentItemsStudent() {
        try (MongoClient mongoClient = createConnection()) {
            MongoDatabase db = mongoClient.getDatabase("CS371");
            MongoCollection<Document> items = db.getCollection("Items");

            // Set up time filter for the past 24 hours
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_WEEK, -7);
            Date last24Hours = calendar.getTime();

            // Query for items added in the last 24 hours
            StringBuilder result = new StringBuilder();
            for (Document item : items.find(Filters.gte("time", last24Hours))) {
                result.append(item.getString("building")).append(", ");
                result.append(item.getDate("time")).append(", ");
                result.append(item.getString("category")).append("\n");
            }
            return result.length() > 0 ? result.toString() : "No recent items found.";
        } catch (Exception e) {
            throw new RuntimeException("Error fetching recent items", e);
        }
    }

    public static void main(String[] args) {

    }

}