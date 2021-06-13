package pictureviewer;

import javafx.scene.control.ListView;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.LinkedList;

public class MySQL {
    static final String url = "jdbc:mysql://localhost:3306/";
    static final String CONNECTION_USERNAME = "root";
    static final String CONNECTION_PASSWORD = "qwe123";

    //putPhoto("Рыжий кот", "c:\\scan\\red_cat.png");

    static void putPhoto(String name, String path) {
        try (Connection conn = DriverManager.getConnection(url, CONNECTION_USERNAME, CONNECTION_PASSWORD)){
            File file = new File(path);
            if (Files.exists(Paths.get(path))) {
                long size = file.length();
                BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
                String sql = "INSERT INTO DBO.PICTURES (NAME, PHOTO) VALUES (?, ?)";

                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, name);
                preparedStatement.setBinaryStream(2, fis, size);
                preparedStatement.execute();
            } else {
                System.out.println("Такого файла не существует");
            }

        } catch (SQLException | FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    static LinkedList<String> loadInfo() {
        LinkedList<String> linkedList = new LinkedList<>();

        try (Connection con = DriverManager.getConnection(url, CONNECTION_USERNAME, CONNECTION_PASSWORD)){

            Statement st = null;
            ResultSet rs = null;

            try{
                st = con.createStatement();
                rs = st.executeQuery("SELECT name FROM DBO.PICTURES");
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

    static Image loadPicture(int index) {
        Image image = null;

        try (Connection con = DriverManager.getConnection(url, CONNECTION_USERNAME, CONNECTION_PASSWORD)){

            Statement st = null;
            ResultSet rs = null;

            try{
                st = con.createStatement();
                rs = st.executeQuery("SELECT * FROM DBO.PICTURES WHERE idpictures = " + index);
                while(rs.next()){
                    byte[] data = rs.getBytes("photo");
                    image = new Image(new ByteArrayInputStream(data));
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return image;
    }

    static void uploadPicture(String inputString) {
        try {
            File file = new File(inputString);
            putPhoto(file.getName(), inputString);
        } catch (Exception ex) {
            System.out.println("Либо такого файла не существует, либо что-то ещё...");
        }

    }
}
