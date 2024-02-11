import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.*;


class Listerner2 implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            SignUp.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/KE015","root","Roney@123");
        }
        catch(Exception e11) {
            System.out.println(e11);
        }
        if(e.getSource()==SignUp.clear){
            SignUp.usern.setText(null);
            SignUp.firstn.setText(null);
            SignUp.lastn.setText(null);
            SignUp.dobn.setText(null);
            SignUp.phonen.setText(null);
         //   SignUp.gendern.setText(null);
            SignUp.passn.setText(null);
        }
        else if(e.getSource()==SignUp.submit){
            String uname= SignUp.usern.getText();
            String passN=SignUp.passn.getText();
            try {
                PreparedStatement pst = SignUp.con.prepareStatement("SELECT * FROM credentials WHERE username =?");
                pst.setString(1,uname);
                ResultSet rs = pst.executeQuery();
                if(rs.next()){
                    JOptionPane.showMessageDialog(SignUp.f, "Username already exists");
                }
                else{
                    String firstN=SignUp.firstn.getText();
                    String lastN=SignUp.lastn.getText();
                    String dobN=SignUp.dobn.getText();
                    String phoneN=SignUp.phonen.getText();
                    String genderl1= (String) SignUp.genderl1.getSelectedItem();
                    if(firstN.equals("")){
                        JOptionPane.showMessageDialog(SignUp.f,"Enter FirstName");
                    }
                    else if(lastN.equals("")){
                        JOptionPane.showMessageDialog(SignUp.f,"Enter LastName");
                    }
                   else if (validateFields(dobN, phoneN, uname)) {
                        if (genderl1.equals("Select")) {
                            JOptionPane.showMessageDialog(SignUp.f, "Please select any gender");
                        } else if (passN.equals("")) {
                            JOptionPane.showMessageDialog(SignUp.f, "Please enter password");
                        } else {
//                      String query = "INSERT INTO credentials(userName, password, firstName,lastName,dob,gender,phoneNo)VALUES (?,?,?,?,?,?.?)";
                            String query = "INSERT INTO credentials" +
                                    "  (userName, password, firstName,lastName,dob,gender,phoneNo) VALUES " +
                                    " (?, ?, ?, ?, ?, ?, ?);";
                            pst = SignUp.con.prepareStatement(query);
                            pst.setString(1, uname);
                            pst.setString(2, passN);
                            pst.setString(3, firstN);
                            pst.setString(4, lastN);
                            pst.setString(5, dobN);
//                        pst.setString(6, genderN);
                            pst.setString(6, genderl1);
                            pst.setString(7, phoneN);
                        }
                    }
                    if(pst.executeUpdate()>0) {
                        JOptionPane.showMessageDialog(SignUp.f,"Account successfully created");
                      //  SignUp.f.setVisible(false);
//                        SignUp.con.close();
//                        SignUp.f.dispose();
//                        new LoginPage();
                    }
                }
//                    String passN=SignUp.passn.getText();
//                    String firstN=SignUp.firstn.getText();
//                    String lastN=SignUp.lastn.getText();
//                    String dobN=SignUp.dobn.getText();
//                    String phoneN=SignUp.phonen.getText();
//                    String genderN=SignUp.gendern.getText();
//                String msg = "" + firstN;
//                msg += " \n";
//                String query = "INSERT INTO credentials values('" + uname + "','" + passN + "','" + firstN + "','" +
//                       lastN + "','" + dobN + "','" + genderN + "','"+phoneN+"')";
//
//                Statement sta = SignUp.con.createStatement();
//                int x = sta.executeUpdate(query);
//                if (x == 0) {
//                    JOptionPane.showMessageDialog(SignUp.submit, "This id alredy exist");
//                } else {
//                    JOptionPane.showMessageDialog(SignUp.submit,
//                            "Welcome, " + msg + "Your account is sucessfully created");
//                }
            }
            catch(Exception ex){
                System.out.println(ex);
            }
        }
    }
    public  boolean validateFields(String dob, String phone, String email) {
        // Validate the fields using regular expressions
        String dobPattern = "\\d{2}/\\d{2}/\\d{4}";
        String phonePattern = "\\d{10}";
        String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        if(!email.matches(emailPattern)){
            JOptionPane.showMessageDialog(SignUp.f,"Please enter valid email id.");
        }
        else if(!dob.matches(dobPattern)){
            JOptionPane.showMessageDialog(SignUp.f,"Please enter you DOB in given Format");
        }
        else if(!phone.matches(phonePattern)){
            JOptionPane.showMessageDialog(SignUp.f,"Please Enter Mobile number of 10 Digits");
        }
        return dob.matches(dobPattern) && phone.matches(phonePattern) && email.matches(emailPattern);
    }
}
class Listenerf implements FocusListener{
    @Override
    public void focusGained(FocusEvent e) {
        if (SignUp.usern.getText().equals("abc@gmail.com")) {
            SignUp.usern.setText("");
            SignUp.usern.setForeground(Color.BLACK);
        }
       if (SignUp.dobn.getText().equals("MM/DD/YYYY")) {
            SignUp.dobn.setText("");
            SignUp.dobn.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (SignUp.usern.getText().isEmpty()) {
            SignUp.usern.setText("abc@gmail.com");
            SignUp.usern.setForeground(Color.LIGHT_GRAY);
        }
        if (SignUp.dobn.getText().isEmpty()) {
            SignUp.dobn.setText("MM/DD/YYYY");
            SignUp.dobn.setForeground(Color.LIGHT_GRAY);
        }

    }
}
public class SignUp {
    static Connection con;
    static JFrame f = new JFrame("SignUp Page");
    static JTextField firstn,lastn,usern,passn,dobn,phonen,gendern;
    static JButton submit, clear;
    static JLabel firstl,lastl,userl,passl,dobl,phonel,genderl;
    static JComboBox genderl1;
    SignUp(){
        f.getContentPane().setBackground(Color.cyan);
        Font f1 =new Font("serif",1,16);
        userl =new JLabel("User Name :");
        firstl =new JLabel("First Name :");
        lastl =new JLabel("Last Name :");
        dobl =new JLabel("Date of Birth :");
        phonel =new JLabel("Phone No :");
        genderl=new JLabel("Gender :");
        passl =new JLabel("Password :");
        userl.setFont(f1);
        firstl.setFont(f1);
        lastl.setFont(f1);
        dobl.setFont(f1);
        phonel.setFont(f1);
        genderl.setFont(f1);
        passl.setFont(f1);
        f.setLayout(null);
        firstl.setBounds(150,70,100,50);
        lastl.setBounds(150,120,100,50);
        userl.setBounds(150,170,100,50);
        dobl.setBounds(150,220,100,50);
        phonel.setBounds(150,270,100,50);
        genderl.setBounds(150,320,100,50);
        passl.setBounds(150,370,100,50);
        f.add(userl);f.add(firstl);f.add(lastl);f.add(dobl);
        f.add(phonel);f.add(genderl);f.add(passl);

        usern =new JTextField("abc@gmail.com");
        usern.setForeground(Color.GRAY);
        firstn =new JTextField("");
        lastn =new JTextField("");
        dobn =new JTextField("MM/DD/YYYY");
        phonen =new JTextField("");
      //  gendern=new JTextField("");
        String[] g ={"Select","Male","Female","Others"};
        genderl1= new JComboBox(g);
        passn =new JTextField("");
        firstn.setBounds(300,70,200,50);
        lastn.setBounds(300,120,200,50);
        usern.setBounds(300,170,200,50);
        dobn.setBounds(300,220,200,50);
        phonen.setBounds(300,270,200,50);
//        gendern.setBounds(300,320,200,50);
        genderl1.setBounds(300,320,200,50);
        passn.setBounds(300,370,200,50);
        f.add(usern);f.add(firstn);f.add(lastn);f.add(dobn);
        f.add(phonen);f.add(passn);
        f.add(genderl1);


        submit =new JButton("Submit");
        submit.setBounds(200,470,100,50);
        clear =new JButton("Clear");
        clear.setBounds(400,470,100,50);
        f.add(submit);f.add(clear);

        Listerner2 l= new Listerner2();
        clear.addActionListener(l);
        submit.addActionListener(l);

        Listenerf l1 =new Listenerf();
        usern.addFocusListener(l1);
        dobn.addFocusListener(l1);


        f.setSize(800, 800);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setVisible(true);


    }
    public static void main(String[] args) {
        new SignUp();
    }
}
