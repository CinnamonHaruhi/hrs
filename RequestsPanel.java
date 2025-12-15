package hrs_re_2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class RequestsPanel extends JPanel {
	ArrayList<Request> requests = new ArrayList<>();	
	JLabel cancelLabel = new JLabel("No cancel requests yet");
	
	RequestsPanel() {
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
		//this.setBackground(Color.decode("#ab782b"));
		
		cancelLabel.setFont(new Font("Verdana", Font.PLAIN, 25));
		cancelLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		retrieveRequests();		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}
	
	void addRequests() {
		for (Request request : requests) {
			this.add(request);
		}
	}

	void retrieveRequests() {
		try {
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement("SELECT * FROM cancelRequests;");
			ResultSet rs = st.executeQuery();
			
			if (!rs.isBeforeFirst()) {
				this.add(cancelLabel);
			} else {
				this.remove(cancelLabel);
			}

			while (rs.next()) {
				requests.add(new Request(rs.getString("reservation_id"), rs.getString("reason")));
			}
			addRequests();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void removeCancelPanels() {
		for (Request request : requests) {
			this.remove(request);
		} 
	}
}
