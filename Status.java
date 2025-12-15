package hrs_re_2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
class Status extends JDialog {
	private JLabel statusTitle = new JLabel("Reservation status");
	Status(String status) {
		super(Main.frame, "Check reservation status", true);
		this.setSize(350, 150);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setBackground(Color.decode("#ab782b"));
		this.setLayout(new BorderLayout());
		this.getContentPane().setBackground(Color.decode("#ab782b"));
		
		statusTitle.setHorizontalAlignment(JLabel.CENTER);
		statusTitle.setFont(new Font("Verdana", Font.BOLD, 25));
		statusTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		statusTitle.setForeground(Color.WHITE);
		this.add(statusTitle, BorderLayout.NORTH);
		
		this.add(new StatusPanel(status), BorderLayout.CENTER);
		statusTitle.setFont(new Font("Verdana", Font.BOLD, 20));
	}

	void showStatus() {
		this.setVisible(true);
	}
	
	void close() {
		this.dispose();
	}
}

@SuppressWarnings("serial")
class StatusPanel extends JPanel {
	private JTextField statusField = new JTextField();
	private JButton backBtn = new JButton("Back"); 
		
	StatusPanel(String status) {
		this.setBackground(Color.decode("#ab782b"));
		setComponentProperties();
		
		statusField.setText(status);
		this.add(statusField);
		
		this.add(backBtn);

		backBtn.addActionListener((ActionEvent e) -> GuestHome.statusDialog.close());
	}
	
	void setComponentProperties() {		
		statusField.setEditable(false);
		statusField.setFocusable(false);
		statusField.setPreferredSize(new Dimension(200, 30));
		statusField.setFont(new Font("Vedana", Font.PLAIN, 20));
		
		backBtn.setFont(new Font("Vedana", Font.PLAIN, 20));
		backBtn.setBackground(Color.decode("#433217"));
		backBtn.setForeground(Color.WHITE);
	}
}
