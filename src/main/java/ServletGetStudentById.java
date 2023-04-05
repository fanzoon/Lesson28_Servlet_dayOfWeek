import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/getStudentById")
public class ServletGetStudentById extends HttpServlet {
    public static final String URL = "jdbc:mysql://localhost:3306/students28";
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "admin";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            System.out.println("Exception loading driver...");
        }

        int id = Integer.parseInt(req.getParameter("id"));
        String query = "SELECT name, surname, age, number_group, gender FROM students28.students WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                int age = resultSet.getInt("age");
                int numberGroup = resultSet.getInt("number_group");
                String gender = resultSet.getString("gender");
                resp.setContentType("text/html");
                try (PrintWriter writer = resp.getWriter()) {
                    writer.println("<h1>Student</h1>");
                    writer.println("<h1>" + "id=" + id
                            + "; " + surname
                            + ' ' + name
                            + "; age:" + age
                            + "; numberGroup:" + numberGroup
                            + "; gender:" + gender + "<h1>");
                } catch (IOException e) {
                    System.out.println("(writer не закрыт");
                }
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
            System.out.println("Exception!!!");
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.out.println("resultSet не закрыт");
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println("statement не закрыт");
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("connection не закрыт");
                }
            }
        }
    }
}
