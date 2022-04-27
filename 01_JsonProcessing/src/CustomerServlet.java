import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * @author : M-Prageeth
 * @created : 20/04/2022 - 8:18 PM
 **/

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "1234");
            ResultSet rst = connection.prepareStatement("SELECT * FROM Customer").executeQuery();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            while (rst.next()) {
                String id = rst.getString(1);
                String name = rst.getString(2);
                String address = rst.getString(3);
                double salary = rst.getDouble(4);

                JsonObjectBuilder customerB = Json.createObjectBuilder();
                customerB.add("id",id);
                customerB.add("name",name);
                customerB.add("address",address);
                customerB.add("salary",salary);
                JsonObject build = customerB.build();

                arrayBuilder.add(build);
            }

            JsonArray build = arrayBuilder.build();

            JsonObjectBuilder successB = Json.createObjectBuilder();
            successB.add("data",build);
            successB.add("message","done");
            successB.add("status","200");
            JsonObject success = successB.build();

            PrintWriter writer = resp.getWriter();
            writer.print(success);

            resp.setContentType("application/json");

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerId = req.getParameter("customerId");
        String customerName = req.getParameter("customerName");
        String customerAddress = req.getParameter("customerAddress");
        String customerSalary = req.getParameter("customerSalary");
        double salary = Double.parseDouble(customerSalary);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "1234");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Customer VALUES(?,?,?,?)");
            statement.setString(1, customerId);
            statement.setString(2, customerName);
            statement.setString(3, customerAddress);
            statement.setDouble(4, salary);

            boolean b = statement.executeUpdate() > 0;

            if (b){
                JsonObjectBuilder successB = Json.createObjectBuilder();

                successB.add("data","");
                successB.add("message","Successfully added");
                successB.add("status","200");

                JsonObject success = successB.build();

                PrintWriter writer = resp.getWriter();
                writer.print(success);
            }

            resp.setContentType("application/json");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String custId = req.getParameter("CusID");
        System.out.println(custId);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "1234");
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM Customer WHERE id=?");
            pstm.setObject(1, custId);
            if (pstm.executeUpdate() > 0) {
                JsonObjectBuilder deleteB = Json.createObjectBuilder();

                deleteB.add("data","");
                deleteB.add("message","Successfully deleted");
                deleteB.add("status","200");

                JsonObject delete = deleteB.build();

                PrintWriter writer = resp.getWriter();
                writer.print(delete);
            }

            resp.setContentType("application/json");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerId = req.getParameter("customerId");
        String customerName = req.getParameter("customerName");
        String customerAddress = req.getParameter("customerAddress");
        String customerSalary = req.getParameter("customerSalary");
        double salary = Double.parseDouble(customerSalary);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "1234");
            PreparedStatement statement = connection.prepareStatement("UPDATE Customer SET name=?,address=?,salary=? WHERE id=?");
            statement.setObject(1,customerName);
            statement.setObject(2,customerAddress);
            statement.setObject(3,customerSalary);
            statement.setObject(4,customerId);
            if (statement.executeUpdate()>0) {
                JsonObjectBuilder updateB = Json.createObjectBuilder();

                updateB.add("data","");
                updateB.add("message","Successfully updated");
                updateB.add("status","200");

                JsonObject update = updateB.build();

                PrintWriter writer = resp.getWriter();
                writer.print(update);
            }

            resp.setContentType("application/json");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
