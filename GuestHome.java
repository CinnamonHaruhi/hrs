package hrs_re_2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
class GuestHome extends JPanel{
	static Cancel cancelDialog;
	static Status statusDialog;
	
	GuestHome() {
		this.setLayout(new BorderLayout());
		this.add(new ButtonsPanel(), BorderLayout.CENTER);
		this.add(new WelcomePanel(), BorderLayout.WEST);
	}
	
	class WelcomePanel extends JPanel {
		private JLabel welcomeTitle = new JLabel("Welcome!");
		private JTextArea welcomeDesc = new JTextArea();
		private ImageIcon hotelImg = new ImageIcon("D:\\hrs_resources\\guestBG.jpg");
		
		WelcomePanel() {
			this.setBackground(Color.WHITE);
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			welcomeTitle.setBorder(BorderFactory.createEmptyBorder(130, 30, 30, 30));
			welcomeTitle.setAlignmentX(CENTER_ALIGNMENT);
			welcomeTitle.setFont(new Font("Verdana", Font.BOLD, 40));
			welcomeTitle.setForeground(Color.WHITE);
			this.add(welcomeTitle);
			
			welcomeDesc.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));
			welcomeDesc.setText("2BIT hotel is your budget-friendly sanctuary in the heart of the city, near Tagaytay."
					+ " Enjoy fantastic views where comfort meets convenience, all without the high price tag.");
			welcomeDesc.setFont(new Font("Verdana", Font.PLAIN, 25));
			welcomeDesc.setForeground(Color.WHITE);
			welcomeDesc.setEditable(false);
			welcomeDesc.setFocusable(false);
			welcomeDesc.setWrapStyleWord(true);
			welcomeDesc.setLineWrap(true);
			welcomeDesc.setOpaque(false);
			welcomeDesc.setPreferredSize(new Dimension(525, 500));
			this.add(welcomeDesc);
		}
		
		 @Override
		 protected void paintComponent(Graphics g) {
		     super.paintComponent(g);;
		     g.drawImage(hotelImg.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
		 }
	}
	
	class ButtonsPanel extends JPanel {
		private JButton reservationBtn = new JButton("Make reservation");
		private JButton checkBtn = new JButton("Check reservation status");
		private JButton cancelBtn = new JButton("Cancel reservation");
		private JButton backBtn = new JButton("Back");
		
		ButtonsPanel() {
			this.setBackground(Color.WHITE);
			this.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
			this.setLayout(new GridLayout(5, 1, 10, 10));
			
			this.add(new LogoPanel());

			applyButtonStyle(reservationBtn);
			this.add(reservationBtn);
		
			applyButtonStyle(checkBtn);
			this.add(checkBtn);
			
			applyButtonStyle(cancelBtn);
			this.add(cancelBtn);
			
			applyButtonStyle(backBtn);
			this.add(backBtn);
			
			reservationBtn.addActionListener((ActionEvent e) -> Guest.showFormsPanel());
			checkBtn.addActionListener((ActionEvent e) -> showReservationStatus());
			cancelBtn.addActionListener((ActionEvent e) -> {
				cancelReservation();
			});
			backBtn.addActionListener((ActionEvent e) -> Frame.showLoginSelection());
		}
		
		private void showReservationStatus() {
			String refNo = JOptionPane.showInputDialog("Enter your reservation's reference number: ");
			
			if (refNo == null) {
				return;
			}
			
			try {
				Connection con = DBconnection.getConnection();
				PreparedStatement st = con.prepareStatement("SELECT * FROM reservations WHERE reservation_id=?;");
				st.setString(1, refNo);
				ResultSet rs = st.executeQuery();
				
				if (! rs.next()) {
					JOptionPane.showMessageDialog(null, "Reservation no. does not exist!", "Error", 
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				String status = rs.getString("status");
				statusDialog = new Status(status);
				statusDialog.showStatus();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private void cancelReservation() {
			cancelDialog = new Cancel();
			cancelDialog.showCancelDialog();
		}
		
		private void applyButtonStyle(JButton btn) {
			btn.setMaximumSize(new Dimension(800, 100));
			btn.setBackground(Color.decode("#ab782b"));
			btn.setForeground(Color.WHITE);
			btn.setFont(new Font("Verdana", Font.PLAIN, 30));
			btn.setFocusable(false);
			btn.setAlignmentX(CENTER_ALIGNMENT);
		}
	}
	
	class LogoPanel extends JPanel {
		private ImageIcon logo = new ImageIcon("D:/hrs_resources/2bitLogo285x190.png");
		private JLabel logoLabel = new JLabel();
		
		LogoPanel() {
			this.setBackground(Color.WHITE);
			this.setLayout(new BorderLayout());
			logoLabel.setHorizontalAlignment(JLabel.CENTER);
			logoLabel.setIcon(logo);
			logoLabel.setPreferredSize(new Dimension(50, 50));
			this.add(logoLabel);
		}
	}
}

