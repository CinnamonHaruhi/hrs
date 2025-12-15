package hrs_re_2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class Reservations extends JPanel{
	private static Object[] colNames = {"Reference No.", "Room No.", "Occupant", "No. of Persons", "Arrival Date", "Departure Date", "Status", "Payment Amount"};
	private static DefaultTableModel tableModel = new DefaultTableModel(colNames, 0);
	private static CustomTable reservationsTable = new CustomTable(tableModel);
	private JScrollPane scrollPane = new JScrollPane(reservationsTable);
	static ReservationActionDialog RAD;
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	Reservations() {
		this.setLayout(new BorderLayout());
		CustomTable.setScrollPaneProperties(scrollPane);
		ReservationsController.addReservationDataToTable();
		
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(new ButtonsPanel(), BorderLayout.SOUTH);
	}
	
	static String getReservationID() {
		return (String) reservationsTable.getValueAt(reservationsTable.getSelectedRow(), 0);
	}
	
	static String getRoomNo() {
		return String.valueOf(reservationsTable.getValueAt(reservationsTable.getSelectedRow(), 1));
	}
	
	static int getNoOfPersons() {
		return Integer.parseInt((String) reservationsTable.getValueAt(reservationsTable.getSelectedRow(), 3));
	}
	
	static Date getArrivalDate() {
		try {
			Date arrDate = sdf.parse((String) reservationsTable.getValueAt(reservationsTable.getSelectedRow(), 4));
			return arrDate;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	static Date getDepartureDate() {
		try {
			Date depDate = sdf.parse((String) reservationsTable.getValueAt(reservationsTable.getSelectedRow(), 5));
			return depDate;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	static String getPaymentAmount() {
		return String.valueOf(reservationsTable.getValueAt(reservationsTable.getSelectedRow(), 7));
	}
	
	static String getStatus() {
		return String.valueOf(reservationsTable.getValueAt(reservationsTable.getSelectedRow(), 6));
	}
	
	public class ButtonsPanel extends JPanel{
		private JButton createReservationBtn = new JButton("Create reservation");
		private JButton editBtn = new JButton("Edit");
		private JButton archiveBtn = new JButton("Archive");
		private JButton updateBtn = new JButton("Update status");
		
		ButtonsPanel() {
			this.setBackground(Color.WHITE);
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			this.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
			
			applyButtonStyle(createReservationBtn);
			this.add(createReservationBtn);
			
			this.add(Box.createHorizontalGlue());
			applyButtonStyle(editBtn);
			this.add(editBtn);
			this.add(Box.createRigidArea(new Dimension(10, 0)));
			
			applyButtonStyle(archiveBtn);
			this.add(archiveBtn);
			this.add(Box.createRigidArea(new Dimension(10, 0)));
			
			applyButtonStyle(updateBtn);
			this.add(updateBtn);
			
			createReservationBtn.addActionListener((ActionEvent e) -> Admin.showFormsPanel());
			updateBtn.addActionListener((ActionEvent e) -> {
				if (! roomSelected()) {
					return;
				}
				RAD = new ReservationActionDialog("update");
				RAD.showReservationActionDialog();
			});
			
			editBtn.addActionListener((ActionEvent e) -> {
				if (! roomSelected()) {
					return;
				}
				RAD = new ReservationActionDialog("edit");
				RAD.showReservationActionDialog();
			});
			
			archiveBtn.addActionListener((ActionEvent e) -> {
				if (! roomSelected()) {
					return;
				}
				ReservationActionController.archiveReservation();
			});
		}
		
		boolean roomSelected() {
			if (reservationsTable.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Please select a reservation!", "Error!", JOptionPane.ERROR_MESSAGE);	
				return false;
			}
			return true;
		}
		
		void applyButtonStyle(JButton btn) {
			btn.setBackground(Color.decode("#ab782b"));
			btn.setForeground(Color.WHITE);
			btn.setFont(new Font("Verdana", Font.PLAIN, 20));
		}
	}
	
	public static class ReservationsController {
		private static String query = "SELECT * FROM reservations";
		private static Connection con;
		private static PreparedStatement st;
		private static ResultSet rs;
		private static String reservation_id, no_of_persons, arrival_date, departure_date, status;
		private static int occupant_id, room_id, payment_amount;
		
		static void addReservationDataToTable() {	
			executeQuery();
			getTableData();
		}
		
		private static void executeQuery() {
			try {
				con = DBconnection.getConnection();
				st = con.prepareStatement(query);
				rs = st.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private static void getTableData() {
	        tableModel.setRowCount(0);

			try {
				while (rs.next()) {					
					reservation_id = rs.getString("reservation_id");
					occupant_id = rs.getInt("occupant_id");
					no_of_persons = rs.getString("no_of_persons");
					room_id = rs.getInt("room_id");
					arrival_date = rs.getString("arrival_date");
					departure_date = rs.getString("departure_date");
					payment_amount = rs.getInt("payment_amount");
					status = rs.getString("status");
					String occupant_name = getOccupantName(occupant_id);
										
					Object[] data = {reservation_id, 
									room_id, 
									occupant_name,
									no_of_persons,
									arrival_date, 
									departure_date, 
									status, 
									payment_amount};
					tableModel.addRow(data);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		static String getOccupantName(int id) {
			String fname = "";
			String lname = "";
			try {
				PreparedStatement stName = con.prepareStatement("SELECT first_name, last_name FROM occupants WHERE occupant_id=" + id);
				ResultSet rsName = stName.executeQuery();
				
				while (rsName.next()) {
					fname = rsName.getString("first_name");
					lname = rsName.getString("last_name");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lname + ", " + fname;
		}
		
		public static void refreshReservationsTable() {
			addReservationDataToTable();
			reservationsTable.repaint();
		}
	}
}
