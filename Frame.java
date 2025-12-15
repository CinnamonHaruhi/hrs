package hrs_re_2;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class Frame extends JFrame{
	static CardLayout cl = new CardLayout();
	static JPanel cardPanel = new JPanel(cl);
	
	Frame() {		
		this.setSize(700, 500);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setTitle("Hotel reservation system");

		cardPanel.add(new LoginSelection(), "login");
		cardPanel.add(new MainPanel(), "main");
		cardPanel.add(new ContactUs(), "contact");
		cardPanel.add(new AboutUs(), "about");
		
		this.add(cardPanel);	
		this.setVisible(true);
	}
	
	static void showLoginSelection() {
		cl.show(cardPanel, "login");
	}
	
	static void showMainPanel() {
		cl.show(cardPanel, "main");
	}
	
	static void showContactUsPanel() {
		cl.show(cardPanel, "contact");
	}
	
	static void showAboutUsPanel() {
		cl.show(cardPanel, "about");
	}
}
