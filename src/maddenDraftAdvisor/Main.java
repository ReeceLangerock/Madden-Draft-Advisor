package maddenDraftAdvisor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Main {
	static boolean draftBoardLoaded = false;
	private JFileChooser fc = new JFileChooser();
	private static File file;
	private String[] lastDB = new String[2];
	private static ArrayList<Prospects> prospectList = new ArrayList<Prospects>();

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		GUISetup guiSetup = new GUISetup();
		guiSetup.createAndShowGUI();

	}

	public ArrayList<Prospects> getProspectList() {
		return prospectList;
	}

	public String getDraftBoard() {
		return lastDB[0];
	}

	@SuppressWarnings("unchecked")
	public void loadLast() {
		try {
			FileInputStream fileStream = new FileInputStream("lastDB.ser");
			ObjectInputStream ois = new ObjectInputStream(fileStream);
			lastDB = (String[]) ois.readObject();
			ois.close();
			FileInputStream fileStream2 = new FileInputStream(lastDB[1]);
			ObjectInputStream ois2 = new ObjectInputStream(fileStream2);
			prospectList = (ArrayList<Prospects>) ois2.readObject();

			ois2.close();
			file = new File(lastDB[0]);
			draftBoardLoaded = true;

		} catch (IOException | ClassNotFoundException e) {
			lastDB[0] = "None";
			lastDB[1] = "None";

		}

	}

	@SuppressWarnings("unchecked")
	public void loadDraftBoard(JPanel cardPanel) throws ClassNotFoundException {
		int returnVal = fc.showOpenDialog(cardPanel);
		if (returnVal == JFileChooser.APPROVE_OPTION)
			;

		file = fc.getSelectedFile();
		try {
			prospectList.clear();
			FileInputStream fileStream = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fileStream);
			prospectList = (ArrayList<Prospects>) ois.readObject();
			ois.close();
			draftBoardLoaded = true;
			lastDB[0] = file.getName();
			lastDB[1] = file.getAbsolutePath();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Incorrect filetype. Draft Board did not load.", "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

	public void createDraftBoard(JPanel cardPanel) {
		fc.setSelectedFile(new File("leagueName.ser"));
		// fc.setFileFilter(new FileNameExtensionFilter("ser file", "ser"));
		if (fc.showSaveDialog(cardPanel) == JFileChooser.APPROVE_OPTION) {

			file = fc.getSelectedFile();
			try {
				prospectList.clear();
				FileOutputStream fileStream = new FileOutputStream(file);
				ObjectOutputStream oos = new ObjectOutputStream(fileStream);
				oos.writeObject(prospectList);
				oos.close();
				draftBoardLoaded = true;
				lastDB[0] = file.getName();
				lastDB[1] = file.getAbsolutePath();
				FileOutputStream fileStream2 = new FileOutputStream("lastDB.ser");
				ObjectOutputStream oos2 = new ObjectOutputStream(fileStream2);
				oos2.writeObject(lastDB);
				oos2.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void exportDraftBoard() {

	}

	public void updateProspectList(ArrayList<Prospects> p) {
		prospectList = p;

	}

	public boolean save() throws IOException {
		if (prospectList.size() < 1) {
			return false;

		} else {

			FileOutputStream fileStream = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fileStream);
			oos.writeObject(prospectList);
			oos.close();
			FileOutputStream fileStream2 = new FileOutputStream("lastDB.ser");
			ObjectOutputStream oos2 = new ObjectOutputStream(fileStream2);
			lastDB[0] = file.getName();
			lastDB[1] = file.getAbsolutePath();
			oos2.writeObject(lastDB);
			oos2.close();
		}
		return true;

	}
}
