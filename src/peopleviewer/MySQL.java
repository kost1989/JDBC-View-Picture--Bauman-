package peopleviewer;

import javafx.scene.image.Image;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.LinkedList;

public class MySQL {
    static final String url = "jdbc:mysql://localhost:3306/";
    static final String CONNECTION_USERNAME = "root";
    static final String CONNECTION_PASSWORD = "qwe123";

    static void uploadHuman(String name, int year) {
        try (Connection conn = DriverManager.getConnection(url, CONNECTION_USERNAME, CONNECTION_PASSWORD)){
            String sql = "INSERT INTO DBO.PEOPLES (NAME, YEAR) VALUES (?, ?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, year);
            preparedStatement.execute();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    static LinkedList<String> loadPeoples() {
        LinkedList<String> linkedList = new LinkedList<>();

        try (Connection con = DriverManager.getConnection(url, CONNECTION_USERNAME, CONNECTION_PASSWORD)){

            Statement st = null;
            ResultSet rs = null;

            try{
                st = con.createStatement();
                rs = st.executeQuery("SELECT name FROM DBO.PEOPLES");
                while(rs.next()){
                    String name = rs.getString("name");
                    linkedList.add(name);
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return linkedList;
    }

    static String[] loadHuman(int index) {
        String[] outputString = new String[2];

        try (Connection con = DriverManager.getConnection(url, CONNECTION_USERNAME, CONNECTION_PASSWORD)){

            Statement st = null;
            ResultSet rs = null;

            try{
                st = con.createStatement();
                rs = st.executeQuery("SELECT * FROM DBO.PEOPLES WHERE idpeoples = " + index);
                while(rs.next()){
                    outputString[0] = rs.getString("name");
                    outputString[1] = rs.getString("year");
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return outputString;
    }

    public static LinkedList<String> searchHuman(String loadName, String loadYear) {
        LinkedList<String> linkedList = new LinkedList<>();

        try (Connection con = DriverManager.getConnection(url, CONNECTION_USERNAME, CONNECTION_PASSWORD)){

            Statement st = null;
            ResultSet rs;

            loadName = "%" + loadName + "%";

            try{
                st = con.createStatement();
                PreparedStatement p;
                if (!loadName.equals("") && !loadYear.equals("")) {
                    p = con.prepareStatement("SELECT name FROM DBO.PEOPLES WHERE name like ? and year = ?");
                    p.setString(1, loadName);
                    p.setInt(2, Integer.parseInt(loadYear));
                } else {
                    if (!loadName.equals("")) {
                        p = con.prepareStatement("SELECT name FROM DBO.PEOPLES WHERE name like ?");
                        p.setString(1, loadName);
                    } else {
                        if (!loadYear.equals("")) {
                            p = con.prepareStatement("SELECT name FROM DBO.PEOPLES WHERE year = ?");
                            p.setInt(1, Integer.parseInt(loadYear));
                        } else {
                            p = con.prepareStatement("SELECT name FROM DBO.PEOPLES");
                        }
                    }
                }

                rs = p.executeQuery();

                while(rs.next()){
                    String name = rs.getString("name");
                    linkedList.add(name);
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return linkedList;
    }
}
