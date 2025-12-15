package hrs_re_2;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import javax.swing.JOptionPane;

class LoginController {
	static LoginDialog loginDialog = new LoginDialog();
	
	static void showAdminPanel() {
		if (authorizedLogin()) {
			JOptionPane.showMessageDialog(null, "Logged in succesfully!", "Login successful", 
					JOptionPane.INFORMATION_MESSAGE);
			
			loginDialog.dispose();
			loginDialog.getFieldsPanel().clearLoginFields();
			Admin.isAdmin = true;
			MainPanel.header.showButtonsPanel();
			Frame.showMainPanel();
			MainPanel.showAdminPanel();
			Admin.showReservationsPanel();
		}
	}
	
	static boolean authorizedLogin() {
		return fieldsNotEmpty() && credentialsValid();
	}
	
	static boolean fieldsNotEmpty() {
		if (loginDialog.getFieldsPanel().getUsername().equals("") || loginDialog.getFieldsPanel().getPassword().equals("")) {
			JOptionPane.showMessageDialog(null, "Please fill in all required fields!", "Error", 
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	
	static boolean credentialsValid() {	
		try {
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement("SELECT * FROM admins WHERE username=\"" + loginDialog.getFieldsPanel().getUsername() + "\"");
			ResultSet rs = st.executeQuery();
			
			if (!rs.next()) {
				JOptionPane.showMessageDialog(null, "Account not found!", "Error", 
						JOptionPane.ERROR_MESSAGE);	
				con.close();
				return false;
			}

			if (!rs.getString("password").equals(loginDialog.getFieldsPanel().getPassword())) {
				JOptionPane.showMessageDialog(null, "Incorrect password!", "Error", 
						JOptionPane.ERROR_MESSAGE);
				con.close();
				return false;
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
