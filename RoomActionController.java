package hrs_re_2;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

class RoomActionController {
	static void addRoom() {
		if (! allFieldsHaveInput() || ! imageSelected()) {
			return;
		}
		
		if ( ! validateRoomNo() || ! validateFloor() || ! validatePrice()) {
			return;
		}
		
		executeAddQuery();
		JOptionPane.showMessageDialog(null, "Room successfully added!", "Message", JOptionPane.INFORMATION_MESSAGE);
		
		RoomActionDialog RAD = Rooms.ButtonsPanel.getRoomActionDialog();
		RAD.closeRoomActionDialog();
		Rooms.refreshRoomsTable();
		Guest.refreshRoomCards();
	}
	
	static void editRoom() {
		if (! allFieldsHaveInput()) {
			return;
		}
		if (! validateFloor() || ! validatePrice()) {
			return;
		}
		executeEditQuery();
		JOptionPane.showMessageDialog(null, "Room successfully edited!", "Message", JOptionPane.INFORMATION_MESSAGE);
		
		RoomActionDialog RAD = Rooms.ButtonsPanel.getRoomActionDialog();
		RAD.closeRoomActionDialog();
		Rooms.refreshRoomsTable();
		Guest.refreshRoomCards();
	}
	
	static void executeEditQuery() {
		RoomActionDialog.RoomActionPanel RAP = getRoomActionPanel();
		
		String type = RAP.getType();
		String floor =  RAP.getFloor();
		String status = RAP.getStatus();
		String price = RAP.getPrice();
		String roomNo = String.valueOf(Rooms.getSelectedRowID());
		String img_path = RAP.getImgPath();
		String description = RAP.getDescription();
		
		try {			
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement("UPDATE rooms SET type=?, floor=?, status=?, price=?, img_path=?, description=? WHERE room_id=?;");
			st.setString(1, type);
			st.setString(2, floor);
			st.setString(3, status);
			st.setString(4, price);
			st.setString(5, img_path);
			st.setString(6, description);
			st.setString(7, roomNo);
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void executeAddQuery() {
		RoomActionDialog.RoomActionPanel RAP = getRoomActionPanel();

		String roomNo = RAP.getRoomNo();
		String type = RAP.getType();
		String floor = RAP.getFloor();
		String status = RAP.getStatus();
		String price = RAP.getPrice();
		String img_path = RAP.getImgPath().replace("\\", "/");
		String description = RAP.getDescription();
		
		try {
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement("INSERT INTO rooms(room_id, type, floor, status, price, img_path, description) VALUES(?, ?, ?, ?, ?, ?, ?);");
			st.setString(1, roomNo);
			st.setString(2, type);
			st.setString(3, floor);
			st.setString(4, status);
			st.setString(5, price);
			st.setString(6, img_path);
			st.setString(7, description);
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static boolean allFieldsHaveInput() {
		RoomActionDialog.RoomActionPanel RAP = getRoomActionPanel();

		String roomNo = RAP.getRoomNo();
		String floor = RAP.getFloor();
		String price = RAP.getPrice();
		String description = RAP.getDescription();
		if (roomNo.equals("")||floor.equals("")||price.equals("")||description.equals("")) {
			JOptionPane.showMessageDialog(null, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);	
			return false;
		}
		return true;
	}
	
	static boolean validateRoomNo() {
		RoomActionDialog.RoomActionPanel RAP = getRoomActionPanel();

		if (! RAP.getRoomNo().matches("[0-9]+")) {
			JOptionPane.showMessageDialog(null, "Room no must only contain numbers!", "Invalid room no", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if (roomNoExists()) {
			JOptionPane.showMessageDialog(null, "Room no already exists!", "Invalid room number", JOptionPane.ERROR_MESSAGE);	
			return false;
		}
		
		return true;
	}
	
	static boolean roomNoExists() {
		RoomActionDialog.RoomActionPanel RAP = getRoomActionPanel();

		try {
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement("SELECT * FROM rooms WHERE room_id=?;");
			st.setString(1, RAP.getRoomNo());
			ResultSet rs = st.executeQuery();
			
			if (rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	static boolean validateFloor() {
		RoomActionDialog.RoomActionPanel RAP = getRoomActionPanel();

		if (! RAP.getFloor().matches("[0-9]+")) {
			JOptionPane.showMessageDialog(null, "Floor must be a number!", "Invalid floor", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	static boolean validatePrice() {
		RoomActionDialog.RoomActionPanel RAP = getRoomActionPanel();

		if (! RAP.getPrice().matches("[0-9]+")) {
			JOptionPane.showMessageDialog(null, "Price must be a number!", "Invalid price", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	static boolean imageSelected() {
		RoomActionDialog.RoomActionPanel RAP = getRoomActionPanel();
		
		if (RAP.getImgPath().equals("No file chosen")) {
			JOptionPane.showMessageDialog(null, "Please select an image! (recommended size: 370x370)", "No image", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	static void archiveRoom() {
		String id = String.valueOf(Rooms.getSelectedRowID());
		String type = Rooms.getType();
		String floor = String.valueOf(Rooms.getFloor());
		String price = String.valueOf(Rooms.getPrice());
		String status = Rooms.getStatus();
		
		if (inAnotherTable(String.valueOf(id))) {
			return;
		}
		
		if  (isOccupied(status)) {
			return;
		}
		
		if (! confirmArchive(id)) {
			return;
		}
		
		try {
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement("INSERT INTO archivedRooms VALUES(?, ?, ?, ?);");
			st.setString(1, id);
			st.setString(2, type);
			st.setString(3, floor);
			st.setString(4, price);
			st.executeUpdate();
			
			PreparedStatement stDel  = con.prepareStatement("DELETE FROM rooms WHERE room_id=?;");
			stDel.setString(1, id);
			stDel.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Rooms.refreshRoomsTable();
		Guest.refreshRoomCards();
		JOptionPane.showMessageDialog(null, "Room successfully archived!", "Message", JOptionPane.INFORMATION_MESSAGE);
	}
	
	static boolean confirmArchive(String id) {
		int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to archive room " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
		if (result == 0) {
			return true;
		}
		return false;
	}
	
	static boolean isOccupied(String status) {
		if (status.equals("O")) {
			JOptionPane.showMessageDialog(null, "Cannot archive an occupied room!", "Error", JOptionPane.ERROR_MESSAGE);
			return true;
		}
		return false;
	}
	
	static boolean inAnotherTable(String room_id) {
		try {
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement("SELECT * FROM reservations WHERE room_id=?;");
			st.setString(1, room_id);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				JOptionPane.showMessageDialog(null, "Cannot archive a room being used in another table!", "Error", JOptionPane.ERROR_MESSAGE);
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	static RoomActionDialog.RoomActionPanel getRoomActionPanel() {
		RoomActionDialog RAD = Rooms.ButtonsPanel.getRoomActionDialog();
		return RAD.getRoomActionPanel();
	}
}

