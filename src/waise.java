import java.sql.*;

public class waise {
    public static void main(String[] args)  {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/KE015", "root", "Roney@123");
            PreparedStatement pst  = con.prepareStatement("Select * from credentials");
            ResultSet rs =pst.executeQuery();
            ResultSetMetaData rms =rs.getMetaData();

        }
        catch(Exception e){
            System.out.println(e);
        }
    }

}
