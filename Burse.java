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

public class Burse extends JFrame {

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
                    Burse frame = new Burse();
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

    public Burse() {
    	setTitle("Burse");
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

        JButton btnFilTB = new JButton("Filtrare");
        btnFilTB.addActionListener(new ActionListener() {
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
        btnFilTB.setForeground(new Color(64, 0, 64));
        btnFilTB.setFont(new Font("Arial", Font.BOLD, 22));
        btnFilTB.setBackground(new Color(230, 230, 250));
        btnFilTB.setBounds(33, 168, 120, 30);
        panel.add(btnFilTB);


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
        btnBack.setBounds(458, 406, 110, 30);
        panel.add(btnBack);
        
        JLabel lblNewLabel = new JLabel("Tip burse");
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel.setForeground(new Color(128, 0, 128));
        lblNewLabel.setBounds(33, 85, 102, 30);
        panel.add(lblNewLabel);
        
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
        		int x=j.showSaveDialog(Burse.this);
        		
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
        
      
        
      //buton si functionalitatea pentru creearea graficelor 
        JButton btnPieChart = new JButton("Generare Diagramă");
        btnPieChart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createPieChart();
            }
        });
        btnPieChart.setForeground(new Color(64, 0, 64));
        btnPieChart.setFont(new Font("Arial", Font.BOLD, 20));
        btnPieChart.setBackground(new Color(230, 230, 250));
        btnPieChart.setBounds(160, 390, 230, 30);
        panel.add(btnPieChart);
        
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
        
        //btn import excel
        JButton importExcelButton = new JButton("Import Excel");
        importExcelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String filePath = "C:\\Users\\elena\\Desktop\\practica\\Burse.xlsx"; // Se specifica calea către fișierul Excel
                importFromExcel("C:\\Users\\elena\\Desktop\\practica\\Burse.xlsx"); // Se apeleaza metoda pentru importul din Excel
                
            }
        });
        importExcelButton.setForeground(new Color(64, 0, 64));
        importExcelButton.setFont(new Font("Arial", Font.BOLD, 22));
        importExcelButton.setBackground(new Color(230, 230, 250));
        importExcelButton.setBounds(163, 442, 180, 30);
        panel.add(importExcelButton);
         


        Connect();
        table_load();
    }
    
    
    //metoda pt a importa date din excel in tabel si baza de date
    public void importFromExcel(String filePath) {
    try {
        FileInputStream file = new FileInputStream(new File("C:\\Users\\elena\\Desktop\\practica\\Burse.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);

        String insertQuery = "INSERT INTO burse (idB, idS, tip_bursa, punctaj, media_finala, venit_student, suma) VALUES (?, ?, ?, ?, ?,? ?)";
        PreparedStatement pstmt = con.prepareStatement(insertQuery);

        int rowCount = sheet.getLastRowNum();
        for (int i = 1; i <= rowCount; i++) { 
            XSSFRow row = sheet.getRow(i);

            if (row != null) {
                Cell idBCell = row.getCell(0);
                Cell idSCell = row.getCell(1);
                Cell tip_bursaCell = row.getCell(2);
                Cell punctajCell = row.getCell(3);
                Cell media_finalaCell = row.getCell(4);
                Cell venit_studentCell = row.getCell(5);
                Cell sumaCell = row.getCell(6);

                int idB = 0;
                int idS = 0;
                String tip_bursa = "";
                int punctaj = 0;
                int media_finala = 0;
                int venit_student = 0;
                int suma = 0;

                if (idBCell != null && idBCell.getCellType() == CellType.NUMERIC) {
                    idB = (int) idBCell.getNumericCellValue();
                }
                
                if (idSCell != null && idSCell.getCellType() == CellType.NUMERIC) {
                    idS = (int) idSCell.getNumericCellValue();
                }

                if (tip_bursaCell != null && tip_bursaCell.getCellType() == CellType.STRING) {
                    tip_bursa = tip_bursaCell.getStringCellValue();
                }

                if (punctajCell != null && punctajCell.getCellType() == CellType.NUMERIC) {
                    punctaj = (int) punctajCell.getNumericCellValue();
                }

                if (media_finalaCell != null && media_finalaCell.getCellType() == CellType.STRING) {
                    media_finala = (int) media_finalaCell.getNumericCellValue();
                }

                if (venit_studentCell != null && venit_studentCell.getCellType() == CellType.NUMERIC) {
                    venit_student = (int) venit_studentCell.getNumericCellValue();
                }
                
                if (sumaCell != null && sumaCell.getCellType() == CellType.NUMERIC) {
                    suma = (int) sumaCell.getNumericCellValue();
                }

            pstmt.setInt(1, idB);
            pstmt.setInt(2, idS);
            pstmt.setString(3, tip_bursa);
            pstmt.setInt(4, punctaj);
            pstmt.setInt(5, media_finala);
            pstmt.setInt(6, venit_student);
            pstmt.setInt(7, suma);

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
    
    // metoda pentru generarea diagramelor
    private void createPieChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        try {
            pst = con.prepareStatement("SELECT tip_bursa, SUM(suma) AS total_sum FROM burse GROUP BY tip_bursa");
            rs = pst.executeQuery();

            while (rs.next()) {
                String tipBursa = rs.getString("tip_bursa");
                double suma = rs.getDouble("total_sum");
                dataset.setValue(tipBursa, suma);
            }

            JFreeChart chart = ChartFactory.createPieChart(
                    "Diagramă Tip Bursă vs Sumă",
                    dataset,
                    true,
                    true,
                    false);

            ChartPanel chartPanel = new ChartPanel(chart);
            JFrame frame = new JFrame("Diagramă PieChart");
            frame.getContentPane().add(chartPanel);
            frame.setSize(800, 600);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        } catch (SQLException e) {
            e.printStackTrace();
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



