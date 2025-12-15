package hrs_re_2;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import hrs_re_2.Reservations.ReservationsController;

@SuppressWarnings("serial")
class Request extends JPanel {
	JLabel idLabel = new JLabel();
	JLabel reasonLabel = new JLabel();
	JButton approveBtn = new JButton("Approve");
	JButton declineBtn = new JButton("Decline");
	String reservationID;
	
	Request(String reservationID, String reason) {
		this.setLayout(new GridLayout(1, 3, 10, 0));
		this.setMinimumSize(new Dimension(1000, 40));
		this.setMaximumSize(new Dimension(1200, 60));
		this.setBorder(BorderFactory.createMatteBorder(3, 0, 6, 0, Color.WHITE));
		this.setBackground(Color.decode("#ab782b"));
		setComponentProperties();
		this.reservationID = reservationID;
		
		idLabel.setText(reservationID);
		this.add(idLabel);
		
		reasonLabel.setText(reason);
		this.add(reasonLabel);
		
		this.add(getButtonPanel());
		
		approveBtn.addActionListener((ActionEvent e) -> {
			if (! confirmCancel()) {
				return;
			}
			
			cancelReservation(reservationID);
			deleteCancelRequest(reservationID);
			Admin.requestsPanel.remove(this);
			Admin.refreshRequests();
			ReservationsController.refreshReservationsTable();
			JOptionPane.showMessageDialog(null, "Cancelled successfully!", "Message", 
					JOptionPane.INFORMATION_MESSAGE);
		});
		
		declineBtn.addActionListener((ActionEvent e) -> {
			if (! confirmDecline()) {
				return;
			}
			
			deleteCancelRequest(reservationID);
			Admin.requestsPanel.remove(this);
			Admin.refreshRequests();
			JOptionPane.showMessageDialog(null, "Declined successfully!", "Message", 
					JOptionPane.INFORMATION_MESSAGE);
		});
	}
	
	boolean confirmCancel() {
		int result = JOptionPane.showConfirmDialog(null, 
				"Are you sure you want to approve this cancel request?", "Reservation ID: " + idLabel.getText(), 
				JOptionPane.OK_CANCEL_OPTION);
		if (result == 0) {
			return true;
		}
		return false;
	}
	
	void cancelReservation(String reservationID) {
		try {
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement("UPDATE reservations SET status=? WHERE reservation_id=?;");
			st.setString(1, "Cancelled");
			st.setString(2, reservationID);
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	boolean confirmDecline() {
		int result = JOptionPane.showConfirmDialog(null, 
				"Are you sure you want to decline this cancel request?", "Reservation ID: " + idLabel.getText(), 
				JOptionPane.OK_CANCEL_OPTION);
		
		if (result == 0) {
			return true;
		}
		return false;
	}
	
	void deleteCancelRequest(String reservationID) {
		try {
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement("DELETE FROM cancelRequests WHERE reservation_id=?;");
			st.setString(1, reservationID);
			st.executeUpdate(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void setComponentProperties() {
		JLabel[] labels = {idLabel, reasonLabel};
		for (JLabel label : labels) {
			label.setForeground(Color.WHITE);
			label.setFont(new Font("Verdana", Font.PLAIN, 20));
			label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		}
		declineBtn.setAlignmentY(Component.CENTER_ALIGNMENT);
		applyButtonStyle(declineBtn);
		approveBtn.setAlignmentY(Component.CENTER_ALIGNMENT);
		applyButtonStyle(approveBtn);
	}
	
	JPanel getButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.decode("#ab782b"));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(declineBtn);
		buttonPanel.add(approveBtn);
		return buttonPanel;
	}
	
	void applyButtonStyle(JButton btn) {
		btn.setPreferredSize(new Dimension(145, 40));
		btn.setBackground(Color.decode("#433217"));
		btn.setForeground(Color.WHITE);
		btn.setFont(new Font("Verdana", Font.PLAIN, 20));
	}
}