import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

class Listener3 implements ActionListener{
    static boolean b=true;
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==AmountTransferGUI.clear){
            AmountTransferGUI.sendertf.setText(null);
            AmountTransferGUI.receivertf.setText(null);
            AmountTransferGUI.amounttf.setText(null);
            AmountTransferGUI.passwordf.setText(null);
        }
        else if(e.getSource()==AmountTransferGUI.submit) {
            String s_acc = AmountTransferGUI.sendertf.getText();
            int s_accountno = Integer.parseInt(s_acc);
            String r_acc = AmountTransferGUI.receivertf.getText();
            int r_accountno = Integer.parseInt(r_acc);
            String s_pass = AmountTransferGUI.passwordf.getText();
            String s_AMount = AmountTransferGUI.amounttf.getText();
            int s_amount = Integer.parseInt(s_AMount);
            try {
                //Sender Code
                PreparedStatement pst = AmountTransferGUI.con.prepareStatement("Select * from account where account_number = ?");
                pst.setInt(1, s_accountno);
                ResultSet rs = pst.executeQuery();
                String s_password = "";
                int s_balance = 0;
                int s_accountNo = 0;
                String s_status = "";
                if (rs.next()) {
                    s_password = rs.getString("password");
                    s_balance = rs.getInt(4);
                    s_accountNo = rs.getInt(3);
                    s_status = rs.getString("status");
                }
                //receiver code

                pst = AmountTransferGUI.con.prepareStatement("select * from account where account_number = ?");
                pst.setInt(1, r_accountno);
                rs = pst.executeQuery();
                int r_balance = 0;
                int r_accountNo = 0;
                String r_status = "";
                if (rs.next()) {
                    r_balance = rs.getInt("balance");
                    r_accountNo = rs.getInt(3);
                    r_status = rs.getString("status");
                }
                //checks
                if (s_status.equals("Blocked")) {
                    b=false;
                    JOptionPane.showMessageDialog(AmountTransferGUI.f, "Your account have blocked as you have fill wrong password 3 times");
                } else if (r_status.equals("Blocked")) {
                    b=false;
                    System.out.println("Receiver account is blocked");
                    JOptionPane.showMessageDialog(AmountTransferGUI.f, "Receiver account is blocked so you can't make payment");
                } else if (s_accountNo != s_accountno) {
                    b=false;
                    System.out.println("Incorrect sender account number please enter again");
                    JOptionPane.showMessageDialog(AmountTransferGUI.f, "Incorrect sender account number please enter again");
                    AmountTransferGUI.sendertf.setText(null);
                } else if (r_accountNo != r_accountno) {
                    b=false;
                    System.out.println("Incorrect receiver account number please enter again");
                    JOptionPane.showMessageDialog(AmountTransferGUI.f, "Incorrect receiver account number please enter again");
                    AmountTransferGUI.receivertf.setText(null);
                } else if (!s_pass.equals(s_password)) {
                    b=false;
                    System.out.println("Incorrect Password please enter again");
                    JOptionPane.showMessageDialog(AmountTransferGUI.f, "Incorrect password please enter again. You have "+(2-AmountTransferGUI.count)+" left");
                    AmountTransferGUI.passwordf.setText(null);
                    AmountTransferGUI.count++;
                    if (AmountTransferGUI.count == 3) {
                        //account blocked
                        s_status = "Blocked";
                        pst = AmountTransferGUI.con.prepareStatement("update account set status = ? where account_number = ?");
                        pst.setString(1, s_status);
                        pst.setInt(2, s_accountNo);
                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(AmountTransferGUI.f,"Your account is blocked ");
                        System.out.println("Your account is blocked as you have fill wrong password 3 times");
                    }
                } else if (s_balance < s_amount) {
                    b=false;
                    System.out.println("Insufficient Balance please enter again");
                    JOptionPane.showMessageDialog(AmountTransferGUI.f, "Insufficient balance");
                    AmountTransferGUI.amounttf.setText(null);
                } else {
                    b=true;
                    AmountTransferGUI.con.setAutoCommit(false); //Set the connection property
                    Statement stmt = AmountTransferGUI.con.createStatement();
                    s_balance -= s_amount;
                    r_balance += s_amount;

                    stmt.addBatch("update account set balance = " + s_balance + " where account_number = " + s_accountno);
                    stmt.addBatch("update account set balance = " + r_balance + " where account_number = " + r_accountno);
//                     pst = AmountTransferGUI.con.prepareStatement("update account set balance = ? where account_number = ?");
//                    pst.setInt(1, s_balance);
//                    pst.setInt(2, s_accountno);
//                    pst.addBatch(); //Query is added in the batch
//
//                    pst = AmountTransferGUI.con.prepareStatement("update account set balance = ? where account_number = ?");
//                    pst.setInt(1, r_balance);
//                    pst.setInt(2, r_accountno);
//                    pst.addBatch(); //Second query is added

                  stmt.executeBatch();
                    pst.executeBatch();
                    AmountTransferGUI.con.commit();
                }
                if(b)  JOptionPane.showMessageDialog(AmountTransferGUI.f,"Transaction successful");
            }
            catch(Exception ex){
                System.out.println(ex);
                try {
                    AmountTransferGUI.con.rollback();
                } catch (SQLException exc) {
                    exc.printStackTrace();
                }
            }

        }
        try {
            PreparedStatement st2 = AmountTransferGUI.con.prepareStatement("Select * from account");
            ResultSet rs12 = st2.executeQuery();
            while (rs12.next()) {
                System.out.println(rs12.getString(1) + "\t" + rs12.getString(2) +"\t"+ rs12.getInt(3) + "\t" + rs12.getInt(4) + "\t"+ rs12.getString(5));
            }
        } catch (Exception ee) {}

        try {
            AmountTransferGUI.con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


    }

}
public class AmountTransferGUI {
   static  Connection con ;
    static JFrame f = new JFrame("AmountTranferGUI");
    static  JLabel senderA,receiverA,amount,passwordS;
    static JTextField sendertf,receivertf,amounttf;
    static JPasswordField passwordf;
    static JButton submit, clear;
    static int count=0;
    AmountTransferGUI(){
        f.getContentPane().setBackground(Color.cyan);
        Font font =new Font("serif",1,18);
        senderA =new JLabel("Enter sender's account no: ");
        senderA.setFont(font);
        receiverA =new JLabel("Enter receiver's account no: ");
        receiverA.setFont(font);
        amount =new JLabel("Enter amount to be transferred : ");
        amount.setFont(font);
        passwordS =new JLabel("Enter your password: ");
        passwordS.setFont(font);
        sendertf= new JTextField("");
        receivertf=new JTextField("");
        amounttf =new JTextField("");
        passwordf =new JPasswordField("");
        submit= new JButton("Submit");
        clear =new JButton("Clear");
        f.setLayout(null);
        senderA.setBounds(100,50,280,50);
        receiverA.setBounds(100,150,280,50);
        amount.setBounds(100,250,280,50);
        passwordS.setBounds(100,350,280,50);
        sendertf.setBounds(450,50,250,50);
        receivertf.setBounds(450,150,250,50);
        amounttf.setBounds(450,250,150,50);
        passwordf.setBounds(450,350,150,50);
        submit.setBounds(150,450,100,50);
        clear.setBounds(450,450,100,50);
        f.add(senderA); f.add(receiverA);f.add(amount);f.add(passwordS);
        f.add(sendertf);f.add(receivertf); f.add(amounttf);f.add(passwordf);
        f.add(submit); f.add(clear);
        Listener3 l =new Listener3();
        submit.addActionListener(l);
        clear.addActionListener(l);

        f.setVisible(true);
        f.setSize(800,600);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    public static void main(String[] args) {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/KE015","root","Roney@123");
        }
        catch(Exception e) {
            System.out.println(e);
        }
        new AmountTransferGUI();
    }
}
