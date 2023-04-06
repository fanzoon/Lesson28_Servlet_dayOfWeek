import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

@WebServlet("/DayOfWeek")
public class ServletDayOfWeek extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        int day = Integer.parseInt(req.getParameter("day"));
        int month = (Integer.parseInt(req.getParameter("month")) - 1);
        int year = Integer.parseInt(req.getParameter("year"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        resp.setContentType("text/html");
        try (PrintWriter writer = resp.getWriter()) {
            writer.println("<h1>Day of week: " + simpleDateFormat.format(calendar.getTime()) + "<h1>");
        } catch (IOException e) {
            System.out.println("(writer не закрыт");
        }
    }
}
