package hrs_re_2;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
//import java.sql.SQLException;
import java.text.ParseException;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;

@SuppressWarnings("serial")
class Form extends JPanel {
	JLabel formLabel = new JLabel("Create reservation");
	Calendar calendar = Calendar.getInstance();
	ArrayList<JLabel> labels = new ArrayList<>();
	ArrayList<JTextField> textFields = new ArrayList<>();
	ArrayList<JSpinner> spinners = new ArrayList<>();
	GridBagConstraints gbc = new GridBagConstraints();
	SpinnerModel numberModel;
	
	Form() {
		this.setBackground(Color.decode("#ab782b"));
		this.setLayout(new GridBagLayout());
		addComponentsToArray();
		setSpinnerModels();
		setComponentProperties();
		addComponents();
	}
	
	private void addComponents() {
		gbc.insets = new Insets(0, 0, 20, 0);
		gbc.gridy = 0;
		gbc.gridx = 0;
		formLabel.setForeground(Color.WHITE);
		formLabel.setFont(new Font("Verdana", Font.BOLD, 30));
		this.add(formLabel, gbc);
		
		gbc.insets = new Insets(0, 0, 0, 20);
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.gridy = 1;
		gbc.gridx = 0;
		this.add(labels.get(0), gbc);
		gbc.insets = new Insets(0, 0, 20, 20);
		gbc.gridy = 2;
		this.add(textFields.get(0), gbc);
		
		gbc.insets = new Insets(0, 0, 0, 20);
		gbc.gridy = 3;
		this.add(labels.get(1), gbc);
		gbc.insets = new Insets(0, 0, 20, 20);
		gbc.gridy = 4;
		this.add(textFields.get(1), gbc);
		
		gbc.insets = new Insets(0, 0, 0, 20);
		gbc.gridy = 5;
		this.add(labels.get(2), gbc);
		gbc.insets = new Insets(0, 0, 20, 20);
		gbc.gridy = 6;
		this.add(textFields.get(2), gbc);
		
		gbc.insets = new Insets(0, 0, 0, 20);
		gbc.gridy = 7;
		this.add(labels.get(3), gbc);
		gbc.insets = new Insets(0, 0, 20, 20);
		gbc.gridy = 8;
		this.add(textFields.get(3), gbc);
		
		//
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridy = 1;
		gbc.gridx = 1;
		this.add(labels.get(4), gbc);
		gbc.gridy = 2;
		gbc.insets = new Insets(0, 0, 20, 0);
		this.add(spinners.get(0), gbc);
		
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridy = 3;
		this.add(labels.get(5), gbc);
		gbc.insets = new Insets(0, 0, 20, 0);
		gbc.gridy = 4;
		this.add(spinners.get(1), gbc);
		
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridy = 5;
		this.add(labels.get(6), gbc);
		gbc.insets = new Insets(0, 0, 20, 0);
		gbc.gridy = 6;
		this.add(spinners.get(2), gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridy = 8;
		gbc.gridx = 1;
		this.add(new ButtonsPanel(), gbc);
	}
	
	private void addComponentsToArray() {
		labels.add(new JLabel("First name: "));
		labels.add(new JLabel("Last name: "));
		labels.add(new JLabel("Email: "));
		labels.add(new JLabel("Contact number: "));
		labels.add(new JLabel("No. of persons: "));
		labels.add(new JLabel("Check-in date: "));
		labels.add(new JLabel("Check-out date: "));
		
		textFields.add(new JTextField());
		textFields.add(new JTextField());
		textFields.add(new JTextField());
		textFields.add(new JTextField());

		spinners.add(new JSpinner());
		spinners.add(new JSpinner());
		spinners.add(new JSpinner());
	}
	
	private void setComponentProperties() {
		for (JTextField textField : textFields) {
			textField.setPreferredSize(new Dimension(300, 40));
			textField.setFont(new Font("Verdana", Font.PLAIN, 15));
		}
		for (JSpinner spinner : spinners) {
			spinner.setPreferredSize(new Dimension(300, 40));
			spinner.setFont(new Font("Verdana", Font.PLAIN, 15));
		}
		for (JLabel label : labels) {
			label.setFont(new Font("Verdana", Font.PLAIN, 20));
			label.setForeground(Color.WHITE);
		}
		
		textFields.get(3).addKeyListener(new java.awt.event.KeyAdapter() {
		    public void keyTyped(java.awt.event.KeyEvent evt) {
		        if(textFields.get(3).getText().length()>=11&&!(evt.getKeyChar()==KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
		            getToolkit().beep();
		            evt.consume();
		         }
		     }
		});
	}
	
	private void setSpinnerModels() {
		numberModel = new SpinnerNumberModel(1, 1, 3, 1);
		spinners.get(0).setModel(numberModel);
		((JSpinner.DefaultEditor) spinners.get(0).getEditor()).getTextField().setEditable(false);

		spinners.get(1).setModel(getDateModel());
		spinners.get(1).setEditor(new JSpinner.DateEditor(spinners.get(1), "yyyy/MM/dd"));
		calendar = Calendar.getInstance();
		spinners.get(2).setModel(getDateModel());
		spinners.get(2).setEditor(new JSpinner.DateEditor(spinners.get(2), "yyyy/MM/dd"));
	}
	
	private SpinnerDateModel getDateModel() {
		Date initDate = calendar.getTime();
		calendar.add(Calendar.YEAR, -1);
		Date earliestDate = calendar.getTime();
		calendar.add(Calendar.YEAR, 2);
		Date latestDate = calendar.getTime();
		return new SpinnerDateModel(initDate, earliestDate, latestDate, Calendar.YEAR);
	}
	
	ArrayList<String> getFieldInputsArray() { 
		ArrayList<String> fieldInputs = new ArrayList<>();
		
		for (JTextField textField : textFields) {
			fieldInputs.add(textField.getText());
		}
		return fieldInputs;
	}
	
	int getNoOfPersons() {
		return (Integer) spinners.get(0).getValue();
	}
	
	Date getCheckinDate() {
		return (Date) spinners.get(1).getValue();
	}
	
	Date getCheckoutDate() {
		return (Date) spinners.get(2).getValue();

	}
	
	String getFirstName() {
		return getFieldInputsArray().get(0);
	}
	
	String getLastName() {
		return getFieldInputsArray().get(1);
	}
	
	String getEmail() {
		return getFieldInputsArray().get(2);
	}
	
	String getContactNumber() {
		return getFieldInputsArray().get(3);
	}
	
	private boolean validateInputs() throws ParseException {
		return FormController.validateTextFields(textFields) && 
				FormController.validateEmail(textFields.get(2)) &&
				FormController.validateNumberField(textFields.get(3)) && 
				FormController.validateDates(getCheckinDate(), getCheckoutDate());
	}
	
	class ButtonsPanel extends JPanel {
		private JButton nextBtn = new JButton("Next");
		private JButton backBtn = new JButton("Back");
		ButtonsPanel() {
			this.setBackground(Color.decode("#ab782b"));
			this.setLayout(new GridLayout(1, 2, 5, 0));
			applyButtonStyle(backBtn);
			this.add(backBtn);
			setButtonActions();
			applyButtonStyle(nextBtn);
			this.add(nextBtn);
		}
		
		void applyButtonStyle(JButton btn) {
			btn.setPreferredSize(new Dimension(145, 40));
			btn.setBackground(Color.decode("#433217"));
			btn.setForeground(Color.WHITE);
			btn.setFont(new Font("Verdana", Font.PLAIN, 20));
		}
		
		private void setButtonActions() {
			nextBtn.addActionListener((ActionEvent e) -> {
				try {
					if (! validateInputs()) {
						return;
					}  

					if (Admin.isAdmin) {
						Admin.showRoomsPanel();
					} else {
						Guest.showRoomPanel();
					}
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			});
			
			backBtn.addActionListener((ActionEvent e) -> {
				if (Admin.isAdmin) {
					Admin.showReservationsPanel();
				} else {
					Guest.showGuestHome();
				}
			});
		}
	}
}