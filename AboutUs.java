package hrs_re_2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
class AboutUs extends JPanel {
	JLabel aboutUsTitle = new JLabel("About us");
	
	AboutUs() {
		this.setLayout(new BorderLayout());
		this.setBackground(Color.decode("#ab782b"));;
		
		aboutUsTitle.setHorizontalAlignment(JLabel.CENTER);
		aboutUsTitle.setBorder(BorderFactory.createEmptyBorder(40, 20, 0, 20));
		aboutUsTitle.setForeground(Color.WHITE);
		aboutUsTitle.setFont(new Font("Verdana", Font.BOLD, 25));
		this.add(aboutUsTitle, BorderLayout.NORTH);
		
		this.add(new AboutUsPanel());
	}
}

@SuppressWarnings("serial")
class AboutUsPanel extends JPanel {
	JLabel storyLabel = new JLabel("Our story");
	JTextArea storyText = new JTextArea("2BIT Hotel was established in 2024 as a modern budget-friendly sanctuary located near Tagaytay. It aims to provide a comfortable retreat for travelers looking for affordable yet quality services. From a simple idea of two friends who wanted to build a hotel that was open to everyone, the 2BIT Hotel has become a favorite destination for those who want to unwind and escape the stress of the city without having to spend a fortune. 2BIT Hotel boasts facilities that cater to the needs of solo travelers, couples, families, and even work-from-anywhere professionals. It is close to the main attractions in Tagaytay, making it perfect for a quick vacation, a romantic getaway, or a simple weekend getaway. 2BIT Hotel boasts facilities that cater to the needs of solo travelers, couples, families, and even work-from-anywhere professionals. It is close to the main attractions in Tagaytay, making it perfect for a quick vacation, a romantic getaway, or a simple weekend getaway.");
	JLabel visionLabel = new JLabel("Vision");
	JTextArea visionText = new JTextArea("2BIT Hotel envisions becoming the leading budget-friendly hotel near Tagaytay, recognized for providing exceptional comfort, inclusive hospitality, and memorable experiences for every type of traveler.");
	JLabel missionLabel = new JLabel("Mission");
	JTextArea missionText = new JTextArea("Our mission at 2BIT Hotel is to deliver high-quality, affordable accommodations that make every guest feel at home. We strive to create a welcoming environment for solo travelers, couples, families, and work-from-anywhere professionals. Our goal is to provide personalized service and thoughtful amenities that support relaxation, productivity, and connection. Conveniently located near Tagaytay’s main attractions, we aim to help guests explore, unwind, and enjoy more of what the area has to offer. True to our roots, we are committed to fostering a culture of inclusivity, accessibility, and warm hospitality for everyone.");
	JButton backBtn = new JButton("Back");
	
	GridBagConstraints gbc = new GridBagConstraints();
			
	AboutUsPanel() {
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.decode("#ab782b"));
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 10, 10, 10);
		this.add(storyLabel, gbc);
		gbc.gridy = 1;
		this.add(storyText, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		this.add(visionLabel, gbc);
		gbc.gridy = 1;
		this.add(visionText, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		this.add(missionLabel, gbc);
		gbc.gridy = 1;
		this.add(missionText, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		this.add(backBtn, gbc);
		
		applyTextFieldStyle(storyText);
		applyTextFieldStyle(visionText);
		applyTextFieldStyle(missionText);
		
		applyLabelStyle(storyLabel);
		applyLabelStyle(visionLabel);
		applyLabelStyle(missionLabel);
		
		applyButtonStyle(backBtn);
		
		backBtn.addActionListener((ActionEvent e) -> Frame.showLoginSelection());
	}
	
	void applyButtonStyle(JButton btn) {
		btn.setBackground(Color.decode("#433217"));
		btn.setForeground(Color.WHITE);
		btn.setFont(new Font("Verdana", Font.PLAIN, 20));
	}
	
	void applyLabelStyle(JLabel lbl) {
		lbl.setFont(new Font("Verdana", Font.BOLD, 20));
		lbl.setForeground(Color.WHITE);
	}
	
	void applyTextFieldStyle(JTextArea tArea) {
		tArea.setPreferredSize(new Dimension(400, 400));
		tArea.setLineWrap(true);
		tArea.setWrapStyleWord(true);
		tArea.setFont(new Font("Verdana", Font.PLAIN, 14));
		tArea.setMargin(new Insets(10, 10, 10, 10));
		tArea.setEditable(false);
		tArea.setFocusable(false);
	}
}
