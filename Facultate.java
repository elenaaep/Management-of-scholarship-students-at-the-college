package practica;

import java.awt.*;
//import com.lowagie.text.Document;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.hyphenation.TernaryTree.Iterator;
import com.mysql.cj.result.Row;

import net.proteanit.sql.DbUtils;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Facultate extends JFrame {
	

    private JPanel contentPane;
    private JTextField txtDom;
    private JTextField txtNF;
    private JTable table;

    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    private JTextField search;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Facultate frame = new Facultate();
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
           
            // Create a DefaultTableModel with column names
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("ID");
            tableModel.addColumn("Nume facultate");
            tableModel.addColumn("Adresa");
            tableModel.addColumn("Domeniul");
            tableModel.addColumn("Nr. studenti");
            

            // Add rows to the table model
            while (rs.next()) {
                String id = rs.getString("idF");
                String numeF = rs.getString("numeF");
                String adr=rs.getString("adresa");
                String domeniul = rs.getString("domeniul");
                String nrS=rs.getString("nr_studenti");
                tableModel.addRow(new Object[]{id, numeF,adr, domeniul, nrS});
            }

            // Set the table model to the JTable 
            table.setModel(tableModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Facultate() {
    	setTitle("Facultate");
    	setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\Program Files\\Eclipse\\Workspace\\Practica\\src\\practica\\university.png"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 641, 539);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setForeground(new Color(128, 0, 128));
        panel.setBounds(0, 0, 1069, 514);
        panel.setBackground(new Color(213, 134, 221));
        contentPane.add(panel);
        panel.setLayout(null);

        txtDom = new JTextField();
        txtDom.setBounds(193, 110, 260, 35);
        panel.add(txtDom);
        txtDom.setColumns(10);

        txtNF = new JTextField();
        txtNF.setColumns(10);
        txtNF.setBounds(193, 65, 260, 35);
        panel.add(txtNF);

        JButton btnFilTB = new JButton("Filtrare");
        btnFilTB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String NF, dom;
            	NF=txtNF.getText();
            	dom=txtDom.getText();

                try {
                    pst = con.prepareStatement("SELECT * FROM facultate WHERE numeF = ? and domeniul=?");
                    pst.setString(1, NF );
                    pst.setString(2, dom );
                   
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
        btnFilTB.setForeground(new Color(64, 0, 64));
        btnFilTB.setFont(new Font("Arial", Font.BOLD, 22));
        btnFilTB.setBackground(new Color(230, 230, 250));
        btnFilTB.setBounds(23, 160, 120, 30);
        panel.add(btnFilTB);

        JScrollPane tblFacultate = new JScrollPane();
        tblFacultate.setBounds(23, 201, 582, 202);
        panel.add(tblFacultate);

        table = new JTable();
        tblFacultate.setViewportView(table);
        
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
        btnBack.setBounds(485, 453, 120, 30);
        panel.add(btnBack);
        
        JLabel lblNF = new JLabel("Nume facultate");
        lblNF.setFont(new Font("Arial", Font.BOLD, 20));
        lblNF.setForeground(new Color(128, 0, 128));
        lblNF.setBounds(23, 70, 150, 30);
        panel.add(lblNF);
        
        JLabel lblDom = new JLabel("Domeniul");
        lblDom.setForeground(new Color(128, 0, 128));
        lblDom.setFont(new Font("Arial", Font.BOLD, 20));
        lblDom.setBounds(23, 110, 102, 30);
        panel.add(lblDom);
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\elena\\Desktop\\practica\\fac.png"));
        lblNewLabel.setBounds(505, 76, 64, 64);
        panel.add(lblNewLabel);
        
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		table_load();
        		
        	}
        });
        btnRefresh.setForeground(new Color(64, 0, 64));
        btnRefresh.setFont(new Font("Arial", Font.BOLD, 22));
        btnRefresh.setBackground(new Color(230, 230, 250));
        btnRefresh.setBounds(485, 19, 120, 30);
        panel.add(btnRefresh);
        
        JButton btnPdf = new JButton("PDF");
        btnPdf.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		String path="";
        		JFileChooser j=new JFileChooser();
        		j.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        		int x=j.showSaveDialog(Facultate.this);
        		
        		if(x==JFileChooser.APPROVE_OPTION) {
        			path=j.getSelectedFile().getPath();
        			
        		}
        		
        		Document doc=new Document();
        		
        		
        		try {
        			PdfWriter.getInstance(doc, new FileOutputStream(path+"Facultate.pdf"));
        			
        			doc.open();
        			
        			PdfPTable tbl=new PdfPTable(5);
        			
        			tbl.addCell("ID facultate");
        			tbl.addCell("Nume facultate");
        			tbl.addCell("Adresa");
        			tbl.addCell("Domeniul");
        			tbl.addCell("Nr. studenti");
        			
        			JTable table = (JTable) ((JViewport) tblFacultate.getComponent(0)).getView();
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
        btnPdf.setBounds(23, 413, 120, 30);
        panel.add(btnPdf);
        
        search = new JTextField();
        search.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyReleased(KeyEvent e) {
        		DefaultTableModel obj=(DefaultTableModel) table.getModel();
        		TableRowSorter<DefaultTableModel> obj1=new TableRowSorter<>(obj);
        		table.setRowSorter(obj1);
        		obj1.setRowFilter(RowFilter.regexFilter(search.getText()));
        		
        	}
        });
        search.setBounds(193, 20, 260, 35);
        panel.add(search);
        search.setColumns(10);
        
        JLabel cautare = new JLabel("Cautare");
        cautare.setForeground(new Color(128, 0, 128));
        cautare.setFont(new Font("Arial", Font.BOLD, 20));
        cautare.setBounds(23, 20, 84, 40);
        panel.add(cautare);
  
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setModel(new DefaultComboBoxModel(new String[] { "Ascendent", "Descendent" }));
        comboBox.setForeground(new Color(64, 0, 64));
        comboBox.setFont(new Font("Arial", Font.BOLD, 20));
        comboBox.setBounds(170, 160, 140, 30);
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
	btnSort.setBounds(333, 160, 120, 30);
	panel.add(btnSort);
	
	
	JButton btnGenerareDiagrama = new JButton("Generare Diagramă");
	btnGenerareDiagrama.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
	    	
	    	        DefaultCategoryDataset dataset = createDatasetFromTable();
	    	        
	    	        JFreeChart chart = ChartFactory.createBarChart(
	    	                "Diagramă Nr. Studenti pe Facultăți",
	    	                "Facultăți",
	    	                "Nr. Studenti",
	    	                dataset,
	    	                PlotOrientation.VERTICAL,
	    	                true, true, false);

	    	        ChartPanel chartPanel = new ChartPanel(chart);
	    	        JFrame frame = new JFrame("Diagramă Bară");
	    	        frame.getContentPane().add(chartPanel);
	    	        frame.setSize(800, 600);
	    	        frame.setVisible(true);
	    	        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    	    }
	    	

	    
	});
	btnGenerareDiagrama.setForeground(new Color(64, 0, 64));
	btnGenerareDiagrama.setFont(new Font("Arial", Font.BOLD, 22));
	btnGenerareDiagrama.setBackground(new Color(230, 230, 250));
	btnGenerareDiagrama.setBounds(170, 413, 252, 30);
	panel.add(btnGenerareDiagrama);

	
	
	JButton btnExportExcel = new JButton("Excel");
    btnExportExcel.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            exportToExcel();
        }
    });
    btnExportExcel.setForeground(new Color(64, 0, 64));
    btnExportExcel.setFont(new Font("Arial", Font.BOLD, 22));
    btnExportExcel.setBackground(new Color(230, 230, 250));
    btnExportExcel.setBounds(23, 453, 120, 30);
    panel.add(btnExportExcel);
    
    JButton importExcelButton = new JButton("Import Excel");
    importExcelButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String filePath = "C:\\Users\\elena\\Desktop\\practica\\Test.xlsx"; // Specificați calea către fișierul Excel
            importFromExcel("C:\\Users\\elena\\Desktop\\practica\\Test.xlsx"); // Apelați metoda pentru importul din Excel
            // Adăugați cod suplimentar aici pentru reîmprospătarea tabelului sau manipularea datelor în alt mod
        }
    });
    importExcelButton.setForeground(new Color(64, 0, 64));
    importExcelButton.setFont(new Font("Arial", Font.BOLD, 22));
    importExcelButton.setBackground(new Color(230, 230, 250));
    importExcelButton.setBounds(168, 453, 180, 30);
    panel.add(importExcelButton); // Adăugați butonul în panoul dvs. sau în locul potrivit al interfeței grafice

    
    

        Connect();
        table_load();
    }
    
    
    //metoda pt a importa date din excel in tabel si baza de date
    public void importFromExcel(String filePath) {
    try {
        FileInputStream file = new FileInputStream(new File("C:\\Users\\elena\\Desktop\\practica\\Test.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);

        String insertQuery = "INSERT INTO facultate (idF, numeF, adresa, domeniul, nr_studenti) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(insertQuery);

        int rowCount = sheet.getLastRowNum();
        for (int i = 1; i <= rowCount; i++) { // Ignorăm prima linie (coloanele)
            XSSFRow row = sheet.getRow(i);

            if (row != null) {
                Cell idCell = row.getCell(0);
                Cell nameCell = row.getCell(1);
                Cell addressCell = row.getCell(2);
                Cell domainCell = row.getCell(3);
                Cell studentCountCell = row.getCell(4);

                int idF = 0;
                String numeF = "";
                String adresa = "";
                String domeniul = "";
                int nr_studenti = 0;

                if (idCell != null && idCell.getCellType() == CellType.NUMERIC) {
                    idF = (int) idCell.getNumericCellValue();
                }

                if (nameCell != null && nameCell.getCellType() == CellType.STRING) {
                    numeF = nameCell.getStringCellValue();
                }

                if (addressCell != null && addressCell.getCellType() == CellType.STRING) {
                    adresa = addressCell.getStringCellValue();
                }

                if (domainCell != null && domainCell.getCellType() == CellType.STRING) {
                    domeniul = domainCell.getStringCellValue();
                }

                if (studentCountCell != null && studentCountCell.getCellType() == CellType.NUMERIC) {
                    nr_studenti = (int) studentCountCell.getNumericCellValue();
                }

            pstmt.setInt(1, idF);
            pstmt.setString(2, numeF);
            pstmt.setString(3, adresa);
            pstmt.setString(4, domeniul);
            pstmt.setInt(5, nr_studenti);

            pstmt.executeUpdate();
        }

        pstmt.close();
        workbook.close();
        file.close();
    } 
    }catch (Exception e) {
        e.printStackTrace();
    }

    }
   
    
    //metoda pt a exporta datele din tabel intr-un excel
    private void exportToExcel() {
        try {
            Workbook workbook = new XSSFWorkbook();
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Facultate");

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

    
    public DefaultCategoryDataset createDatasetFromTable() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
            pst = con.prepareStatement("SELECT * FROM facultate");
            rs = pst.executeQuery();

            while (rs.next()) {
                String numeF = rs.getString("numeF");
                int nrStudenti = rs.getInt("nr_studenti");
                dataset.addValue(nrStudenti, "Facultati", numeF);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataset;
    }

    
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
        txtDom.setText("");
        txtNF.setText("");
        txtDom.requestFocus();
        
        
    }
}



