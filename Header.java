package hrs_re_2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

@SuppressWarnings("serial")
class Header extends JPanel{
	JLabel systemTitle = new JLabel("2BIT hotel");	
	ButtonsPanel buttonsPanel = new ButtonsPanel();
	
	Header() {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		this.setBackground(Color.decode("#ab782b"));
		
		systemTitle.setFont(new Font("Verdana", Font.BOLD, 50));
		systemTitle.setForeground(Color.WHITE);
		this.add(systemTitle, BorderLayout.WEST);	
		this.add(buttonsPanel, BorderLayout.EAST);
	}
	
	void hideButtonsPanel() {
		this.remove(buttonsPanel);
		this.repaint();
		this.revalidate();
	}
	
	void showButtonsPanel() {
		this.add(buttonsPanel, BorderLayout.EAST);
		this.repaint();
		this.revalidate();
	}
	
	class ButtonsPanel extends JPanel {
		JButton reservationsBtn = new JButton("Reservations");
		JButton occupantsBtn = new JButton("Occupants");
		JButton roomsBtn = new JButton("Rooms");
		JButton requestsBtn = new JButton("Cancellations");
		JButton logoutBtn = new JButton("Logout");
		
		ButtonsPanel() {
			this.setLayout(new FlowLayout(FlowLayout.RIGHT));
			this.setBackground(Color.decode("#ab782b"));
			
			applyButtonStyle(reservationsBtn);
			this.add(reservationsBtn);
			
			applyButtonStyle(occupantsBtn);
			this.add(occupantsBtn);
			
			applyButtonStyle(roomsBtn);
			this.add(roomsBtn);
			
			applyButtonStyle(requestsBtn);
			this.add(requestsBtn);
			
			applyButtonStyle(logoutBtn);
			this.add(logoutBtn);
			
			logoutBtn.addActionListener((ActionEvent e) -> logout());
			roomsBtn.addActionListener((ActionEvent e) -> Admin.showAdminRooms());
			occupantsBtn.addActionListener((ActionEvent e) -> Admin.showOccupantsPanel());
			reservationsBtn.addActionListener((ActionEvent e) -> Admin.showReservationsPanel());
			requestsBtn.addActionListener((ActionEvent e) -> Admin.showRequestsPanel());
		}
		
		private void logout() {
			int result = JOptionPane.showConfirmDialog(null, 
					"Are you sure you want to log out?", "Logout", 
					JOptionPane.YES_NO_OPTION);
			if (result == 1 || result == JOptionPane.CLOSED_OPTION) {
				return;
			}
			Frame.showLoginSelection();
		}
		
		private void applyButtonStyle(JButton button) {
			button.setPreferredSize(new Dimension(150, 55));
			button.setFont(new Font("Verdana", Font.BOLD, 15));
			button.setBackground(Color.decode("#ab782b"));
			button.setForeground(Color.WHITE);
			button.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
			button.setBorderPainted(false);
			button.setFocusable(false);
			button.setOpaque(false);
			button.setContentAreaFilled(false);
			
			button.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					button.setForeground(Color.decode("#ababab"));
				}
			});
			
			button.addMouseListener(new MouseAdapter() {
				public void mouseExited(MouseEvent e) {
					button.setForeground(Color.WHITE);
				}
			});
			
			button.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					button.setForeground(Color.decode("#5c5a5a"));
				}
			});
			
			button.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					button.setForeground(Color.decode("#ababab"));
				}
			});
		}
	}
}
