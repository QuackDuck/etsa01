package GUI;

import java.util.Locale;
import java.util.TreeSet;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.GridLayout;
import java.awt.Component;
import javax.swing.border.EmptyBorder;
import java.io.File;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import SYS.BicycleGarageManager;
import SYS.User;
import SYS.Bicycle;

/**
 * This class creates the main Operator Interface.
 *
 */
@SuppressWarnings("serial")
public class OperatorGUI extends JFrame {
	private SYS.BicycleGarageManager bgm;
	private JTextArea bikeArea;

	private JPanel startPanel;
	private JPanel bp;
	private JPanel searchResultPanel;
	private JPanel panel;

	public JTextField pinTextField;
	public JTextField pinCodeTextField;
	public JTextField phoneNumTextField;
	public JTextField bicycleTextField;
	public JTextField nameTextField;
	public JTextField searchTextField;

	private String currentPin;
	private String currentPinCode;
	private String currentPhoneNum;
	private String currentName;
	private String currentBarcode;

	private JLabel userCount;

	private User currentUser;

	public static final int DEFAULT_MODE = 0;
	public static final int CREATE_MODE = 1;
	public static final int EDIT_MODE = 2;
	public static final int SEARCH_MODE = 3;
	public static final int VIEW_MODE = 4;

	private int currentMode;

	/**
	 * Constructor, creates and populates the GUI.
	 * @param bgm The BicycleGarageManager to use.
	 */
	public OperatorGUI(BicycleGarageManager bgm) {
		super("Operator Interface");

		this.bgm = bgm;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Locale.setDefault(new Locale("en"));
		setPreferredSize(new Dimension(600, 600));
		/** To avoid hardcoded Swedish text on OptionPane dialogs */
		UIManager.put("OptionPane.cancelButtonText", "Cancel");

		/**
		 * Create and populate the MenuBar.
		 */
		setLayout(new BorderLayout());
		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);
		JMenu fileMenu = new JMenu("File");
		menubar.add(fileMenu);
		fileMenu.add(new OpenMenu(this));
		fileMenu.add(new SaveMenu(this));
		fileMenu.add(new SaveAsMenu(this));
		/**
		 * Create and populate the ButtonPanel.
		 */
		bp = new JPanel();
		bp.add(new NewUserButton(this));
		bp.add(new EditUserButton(this));
		searchTextField = new JTextField("Search ...");
		searchTextField.setPreferredSize(new Dimension(100, 30));
		bp.add(searchTextField);
		bp.add(new SearchButton(this));

		/**
		 * Create and populate the StartPanel (default view).
		 */
		startPanel = new JPanel();
		changeView(DEFAULT_MODE);
	}

	/**
	 * Changes the view of the GUI.
	 * @param mode One of the static MODE_? fields of this class.
	 */
	public void changeView(int mode) {
		startPanel.removeAll();
		if (searchResultPanel != null) {
			searchResultPanel.removeAll();
		}
		if (panel != null) {
			panel.removeAll();
		}
		dispose();
		switch (mode) {
<<<<<<< HEAD
			case CREATE_MODE:
				pinTextField = new JTextField("Personal Identity Number (PIN)");
				pinCodeTextField = new JTextField("PIN-code");
				phoneNumTextField = new JTextField("Telephone Number");
				bicycleTextField = new JTextField("Bicycle");
				nameTextField = new JTextField("Name");
				startPanel.add(pinTextField);
				startPanel.add(pinCodeTextField);
				startPanel.add(phoneNumTextField);
				startPanel.add(bicycleTextField);
				startPanel.add(nameTextField);
				startPanel.add(new SaveButton(this));
				startPanel.add(new CancelButton(this));
				currentMode = CREATE_MODE;
				break;
			case EDIT_MODE:
				currentPin = currentUser.getPIN();
				currentPinCode = currentUser.getPinCode();
				currentPhoneNum = currentUser.getPhoneNum();
		        currentBarcode = currentUser.getBicycle().getBarcode();
		        currentName = currentUser.getName();
		        pinTextField = new JTextField(currentPin);
		        pinTextField.setPreferredSize(new Dimension(110, 30));
				pinCodeTextField = new JTextField(currentPinCode);
				pinCodeTextField.setPreferredSize(new Dimension(60, 30));
		        phoneNumTextField = new JTextField(currentPhoneNum);
		        phoneNumTextField.setPreferredSize(new Dimension(110, 30));
		        bicycleTextField = new JTextField(currentBarcode);
		        bicycleTextField.setPreferredSize(new Dimension(60, 30));
		        nameTextField = new JTextField(currentName);
		        nameTextField.setPreferredSize(new Dimension(150, 30));
				pinTextField.setEditable(false);
				pinCodeTextField.setEditable(false);
				bicycleTextField.setEditable(false);
		        startPanel.add(pinTextField);
				startPanel.add(pinCodeTextField);
		        startPanel.add(phoneNumTextField);
		        startPanel.add(bicycleTextField);
		        startPanel.add(nameTextField);
		        startPanel.add(new SaveButton(this));
		        startPanel.add(new CancelButton(this));
    		    startPanel.add(new DeleteUserButton(this));
				currentMode = EDIT_MODE;
				break;
			case SEARCH_MODE:
				searchResultPanel = new JPanel();
				setContentPane(searchResultPanel);
				searchResultPanel.setLayout(null);

				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(32, 32, 500, 400);
				searchResultPanel.add(scrollPane);

				panel = new JPanel();
				scrollPane.setViewportView(panel);
				panel.setLayout(new GridLayout(100, 1));

				TreeSet<User> result = bgm.searchUsers(searchTextField.getText());
                if (result.size() != 0) {
                    int n = 0;
                    for (User user : result) {
                        JPanel panel_1 = new JPanel();
                        JTextField jtf = new JTextField(user.getName());
                        jtf.setEditable(false);
                        panel_1.add(jtf);
                        panel_1.add(new ViewUserButton(this, user));
                        if(n % 2 == 0) {
                            panel_1.setBackground(SystemColor.inactiveCaptionBorder);
                        }
                        panel.add(panel_1);
                        n++;
                    }
                } else {
                    showMessageDialog("No user found");
					changeView(DEFAULT_MODE);
                }
				searchResultPanel.add(new CancelButton(this));
				currentMode = SEARCH_MODE;
				break;
			case VIEW_MODE:
				currentPin = currentUser.getPIN();
				currentPinCode = currentUser.getPinCode();
				currentPhoneNum = currentUser.getPhoneNum();
				currentBarcode = currentUser.getBicycle().getBarcode();
				currentName = currentUser.getName();
				pinTextField = new JTextField(currentPin);
				pinCodeTextField = new JTextField(currentPinCode);
				phoneNumTextField = new JTextField(currentPhoneNum);
				bicycleTextField = new JTextField(currentBarcode);
				nameTextField = new JTextField(currentName);
				pinTextField.setEditable(false);
				pinCodeTextField.setEditable(false);
				bicycleTextField.setEditable(false);
				phoneNumTextField.setEditable(false);
				nameTextField.setEditable(false);
				startPanel.add(pinTextField);
				startPanel.add(pinCodeTextField);
				startPanel.add(phoneNumTextField);
				startPanel.add(bicycleTextField);
				startPanel.add(nameTextField);
				startPanel.add(new SaveButton(this));
				startPanel.add(new CancelButton(this));
				startPanel.add(new DeleteUserButton(this));
				startPanel.add(new PrintBarcodeButton(this));
				currentMode = VIEW_MODE;
				break;
			default:
				bikeArea = new JTextArea(20,20);
				bikeArea.setEditable(false);
				startPanel.add(bikeArea);
				startPanel.add(new JScrollPane(bikeArea));

				userCount = new JLabel("Registered users: " + bgm.getUserCount());
				startPanel.add(userCount);
				
				bikeArea.append("Bikes currently in garage:" + "\n");
				TreeSet<Bicycle> bikes = bgm.bicyclesInGarage();
				for (Bicycle b : bikes) {
					bikeArea.append(b.getBarcode() + "\n");
				}

				currentMode = DEFAULT_MODE;
				break;
=======
		case CREATE_MODE:
			pinTextField = new JTextField("Personal Identity Number (PIN)");
			pinCodeTextField = new JTextField("PIN-code");
			phoneNumTextField = new JTextField("Telephone Number");
			bicycleTextField = new JTextField("Bicycle");
			nameTextField = new JTextField("Name");
			startPanel.add(pinTextField);
			startPanel.add(pinCodeTextField);
			startPanel.add(phoneNumTextField);
			startPanel.add(bicycleTextField);
			startPanel.add(nameTextField);
			startPanel.add(new SaveButton(this));
			startPanel.add(new CancelButton(this));
			currentMode = CREATE_MODE;
			break;
		case EDIT_MODE:
			currentPin = currentUser.getPIN();
			currentPinCode = currentUser.getPinCode();
			currentPhoneNum = currentUser.getPhoneNum();
			currentBarcode = currentUser.getBicycle().getBarcode();
			currentName = currentUser.getName();
			pinTextField = new JTextField(currentPin);
			pinTextField.setPreferredSize(new Dimension(110, 30));
			pinCodeTextField = new JTextField(currentPinCode);
			pinCodeTextField.setPreferredSize(new Dimension(60, 30));
			phoneNumTextField = new JTextField(currentPhoneNum);
			phoneNumTextField.setPreferredSize(new Dimension(110, 30));
			bicycleTextField = new JTextField(currentBarcode);
			bicycleTextField.setPreferredSize(new Dimension(60, 30));
			nameTextField = new JTextField(currentName);
			nameTextField.setPreferredSize(new Dimension(150, 30));
			pinTextField.setEditable(false);
			pinCodeTextField.setEditable(false);
			bicycleTextField.setEditable(false);
			startPanel.add(pinTextField);
			startPanel.add(pinCodeTextField);
			startPanel.add(phoneNumTextField);
			startPanel.add(bicycleTextField);
			startPanel.add(nameTextField);
			startPanel.add(new SaveButton(this));
			startPanel.add(new CancelButton(this));
			startPanel.add(new DeleteUserButton(this));
			currentMode = EDIT_MODE;
			break;
		case SEARCH_MODE:
			searchResultPanel = new JPanel();
			setContentPane(searchResultPanel);
			searchResultPanel.setLayout(null);

			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(32, 32, 500, 400);
			searchResultPanel.add(scrollPane);

			panel = new JPanel();
			scrollPane.setViewportView(panel);
			panel.setLayout(new GridLayout(100, 1));

			TreeSet<User> result = bgm.searchUsers(searchTextField.getText());
			if (result.size() != 0) {
				int n = 0;
				for (User user : result) {
					JPanel panel_1 = new JPanel();
					JTextField jtf = new JTextField(user.getName());
					jtf.setEditable(false);
					panel_1.add(jtf);
					panel_1.add(new ViewUserButton(this, user));
					if (n % 2 == 0) {
						panel_1.setBackground(SystemColor.inactiveCaptionBorder);
					}
					panel.add(panel_1);
					n++;
				}
			} else {
				showMessageDialog("No user found");
				changeView(DEFAULT_MODE);
			}
			searchResultPanel.add(new CancelButton(this));
			currentMode = SEARCH_MODE;
			break;
		case VIEW_MODE:
			currentPin = currentUser.getPIN();
			currentPinCode = currentUser.getPinCode();
			currentPhoneNum = currentUser.getPhoneNum();
			currentBarcode = currentUser.getBicycle().getBarcode();
			currentName = currentUser.getName();
			pinTextField = new JTextField(currentPin);
			pinCodeTextField = new JTextField(currentPinCode);
			phoneNumTextField = new JTextField(currentPhoneNum);
			bicycleTextField = new JTextField(currentBarcode);
			nameTextField = new JTextField(currentName);
			pinTextField.setEditable(false);
			pinCodeTextField.setEditable(false);
			bicycleTextField.setEditable(false);
			phoneNumTextField.setEditable(false);
			nameTextField.setEditable(false);
			startPanel.add(pinTextField);
			startPanel.add(pinCodeTextField);
			startPanel.add(phoneNumTextField);
			startPanel.add(bicycleTextField);
			startPanel.add(nameTextField);
			startPanel.add(new SaveButton(this));
			startPanel.add(new CancelButton(this));
			startPanel.add(new DeleteUserButton(this));
			startPanel.add(new PrintBarcodeButton(this));
			currentMode = VIEW_MODE;
			break;
		default:
			bikeArea = new JTextArea(20, 20);
			bikeArea.setEditable(false);
			startPanel.add(bikeArea);
			startPanel.add(new JScrollPane(bikeArea));

			userCount = new JLabel("Registered users: " + bgm.getUserCount());
			startPanel.add(userCount);

			bikeArea.append("Bikes currently in garage:" + "\n");
			TreeSet<Bicycle> bikes = bgm.bicyclesInGarage();
			for (Bicycle b : bikes) {
				bikeArea.append(b.getBarcode() + "\n");
			}

			currentMode = DEFAULT_MODE;
			break;
>>>>>>> 34b7eb0a39ad59cf1a53ebce9417c22a8371b60c
		}
		add(bp, BorderLayout.PAGE_END);
		add(startPanel, BorderLayout.CENTER);
		pack();
		setVisible(true);
	}

	/**
	 * Sets the current user to user so that we can view them in edit- and view-mode.
	 * @param user The user to be set as currentUser.
	 */
	public void setCurrentUser(User user) {
		currentUser = user;
	}

	/**
	 * Prints a barcode.
	 * @param bicycleID The barcode to print.
	 */
	public void printBarcode(String bicycleID) {
		bgm.printBarcode(bicycleID);
	}

	/**
	 * Returns an int representing the current mode of the GUI.
	 * @return An int representing the current mode of the GUI.
	 */
	public int getCurrentMode() {
		return currentMode;
	}

	/**
	 * Adds a new User to the system.
	 * @param pin The PIN of the new user.
	 * @param pinCode The PIN-Code of the new user.
	 * @param bicycle The Bicycle of the new user.
	 * @param name The name of the new user.
	 * @param phoneNum The telephone number of the new user.
	 *
	 */
	public void saveUser(String pin, String pinCode, SYS.Bicycle bicycle, String name, String phoneNum) {
		bgm.addNewUser(pin, pinCode, bicycle, name, phoneNum);
		boolean b = bgm.addNewUser(pin, pinCode, bicycle, name, phoneNum);
		if (b) {
			userCount.setText("Registered users: " + bgm.getUserCount());
			changeView(OperatorGUI.DEFAULT_MODE);
		}
	}

	/**
	 * Deletes the User u from the system.
	 * 
	 * @param u
	 *            The User to delete.
	 * @return True if the User was successfully deleted.
	 */
	public boolean deleteUser(User u) {
		return bgm.deleteUser(u);
	}

	/**
	 * Returns the User that has PIN pin, null if there is no User with that PIN.
	 * @param pin The PIN to check for.
	 * @return The User with that PIN, if it exists.
	 */
	public User getUser(String pin) {
		User u = bgm.getUser(pin);
		if (u != null) {
			return u;
		} else {
			showErrorDialog("User not found.");
			return null;
		}
	}
	/**
	 * Shows a Message Dialog with the specified message as an information message.
	 * @param msg The informational message to display.
	 */
	public void showMessageDialog(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Message",
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Shows a Message Dialog with the specified message as an error message.
	 * @param msg The error message to display.
	 */
	public void showErrorDialog(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Saves info to the storage file on the form 'pin pinCode, barcode, name,
	 * phoneNum', newline represents a new object.
	 * 
	 * @param f
	 *            The file to save to.
	 */
	public void saveGarage(File f) {
		bgm.saveGarage(f);
	}

	/**
	 * Reads info from the storage file on the form 'pin pinCode barcode name
	 * phoneNum', newline represents a new object.
	 * 
	 * @param f
	 *            The file to open.
	 */
	public void openGarage(File f) {
		bgm.openGarage(f);
	}

	public void editUser() {
		boolean b = bgm.editUser(nameTextField.getText(), phoneNumTextField.getText(),
				    currentUser);
		if (b) {
			changeView(OperatorGUI.DEFAULT_MODE);
		}
	}

	/**
	 * Saves info to the storage file on the form 'pin pinCode, barcode, name, phoneNum', newline represents a new object.
	 * @param f The file to save to.
	 */
	public void saveGarage(File f) {
		bgm.saveGarage(f);
	}

	/**
     * Reads info from the storage file on the form 'pin pinCode barcode name phoneNum', newline represents a new object.
     * @param f The file to open.
     */
	public void openGarage(File f) {
		bgm.openGarage(f);
	}

	public void editUser() {
		bgm.editUser(nameTextField.getText(), phoneNumTextField.getText(), currentUser);
		
	}
}
