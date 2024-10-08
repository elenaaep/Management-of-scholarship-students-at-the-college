package practica;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import net.proteanit.sql.DbUtils;

public class EditBurse extends JFrame {

    private JPanel contentPane;
    private JTable table;

    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    private JTextField txtIDB;
    private JTextField txtTipB;
    private JTextField txtNrB;
    private JTextField txtPct;
    private JTextField txtMF;
    private JTextField txtVS;
    private JTextField txtSuma;
    private JComboBox<String> comboBoxStudentID;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    EditBurse frame = new EditBurse();
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
        	
        	 pst = con.prepareStatement("SELECT idS FROM student");
             rs = pst.executeQuery();

             comboBoxStudentID.removeAllItems(); // Golim combobox-ul pentru a evita duplicarea ID-urilor

             while (rs.next()) {
                 String studentID = rs.getString("idS");
                 comboBoxStudentID.addItem(studentID);
             }
             
            pst = con.prepareStatement("SELECT * FROM burse");
            rs = pst.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public EditBurse() {
    	setTitle("Editari Burse");
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
                String IDb, IDs, TipB, NrB, Pct, MF, VS, Suma;

                IDb = txtIDB.getText();
                IDs = (String)comboBoxStudentID.getSelectedItem();
                TipB = txtTipB.getText();
                NrB = txtNrB.getText();
                Pct = txtPct.getText();
                MF = txtMF.getText();
                VS = txtVS.getText();
                Suma = txtSuma.getText();

                try {
                    pst = con.prepareStatement("UPDATE burse SET idS = ?, tip_bursa = ?, nr_burse = ?, punctaj = ?, media_finala = ?, venit_student = ?, suma = ? WHERE idB = ?");
                    pst.setString(1, IDs);
                    pst.setString(2, TipB);
                    pst.setString(3, NrB);
                    pst.setString(4, Pct);
                    pst.setString(5, MF);
                    pst.setString(6, VS);
                    pst.setString(7, Suma);
                    pst.setString(8, IDb);

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
            	 String IDb, IDs, TipB,NrB, Pct, MF, VS, Suma;
            	 
            	 IDb = txtIDB.getText();
                 IDs = (String)comboBoxStudentID.getSelectedItem();
                 TipB = txtTipB.getText();
                 NrB = txtNrB.getText();
                 Pct=txtPct.getText();
                 MF=txtMF.getText();
                 VS=txtVS.getText();
                 Suma=txtSuma.getText();
                 

                try {
                    pst = con.prepareStatement("INSERT INTO burse VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                    pst.setString(1, IDb);
                    pst.setString(2, IDs);
                    pst.setString(3, TipB);
                    pst.setString(4, NrB);
                    pst.setString(5, Pct);
                    pst.setString(6, MF);
                    pst.setString(7, VS);
                    pst.setString(8, Suma);
                    
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
        btnDelete.setBounds(390, 199, 123, 60);
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String IDb = txtIDB.getText();

                try {
                    pst = con.prepareStatement("DELETE FROM burse WHERE idB = ?");
                    pst.setString(1, IDb);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleted!");
                    table_load();
                    clearFields();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
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
        
        JLabel lblIDB = new JLabel("ID Bursa");
        lblIDB.setForeground(new Color(128, 0, 128));
        lblIDB.setBounds(30, 20, 90, 25);
        lblIDB.setFont(new Font("Arial", Font.BOLD, 15));
        panel.add(lblIDB);
        
        txtIDB = new JTextField();
        txtIDB.setBounds(130, 20, 243, 25);
        panel.add(txtIDB);
        txtIDB.setColumns(10);
        
        JLabel lblIdS = new JLabel("ID Student");
        lblIdS.setForeground(new Color(128, 0, 128));
        lblIdS.setBounds(30, 60, 102, 25);
        lblIdS.setFont(new Font("Arial", Font.BOLD, 15));
        panel.add(lblIdS);
        
        txtTipB = new JTextField();
        txtTipB.setBounds(130, 100, 245, 25);
        txtTipB.setColumns(10);
        panel.add(txtTipB);
        
        JLabel lblTipB = new JLabel("Tip Bursa");
        lblTipB.setForeground(new Color(128, 0, 128));
        lblTipB.setBounds(30, 100, 86, 25);
        lblTipB.setFont(new Font("Arial", Font.BOLD, 15));
        panel.add(lblTipB);
        
        JLabel lblNrB = new JLabel("Nr. burse");
        lblNrB.setForeground(new Color(128, 0, 128));
        lblNrB.setBounds(30, 140, 86, 25);
        lblNrB.setFont(new Font("Arial", Font.BOLD, 15));
        panel.add(lblNrB);
        
        txtNrB = new JTextField();
        txtNrB.setBounds(130, 140, 243, 25);
        txtNrB.setColumns(10);
        panel.add(txtNrB);
        
        JLabel lblPunctaj = new JLabel("Punctaj");
        lblPunctaj.setForeground(new Color(128, 0, 128));
        lblPunctaj.setFont(new Font("Arial", Font.BOLD, 15));
        lblPunctaj.setBounds(710, 20, 90, 25);
        panel.add(lblPunctaj);
        
        JLabel lblMF = new JLabel("Media finala");
        lblMF.setForeground(new Color(128, 0, 128));
        lblMF.setFont(new Font("Arial", Font.BOLD, 15));
        lblMF.setBounds(710, 60, 110, 25);
        panel.add(lblMF);
        
        JLabel lblVS = new JLabel("Venit student");
        lblVS.setForeground(new Color(128, 0, 128));
        lblVS.setFont(new Font("Arial", Font.BOLD, 15));
        lblVS.setBounds(710, 100, 110, 25);
        panel.add(lblVS);
        
        JLabel lblSuma = new JLabel("Suma");
        lblSuma.setForeground(new Color(128, 0, 128));
        lblSuma.setFont(new Font("Arial", Font.BOLD, 15));
        lblSuma.setBounds(710, 140, 110, 25);
        panel.add(lblSuma);
        
        
        comboBoxStudentID = new JComboBox<>();
        comboBoxStudentID.setBounds(130, 60, 243, 25);
        panel.add(comboBoxStudentID);
        
        txtPct = new JTextField();
        txtPct.setColumns(10);
        txtPct.setBounds(820, 20, 243, 25);
        panel.add(txtPct);
        
        txtMF = new JTextField();
        txtMF.setColumns(10);
        txtMF.setBounds(817, 61, 243, 25);
        panel.add(txtMF);
        
        txtVS = new JTextField();
        txtVS.setColumns(10);
        txtVS.setBounds(817, 100, 243, 25);
        panel.add(txtVS);
        
        txtSuma = new JTextField();
        txtSuma.setColumns(10);
        txtSuma.setBounds(817, 140, 243, 25);
        panel.add(txtSuma);
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\elena\\Desktop\\practica\\EB.png"));
        lblNewLabel.setBounds(475, 24, 165, 141);
        panel.add(lblNewLabel);
        
        
      


        Connect();
        table_load();
    }

    
   
    private void clearFields() {
       
        
        txtIDB.setText("");
      //  txtIDS.setText("");
        txtTipB.setText("");
        txtNrB.setText("");
        txtPct.setText("");
        txtMF.setText("");
        txtVS.setText("");
        txtSuma.setText("");
        
        
        txtIDB.requestFocus();
    }
}



