package hrs_re_2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.*;

@SuppressWarnings("serial")

class LoginDialog extends JDialog{
	private AdminLogin adminLogin = new AdminLogin();
	private AdminLogin.FieldsPanel fieldsPanel = adminLogin.fieldsPanel;
	
	LoginDialog() {
		super(Main.frame, "Login", true);
		this.setSize(300, 360);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.add(adminLogin);
	}
	
	void showLoginDialog() {
		this.setVisible(true); 
	}
	
	void disposeLoginDialog() {
		this.dispose();
	}
	
	AdminLogin.FieldsPanel getFieldsPanel() {
		return fieldsPanel;
	}
	
	class AdminLogin extends JPanel{
		private JLabel loginLabel = new JLabel("Admin Login");
		FieldsPanel fieldsPanel = new FieldsPanel();
		
		AdminLogin() {
			this.setLayout(new BorderLayout());
			this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			this.setBackground(Color.decode("#ab782b"));
			
			this.add(loginLabel, BorderLayout.NORTH);
			this.add(fieldsPanel, BorderLayout.CENTER);
			this.add(new ButtonsPanel(), BorderLayout.SOUTH);
		}
		
		class FieldsPanel extends JPanel {
			private JLabel unameLabel = new JLabel("Username: ");
			private JLabel passLabel = new JLabel("Password: ");
			private JTextField unameField = new JTextField();
			private JPasswordField passField = new JPasswordField();
			GridBagConstraints gbc = new GridBagConstraints();
			
			FieldsPanel() {
				this.setLayout(new GridBagLayout());
				this.setBackground(Color.decode("#ab782b"));
				this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
				
				gbc.gridy = 0;
				gbc.anchor = GridBagConstraints.WEST;
				this.add(unameLabel, gbc);
				gbc.gridy = 1;
				gbc.insets = new Insets(0, 0, 10, 0);
				this.add(unameField, gbc);
				gbc.gridy = 2;
				gbc.insets = new Insets(10, 0, 0, 0);
				this.add(passLabel, gbc);
				gbc.gridy = 3;
				gbc.insets = new Insets(0, 0, 0, 0);
				this.add(passField, gbc);
				
				applyLabelStyle(unameLabel);
				applyLabelStyle(passLabel);
				applyFieldStyle(unameField);
				applyFieldStyle(passField);
			}
			
			private void applyLabelStyle(JLabel label) {
				label.setFont(new Font("Verdana", Font.PLAIN, 20));
				label.setForeground(Color.WHITE);
			}
			
			private void applyFieldStyle(JTextField textField) {
				textField.setPreferredSize(new Dimension(200, 30));
				textField.setBorder(BorderFactory.createEmptyBorder());
				textField.setFont(new Font("Verdana", Font.PLAIN, 15));
			}
			
			String getUsername() {
				return unameField.getText();
			}
			
			String getPassword() {
				return new String(passField.getPassword());
			}
			
			void clearLoginFields() {
				unameField.setText("");
				passField.setText("");
			}
		}
		
		class ButtonsPanel extends JPanel {
			private JButton loginBtn = new JButton("Login");

			ButtonsPanel() {
				loginLabel.setHorizontalAlignment(JLabel.CENTER);
				loginLabel.setVerticalAlignment(JLabel.CENTER);
				loginLabel.setFont(new Font("Verdana", Font.BOLD, 35));
				loginLabel.setForeground(Color.WHITE);
				
				this.setBackground(Color.decode("#ab782b"));
				this.setLayout(new FlowLayout());
				applyButtonStyle(loginBtn);
				this.add(loginBtn);
				
				
				loginBtn.addActionListener((ActionEvent e) -> {
						LoginController.showAdminPanel();
				});
			}
			
			private void applyButtonStyle(JButton button) {
				button.setFocusable(false);
				button.setForeground(Color.WHITE);
				button.setBackground(Color.decode("#433217"));
				button.setPreferredSize(new Dimension(125, 40));
				button.setFont(new Font("Verdana", Font.PLAIN, 20));
			}
		}
	}
}

