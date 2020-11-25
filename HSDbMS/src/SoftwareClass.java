import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SoftwareClass  implements ActionListener, ItemListener{

    private JFrame loginFrame;
    private JButton loginButton, cancelLogin;
    private JTextField userTextField;
    private JPasswordField passTextField;

     void createAndShowLoginFrame() {
        //creating login page
        loginFrame= new JFrame("Admin Login");
        JPanel loginPanel= new JPanel();
        loginFrame.setSize(500,400);
        SpringLayout layout= new SpringLayout();
        loginPanel.setLayout(layout);

        JLabel title= new JLabel("<html>Welcome to HSDM Login page! <br> Enter your credentials below </html>");
        JLabel userLabel= new JLabel("Username: ");
        JLabel passLabel= new JLabel("Password: ");
        userTextField= new JTextField() {
            @Override public void setBorder(Border border) {
            }
        };
        passTextField= new JPasswordField() {
            @Override public void setBorder(Border border) {
            }
        };

        userTextField.setPreferredSize(new Dimension(100,15));
        passTextField.setPreferredSize(new Dimension(100,15));

        JLabel background= new JLabel();
        background.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("/res/AllBackground.jpg"))
                    .getScaledInstance(500,400,0)));

        title.setForeground(Color.BLACK);
        title.setBackground(Color.LIGHT_GRAY);
        title.setOpaque(true);
        userLabel.setForeground(Color.BLACK);
        userLabel.setBackground(Color.LIGHT_GRAY);
        userLabel.setOpaque(true);
        passLabel.setForeground(Color.BLACK);
        passLabel.setBackground(Color.LIGHT_GRAY);
        passLabel.setOpaque(true);
        userTextField.setForeground(Color.BLACK);
        userTextField.setBackground(Color.LIGHT_GRAY);
        passTextField.setForeground(Color.BLACK);
        passTextField.setBackground(Color.LIGHT_GRAY);

        layout.putConstraint(SpringLayout.NORTH,title,100,SpringLayout.NORTH,loginPanel);
        layout.putConstraint(SpringLayout.WEST,title,100,SpringLayout.WEST,loginPanel);

        layout.putConstraint(SpringLayout.NORTH,userLabel,50,SpringLayout.SOUTH,title);
        layout.putConstraint(SpringLayout.WEST,userLabel,50,SpringLayout.WEST,loginPanel);

        layout.putConstraint(SpringLayout.NORTH,passLabel,50,SpringLayout.SOUTH,userLabel);
        layout.putConstraint(SpringLayout.WEST,passLabel,50,SpringLayout.WEST,loginPanel);

        layout.putConstraint(SpringLayout.NORTH,userTextField,0,SpringLayout.NORTH,userLabel);
        layout.putConstraint(SpringLayout.WEST,userTextField,20,SpringLayout.EAST,userLabel);

        layout.putConstraint(SpringLayout.NORTH,passTextField,0,SpringLayout.NORTH,passLabel);
        layout.putConstraint(SpringLayout.WEST,passTextField,20,SpringLayout.EAST,passLabel);

        loginButton= new JButton("Login");

        layout.putConstraint(SpringLayout.NORTH,loginButton,70,SpringLayout.SOUTH,passTextField);
        layout.putConstraint(SpringLayout.EAST,loginButton,-10,SpringLayout.EAST,loginPanel);

        cancelLogin= new JButton("Cancel");

        layout.putConstraint(SpringLayout.EAST,cancelLogin,-20,SpringLayout.WEST,loginButton);
        layout.putConstraint(SpringLayout.NORTH,cancelLogin,0,SpringLayout.NORTH,loginButton);

        loginButton.addActionListener(this);
        cancelLogin.addActionListener(this);

        loginButton.setBackground(Color.LIGHT_GRAY);
        cancelLogin.setBackground(Color.LIGHT_GRAY);
        loginButton.setOpaque(true);
        cancelLogin.setOpaque(true);

        loginPanel.add(title);
        loginPanel.add(userLabel);
        loginPanel.add(passLabel);
        loginPanel.add(userTextField);
        loginPanel.add(passTextField);
        loginPanel.add(loginButton);
        loginPanel.add(cancelLogin);
        loginPanel.add(background);

        loginFrame.add(loginPanel);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        loginFrame.setLocation(dim.width/2-loginFrame.getSize().width/2,
                dim.height/2-loginFrame.getSize().height/2);

        createLoadingDialog();

        loginFrame.setUndecorated(true);
        loginFrame.setVisible(true);
    }

    private JDialog load;
    private JLabel loading;

    private void createLoadingDialog() {
        load= new JDialog();
        load.setLayout(new FlowLayout());
        load.setSize(new Dimension(300,150));
        load.setLocationRelativeTo(null);
        loading= new JLabel("Loading.. Please wait..");
        load.add(loading);
        load.setAlwaysOnTop(true);
    }

    private String user,pass;

    private boolean creds= false;

    private void checkCredentials() {
        user= userTextField.getText();
        pass= new String(passTextField.getPassword());

        load.setVisible(true);
        if(!user.isEmpty() && !pass.isEmpty())
        {
            new AnswerWorker().execute();
        }

        else
        {
            userTextField.setText(null);
            passTextField.setText(null);
            loading.setText("Login information cannot be empty");
        }
    }

    private Connection connection;

    private boolean createConnection(String user, String pass) {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/societymanagement?autoReconnect=true&useSSL=false",user,pass);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    class AnswerWorker extends SwingWorker<Integer, Integer> {
        protected Integer doInBackground() throws Exception
        {
            // Do a time-consuming task.
            creds= createConnection(user,pass);
            Thread.sleep(1000);
            return 42;
        }

        protected void done()
        {
            try
            {
                if(creds)
                {
                    load.setVisible(false);
                    loginFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    if(loginFrame.isDisplayable())
                        loginFrame.dispose();
                    createAndStartSoftware();
                }
                else
                {
                    userTextField.setText(null);
                    passTextField.setText(null);
                    loading.setText("Login information is invalid");
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private JFrame frame;
    private JPanel main_panel;
    private CardLayout cd_lt;

    private JMenuItem home,add_n,updt,del,view,logout,exit;
    private JMenuItem admin_cali,admin_reset;
    private JMenuItem tool_softHelp,tool_notices = new JMenuItem("Notice Management"),tool_quickLookup;
    private JMenu file;
    private JMenu tools;

    private double screen_width,screen_height;

    private void createHomeScreen() {
        //Setting the system look an feel
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Cannot set System Look and Feel","Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        //initializing the JFrame and the main JPanel
        frame= new JFrame("Welcome to the HSDM System");
        main_panel= new JPanel(new CardLayout());
        cd_lt=(CardLayout) main_panel.getLayout();


        //creating the home screen on login
        JPanel h_pg;
        JMenuBar menu_b;

        menu_b= new JMenuBar();

        file= new JMenu("File");
        tools= new JMenu("Tools");
        JMenu admin = new JMenu("Admin Tools");

        tool_softHelp= new JMenuItem("Help");
        tool_quickLookup= new JMenuItem("Quick Lookup");

        tool_softHelp.addActionListener(this);
        tool_notices.addActionListener(this);
        tool_quickLookup.addActionListener(this);

        admin_cali= new JMenuItem("Calibrate the software");
        admin_reset= new JMenuItem("Reset Database");

        admin_cali.addActionListener(this);
        admin_reset.addActionListener(this);

        home= new JMenuItem("Home");
        add_n= new JMenuItem("Add a member");
        updt= new JMenuItem("Update details for a member");
        del= new JMenuItem("Remove a member");
        view= new JMenuItem("View Data");
        logout= new JMenuItem("Logout");
        exit= new JMenuItem("Logout and Exit");

        home.addActionListener(this);
        add_n.addActionListener(this);
        updt.addActionListener(this);
        del.addActionListener(this);
        view.addActionListener(this);
        logout.addActionListener(this);
        exit.addActionListener(this);

        file.add(home);
        file.add(add_n);
        file.add(updt);
        file.add(del);
        file.add(view);
        file.add(logout);
        file.add(exit);

        tools.add(tool_notices);
        tools.add(tool_softHelp);
        tools.add(tool_quickLookup);

        admin.add(admin_cali);
        admin.add(admin_reset);

        menu_b.add(file);
        menu_b.add(tools);

        if(user.equalsIgnoreCase("root"))
        {
            menu_b.add(admin);
        }

        frame.setJMenuBar(menu_b);

        //The home screen JPanel
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screen_width = screenSize.getWidth();
        screen_height = screenSize.getHeight();

        JLabel background= new JLabel();
        background.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("/res/MainBackground.jpg"))
                .getScaledInstance((int)screen_width,(int)screen_height,0)));

        h_pg= new JPanel();
        SpringLayout layout= new SpringLayout();
        h_pg.setLayout(layout);
        JLabel wel_hm=new JLabel("<html>Welcome to the HSDM System!<br>Select an operation to Perform..</html>");
        wel_hm.setFont(new Font(wel_hm.getFont().getName(),Font.PLAIN,20));
        wel_hm.setForeground(Color.WHITE);
        layout.putConstraint(SpringLayout.NORTH,wel_hm,(int)screen_height/2-100,SpringLayout.NORTH,h_pg);
        layout.putConstraint(SpringLayout.WEST,wel_hm,(int)screen_width/2-100,SpringLayout.WEST,h_pg);
        h_pg.add(wel_hm);
        h_pg.add(background);

        //Adding home screen JPanel to the main JPanel
        main_panel.add(h_pg,"home");
    }

    private JTextField data_in_wing_txt,data_in_flat_txt,data_in_owner_name_txt,data_in_owner_email_txt;
    private JTextField data_in_owner_phnno_txt,data_in_renter_name_txt,data_in_renter_email_txt,data_in_renter_phnno_txt;

    private JRadioButton data_in_radio_btn_renter_y,data_in_radio_btn_renter_n;
    private JRadioButton data_in_radio_btn_committee_y,data_in_radio_btn_committee_n,data_in_radio_btn_committee_c;

    private JButton data_in_submit,data_up_update;

    private void createDataInputScreen() {
        //create data input JPanel
        JPanel data_in= new JPanel();

        JLabel data_in_lable_wing,data_in_lable_flat,data_in_lable_owner_name,data_in_lable_owner_email;
        JLabel data_in_lable_owner_phnno,data_in_lable_renter_name,data_in_lable_renter_email;
        JLabel data_in_lable_renter_phnno,data_in_lable_committee,data_in_lable_owner_title;
        JLabel data_in_lable_renter_title,data_in_lable_renter_question,data_in_lable_insert_title;

        SpringLayout layout= new SpringLayout();
        data_in.setLayout(layout);

        data_in_lable_insert_title= new JLabel("Insert a new member");
        data_in_lable_wing= new JLabel("Enter the Wing: ");
        data_in_lable_flat= new JLabel("Enter the Flat number: ");
        data_in_lable_owner_title= new JLabel("Enter the Owner's Information: ");
        data_in_lable_owner_name= new JLabel("Enter the Name: ");
        data_in_lable_owner_phnno= new JLabel("Enter the Phone Number: ");
        data_in_lable_owner_email= new JLabel("Enter the Email: ");
        data_in_lable_renter_name= new JLabel("Enter the Name: ");
        data_in_lable_renter_phnno= new JLabel("Enter the Phone Number: ");
        data_in_lable_renter_email= new JLabel("Enter the Email: ");
        data_in_lable_renter_question= new JLabel("Is the flat rented out? (Yes/No)");
        data_in_lable_renter_title=  new JLabel("Enter the renter's information: ");
        data_in_lable_committee= new JLabel("Is the owner a committee member? (Yes/No/Chairman)");

        data_in_lable_insert_title.setForeground(Color.WHITE);
        data_in_lable_wing.setForeground(Color.WHITE);
        data_in_lable_flat.setForeground(Color.WHITE);
        data_in_lable_owner_title.setForeground(Color.WHITE);
        data_in_lable_owner_name.setForeground(Color.WHITE);
        data_in_lable_owner_phnno.setForeground(Color.WHITE);
        data_in_lable_owner_email.setForeground(Color.WHITE);
        data_in_lable_renter_name.setForeground(Color.WHITE);
        data_in_lable_renter_phnno.setForeground(Color.WHITE);
        data_in_lable_renter_email.setForeground(Color.WHITE);
        data_in_lable_renter_question.setForeground(Color.WHITE);
        data_in_lable_renter_title.setForeground(Color.WHITE);
        data_in_lable_committee.setForeground(Color.WHITE);

        data_in_wing_txt= new JTextField();
        data_in_flat_txt= new JTextField();
        data_in_owner_name_txt= new JTextField();
        data_in_owner_phnno_txt= new JTextField();
        data_in_owner_email_txt= new JTextField();
        data_in_renter_name_txt= new JTextField();
        data_in_renter_phnno_txt= new JTextField();
        data_in_renter_email_txt= new JTextField();

        data_in_wing_txt.setPreferredSize(new Dimension(25,20));
        data_in_flat_txt.setPreferredSize(new Dimension(25,20));
        data_in_owner_name_txt.setPreferredSize(new Dimension(200,20));
        data_in_owner_phnno_txt.setPreferredSize(new Dimension(200,20));
        data_in_owner_email_txt.setPreferredSize(new Dimension(200,20));
        data_in_renter_name_txt.setPreferredSize(new Dimension(200,20));
        data_in_renter_phnno_txt.setPreferredSize(new Dimension(200,20));
        data_in_renter_email_txt.setPreferredSize(new Dimension(200,20));

        layout.putConstraint(SpringLayout.NORTH,data_in_lable_insert_title,25,SpringLayout.NORTH,data_in);
        layout.putConstraint(SpringLayout.WEST,data_in_lable_insert_title,25,SpringLayout.WEST,data_in);

        layout.putConstraint(SpringLayout.NORTH,data_in_lable_wing,40,SpringLayout.SOUTH,data_in_lable_insert_title);
        layout.putConstraint(SpringLayout.WEST,data_in_lable_wing,25,SpringLayout.WEST,data_in);
        layout.putConstraint(SpringLayout.WEST,data_in_wing_txt,25,SpringLayout.EAST,data_in_lable_wing);
        layout.putConstraint(SpringLayout.NORTH,data_in_wing_txt,40,SpringLayout.SOUTH,data_in_lable_insert_title);

        layout.putConstraint(SpringLayout.NORTH,data_in_lable_flat,40,SpringLayout.SOUTH,data_in_lable_wing);
        layout.putConstraint(SpringLayout.WEST,data_in_lable_flat,25,SpringLayout.WEST,data_in);
        layout.putConstraint(SpringLayout.WEST,data_in_flat_txt,25,SpringLayout.EAST,data_in_lable_flat);
        layout.putConstraint(SpringLayout.NORTH,data_in_flat_txt,40,SpringLayout.SOUTH,data_in_lable_wing);

        layout.putConstraint(SpringLayout.NORTH,data_in_lable_owner_title,40,SpringLayout.SOUTH,data_in_lable_flat);
        layout.putConstraint(SpringLayout.WEST,data_in_lable_owner_title,25,SpringLayout.WEST,data_in);

        layout.putConstraint(SpringLayout.NORTH,data_in_lable_owner_name,40,SpringLayout.SOUTH,data_in_lable_owner_title);
        layout.putConstraint(SpringLayout.WEST,data_in_lable_owner_name,25,SpringLayout.WEST,data_in);
        layout.putConstraint(SpringLayout.WEST,data_in_owner_name_txt,25,SpringLayout.EAST,data_in_lable_owner_name);
        layout.putConstraint(SpringLayout.NORTH,data_in_owner_name_txt,40,SpringLayout.SOUTH,data_in_lable_owner_title);

        layout.putConstraint(SpringLayout.NORTH,data_in_lable_owner_phnno,40,SpringLayout.SOUTH,data_in_lable_owner_name);
        layout.putConstraint(SpringLayout.WEST,data_in_lable_owner_phnno,25,SpringLayout.WEST,data_in);
        layout.putConstraint(SpringLayout.WEST,data_in_owner_phnno_txt,25,SpringLayout.EAST,data_in_lable_owner_phnno);
        layout.putConstraint(SpringLayout.NORTH,data_in_owner_phnno_txt,40,SpringLayout.SOUTH,data_in_lable_owner_name);

        layout.putConstraint(SpringLayout.NORTH,data_in_lable_owner_email,40,SpringLayout.SOUTH,data_in_lable_owner_phnno);
        layout.putConstraint(SpringLayout.WEST,data_in_lable_owner_email,25,SpringLayout.WEST,data_in);
        layout.putConstraint(SpringLayout.WEST,data_in_owner_email_txt,25,SpringLayout.EAST,data_in_lable_owner_email);
        layout.putConstraint(SpringLayout.NORTH,data_in_owner_email_txt,40,SpringLayout.SOUTH,data_in_lable_owner_phnno);

        layout.putConstraint(SpringLayout.NORTH,data_in_lable_renter_question,40,SpringLayout.SOUTH,data_in_lable_owner_email);
        layout.putConstraint(SpringLayout.WEST,data_in_lable_renter_question,25,SpringLayout.WEST,data_in);

        ButtonGroup rent_btng;
        data_in_radio_btn_renter_y= new JRadioButton("Yes");
        data_in_radio_btn_renter_n= new JRadioButton("No");
        rent_btng= new ButtonGroup();
        rent_btng.add(data_in_radio_btn_renter_y);
        rent_btng.add(data_in_radio_btn_renter_n);

        layout.putConstraint(SpringLayout.WEST,data_in_radio_btn_renter_y,15,SpringLayout.EAST,data_in_lable_renter_question);
        layout.putConstraint(SpringLayout.NORTH,data_in_radio_btn_renter_y,0,SpringLayout.NORTH,data_in_lable_renter_question);

        layout.putConstraint(SpringLayout.WEST,data_in_radio_btn_renter_n,15,SpringLayout.EAST,data_in_radio_btn_renter_y);
        layout.putConstraint(SpringLayout.NORTH,data_in_radio_btn_renter_n,0,SpringLayout.NORTH,data_in_lable_renter_question);

        layout.putConstraint(SpringLayout.NORTH,data_in_lable_renter_title,40,SpringLayout.SOUTH,data_in_lable_renter_question);
        layout.putConstraint(SpringLayout.WEST,data_in_lable_renter_title,25,SpringLayout.WEST,data_in);

        layout.putConstraint(SpringLayout.NORTH,data_in_lable_renter_name,40,SpringLayout.SOUTH,data_in_lable_renter_title);
        layout.putConstraint(SpringLayout.WEST,data_in_lable_renter_name,25,SpringLayout.WEST,data_in);
        layout.putConstraint(SpringLayout.WEST,data_in_renter_name_txt,25,SpringLayout.EAST,data_in_lable_renter_name);
        layout.putConstraint(SpringLayout.NORTH,data_in_renter_name_txt,40,SpringLayout.SOUTH,data_in_lable_renter_title);

        layout.putConstraint(SpringLayout.NORTH,data_in_lable_renter_phnno,40,SpringLayout.SOUTH,data_in_lable_renter_name);
        layout.putConstraint(SpringLayout.WEST,data_in_lable_renter_phnno,25,SpringLayout.WEST,data_in);
        layout.putConstraint(SpringLayout.WEST,data_in_renter_phnno_txt,25,SpringLayout.EAST,data_in_lable_renter_phnno);
        layout.putConstraint(SpringLayout.NORTH,data_in_renter_phnno_txt,40,SpringLayout.SOUTH,data_in_lable_renter_name);

        layout.putConstraint(SpringLayout.NORTH,data_in_lable_renter_email,40,SpringLayout.SOUTH,data_in_lable_renter_phnno);
        layout.putConstraint(SpringLayout.WEST,data_in_lable_renter_email,25,SpringLayout.WEST,data_in);
        layout.putConstraint(SpringLayout.WEST,data_in_renter_email_txt,25,SpringLayout.EAST,data_in_lable_renter_email);
        layout.putConstraint(SpringLayout.NORTH,data_in_renter_email_txt,40,SpringLayout.SOUTH,data_in_lable_renter_phnno);

        layout.putConstraint(SpringLayout.NORTH,data_in_lable_committee,40,SpringLayout.SOUTH,data_in_lable_renter_email);
        layout.putConstraint(SpringLayout.WEST,data_in_lable_committee,25,SpringLayout.WEST,data_in);

        ButtonGroup com_btng;
        data_in_radio_btn_committee_y= new JRadioButton("Yes");
        data_in_radio_btn_committee_n= new JRadioButton("No");
        data_in_radio_btn_committee_c= new JRadioButton("Is the Chairman");
        com_btng= new ButtonGroup();
        com_btng.add(data_in_radio_btn_committee_y);
        com_btng.add(data_in_radio_btn_committee_n);
        com_btng.add(data_in_radio_btn_committee_c);

        layout.putConstraint(SpringLayout.WEST,data_in_radio_btn_committee_y,15,SpringLayout.EAST,data_in_lable_committee);
        layout.putConstraint(SpringLayout.NORTH,data_in_radio_btn_committee_y,0,SpringLayout.NORTH,data_in_lable_committee);

        layout.putConstraint(SpringLayout.WEST,data_in_radio_btn_committee_n,15,SpringLayout.EAST,data_in_radio_btn_committee_y);
        layout.putConstraint(SpringLayout.NORTH,data_in_radio_btn_committee_n,0,SpringLayout.NORTH,data_in_lable_committee);

        layout.putConstraint(SpringLayout.WEST,data_in_radio_btn_committee_c,15,SpringLayout.EAST,data_in_radio_btn_committee_n);
        layout.putConstraint(SpringLayout.NORTH,data_in_radio_btn_committee_c,0,SpringLayout.NORTH,data_in_lable_committee);

        data_in_submit=new JButton("Submit");
        data_up_update=new JButton("Update");
        layout.putConstraint(SpringLayout.NORTH,data_in_submit,10,SpringLayout.SOUTH,data_in_lable_committee);
        layout.putConstraint(SpringLayout.EAST,data_in_submit,-10,SpringLayout.EAST,data_in);
        layout.putConstraint(SpringLayout.NORTH,data_up_update,0,SpringLayout.SOUTH,data_in_submit);
        layout.putConstraint(SpringLayout.EAST,data_up_update,-10,SpringLayout.EAST,data_in);

        data_up_update.setVisible(false);

        data_up_update.addActionListener(this);
        data_in_submit.addActionListener(this);

        data_in_radio_btn_renter_y.addItemListener(this);
        data_in_radio_btn_renter_n.addItemListener(this);
        data_in_radio_btn_committee_y.addItemListener(this);
        data_in_radio_btn_committee_n.addItemListener(this);
        data_in_radio_btn_committee_c.addItemListener(this);

        data_in_renter_name_txt.setEditable(false);
        data_in_renter_phnno_txt.setEditable(false);
        data_in_renter_email_txt.setEditable(false);

        data_in.add(data_up_update);
        data_in.add(data_in_radio_btn_renter_y);
        data_in.add(data_in_radio_btn_renter_n);
        data_in.add(data_in_radio_btn_committee_y);
        data_in.add(data_in_radio_btn_committee_n);
        data_in.add(data_in_radio_btn_committee_c);
        data_in.add(data_in_lable_insert_title);
        data_in.add(data_in_lable_wing);
        data_in.add(data_in_lable_flat);
        data_in.add(data_in_lable_owner_title);
        data_in.add(data_in_lable_owner_name);
        data_in.add(data_in_lable_owner_phnno);
        data_in.add(data_in_lable_owner_email);
        data_in.add(data_in_lable_renter_title);
        data_in.add(data_in_lable_renter_name);
        data_in.add(data_in_lable_renter_phnno);
        data_in.add(data_in_lable_renter_email);
        data_in.add(data_in_lable_renter_question);
        data_in.add(data_in_lable_committee);
        data_in.add(data_in_wing_txt);
        data_in.add(data_in_flat_txt);
        data_in.add(data_in_owner_name_txt);
        data_in.add(data_in_owner_phnno_txt);
        data_in.add(data_in_owner_email_txt);
        data_in.add(data_in_renter_name_txt);
        data_in.add(data_in_renter_phnno_txt);
        data_in.add(data_in_renter_email_txt);
        data_in.add(data_in_submit);

        JLabel background= new JLabel();
        background.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("/res/AllBackground.jpg"))
                .getScaledInstance((int)screen_width,(int)screen_height,0)));
        data_in.add(background);

        main_panel.add(data_in,"data insert");
    }

    private JTextField data_srch_wing_txt,data_srch_flat_txt;
    private JButton data_srch_submit;

    private void createDataSearchScreen() {
        //search for data before update, removal or viewing
        JLabel data_srch_title,data_srch_wing,data_srch_flat;

        JPanel data_srch= new JPanel();
        SpringLayout layout= new SpringLayout();
        data_srch.setLayout(layout);

        data_srch_title= new JLabel("Enter the data:");
        data_srch_wing= new JLabel("Enter the wing number");
        data_srch_flat= new JLabel("Enter the flat number");

        data_srch_title.setForeground(Color.WHITE);
        data_srch_wing.setForeground(Color.WHITE);
        data_srch_flat.setForeground(Color.WHITE);

        data_srch_wing_txt= new JTextField();
        data_srch_flat_txt= new JTextField();

        data_srch_wing_txt.setPreferredSize(new Dimension(25,20));
        data_srch_flat_txt.setPreferredSize(new Dimension(25,20));

        layout.putConstraint(SpringLayout.NORTH,data_srch_title,50,SpringLayout.NORTH,data_srch);
        layout.putConstraint(SpringLayout.WEST,data_srch_title,15,SpringLayout.WEST,data_srch);

        layout.putConstraint(SpringLayout.NORTH,data_srch_wing,45,SpringLayout.SOUTH,data_srch_title);
        layout.putConstraint(SpringLayout.WEST,data_srch_wing,15,SpringLayout.WEST,data_srch);
        layout.putConstraint(SpringLayout.WEST,data_srch_wing_txt,15,SpringLayout.EAST,data_srch_wing);
        layout.putConstraint(SpringLayout.NORTH,data_srch_wing_txt,45,SpringLayout.SOUTH,data_srch_title);

        layout.putConstraint(SpringLayout.NORTH,data_srch_flat,45,SpringLayout.SOUTH,data_srch_wing);
        layout.putConstraint(SpringLayout.WEST,data_srch_flat,15,SpringLayout.WEST,data_srch);
        layout.putConstraint(SpringLayout.WEST,data_srch_flat_txt,15,SpringLayout.EAST,data_srch_flat);
        layout.putConstraint(SpringLayout.NORTH,data_srch_flat_txt,45,SpringLayout.SOUTH,data_srch_wing);

        data_srch_submit= new JButton("Submit");
        layout.putConstraint(SpringLayout.NORTH,data_srch_submit,45,SpringLayout.SOUTH,data_srch_flat);
        layout.putConstraint(SpringLayout.WEST,data_srch_submit,15,SpringLayout.WEST,data_srch);

        data_srch_submit.addActionListener(this);

        data_srch.add(data_srch_title);
        data_srch.add(data_srch_wing);
        data_srch.add(data_srch_wing_txt);
        data_srch.add(data_srch_flat);
        data_srch.add(data_srch_flat_txt);
        data_srch.add(data_srch_submit);

        JLabel background= new JLabel();
        background.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("/res/AllBackground.jpg"))
                .getScaledInstance((int)screen_width,(int)screen_height,0)));
        data_srch.add(background);

        main_panel.add(data_srch,"data search");
    }

    private JTextField cali_screen_floor_txt,cali_screen_flats_floor_txt,cali_screen_wing_txt;
    private JButton cali_screen_submit;

    private void createCalibrationScreen() {
        //create the calibration screen
        JLabel cali_screen_title,cali_screen_floors,cali_screen_flats_floor,cali_screen_wing;

        JPanel cali_screen= new JPanel();
        SpringLayout layout= new SpringLayout();
        cali_screen.setLayout(layout);

        cali_screen_title= new JLabel("Enter the calibration data!");
        cali_screen_floors= new JLabel("Number of Floors:");
        cali_screen_flats_floor= new JLabel("Number of flats per floor:");
        cali_screen_wing= new JLabel("Number of buildings/wings: ");

        cali_screen_title.setForeground(Color.WHITE);
        cali_screen_floors.setForeground(Color.WHITE);
        cali_screen_flats_floor.setForeground(Color.WHITE);
        cali_screen_wing.setForeground(Color.WHITE);

        cali_screen_floor_txt= new JTextField();
        cali_screen_flats_floor_txt= new JTextField();
        cali_screen_wing_txt= new JTextField();

        cali_screen_floor_txt.setPreferredSize(new Dimension(100,20));
        cali_screen_flats_floor_txt.setPreferredSize(new Dimension(100,20));
        cali_screen_wing_txt.setPreferredSize(new Dimension(100,20));

        layout.putConstraint(SpringLayout.NORTH,cali_screen_title,15,SpringLayout.NORTH,cali_screen);
        layout.putConstraint(SpringLayout.WEST,cali_screen_title,15,SpringLayout.WEST,cali_screen);

        layout.putConstraint(SpringLayout.NORTH,cali_screen_floors,15,SpringLayout.SOUTH,cali_screen_title);
        layout.putConstraint(SpringLayout.WEST,cali_screen_floors,15,SpringLayout.WEST,cali_screen);
        layout.putConstraint(SpringLayout.WEST,cali_screen_floor_txt,10,SpringLayout.EAST,cali_screen_floors);
        layout.putConstraint(SpringLayout.NORTH,cali_screen_floor_txt,0,SpringLayout.NORTH,cali_screen_floors);

        layout.putConstraint(SpringLayout.NORTH,cali_screen_flats_floor,15,SpringLayout.SOUTH,cali_screen_floors);
        layout.putConstraint(SpringLayout.WEST,cali_screen_flats_floor,15,SpringLayout.WEST,cali_screen);
        layout.putConstraint(SpringLayout.WEST,cali_screen_flats_floor_txt,10,SpringLayout.EAST,cali_screen_flats_floor);
        layout.putConstraint(SpringLayout.NORTH,cali_screen_flats_floor_txt,0,SpringLayout.NORTH,cali_screen_flats_floor);

        layout.putConstraint(SpringLayout.NORTH,cali_screen_wing,15,SpringLayout.SOUTH,cali_screen_flats_floor);
        layout.putConstraint(SpringLayout.WEST,cali_screen_wing,15,SpringLayout.WEST,cali_screen);
        layout.putConstraint(SpringLayout.WEST,cali_screen_wing_txt,10,SpringLayout.EAST,cali_screen_wing);
        layout.putConstraint(SpringLayout.NORTH,cali_screen_wing_txt,0,SpringLayout.NORTH,cali_screen_wing);


        cali_screen_submit= new JButton("Done!");
        cali_screen_submit.addActionListener(this);
        layout.putConstraint(SpringLayout.NORTH,cali_screen_submit,20,SpringLayout.SOUTH,cali_screen_wing_txt);
        layout.putConstraint(SpringLayout.WEST,cali_screen_submit,0,SpringLayout.WEST,cali_screen_wing_txt);

        cali_screen.add(cali_screen_title);
        cali_screen.add(cali_screen_floors);
        cali_screen.add(cali_screen_floor_txt);
        cali_screen.add(cali_screen_flats_floor);
        cali_screen.add(cali_screen_flats_floor_txt);
        cali_screen.add(cali_screen_submit);
        cali_screen.add(cali_screen_wing);
        cali_screen.add(cali_screen_wing_txt);

        JLabel background= new JLabel();
        background.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("/res/AllBackground.jpg"))
                .getScaledInstance((int)screen_width,(int)screen_height,0)));
        cali_screen.add(background);

        main_panel.add(cali_screen,"calibrate");
    }

    private void createHelpPage() {
        JPanel helpPage= new JPanel();
        SpringLayout layout= new SpringLayout();

        helpPage.setLayout(layout);

        JLabel background= new JLabel();
        background.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("/res/AllBackground.jpg"))
                .getScaledInstance((int)screen_width,(int)screen_height,0)));
        helpPage.add(background);

        main_panel.add(helpPage,"Help page");
    }

    private JTable table1,table2,table3;

    private void createDisplayPage() {
        JPanel display= new JPanel();
        JTabbedPane tabbedPane= new JTabbedPane();

        table1= new JTable();
        table2= new JTable();
        table3= new JTable();

        JPanel panel_own= new JPanel();
        JPanel panel_rent= new JPanel();
        JPanel panel_com= new JPanel();

        Dimension dim= new Dimension(new Dimension((int)screen_width-100,(int)screen_height-100));

        JScrollPane pane1,pane2,pane3;

        table1.setPreferredScrollableViewportSize(dim);
        table2.setPreferredScrollableViewportSize(dim);
        table3.setPreferredScrollableViewportSize(dim);

        pane1= new JScrollPane(table1);
        pane2= new JScrollPane(table2);
        pane3= new JScrollPane(table3);

        pane1.setPreferredSize(dim);
        pane2.setPreferredSize(dim);
        pane3.setPreferredSize(dim);

        panel_own.add(pane1);
        panel_rent.add(pane2);
        panel_com.add(pane3);

        panel_own.setPreferredSize(dim);
        panel_rent.setPreferredSize(dim);
        panel_com.setPreferredSize(dim);

        tabbedPane.add("Owners Info",panel_own);
        tabbedPane.add("Renters Info",panel_rent);
        tabbedPane.add("Committee",panel_com);

        tabbedPane.setPreferredSize(dim);

        display.add(tabbedPane);

        main_panel.add(display,"display page");
    }

    private JButton newNoticeSubmit, remNoticeSubmit;
    private JEditorPane enterData;
    private JTextField enterTitle,remField;
    private JSpinner issueDateIN,dueDateIN;
    private JTable viewNoticesTable;

    private void createNoticeMgmtPage() {
        JPanel notice_panel= new JPanel();
        JTabbedPane tabbedPane= new JTabbedPane();

        JPanel addNotice= new JPanel();
        JPanel removeNotice= new JPanel();
        JPanel viewNotices= new JPanel();

        //add notice panel
        JLabel header= new JLabel("Add a Notice");
        JLabel title= new JLabel("<html>Enter the title of the notice<br>(not more than 50 characters)</html>");
        JLabel issueDate= new JLabel("Select the date of issue");
        JLabel dueDate= new JLabel("Select the due date");
        JLabel content= new JLabel("Enter the content of the notice");
        enterData= new JEditorPane();
        enterTitle= new JTextField();
        issueDateIN= new JSpinner();
        dueDateIN= new JSpinner();

        Calendar calendar= Calendar.getInstance();
        Date todayDate= calendar.getTime();
        calendar.add(Calendar.YEAR,-100);
        Date earliestDate= calendar.getTime();

        SpinnerDateModel model= new SpinnerDateModel(todayDate,earliestDate,todayDate,Calendar.YEAR);
        issueDateIN.setModel(model);

        calendar.add(Calendar.YEAR,200);
        Date latestDate= calendar.getTime();

        SpinnerDateModel model1= new SpinnerDateModel(todayDate,earliestDate,latestDate,Calendar.DATE);
        dueDateIN.setModel(model1);

        newNoticeSubmit= new JButton("Add the notice");

        SpringLayout layout_insert= new SpringLayout();
        addNotice.setLayout(layout_insert);

        layout_insert.putConstraint(SpringLayout.NORTH,header,50,SpringLayout.NORTH,addNotice);
        layout_insert.putConstraint(SpringLayout.WEST,header,50,SpringLayout.WEST,addNotice);

        layout_insert.putConstraint(SpringLayout.NORTH,title,15,SpringLayout.SOUTH,header);
        layout_insert.putConstraint(SpringLayout.WEST,title,20,SpringLayout.WEST,addNotice);
        layout_insert.putConstraint(SpringLayout.NORTH,enterTitle,0,SpringLayout.NORTH,title);
        layout_insert.putConstraint(SpringLayout.WEST,enterTitle,10,SpringLayout.EAST,title);

        layout_insert.putConstraint(SpringLayout.NORTH,issueDate,15,SpringLayout.SOUTH,title);
        layout_insert.putConstraint(SpringLayout.WEST,issueDate,20,SpringLayout.WEST,addNotice);
        layout_insert.putConstraint(SpringLayout.NORTH,issueDateIN,0,SpringLayout.NORTH,issueDate);
        layout_insert.putConstraint(SpringLayout.WEST,issueDateIN,10,SpringLayout.EAST,issueDate);

        layout_insert.putConstraint(SpringLayout.NORTH,dueDate,15,SpringLayout.SOUTH,issueDate);
        layout_insert.putConstraint(SpringLayout.WEST,dueDate,20,SpringLayout.WEST,addNotice);
        layout_insert.putConstraint(SpringLayout.NORTH,dueDateIN,0,SpringLayout.NORTH,dueDate);
        layout_insert.putConstraint(SpringLayout.WEST,dueDateIN,10,SpringLayout.EAST,dueDate);

        layout_insert.putConstraint(SpringLayout.NORTH,content,15,SpringLayout.SOUTH,dueDate);
        layout_insert.putConstraint(SpringLayout.WEST,content,20,SpringLayout.WEST,addNotice);
        layout_insert.putConstraint(SpringLayout.NORTH,enterData,0,SpringLayout.NORTH,content);
        layout_insert.putConstraint(SpringLayout.WEST,enterData,10,SpringLayout.EAST,content);

        layout_insert.putConstraint(SpringLayout.SOUTH,newNoticeSubmit,-5,SpringLayout.SOUTH,addNotice);
        layout_insert.putConstraint(SpringLayout.WEST,newNoticeSubmit,15,SpringLayout.WEST,addNotice);

        enterTitle.setPreferredSize(new Dimension(200,20));
        issueDateIN.setPreferredSize(new Dimension(200,20));
        dueDateIN.setPreferredSize(new Dimension(200,20));
        enterData.setPreferredSize(new Dimension(600,200));

        newNoticeSubmit.addActionListener(this);

        addNotice.add(header);
        addNotice.add(title);
        addNotice.add(enterTitle);
        addNotice.add(issueDate);
        addNotice.add(dueDate);
        addNotice.add(issueDateIN);
        addNotice.add(dueDateIN);
        addNotice.add(content);
        addNotice.add(enterData);
        addNotice.add(newNoticeSubmit);

        Dimension dim= new Dimension(new Dimension((int)screen_width-100,(int)screen_height-100));
        addNotice.setPreferredSize(dim);

        tabbedPane.add("New Notice",addNotice);

        //remove notice panel
        JLabel remTitle= new JLabel("Enter the index of notice to remove!");
        remField= new JTextField();

        remNoticeSubmit= new JButton("Remove");
        remNoticeSubmit.addActionListener(this);

        SpringLayout layout_remove= new SpringLayout();
        removeNotice.setLayout(layout_remove);

        layout_remove.putConstraint(SpringLayout.NORTH,remTitle,80,SpringLayout.NORTH,removeNotice);
        layout_remove.putConstraint(SpringLayout.WEST,remTitle,30,SpringLayout.WEST,removeNotice);
        layout_remove.putConstraint(SpringLayout.WEST,remField,20,SpringLayout.EAST,remTitle);
        layout_remove.putConstraint(SpringLayout.NORTH,remField,0,SpringLayout.NORTH,remTitle);
        layout_remove.putConstraint(SpringLayout.NORTH,remNoticeSubmit,50,SpringLayout.SOUTH,remTitle);
        layout_remove.putConstraint(SpringLayout.WEST,remNoticeSubmit,0,SpringLayout.WEST,remTitle);

        remField.setPreferredSize(new Dimension(100,20));

        removeNotice.add(remTitle);
        removeNotice.add(remField);
        removeNotice.add(remNoticeSubmit);

        removeNotice.setPreferredSize(dim);

        tabbedPane.add("Remove Notice",removeNotice);

        //View notices
        JScrollPane scrollPane;

        viewNoticesTable= new JTable();
        scrollPane= new JScrollPane(viewNoticesTable);

        scrollPane.setPreferredSize(dim);
        viewNoticesTable.setPreferredScrollableViewportSize(dim);
        tabbedPane.setPreferredSize(dim);
        viewNotices.setPreferredSize(dim);

        viewNotices.add(scrollPane);
        tabbedPane.add("View Notices",viewNotices);

        notice_panel.add(tabbedPane);
        main_panel.add(notice_panel,"notices");
    }

    private JTextField nameEnter;
    private JLabel nameLabel;
    private JButton lookupSubmit;

    private void createQuickLookupPage()
    {
        JPanel lookupPanel = new JPanel();
        SpringLayout layout= new SpringLayout();
        lookupPanel.setLayout(layout);

        nameLabel= new JLabel("Enter the name to lookup in the database");
        nameEnter= new JTextField();
        nameEnter.setPreferredSize(new Dimension(200,20));

        lookupSubmit= new JButton("Search");
        lookupSubmit.addActionListener(this);

        layout.putConstraint(SpringLayout.NORTH,nameLabel,30,SpringLayout.NORTH, lookupPanel);
        layout.putConstraint(SpringLayout.WEST,nameLabel,15,SpringLayout.WEST, lookupPanel);

        layout.putConstraint(SpringLayout.NORTH,nameEnter,0,SpringLayout.NORTH,nameLabel);
        layout.putConstraint(SpringLayout.WEST,nameEnter,10,SpringLayout.EAST,nameLabel);

        layout.putConstraint(SpringLayout.NORTH,lookupSubmit,15,SpringLayout.SOUTH,nameLabel);
        layout.putConstraint(SpringLayout.WEST,lookupSubmit,15,SpringLayout.WEST, lookupPanel);

        lookupPanel.add(nameLabel);
        lookupPanel.add(nameEnter);
        lookupPanel.add(lookupSubmit);

        main_panel.add("lookup", lookupPanel);
    }

    private void createScreens() {
        createHomeScreen();
        createDataInputScreen();
        createDataSearchScreen();
        createCalibrationScreen();
        createHelpPage();
        createDisplayPage();
        createNoticeMgmtPage();
        createQuickLookupPage();

        frame.add(main_panel);
    }

    private void checkCalibration() {
        try {
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables("societymanagement", null, "%", null);
            if (tables.next()) {
                String check_pid_table = "select * from property_ids";
                PreparedStatement statement = connection.prepareStatement(check_pid_table);
                ResultSet rs = statement.executeQuery();
                if (!rs.next()) {
                    add_n.setVisible(false);
                    updt.setVisible(false);
                    del.setVisible(false);
                    view.setVisible(false);
                    tools.setVisible(false);
                    admin_reset.setVisible(false);
                    JOptionPane.showMessageDialog(null, "Calibrate first and then use!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    admin_cali.setEnabled(false);
                    file.setVisible(true);
                    view.setVisible(true);
                    tools.setVisible(true);
                    admin_reset.setVisible(true);
                }
            }
            else
                {
                String createPidSQL, createOwnersSQL, createRentersSQL, createNoticesSQL;
                createPidSQL = "create table property_ids(pid int auto_increment primary key,w_no char(2),f_no int(4));";
                createOwnersSQL = "create table owner_info(pid int,name varchar(50),phno varchar(15),email varchar(50),rent char(2),com char(2), foreign key(pid) references property_ids(pid));";
                createRentersSQL = "create table renter_info(pid int,name varchar(50),phno varchar(15),email varchar(50),foreign key(pid) references property_ids(pid));";
                createNoticesSQL="create table notices(noticeno int auto_increment primary key, issuedate datetime, duedate datetime, title varchar(50), context text);";

                PreparedStatement createPid, createOwners, createRenters, createNotices;
                createPid = connection.prepareStatement(createPidSQL);
                createOwners = connection.prepareStatement(createOwnersSQL);
                createRenters = connection.prepareStatement(createRentersSQL);
                createNotices= connection.prepareStatement(createNoticesSQL);

                createPid.executeUpdate();
                createOwners.executeUpdate();
                createRenters.executeUpdate();
                createNotices.executeUpdate();

                checkCalibration();
                }
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createAndStartSoftware() {
        createScreens();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        checkCalibration();
        frame.setVisible(true);
    }

    private String get_insert_owner_name,get_insert_owner_phn_no,get_insert_owner_email,get_insert_rent_name="";
    private String get_insert_rent_phn_no="",get_insert_rent_email="",get_insert_flat,get_insert_wing;
    private boolean get_insert_rental;
    private char get_insert_committee;

    private boolean getInsertTexts() {
        int flag=0;
        get_insert_rental= data_in_radio_btn_renter_y.isSelected();

        if(data_in_radio_btn_committee_c.isSelected())
            get_insert_committee='c';
        else if(data_in_radio_btn_committee_y.isSelected())
            get_insert_committee='y';
        else if(data_in_radio_btn_committee_n.isSelected())
            get_insert_committee='n';

        get_insert_owner_name= data_in_owner_name_txt.getText();
        get_insert_owner_phn_no= data_in_owner_phnno_txt.getText();
        get_insert_owner_email= data_in_owner_email_txt.getText();

        if(get_insert_rental)
        {
            get_insert_rent_name= data_in_renter_name_txt.getText();
            get_insert_rent_phn_no= data_in_renter_phnno_txt.getText();
            get_insert_rent_email= data_in_renter_email_txt.getText();

            try
            {
                Long.parseLong(get_insert_rent_phn_no);

                if(!get_insert_rent_email.endsWith(".com") || !get_insert_rent_email.contains("@"))
                    throw new Exception("Email is invalid");
            }
            catch (Exception e)
            {
                flag=1;
                JOptionPane.showMessageDialog(null,e.getMessage(),"Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        get_insert_flat= data_in_flat_txt.getText();
        get_insert_wing= data_in_wing_txt.getText();

        try
        {
            Long.parseLong(get_insert_flat);
            Long.parseLong(get_insert_owner_phn_no);

            if(!get_insert_owner_email.endsWith(".com") || !get_insert_owner_email.contains("@"))
                throw new Exception("Email is invalid");
        }
        catch (Exception e)
        {
            flag=1;
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        return flag == 0;
    }

    private String get_search_flat,get_search_wing;

    private boolean getSearchTexts() {
        get_search_flat= data_srch_flat_txt.getText();
        get_search_wing= data_srch_wing_txt.getText();

        int flag=0;

        try
        {
            Integer.parseInt(get_search_flat);
        }
        catch (Exception e)
        {
            flag=1;
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        return flag == 0;
    }

    private void clearAllScreens() {

        data_in_wing_txt.setText(null);
        data_in_flat_txt.setText(null);
        data_in_owner_name_txt.setText(null);
        data_in_owner_phnno_txt.setText(null);
        data_in_owner_email_txt.setText(null);
        data_in_renter_name_txt.setText(null);
        data_in_renter_phnno_txt.setText(null);
        data_in_renter_email_txt.setText(null);
        data_srch_wing_txt.setText(null);
        data_srch_flat_txt.setText(null);

    }

    private String choice="";

    private void actionToPerform(String action) {
        if(action.equalsIgnoreCase("add member"))
        {
            addMember();
        }
        else if(action.equalsIgnoreCase("update member"))
        {
            updateMember();
        }
        else if(action.equalsIgnoreCase("remove member"))
        {
            deleteMember();
        }
        else if(action.equalsIgnoreCase("perform update"))
        {
            performUpdate();
        }
    }

    private void refreshSoftware() {
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        if(frame.isDisplayable())
            frame.dispose();
        createAndStartSoftware();
    }

    private void closeAndExit() {
        try
        {
            connection.close();
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            if(frame.isDisplayable())
                frame.dispose();
            System.exit(0);
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void logoutUser() {
        try
        {
            connection.close();
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            if(frame.isDisplayable())
                frame.dispose();
            createAndShowLoginFrame();
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage(),
                    "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //listening for selected events
        if(e.getSource()==loginButton)
        {
            checkCredentials();
        }
        if(e.getSource()==cancelLogin)
        {
            if(loginFrame.isDisplayable())
                loginFrame.dispose();
            System.exit(0);
        }

        if(e.getSource()==home)
        {
            clearAllScreens();
            cd_lt.show(main_panel, "home");
        }
        if(e.getSource()==add_n)
        {
            choice="add member";
            data_in_submit.setVisible(true);
            data_up_update.setVisible(false);
            cd_lt.show(main_panel, "data insert");
        }
        if(e.getSource()==updt)
        {
            choice="update member";
            data_up_update.setVisible(true);
            data_in_submit.setVisible(false);
            cd_lt.show(main_panel, "data search");
        }
        if(e.getSource()==del)
        {
            choice="remove member";
            cd_lt.show(main_panel, "data search");
        }
        if(e.getSource()==view)
        {
            populateDisplay();
        }
        if(e.getSource()==logout)
        {
            logoutUser();
        }
        if(e.getSource()==exit)
        {
            int response= JOptionPane.showConfirmDialog(main_panel,"Do you want to exit?","Exit?",
                    JOptionPane.YES_NO_OPTION);
            if(response==JOptionPane.YES_OPTION)
            {
                closeAndExit();
            }
        }
        if(e.getSource()==tool_notices)
        {
            showNotices();
            cd_lt.show(main_panel,"notices");
        }
        if(e.getSource()==tool_softHelp)
        {
            cd_lt.show(main_panel,"Help page");
        }
        if(e.getSource()==tool_quickLookup)
        {
            cd_lt.show(main_panel,"lookup");
        }
        if(e.getSource()==admin_cali)
        {
            cd_lt.show(main_panel,"calibrate");
        }
        if(e.getSource()==admin_reset)
        {
            int response= JOptionPane.showConfirmDialog(null,"Are you sure?","RESET DATA",
                    JOptionPane.YES_NO_OPTION);
            if(response==JOptionPane.YES_OPTION)
            {
                resetTables();
            }
        }

        if(e.getSource()==data_in_submit)
        {
            actionToPerform(choice);
        }
        if(e.getSource()==data_srch_submit && choice.equalsIgnoreCase("update member"))
        {
            actionToPerform(choice);
        }
        if(e.getSource()==data_srch_submit && choice.equalsIgnoreCase("remove member"))
        {
            actionToPerform(choice);
        }
        if(e.getSource()==data_in_submit && choice.equalsIgnoreCase("update member"))
        {
            actionToPerform("perform update");
        }
        if(e.getSource()==cali_screen_submit)
        {
            Calibrate();
        }
        if(e.getSource()==data_up_update)
        {
            actionToPerform("perform update");
        }

        if(e.getSource()==newNoticeSubmit)
        {
            addNotice();
        }
        if(e.getSource()==remNoticeSubmit)
        {
            removeNotice();
        }
        if(e.getSource()==lookupSubmit)
        {
            if(lookupSubmit.getText().equalsIgnoreCase("search"))
            {
                searchInDatabase();
            }
            else if(lookupSubmit.getText().equalsIgnoreCase("go again!"))
            {
                resetSearch();
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            if(e.getItem()==data_in_radio_btn_renter_y)
            {
                data_in_renter_name_txt.setEditable(true);
                data_in_renter_phnno_txt.setEditable(true);
                data_in_renter_email_txt.setEditable(true);
                data_in_radio_btn_committee_y.setEnabled(false);
                data_in_radio_btn_committee_c.setEnabled(false);
                data_in_radio_btn_committee_n.setEnabled(false);
                data_in_radio_btn_committee_n.setSelected(true);
            }
            else if(e.getItem()==data_in_radio_btn_renter_n)
            {
                data_in_renter_name_txt.setText("");
                data_in_renter_phnno_txt.setText("");
                data_in_renter_email_txt.setText("");
                data_in_renter_name_txt.setEditable(false);
                data_in_renter_phnno_txt.setEditable(false);
                data_in_renter_email_txt.setEditable(false);
                data_in_radio_btn_committee_y.setEnabled(true);
                data_in_radio_btn_committee_c.setEnabled(true);
                data_in_radio_btn_committee_n.setEnabled(true);
            }
        }
    }

    private void addMember() {
        boolean check= getInsertTexts();
        if(check)
        {
            try
            {
                int p_id=0;
                String get_id_sql="select pid from property_ids where w_no=? and f_no=?";
                PreparedStatement get_id= connection.prepareStatement(get_id_sql);
                get_id.setString(1,get_insert_wing);
                get_id.setInt(2,Integer.parseInt(get_insert_flat));
                ResultSet rs= get_id.executeQuery();
                while(rs.next())
                    p_id= rs.getInt("pid");
                rs.close();
                if(p_id==0)
                {
                    JOptionPane.showMessageDialog(null,"Cannot find registered flat","Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    String owner_sql="insert into owner_info values(?,?,?,?,?,?)";
                    String renter_sql="insert into renter_info values(?,?,?,?)";
                    PreparedStatement owner_entry= connection.prepareStatement(owner_sql);
                    PreparedStatement renter_entry= connection.prepareStatement(renter_sql);

                    owner_entry.setInt(1,p_id);
                    owner_entry.setString(2,get_insert_owner_name);
                    owner_entry.setString(3,get_insert_owner_phn_no);
                    owner_entry.setString(4,get_insert_owner_email);
                    if(get_insert_rental)
                    {
                        owner_entry.setString(5,"y");
                        renter_entry.setInt(1,p_id);
                        renter_entry.setString(2,get_insert_rent_name);
                        renter_entry.setString(3,get_insert_rent_phn_no);
                        renter_entry.setString(4,get_insert_rent_email);
                        renter_entry.executeUpdate();
                    }
                    else
                        owner_entry.setString(5,"n");
                    if(get_insert_committee=='y')
                        owner_entry.setString(6,"y");
                    else if(get_insert_committee=='n')
                        owner_entry.setString(6,"n");
                    else if(get_insert_committee=='c')
                        owner_entry.setString(6,"c");
                    owner_entry.executeUpdate();
                }

                JOptionPane.showMessageDialog(null,"New member added!");
                clearAllScreens();
                cd_lt.show(main_panel,"home");
            }
            catch(SQLException e)
            {
                JOptionPane.showMessageDialog(null,e.getMessage(),"Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private String owner_name="",owner_ph="",owner_email="",owner_rent="",owner_committee="";
    private String renter_name="",renter_ph="",renter_email="";

    private int check_pid;

    private void updateMember() {
        boolean check= getSearchTexts();

        if(check)
        {
            int stop=0;

            String flat_check = get_search_flat;
            String wing_check = get_search_wing;

            cd_lt.show(main_panel,"data insert");
            data_in_submit.setVisible(false);
            data_up_update.setVisible(true);

            int p_id=0;

            try
            {
                String getP_idSQL="select pid from property_ids where f_no=? and w_no=?";
                PreparedStatement getP_id= connection.prepareStatement(getP_idSQL);
                getP_id.setInt(1,Integer.parseInt(flat_check));
                getP_id.setString(2, wing_check);
                ResultSet resultSet= getP_id.executeQuery();
                if(resultSet.next())
                    p_id=resultSet.getInt("pid");
                resultSet.close();
                if(p_id==0)
                {
                    JOptionPane.showMessageDialog(null,"Cannot find registered flat","Error",
                            JOptionPane.ERROR_MESSAGE);

                }
                else
                {
                    String getOwnDataSQL="select * from owner_info where pid=?";
                    PreparedStatement getOwnData= connection.prepareStatement(getOwnDataSQL);
                    getOwnData.setInt(1,p_id);
                    ResultSet resultSet1= getOwnData.executeQuery();

                    if(resultSet1.next())
                    {
                        owner_name=resultSet1.getString("name");
                        owner_ph=resultSet1.getString("phno");
                        owner_email=resultSet1.getString("email");
                        owner_rent=resultSet1.getString("rent");
                        owner_committee=resultSet1.getString("com");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"Member doesn't exist",
                                "Error",JOptionPane.ERROR_MESSAGE);
                        stop=1;
                        clearAllScreens();
                        cd_lt.show(main_panel,"home");
                    }

                    if(stop==0)
                    {
                        data_in_wing_txt.setText(wing_check);
                        data_in_flat_txt.setText(flat_check);

                        data_in_owner_name_txt.setText(owner_name);
                        data_in_owner_phnno_txt.setText(owner_ph);
                        data_in_owner_email_txt.setText(owner_email);

                        if(owner_rent.equalsIgnoreCase("y"))
                            data_in_radio_btn_renter_y.setSelected(true);
                        else
                            data_in_radio_btn_renter_n.setSelected(true);

                        if(owner_committee.equalsIgnoreCase("c"))
                            data_in_radio_btn_committee_c.setSelected(true);
                        else if(owner_committee.equalsIgnoreCase("y"))
                            data_in_radio_btn_committee_y.setSelected(true);
                        else
                            data_in_radio_btn_committee_n.setSelected(true);

                        if(owner_rent.equalsIgnoreCase("y"))
                        {
                            String getRentDataSQL="select * from renter_info where pid=?";
                            PreparedStatement getRentData= connection.prepareStatement(getRentDataSQL);
                            getRentData.setInt(1,p_id);
                            ResultSet resultSet2= getRentData.executeQuery();

                            if(resultSet2.next())
                            {
                                renter_name=resultSet2.getString("name");
                                renter_ph=resultSet2.getString("phno");
                                renter_email=resultSet2.getString("email");
                            }

                            data_in_renter_name_txt.setText(renter_name);
                            data_in_renter_phnno_txt.setText(renter_ph);
                            data_in_renter_email_txt.setText(renter_email);

                            resultSet2.close();
                        }
                        resultSet1.close();

                        check_pid=p_id;
                    }
                }
            }
            catch (SQLException e)
            {
                JOptionPane.showMessageDialog(null,e.getMessage(),"Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void performUpdate() {

        int flag=0;

        getInsertTexts();

        String old_flat="", old_wing="";
        String old_owner_name="",old_owner_phno="",old_owner_email="",old_owner_com="",old_owner_rent="";
        String old_renter_name="",old_renter_phno="",old_renter_email="";
        boolean check_rental=false;

        try
        {
            String fetch_detsSQL= "select * from property_ids where pid=?";
            PreparedStatement fetchDets= connection.prepareStatement(fetch_detsSQL);
            fetchDets.setInt(1,check_pid);
            ResultSet rs1= fetchDets.executeQuery();

            if(rs1.next())
            {
                old_flat= rs1.getString("f_no");
                old_wing= rs1.getString("w_no");
            }

            String fetch_old_ownersSQL= "select * from owner_info where pid=?";
            PreparedStatement fetchOwners= connection.prepareStatement(fetch_old_ownersSQL);
            fetchOwners.setInt(1,check_pid);
            ResultSet rs2= fetchOwners.executeQuery();

            if(rs2.next())
            {
                old_owner_name= rs2.getString("name");
                old_owner_phno= rs2.getString("phno");
                old_owner_email= rs2.getString("email");
                old_owner_com= rs2.getString("com");
                old_owner_rent= rs2.getString("rent");
                if(old_owner_rent.equalsIgnoreCase("y"))
                    check_rental=true;
            }

            if(old_owner_rent.equalsIgnoreCase("y"))
            {
                String fetch_old_rentersSQL= "select * from renter_info where pid=?";
                PreparedStatement fetchRenters= connection.prepareStatement(fetch_old_rentersSQL);
                fetchRenters.setInt(1,check_pid);
                ResultSet rs3= fetchRenters.executeQuery();

                if(rs3.next())
                {
                    old_renter_name= rs3.getString("name");
                    old_renter_phno= rs3.getString("phno");
                    old_renter_email= rs3.getString("email");
                }
            }

            if(Integer.parseInt(old_flat)!=Integer.parseInt(get_insert_flat) ||
                        !old_wing.equals(get_insert_wing))
            {
                flag=1;

                String delete_old_owner_dataSQL="delete from owner_info where pid=?";
                PreparedStatement delete_old_owner= connection.prepareStatement(delete_old_owner_dataSQL);
                delete_old_owner.setInt(1,check_pid);
                delete_old_owner.executeUpdate();

                if(old_owner_rent.equalsIgnoreCase("y"))
                {
                    String delete_old_renter_dataSQL="delete from renter_info where pid=?";
                    PreparedStatement delete_old_renter= connection.prepareStatement(delete_old_renter_dataSQL);
                    delete_old_renter.setInt(1,check_pid);
                    delete_old_renter.executeUpdate();
                }

                addMember();
            }
            else
            {
                if(check_rental && !get_insert_rental)
                {
                    flag=1;

                    String update_com_statusSQL= "update owner_info set rent=? where pid=?";
                    PreparedStatement update_com_status= connection.prepareStatement(update_com_statusSQL);
                    update_com_status.setString(1,"n");
                    update_com_status.setInt(2,check_pid);
                    update_com_status.executeUpdate();

                    String delete_old_renter_dataSQL="delete from renter_info where pid=?";
                    PreparedStatement delete_old_renter= connection.prepareStatement(delete_old_renter_dataSQL);
                    delete_old_renter.setInt(1,check_pid);
                    delete_old_renter.executeUpdate();
                }
                else if((!check_rental && get_insert_rental))
                {
                    flag=1;

                    String update_rent_statusSQL= "update owner_info set rent=? where pid=?";
                    PreparedStatement update_rent_status= connection.prepareStatement(update_rent_statusSQL);
                    update_rent_status.setString(1,"y");
                    update_rent_status.setInt(2,check_pid);
                    update_rent_status.executeUpdate();

                    String update_com_statusSQL= "update owner_info set com=? where pid=?";
                    PreparedStatement update_com_status= connection.prepareStatement(update_com_statusSQL);
                    update_com_status.setString(1,"n");
                    update_com_status.setInt(2,check_pid);
                    update_com_status.executeUpdate();

                    String addTheRenterSQL= "insert into renter_info values(?,?,?,?)";
                    PreparedStatement addTheRenter= connection.prepareStatement(addTheRenterSQL);
                    addTheRenter.setInt(1,check_pid);
                    addTheRenter.setString(2,get_insert_rent_name);
                    addTheRenter.setString(3,get_insert_rent_phn_no);
                    addTheRenter.setString(4,get_insert_rent_email);
                    addTheRenter.executeUpdate();
                }
                else if(check_rental)
                {

                    if(!get_insert_rent_name.equalsIgnoreCase(old_renter_name))
                    {
                        flag=1;

                        String updateRenterInfoSQL= "update renter_info set name=? where pid=?";
                        PreparedStatement updateRenter= connection.prepareStatement(updateRenterInfoSQL);
                        updateRenter.setInt(2,check_pid);
                        updateRenter.setString(1,get_insert_rent_name);
                        updateRenter.executeUpdate();
                    }

                    if(!get_insert_rent_phn_no.equalsIgnoreCase(old_renter_phno))
                    {
                        flag=1;

                        String updateRenterInfoSQL= "update renter_info set phno=? where pid=?";
                        PreparedStatement updateRenter= connection.prepareStatement(updateRenterInfoSQL);
                        updateRenter.setInt(2,check_pid);
                        updateRenter.setString(1,get_insert_rent_phn_no);
                        updateRenter.executeUpdate();
                    }

                    if(!get_insert_rent_email.equalsIgnoreCase(old_renter_email))
                    {
                        flag=1;

                        String updateRenterInfoSQL= "update renter_info set email=? where pid=?";
                        PreparedStatement updateRenter= connection.prepareStatement(updateRenterInfoSQL);
                        updateRenter.setInt(2,check_pid);
                        updateRenter.setString(1,get_insert_rent_email);
                        updateRenter.executeUpdate();
                    }
                }

                if(!get_insert_owner_name.equalsIgnoreCase(old_owner_name))
                {
                    flag=1;

                    String updateOwnerInfoSQL= "update owner_info set name=? where pid=?";
                    PreparedStatement updateOwner= connection.prepareStatement(updateOwnerInfoSQL);
                    updateOwner.setInt(2,check_pid);
                    updateOwner.setString(1,get_insert_owner_name);
                    updateOwner.executeUpdate();
                }

                if(!get_insert_owner_phn_no.equalsIgnoreCase(old_owner_phno))
                {
                    flag=1;

                    String updateOwnerInfoSQL= "update owner_info set phno=? where pid=?";
                    PreparedStatement updateOwner= connection.prepareStatement(updateOwnerInfoSQL);
                    updateOwner.setInt(2,check_pid);
                    updateOwner.setString(1,get_insert_owner_phn_no);
                    updateOwner.executeUpdate();
                }

                if(!get_insert_owner_email.equalsIgnoreCase(old_owner_email))
                {
                    flag=1;

                    String updateOwnerInfoSQL= "update owner_info set email=? where pid=?";
                    PreparedStatement updateOwner= connection.prepareStatement(updateOwnerInfoSQL);
                    updateOwner.setInt(2,check_pid);
                    updateOwner.setString(1,get_insert_owner_email);
                    updateOwner.executeUpdate();
                }

                if(!Character.toString(get_insert_committee).equalsIgnoreCase(old_owner_com))
                {
                    flag=1;

                    String updateOwnerInfoSQL= "update owner_info set com=? where pid=?";
                    PreparedStatement updateOwner= connection.prepareStatement(updateOwnerInfoSQL);
                    updateOwner.setInt(2,check_pid);
                    updateOwner.setString(1,Character.toString(get_insert_committee));
                    updateOwner.executeUpdate();
                }
            }

            if(flag==0)
                JOptionPane.showMessageDialog(null,"Nothing to update",
                        "Error",JOptionPane.ERROR_MESSAGE);
            else
                JOptionPane.showMessageDialog(null,"Updation is Complete");

            clearAllScreens();
            cd_lt.show(main_panel,"home");
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        clearAllScreens();
        cd_lt.show(main_panel,"home");
    }

    private void deleteMember() {
        getSearchTexts();

        String flat_check,wing_check;
        flat_check= get_search_flat;
        wing_check= get_search_wing;

        try
        {
            String getPIDSql="select pid from property_ids where f_no=? and w_no=?";
            PreparedStatement getPID= connection.prepareStatement(getPIDSql);
            getPID.setInt(1,Integer.parseInt(flat_check));
            getPID.setString(2,wing_check);

            ResultSet rs= getPID.executeQuery();

            int p_id=0;

            if(rs.next())
                p_id=rs.getInt("pid");

            if(p_id==0)
            {
                JOptionPane.showMessageDialog(null,"Cannot find registered flat","Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            else
            {

                String checkRentSQL="select rent from owner_info where pid=?";
                PreparedStatement checkRent= connection.prepareStatement(checkRentSQL);
                checkRent.setInt(1,p_id);
                ResultSet rentCheck= checkRent.executeQuery();

                String check="";

                if (rentCheck.next())
                    check= rentCheck.getString("rent");

                String delMemSQL="delete from owner_info where pid=?";
                PreparedStatement delMem= connection.prepareStatement(delMemSQL);
                delMem.setInt(1,p_id);
                delMem.executeUpdate();

                if(check.equals("y"))
                {
                    String delRentMemSQL="delete from renter_info where pid=?";
                    PreparedStatement delRent= connection.prepareStatement(delRentMemSQL);
                    delRent.setInt(1,p_id);
                    delRent.executeUpdate();
                }

                JOptionPane.showMessageDialog(null,"Member removed successfully!");
                clearAllScreens();
                cd_lt.show(main_panel,"home");
            }
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 15; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +1 , width);
            }
            if(width > 300)
                width=300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    private void populateDisplay() {
        try
        {
            DefaultTableModel model1 = new DefaultTableModel
                    (new String[]{"Wing","Flat Number","Name", "Phone Number", "Email id","Rented","Committee Member"}
                            , 0);

            String fetchSQL1="Select * from owner_info";
            Statement fetch_owners_statement= connection.createStatement();
            ResultSet resultSet1= fetch_owners_statement.executeQuery(fetchSQL1);

            while(resultSet1.next())
            {
                String fetchPID= "select * from property_ids where pid = ?";
                PreparedStatement fetch_pid_statement= connection.prepareStatement(fetchPID);
                fetch_pid_statement.setInt(1,
                        Integer.parseInt(resultSet1.getString("pid")));
                ResultSet pidResultSet= fetch_pid_statement.executeQuery();
                String wing="",flat="";
                if(pidResultSet.next())
                {
                    wing= pidResultSet.getString("w_no");
                    flat= pidResultSet.getString("f_no");
                }
                String name = resultSet1.getString("name");
                String phno = resultSet1.getString("phno");
                String email = resultSet1.getString("email");
                String rented = resultSet1.getString("rent");
                String com = resultSet1.getString("com");
                model1.addRow(new Object[]{wing,flat,name, phno, email, rented, com});
            }

            table1.setModel(model1);
            resizeColumnWidth(table1);

            DefaultTableModel model2 = new DefaultTableModel
                    (new String[]{"Wing","Flat Number","Name", "Phone Number", "Email id"}
                            , 0);

            String fetchSQL2="Select * from renter_info";
            Statement fetch_renters_statement= connection.createStatement();
            ResultSet resultSet2= fetch_renters_statement.executeQuery(fetchSQL2);

            while(resultSet2.next())
            {
                String fetchPID= "select * from property_ids where pid = ?";
                PreparedStatement fetch_pid_statement= connection.prepareStatement(fetchPID);
                fetch_pid_statement.setInt(1,
                        Integer.parseInt(resultSet2.getString("pid")));
                ResultSet pidResultSet= fetch_pid_statement.executeQuery();
                String wing="",flat="";
                if(pidResultSet.next())
                {
                    wing= pidResultSet.getString("w_no");
                    flat= pidResultSet.getString("f_no");
                }
                String name = resultSet2.getString("name");
                String phno = resultSet2.getString("phno");
                String email = resultSet2.getString("email");
                model2.addRow(new Object[]{wing,flat,name, phno, email});
            }

            table2.setModel(model2);
            resizeColumnWidth(table2);


            DefaultTableModel model3 = new DefaultTableModel
                    (new String[]{"Name"}
                            , 0);

            String fetchSQL3="Select * from owner_info";
            Statement fetch_com_statement= connection.createStatement();
            ResultSet resultSet3= fetch_com_statement.executeQuery(fetchSQL3);

            while(resultSet3.next())
            {
                if(resultSet3.getString("com").equalsIgnoreCase("y"))
                {
                    String name = resultSet3.getString("name");
                    model3.addRow(new Object[]{name});
                }
            }

            table3.setModel(model3);
            resizeColumnWidth(table3);

            table1.setVisible(true);
            table2.setVisible(true);
            table3.setVisible(true);

            cd_lt.show(main_panel,"display page");
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    private void Calibrate() {
        try
        {
            int no_of_floors,flats_per_floor,wings;
            no_of_floors=Integer.parseInt(cali_screen_floor_txt.getText());
            flats_per_floor=Integer.parseInt(cali_screen_flats_floor_txt.getText());
            wings=Integer.parseInt(cali_screen_wing_txt.getText());

            for(int i=1;i<=wings;i++)
            {
                char temp= (char)(64+i);
                String wing= Character.toString(temp);
                for(int j=1;j<=no_of_floors;j++)
                {
                    for(int k=1;k<=flats_per_floor;k++)
                    {
                        int flat_no= (j*100) + k;
                        String insertIntoPID= "insert into property_ids values(null,?,?)";
                        PreparedStatement preparedStatement= connection.prepareStatement(insertIntoPID);
                        preparedStatement.setString(1,wing);
                        preparedStatement.setInt(2,flat_no);
                        preparedStatement.executeUpdate();
                    }
                }
            }

            JOptionPane.showMessageDialog(null,"Calibration was successful! Exiting now");
            refreshSoftware();
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetTables() {
        try
        {
            String deleteOwnersTableSQL,deleteRentersTableSQL,dropPidTableSQL;
            deleteOwnersTableSQL= "drop table owner_info";
            deleteRentersTableSQL="drop table renter_info";
            dropPidTableSQL="drop table property_ids";

            PreparedStatement deleteOwnersTable,deleteRentersTable,dropPidTable;
            deleteOwnersTable= connection.prepareStatement(deleteOwnersTableSQL);
            deleteRentersTable= connection.prepareStatement(deleteRentersTableSQL);
            dropPidTable= connection.prepareStatement(dropPidTableSQL);

            deleteOwnersTable.executeUpdate();
            deleteRentersTable.executeUpdate();
            dropPidTable.executeUpdate();

            String createPidSQL,createOwnersSQL,createRentersSQL;
            createPidSQL="create table property_ids(pid int auto_increment primary key,w_no char(2),f_no int(4));";
            createOwnersSQL="create table owner_info(pid int,name varchar(50),phno varchar(15),email varchar(50),rent char(2),com char(2), foreign key(pid) references property_ids(pid));";
            createRentersSQL="create table renter_info(pid int,name varchar(50),phno varchar(15),email varchar(50),foreign key(pid) references property_ids(pid));";

            PreparedStatement createPid,createOwners,createRenters;
            createPid= connection.prepareStatement(createPidSQL);
            createOwners= connection.prepareStatement(createOwnersSQL);
            createRenters= connection.prepareStatement(createRentersSQL);

            createPid.executeUpdate();
            createOwners.executeUpdate();
            createRenters.executeUpdate();

            JOptionPane.showMessageDialog(null,"Database was reset! Exiting now!");
            refreshSoftware();
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addNotice() {
        try
        {
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Object issueDateObj= issueDateIN.getValue();
            Object dueDateObj= dueDateIN.getValue();

            String issueDateStr= dateFormat.format(issueDateObj);
            String dueDateStr= dateFormat.format(dueDateObj);

            String addNoticeSQL="insert into notices values(null,?,?,?,?)";
            PreparedStatement statement= connection.prepareStatement(addNoticeSQL);

            String title= enterTitle.getText();
            String content= enterData.getText();

            statement.setString(1,issueDateStr);
            statement.setString(2,dueDateStr);
            statement.setString(3,title);
            statement.setString(4,content);

            statement.executeUpdate();

            JOptionPane.showMessageDialog(null,"Completed");
            enterTitle.setText(null);
            enterData.setText(null);
            issueDateIN.setValue(Calendar.getInstance().getTime());
            dueDateIN.setValue(Calendar.getInstance().getTime());

            cd_lt.show(main_panel,"home");
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeNotice() {
        String index= remField.getText();

        try
        {
            String remSQL="delete from notices where noticeno = ?";
            PreparedStatement remStmt= connection.prepareStatement(remSQL);
            remStmt.setString(1,index);
            remStmt.executeUpdate();
            JOptionPane.showMessageDialog(null,"Deleted Successfully!");
            remField.setText(null);
            cd_lt.show(main_panel,"home");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage(),
                    "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showNotices() {
        try
        {
            DefaultTableModel viewNoticesModel = new DefaultTableModel
                    (new String[]{"Notice Number","Date Of Issue","Due Date", "Title", "Content"}
                            , 0);

            String fetchSQL="Select * from notices";
            Statement fetchStmt= connection.createStatement();
            ResultSet resultSet= fetchStmt.executeQuery(fetchSQL);

            while(resultSet.next())
            {
                String number = resultSet.getString("noticeno");
                String doi = resultSet.getString("issuedate");
                String dueDate = resultSet.getString("duedate");
                String title = resultSet.getString("title");
                String content = resultSet.getString("context");
                viewNoticesModel.addRow(new Object[]{number,doi,dueDate, title, content});
            }

            viewNoticesTable.setModel(viewNoticesModel);
            resizeColumnWidth(viewNoticesTable);
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage(),
                    "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchInDatabase()
    {
        try
        {
            int count=0;
            String where,getName,wingno="";
            int pid,flatno=0;
            String getFlatInfoSQL;
            PreparedStatement getFlatInfo;

            getName= nameEnter.getText();

            PreparedStatement lookupinOwners, lookupinRenters;
            String lookupinOwnersSQL, lookupinRentersSQL;

            lookupinOwnersSQL="select * from owner_info where name= ?";
            lookupinOwners= connection.prepareStatement(lookupinOwnersSQL);
            lookupinOwners.setString(1,getName);

            ResultSet ownerResult= lookupinOwners.executeQuery();

            while(ownerResult.next()) {
                count++;
                where = "owner";
                pid = ownerResult.getInt("pid");

                getFlatInfoSQL = "select * from property_ids where pid= ?";
                getFlatInfo = connection.prepareStatement(getFlatInfoSQL);
                getFlatInfo.setInt(1, pid);

                ResultSet resultSet = getFlatInfo.executeQuery();

                if (resultSet.next()) {
                    flatno = resultSet.getInt("f_no");
                    wingno = resultSet.getString("w_no");
                }

                nameEnter.setVisible(false);
                lookupSubmit.setText("Go Again!");
                nameLabel.setText(nameLabel.getText()
                        + "\n Name found as a " + where + " in wing: " + wingno + " and flat: " + flatno);
            }

            lookupinRentersSQL="select * from renter_info where name= ?";
            lookupinRenters= connection.prepareStatement(lookupinRentersSQL);
            lookupinRenters.setString(1,getName);

            ResultSet renterResult= lookupinRenters.executeQuery();

            while(renterResult.next())
            {
                count++;
                where="renter";
                pid= renterResult.getInt("pid");

                getFlatInfoSQL = "select * from property_ids where pid= ?";
                getFlatInfo = connection.prepareStatement(getFlatInfoSQL);
                getFlatInfo.setInt(1, pid);

                ResultSet resultSet = getFlatInfo.executeQuery();

                if (resultSet.next()) {
                    flatno = resultSet.getInt("f_no");
                    wingno = resultSet.getString("w_no");
                }

                nameEnter.setVisible(false);
                lookupSubmit.setText("Go Again!");
                nameLabel.setText(nameLabel.getText()
                        + "\n Name found as a " + where + " in wing: " + wingno + " and flat: " + flatno);
        }
        if(count!=0)
        {
            nameLabel.setText("Found at "+count+" locations. "+nameLabel.getText());
        }

        else
            {
                throw new Exception("Name not found");
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage(),
                    "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetSearch()
    {
        nameLabel.setText("Enter the name to lookup in the database");
        lookupSubmit.setText("Search");
        nameEnter.setVisible(true);
        nameEnter.setText("");
    }
}