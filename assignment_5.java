import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DepartmentHandler {

    private static final String URL = "jdbc:mysql://localhost:3306/departments";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin_123";

    private static Connection connection;

    private static final String TABLE_NAME = "department";

    private static final String INSERT_QUERY = "INSERT INTO " + TABLE_NAME + " (id, name) VALUES (?, ?)";

    public static void establishConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }

    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public static void addDepartment(Department department) throws SQLException {
        establishConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setInt(1, department.getId());
            preparedStatement.setString(2, department.getName());
            preparedStatement.executeUpdate();
        } finally {
            closeConnection();
        }
    }

    public static void main(String[] args) {
        try {
            Department department = new Department(1, "Human Resources");
            addDepartment(department);
            System.out.println("Department inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class Department {
    private int id;
    private String name;

    public Department(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
