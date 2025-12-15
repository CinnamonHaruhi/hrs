package hrs_re_2;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.*;

@SuppressWarnings("serial")
class MainPanel extends JPanel {
	static CardLayout cl = new CardLayout();
	static JPanel cardPanel = new JPanel(cl);
	static Header header = new Header();

	MainPanel() {
		this.setLayout(new BorderLayout());
		this.add(header, BorderLayout.NORTH);
		
		cardPanel.add(new Admin(), "admin");
		cardPanel.add(new Guest(), "guest");
		this.add(cardPanel, BorderLayout.CENTER);
	}
	
	static void showAdminPanel() {
		cl.show(cardPanel, "admin");
	}
	
	static void showGuestPanel() {
		cl.show(cardPanel, "guest");
	}
}