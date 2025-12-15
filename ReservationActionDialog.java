package hrs_re_2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
class ReservationActionDialog extends JDialog {
	UpdatePanel updatePanel = new UpdatePanel();
	EditPanel editPanel = new EditPanel();
	
	ReservationActionDialog(String actionType) {
		super(Main.frame, "", true);
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		
		if (actionType.equals("update")) {
			this.getContentPane().setBackground(Color.decode("#ab782b"));
			this.setSize(400, 200);
			this.setResizable(false);
			this.add(updatePanel.updateTitle, BorderLayout.NORTH);
			this.add(updatePanel, BorderLayout.CENTER);
			this.setTitle("Update reservation");
		} else {
			this.setSize(460, 380);
			this.setResizable(false);
			this.add(editPanel.getTitlePanel(), BorderLayout.NORTH);
			this.add(editPanel, BorderLayout.CENTER);
			this.setTitle("Edit reservation");
		}
		this.setLocationRelativeTo(null);
	}
	
	void showReservationActionDialog() {
		this.setVisible(true);
	}
	
	void closeReservationActionDialog() {
		this.dispose();
	}
	
	class UpdatePanel extends JPanel {
		private JLabel updateTitle = new JLabel("Update reservation status");
		private String[] statuses = {"Pending", "Approved", "Paid", "Checked-in", "Checked-out", "Cancelled"};
		JComboBox<String> statusBox = new JComboBox<>(statuses);
		private JButton updateBtn = new JButton("Update");
		private JButton cancelBtn = new JButton("Cancel");
		GridBagConstraints gbc = new GridBagConstraints();

		UpdatePanel() {
			this.setLayout(new GridBagLayout());
			this.setBackground(Color.decode("#ab782b"));
			setComponentValue();
			setComponentProperties();
			
			gbc.gridy = 0;
			gbc.gridx = 0;
			gbc.gridwidth = 2;
			gbc.insets = new Insets(0, 0, 10, 0);
			this.add(statusBox, gbc);
			
			gbc.gridy = 1;
			gbc.gridx = 0;
			gbc.gridwidth = 1;
			gbc.insets = new Insets(0, 0, 0, 5);
			this.add(cancelBtn, gbc);
			
			gbc.gridx = 1;
			gbc.insets = new Insets(0, 5, 0, 0);
			this.add(updateBtn, gbc);
			
			updateBtn.addActionListener((ActionEvent e) -> {
				ReservationActionController.updateStatus();
				Reservations.RAD.closeReservationActionDialog();
			});
			
			cancelBtn.addActionListener((ActionEvent e) -> Reservations.RAD.closeReservationActionDialog());
		}
		
		private void setComponentValue() {
			statusBox.setSelectedItem(Reservations.getStatus());
		}
		
		private void setComponentProperties() {
			updateTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
			updateTitle.setHorizontalAlignment(JLabel.CENTER);
			updateTitle.setForeground(Color.WHITE);
			updateTitle.setFont(new Font("Verdana", Font.BOLD, 25));
			
			statusBox.setPreferredSize(new Dimension(214, 40));
			statusBox.setFont(new Font("Verdana", Font.PLAIN, 20));
			
			applyButtonStyle(cancelBtn);
			applyButtonStyle(updateBtn);
		}
		
		private void applyButtonStyle(JButton btn) {
			btn.setBackground(Color.decode("#433217"));
			btn.setForeground(Color.WHITE);
			btn.setFont(new Font("Verdana", Font.PLAIN, 20));
		}
	}
	
	class EditPanel extends JPanel {
		Calendar calendar = Calendar.getInstance();

		JPanel titlePanel = new JPanel();
		JLabel editTitle = new JLabel("Edit reservation");
		
		ArrayList<JLabel> labels = new ArrayList<>();
		private JLabel roomNoLabel = new JLabel("Room no:");
		private JLabel noOfPersonsLabel = new JLabel("No of persons:");
		private JLabel arrDateLabel = new JLabel("Arrival date:");
		private JLabel depDateLabel = new JLabel("Departure date:");
		private JLabel payLabel = new JLabel("Payment amount:");
		
		private int noOfRooms;
		private String[] rooms;
		private JComboBox<String> roomNoBox;
		
		ArrayList<JSpinner> spinners = new ArrayList<>();
		SpinnerNumberModel numberModel = new SpinnerNumberModel(1, 1, 3, 1);
		private JSpinner noOfPersonsSpinner = new JSpinner(numberModel);
		private JSpinner arrDateSpinner = new JSpinner();
		private JSpinner depDateSpinner = new JSpinner();
		JTextField payAmountField = new JTextField();
		
		private JPanel buttonsPanel = new JPanel();
		private JButton cancelBtn = new JButton("Cancel");
		private JButton editBtn = new JButton("Edit");
		
		private GridBagConstraints gbc = new GridBagConstraints();
	
		EditPanel() {
			this.setLayout(new GridBagLayout());
			this.setBackground(Color.decode("#ab782b"));
			
			getRooms();
			addLabelsToArray();
			addSpinnersToArray();
			setComponentProperties();
			setComponentValues();
			
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.insets = new Insets(0, 0, 10, 10);
			this.add(roomNoLabel, gbc);
			gbc.gridx = 1;
			gbc.insets = new Insets(0, 0, 10, 0);
			this.add(roomNoBox, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.insets = new Insets(0, 0, 10, 10);
			this.add(noOfPersonsLabel, gbc);
			gbc.gridx = 1;
			gbc.insets = new Insets(0, 0, 10, 0);
			this.add(noOfPersonsSpinner, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.insets = new Insets(0, 0, 10, 10);
			this.add(arrDateLabel, gbc);
			gbc.gridx = 1;
			gbc.insets = new Insets(0, 0, 10, 0);
			this.add(arrDateSpinner, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 3;
			gbc.insets = new Insets(0, 0, 10, 10);
			this.add(depDateLabel, gbc);
			gbc.gridx = 1;
			gbc.insets = new Insets(0, 0, 10, 0);
			this.add(depDateSpinner, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 4;
			gbc.insets = new Insets(0, 0, 10, 10);
			this.add(payLabel, gbc);
			gbc.gridx = 1;
			gbc.insets = new Insets(0, 0, 10, 0);
			this.add(payAmountField, gbc);
			
			gbc.gridx = 0;
			gbc.gridwidth = 2;
			gbc.gridy = 5;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			this.add(getButtonsPanel(), gbc);
			
			editBtn.addActionListener((ActionEvent e) -> {
				if (! ReservationActionController.validateInputs()) {
					return;
				}
				ReservationActionController.editReservation();
				Reservations.RAD.closeReservationActionDialog();
			});
			
			cancelBtn.addActionListener((ActionEvent e) -> Reservations.RAD.closeReservationActionDialog());
		}
		
		String getRoomNo() {
			return (String) roomNoBox.getSelectedItem();
		}
		
		String getNoOfPersons() {
			return String.valueOf(noOfPersonsSpinner.getValue());
		}
		
		Date getArrivalDate() {
			return (Date) arrDateSpinner.getValue();
		}
		
		Date getDepartureDate() {
			return (Date) depDateSpinner.getValue();
		}
		
		String getPaymentAmount() {
			return payAmountField.getText();
		}
		
		JPanel getTitlePanel() {
			titlePanel.setBackground(Color.decode("#ab782b"));
			titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			titlePanel.add(editTitle);
			return titlePanel;
		}
		
		JPanel getButtonsPanel() {
			buttonsPanel.setBackground(Color.decode("#ab782b"));
			buttonsPanel.setLayout(new GridLayout(1, 2, 10, 0));
			buttonsPanel.add(cancelBtn);
			buttonsPanel.add(editBtn);
			return buttonsPanel;
		}
		
		void applyButtonStyle(JButton btn) {
			btn.setFont(new Font("Vedana", Font.PLAIN, 20));
			btn.setBackground(Color.decode("#433217"));
			btn.setForeground(Color.WHITE);
		}
		
		void setComponentProperties() {
			editTitle.setForeground(Color.WHITE);
			editTitle.setFont(new Font("Verdana", Font.BOLD, 25));
		
			roomNoBox.setPreferredSize(new Dimension(200, 30));
			roomNoBox.setFont(new Font("Verdana", Font.PLAIN, 20));
			
			((JSpinner.DefaultEditor) noOfPersonsSpinner.getEditor()).getTextField().setEditable(false);
			
			arrDateSpinner.setModel(getDateModel());
			arrDateSpinner.setEditor(new JSpinner.DateEditor(arrDateSpinner, "yyyy/MM/dd"));
			calendar = Calendar.getInstance();
			depDateSpinner.setModel(getDateModel());
			depDateSpinner.setEditor(new JSpinner.DateEditor(depDateSpinner, "yyyy/MM/dd"));
			
			payAmountField.setFont(new Font("Verdana", Font.PLAIN, 20));
			payAmountField.setPreferredSize(new Dimension(200, 30));
			
			applyButtonStyle(cancelBtn);
			applyButtonStyle(editBtn);

			
			for (JLabel label : labels) {
				label.setFont(new Font("Verdana", Font.PLAIN, 20));
				label.setForeground(Color.WHITE);
			}
			
			for (JSpinner spinner : spinners) {
				spinner.setFont(new Font("Verdana", Font.PLAIN, 20));
				spinner.setPreferredSize(new Dimension(200, 30));
			}
		}
		
		private SpinnerDateModel getDateModel() {
			Date initDate = calendar.getTime();
			calendar.add(Calendar.YEAR, -1);
			Date earliestDate = calendar.getTime();
			calendar.add(Calendar.YEAR, 2);
			Date latestDate = calendar.getTime();
			return new SpinnerDateModel(initDate, earliestDate, latestDate, Calendar.YEAR);
		}
		
		void addSpinnersToArray() {
			spinners.add(noOfPersonsSpinner);
			spinners.add(arrDateSpinner);
			spinners.add(depDateSpinner);
		}
		
		void addLabelsToArray() {
			labels.add(roomNoLabel);
			labels.add(noOfPersonsLabel);
			labels.add(arrDateLabel);
			labels.add(depDateLabel);
			labels.add(payLabel);
		}
		
		void setComponentValues() {
			roomNoBox.setSelectedItem(Reservations.getRoomNo());
			noOfPersonsSpinner.setValue(Reservations.getNoOfPersons());
			arrDateSpinner.setValue(Reservations.getArrivalDate());
			depDateSpinner.setValue(Reservations.getDepartureDate());
			payAmountField.setText(Reservations.getPaymentAmount());
		}
		
		void getRooms() {			
			try {
				Connection con = DBconnection.getConnection();
				PreparedStatement st = con.prepareStatement("SELECT room_id FROM rooms");
				ResultSet rs = st.executeQuery();
				rooms = new String[getNoOfRooms()];
				
				int index = 0;
				while(rs.next()) {
					String room_id = String.valueOf(rs.getInt("room_id"));
					rooms[index] = room_id;
					index++;
				}			
				roomNoBox = new JComboBox<>(rooms);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		int getNoOfRooms() {
			try {
				Connection con = DBconnection.getConnection();
				PreparedStatement st = con.prepareStatement("SELECT room_id FROM rooms");
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
}
