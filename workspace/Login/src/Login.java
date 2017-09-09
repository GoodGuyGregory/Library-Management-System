import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login {

	private JFrame frmLibrary;
	private JTextField username;
	private JPasswordField password;
	JLabel msg;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frmLibrary.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}
    public  void validate()
    {
    	String user=username.getText();
		String pass=password.getText();
		if(user.equals("admin") && pass.equals("admin"))
		{
	       frmLibrary.dispose();
	       Welcome w1=new Welcome();
	       w1.newScreen();
	       
	       
	       
		}
		else if(user.equals("") || pass.equals(""))
		{
			msg.setText("Enter username and password");
		}
		else
		{
			msg.setText("Invalid Username and password");
		}
    }
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLibrary = new JFrame();
		frmLibrary.setResizable(false);
		frmLibrary.setTitle(" Library  Management System");
		frmLibrary.getContentPane().setBackground(new Color(51, 51, 51));
		frmLibrary.setBounds(100, 100, 727, 444);
		frmLibrary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLibrary.getContentPane().setLayout(null);
		frmLibrary.setUndecorated(true);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(51, 102, 102));
		panel.setBounds(0, 0, 727, 106);
		frmLibrary.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}
		});
		lblNewLabel_3.setIcon(new ImageIcon(Login.class.getResource("/images/exit.png")));
		lblNewLabel_3.setBounds(662, 0, 65, 45);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel = new JLabel("Library Management System");
		lblNewLabel.setBounds(0, 0, 727, 106);
		panel.add(lblNewLabel);
		lblNewLabel.setBackground(Color.BLACK);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 40));
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBackground(Color.WHITE);
		lblNewLabel_1.setBounds(166, 188, 123, 32);
		frmLibrary.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblNewLabel_2.setBounds(166, 238, 123, 33);
		frmLibrary.getContentPane().add(lblNewLabel_2);
		
		username = new JTextField();
		username.setFont(new Font("Arial", Font.PLAIN, 20));
		username.setBounds(299, 191, 160, 32);
		frmLibrary.getContentPane().add(username);
		username.setColumns(10);
		
		password = new JPasswordField();
		password.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10)
				{
					validate();
				}
					
			}
		});
		password.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		password.setBounds(299, 238, 160, 30);
		frmLibrary.getContentPane().add(password);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnNewButton.setForeground(Color.RED);
			}
		});
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.setIcon(null);
	    
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				validate();
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 20));
		btnNewButton.setBounds(299, 293, 160, 47);
		frmLibrary.getContentPane().add(btnNewButton);
		
		 msg = new JLabel("");
		 msg.setHorizontalAlignment(SwingConstants.CENTER);
		msg.setForeground(Color.RED);
		msg.setFont(new Font("Tahoma", Font.PLAIN, 14));
		msg.setBounds(155, 139, 378, 38);
		frmLibrary.getContentPane().add(msg);
	}
}
