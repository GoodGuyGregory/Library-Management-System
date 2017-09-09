import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.proteanit.sql.DbUtils;

public class Welcome extends JFrame {

	private JPanel contentPane,main_panel,users,borrower_register,background_panel,search_panel;
	private JPanel check_out,check_in,book,Check_Inner,Fines,Fines_Main,Fines_Inner1,Fines_Inner2,Fines_Inner3;
	private JTextField txtSearch;
	private JTable search;
	private JTable table_1;
	private JTextField ln;
	
	private JTextField ssn;
	private JTextField sadd;
	private JTextField phn;
	private JTextField isbn;
	private JTextField isbnNo;
	private JTextField title;
	private JTextField author;
	private JTextField card_id;
	private JTextField name;
	private JTextField SSN;
	private int loans,days=0;
	private JTextField Book_isbn;
	private JTextField Card_id;
	private JTextField textFieldisbn;
	private JTextField textFieldcard;
	private JTextField date_issued;
	private JTextField due_date;
	private JTextField fines_check;
	private JTextField fines_laon_id;
	private JTextField fine_amount;
	private JTextField status;
	private JButton check_fines;
	private JTable Fines_Table;
	private JTextField fn;
	private JTextField c;
	private JTextField st;
	private JTextField cid;
	String lid;
	
	
	

	/**
	 * Launch the application.
	 */
	public  void newScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Welcome frame = new Welcome();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	
	public Welcome() {
		setResizable(false);
		
		setTitle("Library Management System");
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1037, 574);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panel.setBounds(0, 0, 1036, 144);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				txtSearch.setText("");
				txtSearch.setEnabled(false);
				search_panel.setVisible(false);
				borrower_register.setVisible(false);
				users.setVisible(false);
				background_panel.setVisible(true);
				
				
				
			
				
			}
		});
		lblNewLabel_2.setIcon(new ImageIcon(Welcome.class.getResource("/images/home.png")));
		lblNewLabel_2.setBounds(0, 92, 59, 51);
		panel.add(lblNewLabel_2);
		
		txtSearch = new JTextField();
		txtSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				txtSearch.setText("");
				txtSearch.setForeground(Color.BLACK);
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				txtSearch.setText("");
				txtSearch.setForeground(Color.BLACK);
			}
		});
		txtSearch.setForeground(Color.GRAY);
		txtSearch.setText("Search");
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() ==10)
				{				
				  
				  String txt=txtSearch.getText();
				  String query="select * from search where isbn like '%"+txt+"%' or title like '%"+txt+"%' or name like '%"+txt+"%'";
				  
				  Connection con=DBConnection.getConnection();
				  Statement stmt;
				try {
					stmt = con.createStatement();
					ResultSet rs=stmt.executeQuery(query);	
					background_panel.setVisible(false);
					search_panel.setVisible(true);	
					search.setModel(DbUtils.resultSetToTableModel(rs));
					
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					try {
						con.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				  
					
				}
			}
		});
		
		
		txtSearch.setEnabled(false);
		txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtSearch.setBounds(756, 101, 270, 32);
		panel.add(txtSearch);
		txtSearch.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				txtSearch.setEnabled(true);
				
			}
		});
		lblNewLabel_1.setBounds(702, 92, 344, 52);
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setIcon(new ImageIcon(Welcome.class.getResource("/images/search1.png")));
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Welcome.class.getResource("/images/Lib-banner.jpg")));
		lblNewLabel.setBounds(0, 0, 1036, 90);
		panel.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Add borrower");
		btnNewButton.setSelectedIcon(null);
		btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				check_in.setVisible(false);
				check_out.setVisible(false);
				Fines.setVisible(false);
				search_panel.setVisible(false);
				background_panel.setVisible(false);
				borrower_register.setVisible(true);
			
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBounds(59, 92, 140, 51);
		panel.add(btnNewButton);
		
		JButton view = new JButton("View Books");
		view.setHorizontalAlignment(SwingConstants.LEFT);
		view.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				search_panel.setVisible(false);
				background_panel.setVisible(false);
				borrower_register.setVisible(false);
				users.setVisible(true);
				Connection con=DBConnection.getConnection();
				try
				{
					Statement stmt=con.createStatement();
					ResultSet rs=stmt.executeQuery("select * from search");
					table_1.setModel(DbUtils.resultSetToTableModel(rs));
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(users, "unable to view data");
				}
				
			}
		});
		view.setFont(new Font("Tahoma", Font.BOLD, 12));
		view.setBounds(566, 92, 140, 51);
		panel.add(view);
		
		JButton btnNewButton_3 = new JButton("Check out");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Fines.setVisible(false);
				background_panel.setVisible(false);
				search_panel.setVisible(false);
				borrower_register.setVisible(false);
				users.setVisible(false);
				check_in.setVisible(false);
				check_out.setVisible(true);
			}
		});
		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_3.setBounds(198, 92, 118, 51);
		panel.add(btnNewButton_3);
		
		JButton btnCheckIn = new JButton("Check In");
		btnCheckIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Fines.setVisible(false);
				background_panel.setVisible(false);
				search_panel.setVisible(false);
				borrower_register.setVisible(false);
				users.setVisible(false);
				check_out.setVisible(false);
				Check_Inner.setVisible(false);
				check_in.setVisible(true);
				
			}
		});
		btnCheckIn.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCheckIn.setBounds(312, 92, 129, 51);
		panel.add(btnCheckIn);
		
		JButton CheckFines = new JButton("Check Fines");
		CheckFines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				background_panel.setVisible(false);
				search_panel.setVisible(false);
				borrower_register.setVisible(false);
				users.setVisible(false);
				check_out.setVisible(false);
				Check_Inner.setVisible(false);
				check_in.setVisible(false);
				Fines.setVisible(true);
				Fines_Inner1.setVisible(true);
				
			}
		});
		CheckFines.setFont(new Font("Tahoma", Font.BOLD, 12));
		CheckFines.setBounds(440, 92, 129, 51);
		panel.add(CheckFines);
		
		 main_panel = new JPanel();
		main_panel.setBounds(0, 144, 1031, 401);
		contentPane.add(main_panel);
		main_panel.setLayout(new CardLayout(0, 0));
		
		 background_panel = new JPanel();
		background_panel.setBackground(new Color(0, 51, 102));
		main_panel.add(background_panel, "name_1296592178144");
		background_panel.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(Welcome.class.getResource("/images/background.jpg")));
		lblNewLabel_3.setBounds(10, 11, 1011, 379);
		background_panel.add(lblNewLabel_3);
		
		 search_panel = new JPanel();
		search_panel.setBackground(Color.LIGHT_GRAY);
		main_panel.add(search_panel, "name_1330135872328");
		search_panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(239, 56, 580, 289);
		search_panel.add(scrollPane);
		
		search = new JTable();
		search.setEnabled(false);
		scrollPane.setViewportView(search);
		
		 borrower_register = new JPanel();
		borrower_register.setBackground(Color.LIGHT_GRAY);
		main_panel.add(borrower_register, "name_1341431894689");
		borrower_register.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(252, 6, 634, 369);
		borrower_register.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_5 = new JLabel("Borrower Register");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_5.setBounds(100, 0, 379, 38);
		panel_1.add(lblNewLabel_5);
		
		JLabel lname = new JLabel("Lname");
		lname.setHorizontalAlignment(SwingConstants.CENTER);
		lname.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lname.setBounds(98, 120, 79, 26);
		panel_1.add(lname);
		
		ln = new JTextField();
		ln.setFont(new Font("Tahoma", Font.PLAIN, 16));
		ln.setBounds(232, 122, 208, 24);
		panel_1.add(ln);
		ln.setColumns(10);
		
		
		JLabel lblSsn = new JLabel("SSN");
		lblSsn.setHorizontalAlignment(SwingConstants.CENTER);
		lblSsn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSsn.setBounds(70, 156, 84, 26);
		panel_1.add(lblSsn);
		
		JLabel lblAddress = new JLabel("Street Address ");
		lblAddress.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAddress.setBounds(87, 194, 132, 26);
		panel_1.add(lblAddress);
		
		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setHorizontalAlignment(SwingConstants.CENTER);
		lblPhone.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPhone.setBounds(70, 284, 97, 38);
		panel_1.add(lblPhone);
		
		ssn = new JTextField();
		ssn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		ssn.setColumns(10);
		ssn.setBounds(232, 159, 208, 25);
		panel_1.add(ssn);
		
		sadd = new JTextField();
		sadd.setFont(new Font("Tahoma", Font.PLAIN, 16));
		sadd.setColumns(10);
		sadd.setBounds(232, 196, 208, 24);
		panel_1.add(sadd);
		
		phn = new JTextField();
		phn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		phn.setColumns(10);
		phn.setBounds(232, 291, 208, 26);
		panel_1.add(phn);
		
		JLabel msg_error = new JLabel("");
		msg_error.setForeground(Color.RED);
		msg_error.setBounds(452, 84, 132, 26);
		panel_1.add(msg_error);
		
		JLabel msg_error1 = new JLabel("");
		msg_error1.setForeground(Color.RED);
		msg_error1.setBounds(452, 156, 132, 26);
		panel_1.add(msg_error1);
		
		JLabel msg_error2 = new JLabel("");
		msg_error2.setForeground(Color.RED);
		msg_error2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		msg_error2.setBounds(452, 194, 132, 26);
		panel_1.add(msg_error2);
		
		JButton register = new JButton("Register");
		register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String card=cid.getText();
				String fname=fn.getText();
				String lname=ln.getText();
				String SSN=ssn.getText();
				String address=sadd.getText();
				String city=c.getText();
				String state=st.getText();
				
				String phone=phn.getText();
		
				
				if(fname.equals("")||SSN.equals("")||address.equals("")||lname.equals("")||cid.equals("")){
					
					msg_error.setText("* required field");
					msg_error1.setText("* required field");
					msg_error2.setText("* required field");
				}
				else
				{
					Connection con=DBConnection.getConnection();
					try{
						
						PreparedStatement ps=con.prepareStatement("select Ssn from borrower where Ssn=? or card_id=?");
						ps.setString(1,SSN);
						ps.setString(2, card);
					
						ResultSet rs=ps.executeQuery();
						if(rs.next())
						{
							JOptionPane.showMessageDialog(borrower_register, "User Already Exists");
							msg_error1.setText("SSN already Registered");
						}
						else
						{
							PreparedStatement ps1=con.prepareStatement("insert into Borrower(card_id,fname,lname,ssn,address,city,state,phone) values(?,?,?,?,?,?,?,?)");
							ps1.setString(1,card);
							ps1.setString(2, fname);
							ps1.setString(4,SSN);
							ps1.setString(3,lname);
							ps1.setString(5,address);
							ps1.setString(6,city);
							ps1.setString(7,state);
							ps1.setString(8,phone);
							int i=ps1.executeUpdate();
							if(i==1)
							{
								JOptionPane.showMessageDialog(borrower_register, "User Regsitered Successfully");
								ln.setText("");
								fn.setText("");
								c.setText("");
								st.setText("");
								ssn.setText("");
								sadd.setText("");
								phn.setText("");
								cid.setText("");
				
							}
							else
							JOptionPane.showMessageDialog(borrower_register, "Unable to Register");
						
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					finally {
						try
						{
							con.close();
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
				}
				
			}
		});
		register.setForeground(Color.RED);
		register.setFont(new Font("Tahoma", Font.PLAIN, 24));
		register.setBounds(164, 325, 168, 38);
		panel_1.add(register);
		
		JLabel fname = new JLabel("Fname");
		fname.setHorizontalAlignment(SwingConstants.CENTER);
		fname.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		fname.setBounds(88, 82, 79, 26);
		panel_1.add(fname);
		
		fn = new JTextField();
		fn.setBounds(233, 80, 207, 30);
		panel_1.add(fn);
		fn.setColumns(10);
		
		JLabel lblCity = new JLabel("City");
		lblCity.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCity.setBounds(93, 226, 61, 26);
		panel_1.add(lblCity);
		
		c = new JTextField();
		c.setBounds(232, 229, 208, 24);
		panel_1.add(c);
		c.setColumns(10);
		
		JLabel lblState = new JLabel("State ");
		lblState.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblState.setBounds(93, 261, 61, 16);
		panel_1.add(lblState);
		
		st = new JTextField();
		st.setBounds(232, 256, 208, 30);
		panel_1.add(st);
		st.setColumns(10);
		
		cid = new JTextField();
		cid.setBounds(232, 45, 208, 30);
		panel_1.add(cid);
		cid.setColumns(10);
		
		JLabel lblCardid = new JLabel("Card_id");
		lblCardid.setHorizontalAlignment(SwingConstants.CENTER);
		lblCardid.setBounds(80, 50, 61, 16);
		panel_1.add(lblCardid);
		lblCardid.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		 users = new JPanel();
		users.setBackground(Color.LIGHT_GRAY);
		main_panel.add(users, "name_1443965880633");
		users.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("All Borrowers");
		lblNewLabel_4.setForeground(Color.BLACK);
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_4.setBounds(237, 24, 500, 35);
		users.add(lblNewLabel_4);
		
		JScrollPane borrow = new JScrollPane();
		borrow.setBounds(247, 70, 516, 251);
		users.add(borrow);
		
		table_1 = new JTable();
		table_1.setEnabled(false);
		borrow.setViewportView(table_1);
		
		 check_out = new JPanel();
		 check_out.setBackground(Color.LIGHT_GRAY);
		main_panel.add(check_out, "name_6156652856418");
		check_out.setLayout(null);
		
		JLabel lblNewLabel_71 = new JLabel("Check Out");
		lblNewLabel_71.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_71.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_71.setBounds(304, 35, 341, 49);
		check_out.add(lblNewLabel_71);
		
		JLabel lblNewLabel_8 = new JLabel("Book ISBN");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_8.setBounds(277, 105, 130, 38);
		check_out.add(lblNewLabel_8);
		
		JLabel lblBorrower = new JLabel("Card_id");
		lblBorrower.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblBorrower.setBounds(277, 169, 130, 38);
		check_out.add(lblBorrower);
		
		isbn = new JTextField();
		isbn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		isbn.setBounds(427, 105, 204, 38);
		check_out.add(isbn);
		isbn.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("ISSUE");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String isbnumber=isbn.getText();
				String card=card_id.getText();
				if((card.equals("") || isbnumber.equals("")))
				{
					JOptionPane.showMessageDialog(check_out,"please enter book isbn number and card id");
					card_id.setText("");
					isbn.setText("");
				}
				else
				{
					System.out.println("ISBN NUMBER:"+isbnumber);
						
				
				
				if(isbnumber.length()>13||isbnumber.length()<13)
				{
					JOptionPane.showMessageDialog(check_out,"Invalid ISBN NUMBER");
				}
				else
				{
					
   				Connection con=DBConnection.getConnection();
					
					try {
						String status="";
						PreparedStatement ps=con.prepareStatement("select isbn,title,name,availability from search where isbn=?");
						ps.setString(1,isbn.getText());
						ResultSet rs=ps.executeQuery();
						if(rs.next())
						{
							isbnNo.setText(rs.getString(1));
							title.setText(rs.getString(2));
							author.setText(rs.getString(3));
						    status=rs.getString(4);	
						    System.out.println("Status:"+status);
						}
						
						if(status.equals("unavailable"))
						{
							JOptionPane.showMessageDialog(check_out,"Book Not Available");
							isbn.setText("");
							card_id.setText("");
				
						}
						if(status.equals("Available"))
						{
							PreparedStatement ps1=con.prepareStatement("select ssn,fname,loans from borrower where card_id=?");
							ps1.setString(1,card_id.getText());
							ResultSet rs1=ps1.executeQuery();
							if(rs1.next())
							{
								SSN.setText(rs1.getString(1));
								name.setText(rs1.getString(2));
								loans=rs1.getInt(3);
								
								book.setVisible(true);
								
							}
							
						}
					else
						{
							JOptionPane.showMessageDialog(check_out,"CARD_ID NUMBER NOT FOUND");						
						}
						if(loans>=3)
						{
							JOptionPane.showMessageDialog(check_out,"You have reached maximum borrowing limit");
							book.setVisible(false);
							card_id.setText("");
							isbn.setText("");
						}
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
				}
				
				
				
				
			}}});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton_1.setBounds(412, 343, 185, 38);
		check_out.add(btnNewButton_1);
		
		
		
		 book = new JPanel();
		book.setBounds(693, 11, 307, 390);
		check_out.add(book);
		book.setLayout(null);
		book.setVisible(false);
		
		JLabel lblNewLabel_9 = new JLabel("ISBN");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_9.setBounds(10, 62, 80, 27);
		book.add(lblNewLabel_9);
		
		isbnNo = new JTextField();
		isbnNo.setEditable(false);
		isbnNo.setBounds(76, 62, 188, 27);
		book.add(isbnNo);
		isbnNo.setColumns(10);
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTitle.setBounds(10, 114, 80, 27);
		book.add(lblTitle);
		
		title = new JTextField();
		title.setEditable(false);
		title.setColumns(10);
		title.setBounds(76, 114, 188, 27);
		book.add(title);
		
		JLabel lblAuthor = new JLabel("Author");
		lblAuthor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblAuthor.setBounds(10, 167, 80, 27);
		book.add(lblAuthor);
		
		author = new JTextField();
		author.setEditable(false);
		author.setColumns(10);
		author.setBounds(76, 167, 188, 27);
		book.add(author);
		
		JLabel lblNewLabel_10 = new JLabel("Book Information");
		lblNewLabel_10.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_10.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_10.setBounds(33, 11, 231, 34);
		book.add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("Borrower Info");
		lblNewLabel_11.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_11.setBounds(20, 205, 244, 27);
		book.add(lblNewLabel_11);
		
		JLabel lblNewLabel_12 = new JLabel("Name");
		lblNewLabel_12.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_12.setBounds(10, 253, 80, 27);
		book.add(lblNewLabel_12);
		
		JLabel lblNewLabel_13 = new JLabel("SSN");
		lblNewLabel_13.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_13.setBounds(10, 295, 80, 32);
		book.add(lblNewLabel_13);
		
		name = new JTextField();
		name.setEditable(false);
		name.setBounds(76, 256, 188, 27);
		book.add(name);
		name.setColumns(10);
		
		SSN = new JTextField();
		SSN.setEditable(false);
		SSN.setColumns(10);
		SSN.setBounds(76, 303, 188, 27);
		book.add(SSN);
		
		JButton btnNewButton_2 = new JButton("Confirm");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i=JOptionPane.showConfirmDialog(check_out,"Do you want to issue the book?");
				System.out.println("I:"+i);
				if(i==0 && loans<3)
				{
					String ssn=SSN.getText();
					String isb=isbnNo.getText();
					String Card_id=card_id.getText();
					Connection con=DBConnection.getConnection();
					try {
						
						PreparedStatement ps=con.prepareStatement("insert into book_loans(isbn,card_id,date_out,due_date) values(?,?,current_date(),current_date()+interval 14 day)");
						ps.setString(1,isb);
						ps.setString(2,Card_id);
						int j=ps.executeUpdate();
						PreparedStatement ps1=con.prepareStatement("update borrower set loans=? where ssn=?");
						ps1.setInt(1,loans+1);
						ps1.setString(2,ssn);
						int k=ps1.executeUpdate();
						PreparedStatement ps2=con.prepareStatement("update book set Availability=? where isbn=?");
						ps2.setString(1,"unavailable");
						ps2.setString(2,isb);
						int l=ps2.executeUpdate();
						if(j==1 && k==1 && l==1)
						{
							JOptionPane.showMessageDialog(check_out,"Book Issued Successfully!!");
							book.setVisible(false);
							card_id.setText("");
							isbn.setText("");
						}
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					JOptionPane.showMessageDialog(check_out,"You have reached maximum borrowing limit");
				}
				
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNewButton_2.setBounds(64, 338, 201, 41);
		book.add(btnNewButton_2);
		
		card_id = new JTextField();
		card_id.setFont(new Font("Tahoma", Font.PLAIN, 16));
		card_id.setBounds(430, 169, 201, 36);
		check_out.add(card_id);
		card_id.setColumns(10);
		
		 check_in = new JPanel();
		 check_in.setBackground(Color.LIGHT_GRAY);
		main_panel.add(check_in, "name_6171782382509");
		check_in.setLayout(null);
		
		JLabel label = new JLabel("Check In");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.BOLD, 20));
		label.setBounds(130, 54, 341, 49);
		check_in.add(label);
		
		JLabel label_1 = new JLabel("Book ISBN");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_1.setBounds(48, 127, 130, 38);
		check_in.add(label_1);
		
		JLabel label_2 = new JLabel("Card_id");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_2.setBounds(48, 223, 130, 38);
		check_in.add(label_2);
		
		Book_isbn = new JTextField();
		Book_isbn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Book_isbn.setColumns(10);
		Book_isbn.setBounds(248, 127, 204, 38);
		check_in.add(Book_isbn);
		
		Card_id = new JTextField();
		Card_id.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Card_id.setColumns(10);
		Card_id.setBounds(248, 223, 204, 38);
		check_in.add(Card_id);
		
		JButton btnNewButton_4 = new JButton("Retrieve");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String isbn=Book_isbn.getText();
				String card_id=Card_id.getText();
				if(isbn.equals("") && card_id.equals(""))
				{
					JOptionPane.showMessageDialog(check_in,"Please Enter Book ISBN or Card_id");
				}
				else
				{
					Connection con=DBConnection.getConnection();
					try {
						if(!isbn.equals("")){								
						
						PreparedStatement ps=con.prepareStatement("select isbn,card_id,date_out,due_date,loan_id from book_loans where isbn=? and date_in is null");
						ps.setString(1,isbn);
						ResultSet rs=ps.executeQuery();
						if(rs.next())
						{
							lid=rs.getString(5); 
							System.out.println("loan_id:"+lid);
							textFieldisbn.setText(rs.getString(1));
							textFieldcard.setText(rs.getString(2));
							date_issued.setText(rs.getString(3));
							due_date.setText(rs.getString(4));							
							Check_Inner.setVisible(true);
							
						}
						else
						{
							JOptionPane.showMessageDialog(check_in,"This book has not been Checked out!!");
						}
						}
						else
						{
							if(!card_id.equals("")){
								
								PreparedStatement ps=con.prepareStatement("select isbn,card_id,date_out,due_date from book_loans where card_id=? and date_in is null");
								ps.setInt(1,Integer.parseInt(card_id));
								ResultSet rs=ps.executeQuery();
								if(rs.next())
								{
									textFieldisbn.setText(rs.getString(1));
									textFieldcard.setText(rs.getString(2));
									date_issued.setText(rs.getString(3));
									due_date.setText(rs.getString(4));									
									Check_Inner.setVisible(true);
									
								}
								else
								{
									JOptionPane.showMessageDialog(check_in,"This borrower does not have any Pending Books to return");
								}
						}
					} }catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		btnNewButton_4.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton_4.setBounds(218, 313, 142, 49);
		check_in.add(btnNewButton_4);
		
		JLabel lblNewLabel_14 = new JLabel("(OR)");
		lblNewLabel_14.setForeground(Color.RED);
		lblNewLabel_14.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_14.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_14.setBounds(163, 184, 70, 28);
		check_in.add(lblNewLabel_14);
		
		 Check_Inner = new JPanel();
		Check_Inner.setBounds(538, 0, 461, 401);
		check_in.add(Check_Inner);
		Check_Inner.setLayout(null);
		
		JLabel label_3 = new JLabel("Book ISBN");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_3.setBounds(22, 31, 130, 38);
		Check_Inner.add(label_3);
		
		textFieldisbn = new JTextField();
		textFieldisbn.setEditable(false);
		textFieldisbn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldisbn.setColumns(10);
		textFieldisbn.setBounds(140, 31, 204, 38);
		Check_Inner.add(textFieldisbn);
		
		JLabel label_4 = new JLabel("Card_id");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_4.setBounds(22, 92, 98, 38);
		Check_Inner.add(label_4);
		
		textFieldcard = new JTextField();
		textFieldcard.setEditable(false);
		textFieldcard.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldcard.setColumns(10);
		textFieldcard.setBounds(140, 92, 204, 38);
		Check_Inner.add(textFieldcard);
		
		JLabel lblDateissued = new JLabel("Date_Issued");
		lblDateissued.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDateissued.setBounds(22, 164, 98, 38);
		Check_Inner.add(lblDateissued);
		
		JLabel lblDuedate = new JLabel("Due_Date");
		lblDuedate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDuedate.setBounds(22, 226, 98, 38);
		Check_Inner.add(lblDuedate);
		
		date_issued = new JTextField();
		date_issued.setFont(new Font("Tahoma", Font.PLAIN, 16));
		date_issued.setEditable(false);
		date_issued.setColumns(10);
		date_issued.setBounds(140, 164, 204, 38);
		Check_Inner.add(date_issued);
		
		due_date = new JTextField();
		due_date.setFont(new Font("Tahoma", Font.PLAIN, 16));
		due_date.setEditable(false);
		due_date.setColumns(10);
		due_date.setBounds(140, 226, 204, 38);
		Check_Inner.add(due_date);
		
		JButton btnCheckIn_1 = new JButton("Check In");
		btnCheckIn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean flag=true;
				double fine_amt=0;
				String isbn=textFieldisbn.getText();
				int card_id=Integer.parseInt(textFieldcard.getText());
				Connection con=DBConnection.getConnection();
				PreparedStatement ps3;
				try {
					ps3 = con.prepareStatement("select loans from borrower where card_id=?");
					ps3.setInt(1,card_id);
					ResultSet rs1=ps3.executeQuery();
					if(rs1.next())
					{
						loans=rs1.getInt(1);
						System.out.println("Loans:"+loans);
					}
					else if(loans<=0 )
					{
						JOptionPane.showMessageDialog(check_in,"No books to return");
						flag=false;
						if(loans<0){loans=0;
						System.out.println("loans:"+loans);}
						Book_isbn.setText("");
						Card_id.setText("");
					}

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}		
				
				try {
					
					Connection con1=DBConnection.getConnection();
					
					PreparedStatement s=con1.prepareStatement("update book_loans set date_in=current_date() where isbn=?");
					s.setString(1,isbn);	
					s.executeUpdate();
					PreparedStatement s11=con1.prepareStatement("update book_loans set date_diff=datediff(Date_in,Due_date) where isbn=?");
					s11.setString(1,isbn);	
					s11.executeUpdate();
					PreparedStatement ps=con1.prepareStatement("select date_diff from book_loans where card_id=? and loan_id=?");
					ps.setInt(1,card_id);
					ps.setInt(2,Integer.parseInt(lid));
					ResultSet rs=ps.executeQuery();
				
					while(rs.next())
						{
							days=rs.getInt(1);
							System.out.println("Days:"+days);
							if(days >= 1)
							{
								int loan_id=Integer.parseInt(lid);
								fine_amt=  (days*0.25);
								System.out.println("Fine_ammount:"+fine_amt);
								PreparedStatement ps1=con1.prepareStatement("insert into fines(loan_id,Fine_amt) values(?,?)");
								ps1.setInt(1,loan_id);
								ps1.setDouble(2,fine_amt);
								ps1.executeUpdate();
								PreparedStatement ps2=con1.prepareStatement("select * from fines where loan_id=?");
								ps2.setInt(1,loan_id);
								ResultSet rs1=ps2.executeQuery();
								if(rs1.next())
								{
								  background_panel.setVisible(false);
								  search_panel.setVisible(false);
								  borrower_register.setVisible(false);
								  users.setVisible(false);
								  check_out.setVisible(false);
								  Check_Inner.setVisible(false);
								  check_in.setVisible(false);								  
								  Fines.setVisible(true);
								  Fines_Inner1.setVisible(false);
								  Fines_Inner3.setVisible(false);
								  Fines_Inner2.setVisible(true);
								  JOptionPane.showMessageDialog(check_in,"This Book has an Overdue fine");
								  fines_laon_id.setText(""+rs1.getInt(1));
								  fine_amount.setText("$"+rs1.getDouble(2));
								  status.setText(rs1.getString(3));
								  
								  
								  PreparedStatement ps11=con1.prepareStatement("update book set Availability=? where isbn=?");
									ps11.setString(1,"Available");
									ps11.setString(2,isbn);
									int j=ps11.executeUpdate();														    
									PreparedStatement ps21=con1.prepareStatement("update borrower set loans=? where card_id=?");								
									if(loans>0)
									ps21.setInt(1,--loans);
									else
									ps21.setInt(1,loans);
									System.out.println("loans:"+loans);
									ps21.setInt(2,card_id);
									int k=ps21.executeUpdate();
									if(j==1 && k==1 && flag){
										if(fine_amt==0)
										JOptionPane.showMessageDialog(check_in,"Book returned Successfully");
										else 
											JOptionPane.showMessageDialog(check_in,"Book has a fine ");
										Check_Inner.setVisible(false);
									Book_isbn.setText("");
									Card_id.setText("");
									}
								}
			
							}
							else
							{
								PreparedStatement ps1=con1.prepareStatement("update book set Availability=? where isbn=?");
								ps1.setString(1,"Available");
								ps1.setString(2,isbn);
								int j=ps1.executeUpdate();														    
								PreparedStatement ps2=con1.prepareStatement("update borrower set loans=? where card_id=?");								
								if(loans>0)
								ps2.setInt(1,--loans);
								else
								ps2.setInt(1,loans);
								System.out.println("loans:"+loans);
								ps2.setInt(2,card_id);
								int k=ps2.executeUpdate();
								if(j==1 && k==1 && flag){
									if(fine_amt==0)
									JOptionPane.showMessageDialog(check_in,"Book returned Successfully");
									else 
										JOptionPane.showMessageDialog(check_in,"Book has a fine ");
									Check_Inner.setVisible(false);
								Book_isbn.setText("");
								Card_id.setText("");
								}
								
							}
						}
						
					}
					
				 catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnCheckIn_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnCheckIn_1.setBounds(150, 341, 142, 49);
		Check_Inner.add(btnCheckIn_1);
		
		 Fines = new JPanel();
		 Fines.setBackground(Color.GRAY);
		main_panel.add(Fines, "name_675556986004");
		Fines.setLayout(null);
		
		 Fines_Main = new JPanel();
		Fines_Main.setBounds(187, 27, 569, 335);
		Fines.add(Fines_Main);
		Fines_Main.setLayout(new CardLayout(0, 0));
		
		 Fines_Inner1 = new JPanel();
		Fines_Inner1.setLayout(null);
		Fines_Main.add(Fines_Inner1, "name_1475554940858");
		
		JLabel label_5 = new JLabel("Fines Information");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 22));
		label_5.setBounds(145, 11, 319, 38);
		Fines_Inner1.add(label_5);
		
		JLabel label_6 = new JLabel("Borrowers_ID");
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label_6.setBounds(79, 103, 143, 54);
		Fines_Inner1.add(label_6);
		
		fines_check = new JTextField();
		fines_check.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		fines_check.setColumns(10);
		fines_check.setBounds(226, 103, 252, 49);
		Fines_Inner1.add(fines_check);
		
		check_fines = new JButton("Check Fines");
		check_fines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String id=fines_check.getText();
				if(id.equals(""))
				{
					JOptionPane.showMessageDialog(Fines_Inner1,"Please enter a Borrower ID");
				}
				else
				{
					try
					{
						Connection con=DBConnection.getConnection();
						PreparedStatement ps=con.prepareStatement("select loan_id, isbn, card_id, date_out, due_date from book_loans where (due_date< current_date() and date_in is null ) and card_id=?");
						ps.setInt(1,Integer.parseInt(fines_check.getText()));
						ResultSet rs=ps.executeQuery();
						if(!rs.next())
						{
							fines_check.setText("");
							JOptionPane.showMessageDialog(Fines_Inner1,"No Overdue books for this Borrower" );
						}
						else
						{
							Fines_Inner1.setVisible(false);
							Fines_Inner2.setVisible(false);
							Fines_Inner3.setVisible(true);
							Fines_Table.setModel(DbUtils.resultSetToTableModel(rs));
						}
						
						
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				
			}
		});
		check_fines.setFont(new Font("Tahoma", Font.BOLD, 16));
		check_fines.setBounds(171, 215, 246, 49);
		Fines_Inner1.add(check_fines);
		
		 Fines_Inner2 = new JPanel();
		Fines_Main.add(Fines_Inner2, "name_1619003523841");
		Fines_Inner2.setLayout(null);
		
		JLabel lblNewLabel_15 = new JLabel("Fines Information");
		lblNewLabel_15.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_15.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_15.setBounds(91, 11, 375, 25);
		Fines_Inner2.add(lblNewLabel_15);
		
		JButton pay_button = new JButton("Pay");
		pay_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int loan_id=Integer.parseInt(fines_laon_id.getText());
				try
				{
					Connection con=DBConnection.getConnection();
					PreparedStatement ps=con.prepareStatement("update fines set paid=? where loan_id=?");
					ps.setString(1,"yes");
					ps.setInt(2,loan_id);
					int i=ps.executeUpdate();
					if(i==1)
					{
						JOptionPane.showMessageDialog(Fines_Inner2,"Dues cleared!!!!");
						JOptionPane.showMessageDialog(Fines_Inner2,"Book returned successfully!!!!");
						pay_button.setEnabled(false);
						Fines_Inner2.setVisible(false);
						Fines_Inner1.setVisible(false);
						Fines_Inner3.setVisible(false);
						Fines.setVisible(false);
						background_panel.setVisible(true);
						
					}
					
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		pay_button.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pay_button.setBounds(208, 285, 151, 39);
		Fines_Inner2.add(pay_button);
		
		JLabel lblNewLabel_16 = new JLabel("Loan_ID");
		lblNewLabel_16.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_16.setBounds(65, 62, 101, 39);
		Fines_Inner2.add(lblNewLabel_16);
		
		fines_laon_id = new JTextField();
		fines_laon_id.setEditable(false);
		fines_laon_id.setBounds(185, 58, 217, 43);
		Fines_Inner2.add(fines_laon_id);
		fines_laon_id.setColumns(10);
		
		JLabel lblFineAmount = new JLabel("Fine Amount");
		lblFineAmount.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblFineAmount.setBounds(65, 139, 101, 39);
		Fines_Inner2.add(lblFineAmount);
		
		fine_amount = new JTextField();
		fine_amount.setEditable(false);
		fine_amount.setColumns(10);
		fine_amount.setBounds(185, 135, 217, 43);
		Fines_Inner2.add(fine_amount);
		
		JLabel lblStatus = new JLabel("Paid");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblStatus.setBounds(65, 204, 101, 39);
		Fines_Inner2.add(lblStatus);
		
		status = new JTextField();
		status.setEditable(false);
		status.setColumns(10);
		status.setBounds(185, 200, 217, 43);
		Fines_Inner2.add(status);
		
		 Fines_Inner3 = new JPanel();
		Fines_Main.add(Fines_Inner3, "name_20808321785091");
		Fines_Inner3.setLayout(null);
		
		JLabel lblNewLabel_17 = new JLabel("OverDue books");
		lblNewLabel_17.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_17.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_17.setBounds(113, 23, 296, 29);
		Fines_Inner3.add(lblNewLabel_17);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(88, 74, 401, 212);
		Fines_Inner3.add(scrollPane_1);
		
		Fines_Table = new JTable();
		Fines_Table.setEnabled(false);
		scrollPane_1.setViewportView(Fines_Table);
		
		
		
		
		
	}
}
