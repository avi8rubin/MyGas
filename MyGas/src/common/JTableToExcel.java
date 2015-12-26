package common;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 * Class was made to export tables to excel files easily way.
 * Current author made changes to computable the class to MyGas needs.

 *  An ERROR message pop-up when open the export excel file
 * 
 * "The file you are trying to open, '[filename]', is in a different format than specified by the file extension.
 * Verify that the file is not corrupted and is from a trusted source before opening the file. 
 * Do you want to open the file now?"  (Yes | No | Help)"
 * 
 * To solve that problem you need, NOT MUST, to do as follow:
 *  Open your Registry (Start -> Run -> regedit.exe)
 *  Navigate to HKEY_CURRENT_USER\SOFTWARE\MICROSOFT\OFFICE\12.0\EXCEL\SECURITY
 * 	Right click in the right window and choose New -> DWORD
 * 	Type “ExtensionHardening” as the name (without the quotes)
 * 	Verify that the data has the value 0
 * 
 * @author Zino Ohad 
 * Original author Mr. Xymon 
 */
public class JTableToExcel extends JFrame{

	private static final long serialVersionUID = 1L;
	DefaultTableModel TableModel;
	JTable Table;
	JButton Export;
 
	public JTableToExcel(JButton Export, DefaultTableModel TableModel){
		this.Export = Export;
		this.TableModel = TableModel;
		Table = new JTable(TableModel);
		Export.setMnemonic(KeyEvent.VK_X);
		Export.addActionListener(new AksyonListener());	
	}
	public JTableToExcel(JButton Export, JTable Table){
		this.Export = Export;
		this.Table = Table;
		Export.setMnemonic(KeyEvent.VK_X);
		Export.addActionListener(new AksyonListener());	
	}
	public JTableToExcel(JButton Export, String Data[][], String Header[]){
		this.Export = Export;
		TableModel = new DefaultTableModel(Data, Header);
		Table = new JTable(TableModel);
		Export.setMnemonic(KeyEvent.VK_X);
		Export.addActionListener(new AksyonListener());	
	}
    public void toExcel(JTable table, File file){
		try{
			TableModel model = table.getModel();
			FileWriter excel = new FileWriter(file);

			for(int i = 0; i < model.getColumnCount(); i++){
				excel.write(model.getColumnName(i) + "\t");
			}

			excel.write("\n");

			for(int i=0; i< model.getRowCount(); i++) {
				for(int j=0; j < model.getColumnCount(); j++) {
					excel.write(model.getValueAt(i,j).toString()+"\t");
				}
				excel.write("\n");
			}

			excel.close();
		}catch(IOException e){ System.out.println(e); }
	}
    private void ApproveMassage(){
    	JOptionPane.showMessageDialog(this,	
				"File was create successfully.");
    }
    class AksyonListener implements ActionListener{
    	public AksyonListener(){}
	    public void actionPerformed(ActionEvent e){
			
                JFileChooser fc = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("Excel Files","xlsx","xls");
                fc.addChoosableFileFilter(filter);
                int option = fc.showSaveDialog(JTableToExcel.this);				
                if(option == JFileChooser.APPROVE_OPTION){
                    String filename = fc.getSelectedFile().getName(); 
                    String path = fc.getSelectedFile().getParentFile().getPath();

					int len = filename.length();
					String ext1 = "";
					String ext2 = "";
					String file = "";

					if(len > 4){
						ext1 = filename.substring(len-4, len);
						ext2 = filename.substring(len-5, len);
					}

					if(ext1.equals(".xls"))
						file = path + "\\" + filename; 
					else if (ext2.equals(".xlsx"))
						file = path + "\\" + filename;							
					else{
						file = path + "\\" + filename + ".xls"; 
					}
					toExcel(Table, new File(file));
					ApproveMassage();
			}
		}
	}
}