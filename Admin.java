package hrs_re_2;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class Admin extends JPanel {
	 static CardLayout cl = new CardLayout();
	 static JPanel cardPanel = new JPanel(cl);
	 static RoomSelection roomSelection  = new RoomSelection();
	 static JPanel formPanel = new JPanel();
	 static Form form = new Form();
	 static Rooms adminRoom = new Rooms();
	 static RequestsPanel requestsPanel = new RequestsPanel();
	 static boolean isAdmin;
	
	Admin() {
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		setFormPanelProperties();
		addPanelsToCard();
		this.add(cardPanel, BorderLayout.CENTER);
	}
	
	private void addPanelsToCard() {
		cardPanel.add(new Reservations(), "reservations");
		cardPanel.add(formPanel, "forms");
		cardPanel.add(roomSelection, "rooms");
		cardPanel.add(adminRoom, "adminRooms");
		cardPanel.add(new Occupants(), "occupants");
		cardPanel.add(requestsPanel, "requests");
	}
	
	private void setFormPanelProperties() {
		formPanel.setLayout(new BorderLayout());;
		formPanel.add(form, BorderLayout.CENTER);
		formPanel.setBackground(Color.WHITE);
		formPanel.setBorder(BorderFactory.createEmptyBorder(30, 270, 30, 270));
	}
	
	static void showReservationsPanel() {
		cl.show(cardPanel, "reservations");
	}
	
	static void showFormsPanel() {
		cl.show(cardPanel, "forms");
	}
	
	static void showRoomsPanel() {
		cl.show(cardPanel, "rooms");
	}
	
	static void showAdminRooms() {
		cl.show(cardPanel, "adminRooms");
	}
	
	static void showOccupantsPanel() {
		cl.show(cardPanel, "occupants");
	}

	static void showRequestsPanel() {
		cl.show(cardPanel, "requests");
	}
	
	static String getPaymentAmount() {
		return roomSelection.getRoomPrice();
	}
	
	static String getRoomNo() {
		return roomSelection.getSelectedRoom();
	}
	
	static String getRoomType() {
		return roomSelection.getRoomType();
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
	
	static void refreshRequests() {
		requestsPanel.removeCancelPanels();
		requestsPanel.requests.clear();
		requestsPanel.retrieveRequests();
		requestsPanel.addRequests();
		requestsPanel.repaint();
		requestsPanel.revalidate();
	}
}
