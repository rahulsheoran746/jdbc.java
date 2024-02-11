import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SecondPage extends JFrame implements ActionListener {
    public static  JLabel label1, label2, label3,label4;
    public static  JButton button1, button2,button3,button4;
    public static Connection connection;

    public SecondPage() {
        super("Photo Frame");

        // Create JLabels with image icons
    //    ImageIcon icon1 = new ImageIcon("/Users/rahulsheoran/IdeaProjects/jdbc.java/src/apple.jpg");
      //  label1 = new JLabel(icon1);
      //  ImageIcon icon2 = new ImageIcon("/Users/rahulsheoran/IdeaProjects/jdbc.java/src/Orange.jpg");
    //    label2 = new JLabel(icon2);
        Font f1 =new Font("serif",2,200);
        label1 =new JLabel("10");
        label1.setFont(f1);
        label3= new JLabel("+");
        label3.setFont(f1);
        label2 =new JLabel("20");
        label2.setFont(f1);



        label4 =new JLabel("What is the sum of these");

        // Create JButtons
        button1 = new JButton("    30    ");
        button2 = new JButton("    50    ");
        button3 = new JButton("    80    ");
        button4 = new JButton("    45    ");


        // Add action listeners to buttons
        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        button4.addActionListener(this);


        // Set layout and add components to frame
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(1, 3));
        panel.add(label1);
        panel.add(label3);
        panel.add(label2);
        add(panel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.add(button4);
        add(buttonPanel, BorderLayout.SOUTH);
        Font f =new Font("serif",2,40);
        label4.setFont(f);
        button1.setFont(f);
        button2.setFont(f);
        button3.setFont(f);
        button4.setFont(f);
        add(label4,BorderLayout.NORTH);

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
        setLocationRelativeTo(null);

        // Connect to database
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/KE015","root","Roney@123");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
       if(e.getSource()==button1){
           JOptionPane.showMessageDialog(null,"correct answer");
       }
       else if(e.getSource()==button2||e.getSource()==button3||e.getSource()==button4){
           JOptionPane.showMessageDialog(null,"Incorrect answer");
       }
    }
    public static void main(String[] args) {
        new SecondPage();
        try{
            PreparedStatement st2 =connection.prepareStatement("Select * from buttons");
            ResultSet rs12 =st2.executeQuery();
            while(rs12.next()){
                System.out.println(rs12.getInt(1)+"\t"+rs12.getString(2)+"\t"+ rs12.getInt(3));
            }

        }
        catch(Exception ee){}
    }
}

