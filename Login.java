package practica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import com.mysql.cj.jdbc.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField txtUname;
	private JPasswordField txtPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
	
	 
	 
	 
	public Login() {
		setTitle("Login");
		setForeground(new Color(213, 134, 221));
		setFont(new Font("Arial", Font.BOLD, 18));
		setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\Program Files\\Eclipse\\Workspace\\Practica\\src\\practica\\university.png"));
		setBackground(new Color(213, 134, 221));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 498, 389);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(213, 134, 221));
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0), 2));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Arial", Font.BOLD, 20));
		lblUsername.setBackground(new Color(240, 240, 240));
		lblUsername.setForeground(new Color(128, 0, 128));
		lblUsername.setBounds(27, 79, 129, 34);
		contentPane.add(lblUsername);
		
		JLabel lblTitlu = new JLabel("LOGIN");
		lblTitlu.setForeground(new Color(64, 0, 64));
		lblTitlu.setFont(new Font("Arial", Font.BOLD, 30));
		lblTitlu.setBounds(186, 30, 129, 30);
		contentPane.add(lblTitlu);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(new Color(128, 0, 128));
		lblPassword.setFont(new Font("Arial", Font.BOLD, 20));
		lblPassword.setBackground(SystemColor.menu);
		lblPassword.setBounds(27, 131, 129, 34);
		contentPane.add(lblPassword);
		
		JLabel lblUsertype = new JLabel("Usertype");
		lblUsertype.setForeground(new Color(128, 0, 128));
		lblUsertype.setFont(new Font("Arial", Font.BOLD, 20));
		lblUsertype.setBackground(SystemColor.menu);
		lblUsertype.setBounds(27, 183, 129, 34);
		contentPane.add(lblUsertype);
		
		txtUname = new JTextField();
		txtUname.setForeground(new Color(64, 0, 64));
		txtUname.setFont(new Font("Arial", Font.BOLD, 15));
		txtUname.setBounds(201, 79, 243, 35);
		contentPane.add(txtUname);
		
		txtPass = new JPasswordField();
		txtPass.setForeground(new Color(64, 0, 64));
		txtPass.setFont(new Font("Arial", Font.BOLD, 15));
		txtPass.setBounds(201, 131, 243, 35);
		contentPane.add(txtPass);
		
		JComboBox txtUtype = new JComboBox();
		txtUtype.setForeground(new Color(64, 0, 64));
		txtUtype.setFont(new Font("Arial", Font.BOLD, 15));
		txtUtype.setModel(new DefaultComboBoxModel(new String[] {"Admin", "Student"}));
		txtUtype.setBounds(201, 183, 243, 35);
		contentPane.add(txtUtype);
		
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.setForeground(new Color(64, 0, 64));
		btnLogin.setBackground(new Color(230, 230, 250));
		btnLogin.setFont(new Font("Arial", Font.BOLD, 20));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = txtUname.getText();
		        String password = txtPass.getText();
		        String utype = txtUtype.getSelectedItem().toString();

		        try {
		            Class.forName("com.mysql.cj.jdbc.Driver");
		             con = DriverManager.getConnection("jdbc:mysql://localhost/practica","root","");
		            
		            pst = con.prepareStatement("select * from user where uname =? and password =? and utype = ?");
		            
		            pst.setString(1, username);
		            pst.setString(2, password);
		             pst.setString(3, utype);
		             
		            rs= pst.executeQuery();
		            
		            if (rs.next()) {
		            if (utype.equals("Admin")) {
		                dispose();  // Ascunde fereastra de login
		                new Administrator().setVisible(true);
		            } else if (utype.equals("Student")) {
		                dispose();  // Ascunde fereastra de login
		                new TabelBurse().setVisible(true);
		            }

		            else
		            {
		                JOptionPane.showMessageDialog(null,"UserName and Password do not Match");
		            }
		                
		            }
		          } catch (ClassNotFoundException ex) {
		             ex.printStackTrace();
		          
		          } catch (SQLException ex) {
		            
		           ex.printStackTrace();
		          
		          } 
		        try {
		            // Înregistrare activitate în jurnal
		            String logDescription = "Logare reușită pentru " + utype + " cu numele de utilizator: " + username;
		            String sqlLog = "INSERT INTO activity_log (user_type, username, action_description, action_time) VALUES (?, ?, ?, NOW())";

		            pst = con.prepareStatement(sqlLog);
		            pst.setString(1, utype);
		            pst.setString(2, username);
		            pst.setString(3, logDescription);

		            // Executarea comenzii SQL pentru înregistrarea activității
		            pst.executeUpdate();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		            // Tratează excepțiile SQL aici
		        }
		        
			}

			private void hide() {
				// TODO Auto-generated method stub
				
			}
		});
		btnLogin.setBounds(299, 268, 145, 51);
		contentPane.add(btnLogin);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon("D:\\Program Files\\Eclipse\\Workspace\\Practica\\src\\practica\\login1.png"));
		lblNewLabel_2.setBounds(27, 10, 75, 69);
		contentPane.add(lblNewLabel_2);
	}
}
