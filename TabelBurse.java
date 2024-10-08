package practica;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import net.proteanit.sql.DbUtils;

public class TabelBurse extends JFrame {

    private JPanel contentPane;
    private JTextField txtIDS;
    private JTextField txtTipB;
    private JTable table;

    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    private JTextField txtSuma;
    private JTextField txtSearch;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TabelBurse frame = new TabelBurse();
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
            pst = con.prepareStatement("SELECT * FROM burse");
            rs = pst.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TabelBurse() {
    	setTitle("Tabel Burse");
    	setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\Program Files\\Eclipse\\Workspace\\Practica\\src\\practica\\university.png"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 633, 539);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 632, 514);
        panel.setBackground(new Color(213, 134, 221));
        contentPane.add(panel);
        panel.setLayout(null);

        txtIDS = new JTextField();
        txtIDS.setBounds(145, 50, 305, 30);
        panel.add(txtIDS);
        txtIDS.setColumns(10);

        txtTipB = new JTextField();
        txtTipB.setColumns(10);
        txtTipB.setBounds(145, 86, 305, 30);
        panel.add(txtTipB);

        JButton btnFil = new JButton("Filtrare");
        btnFil.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
            	String IDS, TipBursa, Suma;
            	IDS=txtIDS.getText();
            	TipBursa=txtTipB.getText();
            	Suma=txtSuma.getText();
            	

                try {
                    pst = con.prepareStatement("SELECT * FROM burse WHERE  idS= ? and tip_bursa=? and suma=?");
                    pst.setString(1, IDS );
                    pst.setString(2, TipBursa );
                    pst.setString(3, Suma );
                   
                    rs = pst.executeQuery();
                    if (!rs.isBeforeFirst()) {
                        JOptionPane.showMessageDialog(null, "Nu s-au găsit înregistrări conform criteriilor de filtrare.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Filtrare reușită!");
                        table.setModel(DbUtils.resultSetToTableModel(rs));
                        clearFields();
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Eroare în filtrare!");
                }
            }
        });
        btnFil.setForeground(new Color(64, 0, 64));
        btnFil.setFont(new Font("Arial", Font.BOLD, 22));
        btnFil.setBackground(new Color(230, 230, 250));
        btnFil.setBounds(33, 168, 120, 30);
        panel.add(btnFil);


        JScrollPane tblBurse = new JScrollPane();
        tblBurse.setBounds(33, 208, 560, 172);
        panel.add(tblBurse);

        table = new JTable();
        tblBurse.setViewportView(table);
        
        JButton btnBack = new JButton("Back");
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
        btnBack.setBounds(483, 442, 110, 30);
        panel.add(btnBack);
        
        JLabel lblTB = new JLabel("Tip burse");
        lblTB.setFont(new Font("Arial", Font.BOLD, 20));
        lblTB.setForeground(new Color(128, 0, 128));
        lblTB.setBounds(33, 85, 102, 30);
        panel.add(lblTB);
        
        JLabel lblIdStudent = new JLabel("Id Student");
        lblIdStudent.setForeground(new Color(128, 0, 128));
        lblIdStudent.setFont(new Font("Arial", Font.BOLD, 20));
        lblIdStudent.setBounds(33, 50, 102, 30);
        panel.add(lblIdStudent);
        
        JLabel lblicon = new JLabel("");
        lblicon.setIcon(new ImageIcon("C:\\Users\\elena\\Desktop\\practica\\3241410-200.png"));
        lblicon.setBounds(491, 76, 64, 64);
        panel.add(lblicon);
        
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		table_load();
        	}
        });
        btnRefresh.setForeground(new Color(64, 0, 64));
        btnRefresh.setFont(new Font("Arial", Font.BOLD, 20));
        btnRefresh.setBackground(new Color(230, 230, 250));
        btnRefresh.setBounds(483, 20, 110, 30);
        panel.add(btnRefresh);

        
        
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setBackground(new Color(230, 230, 250));
        comboBox.setModel(new DefaultComboBoxModel(new String[] { "Ascendent", "Descendent" }));
        comboBox.setForeground(new Color(64, 0, 64));
        comboBox.setFont(new Font("Arial", Font.BOLD, 20));
        comboBox.setBounds(163, 168, 140, 30);
        panel.add(comboBox);

        JButton btnSort = new JButton("Sorteaza");
    	btnSort.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent e) {
    	        String selectedOption = comboBox.getSelectedItem().toString();
    	        performSort(selectedOption);
    	    }
    	});
    	btnSort.setForeground(new Color(64, 0, 64));
    	btnSort.setFont(new Font("Arial", Font.BOLD, 20));
    	btnSort.setBackground(new Color(230, 230, 250));
    	btnSort.setBounds(318, 168, 120, 30);
    	panel.add(btnSort);
    	
    	
        
        JButton btnPdf = new JButton("PDF");
        btnPdf.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		String path="";
        		JFileChooser j=new JFileChooser();
        		j.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        		int x=j.showSaveDialog(TabelBurse.this);
        		
        		if(x==JFileChooser.APPROVE_OPTION) {
        			path=j.getSelectedFile().getPath();
        			
        		}
        		
        		Document doc=new Document();
        		
        		
        		try {
        			PdfWriter.getInstance(doc, new FileOutputStream(path+"Burse.pdf"));
        			
        			doc.open();
        			
        			PdfPTable tbl=new PdfPTable(8);
        			
        			tbl.addCell("ID burse");
        			tbl.addCell("ID student");
        			tbl.addCell("Tip burse");
        			tbl.addCell("Nr. burse acordate");
        			tbl.addCell("Punctaj");
        			tbl.addCell("Media finala");
        			tbl.addCell("Venit student");
        			tbl.addCell("Suma");
        			
        			JTable table = (JTable) ((JViewport) tblBurse.getComponent(0)).getView();
        			TableModel model = table.getModel();

        			int rowCount = model.getRowCount();
        			int columnCount = model.getColumnCount();

        			for (int row = 0; row < rowCount; row++) {
        			    for (int col = 0; col < columnCount; col++) {
        			        Object cellValue = model.getValueAt(row, col);
        			        String cellText = (cellValue != null) ? cellValue.toString() : "";
        			        tbl.addCell(cellText);
        			    }
        			}

        			doc.add(tbl);
        			
        		}catch(FileNotFoundException ex) {
        			Logger.getLogger(Facultate.class.getName()).log(Level.SEVERE, null, ex);
        		} catch( DocumentException ex) {
        			Logger.getLogger(Facultate.class.getName()).log(Level.SEVERE, null, ex);
        		}
        		
        		doc.close();
        	}
        });
        btnPdf.setForeground(new Color(64, 0, 64));
        btnPdf.setFont(new Font("Arial", Font.BOLD, 22));
        btnPdf.setBackground(new Color(230, 230, 250));
        btnPdf.setBounds(33, 390, 110, 30);
        panel.add(btnPdf);
        
        JLabel lblSuma = new JLabel("Suma");
        lblSuma.setFont(new Font("Arial", Font.BOLD, 20));
        lblSuma.setForeground(new Color(128, 0, 128));
        lblSuma.setBounds(33, 125, 89, 30);
        panel.add(lblSuma);
        
        txtSuma = new JTextField();
        txtSuma.setBounds(145, 125, 305, 30);
        panel.add(txtSuma);
        txtSuma.setColumns(10);
        
        JLabel lblSearch = new JLabel("Cautare");
        lblSearch.setForeground(new Color(128, 0, 128));
        lblSearch.setFont(new Font("Arial", Font.BOLD, 20));
        lblSearch.setBounds(33, 20, 100, 30);
        panel.add(lblSearch);
        
        
        JTextField search = new JTextField();
        search.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyReleased(KeyEvent e) {
        		DefaultTableModel obj=(DefaultTableModel) table.getModel();
        		TableRowSorter<DefaultTableModel> obj1=new TableRowSorter<>(obj);
        		table.setRowSorter(obj1);
        		obj1.setRowFilter(RowFilter.regexFilter(search.getText()));
        		
        	}
        });
        search.setBounds(145, 15, 305, 30);
        panel.add(search);
        search.setColumns(10);
        
        //btn pt export in excel
        JButton btnExportExcel = new JButton("Excel");
        btnExportExcel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exportToExcel();
            }
        });
        btnExportExcel.setForeground(new Color(64, 0, 64));
        btnExportExcel.setFont(new Font("Arial", Font.BOLD, 22));
        btnExportExcel.setBackground(new Color(230, 230, 250));
        btnExportExcel.setBounds(33, 442, 110, 30);
        panel.add(btnExportExcel);
        

        Connect();
        table_load();
    }
    
    
 
    
  //metoda pt a exporta datele din tabel intr-un excel
    private void exportToExcel() {
        try {
            Workbook workbook = new XSSFWorkbook();
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Burse");

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int rowCount = model.getRowCount();
            int columnCount = model.getColumnCount();

            // Create the header row
            org.apache.poi.ss.usermodel.Row headerRow = ((org.apache.poi.ss.usermodel.Sheet) sheet).createRow(0);
            for (int col = 0; col < columnCount; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(model.getColumnName(col));
            }

            // Fill data rows
            for (int row = 0; row < rowCount; row++) {
                org.apache.poi.ss.usermodel.Row excelRow = sheet.createRow(row + 1);
                for (int col = 0; col < columnCount; col++) {
                    Object cellValue = model.getValueAt(row, col);
                    Cell cell = excelRow.createCell(col);
                    if (cellValue != null) {
                        if (cellValue instanceof Number) {
                            cell.setCellValue(((Number) cellValue).doubleValue());
                        } else {
                            cell.setCellValue(cellValue.toString());
                        }
                    }
                }
            }

            // Prompt user to select file location to save the Excel file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");
            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();

                // Write the workbook to the file
                try (FileOutputStream outputStream = new FileOutputStream(filePath + ".xlsx")) {
                    workbook.write(outputStream);
                    JOptionPane.showMessageDialog(null, "Datele au fost exportate cu succes în Excel!");
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Eroare la exportul în Excel!");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la exportul în Excel!");
        }
    }
    
    
  //metoda pt sortarea datelor din tabel
    private void performSort(String selectedOption) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        if (selectedOption.equals("Ascendent")) {
            sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
        } else if (selectedOption.equals("Descendent")) {
            sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(0, SortOrder.DESCENDING)));
        } else {
            sorter.setSortKeys(null);
        }

        sorter.sort();
    }
    
   

    private void clearFields() {
        txtIDS.setText("");
        txtTipB.setText("");
        txtIDS.requestFocus();
    }
}



