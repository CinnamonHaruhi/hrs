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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
class ContactUs extends JPanel {
	JLabel contactUsTitle =  new JLabel("Contact us");
     
	ContactUs() {
		setComponentProperties();
		this.setLayout(new BorderLayout());
		this.add(contactUsTitle, BorderLayout.NORTH);
		this.add(new ContactUsText(), BorderLayout.CENTER);
	}
	
	void setComponentProperties() {
		contactUsTitle.setHorizontalAlignment(JLabel.CENTER);
		contactUsTitle.setFont(new Font("Verdana", Font.BOLD, 25));
		contactUsTitle.setBorder(BorderFactory.createEmptyBorder(50, 10, 0, 10));
	}
}

@SuppressWarnings("serial")
class ContactUsText extends JPanel {
	ImageIcon img = new ImageIcon("C:\\Users\\Abygail Medrano\\Downloads\\ContactUsImg500x500.jpg");
	JLabel imgLabel = new JLabel();
	JTextArea contactUsText = new JTextArea("Welcome to 2BIT Hotel, your budget-friendly sanctuary in the heart of the city, near Tagaytay. Enjoy fantastic views where comfort meets convenience, all without the high price tag. Our clean and spacious rooms are equipped with essential amenities, providing a welcoming atmosphere that is perfect for both business and leisure travelers. With our unbeatable location and on-site services, your stay is guaranteed to be both affordable and memorable."
			+ "\n\n\n\n\n\n\n\n\n\nPhone: +63 91234567\nEmail: 2BITS@hotel.com");
	JPanel buttonPanel = new JPanel();
    JButton backBtn = new JButton("Back");
    GridBagConstraints gbc = new GridBagConstraints();

    ContactUsText() {
    	this.setLayout(new GridBagLayout());
    	setComponentProperties();
    	
    	gbc.gridx = 0;
    	gbc.gridy = 0;
    	gbc.insets = new Insets(10, 0, 10, 0);
    	this.add(imgLabel, gbc);
    	
    	gbc.gridx = 1;
    	this.add(contactUsText, gbc);
    
    	gbc.gridx = 0;
    	gbc.gridy = 1;
    	gbc.anchor = GridBagConstraints.WEST;
    	this.add(getButtonPanel(), gbc);
    	
    	backBtn.addActionListener((ActionEvent e) -> Frame.showLoginSelection());
    }
    
   void setComponentProperties() {
	   imgLabel.setIcon(img);
	   contactUsText.setFont(new Font("Verdana", Font.PLAIN, 20));
	   contactUsText.setPreferredSize(new Dimension(800, 500));
	   contactUsText.setEditable(false);
	   contactUsText.setFocusable(false);
	   contactUsText.setWrapStyleWord(true);
	   contactUsText.setLineWrap(true);
	   contactUsText.setForeground(Color.WHITE);
	   contactUsText.setBackground(Color.decode("#ab782b"));
	   
	   contactUsText.setBorder(BorderFactory.createCompoundBorder(
			   contactUsText.getBorder(), 
		        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
	   
	   applyButtonStyle(backBtn);
    }
   
   
   JPanel getButtonPanel() {
	   buttonPanel.setLayout(new BorderLayout());
	   buttonPanel.add(backBtn, BorderLayout.CENTER);
	   return buttonPanel;
   }
   
   private void applyButtonStyle(JButton btn) {
	   btn.setBackground(Color.decode("#433217"));
	   btn.setForeground(Color.WHITE);
	   btn.setFont(new Font("Verdana", Font.PLAIN, 20));
	}
   
}
