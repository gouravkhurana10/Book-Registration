package com.gourav;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Crud_App {

	private JFrame frame;
	private JTextField bookName;
	private JTextField bookEdition;
	private JTextField bookPrice;
	private JTable table;
	private JTextField bookID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Crud_App window = new Crud_App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Crud_App() {
		initialize();
		Connect();
		table_load();
	}
	
	//Setting up connection
		Connection con;
		PreparedStatement pst;
		ResultSet rs;
		
		public void Connect() {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost/crud_app", "root", "");
			} 
			catch(ClassNotFoundException ex) {
				
			} 
			catch(SQLException ex) {
				
			}
			
		}
		
		public void table_load() {
			try {
				pst = con.prepareStatement("select * from book");
				rs = pst.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(rs));
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 693, 463);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblBookRegistration = new JLabel("Book Registration");
		lblBookRegistration.setHorizontalAlignment(SwingConstants.CENTER);
		lblBookRegistration.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblBookRegistration.setBounds(177, 11, 318, 52);
		frame.getContentPane().add(lblBookRegistration);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 86, 285, 171);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblBookName = new JLabel("Book name:");
		lblBookName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblBookName.setBounds(20, 26, 94, 23);
		panel.add(lblBookName);
		
		JLabel lblEdition = new JLabel("Price:");
		lblEdition.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblEdition.setBounds(20, 123, 94, 23);
		panel.add(lblEdition);
		
		JLabel lblEdition_1 = new JLabel("Edition:");
		lblEdition_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblEdition_1.setBounds(20, 74, 94, 23);
		panel.add(lblEdition_1);
		
		bookName = new JTextField();
		bookName.setBounds(124, 29, 128, 20);
		panel.add(bookName);
		bookName.setColumns(10);
		
		bookEdition = new JTextField();
		bookEdition.setColumns(10);
		bookEdition.setBounds(124, 77, 128, 20);
		panel.add(bookEdition);
		
		bookPrice = new JTextField();
		bookPrice.setColumns(10);
		bookPrice.setBounds(124, 126, 128, 20);
		panel.add(bookPrice);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(329, 86, 328, 211);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bname, edition, price;
				bname = bookName.getText();
				edition = bookEdition.getText();
				price = bookPrice.getText();
				
				try {
					pst = con.prepareStatement("insert into book(name,edition,price)values(?,?,?)");
					pst.setString(1, bname);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "**Record added**");
					table_load();
					bookName.setText("");
					bookEdition.setText("");
					bookPrice.setText("");
					bookName.requestFocus();
				}
				catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSave.setBounds(10, 268, 76, 33);
		frame.getContentPane().add(btnSave);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(115, 268, 76, 33);
		frame.getContentPane().add(btnExit);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookName.setText("");
				bookEdition.setText("");
				bookPrice.setText("");
				bookName.requestFocus();
				bookID.setText("");
			}
		});
		btnClear.setBounds(219, 268, 76, 33);
		frame.getContentPane().add(btnClear);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bname, edition, price, bID;
				bname = bookName.getText();
				edition = bookEdition.getText();
				price = bookPrice.getText();
				bID = bookID.getText();
				
				try {
					pst = con.prepareStatement("update book set name=?,edition=?,price=? where id=?");
					pst.setString(1, bname);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.setString(4, bID);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "**Record Updated**");
					table_load();
					bookName.setText("");
					bookEdition.setText("");
					bookPrice.setText("");
					bookID.setText("");
					bookName.requestFocus();
				}
				catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnUpdate.setBounds(412, 314, 76, 33);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bID;
				bID = bookID.getText();
				
				try {
					pst = con.prepareStatement("delete from book where id=?");
					pst.setString(1, bID);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "**Record Deleted**");
					table_load();
					bookName.setText("");
					bookEdition.setText("");
					bookPrice.setText("");
					bookID.setText("");
					bookName.requestFocus();
				}
				catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnDelete.setBounds(520, 314, 76, 33);
		frame.getContentPane().add(btnDelete);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 312, 285, 96);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblBookId = new JLabel("Book ID:");
		lblBookId.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblBookId.setBounds(55, 38, 77, 23);
		panel_1.add(lblBookId);
		
		bookID = new JTextField();
		bookID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					String id = bookID.getText();
					pst = con.prepareStatement("select name,edition,price from book where id = ?");
					pst.setString(1, id);
					ResultSet rs = pst.executeQuery();
					
					if(rs.next() == true) {
						String name = rs.getString(1);
						String edition = rs.getString(2);
						String price = rs.getString(3);
						
						bookName.setText(name);
						bookEdition.setText(edition);
						bookPrice.setText(price);
					}
					else {
						bookName.setText("");
						bookEdition.setText("");
						bookPrice.setText("");
					}
					
					
				}
				catch(SQLException ex) {
					
				}
			}
		});
		bookID.setColumns(10);
		bookID.setBounds(142, 41, 89, 20);
		panel_1.add(bookID);
	}
}
