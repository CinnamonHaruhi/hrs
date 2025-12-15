package hrs_re_2;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class RoomPanel extends JPanel {
	static Room rooms[];
	static String roomNames[];
	static NoRoomsPanel noRoomsPanel = new NoRoomsPanel();
	CardLayout cl = new CardLayout();
	
	RoomPanel() {
		this.setLayout(cl);		
		retrieveRooms();
		addRooms();
	}
	
	static String getRoomNo() {
		return rooms[Room.getRoomIndex()].roomNo;
	}
	
	static String getType() {
		return rooms[Room.getRoomIndex()].type;
	}
	
	static int getFloor() {
		return rooms[Room.getRoomIndex()].floor;
	}
	
	static int getPrice() {
		return rooms[Room.getRoomIndex()].price;
	}
	
	void addRooms() {
		if (getNoOfRooms() == 0) {
			this.removeAll();
			this.add(noRoomsPanel);			
		} else {
			this.remove(noRoomsPanel);
			
			for (int x = 0; x < rooms.length; x++) {
				this.add(rooms[x], roomNames[x]);
			}
		}		
		this.repaint();
		this.revalidate();
	}
	
	static void retrieveRooms() {				
		try {
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement("SELECT * FROM rooms WHERE status=?;");
			st.setString(1, "VC");
			ResultSet rs = st.executeQuery();

			rooms = new Room[getNoOfRooms()];
			roomNames = new String[getNoOfRooms()];
			int index = 0;
			while (rs.next()) {
				String room_no = rs.getString("room_id");
				String type = rs.getString("type");
				int floor = rs.getInt("floor");
				int price = rs.getInt("price");
				String img_path = rs.getString("img_path");
				String description = rs.getString("description");

				if (rs.getString("status").equals("VC")) {
					rooms[index] = new Room(room_no, type, floor, price, img_path, description);
					roomNames[index] = room_no;
					index++;
				}
			}		
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
	
	static private int getNoOfRooms() {
		int noOfRooms = 0;
		try {
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement("SELECT room_id FROM rooms WHERE status=?;");
			st.setString(1, "VC");
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				++noOfRooms;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return noOfRooms;
	}
}

@SuppressWarnings("serial")
class NoRoomsPanel extends JPanel {
	JLabel noRoomLabel = new JLabel("No rooms available");
	JButton backBtn = new JButton("Back");
	
	NoRoomsPanel() {
		noRoomLabel.setFont(new Font("Verdana", Font.PLAIN, 25));
		this.add(noRoomLabel);
		
		applyButtonStyle(backBtn);
		this.add(backBtn);
		
		backBtn.addActionListener((ActionEvent e) -> Guest.showGuestHome());
	}
	
	   private void applyButtonStyle(JButton btn) {
		   btn.setBackground(Color.decode("#ab782b"));
		   btn.setForeground(Color.WHITE);
		   btn.setFont(new Font("Verdana", Font.PLAIN, 20));
		}
}
