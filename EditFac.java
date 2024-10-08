package practica;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

public class EditFac extends JFrame {

    private JPanel contentPane;
    private JTextField txtIDF;
    private JTextField txtNumeF;
    private JTextField txtAdr;
    private JTextField txtDom;
    private JTable table;

    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    private JTextField txtNrStud;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    EditFac frame = new EditFac();
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
            pst = con.prepareStatement("SELECT * FROM facultate");
            rs = pst.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    
    
    public EditFac() {
    	setTitle("Editari facultate");
    	setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\Program Files\\Eclipse\\Workspace\\Practica\\src\\practica\\university.png"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1085, 539);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(213, 134, 221));
        panel.setBounds(0, 10, 1083, 557);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel lblIdF = new JLabel("ID Facultate");
        lblIdF.setForeground(new Color(128, 0, 128));
        lblIdF.setFont(new Font("Arial", Font.BOLD, 15));
        lblIdF.setBounds(34, 20, 123, 58);
        panel.add(lblIdF);

        JLabel lblNumeF = new JLabel("Nume Facultate");
        lblNumeF.setForeground(new Color(128, 0, 128));
        lblNumeF.setFont(new Font("Arial", Font.BOLD, 15));
        lblNumeF.setBounds(34, 80, 123, 58);
        panel.add(lblNumeF);

        JLabel lblAdr = new JLabel("Adresa");
        lblAdr.setForeground(new Color(128, 0, 128));
        lblAdr.setFont(new Font("Arial", Font.BOLD, 15));
        lblAdr.setBounds(34, 140, 123, 58);
        panel.add(lblAdr);

        JLabel lblDom = new JLabel("Domeniul");
        lblDom.setForeground(new Color(128, 0, 128));
        lblDom.setFont(new Font("Arial", Font.BOLD, 15));
        lblDom.setBounds(34, 200, 123, 58);
        panel.add(lblDom);

        txtIDF = new JTextField();
        txtIDF.setBounds(181, 20, 250, 42);
        panel.add(txtIDF);
        txtIDF.setColumns(10);

        txtNumeF = new JTextField();
        txtNumeF.setColumns(10);
        txtNumeF.setBounds(181, 80, 250, 42);
        panel.add(txtNumeF);

        txtAdr = new JTextField();
        txtAdr.setColumns(10);
        txtAdr.setBounds(181, 140, 250, 42);
        panel.add(txtAdr);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String  NumeF, Adr, Dom;
                String bID, Nr_stud;
                bID = txtIDF.getText();
                NumeF = txtNumeF.getText();
                Adr = txtAdr.getText();
                Dom = txtDom.getText();
                Nr_stud=txtNrStud.getText();

                try {
                    pst = con.prepareStatement("UPDATE facultate SET numeF = ?, adresa = ?, domeniul = ?, nr_studenti = ? WHERE idF = ?");
                    pst.setString(1, NumeF);
                    pst.setString(2, Adr);
                    pst.setString(3, Dom);
                    pst.setString(4, Nr_stud);
                    pst.setString(5, bID);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updated!");
                    table_load();
                    clearFields();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnUpdate.setFont(new Font("Arial", Font.BOLD, 22));
        btnUpdate.setBackground(new Color(230, 230, 250));
        btnUpdate.setForeground(new Color(64, 0, 64));
        btnUpdate.setBounds(175, 345, 123, 79);
        panel.add(btnUpdate);

        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	 String bID, Nr_stud;
            	 String  NumeF, Adr, Dom;
                 bID = txtIDF.getText();
                 NumeF = txtNumeF.getText();
                 Adr = txtAdr.getText();
                 Dom = txtDom.getText();
                 Nr_stud=txtNrStud.getText();

                try {
                    pst = con.prepareStatement("INSERT INTO facultate VALUES (?, ?, ?, ?, ?)");
                    pst.setString(2, NumeF);
                    pst.setString(3, Adr);
                    pst.setString(4, Dom);
                    pst.setString(5, Nr_stud);
                    pst.setString(1, bID);
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
        btnAdd.setBounds(34, 345, 123, 79);
        panel.add(btnAdd);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String bID = txtIDF.getText();

                try {
                    pst = con.prepareStatement("DELETE FROM facultate WHERE idF = ?");
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
        btnDelete.setForeground(new Color(64, 0, 64));
        btnDelete.setFont(new Font("Arial", Font.BOLD, 22));
        btnDelete.setBackground(new Color(230, 230, 250));
        btnDelete.setBounds(312, 345, 123, 79);
        panel.add(btnDelete);

        txtDom = new JTextField();
        txtDom.setColumns(10);
        txtDom.setBounds(181, 200, 250, 42);
        panel.add(txtDom);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(470, 20, 574, 400);
        panel.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);
        
        JLabel lblNrStud = new JLabel("Nr. studenti");
        lblNrStud.setForeground(new Color(128, 0, 128));
        lblNrStud.setFont(new Font("Arial", Font.BOLD, 15));
        lblNrStud.setBounds(34, 260, 123, 58);
        panel.add(lblNrStud);
        
        txtNrStud = new JTextField();
        txtNrStud.setColumns(10);
        txtNrStud.setBounds(181, 260, 250, 42);
        panel.add(txtNrStud);
        
        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Administrator adm=new Administrator();
        		dispose();
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
        btnBack.setBounds(924, 430, 120, 50);
        panel.add(btnBack);

        Connect();
        table_load();
    }

    private void clearFields() {
        txtIDF.setText("");
        txtNumeF.setText("");
        txtAdr.setText("");
        txtDom.setText("");
        txtNrStud.setText("");
        txtIDF.requestFocus();
    }
}