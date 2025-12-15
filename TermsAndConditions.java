package hrs_re_2;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
class TermsAndConditions extends JPanel {
	JLabel continueLabel = new JLabel("By continuing, you agree to 2BIT Hotel's");
	JLabel clickableLabel = new JLabel("<html><u>terms and conditions</u></html>");
	static TermsAndConditionsDialog TACD = new TermsAndConditionsDialog();
	
	TermsAndConditions() {
		TermsAndConditionsPanel.setComponentProperties();
		this.setLayout(new FlowLayout());
		this.setBackground(Color.decode("#ab782b"));
		this.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		applyLabelStyle(continueLabel);
		this.add(continueLabel);
		applyLabelStyle(clickableLabel);
		this.add(clickableLabel);
		
		clickableLabel.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				clickableLabel.setForeground(Color.decode("#ababab"));
			}
		});
			
		clickableLabel.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent e) {
				clickableLabel.setForeground(Color.WHITE);
			}
		});
		
		clickableLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (Admin.isAdmin) {
					RoomSelection.confirmDialog.hideConfirmDialog();
				} else {
					Room.confirmDialog.hideConfirmDialog();
				}
				
				TACD.setVisible(true);
			}
		});
	}
	
	void applyLabelStyle(JLabel label) {
		label.setFont(new Font("Verdana", Font.PLAIN, 15));
		label.setForeground(Color.WHITE);
	}
}

@SuppressWarnings("serial")
class TermsAndConditionsDialog extends JDialog {
	TermsAndConditionsDialog() {
		super(Main.frame, "Terms and Conditions", true);
		this.setSize(817, 675);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.add(new TermsAndConditionsPanel());
	}
	
	void showTermsAndConditions() {
		this.setVisible(rootPaneCheckingEnabled);
	}
}

@SuppressWarnings("serial")
class TermsAndConditionsPanel extends JPanel {
	static JLabel reservationPolicy = new JLabel("1. Reservation policy");
	static JLabel checkInPolicy = new JLabel("2. Check-in policy");
	static JLabel identificationRequirements = new JLabel("3. Identification requirements");
	static JLabel paymentAndFees = new JLabel("4. Payment and Fees");
	static JLabel noShowPolicy = new JLabel("5. No-show Policy");
	static JLabel hotelRules = new JLabel("6. Hotel rules");
	static JLabel liability = new JLabel("7. Liability");
	static JLabel privacyPolicy = new JLabel("8. Privacy policy");
	static JLabel agreement = new JLabel("9. Agreement policy");
	static JLabel[] labels = {reservationPolicy, checkInPolicy, identificationRequirements, paymentAndFees, noShowPolicy, hotelRules, liability, privacyPolicy, agreement};
	
	static JTextArea reservationPolicyText = new JTextArea("All room reservations must be completed through the official 2BIT Hotel booking system.\n"
			+ "Guests must provide accurate personal information when booking. Any false or incomplete details may result in cancellation.");
	static JTextArea checkInPolicyText = new JTextArea("Check-in time is exactly 12:00 PM (noon).\r\n"
			+ "Guests must arrive on or before 12:00 PM to retain their reservation.\n"
			+ "The Hotel allows a maximum grace period until 1:00 PM for late arrivals.\n"
			+ "If the guest fails to check in by 1:00 PM, 2BIT Hotel reserves the right to automatically cancel the reservation and release the room to other guests, without refund");
	static JTextArea identificationRequirementsText = new JTextArea("All guests must present valid identification (ID, passport, or government-issued document) upon check-in.\n"
			+ "The name on the ID must match the name used during booking.");
	static JTextArea paymentAndFeesText = new JTextArea("All payments must be completed through the approved payment methods provided by 2BIT Hotel.\n"
			+ "Additional charges may apply for special requests, room upgrades, or damaged property.");
	static JTextArea noShowPolicyText = new JTextArea("Failure to appear by 1:00 PM on the check-in date without prior notice is considered a no-show, resulting in automatic cancellation and loss of the reservation.");
	static JTextArea hotelRulesText = new JTextArea("Smoking inside rooms is strictly prohibited unless the room is designated for smoking.\n"
			+ "Pets are only allowed in designated pet-friendly rooms (if applicable).\n"
			+ "Guests must follow all hotel rules to ensure comfort, safety, and privacy for everyone.");
	static JTextArea liabilityText = new JTextArea("2BIT Hotel is not responsible for loss of personal belongings left unattended by guests.\n"
			+ "Any damage to hotel property caused by the guest will result in repair or replacement charges.");
	static JTextArea privacyPolicyText = new JTextArea("Guest information will be used only for booking, identification, and hotel service purposes.\n"
			+ "2BIT Hotel ensures all personal data is protected and will not be shared with third parties except when required by law.");
	static JTextArea agreementText = new JTextArea("By completing a reservation, the guest acknowledges that they have read, understood, and agreed to all 2BIT Hotel Terms & Conditions.");
	static JTextArea[] tAreas = {reservationPolicyText, checkInPolicyText, identificationRequirementsText, paymentAndFeesText, noShowPolicyText, hotelRulesText, liabilityText, privacyPolicyText, agreementText};
	
	static JButton closeBtn = new JButton("Close");

	TermsAndConditionsPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.setBackground(Color.decode("#ab782b"));
		this.setBorder(BorderFactory.createEmptyBorder(10, 0, 0 ,0));
		//Box.createHorizontalBox();
		
		this.add(reservationPolicy);
		this.add(reservationPolicyText);
		
		this.add(checkInPolicy);
		this.add(checkInPolicyText);
		
		this.add(identificationRequirements);
		this.add(identificationRequirementsText);
		
		this.add(paymentAndFees);
		this.add(paymentAndFeesText);
		
		this.add(noShowPolicy);
		this.add(noShowPolicyText);
		
		this.add(hotelRules);
		this.add(hotelRulesText);

		this.add(liability);
		this.add(liabilityText);
		
		this.add(privacyPolicy);
		this.add(privacyPolicyText);
		
		this.add(agreement);
		this.add(agreementText);
		
		this.add(getButtonPanel());
				
		closeBtn.addActionListener((ActionEvent e) -> {
			System.out.println(TermsAndConditions.TACD.getSize());
		});
		
		closeBtn.addActionListener((ActionEvent e) -> {
			TermsAndConditions.TACD.dispose();
			if (Admin.isAdmin) {
				RoomSelection.confirmDialog.showConfirmDialog();
			} else {
				Room.confirmDialog.showConfirmDialog();
			}
		});
	}
	
	static JPanel getButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setBackground(Color.decode("#ab782b"));
		closeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.add(closeBtn);
		
		return buttonPanel;
	}
	
	static void setComponentProperties() {
		for (JLabel label : labels) {
			label.setForeground(Color.WHITE);
			label.setFont(new Font("Verdana", Font.BOLD, 17));
			label.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		}
		
		for (JTextArea tArea: tAreas) {
			tArea.setBackground(Color.decode("#ab782b"));
			tArea.setForeground(Color.WHITE);
			tArea.setFont(new Font("Verdana", Font.PLAIN, 12));
			tArea.setEditable(false);
			tArea.setFocusable(false);
			tArea.setLineWrap(true);
			tArea.setWrapStyleWord(true);
			tArea.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		}
		
		closeBtn.setPreferredSize(new Dimension(100, 30));
		closeBtn.setFont(new Font("Vedana", Font.PLAIN, 20));
		closeBtn.setBackground(Color.decode("#433217"));
		closeBtn.setForeground(Color.WHITE);
		closeBtn.setFocusable(false);
	}
}
