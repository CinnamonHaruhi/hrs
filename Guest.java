package hrs_re_2;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.text.SimpleDateFormat;

import javax.swing.*;

@SuppressWarnings("serial")
class Guest extends JPanel{
	 static CardLayout cl = new CardLayout();
	 static JPanel cardPanel = new JPanel(cl);
	 	 
	 static Form form = new Form();
	 static JPanel formPanel = new JPanel();
	 
	 static JPanel roomPanel = new JPanel();
	 static RoomPanel room = new RoomPanel();
	
	Guest() {
		this.setLayout(new BorderLayout());
		cardPanel.setLayout(cl);
		cardPanel.setBackground(Color.WHITE);
		cardPanel.add(new GuestHome(), "home");
		//cardPanel.add(new RoomPanel(), "roomPanel");
		
		formPanel.setLayout(new BorderLayout());
		formPanel.setBorder(BorderFactory.createEmptyBorder(40, 270, 40, 270));
		formPanel.add(form);
		cardPanel.add(formPanel, "forms");
		
		roomPanel.setLayout(new BorderLayout());
		roomPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		roomPanel.add(room);
		cardPanel.add(roomPanel, "roomPanel");
		
		this.add(cardPanel);
	}
	
	static void showFormsPanel() {
		cl.show(cardPanel, "forms");
	}
	
	static void showRoomPanel() {
		cl.show(cardPanel, "roomPanel");
	}
	
	/*
	static void showRoomsPanel() {
		cl.show(cardPanel, "rooms");
	}
	*/
	
	static void showGuestHome() {
		cl.show(cardPanel, "home");
	}
	
	static String getPaymentAmount() {
		return String.valueOf(RoomPanel.getPrice());
	}
	
	static String getRoomNo() {
		return RoomPanel.getRoomNo();
	}
	
	static String getRoomType() {
		return RoomPanel.getType();
	}
	
	static String getFirstName() {
		return form.getFirstName();
	}
	
	static String getLastName() {
		return form.getLastName();
	}
	
	static String getConNum() {
		return form.getContactNumber();
	}
	
	static String getEmail() {
		return form.getEmail();
	}
	
	static String getNoOfPersons() {
		return String.valueOf(form.getNoOfPersons());
	}
	
	static String getCheckInDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		return dateFormat.format(form.getCheckinDate());
	}
	
	static String getCheckOutDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		return dateFormat.format(form.getCheckoutDate());
	}
	
	static void showRoomCard(int index) {
		if (RoomPanel.rooms.length == 1) {
			return;
		}
		
		room.cl.show(room, RoomPanel.roomNames[index]);
	}
	
	static void refreshRoomCards() {
		RoomPanel.retrieveRooms();
		room.addRooms();
	}
}
