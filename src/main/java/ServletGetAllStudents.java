import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/getAllStudents")
public class ServletGetAllStudents extends HttpServlet {
    public static final String URL = "jdbc:mysql://localhost:3306/students28";
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "admin";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
//        String id = String.valueOf(Integer.parseInt(req.getParameter("id")));
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            System.out.println("Exception loading driver...");
        }
        String query = "SELECT * FROM students28.students";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery(query);
            PrintWriter writer = resp.getWriter();
            writer.println("<h1>Students:</h1>");
            while (resultSet.next()) {
                int idStudent = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                int age = resultSet.getInt("age");
                int numberGroup = resultSet.getInt("number_group");
                String gender = resultSet.getString("gender");
                resp.setContentType("text/html");
                writer.println("<h1>" + "id=" + idStudent
                        + "; " + surname
                        + ' ' + name
                        + "; age:" + age
                        + "; numberGroup:" + numberGroup
                        + "; gender:" + gender + "<h1>");
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
            System.out.println("Exception!!!");
        } catch (IOException e) {
            System.out.println("writer не закрыт!");
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.out.println("resultSet не закрыт!");
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
                    System.out.println("connection не закрыт!");
                }
            }
        }
    }
}
