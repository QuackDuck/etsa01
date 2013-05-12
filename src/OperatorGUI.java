package GUI;

import javax.swing.*;
import java.util.List;
import java.util.Set;
import java.util.Locale;
import java.util.TreeSet;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.border.EmptyBorder;

import java.awt.Component;

import SYS.*;

public class OperatorGUI extends JFrame {
	private SYS.BicycleGarageManager bgm;
	private JTextArea bikeArea;
	private JTextArea bikerArea;

	private JPanel startPanel;
	private JPanel bp;
	private JPanel searchResultPanel;
	private JPanel panel;

	public JTextField pinTextField;
	public JTextField pinCodeTextField;
	public JTextField phoneNumTextField;
	public JTextField bicycleTextField;
	public JTextField nameTextField;
	public JTextField SEARCH_TEXT_FIELD;

	private String currentPin;
	private String currentPinCode;
	private String currentPhoneNum;
	private String currentName;
	private String currentBarcode;

	private User currentBiker;

	public static final int DEFAULT_MODE = 0;
	public static final int CREATE_MODE = 1;
	public static final int EDIT_MODE = 2;
	public static final int SEARCH_MODE = 3;
	public static final int VIEW_MODE = 4;

	public OperatorGUI(SYS.BicycleGarageManager bgm) {
		super("Operator Interface");
		
		this.bgm = bgm;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Locale.setDefault(new Locale("en"));
		setPreferredSize(new Dimension(600, 600));
		/* To avoid hardcoded Swedish text on OptionPane dialogs */
		UIManager.put("OptionPane.cancelButtonText","Cancel");
		
		/*
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
		JMenu viewMenu = new JMenu("View");
		menubar.add(viewMenu);
		viewMenu.add(new ViewAllMenu(this));
	
		/*
		 * Create and populate the ButtonPanel.
		 */
		bp = new JPanel();
		bp.add(new NewBikerButton(this));
		bp.add(new EditBikerButton(this));
		SEARCH_TEXT_FIELD = new SearchForm(this);
		bp.add(SEARCH_TEXT_FIELD);
		bp.add(new SearchButton(this));

		/*
		 * Create and populate the StartPanel (default view).
		 */
		startPanel = new JPanel();
		changeView(DEFAULT_MODE);
	}

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
				break;
			case EDIT_MODE:
				currentPin = currentBiker.getPIN();
				currentPinCode = currentBiker.getPinCode();
				currentPhoneNum = currentBiker.getPhoneNum();
		        currentBarcode = currentBiker.getBicycle().getBarcode();
		        currentName = currentBiker.getName();
		        pinTextField = new JTextField(currentPin);
				pinCodeTextField = new JTextField(currentPinCode);
		        phoneNumTextField = new JTextField(currentPhoneNum);
		        bicycleTextField = new JTextField(currentBarcode);
		        nameTextField = new JTextField(currentName);
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
    		    startPanel.add(new DeleteBikerButton(this));
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

				TreeSet<User> result = bgm.searchUsers(SEARCH_TEXT_FIELD.getText());
                if (result.size() != 0) {
                    int n = 0;
                    for (User user : result) {
                        JPanel panel_1 = new JPanel();
                        JTextField jtf = new JTextField(user.getName());
                        jtf.setEditable(false);
                        panel_1.add(jtf);
                        panel_1.add(new ViewBikerButton(this, user));
                        if(n % 2 == 0) {
                            panel_1.setBackground(SystemColor.inactiveCaptionBorder);
                        }
                        panel.add(panel_1);
                        n++;
                    }
                } else {
                    showMessageDialog("No user found");
                }
				searchResultPanel.add(new CancelButton(this));
				break;
			case VIEW_MODE:
				currentPin = currentBiker.getPIN();
				currentPinCode = currentBiker.getPinCode();
				currentPhoneNum = currentBiker.getPhoneNum();
				currentBarcode = currentBiker.getBicycle().getBarcode();
				currentName = currentBiker.getName();
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
				startPanel.add(new DeleteBikerButton(this));
				startPanel.add(new PrintBarcodeButton(this));
				break;
			default:
				bikerArea = new JTextArea(20,20);
				bikerArea.setEditable(false);
				startPanel.add(bikerArea);
				startPanel.add(new JScrollPane(bikerArea));

				bikeArea = new JTextArea(20,20);
				bikeArea.setEditable(false);
				startPanel.add(bikeArea);
				startPanel.add(new JScrollPane(bikeArea));

				bikerArea.append("Bikers currently in garage\n");
				for (int i = 0; i < 10; i++) {
					bikerArea.append("Sven Svensson");
					bikerArea.append("\n");
				}
				
				bikeArea.append("Bikes currently in garage:\n");
				for (int i = 0; i < 50; i++) {
					bikeArea.append("91932");
					bikeArea.append("\n");
				}
				break;
		}
		add(bp, BorderLayout.PAGE_END);
		add(startPanel, BorderLayout.CENTER);
		pack();
		setVisible(true);
	}

	public void setCurrentBiker(User biker) {
		currentBiker = biker;
	}

	public void printBarcode(String bicycleID) {
		bgm.printBarcode(bicycleID);
	}

	public void saveUser(String pin, String pinCode, SYS.Bicycle bicycle, String name, String phoneNum) {
		bgm.addNewUser(pin, pinCode, bicycle, name, phoneNum);
	}

	public User getUser(String pin) {
		User u = bgm.getUser(pin);
		if (u != null) {
			return u;
		}
		else {
			showErrorDialog("User not found.");
			return null;
		}
	}

	public void showMessageDialog(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Message", JOptionPane.INFORMATION_MESSAGE);
	}

	public void showErrorDialog(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
