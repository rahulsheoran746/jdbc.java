import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;


class Listener1 implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {
         if(e.getSource()==LoginPage.clear){
            LoginPage.user.setText(null);
            LoginPage.pass.setText(null);
         }
        else if(e.getSource()==LoginPage.signup){
           // LoginPage.f.setVisible(false);
             try {
                 LoginPage.con.close();
             } catch (SQLException ex) {
                 ex.printStackTrace();
                 System.out.println(ex);
             }
            // LoginPage.f.dispose();
             new SignUp();
        }
        else if(e.getSource()==LoginPage.submit){
            String uname = LoginPage.user.getText();
            String pwd1 = LoginPage.pass.getText();
            if(uname.equals("")){
                JOptionPane.showMessageDialog(LoginPage.f,"Please Enter username");
            }
             else if(pwd1.equals("")){
                 JOptionPane.showMessageDialog(LoginPage.f,"Please Enter password");
             }
             else{
                 try {
                     PreparedStatement pst = LoginPage.con.prepareStatement("select * from credentials where username =?");
                     pst.setString(1, uname);
                     ResultSet rs = pst.executeQuery();
                     if (rs.next()) {
                         //String user =rs.getString(1);
                         String pwd = rs.getString(2);
                         if (pwd.equals(pwd1)) {
                             JOptionPane.showMessageDialog(LoginPage.f, "Log In Successful");
                      //       LoginPage.f.setVisible(false);
                           //  LoginPage.f.dispose();
                             new FirstPage();
                         } else {
                             LoginPage.pass.setText(null);
                             JOptionPane.showMessageDialog(LoginPage.f, "Incorrect Password");
                         }
                     } else {
                         LoginPage.pass.setText(null);
                         LoginPage.user.setText(null);
                         JOptionPane.showMessageDialog(LoginPage.f, "User id not exists Please Click on SIGNUP");
                     }

                 } catch (Exception e1) {
                     System.out.print(e1);
                 }
             }

        }
        else if(e.getSource()==LoginPage.forpass){
            JOptionPane.showMessageDialog(LoginPage.f,"Redirecting you to change Password");
           //  LoginPage.f.setVisible(false);
             new PasswordReset();
        //     LoginPage.f.dispose();
        }
    }
}
class Listenerf1 implements FocusListener{

    @Override
    public void focusGained(FocusEvent e) {
        if(LoginPage.user.getText().equals("abc@gmail.com")){
            LoginPage.user.setText("");
            LoginPage.user.setForeground(Color.BLACK);
        }
    }
    @Override
    public void focusLost(FocusEvent e) {
        if(LoginPage.user.getText().isEmpty()){
            LoginPage.user.setText("abc@gmail.com");
            LoginPage.user.setForeground(Color.LIGHT_GRAY);
        }
    }
}
public class LoginPage {
    static Connection con;
    static JFrame f = new JFrame("Login Page");
    static JTextField user, pass;
    static JButton submit, clear,forpass,signup;
    static JLabel usern,passn;
    LoginPage(){
        f.getContentPane().setBackground(Color.cyan);
        Font f1 =new Font("serif",1,18);
        usern =new JLabel("User Name:");
        passn =new JLabel("Password:");
        user = new JTextField("abc@gmail.com", 20);
        pass = new JTextField("", 20);
        submit = new JButton("Submit");
        clear = new JButton("Clear");
        forpass =new JButton("Forgot Password");
        signup =new JButton("Sign Up");
        usern.setFont(f1);
        passn.setFont(f1);
        Font f2 =new Font("serif",1,14);
        submit.setFont(f2);
        clear.setFont(f2);
        forpass.setFont(f2);
        signup.setFont(f2);


        f.setLayout(null);
        usern.setBounds(150, 150, 150, 50);
        passn.setBounds(150, 220, 150, 50);
        user.setBounds(300, 150, 250, 50);
        pass.setBounds(300, 220, 250, 50);
        submit.setBounds(150, 360, 100, 50);
        clear.setBounds(250, 360, 100, 50);
        forpass.setBounds(350, 360, 150, 50);
        signup.setBounds(500, 360, 100, 50);
        f.add(usern); f.add(passn);
        f.add(user); f.add(pass);
        f.add(submit); f.add(clear);
        f.add(forpass); f.add(signup);

        Listener1 l = new Listener1();
        submit.addActionListener(l);
        clear.addActionListener(l);
        forpass.addActionListener(l);
        signup.addActionListener(l);

        Listenerf1 l3= new Listenerf1();
        user.addFocusListener(l3);
//        pass.addFocusListener(l3);

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
        new LoginPage();
        try{
            PreparedStatement st2 =LoginPage.con.prepareStatement("Select * from credentials");
            ResultSet rs12 =st2.executeQuery();
            while(rs12.next()){
                System.out.println(rs12.getString(1)+"\t"+rs12.getString(2)+"\t"+rs12.getString(3)+"\t"+rs12.getString(4)+"\t"+rs12.getString(5)+"\t"+rs12.getString(6)+"\t"+rs12.getString(7)+"\t");
            }
        }
        catch(Exception ee){
            System.out.println(ee);
        }
    }
}

