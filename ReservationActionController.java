package hrs_re_2;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import hrs_re_2.Reservations.ReservationsController;

class ReservationActionController {
	static ReservationActionDialog RAD;;
	static ReservationActionDialog.EditPanel editPanel;
	static ReservationActionDialog.UpdatePanel updatePanel;
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	
	static void editReservation() {
		RAD = Reservations.RAD;
		editPanel = RAD.editPanel;
		
		String reservation_id = Reservations.getReservationID();
		String room_no = editPanel.getRoomNo();
		String no_of_persons = editPanel.getNoOfPersons();
		String arrival_date = dateFormat.format(editPanel.getArrivalDate());
		String departure_date = dateFormat.format(editPanel.getDepartureDate());
		String payment_amount = editPanel.getPaymentAmount();

		try {
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement("UPDATE reservations SET room_id=?, no_of_persons=?, arrival_date=?, departure_date=?, payment_amount=? WHERE reservation_id=?;");
			st.setString(1, room_no);
			st.setString(2, no_of_persons);
			st.setString(3, arrival_date);
			st.setString(4, departure_date);
			st.setString(5, payment_amount);
			st.setString(6, reservation_id);
			st.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ReservationsController.refreshReservationsTable();
		JOptionPane.showMessageDialog(null, "Reservation edited successfully!", "Edit successful", 
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	static boolean validateInputs() {
		RAD = Reservations.RAD;
		editPanel = RAD.editPanel;
		
		return dateIsValid(RAD.editPanel.getArrivalDate(), editPanel.getDepartureDate()) 
				&& paymentIsValid();
	}
	
	static boolean paymentIsValid() {
		RAD = Reservations.RAD;
		editPanel = RAD.editPanel;
		
		if (editPanel.getPaymentAmount().equals("")) {
			JOptionPane.showMessageDialog(null, "Please fill in all required fields!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if (!editPanel.getPaymentAmount().matches("[0-9]+")) {
			JOptionPane.showMessageDialog(null, "Payment amount must be a number!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	static boolean dateIsValid(Date checkinDate, Date checkoutDate) {
		if (checkoutDate.before(checkinDate)) {
			JOptionPane.showMessageDialog(null, "Check-out date must be after check-in date!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}		
		return true;
	}
	
	static void updateStatus() {
		RAD = Reservations.RAD;
		updatePanel = RAD.updatePanel;
		
		String reservation_id = Reservations.getReservationID();
		String status = (String) updatePanel.statusBox.getSelectedItem();
		try {
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement("UPDATE reservations SET status=? WHERE reservation_id=?;");
			st.setString(1, status);
			st.setString(2, reservation_id);
			st.executeUpdate();
			
			updateRoomStatus(Reservations.getRoomNo(), getPrevRoomStatus(Reservations.getRoomNo()), status);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ReservationsController.refreshReservationsTable();
		Occupants.refreshOccupantsTable();
		JOptionPane.showMessageDialog(null, "Reservation updated successfully!", "Update successful", 
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	private static void updateRoomStatus(String room_no, String prevRoomStatus, String reservationStatus) {
		if (reservationStatus.equals("Checked-in") || reservationStatus.equals("Approved")) {
			setRoomStatusTo(room_no, "O");
		}
		
		else if (reservationStatus.equals("Checked-out")) {
			setRoomStatusTo(room_no, "VD");
		}
		
		/*
		else {
			setRoomStatusTo(room_no, "VC");
		}
		*/
		
		Rooms.refreshRoomsTable();
		Guest.refreshRoomCards();
	}
	
	private static String getPrevRoomStatus(String room_no) {
		String status = "";
		try {
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement("SELECT status FROM rooms WHERE room_id=?;");
			st.setString(1, room_no);
			ResultSet rs = st.executeQuery();
			rs.next();
			status = rs.getString("status");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
	
	private static void setRoomStatusTo(String room_no, String status) {
		try {
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement("UPDATE rooms SET status=? WHERE room_id=?;");
			st.setString(1, status);
			st.setString(2, room_no);
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static boolean confirmArchive() {
		int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to archive this reservation?\nThis action will also archive the associated occupant", "Confirm", JOptionPane.YES_NO_OPTION);
		if (result == 0) {
			return true;
		}
		return false;
	}
	
	static void archiveReservation() {
		if (! confirmArchive()) {
			return;
		}
		
		String reservation_id = Reservations.getReservationID();
		String occupant_id = getOccupant(reservation_id);
		try {
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement("DELETE FROM reservations WHERE reservation_id=?");
			st.setString(1, reservation_id);
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		archiveOccupant(reservation_id, occupant_id);
		ReservationsController.refreshReservationsTable();
		Occupants.refreshOccupantsTable();
		JOptionPane.showMessageDialog(null, "Reservation successfully archived!", "Message", JOptionPane.INFORMATION_MESSAGE);
	}
	
	static void archiveOccupant(String reservation_id, String occupant_id) {
		try {
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement("DELETE FROM occupants WHERE occupant_id=?");
			st.setString(1, occupant_id);
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	static String getOccupant(String reservation_id) {
		String occupant_id = null;
		try {
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement("SELECT occupant_id FROM reservations WHERE reservation_id=?");
			st.setString(1, reservation_id);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				occupant_id = String.valueOf(rs.getInt("occupant_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return occupant_id;
	}
	

}
