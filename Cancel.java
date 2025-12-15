package hrs_re_2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
class Cancel extends JDialog {
	JLabel cancelTitle = new JLabel("Cancel reservation");
	JPanel titlePanel = new JPanel();
	static CancelPanel cancelPanel;
	
	Cancel() {
		super(Main.frame, "Cancel reservation", true);
		cancelPanel = new CancelPanel();
		this.setSize(500, 320);
		this.setResizable(false);
		this.getContentPane().setBackground(Color.decode("#ab782b"));
		this.setLayout(new BorderLayout());
		this.add(getTitlePanel(), BorderLayout.NORTH);
		this.add(cancelPanel, BorderLayout.CENTER);
		this.setLocationRelativeTo(null);
	}
	
	JPanel getTitlePanel() {
		titlePanel.setBackground(Color.decode("#ab782b"));
		
		cancelTitle.setHorizontalAlignment(JLabel.CENTER);
		cancelTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		cancelTitle.setForeground(Color.WHITE);
		cancelTitle.setFont(new Font("Verdana", Font.BOLD, 25));
		titlePanel.add(cancelTitle);
		
		return titlePanel;
	}
	
	void showCancelDialog() {
		this.setVisible(true);
	}
	
	void closeCancelDialog() {
		this.dispose();;
	}
}

@SuppressWarnings("serial")
class CancelPanel extends JPanel {
	JLabel refNoLabel = new JLabel("Reference no:");
	JTextField refNoField = new JTextField();
	JLabel reasonLabel = new JLabel("Reason:");
	JTextArea reasonArea = new JTextArea();
	GridBagConstraints gbc = new GridBagConstraints();
	
	CancelPanel() {
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.decode("#ab782b"));
		setComponentProperties();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 0, 5, 5);
		this.add(refNoLabel, gbc);
		gbc.gridx = 1;
		this.add(refNoField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 0, 0, 0);
		this.add(reasonLabel, gbc);
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 10, 0);
		this.add(reasonArea, gbc);
		
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		this.add(new ButtonsArea(), gbc);
	}
	
	String getRefNo() {
		return refNoField.getText();
	}
	
	String getReason() {
		return reasonArea.getText();
	}
	
	void setComponentProperties() {
		JLabel[] labels = {refNoLabel, reasonLabel};
		for (JLabel label : labels) {
			label.setFont(new Font("Verdana", Font.PLAIN, 20));
			label.setForeground(Color.WHITE);
		}
		
		refNoField.setPreferredSize(new Dimension(200, 30));
		refNoField.setFont(new Font("Verdana", Font.PLAIN, 20));
		setInputLimit(refNoField, 8);
		
		reasonArea.setPreferredSize(new Dimension(200, 100));
		reasonArea.setFont(new Font("Verdana", Font.PLAIN, 15));
		reasonArea.setLineWrap(true);
		reasonArea.setWrapStyleWord(true);
	}
	

	void setInputLimit(JTextField textField, int limit) {
		textField.addKeyListener(new java.awt.event.KeyAdapter() {
		    public void keyTyped(java.awt.event.KeyEvent evt) {
		        if(textField.getText().length()>=limit&&!(evt.getKeyChar()==KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
		            getToolkit().beep();
		            evt.consume();
		         }
		     }
		});
	}
}

@SuppressWarnings("serial")
class ButtonsArea extends JPanel {
	private JButton submitBtn = new JButton("Submit");
	private JButton cancelBtn = new JButton("Cancel");
	
	ButtonsArea() {
		setComponentProperties();
		this.setBackground(Color.decode("#ab782b"));
		this.setLayout(new GridLayout(1, 2, 10, 0));
		this.add(cancelBtn);
		this.add(submitBtn);
		
		cancelBtn.addActionListener((ActionEvent e) -> GuestHome.cancelDialog.closeCancelDialog());
		submitBtn.addActionListener((ActionEvent e) -> {
			if (fieldsEmpty()) {
				return;
			}
			
			if (CancellationController.requestHasBeenSent(Cancel.cancelPanel.getRefNo(), Cancel.cancelPanel.getReason())) {
				JOptionPane.showMessageDialog(null, "Cancel request sent!", "Message", JOptionPane.INFORMATION_MESSAGE);
				GuestHome.cancelDialog.closeCancelDialog();
			}
		});
	}
	
	boolean fieldsEmpty() {
		if (Cancel.cancelPanel.getRefNo().equals("") || Cancel.cancelPanel.getReason().equals("")) {
			JOptionPane.showMessageDialog(null, "Please fill in all required fields!", "Error", 
					JOptionPane.ERROR_MESSAGE);
			return true;
		}
		return false;
	}
	
	void setComponentProperties() {
		JButton[] buttons = {cancelBtn, submitBtn};
		for (JButton button : buttons) {
			button.setBackground(Color.decode("#433217"));
			button.setForeground(Color.WHITE);
			button.setFont(new Font("Verdana", Font.PLAIN, 20));
		}
	}
}

class CancellationController {
	static boolean requestHasBeenSent(String refNo, String reason) {
		if (! refNoExists(refNo)) {
			JOptionPane.showMessageDialog(null, "Reference no. does not exist!", "Error", 
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if (reservationAlreadyCancelled(refNo)) {
			JOptionPane.showMessageDialog(null, "Reservation has already been cancelled!", "Error", 
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if (reservationCheckedOut(refNo)) {
			JOptionPane.showMessageDialog(null, "Cannot cancel a checked-out reservation!", "Error", 
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if (requestAlreadySent(refNo)) {
			JOptionPane.showMessageDialog(null, "Cancel request already sent!", "Error", 
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		try {
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement("INSERT INTO cancelRequests(reservation_id, reason) VALUES(?, ?);");
			st.setString(1, refNo);
			st.setString(2, reason);
			st.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Admin.refreshRequests();
		return true;
	}
	
	static boolean refNoExists(String refNo) {
		try {
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement("SELECT * FROM reservations WHERE reservation_id=?;");
			st.setString(1, refNo);
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
	
	static boolean requestAlreadySent(String refNo) {
		try {
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement("SELECT * FROM cancelRequests WHERE reservation_id=?;");
			st.setString(1, refNo);
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
	
	static boolean reservationAlreadyCancelled(String refNo) {
		try {
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement("SELECT * FROM reservations WHERE reservation_id=?;");
			st.setString(1, refNo);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				if (rs.getString("status").equals("Cancelled")) {
					return true;
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	static boolean reservationCheckedOut(String refNo) {
		try {
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement("SELECT * FROM reservations WHERE reservation_id=?;");
			st.setString(1, refNo);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				if (rs.getString("status").equals("Checked-out")) {
					return true;
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
}
