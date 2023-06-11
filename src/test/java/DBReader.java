import java.sql.*;

public class DBReader {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USER = "username";
    private static final String DB_PASSWORD = "password";

    public static void main(String[] args) {
        try {
            // Подключение к базе данных
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Выполнение INSERT запроса
            insert(conn, "John", 25);

            // Выполнение SELECT запроса
            select(conn);

            // Выполнение UPDATE запроса
            update(conn, 1, "Jane", 30);

            // Выполнение DELETE запроса
            delete(conn, 1);

            // Закрытие подключения к базе данных
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insert(Connection conn, String name, int age) throws SQLException {
        String query = "INSERT INTO People (name, age) VALUES (?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, name);
        pstmt.setInt(2, age);
        pstmt.executeUpdate();
        System.out.println("Данные были успешно добавлены.");
    }

    private static void select(Connection conn) throws SQLException {
        String query = "SELECT * FROM People";
        PreparedStatement pstmt = conn.prepareStatement(query);
        ResultSet resultSet = pstmt.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");
            System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age);
        }

        resultSet.close();
        pstmt.close();
    }

    private static void update(Connection conn, int id, String name, int age) throws SQLException {
        String query = "UPDATE People SET name = ?, age = ? WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, name);
        pstmt.setInt(2, age);
        pstmt.setInt(3, id);
        pstmt.executeUpdate();
        System.out.println("Данные были успешно обновлены.");
    }

    private static void delete(Connection conn, int id) throws SQLException {
        String query = "DELETE FROM People WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
        System.out.println("Данные были успешно удалены.");
    }
}
