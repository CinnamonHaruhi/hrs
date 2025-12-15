package hrs_re_2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hrs_re_2.Reservations.ReservationsController;


@SuppressWarnings("serial")
class Confirm extends JDialog{
	ConfirmPanel confirmPanel = new ConfirmPanel();
	JPanel headingPanel = new JPanel();
	JLabel heading = new JLabel("Confirm details");

	Confirm() {
		super(Main.frame, "Confirm", true);
		this.setSize(600, 610);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		this.getContentPane().setBackground(Color.decode("#ab782b"));
		
		this.add(getHeadingPanel(), BorderLayout.NORTH);
		this.add(confirmPanel, BorderLayout.CENTER);
		
		this.add(new TermsAndConditions(), BorderLayout.SOUTH);
	}
	
	void showConfirmDialog() {
		this.setVisible(true);
	}
	
	void closeConfirmDialog() {
		this.dispose();
	}
	
	void hideConfirmDialog() {
		this.setVisible(false);
	}
	
	private JPanel getHeadingPanel() {
		headingPanel.setBackground(Color.decode("#ab782b"));
		headingPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		heading.setHorizontalAlignment(JLabel.CENTER);
		heading.setForeground(Color.WHITE);
		heading.setFont(new Font("Verdana", Font.BOLD, 25));
		headingPanel.add(heading);
		return headingPanel;
	}
}

@SuppressWarnings("serial")
class ConfirmPanel extends JPanel{

	ArrayList<JLabel> labels = new ArrayList<>();
	JLabel firstName = new JLabel("First name:");
	JLabel lastName = new JLabel("Last name:");
	JLabel conNum = new JLabel("Contact number:");
	JLabel email = new JLabel("Email:");
	JLabel noOfPersons = new JLabel("No. of persons:");
	JLabel cInDate = new JLabel("Check-in date:");
	JLabel cOutDate = new JLabel("Check-out date:");
	JLabel paymentAmount = new JLabel("Payment amount:");
	JLabel roomNo = new JLabel("Room no:");
	JLabel roomType = new JLabel("Room type:");
	
	ArrayList<JTextField> textFields = new ArrayList<>();
	JTextField fnameField = new JTextField();
	JTextField lnameField = new JTextField();
	JTextField conNumField = new JTextField();
	JTextField emailField = new JTextField();
	JTextField noOfPersonsField = new JTextField();
	JTextField cInDateField = new JTextField();
	JTextField cOutDateField = new JTextField();
	JTextField paymentAmountField = new JTextField();
	JTextField roomNoField = new JTextField();
	JTextField roomTypeField = new JTextField();
	
	JPanel buttonsPanel = new JPanel();
	JButton confirmBtn = new JButton("Confirm");	
	JButton cancelBtn = new JButton("Cancel");		
	
	GridBagConstraints gbc = new GridBagConstraints();
	static Submitted submittedDialog;
	
	ConfirmPanel() {
		setButtonActions();
		addTextFieldsToArray();
		setConfirmationDetails();
		addLabelsToArray();
		setComponentProperties();
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.decode("#ab782b"));
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 0, 10, 10);
		this.add(firstName, gbc);
		gbc.gridx = 1;
		gbc.insets = new Insets(0, 0, 10, 0);
		this.add(fnameField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 0, 10, 10);
		this.add(lastName, gbc);
		gbc.gridx = 1;
		gbc.insets = new Insets(0, 0, 10, 0);
		this.add(lnameField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(0, 0, 10, 10);
		this.add(conNum, gbc);
		gbc.gridx = 1;
		gbc.insets = new Insets(0, 0, 10, 0);
		this.add(conNumField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(0, 0, 10, 10);
		this.add(email, gbc);
		gbc.gridx = 1;
		gbc.insets = new Insets(0, 0, 10, 0);
		this.add(emailField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.insets = new Insets(0, 0, 10, 10);
		this.add(noOfPersons, gbc);
		gbc.gridx = 1;
		gbc.insets = new Insets(0, 0, 10, 0);
		this.add(noOfPersonsField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.insets = new Insets(0, 0, 10, 10);
		this.add(cInDate, gbc);
		gbc.gridx = 1;
		gbc.insets = new Insets(0, 0, 10, 0);
		this.add(cInDateField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.insets = new Insets(0, 0, 10, 10);
		this.add(cOutDate, gbc);
		gbc.gridx = 1;
		gbc.insets = new Insets(0, 0, 10, 0);
		this.add(cOutDateField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.insets = new Insets(0, 0, 10, 10);
		this.add(roomNo, gbc);
		gbc.gridx = 1;
		gbc.insets = new Insets(0, 0, 10, 0);
		this.add(roomNoField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.insets = new Insets(0, 0, 10, 10);
		this.add(roomType, gbc);
		gbc.gridx = 1;
		gbc.insets = new Insets(0, 0, 10, 0);
		this.add(roomTypeField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.insets = new Insets(0, 0, 10, 10);
		this.add(paymentAmount, gbc);
		gbc.gridx = 1;
		gbc.insets = new Insets(0, 0, 10, 0);
		this.add(paymentAmountField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 10;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.add(getButtonsPanel(), gbc);
	}
	
	private void setButtonActions() {
		confirmBtn.addActionListener((ActionEvent e) -> {
			try {
				submitReservation();
				submittedDialog = new Submitted();
				if (Admin.isAdmin) {
					RoomSelection.confirmDialog.dispose();
				} else {
					Room.confirmDialog.dispose();
				}
				
				refreshAllTables();
				submittedDialog.showSubmittedDialog();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		
		cancelBtn.addActionListener((ActionEvent e) -> {
			if (Admin.isAdmin) {
				RoomSelection.confirmDialog.closeConfirmDialog();
			} else {
				Room.confirmDialog.closeConfirmDialog();
			}
		});
	}
	
	private void refreshAllTables() {
		ReservationsController.refreshReservationsTable();
		Occupants.refreshOccupantsTable();
		Rooms.refreshRoomsTable();
	}
	
	private void submitReservation() throws SQLException {
		if (Admin.isAdmin) {
			FormController.submitOccupantDetails(Admin.form);
		} else {
			FormController.submitOccupantDetails(Guest.form);
		}
	}
	
	private void setConfirmationDetails() {
		if (Admin.isAdmin) {
			getDetailsFromAdminPanels();
		} else {
			getDetailsFromGuestPanels();
		}
	}
	
	private void getDetailsFromAdminPanels() {
		fnameField.setText(Admin.getFirstName());
		lnameField.setText(Admin.getLastName());
		conNumField.setText(Admin.getConNum());
		emailField.setText(Admin.getEmail());
		noOfPersonsField.setText(Admin.getNoOfPersons());
		cInDateField.setText(Admin.getCheckInDate());
		cOutDateField.setText(Admin.getCheckOutDate());
		paymentAmountField.setText(Admin.getPaymentAmount());
		roomNoField.setText(Admin.getRoomNo());
		roomTypeField.setText(Admin.getRoomType());
	}
	
	private void getDetailsFromGuestPanels() {
		fnameField.setText(Guest.getFirstName());
		lnameField.setText(Guest.getLastName());
		conNumField.setText(Guest.getConNum());
		emailField.setText(Guest.getEmail());
		noOfPersonsField.setText(Guest.getNoOfPersons());
		cInDateField.setText(Guest.getCheckInDate());
		cOutDateField.setText(Guest.getCheckOutDate());
		paymentAmountField.setText(Guest.getPaymentAmount());
		roomNoField.setText(Guest.getRoomNo());
		roomTypeField.setText(Guest.getRoomType());
	}
	
	private void setComponentProperties() {
		for (JLabel label : labels) {
			label.setFont(new Font("Verdana", Font.BOLD, 20));
			label.setForeground(Color.WHITE);
		}
		
		for (JTextField textField : textFields) {
			textField.setEditable(false);
			textField.setBorder(null);
			textField.setFont(new Font("Verdana", Font.PLAIN, 15));
			textField.setPreferredSize(new Dimension(300, 30));	
			textField.setFocusable(false);
		}
		
		applyButtonStyle(confirmBtn);
		applyButtonStyle(cancelBtn);
	}
	
	private void applyButtonStyle(JButton btn) {
		btn.setFont(new Font("Vedana", Font.PLAIN, 20));
		btn.setBackground(Color.decode("#433217"));
		btn.setForeground(Color.WHITE);
	}
	
	private JPanel getButtonsPanel() {
		buttonsPanel.setBackground(Color.decode("#ab782b"));
		buttonsPanel.setLayout(new GridLayout(1, 2, 10, 0));
		buttonsPanel.add(cancelBtn);
		buttonsPanel.add(confirmBtn);
		return buttonsPanel;
	}
	
	private void addTextFieldsToArray() {
		textFields.add(fnameField);
		textFields.add(lnameField);
		textFields.add(conNumField);
		textFields.add(emailField);
		textFields.add(noOfPersonsField);
		textFields.add(cInDateField);
		textFields.add(cOutDateField);
		textFields.add(roomNoField);
		textFields.add(roomTypeField);
		textFields.add(paymentAmountField);
	}
	
	private void addLabelsToArray() {
		labels.add(firstName);
		labels.add(lastName);
		labels.add(conNum);
		labels.add(email);
		labels.add(noOfPersons);
		labels.add(cInDate);
		labels.add(cOutDate);
		labels.add(roomNo);
		labels.add(roomType);
		labels.add(paymentAmount);
	}
}

@SuppressWarnings("serial")
class Submitted extends JDialog{
	SubmittedPanel submittedPanel = new SubmittedPanel();
	static JPanel submittedLabelPanel = new JPanel();
	static JLabel submittedLabel = new JLabel("Reservation submitted!");
	Submitted() {
		super(Main.frame, "Reservation submitted", true);
		this.setSize(400, 240);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());

		this.add(getSubmittedLabelPanel(), BorderLayout.NORTH);
		this.add(submittedPanel, BorderLayout.CENTER);
	}
	
	void showSubmittedDialog() {
		this.setVisible(true);
	}
	
	void closeSubmittedDialog() {
		this.dispose();
	}
	
	private static JPanel getSubmittedLabelPanel() {
		submittedLabelPanel.setBackground(Color.decode("#ab782b"));
		submittedLabelPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		
		submittedLabel.setHorizontalAlignment(JLabel.CENTER);
		submittedLabel.setForeground(Color.WHITE);
		submittedLabel.setFont(new Font("Verdana", Font.BOLD, 25));
		submittedLabelPanel.add(submittedLabel);
		return submittedLabelPanel;
	}
}

@SuppressWarnings("serial")
class SubmittedPanel extends JPanel {
	JLabel refNoLabel = new JLabel("Reference number");
	JTextField refNo = new JTextField();
	
	JPanel buttonsPanel = new JPanel();
	JButton copyBtn = new JButton("Copy");
	JButton closeBtn = new JButton("Close");
	
	GridBagConstraints gbc = new GridBagConstraints();
	
	SubmittedPanel() {
		refNo.setText(FormController.referenceNo);
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.decode("#ab782b"));
		setComponentProperties();
		
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 5, 0);
		this.add(refNoLabel, gbc);
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridy = 1;
		this.add(refNo, gbc);
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 0, 0, 0);
		this.add(getButtonsPanel(), gbc);
		
		setButtonActions();

	}
	
	void setButtonActions() {
		copyBtn.addActionListener((ActionEvent e) -> {
			Toolkit.getDefaultToolkit()
	        .getSystemClipboard()
	        .setContents(
	                new StringSelection(refNo.getText()),
	                null
	        );
		});
		
		closeBtn.addActionListener((ActionEvent e) -> {		
			if (Admin.isAdmin) {
				ConfirmPanel.submittedDialog.closeSubmittedDialog();
				Admin.showReservationsPanel();
			} else {
				ConfirmPanel.submittedDialog.closeSubmittedDialog();
				Guest.showGuestHome();
			}
		});
	}
	
	JPanel getButtonsPanel() {
		buttonsPanel.setLayout(new GridLayout(1, 2, 5, 0));
		buttonsPanel.setBackground(Color.decode("#ab782b"));
		buttonsPanel.add(closeBtn);
		buttonsPanel.add(copyBtn);
		return buttonsPanel;
	}
	
	
	void setComponentProperties() {
		refNoLabel.setForeground(Color.WHITE);
		refNoLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		
		refNo.setPreferredSize(new Dimension(200, 40));
		refNo.setFont(new Font("Verdana", Font.PLAIN, 20));
		refNo.setEditable(false);
		refNo.setFocusable(false);
		refNo.setBorder(null);
		
		applyButtonStyle(closeBtn);
		applyButtonStyle(copyBtn);
	}
	
	private void applyButtonStyle(JButton btn) {
		btn.setFont(new Font("Vedana", Font.PLAIN, 20));
		btn.setBackground(Color.decode("#433217"));
		btn.setForeground(Color.WHITE);
	}
}


