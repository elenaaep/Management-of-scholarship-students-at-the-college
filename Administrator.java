package practica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JLabel;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

import java.awt.*;

import java.sql.*;
import com.mysql.cj.jdbc.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class Administrator extends JFrame {

	private JPanel contentPane;
	private JComboBox<String> comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Administrator frame = new Administrator();
					frame.setVisible(true);
					frame.setResizable(false);
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;

	/**
	 * Create the frame.
	 */
	public Administrator() {
		setTitle("Administratie");
		setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\Program Files\\Eclipse\\Workspace\\Practica\\src\\practica\\university.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 564, 465);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setForeground(new Color(64, 0, 64));
		panel.setBackground(new Color(213, 134, 221));
		panel.setBounds(0, 0, 550, 439);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ADMINISTRATOR");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 28));
		lblNewLabel.setBounds(162, 28, 239, 30);
		panel.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("FACULTATE");
		btnNewButton.setForeground(new Color(64, 0, 64));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Facultate fac=new Facultate();
				fac.show();
				
				dispose();
			}
		});
		btnNewButton.setBackground(new Color(230, 230, 250));
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 15));
		btnNewButton.setBounds(52, 100, 147, 40);
		panel.add(btnNewButton);
		
		JButton btnStudenti = new JButton("STUDENTI");
		btnStudenti.setForeground(new Color(64, 0, 64));
		btnStudenti.setActionCommand("STUDENTI");
		btnStudenti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					Studenti stud=new Studenti();
					stud.show();
					
					
					dispose();
			}
		});
		btnStudenti.setFont(new Font("Arial", Font.BOLD, 15));
		btnStudenti.setBackground(new Color(230, 230, 250));
		btnStudenti.setBounds(52, 170, 147, 40);
		panel.add(btnStudenti);
		
		JButton btnBurse = new JButton("BURSE");
		btnBurse.setForeground(new Color(64, 0, 64));
		btnBurse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Burse burse=new Burse();
				burse.show();
				
				dispose();
				
			}
		});
		btnBurse.setFont(new Font("Arial", Font.BOLD, 15));
		btnBurse.setBackground(new Color(230, 230, 250));
		btnBurse.setBounds(52, 240, 147, 40);
		panel.add(btnBurse);
		/*
		JButton btnEditFac = new JButton("EDIT FACULTATE");
		btnEditFac.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditFac fac1=new EditFac();
				fac1.show();
				
				dispose();
				
			}
		});
		btnEditFac.setFont(new Font("Arial", Font.BOLD, 15));
		btnEditFac.setBackground(new Color(230, 230, 250));
		btnEditFac.setBounds(310, 100, 180, 40);
		panel.add(btnEditFac);
		
		JButton btnEditStudenti = new JButton("EDIT STUDENTI");
		btnEditStudenti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditStud stud1=new EditStud();
				stud1.show();
				
				
				dispose();
				
			}
		});
		btnEditStudenti.setFont(new Font("Arial", Font.BOLD, 15));
		btnEditStudenti.setBackground(new Color(230, 230, 250));
		btnEditStudenti.setBounds(310, 170, 180, 40);
		panel.add(btnEditStudenti);
		
		JButton btnEditBurse = new JButton("EDIT BURSE");
		btnEditBurse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				EditBurse burse1=new EditBurse();
				burse1.show();
				
				dispose();
			}
		});
		btnEditBurse.setFont(new Font("Arial", Font.BOLD, 15));
		btnEditBurse.setBackground(new Color(230, 230, 250));
		btnEditBurse.setBounds(310, 240, 180, 40);
		panel.add(btnEditBurse);
		*/
		
		JButton btnNewButton_1 = new JButton("BACK");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login log=new Login();
				log.show();
				dispose();
			}
		});
		btnNewButton_1.setBackground(new Color(230, 230, 250));
		btnNewButton_1.setForeground(new Color(64, 0, 64));
		btnNewButton_1.setFont(new Font("Arial", Font.BOLD, 20));
		btnNewButton_1.setBounds(423, 371, 100, 40);
		panel.add(btnNewButton_1);
		
		
		JButton btnModificari = new JButton("MODIFICARI");
		btnModificari.setForeground(new Color(64, 0, 64));
		btnModificari.setFont(new Font("Arial", Font.BOLD, 15));
		btnModificari.setBackground(new Color(230, 230, 250));
		btnModificari.setBounds(52, 310, 150, 40);
        panel.add(btnModificari);

        btnModificari.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) comboBox.getSelectedItem();
                if (selectedItem.equals("EDIT FACULTATE")) {
                    EditFac editFac = new EditFac();
                    editFac.setVisible(true);
                    dispose();
                } else if (selectedItem.equals("EDIT STUDENTI")) {
                    EditStud editStud = new EditStud();
                    editStud.setVisible(true);
                    dispose();
                } else if (selectedItem.equals("EDIT BURSE")) {
                    EditBurse editBurse = new EditBurse();
                    editBurse.setVisible(true);
                    dispose();
                }
            }
        });
    

	
	/*

		btnModificari.setFont(new Font("Arial", Font.BOLD, 15));
		btn.setBackground(new Color(230, 230, 250));
		Modific.setBounds(52, 310, 147, 40);
		panel.add(Modific);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setForeground(new Color(64, 0, 64));
		comboBox.setFont(new Font("Arial", Font.BOLD, 20));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"EDIT FACULTATE", "EDIT STUDENTI", "EDIT BURSE"}));
		comboBox.setBounds(256, 310, 200, 40);
		panel.add(comboBox);  */
	//} 
	
	 comboBox = new JComboBox<>();
	 comboBox.setForeground(new Color(64, 0, 64));
	 comboBox.setBackground(new Color(230, 230, 250));
	 comboBox.setFont(new Font("Arial", Font.BOLD, 15));
     comboBox.addItem("EDIT FACULTATE");
     comboBox.addItem("EDIT STUDENTI");
     comboBox.addItem("EDIT BURSE");
     comboBox.setBounds(230, 310, 200, 40);
     panel.add(comboBox);
     
     JLabel lblNewLabel_1 = new JLabel("");
     lblNewLabel_1.setIcon(new ImageIcon("D:\\Program Files\\Eclipse\\Workspace\\Practica\\src\\practica\\administrator.png"));
     lblNewLabel_1.setBounds(251, 70, 292, 240);
     panel.add(lblNewLabel_1);
     
     
	 
     
	}
 
	String uname;
    String utype;
	public Administrator(String username, String utype) {
		 initComponents();
         this.uname = username;
         
   
         this.utype = utype;
        
        
         if(utype.equals("admin"))
         {
		
	}

}

	private void initComponents() {
		// TODO Auto-generated method stub
		
	}
}