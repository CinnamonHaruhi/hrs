package hrs_re_2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
class Room extends JPanel {
	static int counter = 0;
	static Confirm confirmDialog;
	TitleArea titleArea;
	String roomNo, type;
	int floor, price;
	Room(String roomNo, String type, int floor, int price, String img, String desc) {
		this.roomNo = roomNo;
		this.type = type;
		this.floor = floor;
		this.price = price;
		
		this.setLayout(new BorderLayout());
		this.setBackground(Color.decode("#ab782b"));
		
		titleArea = new TitleArea(type, roomNo);
		this.add(titleArea, BorderLayout.NORTH);
		this.add(new ImageArea(price, img), BorderLayout.WEST);
		this.add(new DescArea(desc), BorderLayout.CENTER);
	}
	
	static int getRoomIndex() {
		return counter;
	}
	
	class TitleArea extends JPanel {
		JLabel titleLabel = new JLabel();
		TitleArea(String type, String roomNo) {
			this.setBackground(Color.decode("#ab782b"));
			this.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
			
			titleLabel.setText(type + " - " + roomNo);
			titleLabel.setAlignmentY(JLabel.BOTTOM_ALIGNMENT);
			titleLabel.setFont(new Font("Verdana", Font.BOLD, 20));
			titleLabel.setForeground(Color.WHITE);
			this.add(titleLabel);
		}
		
		void getRoomTitle() {
			System.out.println("This is your room: " + titleLabel.getText());
		}
	}
	
	class ImageArea extends JPanel {
		JLabel imgLabel = new JLabel();
		JLabel priceLabel;
		ImageArea(int price, String img) {
			this.setBackground(Color.decode("#ab782b"));
			this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			this.setLayout(new BorderLayout());
			
			ImageIcon roomImg = new ImageIcon(img);
			imgLabel.setIcon(roomImg);
			this.add(imgLabel, BorderLayout.CENTER);
			
			priceLabel = new JLabel("Price: ₱" + price);
			priceLabel.setHorizontalAlignment(JLabel.CENTER);
			priceLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
			priceLabel.setForeground(Color.WHITE);
			priceLabel.setBorder(BorderFactory.createEmptyBorder(17, 0, 0, 0));
			this.add(priceLabel, BorderLayout.SOUTH);
		}
	}
	
	class DescArea extends JPanel {
		JTextArea descArea = new JTextArea();
		DescArea(String desc) {
			this.setBackground(Color.decode("#ab782b"));
			this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
			this.setLayout(new BorderLayout());
			
			descArea.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
			descArea.setText(desc);
			descArea.setEditable(false);
			descArea.setFocusable(false);
			descArea.setLineWrap(true);
			descArea.setWrapStyleWord(true);
			descArea.setBackground(Color.decode("#ab782b"));
			descArea.setFont(new Font("Verdana", Font.PLAIN, 20));
			descArea.setForeground(Color.WHITE);
			this.add(descArea, BorderLayout.CENTER);
			
			this.add(new Buttons(), BorderLayout.SOUTH);
		}
		
		class Buttons extends JPanel {
			JButton prevBtn = new JButton("<");
			JButton selectBtn = new JButton("Select");
			JButton backBtn = new JButton("Back");
			JButton nextBtn = new JButton(">");
			
			Buttons() {
				this.setBackground(Color.decode("#ab782b"));
				this.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
				this.setLayout(new GridLayout(1, 3, 20, 10));
				applyButtonStyle(prevBtn);
				this.add(prevBtn);
				applyButtonStyle(backBtn);
				this.add(backBtn);
				applyButtonStyle(selectBtn);
				this.add(selectBtn);
				applyButtonStyle(nextBtn);
				this.add(nextBtn);
				applyButtonStyle(nextBtn);

				nextBtn.addActionListener((ActionEvent e) -> showNextRoom());
				prevBtn.addActionListener((ActionEvent e) -> showPrevRoom());
				backBtn.addActionListener((ActionEvent e) -> Guest.showFormsPanel());
				selectBtn.addActionListener((ActionEvent e) -> {
					confirmDialog = new Confirm();
					confirmDialog.showConfirmDialog();
				});
			}
			
			static void showNextRoom() {
				counter++;
				if (counter > RoomPanel.roomNames.length - 1) {
					counter = 0;
				}
				Guest.showRoomCard(counter);
			}
			
			static void showPrevRoom() {
				counter--;
				if (counter < 0) {
					counter = RoomPanel.roomNames.length - 1;
				}
				Guest.showRoomCard(counter);
			}
			
			static void applyButtonStyle(JButton btn) {
				btn.setPreferredSize(new Dimension(0, 40));
				btn.setFont(new Font("Verdana", Font.PLAIN, 15));
				btn.setForeground(Color.WHITE);
				btn.setBackground(Color.decode("#433217"));
			}
		}
	}
}
