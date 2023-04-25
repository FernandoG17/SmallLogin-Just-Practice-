import java.sql.*;
import java.util.Scanner;

public class MySQL {
    public static void main(String[] args) {
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        Connection connection = null;
        ResultSet resultSet = null;
        Scanner scanner = new Scanner(System.in);
        String user = "";
        String password = "";
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test02", "root", "terminator17");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM USERS WHERE username = ?");
            while (!user.equals("QUIT")) {
                System.out.print("Enter new username (or QUIT to exit): ");
                user = scanner.nextLine();
                if (user.equals("QUIT")) {
                    break;
                }
                System.out.print("Enter new password: ");
                password = scanner.nextLine();
                psCheckUserExists.setString(1, user);
                resultSet = psCheckUserExists.executeQuery();
                if (resultSet.isBeforeFirst()) {
                    System.out.println("User already exists!");
                } else {
                    psInsert = connection.prepareStatement("INSERT INTO users (username, password) VALUES (?,?)");
                    psInsert.setString(1, user);
                    psInsert.setString(2, password);
                    psInsert.executeUpdate();
                    System.out.println("User created successfully!");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckUserExists != null) {
                try {
                    psCheckUserExists.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
