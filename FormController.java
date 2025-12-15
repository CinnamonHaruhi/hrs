package hrs_re_2;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.apache.commons.text.RandomStringGenerator;

class FormController {
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	static String referenceNo;
	
	static void submitOccupantDetails(Form form) {
		String fname = form.getFirstName();
		String lname = form.getLastName();
		String email = form.getEmail();
		String conNum = form.getContactNumber();
		
		try {
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement("INSERT INTO occupants(first_name, last_name, email, contact_no) VALUES(?, ?, ?, ?);");
			st.setString(1, fname);
			st.setString(2, lname);
			st.setString(3, email);
			st.setString(4, conNum);
			st.executeUpdate();		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		submitReservationDetails(form, getOccupantID(email));
	}
	
	
	static void submitReservationDetails(Form form, String occupantID) {
		String strCheckIn = dateFormat.format(form.getCheckinDate());
		String strCheckOut = dateFormat.format(form.getCheckoutDate());
		String noOfPersons = String.valueOf(form.getNoOfPersons());
		String paymentAmount = String.valueOf(getPaymentAmount()); 
		referenceNo = generateReferenceNumber();
		
		try {
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement("INSERT INTO reservations(reservation_id, occupant_id, no_of_persons, room_id, arrival_date, departure_date, payment_amount) VALUES(?, ?, ?, ?, ?, ?, ?);");
			st.setString(1, referenceNo);
			st.setString(2, occupantID);
			st.setString(3, noOfPersons);
			st.setString(4, getRoomID());
			st.setString(5, strCheckIn);
			st.setString(6, strCheckOut);
			st.setString(7, paymentAmount);
			st.executeUpdate();		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	static void setRoomStatusToOccupied(int id) {
		try {
			Connection con = DBconnection.getConnection();
			PreparedStatement stOccupied = con.prepareStatement("UPDATE rooms SET status=\"O\" WHERE room_id=" + id);
			stOccupied.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
	
	static String getRoomID() {
		if (Admin.isAdmin) {
			return Admin.getRoomNo();
		} else {
			return Guest.getRoomNo();
		}
	}
	
	static int getPaymentAmount() {
		if (Admin.isAdmin) {
			return Integer.parseInt(Admin.getPaymentAmount());
		} else {
			return Integer.parseInt(Guest.getPaymentAmount());
		}
	}
	
	static String getOccupantID(String email) {
		String occupantID = "";
		try {
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement("SELECT occupant_id FROM occupants WHERE email='" + email + "';");
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				occupantID = String.valueOf(rs.getInt("occupant_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return occupantID;
	}

	static String generateReferenceNumber() {
		char[][] allowedCharacterRanges = {{'a','z'},{'A','Z'},{'0','9'}};
		RandomStringGenerator generator = new RandomStringGenerator.Builder()
				.withinRange(allowedCharacterRanges)
				.get();
		String referenceNo = generator.generate(8).toUpperCase();
		return referenceNo;
	} 
	
	static boolean validateEmail(JTextField emailField) {
		if (! emailField.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
			JOptionPane.showMessageDialog(null, "Please enter a valid email!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	static boolean validateTextFields(ArrayList<JTextField> textFields) {
		for (JTextField textField : textFields) {
			if (textField.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Please fill in all required fields!", "Error",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}
	
	static boolean validateNumberField(JTextField textField) {
		String contactNo = textField.getText();
		if (! contactNo.matches("[0-9]+") || contactNo.length() < 11) {
			JOptionPane.showMessageDialog(null, "Invalid number!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	static boolean validateDates(Date checkinDate, Date checkoutDate) throws ParseException {
		Date currentDate = dateFormat.parse(dateFormat.format(new Date()));
		Date cDateWithoutTime = dateFormat.parse(dateFormat.format(checkinDate));
		
		if (cDateWithoutTime.before(currentDate)) {
			JOptionPane.showMessageDialog(null, "Invalid check-in date", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		} 
		
		if (checkoutDate.before(checkinDate)) {
			JOptionPane.showMessageDialog(null, "Check-out date must be after check-in date!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}		
		return true;
	}
}
