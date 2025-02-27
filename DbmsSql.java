import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

public class DbmsSql {

	private JFrame frmDataCrudOperation;
	private JTable table;
	private JLabel lbId;
	private JLabel lbName;
	private JLabel lbMark;
	private JLabel lbCity;
	private JTextField tfId;
	private JTextField tfName;
	private JTextField tfMark;
	private JTextField tfCity;
	private JButton btnSave;
	private JButton btnUpdate;
	private JButton btnDelete;
	
	private Connection con=null;
	private PreparedStatement pst;
	private ResultSet rs=null;
	private int row=0;
	private JScrollPane scrollPane_1;
	
	public DbmsSql() {
			initialize();
			connection();
			table.setModel(loadData());
	}
	
	private void connection() {
		try {
		 String url="jdbc:mysql://localhost:3306/crudopp"; 
		 String usrname="root";
		 String password="1234";
		 con =DriverManager.getConnection(url,usrname,password);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void clear() {
		try{
			tfId.setText("");
			tfName.setText("");
			tfMark.setText("");
			tfCity.setText("");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void refresh() {
		clear();
	}
	
	private DefaultTableModel loadData() {
		try {
			String query="Select*from crud;";
			
			Statement st=con.createStatement();
			rs=st.executeQuery(query);
			ResultSetMetaData metaData=rs.getMetaData();
			int col=metaData.getColumnCount();
			Vector<String> columnName=new Vector<String>();
			for(int column=0;column<col;column++) {
				columnName.add(metaData.getColumnLabel(column+1));
			}
			DefaultTableModel model=(DefaultTableModel) table.getModel();
			model.setColumnIdentifiers(columnName);
			
			Vector<Vector<Object>> rows=new Vector<>();
			
			while(rs.next()) {
				Vector<Object> newRow=new Vector<Object>();
				for(int i=1;i<=col;i++) {
					newRow.addElement(rs.getObject(i));
				}
				rows.addElement(newRow);	
			}
			
		return new DefaultTableModel(rows,columnName);//
		}  
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void save() {
		try {
			String id=tfId.getText();
			String name=tfName.getText();
			String mark=tfMark.getText();
			String city=tfCity.getText();
			
			
			 if(name==null || name.isEmpty() || name.trim().isEmpty()) {
				 JOptionPane.showMessageDialog(null, "Enter Name");
				 tfName.requestFocus();
				 return;
			 }
			 if(mark==null || mark.isEmpty() || mark.trim().isEmpty()||Integer.parseInt(mark)<0||Integer.parseInt(mark)>500) {
				 JOptionPane.showMessageDialog(null, "Enter Mark \nMark should be 0 to 500");
				 tfMark.requestFocus();
				 return;
			 }
			 if(city==null || city.isEmpty() || city.trim().isEmpty()) {
				 JOptionPane.showMessageDialog(null, "Enter City");
				 tfCity.requestFocus();
				 return;
			 }
			 
//			easy method for insert record variables
			String query="Insert into crud (Name,Mark,City)values(?,?,?);";
			if(id.isEmpty()) {
			pst=con.prepareStatement(query);			
			pst.setString(1, name);
			pst.setString(2,mark);
			pst.setString(3, city);
			
			row=pst.executeUpdate();
			JOptionPane.showMessageDialog(null,String.valueOf("Data Saved Succesfully\nNumber of row affected : "+row),"PopUp",JOptionPane.PLAIN_MESSAGE);
			clear();
			table.setModel(loadData());
			
			}
		 }
		 catch(SQLException a) {
			 a.printStackTrace();
		 }
	}
	private void update() {
		try {
			String id=tfId.getText();
			String name=tfName.getText();
			String mark=tfMark.getText();
			String city=tfCity.getText();
			
			
			 if(name==null || name.isEmpty() || name.trim().isEmpty()) {
				 JOptionPane.showMessageDialog(null, "Enter Name");
				 lbName.requestFocus();
				 return;
			 }
			 if(mark==null || mark.isEmpty() || mark.trim().isEmpty()||Integer.parseInt(mark)<0||Integer.parseInt(mark)>500) {
				 JOptionPane.showMessageDialog(null, "Enter Mark \nMark should be 0 to 500");
				 lbMark.requestFocus();
				 return;
			 }
			 if(city==null || city.isEmpty() || city.trim().isEmpty()) {
				 JOptionPane.showMessageDialog(null, "Enter City");
				 lbCity.requestFocus();
				 return;
			 }
			 
				String query="update crud set Name=?,Mark=?,City=? where id=?";
				if(!tfId.getText().isEmpty()) {
				pst=con.prepareStatement(query);			
				pst.setString(1,tfName.getText());
				pst.setString(2,tfMark.getText());
				pst.setString(3,tfCity.getText());
				pst.setString(4,tfId.getText());
				
				row=pst.executeUpdate();
				
				JOptionPane.showMessageDialog(null,String.valueOf("Data Updated Succesfully\nNumber of row affected : "+row),"PopUp",JOptionPane.PLAIN_MESSAGE);
				clear();
				table.setModel(loadData());
				
				}
			 	
			 }
			 catch(SQLException a) {
				 a.printStackTrace();
			 }
		}
	private void delete() {
		try {
			String id=tfId.getText();
			String name=tfName.getText();
			String mark=tfMark.getText();
			String city=tfCity.getText();
			
			
			 if(name==null || name.isEmpty() || name.trim().isEmpty()) {
				 JOptionPane.showMessageDialog(null, "Enter Name");
				 lbName.requestFocus();
				 return;
			 }
			 if(mark==null || mark.isEmpty() || mark.trim().isEmpty()||Integer.parseInt(mark)<0||Integer.parseInt(mark)>500) {
				 JOptionPane.showMessageDialog(null, "Enter Mark \nMark should be 0 to 500");
				 lbMark.requestFocus();
				 return;
			 }
			 if(city==null || city.isEmpty() || city.trim().isEmpty()) {
				 JOptionPane.showMessageDialog(null, "Enter City");
				 lbCity.requestFocus();
				 return;
			 }
			 
				String query="Delete from crud where id=?";
				if(!tfId.getText().isEmpty()) {
				pst=con.prepareStatement(query);			
				pst.setString(1,tfId.getText());
//				pst.setString(2,tfMark.getText());
//				pst.setString(3,tfCity.getText());
//				pst.setString(4,tfId.getText());
				
				row=pst.executeUpdate();
				
				JOptionPane.showMessageDialog(null,String.valueOf("Data Deleted Succesfully\nNumber of row affected : "+row),"PopUp",JOptionPane.PLAIN_MESSAGE);
				clear();
				table.setModel(loadData());
				
				}
			 	
			 }
			 catch(SQLException a) {
				 a.printStackTrace();
			 }
		}
	
	
	private void initialize() {
		frmDataCrudOperation = new JFrame();
		frmDataCrudOperation.getContentPane().setBackground(new Color(255, 228, 225));
		frmDataCrudOperation.setTitle("Data CRUD operation");
		frmDataCrudOperation.setBounds(100, 100, 839, 395);
		frmDataCrudOperation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDataCrudOperation.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("STUDENT DATABASE ENTRY");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(292, 11, 219, 14);
		frmDataCrudOperation.getContentPane().add(lblNewLabel);
		
		JPanel InputPanel = new JPanel();
		InputPanel.setBackground(SystemColor.activeCaption);
		InputPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		InputPanel.setBounds(10, 36, 326, 271);
		frmDataCrudOperation.getContentPane().add(InputPanel);
		InputPanel.setLayout(null);
		
		lbId = new JLabel("Id");
		lbId.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbId.setBounds(10, 34, 46, 14);
		InputPanel.add(lbId);
		
		lbName = new JLabel("Name");
		lbName.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbName.setBounds(10, 76, 64, 14);
		InputPanel.add(lbName);
		
		lbMark = new JLabel("Mark");
		lbMark.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbMark.setBounds(10, 119, 46, 14);
		InputPanel.add(lbMark);
		
		lbCity = new JLabel("City");
		lbCity.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbCity.setBounds(10, 163, 46, 14);
		InputPanel.add(lbCity);
		
		tfId = new JTextField();
		tfId.setEditable(false);
		tfId.setBounds(84, 31, 209, 20);
		InputPanel.add(tfId);
		tfId.setEnabled(false);
		tfId.setColumns(10);
		
		tfName = new JTextField();
		tfName.setColumns(10);
		tfName.setBounds(84, 73, 209, 20);
		InputPanel.add(tfName);
		
		tfMark = new JTextField();
		tfMark.setColumns(10);
		tfMark.setBounds(84, 116, 209, 20);
		InputPanel.add(tfMark);
		
		tfCity = new JTextField();
		tfCity.setColumns(10);
		tfCity.setBounds(84, 160, 209, 20);
		InputPanel.add(tfCity);
		
		btnSave = new JButton("Save");
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnSave.setBounds(10, 218, 89, 23);
		InputPanel.add(btnSave);
		btnSave.addActionListener(a -> save());
		
		btnUpdate = new JButton("Update");
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnUpdate.setBounds(105, 218, 89, 23);
		InputPanel.add(btnUpdate);
		btnUpdate.addActionListener(b -> update());
		
		btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnDelete.setBounds(204, 218, 89, 23);
		InputPanel.add(btnDelete);
		btnDelete.addActionListener(c -> delete());
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnRefresh.setBounds(527, 318, 89, 23);
		frmDataCrudOperation.getContentPane().add(btnRefresh);
		btnRefresh.addActionListener(d -> refresh());
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(346, 36, 456, 271);
		frmDataCrudOperation.getContentPane().add(scrollPane_1);
		
		table = new JTable();
		scrollPane_1.setViewportView(table);
		table.setBackground(SystemColor.scrollbar);
		table.setFont(new Font("Tahoma", Font.BOLD, 13));
		table.setRowHeight(30);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel model=(DefaultTableModel) table.getModel();
				int index=table.getSelectedRow();
				tfId.setText(model.getValueAt(index,0).toString());
				tfName.setText(model.getValueAt(index,1).toString());
				tfMark.setText(model.getValueAt(index,2).toString());
				tfCity.setText(model.getValueAt(index,3).toString());
			}
			
		});
		
		
}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					DbmsSql window = new DbmsSql();
					window.frmDataCrudOperation.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
