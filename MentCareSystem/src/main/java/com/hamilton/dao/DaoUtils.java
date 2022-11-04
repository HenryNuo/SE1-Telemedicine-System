package com.hamilton.dao;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.*;
import java.util.Arrays;

public class DaoUtils {
    private String javaDatabaseURL = "jdbc:mysql://alfred.cs.uwec.edu:3306/hamiltong2";
    private String javaDatabaseUsername = "HAMILTONG2" ;
    private String javaDatabasePassword = "uA2k1hj5)LYi" ;
    private String javaDatabaseDriver = "com.mysql.jdbc.Driver";
    private Connection connection;

    public DaoUtils(Connection connection){
        this.connection = connection;
    }
    public DaoUtils(){
        this.connection = getConnection();
    }
    public Connection getConnection(){
        Connection connection = null;
        try{
            Class.forName(javaDatabaseDriver);
            connection = DriverManager.getConnection(javaDatabaseURL, javaDatabaseUsername, javaDatabasePassword );
            System.out.println(connection);
        } catch (SQLException e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        } catch(ClassNotFoundException e){
            //TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    public void printSQLException(SQLException ex){
        for(Throwable e : ex){
            if(e instanceof SQLException){
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while(t != null){
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    public boolean uniqueEmail(String email, String role){
        boolean isUnique = false;
        String table = "";
        switch (role){
            case "patient":
                table = "patient";
                break;
            case "doctor":
                table = "doctor";
                break;
            case "CO":
                table = "community_owner";
                break;
            case "MO":
                table = "medical_owner";
                break;
            default:
                break;
        }
        String query = "Select email from " + table + " where email = ?;";
        System.out.println(query);
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, email);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            isUnique = !rs.next(); // it is unique if nothing is returned
        }catch(SQLException e){
            printSQLException(e);
        }
        return isUnique;
    }

    public String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 65536;
        int saltBytes = 16;
        int keyLength = 128;

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[saltBytes];
        random.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = factory.generateSecret(spec).getEncoded();

        System.out.println(hash);

        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    public String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }

    public byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for(int i = 0; i < binary.length; i++) {
            binary[i]= (byte)Integer.parseInt(hex.substring(2*i, 2*i+2), 16);
        }
        return binary;
    }

    public boolean checkPassword(String enteredPassword, String databaseHash) throws NoSuchAlgorithmException, InvalidKeySpecException{
        String[] hashParameters = databaseHash.split(":");
        int iterations = Integer.parseInt(hashParameters[0]);
        byte[] salt = fromHex(hashParameters[1]);
        byte[] hash = fromHex(hashParameters[2]);

        KeySpec spec = new PBEKeySpec(enteredPassword.toCharArray(), salt, iterations, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] testHash = factory.generateSecret(spec).getEncoded();

        System.out.println(testHash);
        System.out.println(hash);

        return(Arrays.equals(hash, testHash));
    }

}
