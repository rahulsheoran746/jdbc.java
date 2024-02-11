import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class FirstPage extends JFrame implements ActionListener {
    public static  JLabel label1, label2, label3,label4;
   public static  JButton button1, button2;
    public static Connection connection;

    public FirstPage() {
        super("Photo Frame");

        // Create JLabels with image icons
        ImageIcon icon1 = new ImageIcon("/Users/rahulsheoran/IdeaProjects/jdbc.java/src/apple.jpg");
        label1 = new JLabel(icon1);
        ImageIcon icon2 = new ImageIcon("/Users/rahulsheoran/IdeaProjects/jdbc.java/src/Orange.jpg");
        label2 = new JLabel(icon2);
        ImageIcon icon3 = new ImageIcon("/Users/rahulsheoran/IdeaProjects/jdbc.java/src/mango.jpg");
        label3 = new JLabel(icon3);

        label4 =new JLabel("Choose Apple from below images");

        // Create JButtons
        button1 = new JButton("Button 1");
        button2 = new JButton("Button 2");

        // Add action listeners to buttons
        button1.addActionListener(this);
        button2.addActionListener(this);
        mouseListener1 ml= new mouseListener1();
        label1.addMouseListener(ml);
        label2.addMouseListener(ml);
        label3.addMouseListener(ml);


        // Set layout and add components to frame
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(1, 3));
        panel.add(label1);
        panel.add(label2);
        panel.add(label3);
        add(panel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        add(buttonPanel, BorderLayout.SOUTH);
        Font f =new Font("serif",2,26);
        label4.setFont(f);
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
        if (e.getSource() == button1) {
            // Increment clicks for button 1 in database
            try {
                Statement statement = connection.createStatement();
                String query = "UPDATE buttons SET clicks = clicks + 1 WHERE name = 'button1'";
                statement.executeUpdate(query);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == button2) {
            // Increment clicks for button 2 in database
            try {
                Statement statement = connection.createStatement();
                String query = "UPDATE buttons SET clicks = clicks + 1 WHERE name = 'button2'";
                statement.executeUpdate(query);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        new FirstPage();
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
class mouseListener1 extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if(e.getSource()==FirstPage.label1){
            JOptionPane.showMessageDialog(null,"Your answer is correct");
            JOptionPane.showMessageDialog(null,"Move to next Question");
            new SecondPage();
        }
        else if(e.getSource()==FirstPage.label2){
            JOptionPane.showMessageDialog(null,"Your answer is not correct");
        }
        else if(e.getSource()==FirstPage.label3){
            JOptionPane.showMessageDialog(null,"Your answer is not correct");
        }
    }
}
