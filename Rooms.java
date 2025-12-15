package hrs_re_2;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;


@SuppressWarnings("serial")
class Rooms extends JPanel {
	private static Object[] colNames = {"Room No.", "Type", "Floor", "Price", "Status", "Image", "Description"};
	static DefaultTableModel tableModel = new DefaultTableModel(colNames, 0);
	private static CustomTable roomsTable = new CustomTable(tableModel);
	private JScrollPane scrollPane = new JScrollPane(roomsTable);

	Rooms() {		
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
		retrieveRooms();
		CustomTable.setScrollPaneProperties(scrollPane);
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(new ButtonsPanel(), BorderLayout.SOUTH);
	}
	
	private static void retrieveRooms() {
        tableModel.setRowCount(0);

		try {
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement("SELECT * FROM rooms;");
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				String room_no = rs.getString("room_id");
				String type = rs.getString("type");
				int floor = rs.getInt("floor");
				int price = rs.getInt("price");
				String status = rs.getString("status");
				String img_path = rs.getString("img_path");
				String description = rs.getString("description");
			
				Object[] data = {room_no, type, floor, price, status, img_path, description};
				tableModel.addRow(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static int getSelectedRow() {
		return roomsTable.getSelectedRow();
	}
	
	static int getSelectedRowID() {
		return Integer.parseInt(roomsTable.getValueAt(getSelectedRow(), 0).toString());
	}
	
	static String getType() {
		return (String) roomsTable.getValueAt(getSelectedRow(), 1);
	}
	
	static int getFloor() {
		return Integer.parseInt(roomsTable.getValueAt(getSelectedRow(), 2).toString());
	}
	
	static int getPrice() {
		return Integer.parseInt(roomsTable.getValueAt(getSelectedRow(), 3).toString());
	} 
	
	static String getStatus() {
		return (String) roomsTable.getValueAt(getSelectedRow(), 4);
	}
	
	static String getImg() {
		return (String) roomsTable.getValueAt(getSelectedRow(), 5);
	}
	
	static String getDescription() {
		return (String) roomsTable.getValueAt(getSelectedRow(), 6);
	}
	
	static void refreshRoomsTable() {
		retrieveRooms();
		roomsTable.repaint();
	}
	
	class ButtonsPanel extends JPanel {
		static RoomActionDialog RAD;
		private JButton editBtn = new JButton("Edit");
		private JButton addBtn = new JButton("Add");
		private JButton archiveBtn = new JButton("Archive");
		
		ButtonsPanel() {
			this.setLayout(new FlowLayout(FlowLayout.RIGHT));
			this.setBackground(Color.WHITE);
			
			applyButtonStyle(addBtn);
			this.add(addBtn);
			
			applyButtonStyle(editBtn);
			this.add(editBtn);
	
			applyButtonStyle(archiveBtn);
			this.add(archiveBtn);
			
			editBtn.addActionListener((ActionEvent e) -> {
				if (! roomSelected()) {
					return;
				}
				RAD = new RoomActionDialog("edit");		
				RAD.showRoomActionDialog();
			});
			
			addBtn.addActionListener((ActionEvent e) -> {
				RAD = new RoomActionDialog("add");
				RAD.showRoomActionDialog();
			});
			
			archiveBtn.addActionListener((ActionEvent e) -> {
				if (! roomSelected()) {
					return;
				}
				RoomActionController.archiveRoom();
			});
		}
		
		void applyButtonStyle(JButton btn) {
			btn.setBackground(Color.decode("#ab782b"));
			btn.setForeground(Color.WHITE);
			btn.setFont(new Font("Verdana", Font.PLAIN, 20));
		}
		
		boolean roomSelected() {
			if (getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Please select a room!", "Error!", JOptionPane.ERROR_MESSAGE);	
				return false;
			}
			return true;
		}
		
		static RoomActionDialog getRoomActionDialog() {
			return RAD;
		}
	}
}

