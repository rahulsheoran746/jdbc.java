import java.sql.*;

public class Jdbc{
    public static void main(String[] args) {
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver");
            //Driver manager is a class and get Connection is a method in this class.
            //Connection is an interface
           Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/KE015","root","Roney@123");

            Statement stmt = con.createStatement();
            //execute() for all commands, executeQuery() specially for SELECT query, executeUpdate() for DML commands, executeBatch()
//          imp as we create table here
//            stmt.executeUpdate("create table student (name varchar(20), roll int, cgpa double)");

          //  String query = "insert into student values ('Ravi Kant Sahu', 535, 7.98)";
            stmt.execute("insert into student values ('Ravi Kant Sahu', 535, 7.98)");
            stmt.execute("insert into student values ('Ravi', 538, 8)");
            // stmt.execute("delete from student");

            //System.out.println(s + " Rows affected");
            //PreparedStatement stmt1=con.prepareStatement("select * from Student where cgpa < ? & roll < ?"); //prepareStatement(), prepareCall()
            // stmt1.setDouble(1, 10.0);
            // stmt1.setInt(2, 20);
             ResultSet rs=stmt.executeQuery("Select * from student");

             while(rs.next())
            {
                System.out.println(rs.getString(1)+"\t"+rs.getInt("roll") + "\t" + rs.getDouble(3));
            }

            con.close();
        }
        catch(Exception e){ System.out.println(e);}
    }
}
