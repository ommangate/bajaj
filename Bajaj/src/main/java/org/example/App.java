package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class App {

    public static void main(String[] args) {
        try {
           
            File jsonFile = new File("input.json");
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonFile);

            
            String firstName = rootNode.path("student").path("first_name").asText();
            String rollNumber = rootNode.path("student").path("roll_number").asText();

          
            firstName = firstName.toLowerCase();
            rollNumber = rollNumber.toLowerCase();

            // Step 3: Concatenate the Strings and Generate MD5 Hash
            String concatenatedString = firstName + rollNumber;
            String md5Hash = generateMD5Hash(concatenatedString);

            // Step 4: Write the MD5 Hash to output.txt
            try (FileWriter writer = new FileWriter("output.txt")) {
                writer.write(md5Hash);
            }

            System.out.println("MD5 Hash: " + md5Hash);

        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

  
    public static String generateMD5Hash(String input) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] hashBytes = messageDigest.digest(input.getBytes());
        StringBuilder hexString = new StringBuilder();

        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
