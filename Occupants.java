package hrs_re_2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import hrs_re_2.Reservations.ReservationsController;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
class Occupants extends JPanel {
	static OccupantActionDialog OAD;
	private static Object[] colNames = {"ID", "First name", "Last name", "Email", "Contact No."};
	static DefaultTableModel tableModel = new DefaultTableModel(colNames, 0);
	static CustomTable occupantsTable = new CustomTable(tableModel);
	static TableColumnModel tcm = occupantsTable.getColumnModel();
	private JScrollPane scrollPane = new JScrollPane(occupantsTable);
	private JButton editBtn = new JButton("Edit");
	private JPanel buttonsPanel = new JPanel();

	Occupants() {
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
		retrieveOccupants();
		CustomTable.setScrollPaneProperties(scrollPane);
		setComponentProperties();
		addComponents();
		
		editBtn.addActionListener((ActionEvent e) -> {
			if (! rowSelected()) {
				return;
			}
			OAD = new OccupantActionDialog();
			OAD.showOccupantActionDialog();
		});
	}
	
	static int getOccupantID() {
		return (int) occupantsTable.getModel()
				.getValueAt(occupantsTable.getSelectedRow(), 0);
	}
	
	static String getFirstName() {
		return (String) occupantsTable.getValueAt(occupantsTable.getSelectedRow(), 0);
	}
	
	static String getLastName() {
		return (String) occupantsTable.getValueAt(occupantsTable.getSelectedRow(), 1);
	}
	
	static String getEmail() {
		return (String) occupantsTable.getValueAt(occupantsTable.getSelectedRow(), 2);
	}
	
	static String getConNum() {
		return (String) occupantsTable.getValueAt(occupantsTable.getSelectedRow(), 3);
	}
	
	private void addComponents() {
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(getButtonsPanel(), BorderLayout.SOUTH);
	}
	
	private void setComponentProperties() {
		tcm.removeColumn(tcm.getColumn(0));
		applyButtonStyle(editBtn);
	}
	
	private JPanel getButtonsPanel() {
		buttonsPanel.setBackground(Color.WHITE);
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonsPanel.add(editBtn);
		return buttonsPanel;
	}
	
	private void applyButtonStyle(JButton btn) {
		btn.setBackground(Color.decode("#ab782b"));
		btn.setForeground(Color.WHITE);
		btn.setFont(new Font("Verdana", Font.PLAIN, 20));
	}
	
	private boolean rowSelected() {
		if (occupantsTable.getSelectedRow() != -1) {
			return true;
		}
		JOptionPane.showMessageDialog(null, "Please select an occupant!", "Error", JOptionPane.ERROR_MESSAGE);
		return false;
	}

	private static void retrieveOccupants() {	
        tableModel.setRowCount(0);

		try {
			String query = "SELECT * FROM occupants";	
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement(query);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int occupant_id = rs.getInt("occupant_id");
				String first_name = rs.getString("first_name");
				String last_name = rs.getString("last_name");
				String email = rs.getString("email");
				String contact_no = rs.getString("contact_no");
				Object[] data = {occupant_id, first_name, last_name, email, contact_no};
				tableModel.addRow(data);
			}		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void refreshOccupantsTable() {
		retrieveOccupants();
		occupantsTable.repaint();
	}
}

@SuppressWarnings("serial")
class OccupantActionDialog extends JDialog {
	private JPanel titlePanel = new JPanel();
	private JLabel OADtitle = new JLabel("Edit occupant");
	OccupantActionPanel OAP = new OccupantActionPanel();
	OccupantActionDialog() {
		super(Main.frame, "Edit occupant", true);
		this.setSize(420, 330);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.getContentPane().setBackground(Color.decode("#ab782b"));
		this.setLayout(new BorderLayout());
		
		this.add(getTitlePanel(), BorderLayout.NORTH);
		this.add(OAP, BorderLayout.CENTER);
	}
	
	void showOccupantActionDialog() {
		this.setVisible(true);
	}
	
	void closeOccupantActionDialog() {
		this.dispose();
	}
	
	JPanel getTitlePanel() {
		OADtitle.setForeground(Color.WHITE);
		OADtitle.setFont(new Font("Verdana", Font.BOLD, 25));
		
		titlePanel.setBackground(Color.decode("#ab782b"));
		titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		titlePanel.add(OADtitle);
		return titlePanel;
	}
	
	class OccupantActionPanel extends JPanel {
		ArrayList<JLabel> labels = new ArrayList<>();
		private JLabel fnameLabel = new JLabel("First name:");
		private JLabel lnameLabel = new JLabel("Last name:");
		private JLabel emailLabel = new JLabel("Email:");
		private JLabel conNumLabel = new JLabel("Contact no:");
		
		ArrayList<JTextField> textFields = new ArrayList<>();
		private JTextField fnameField = new JTextField();
		private JTextField lnameField = new JTextField();
		private JTextField emailField = new JTextField();
		private JTextField conNumField = new JTextField();
		
		private JPanel buttonsPanel = new JPanel();
		private JButton editBtn = new JButton("Edit");
		private JButton cancelBtn = new JButton("Cancel");
		private GridBagConstraints gbc = new GridBagConstraints();

		OccupantActionPanel() {
			this.setLayout(new GridBagLayout());
			this.setBackground(Color.decode("#ab782b"));
			addComponentsToArray();
			setComponentProperties();
			setComponentValues();
			
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 0, 10, 10);
			this.add(fnameLabel, gbc);
			gbc.gridx = 1;
			gbc.insets = new Insets(0, 0, 10, 0);
			this.add(fnameField, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.insets = new Insets(0, 0, 10, 10);
			this.add(lnameLabel, gbc);
			gbc.gridx = 1;
			gbc.insets = new Insets(0, 0, 10, 0);
			this.add(lnameField, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.insets = new Insets(0, 0, 10, 10);
			this.add(emailLabel, gbc);
			gbc.gridx = 1;
			gbc.insets = new Insets(0, 0, 10, 0);
			this.add(emailField, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 3;
			gbc.insets = new Insets(0, 0, 10, 10);
			this.add(conNumLabel, gbc);
			gbc.gridx = 1;
			gbc.insets = new Insets(0, 0, 10, 0);
			this.add(conNumField, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 4;
			gbc.gridwidth = 2;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			this.add(getButtonsPanel(), gbc);
			
			editBtn.addActionListener((ActionEvent e) -> {
				if (! OccupantsController.validateInputs()) {
					return;
				}
				OccupantsController.editOccupant();
				Occupants.OAD.closeOccupantActionDialog();
			});
			cancelBtn.addActionListener((ActionEvent e) -> Occupants.OAD.closeOccupantActionDialog());
		}
		
		String getFirstName() {
			return fnameField.getText();
		}
		
		String getLastName() {
			return lnameField.getText();
		}
		
		String getEmail() {
			return emailField.getText();
		}
		
		String getConNum() {
			return conNumField.getText();
		}
		
		private void setComponentValues() {
			fnameField.setText(Occupants.getFirstName());
			lnameField.setText(Occupants.getLastName());
			emailField.setText(Occupants.getEmail());
			conNumField.setText(Occupants.getConNum());
		}
		
		private void setComponentProperties() {
			for (JTextField textField : textFields) {
				textField.setFont(new Font("Verdana", Font.PLAIN, 20));
				textField.setPreferredSize(new Dimension(200, 30));		
			}
			
			for (JLabel label : labels) {
				label.setFont(new Font("Verdana", Font.PLAIN, 20));
				label.setForeground(Color.WHITE);
			}
			
			applyButtonStyle(editBtn);
			applyButtonStyle(cancelBtn);
			limitFieldInput(conNumField, 11);
		}
		
		private void applyButtonStyle(JButton btn) {
			btn.setFont(new Font("Vedana", Font.PLAIN, 20));
			btn.setBackground(Color.decode("#433217"));
			btn.setForeground(Color.WHITE);
		}
		
		private JPanel getButtonsPanel() {
			buttonsPanel.setBackground(Color.decode("#ab782b"));
			buttonsPanel.setLayout(new GridLayout(1, 2, 10, 0));
			buttonsPanel.add(cancelBtn);
			buttonsPanel.add(editBtn);
			return buttonsPanel;
		}
		
		private void addComponentsToArray() {
			labels.add(fnameLabel);
			labels.add(lnameLabel);
			labels.add(emailLabel);
			labels.add(conNumLabel);
			
			textFields.add(fnameField);
			textFields.add(lnameField);
			textFields.add(emailField);
			textFields.add(conNumField);
		}
		
		private void limitFieldInput(JTextField textField, int limit) {
			textField.addKeyListener(new java.awt.event.KeyAdapter() {
			    public void keyTyped(java.awt.event.KeyEvent evt) {
			        if(textFields.get(3).getText().length()>=limit&&!(evt.getKeyChar()==KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
			            getToolkit().beep();
			            evt.consume();
			         }
			     }
			});
		}
	}
}

class OccupantsController {
	static void editOccupant() {
		String occupant_id = String.valueOf(Occupants.getOccupantID());
		String first_name = Occupants.OAD.OAP.getFirstName();
		String last_name = Occupants.OAD.OAP.getLastName();
		String email = Occupants.OAD.OAP.getEmail();
		String contact_no = Occupants.OAD.OAP.getConNum();

		try {
			Connection con = DBconnection.getConnection();
			PreparedStatement st = con.prepareStatement("UPDATE occupants SET first_name=?, last_name=?, email=?, contact_no=? WHERE occupant_id=?;");
			st.setString(1, first_name);
			st.setString(2, last_name);
			st.setString(3, email);
			st.setString(4, contact_no);
			st.setString(5, occupant_id);
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JOptionPane.showMessageDialog(null, "Occupant successfully edited!", "Message", JOptionPane.INFORMATION_MESSAGE);
		Occupants.refreshOccupantsTable();
		ReservationsController.refreshReservationsTable();
	}
	
	static boolean validateInputs() {
		return (!fieldIsEmpty() && emailIsValid() && numberIsValid());
	}
	
	
	static boolean emailIsValid() {
		if (! Occupants.OAD.OAP.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
			JOptionPane.showMessageDialog(null, "Please enter a valid email!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	static boolean numberIsValid() {
		String contactNo = Occupants.OAD.OAP.getConNum();
		if (! contactNo.matches("[0-9]+") || contactNo.length() < 11) {
			JOptionPane.showMessageDialog(null, "Invalid number!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	static boolean fieldIsEmpty() {
		for (JTextField textField : Occupants.OAD.OAP.textFields) {
			if (textField.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Please fill in all required fields!", "Error",
						JOptionPane.ERROR_MESSAGE);
				return true;
			}
		}
		return false;
	}
}
