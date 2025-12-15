package hrs_re_2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
class RoomActionDialog extends JDialog {
	static RoomActionPanel RAP;
	private JPanel titlePanel = new JPanel();
	private JLabel radTitle = new JLabel("Add room");
	RoomActionDialog(String actionType) {
		super(Main.frame, "Add room", true);
		this.setSize(460, 617);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.getContentPane().setBackground(Color.decode("#ab782b"));
		this.setLayout(new BorderLayout());
		RAP = new RoomActionPanel(actionType);
		
		if (actionType.equals("edit")) {
			this.setTitle("Edit room");
			radTitle.setText("Edit room");
			addRoomDataToPanel();
		}
		
		this.add(getTitlePanel(), BorderLayout.NORTH);
		this.add(RAP, BorderLayout.CENTER);
	}
	
	void showRoomActionDialog() {
		this.setVisible(true);
	}
	
	void closeRoomActionDialog() {
		this.dispose();
	}
	
	RoomActionPanel getRoomActionPanel() {
		return RAP;
	}
	
	JPanel getTitlePanel() {
		titlePanel.setBackground(Color.decode("#ab782b"));
		titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		titlePanel.add(radTitle);
		return titlePanel;
	}
	
	void addRoomDataToPanel() {
		RAP.roomNoField.setText(String.valueOf(Rooms.getSelectedRowID()));
		RAP.roomNoField.setEditable(false);
		RAP.floorField.setText(((Integer)Rooms.getFloor()).toString());
		RAP.priceField.setText(((Integer)Rooms.getPrice()).toString());
		RAP.imgField.setText(Rooms.getImg());
		RAP.descArea.setText(Rooms.getDescription());
		
		for (int i = 0; i < 5; i++) {
			if (RAP.typeBox.getItemAt(i).contains(Rooms.getType())) {
				RAP.typeBox.setSelectedIndex(i);
			}
		}
		
		for (int i = 0; i < 4; i++) {
			if (RAP.statusBox.getItemAt(i).contains(Rooms.getStatus())) {
				RAP.statusBox.setSelectedIndex(i);
			}
		}
		
	}
	
	class RoomActionPanel extends JPanel {
		private JButton editBtn = new JButton("Edit");
		private JButton addBtn = new JButton("Add");
		private JButton cancelBtn = new JButton("Cancel");
		
		private ArrayList<JLabel> labels = new ArrayList<>();
		private JLabel roomNoLabel = new JLabel("Room no:");
		private JLabel typeLabel = new JLabel("Type:");
		private JLabel floorLabel = new JLabel("Floor:");
		private JLabel priceLabel = new JLabel("Price:");
		private JLabel statusLabel = new JLabel("Status:");
		
		private ArrayList<JTextField> textFields = new ArrayList<>();
		private JTextField roomNoField = new JTextField();
		private String[] types = {"Standard", "Deluxe", "Executive", "Junior", "Presidential"};
		private JComboBox<String> typeBox = new JComboBox<String>(types);
		private JTextField floorField = new JTextField();
		private JTextField priceField = new JTextField();
		private String[] statuses = {"VC - Vacant & Clean", "VD - Vacant & Dirty", "OOO - Out of Order", "O - Occupied"};
		private JComboBox<String> statusBox = new JComboBox<String>(statuses);
		private GridBagConstraints gbc = new GridBagConstraints();
		
		private JButton chooseFileBtn = new JButton("Select image");
		private JTextField imgField = new JTextField("No file chosen");
		
		private JLabel descLabel = new JLabel("Room description:");
		private JTextArea descArea = new JTextArea();
		
		private JPanel editButtonPanel = new JPanel();
		private JPanel addButtonPanel = new JPanel();
		
		RoomActionPanel (String actionType) {
			this.setLayout(new GridBagLayout());
			this.setBackground(Color.decode("#ab782b"));
			addComponentsToArray();
			setComponentProperties();
	
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 10, 10);
			this.add(roomNoLabel, gbc);
			gbc.gridx = 1;
			gbc.insets = new Insets(0, 0, 10, 0);
			this.add(roomNoField, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.insets = new Insets(0, 0, 10, 10);
			this.add(typeLabel, gbc);
			gbc.gridx = 1;
			gbc.insets = new Insets(0, 0, 10, 0);
			this.add(typeBox, gbc);
			
			gbc.gridx = 0;
			gbc.gridy= 2;
			gbc.insets = new Insets(0, 0, 10, 10);
			this.add(floorLabel, gbc);
			gbc.gridx = 1;
			gbc.insets = new Insets(0, 0, 10, 0);
			this.add(floorField, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 3;
			gbc.insets = new Insets(0, 0, 10, 10);
			this.add(priceLabel, gbc);
			gbc.gridx = 1;
			gbc.insets = new Insets(0, 0, 10, 0);
			this.add(priceField, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 4;
			gbc.insets = new Insets(0, 0, 10, 10);
			this.add(statusLabel, gbc);
			gbc.gridx = 1;
			gbc.insets = new Insets(0, 0, 10, 0);
			this.add(statusBox, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 5;
			gbc.insets = new Insets(0, 0, 10, 10);
			this.add(chooseFileBtn, gbc);
			gbc.gridx = 1;
			gbc.insets = new Insets(0, 0, 10, 0);
			this.add(imgField, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 6;
			gbc.insets = new Insets(0, 0, 5, 0);
			this.add(descLabel, gbc);
			gbc.gridy = 7;
			gbc.gridwidth = 2;
			//gbc.fill = GridBagConstraints.HORIZONTAL;
			this.add(descArea, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 8;
			gbc.gridwidth = 2;
			addButton(actionType, gbc);
			
			cancelBtn.addActionListener((ActionEvent e) -> Rooms.ButtonsPanel.RAD.closeRoomActionDialog());
			addBtn.addActionListener((ActionEvent e) -> RoomActionController.addRoom());
			editBtn.addActionListener((ActionEvent e) -> RoomActionController.editRoom());
			chooseFileBtn.addActionListener((ActionEvent e) -> selectImg());
		}
		
		private void setComponentProperties() {
			radTitle.setHorizontalAlignment(JLabel.CENTER);
			radTitle.setForeground(Color.WHITE);
			radTitle.setFont(new Font("Verdana", Font.BOLD, 25));
			
			for (JTextField textField : textFields) {
				textField.setFont(new Font("Verdana", Font.PLAIN, 20));
				textField.setPreferredSize(new Dimension(200, 30));		
			}
			
			for (JLabel label : labels) {
				label.setFont(new Font("Verdana", Font.PLAIN, 20));
				label.setForeground(Color.WHITE);
			}
			
			typeBox.setFont(new Font("Verdana", Font.PLAIN, 20));
			typeBox.setPreferredSize(new Dimension(200, 30));
			statusBox.setFont(new Font("Verdana", Font.PLAIN, 20));
			statusBox.setPreferredSize(new Dimension(200, 30));
			
			imgField.setEditable(false);
			imgField.setFocusable(false);
			applyButtonStyle(chooseFileBtn);
			
			descArea.setPreferredSize(new Dimension(300, 150));

			descArea.setLineWrap(true);
			descArea.setWrapStyleWord(true);
			descArea.setFont(new Font("Verdana", Font.PLAIN ,15));
						
			applyButtonStyle(editBtn);
			applyButtonStyle(addBtn);
			applyButtonStyle(cancelBtn);

		}
		
		private void addButton(String actionType, GridBagConstraints gbc) {
			if (actionType.equals("edit")) {
				this.add(getEditButtonPanel(), gbc);
			} else {
				this.add(getAddButtonPanel(), gbc);
			}
		}
		
		private JPanel getEditButtonPanel() {
			editButtonPanel.setBackground(Color.decode("#ab782b"));
			editButtonPanel.setLayout(new GridLayout(1, 2, 10, 0));
			editButtonPanel.add(cancelBtn);
			editButtonPanel.add(editBtn);
			return editButtonPanel;
		}
		
		private JPanel getAddButtonPanel() {
			addButtonPanel.setBackground(Color.decode("#ab782b"));
			addButtonPanel.setLayout(new GridLayout(1, 2, 10, 0));
			addButtonPanel.add(cancelBtn);
			addButtonPanel.add(addBtn);
			return addButtonPanel;
		}
		
		private void applyButtonStyle(JButton btn) {
			btn.setFont(new Font("Vedana", Font.PLAIN, 20));
			btn.setBackground(Color.decode("#433217"));
			btn.setForeground(Color.WHITE);
		}
		
		void addComponentsToArray() {
			labels.add(roomNoLabel);
			labels.add(typeLabel);
			labels.add(floorLabel);
			labels.add(priceLabel);
			labels.add(statusLabel);
			labels.add(descLabel);

			textFields.add(roomNoField);
			textFields.add(floorField);
			textFields.add(priceField);
			textFields.add(imgField);
		}
		
		void selectImg() {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("D:/hrs_resources"));
			fileChooser.setFileFilter(new FileNameExtensionFilter("image filter", "jpg", "png"));
			int response = fileChooser.showOpenDialog(Rooms.ButtonsPanel.RAD);
			
			if (response == JFileChooser.APPROVE_OPTION) {
				File imgPath = new File(fileChooser.getSelectedFile().getAbsolutePath());
				imgField.setText(imgPath.toString());
			}
		}
		
		String getRoomNo() {
			return roomNoField.getText();
		}
		
		String getType() {
			return typeBox.getSelectedItem().toString();
		}
		
		String getFloor() {
			return floorField.getText();
		}
		
		String getStatus() {
			switch(statusBox.getSelectedIndex()) {
			case 0:
				return "VC";
			case 1:
				return "VD";
			case 2:
				return "OOO";
			case 3:
				return "O";
			default:
				return "";
			}
		}
		
		String getPrice() {
			return priceField.getText();
		}
		
		String getImgPath() {
			return imgField.getText();
		}
		
		String getDescription() {
			return descArea.getText();
		}
	}
}
