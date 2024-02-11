//import java.awt.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class Listener implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == PasswordReset.clear)
        {
            PasswordReset.user.setText(null);
            PasswordReset.pass.setText(null);
            PasswordReset.pass2.setText(null);
        }
        else if(e.getActionCommand().equals("Submit"))
        {
            String uname = PasswordReset.user.getText();
            String pwd1 = PasswordReset.pass.getText();
            String pwd2 = PasswordReset.pass2.getText();
            if(uname.equals("")){
                JOptionPane.showMessageDialog(PasswordReset.f,"Enter Your Username ");
            }
            if(pwd1.equals("")){
                JOptionPane.showMessageDialog(PasswordReset.f,"Enter password ");
            }
            if(!pwd1.equals(pwd2))
            {
                PasswordReset.pass.setText(null);
                PasswordReset.pass2.setText(null);
                JOptionPane.showMessageDialog(PasswordReset.f, "Both the Passwords must be Same");
            }
            else
            {
//To update the password
                try {
                    try{
                        PasswordReset.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/KE015","root","Roney@123");
                    }
                    catch(Exception ex) {
                        System.out.println(ex);
                    }
                    PreparedStatement pst = PasswordReset.con.prepareStatement("select * from credentials where username = ?");
                    pst.setString(1, uname);
                    ResultSet rs = pst.executeQuery();
                    if (!rs.next()) {
                        JOptionPane.showMessageDialog(PasswordReset.f, "UserId Not exists");
                    } else {
                       // rs.next();
                        String user = rs.getString(1);
                        if (user.equals(uname)) {
                            pst = PasswordReset.con.prepareStatement("update credentials set password = ? where username = ?");
                            pst.setString(1, pwd1);
                            pst.setString(2, uname);
                            pst.executeUpdate();
                        }
                        JOptionPane.showMessageDialog(PasswordReset.f,"Password Successfully changed");
                     //   JOptionPane.showMessageDialog(PasswordReset.f,"Back to Login Page");
                      //  PasswordReset.f.setVisible(false);
                     //   PasswordReset.con.close();
                        PasswordReset.f.dispose();
                      //  new LoginPage();
                    }
                }
                catch(Exception ex) { System.out.println(ex);}
            }
        }
    }
}
public class PasswordReset {
    static Connection con;
    static JFrame f = new JFrame("Password Reset");
    static JLabel userl,passl,pass2l;
    static JTextField user, pass, pass2;
    static JButton submit, clear;
    PasswordReset()
    {
        f.getContentPane().setBackground(Color.cyan);
        Font f1= new Font("serif",3,20);
        userl= new JLabel("UserName:");
        passl= new JLabel("Enter Password:");
        pass2l= new JLabel("ReEnter Password:");
        userl.setFont(f1); passl.setFont(f1);pass2l.setFont(f1);
        user = new JTextField();
        pass = new JTextField();
        pass2 = new JTextField();
        submit = new JButton("Submit");
        clear = new JButton("Clear");
        f.setLayout(null);
        userl.setBounds(200,150,200,50);
        passl.setBounds(200,220,200,50);
        pass2l.setBounds(200,290,200,50);

        user.setBounds(450, 150, 300, 50);
        pass.setBounds(450, 220, 300, 50);
        pass2.setBounds(450, 290, 300, 50);
        submit.setBounds(250, 400, 100, 50);
        clear.setBounds(450, 400, 100, 50);
        f.add(userl);f.add(passl);f.add(pass2l);
        f.add(user); f.add(pass); f.add(pass2);
        f.add(submit); f.add(clear);

        Listener l = new Listener();
        submit.addActionListener(l);
        clear.addActionListener(l);
        f.setSize(800, 800);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setVisible(true);
    }
    public static void main(String[] args) {

        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/KE015","root","Roney@123");
        }
        catch(Exception e) {
            System.out.println(e);
        }
        new PasswordReset();
    }
}
