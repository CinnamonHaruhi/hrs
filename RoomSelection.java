package hrs_re_2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
class RoomSelection extends JPanel{
	private static Object[] colNames = {"Room No.", "Type", "Floor", "Price"};
	DefaultTableModel tableModel = new DefaultTableModel(colNames, 0);
	CustomTable roomsTable = new CustomTable(tableModel);
	JScrollPane scrollPane = new JScrollPane(roomsTable);
	JLabel selectLabel = new JLabel("Select a room");
	JButton nextBtn = new JButton("Next");
	JButton backBtn = new JButton("Back");
	static Confirm confirmDialog;
	JPanel buttonsPanel = new JPanel();
	
	RoomSelection() {
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());
		setComponentProperties();
		CustomTable.setScrollPaneProperties(scrollPane);
		addComponents();
		addButtonActions(); 
		retrieveRooms();
	} 
	
	private void addComponents() {
		this.add(selectLabel, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(getButtonsPanel(), BorderLayout.SOUTH);
	}
	
	private void setComponentProperties() {
		selectLabel.setFont(new Font("Verdana", Font.BOLD, 25));
		applyButtonStyle(nextBtn);
		applyButtonStyle(backBtn);
	}
	
	void applyButtonStyle(JButton btn) {
		btn.setBackground(Color.decode("#ab782b"));
		btn.setForeground(Color.WHITE);
		btn.setFont(new Font("Verdana", Font.PLAIN, 20));
	}
	
	private JPanel getButtonsPanel() {
		buttonsPanel.setBackground(Color.WHITE);
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonsPanel.add(backBtn);
		buttonsPanel.add(nextBtn);
		return buttonsPanel;
	}
	
	private void retrieveRooms() {				
		try {
			String query = "SELECT * FROM rooms WHERE status=\"VC\"";	
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement(query);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				String room_no = rs.getString("room_id");
				String type = rs.getString("type");
				int floor = rs.getInt("floor");
				int price = rs.getInt("price");
				Object[] data = {room_no, type, floor, price};
				
				if (rs.getString("status").equals("VC")) {
					tableModel.addRow(data);
				}
			}		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	private void addButtonActions() {
		nextBtn.addActionListener((ActionEvent e) -> {
			if (! roomSelected()) {
				return;
			}
			confirmDialog = new Confirm();
			confirmDialog.showConfirmDialog();
		});
		
		backBtn.addActionListener((ActionEvent e) -> Admin.showFormsPanel());
	}
	
	private boolean roomSelected() {
		int selectedRow = roomsTable.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(null, "Please select a room!", "No room selected", JOptionPane.OK_OPTION);
			return false;
		}
		return true;
	}

	String getSelectedRoom() {
		return roomsTable.getModel().getValueAt(roomsTable.getSelectedRow(), 0).toString();
	}
	
	String getRoomPrice() {
		return roomsTable.getModel().getValueAt(roomsTable.getSelectedRow(), 3).toString();
	}
	
	String getRoomType() {
		return roomsTable.getModel().getValueAt(roomsTable.getSelectedRow(), 1).toString();

	}
}
