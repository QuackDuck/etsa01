package SYS;

import java.util.TreeSet;

public class BicycleGarageManager {
	private BarcodePrinterTestDriver printer;
	private BarcodeReaderTestDriver barcodeReader;
	private BarcodeReaderEntryTestDriver entryBarcodeReader;
	private BarcodeReaderExitTestDriver exitBarcodeReader;
	private ElectronicLockTestDriver entryLock;
	private ElectronicLockTestDriver exitLock;
	private PinCodeTerminalTestDriver terminal;
	private GUI.OperatorGUI gui;
	private TreeSet<User> users;
	private TreeSet<Bicycle> bikes;
	
	/**
	 * Creates a new BicycleGarageManager
	 *
	 */
	public BicycleGarageManager(){
		users = new TreeSet<User>();
		bikes = new TreeSet<Bicycle>();
		gui = new GUI.OperatorGUI(this);
		registerHardwareDrivers(new BarcodePrinterTestDriver(),
                                new BarcodeReaderExitTestDriver(),
								new BarcodeReaderEntryTestDriver(),
                                new ElectronicLockTestDriver("Entry"),
                                new ElectronicLockTestDriver("Exit"),
                                new PinCodeTerminalTestDriver());
		bikes.add(new Bicycle("12345"));
	}

	/**
	 * Registers the hardware drivers for the system.
	 * @param printer
	 * @param entryLock
	 * @param exitLock
	 * @param terminal
	 *
	 */
	public void registerHardwareDrivers(BarcodePrinterTestDriver printer,
										BarcodeReaderExitTestDriver exitBarcodeReader,
										BarcodeReaderEntryTestDriver entryBarcodeReader,
										ElectronicLockTestDriver entryLock,
										ElectronicLockTestDriver exitLock,
										PinCodeTerminalTestDriver terminal) {
		barcodeReader.register(this);
		this.printer = printer;
		this.entryBarcodeReader = entryBarcodeReader;
		this.exitBarcodeReader = exitBarcodeReader;
		this.entryLock = entryLock;
		this.exitLock = exitLock;
		this.terminal = terminal;	
	}

	/**
	 * Whenever a barcode is read at the entrance.
	 * 
	 */
	public void entryBarcode(String bicycleID) {
		for (Bicycle b : bikes) {
			if (b.getBarcode().equals(bicycleID)) {
				entryLock.open(15);
				if (!b.inGarage()) {
					b.setInGarage(true);
				}
				/** Intervall! */
				terminal.lightLED(terminal.GREEN_LED, 15);
			}
		}
		/** Intervall! */
		terminal.lightLED(terminal.RED_LED, 15);
	}

	/**
	 * Whenever a barcode is read at the exit.
	 *
	 */
	public void exitBarcode(String bicycleID) {
		for (Bicycle b : bikes) {
			if (b.getBarcode().equals(bicycleID)) {
				entryLock.open(15);
				if (b.inGarage()) {
					b.setInGarage(false);
				}
				/** Intervall! */
				terminal.lightLED(terminal.GREEN_LED, 15);
			}
		}
		/** Intervall! */
		terminal.lightLED(terminal.RED_LED, 15);
	}

	/**
	 * Reads the characters entered at the PIN-code terminal.
	 *
	 */
	public void entryCharacter(char c) {

	}

	public Bicycle getBicycle(String barcode) {
		return null;
	}

	public User getUser(String pin) {
		return null;
	}

	public void addNewUser(String pin, String pinCode, Bicycle bicycle, String name, String phoneNum) {
		if (users.size() <= 10000) {
			if (users.add(new User(pin, pinCode, bicycle, name, phoneNum))) {
				gui.showMessageDialog("User was successfully added.");
			}
			else {
				gui.showErrorDialog("The Personal Identity Number is entered on an incorrect form and/or is already registered to another biker.");
			}
		}
		else {
			gui.showErrorDialog("The system biker limit has been reached.");
		}
	}

	public TreeSet<User> searchUsers(String searchString) {
		return null;
	}

	public void printBarcode(String barcode) {
		printer.printBarcode(barcode);		
	}
	
}
