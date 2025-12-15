package hrs_re_2;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

@SuppressWarnings("serial")
class LoginSelection extends JPanel {
	ImageIcon bgImg = new ImageIcon("D:\\hrs_resources\\loginBG.jpg");
	
	LoginSelection() {		
		this.setLayout(new BorderLayout());
		this.add(new WelcomePanel(), BorderLayout.NORTH);
		this.add(new ButtonsPanel(), BorderLayout.CENTER);
		this.add(new AboutAndContact(), BorderLayout.SOUTH);
	}
	
	 protected void paintComponent(Graphics g) {
	     super.paintComponent(g);;
	     g.drawImage(bgImg.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
	 }
	
	class WelcomePanel extends JPanel {
		private String welcomeStr1 = "Welcome to ";
		private String welcomeStr2 = "2BIT HOTEL";
		private JLabel welcomeLabel = new JLabel("<html>" + welcomeStr1 + "<B>" + welcomeStr2 + "</B>" + "</html>");
		WelcomePanel() {
			this.setOpaque(false);
			this.setLayout(new FlowLayout());
			this.setBorder(BorderFactory.createEmptyBorder(125, 0, 0, 0));
			welcomeLabel.setFont(new Font("Verdana", Font.TRUETYPE_FONT, 65));
			welcomeLabel.setForeground(Color.WHITE);
			this.add(welcomeLabel);
		}
	}
	
	class ButtonsPanel extends JPanel {
		private LoginButton adminBtn = new LoginButton("Login as admin");
		private LoginButton guestBtn = new LoginButton("Continue as guest");	
		GridBagConstraints gbc = new GridBagConstraints();
		
		ButtonsPanel() {
			this.setOpaque(false);
			this.setLayout(new GridBagLayout());
			
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.insets = new Insets(0, 0, 22, 0);
			this.add(adminBtn, gbc);
			
			gbc.gridy = 1;
			gbc.ipady = 0;
			gbc.insets = new Insets(22, 0, 0, 0);
			this.add(guestBtn, gbc);
			
			setButtonActions();
		}
		
		private void setButtonActions() {
			adminBtn.addActionListener((ActionEvent e) -> {
				LoginController.loginDialog.showLoginDialog();
			});
			
			guestBtn.addActionListener((ActionEvent e) -> {
				Admin.isAdmin = false;
				MainPanel.header.hideButtonsPanel();
				Frame.showMainPanel();
				MainPanel.showGuestPanel();
			});
		}
	}
	
	class LoginButton extends JButton {
			LoginButton(String btnText) { 
				super(btnText);
				this.setForeground(Color.WHITE);
				this.setPreferredSize(new Dimension(500, 150));
				this.setFont(new Font("Verdana", Font.PLAIN, 30));
				this.setBackground(new Color(171, 120, 43));
				this.setFocusPainted(false);
				//this.setBackground(new Color(171, 120, 43, 100));
			}
	}
	
	class AboutAndContact extends JPanel {
		JLabel aboutLabel = new JLabel("About us");
		JLabel bar = new JLabel("|");
		JLabel contactLabel = new JLabel("Contact us");
		
		AboutAndContact() {
			this.setOpaque(false);
			this.setLayout(new FlowLayout());
			this.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
			setLabelActions();
			
			applyLabelStyle(aboutLabel);
			this.add(aboutLabel);
			
			applyLabelStyle(bar);
			this.add(bar);
			
			applyLabelStyle(contactLabel);
			this.add(contactLabel);

		}
		
		void setLabelActions() {
			aboutLabel.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					aboutLabel.setForeground(Color.decode("#ababab"));
				}
			});
			
			aboutLabel.addMouseListener(new MouseAdapter() {
				public void mouseExited(MouseEvent e) {
					aboutLabel.setForeground(Color.WHITE);
				}
			});
			
			aboutLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					Frame.showAboutUsPanel();
				}
			});
			
			contactLabel.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					contactLabel.setForeground(Color.decode("#ababab"));
				}
			});
			
			contactLabel.addMouseListener(new MouseAdapter() {
				public void mouseExited(MouseEvent e) {
					contactLabel.setForeground(Color.WHITE);
				}
			});
			
			contactLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					Frame.showContactUsPanel();
				}
			});
		}
		
		void applyLabelStyle(JLabel label) {
			label.setFont(new Font("Verdana", Font.PLAIN, 15));
			label.setForeground(Color.WHITE);
		}
	}
	
}
