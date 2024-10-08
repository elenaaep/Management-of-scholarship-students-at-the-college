package practica;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import net.proteanit.sql.DbUtils;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Studenti extends JFrame {
	

    private JPanel contentPane;
    private JTextField txtIDS;
    private JTextField txtCNP;
    private JTable table;

    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    private JTextField search;
    private JTextField txtSpecializare;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Studenti frame = new Studenti();
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
            pst = con.prepareStatement("SELECT * FROM student");
            rs = pst.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Studenti() {
    	setTitle("Studenti");
    	setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\Program Files\\Eclipse\\Workspace\\Practica\\src\\practica\\university.png"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 790, 550);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 776, 514);
        panel.setBackground(new Color(213, 134, 221));
        contentPane.add(panel);
        panel.setLayout(null);

        txtIDS = new JTextField();
        txtIDS.setBounds(148, 45, 305, 20);
        panel.add(txtIDS);
        txtIDS.setColumns(10);

        txtCNP = new JTextField();
        txtCNP.setColumns(10);
        txtCNP.setBounds(148, 70, 305, 20);
        panel.add(txtCNP);

        JButton btnFil= new JButton("Filtrare");
        btnFil.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String IDS, CNP, spec;
            	IDS=txtIDS.getText();
            	CNP=txtCNP.getText();
            	spec=txtSpecializare.getText();
            	

                try {
                    pst = con.prepareStatement("SELECT * FROM student WHERE  idS= ? and CNP=? and specializare=?");
                    pst.setString(1, IDS );
                    pst.setString(2, CNP );
                    pst.setString(3, spec );
                   
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
        btnFil.setBounds(30, 130, 130, 30);
        panel.add(btnFil);
        
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setBackground(new Color(230, 230, 250));
        comboBox.setModel(new DefaultComboBoxModel(new String[] { "Ascendent", "Descendent" }));
        comboBox.setForeground(new Color(64, 0, 64));
        comboBox.setFont(new Font("Arial", Font.BOLD, 20));
        comboBox.setBounds(170, 130, 140, 30);
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
    	btnSort.setBounds(320, 130, 120, 30);
    	panel.add(btnSort);
    	
    	
        

        JScrollPane tblStudenti = new JScrollPane();
        tblStudenti.setBounds(30, 170, 715, 250);
        panel.add(tblStudenti);

        table = new JTable();
        tblStudenti.setViewportView(table);
        
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
        btnBack.setBounds(625, 475, 120, 30);
        panel.add(btnBack);
        
        JLabel lblNF = new JLabel("CNP");
        lblNF.setFont(new Font("Arial", Font.BOLD, 20));
        lblNF.setForeground(new Color(128, 0, 128));
        lblNF.setBounds(30, 70, 120, 20);
        panel.add(lblNF);
        
        JLabel lblDom = new JLabel("ID Student");
        lblDom.setForeground(new Color(128, 0, 128));
        lblDom.setFont(new Font("Arial", Font.BOLD, 20));
        lblDom.setBounds(30, 45, 102, 20);
        panel.add(lblDom);
        
        JLabel lblStud = new JLabel("");
        lblStud.setIcon(new ImageIcon("C:\\Users\\elena\\Desktop\\practica\\35785-200.png"));
        lblStud.setBounds(510, 59, 108, 88);
        panel.add(lblStud);
        
        //btn pt Refresh, atunci cand vrem sa revenim la tabelul cu datele initiale
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		table_load();
        	}
        });
        btnRefresh.setForeground(new Color(64, 0, 64));
        btnRefresh.setFont(new Font("Arial", Font.BOLD, 22));
        btnRefresh.setBackground(new Color(230, 230, 250));
        btnRefresh.setBounds(625, 11, 120, 30);
        panel.add(btnRefresh);
        
        //btn pt export in pdf
        JButton btnPdf = new JButton("PDF");
        btnPdf.setIcon(null);
        btnPdf.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		String path="";
        		JFileChooser j=new JFileChooser();
        		j.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        		int x=j.showSaveDialog(Studenti.this);
        		
        		if(x==JFileChooser.APPROVE_OPTION) {
        			path=j.getSelectedFile().getPath();
        			
        		}
        		
        		Document doc=new Document();
        		
        		
        		try {
        			PdfWriter.getInstance(doc, new FileOutputStream(path+"Studenti.pdf"));
        			
        			doc.open();
        			
        			PdfPTable tbl=new PdfPTable(9);
        			
        			tbl.addCell("ID student");
        			tbl.addCell("ID facultate");
        			tbl.addCell("Nume-prenume");
        			tbl.addCell("Initiala tatalui");
        			tbl.addCell("CNP");
        			tbl.addCell("Data nasterii");
        			tbl.addCell("An studii");
        			tbl.addCell("Specializare");
        			tbl.addCell("Media");
        			
        			JTable table = (JTable) ((JViewport) tblStudenti.getComponent(0)).getView();
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
        btnPdf.setBounds(30, 435, 120, 30);
        panel.add(btnPdf);
        
        
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
        btnExportExcel.setBounds(30, 475, 120, 30);
        panel.add(btnExportExcel);
        
        
        //SearchField si functionalitatea acestuia implementata in field-ul folosit
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
        search.setBounds(148, 20, 305, 20);
        panel.add(search);
        search.setColumns(10);
        
        JLabel lblNewLabel = new JLabel("Cautare");
        lblNewLabel.setForeground(new Color(128, 0, 128));
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel.setBounds(30, 20, 88, 20);
        panel.add(lblNewLabel);
        
        JLabel lblSpec = new JLabel("Specializare");
        lblSpec.setFont(new Font("Arial", Font.BOLD, 20));
        lblSpec.setForeground(new Color(128, 0, 128));
        lblSpec.setBounds(30, 95, 120, 20);
        panel.add(lblSpec);
        
        txtSpecializare = new JTextField();
        txtSpecializare.setBounds(148, 95, 305, 20);
        panel.add(txtSpecializare);
        txtSpecializare.setColumns(10);
        
        
        //buton si functionalitatea pentru creearea graficelor 
        JButton btnGenerareDiagrama = new JButton("Generare Diagramă");
        btnGenerareDiagrama.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DefaultCategoryDataset dataset = createDatasetFromTable();

                JFreeChart chart = ChartFactory.createLineChart(
                        "Diagramă Anul de studii pe specializare",
                        "Specializare",
                        "Anul de studii",
                        dataset,
                        PlotOrientation.VERTICAL,
                        true, true, false);

                ChartPanel chartPanel = new ChartPanel(chart);
                JFrame frame = new JFrame("Diagramă Linie");
                frame.getContentPane().add(chartPanel);
                frame.setSize(800, 600);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });

        btnGenerareDiagrama.setForeground(new Color(64, 0, 64));
        btnGenerareDiagrama.setFont(new Font("Arial", Font.BOLD, 22));
        btnGenerareDiagrama.setBackground(new Color(230, 230, 250));
        btnGenerareDiagrama.setBounds(170, 435, 252, 30);
        panel.add(btnGenerareDiagrama);
        
        
        //btn pt importul excel
        JButton importExcelButton = new JButton("Import Excel");
        importExcelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String filePath = "C:\\Users\\elena\\Desktop\\practica\\StudentTest.xlsx"; // Se specifica calea către fișierul Excel
                importFromExcel("C:\\Users\\elena\\Desktop\\practica\\StudentTest.xlsx"); // Se apeleaza metoda pentru importul din Excel
               
            }
        });
        importExcelButton.setForeground(new Color(64, 0, 64));
        importExcelButton.setFont(new Font("Arial", Font.BOLD, 22));
        importExcelButton.setBackground(new Color(230, 230, 250));
        importExcelButton.setBounds(170, 475, 180, 30);
        panel.add(importExcelButton);

        
        
        Connect();
        table_load();
    }
    
    
  //metoda pt a importa date din excel in tabel si baza de date
    public void importFromExcel(String filePath) {
    try {
        FileInputStream file = new FileInputStream(new File("C:\\Users\\elena\\Desktop\\practica\\StudentTest.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);

        String insertQuery = "INSERT INTO student (idS,idF, nume, init_tata, CNP,dataN, an_studii,specializare, media) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(insertQuery);

        int rowCount = sheet.getLastRowNum();
        for (int i = 1; i <= rowCount; i++) { // Ignorăm prima linie (coloanele)
            XSSFRow row = sheet.getRow(i);

            if (row != null) {
                Cell idSCell = row.getCell(0);
                Cell idFCell = row.getCell(1);
                Cell numeCell = row.getCell(2);
                Cell init_tataCell = row.getCell(3);
                Cell CNPCell = row.getCell(4);
                Cell dataNCell = row.getCell(5);
                Cell an_studiiCell = row.getCell(6);
                Cell specializareCell = row.getCell(7);
                Cell mediaCell = row.getCell(8);

                int idS = 0;
                int idF = 0;
                String nume = "";
                String init_tata = "";
                int CNP = 0;
                java.sql.Date dataN = null;
                int an_studii = 0;
                String specializare = "";
                int media = 0;

                if (idSCell != null && idSCell.getCellType() == CellType.NUMERIC) {
                    idS = (int) idSCell.getNumericCellValue();
                }
                if (idFCell != null && idFCell.getCellType() == CellType.NUMERIC) {
                    idF = (int) idFCell.getNumericCellValue();
                }

                if (numeCell != null && numeCell.getCellType() == CellType.STRING) {
                    nume = numeCell.getStringCellValue();
                }

                if (init_tataCell != null && init_tataCell.getCellType() == CellType.STRING) {
                    init_tata = init_tataCell.getStringCellValue();
                }

                if (CNPCell != null && CNPCell.getCellType() == CellType.NUMERIC) {
                    CNP = (int) CNPCell.getNumericCellValue();
                }

                if (dataNCell != null && dataNCell.getCellType() == CellType.NUMERIC) {
                    double excelDateValue = dataNCell.getNumericCellValue();
                    java.util.Date javaDate = DateUtil.getJavaDate(excelDateValue);
                    java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());

                    dataN = sqlDate;
                }

                
                if (an_studiiCell != null && an_studiiCell.getCellType() == CellType.NUMERIC) {
                   an_studii = (int) an_studiiCell.getNumericCellValue();
                }
                
                if (specializareCell != null && specializareCell.getCellType() == CellType.STRING) {
                    specializare = specializareCell.getStringCellValue();
                }
                
                if (mediaCell != null && mediaCell.getCellType() == CellType.NUMERIC) {
                   media = (int) mediaCell.getNumericCellValue();
                }

            pstmt.setInt(1, idS);
            pstmt.setInt(2, idF);
            pstmt.setString(3, nume);
            pstmt.setString(4, init_tata);
            pstmt.setInt(5, CNP);
            pstmt.setDate(6, dataN);
            pstmt.setInt(7, an_studii);
            pstmt.setString(8, specializare);
            pstmt.setInt(9, media);

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
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Studenti");

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

    // metoda pentru generarea diagramelor
    public DefaultCategoryDataset createDatasetFromTable() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
            pst = con.prepareStatement("SELECT * FROM student");
            rs = pst.executeQuery();

            while (rs.next()) {
                String specializare = rs.getString("specializare");
                int an_studii = rs.getInt("an_studii");
                dataset.addValue(an_studii, "Specializare", specializare);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataset;
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
    
    
    //metoda pentru a goli field-uri dupa ce se face o operatiune asupra lor
    private void clearFields() {
        txtIDS.setText("");
        txtCNP.setText("");
        txtIDS.requestFocus();
    }
}



