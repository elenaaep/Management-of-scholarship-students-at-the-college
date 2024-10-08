package practica;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

public class EditStud extends JFrame {

    private JPanel contentPane;
    private JTable table;

    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    private JTextField txtIDS;
    private JTextField txtNume;
    private JTextField txtInit_tata;
    private JTextField txtCNP;
    private JTextField txtDN;
    private JTextField txtAnStud;
    private JTextField txtSpec;
    private JTextField txtMed;
    private JComboBox<String> comboBoxIDF;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    EditStud frame = new EditStud();
                    frame.setVisible(true);
                    frame.setResizable(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/practica", "root", "");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void table_load() {
        try {
            pst = con.prepareStatement("SELECT idF FROM facultate");
            rs = pst.executeQuery();

            comboBoxIDF.removeAllItems(); // Golim combobox-ul pentru a evita duplicarea ID-urilor

            while (rs.next()) {
                String facultyID = rs.getString("idF");
                comboBoxIDF.addItem(facultyID);
            }

            // Restul codului pentru încărcarea datelor în tabel
            pst = con.prepareStatement("SELECT * FROM student");
            rs = pst.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    
    
    

    public EditStud() {
    	setTitle("Editari Studenti");
    	setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\Program Files\\Eclipse\\Workspace\\Practica\\src\\practica\\university.png"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1103, 662);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(213, 134, 221));
        panel.setBounds(0, 10, 1104, 652);
        contentPane.add(panel);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(210, 199, 123, 60);
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 String IDs, IDf, Nume,Init_tata, CNP, DN, An_stud, Spec, Med;
            	 
                 IDs = txtIDS.getText();
                 IDf = (String)comboBoxIDF.getSelectedItem();
                 Nume = txtNume.getText();
                 Init_tata = txtInit_tata.getText();
                 CNP=txtCNP.getText();
                 DN=txtDN.getText();
                 An_stud=txtAnStud.getText();
                 Spec=txtSpec.getText();
                 Med=txtMed.getText();

                try {
                    pst = con.prepareStatement("UPDATE student SET idF = ?, nume = ?, init_tata = ?, CNP = ?, dataN = ?, an_studii = ?,"
                    		+ "specializare = ?, media = ? WHERE idS = ?");
                    pst.setString(9, IDs);
                    pst.setString(1, IDf);
                    pst.setString(2, Nume);
                    pst.setString(3, Init_tata);
                    pst.setString(4, CNP);
                    pst.setString(5, DN);
                    pst.setString(6, An_stud);
                    pst.setString(7, Spec);
                    pst.setString(8, Med);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updated!");
                    table_load();
                    clearFields();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        panel.setLayout(null);
        btnUpdate.setFont(new Font("Arial", Font.BOLD, 22));
        btnUpdate.setBackground(new Color(230, 230, 250));
        btnUpdate.setForeground(new Color(64, 0, 64));
        panel.add(btnUpdate);

        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(33, 199, 123, 60);
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	 String IDs, IDf, Nume,Init_tata, CNP, DN, An_stud, Spec, Med;
            	 
                 IDs = txtIDS.getText();
                 IDf = (String)comboBoxIDF.getSelectedItem();
                 Nume = txtNume.getText();
                 Init_tata = txtInit_tata.getText();
                 CNP=txtCNP.getText();
                 DN=txtDN.getText();
                 An_stud=txtAnStud.getText();
                 Spec=txtSpec.getText();
                 Med=txtMed.getText();

                try {
                    pst = con.prepareStatement("INSERT INTO student VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    pst.setString(1, IDs);
                    pst.setString(2, IDf);
                    pst.setString(3, Nume);
                    pst.setString(4, Init_tata);
                    pst.setString(5, CNP);
                    pst.setString(6, DN);
                    pst.setString(7, An_stud);
                    pst.setString(8, Spec);
                    pst.setString(9, Med);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Added!");
                    table_load();
                    clearFields();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnAdd.setForeground(new Color(64, 0, 64));
        btnAdd.setFont(new Font("Arial", Font.BOLD, 22));
        btnAdd.setBackground(new Color(230, 230, 250));
        panel.add(btnAdd);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String bID = txtIDS.getText();

                try {
                    pst = con.prepareStatement("DELETE FROM student WHERE idS = ?");
                    pst.setString(1, bID);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleted!");
                    table_load();
                    clearFields();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnDelete.setBounds(390, 199, 123, 60);
        btnDelete.setForeground(new Color(64, 0, 64));
        btnDelete.setFont(new Font("Arial", Font.BOLD, 22));
        btnDelete.setBackground(new Color(230, 230, 250));
        panel.add(btnDelete);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 279, 1040, 263);
        panel.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);
        
        JButton btnBack = new JButton("Back");
        btnBack.setBounds(940, 553, 120, 50);
        btnBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Administrator adm=new Administrator();
				adm.show();
				
				
				dispose();
        		
        		
        	}

			private void hide() {
				// TODO Auto-generated method stub
				
			}
        });
        btnBack.setForeground(new Color(64, 0, 64));
        btnBack.setFont(new Font("Arial", Font.BOLD, 22));
        btnBack.setBackground(new Color(230, 230, 250));
        panel.add(btnBack);
        
        JLabel lblIDS = new JLabel("ID Student");
        lblIDS.setForeground(new Color(128, 0, 128));
        lblIDS.setBounds(30, 20, 90, 25);
        lblIDS.setFont(new Font("Arial", Font.BOLD, 15));
        panel.add(lblIDS);
        
        txtIDS = new JTextField();
        txtIDS.setBounds(130, 20, 243, 25);
        panel.add(txtIDS);
        txtIDS.setColumns(10);
        
        JLabel lblIdFac = new JLabel("ID Facultate");
        lblIdFac.setForeground(new Color(128, 0, 128));
        lblIdFac.setBounds(30, 60, 102, 25);
        lblIdFac.setFont(new Font("Arial", Font.BOLD, 15));
        panel.add(lblIdFac);
        
        txtNume = new JTextField();
        txtNume.setBounds(130, 100, 245, 25);
        txtNume.setColumns(10);
        panel.add(txtNume);
        
        JLabel lblNume = new JLabel("Nume");
        lblNume.setForeground(new Color(128, 0, 128));
        lblNume.setBounds(30, 100, 86, 25);
        lblNume.setFont(new Font("Arial", Font.BOLD, 15));
        panel.add(lblNume);
        
        JLabel lblinit_tata = new JLabel(" Initiala tata");
        lblinit_tata.setForeground(new Color(128, 0, 128));
        lblinit_tata.setBounds(30, 140, 86, 25);
        lblinit_tata.setFont(new Font("Arial", Font.BOLD, 15));
        panel.add(lblinit_tata);
        
        
        
        txtInit_tata = new JTextField();
        txtInit_tata.setBounds(130, 140, 243, 25);
        txtInit_tata.setColumns(10);
        panel.add(txtInit_tata);
        
        JLabel lblCNP = new JLabel("CNP");
        lblCNP.setForeground(new Color(128, 0, 128));
        lblCNP.setFont(new Font("Arial", Font.BOLD, 15));
        lblCNP.setBounds(710, 20, 90, 25);
        panel.add(lblCNP);
        
        JLabel lblDN = new JLabel("Data nasterii");
        lblDN.setForeground(new Color(128, 0, 128));
        lblDN.setFont(new Font("Arial", Font.BOLD, 15));
        lblDN.setBounds(710, 60, 110, 25);
        panel.add(lblDN);
        
        JLabel lblAnStud = new JLabel("An studii");
        lblAnStud.setForeground(new Color(128, 0, 128));
        lblAnStud.setFont(new Font("Arial", Font.BOLD, 15));
        lblAnStud.setBounds(710, 100, 110, 25);
        panel.add(lblAnStud);
        
        JLabel lblSpec = new JLabel("Specializare");
        lblSpec.setForeground(new Color(128, 0, 128));
        lblSpec.setFont(new Font("Arial", Font.BOLD, 15));
        lblSpec.setBounds(710, 140, 110, 25);
        panel.add(lblSpec);
        
        txtCNP = new JTextField();
        txtCNP.setColumns(10);
        txtCNP.setBounds(820, 20, 243, 25);
        panel.add(txtCNP);
        
        txtDN = new JTextField();
        txtDN.setColumns(10);
        txtDN.setBounds(817, 61, 243, 25);
        panel.add(txtDN);
        
        txtAnStud = new JTextField();
        txtAnStud.setColumns(10);
        txtAnStud.setBounds(817, 100, 243, 25);
        panel.add(txtAnStud);
        
        txtSpec = new JTextField();
        txtSpec.setColumns(10);
        txtSpec.setBounds(817, 140, 243, 25);
        panel.add(txtSpec);
        
        txtMed = new JTextField();
        txtMed.setColumns(10);
        txtMed.setBounds(817, 179, 243, 25);
        panel.add(txtMed);
        
        JLabel lblMed = new JLabel("Media");
        lblMed.setForeground(new Color(128, 0, 128));
        lblMed.setFont(new Font("Arial", Font.BOLD, 15));
        lblMed.setBounds(710, 180, 110, 25);
        panel.add(lblMed);
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\elena\\Desktop\\practica\\ES.png"));
        lblNewLabel.setBounds(483, 20, 130, 130);
        panel.add(lblNewLabel);
        
        comboBoxIDF = new JComboBox<>();
        comboBoxIDF.setBounds(130, 60, 243, 25);
        panel.add(comboBoxIDF);

        Connect();
        table_load();
    }

    private void clearFields() {
       
        
        txtIDS.setText("");
      //  txtIDF.setText("");
        txtNume.setText("");
        txtInit_tata.setText("");
        txtCNP.setText("");
        txtDN.setText("");
        txtAnStud.setText("");
        txtSpec.setText("");
        txtMed.setText("");
        comboBoxIDF.setSelectedIndex(0);
        txtIDS.requestFocus();
    }
}