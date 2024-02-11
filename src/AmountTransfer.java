import java.util.*;
import java.sql.*;

public class AmountTransfer {
    static int count=1;
    static boolean b=true;
    public static void accountTransfer() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter the sender's account number: \t");
        int sender = sc.nextInt();
        System.out.print("\nEnter the receiver's account number: \t");
        int receiver = sc.nextInt();
        System.out.print("\nEnter the AMount to be transferred: \t");
        int amount = sc.nextInt();
        System.out.print("\nEnter the sender's Password: \t");
        String pwd = sc.next();
        Connection con = null;
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/KE015","root","Roney@123");
            //sender code
            PreparedStatement pst = con.prepareStatement("select * from account where account_number = ?");
            pst.setInt(1, sender);
            ResultSet rs = pst.executeQuery();
            String s_password="";
            int s_balance=0;
            int s_accountNo=0;
            String s_status="";
            if (rs.next()) {
                s_password = rs.getString("password");
                s_balance = rs.getInt(4);
                s_accountNo = rs.getInt(3);
                s_status=rs.getString("status");
            } else {
                System.out.println("in else of sender");
            }
            //reciever code
            pst = con.prepareStatement("select * from account where account_number = ?");
            pst.setInt(1, receiver);
            rs = pst.executeQuery();
            int r_balance = 0;
            int r_accountNo = 0;
            String r_status="";
            if (rs.next()) {
                r_balance = rs.getInt("balance");
                r_accountNo = rs.getInt(3);
                r_status=rs.getString("status");
            } else {
                System.out.println("in else of receiver");
            }

            //checks
            if(s_status.equals("Blocked")){
                b=false;
                System.out.println("Your account is blocked ");
                System.out.println();
            }
            else if(r_status.equals("Blocked")){
                b=false;
                System.out.println("Receiver account is blocked");
                System.out.println();
            }
            else if (s_accountNo != sender) {
                b=false;
                System.out.println("Incorrect sender account number please enter again");
//            sender = sc.nextInt();
                accountTransfer();
        } else if (r_accountNo != receiver) {
                b=false;
            System.out.println("Incorrect receiver account number please enter again");
//            receiver = sc.nextInt();
            accountTransfer();
        } else if (!pwd.equals(s_password)) {
                b=false;
            System.out.println("Incorrect Password please enter again");
//            pwd = sc.next();
            count++;
                System.out.println(count);
            if(count==3){
                //account blocked
                s_status="Blocked";
                pst = con.prepareStatement("update account set status = ? where account_number = ?");
                pst.setString(1, s_status);
                pst.setInt(2, s_accountNo);
                pst.executeUpdate();
                System.out.println("Your account is blocked as you have fill wrong password 3 times");
                return;
            }
            accountTransfer();
        }
        else if (s_balance < amount) {
            b=false;
            System.out.println("Insufficient Balance please enter again");
            accountTransfer();
        }
            else
            {
                con.setAutoCommit(false); //Set the connection property
                Statement stmt = con.createStatement();
                s_balance -= amount;
                r_balance += amount;
                System.out.println(s_balance);
                System.out.println(r_balance);

                stmt.addBatch("update account set balance = " + s_balance + " where account_number = " + sender);
                stmt.addBatch("update account set balance = " + r_balance + " where account_number = " + receiver);
//                    pst = con.prepareStatement("update account set balance = ? where account_number = ?");
//                    pst.setInt(1, s_balance);
//                    pst.setInt(2, sender);
//                    pst.addBatch(); //First query is added
//
//                    pst = con.prepareStatement("update account set balance = ? where account_number = ?");
//                    pst.setInt(1, r_balance);
//                    pst.setInt(2, receiver);
//                    pst.addBatch(); //Second query is added

               stmt.executeBatch();
              //  pst.executeBatch();
                con.commit();
            }
            if(b) System.out.println("Transaction Successful");
            System.out.println();
        }
        catch(Exception ex)
        {
            System.out.println(ex);
            con.rollback();
        }
        try {
            PreparedStatement st2 = con.prepareStatement("Select * from account");
            ResultSet rs12 = st2.executeQuery();
            while (rs12.next()) {
                System.out.println(rs12.getString(1) + "\t" + rs12.getString(2) +"\t"+ rs12.getInt(3) + "\t" + rs12.getInt(4) + "\t"+ rs12.getString(5));
            }
        } catch (Exception ee) {}

    }

    public static void main(String[] args) throws Exception {
        accountTransfer();

    }
}
