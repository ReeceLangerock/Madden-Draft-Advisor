package maddenDraftAdvisor;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class GUISetup {

	JLabel boardLoaded = new JLabel();
	// Buttons
	JButton viewProspectsButton, addProspectsButton, editProspectsButton, deleteProspectsButton, homeButton,
			addSaveButton, addResetButton, loadButton, sqButton, createButton, printButton, deleteButton;
	// Comboboxs for create prospect
	JComboBox<String> position, firstAttBox, secondAttBox, thirdAttBox, firstGradeBox, secondGradeBox, thirdGradeBox;
	// table models for each view panel
	DefaultTableModel deleteModel = new DefaultTableModel();
	DefaultTableModel editModel = new DefaultTableModel();
	DefaultTableModel viewModel = new DefaultTableModel();
	// Add ProspectFields
	JTextField firstNameField, lastNameField, positionField, fortyField, threeConeField, shuttleField, benchField,
			draftRoundField, draftPickField, vertField, toDeleteField, att1Field, att2Field, att3Field;
	// GUI setup stuff
	JPanel cardPanel, align_0, align_1, align_2, align_3, align_4;
	JPanel bottomPanel;
	JLabel titleLabel, descLabel;
	JFrame frame = new JFrame();

	Main m = new Main();
	DraftBoard draftboard = new DraftBoard();
	HelperFunctions hf = new HelperFunctions();

	// create frame and run method to set up GUI
	public void createAndShowGUI() {

		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setTitle("Madden Draft Advisor");

		GUISetup demo = new GUISetup();
		frame.setContentPane(demo.start());

		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				String[] options = { "Save", "Don't Save", "Cancel" };
				int response = JOptionPane.showOptionDialog(frame, "Do you want to save the draftboard", "Exit?",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if (response == JOptionPane.YES_OPTION) {
					try {
						System.out.println("Save and quit");
						boolean saved = m.save();
						if (saved && m.getDraftBoard() != "none") {
							System.exit(0);
						} else {
							JOptionPane.showMessageDialog(cardPanel, "Cannot save an empty Draft Board");
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (response == JOptionPane.NO_OPTION) {
					System.exit(0);
				} else if (response == JOptionPane.CANCEL_OPTION) {

				}

			}
		});
	}

	public JPanel start() {

		// try to load last used draftboard
		m.loadLast();

		// guiSetup code
		JPanel totalGUI = new JPanel();
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));

		buttonPanel.add(Box.createHorizontalGlue());
		homeButton = new JButton("Home");
		homeButton.addActionListener(new buttonListener());
		buttonPanel.add(homeButton);
		buttonPanel.add(Box.createHorizontalGlue());

		viewProspectsButton = new JButton("View Prospects");
		viewProspectsButton.addActionListener(new buttonListener());
		buttonPanel.add(viewProspectsButton);
		buttonPanel.add(Box.createHorizontalGlue());

		addProspectsButton = new JButton("Add Prospect");
		addProspectsButton.addActionListener(new buttonListener());
		buttonPanel.add(addProspectsButton);
		buttonPanel.add(Box.createHorizontalGlue());

		editProspectsButton = new JButton("Edit Prospects");
		editProspectsButton.addActionListener(new buttonListener());
		buttonPanel.add(editProspectsButton);
		buttonPanel.add(Box.createHorizontalGlue());

		deleteProspectsButton = new JButton("Delete Prospects");
		deleteProspectsButton.addActionListener(new buttonListener());
		buttonPanel.add(deleteProspectsButton);
		buttonPanel.add(Box.createHorizontalGlue());

		align_0 = new JPanel();
		align_0.setLayout(new BoxLayout(align_0, BoxLayout.LINE_AXIS));
		JPanel homePanel = createHomePanel();
		align_0.add(Box.createHorizontalGlue());
		align_0.add(homePanel);
		align_0.add(Box.createHorizontalGlue());
		align_0.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		align_1 = new JPanel();
		align_1.setLayout(new BoxLayout(align_1, BoxLayout.LINE_AXIS));
		JPanel displayPanel = createViewPanel();
		align_1.add(displayPanel);
		align_1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		align_2 = new JPanel();
		align_2.setLayout(new BoxLayout(align_2, BoxLayout.LINE_AXIS));
		JPanel addPanel = createAddPanel();
		align_2.add(addPanel);
		align_2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		align_3 = new JPanel();
		align_3.setLayout(new BoxLayout(align_3, BoxLayout.LINE_AXIS));
		JPanel editPanel = createEditPanel();
		align_3.add(editPanel);
		align_3.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		align_4 = new JPanel();
		align_4.setLayout(new BoxLayout(align_4, BoxLayout.LINE_AXIS));
		JPanel deletePanel = createDeletePanel();
		align_4.add(deletePanel);
		align_4.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		cardPanel = new JPanel(new CardLayout());
		cardPanel.add(align_0, "0");
		cardPanel.add(align_1, "1");
		cardPanel.add(align_2, "2");
		cardPanel.add(align_3, "3");
		cardPanel.add(align_4, "4");

		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(buttonPanel, BorderLayout.PAGE_START);
		bottomPanel.add(cardPanel, BorderLayout.CENTER);
		cardPanel.setBackground(Color.red);
		totalGUI.add(bottomPanel);
		totalGUI.setOpaque(true);

		return totalGUI;

	}

	public JPanel createHomePanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new FlowLayout());
		JPanel tempPanel = new JPanel();
		mainPanel.add(tempPanel);
		tempPanel.setOpaque(true);
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		tempPanel.setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10));
		tempPanel.setLayout(new GridLayout(3, 2, 25, 25));
		loadButton = new JButton("Load Draft Board");
		createButton = new JButton("Create New Draft Board");
		sqButton = new JButton("Save and Quit");
		printButton = new JButton("Export to Text File");
		tempPanel.add(createButton);
		tempPanel.add(loadButton);
		tempPanel.add(sqButton);
		tempPanel.add(printButton);
		createButton.addActionListener(new buttonListener());
		loadButton.addActionListener(new buttonListener());
		sqButton.addActionListener(new buttonListener());
		printButton.addActionListener(new buttonListener());
		boardLoaded.setText("Draft Board Loaded:  " + m.getDraftBoard());
		tempPanel.add(boardLoaded);

		return mainPanel;

	}

	public JPanel createViewPanel() {
		ArrayList<Prospects> prospectList = m.getProspectList();
		JPanel viewPanel = draftboard.createViewPanel();

		for (Prospects p : prospectList) {
			String name = p.getFirstName() + " " + p.getLastName();
			String fAtt = p.getFirstABV() + " - " + p.getFirstLetterGrade();
			String sAtt = p.getSecondABV() + " - " + p.getSecondLetterGrade();
			String tAtt = p.getThirdABV() + " - " + p.getThirdLetterGrade();
			Object[] data = { name, p.getPosition(), p.getDraftRound(), p.getDraftPick(), (byte) p.getOverall(),
					p.getSpeed(), p.getAcceleration(), p.getAgility(), p.getStrength(), p.getJumping(), fAtt, sAtt,
					tAtt };
			viewModel.addRow(data);
		}
		return viewPanel;

	}

	public JPanel createDeletePanel() {

		ArrayList<Prospects> prospectList = m.getProspectList();

		JPanel deletePanel = draftboard.createDeletePanel();

		for (Prospects p : prospectList) {
			String name = p.getFirstName() + " " + p.getLastName();
			Object[] data = { name, p.getPosition(), p.getFortyTime(), p.getThreeCone(), p.getShuttle(), p.getBench(),
					p.getVert() };
			deleteModel.addRow(data);
		}

		return deletePanel;
	}

	public JPanel createEditPanel() {

		ArrayList<Prospects> prospectList = m.getProspectList();

		// temp. using viewPanel method from DraftBaord class in editPanel
		// method to test
		JPanel editPanel = draftboard.createEditPanel();

		for (Prospects p : prospectList) {
			String name = p.getFirstName() + " " + p.getLastName();
			Object[] data = { name, p.getPosition(), p.getFortyTime(), p.getThreeCone(), p.getShuttle(), p.getBench(),
					p.getVert() };
			editModel.addRow(data);
		}

		return editPanel;
	}

	public JPanel createAddPanel() {
		ArrayList<Prospects> prospectList = m.getProspectList();
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		mainPanel.setOpaque(true);
		position = new JComboBox<String>(Prospects.positionChoices);
		firstAttBox = new JComboBox<String>(Prospects.attributeChoices);
		secondAttBox = new JComboBox<String>(Prospects.attributeChoices);
		thirdAttBox = new JComboBox<String>(Prospects.attributeChoices);
		firstGradeBox = new JComboBox<String>(Prospects.attributeGrade);
		secondGradeBox = new JComboBox<String>(Prospects.attributeGrade);
		thirdGradeBox = new JComboBox<String>(Prospects.attributeGrade);
		JPanel tempPanel = new JPanel();
		tempPanel.setOpaque(true);
		tempPanel.setLayout(new GridLayout(10, 4, 15, 5));

		tempPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mainPanel.add(tempPanel);
		JLabel requiredLabel = new JLabel("<HTML><U><B>Required</B></U></HTML>");
		JLabel optionalLabel = new JLabel("<HTML><U>Optional</U></HTML>");
		JLabel blank1Label = new JLabel("");
		JLabel blank2Label = new JLabel("");
		JLabel blank3Label = new JLabel("");
		JLabel blank4Label = new JLabel("");
		JLabel blank5Label = new JLabel("");
		JLabel blank6Label = new JLabel("");
		JLabel firstNameLabel = new JLabel("First Name");
		JLabel lastNameLabel = new JLabel("Last Name");
		JLabel positionLabel = new JLabel("Position");
		JLabel fortyLabel = new JLabel("40 Time");
		JLabel threeConeLabel = new JLabel("Three Cone Time");
		JLabel shuttleLabel = new JLabel("20 Yard Shuttle");
		JLabel benchLabel = new JLabel("Bench");
		JLabel vertLabel = new JLabel("Vertical Jump");
		JLabel draftPickLabel = new JLabel("Age");
		JLabel draftRoundLabel = new JLabel("Projected Pick");
		JLabel draftAtt1 = new JLabel("First Attribute");
		JLabel draftAtt2 = new JLabel("Second Attribute");
		JLabel draftAtt3 = new JLabel("Third Attribute");
		JLabel draftGrade1 = new JLabel("Grade");
		JLabel draftGrade2 = new JLabel("Grade");
		JLabel draftGrade3 = new JLabel("Grade");

		firstNameField = new JTextField();
		firstNameField.requestFocus();
		lastNameField = new JTextField();
		positionField = new JTextField();
		fortyField = new JTextField();
		fortyField.setToolTipText("Enter forty time between 4.2 and 6.0");
		threeConeField = new JTextField();
		threeConeField.setToolTipText("Enter three cone time between 6.4 and 8.5");
		shuttleField = new JTextField();
		shuttleField.setToolTipText("Enter shuttle time between 3.8 and 5.5");
		benchField = new JTextField();
		benchField.setToolTipText("Enter bench reps between 1 and 42");
		vertField = new JTextField();
		vertField.setToolTipText("Enter vertical height between 1 and 43.8");
		draftPickField = new JTextField();
		draftPickField.setToolTipText("Enter draft round between 1 and 7");
		draftRoundField = new JTextField();
		draftRoundField.setToolTipText("Enter age between 19 and 26");

		att1Field = new JTextField();
		att2Field = new JTextField();
		att3Field = new JTextField();

		// first row
		tempPanel.add(requiredLabel);
		tempPanel.add(blank1Label);
		tempPanel.add(blank2Label);
		tempPanel.add(blank3Label);

		// second row
		tempPanel.add(firstNameLabel);
		tempPanel.add(firstNameField);
		firstNameField.requestFocusInWindow();
		tempPanel.add(lastNameLabel);
		tempPanel.add(lastNameField);

		// third row
		tempPanel.add(positionLabel);
		tempPanel.add(position);
		tempPanel.add(fortyLabel);
		tempPanel.add(fortyField);

		// fourth row
		tempPanel.add(threeConeLabel);
		tempPanel.add(threeConeField);
		tempPanel.add(shuttleLabel);
		tempPanel.add(shuttleField);

		// fifth row
		tempPanel.add(benchLabel);
		tempPanel.add(benchField);
		tempPanel.add(vertLabel);
		tempPanel.add(vertField);

		// sixth row
		tempPanel.add(optionalLabel);
		tempPanel.add(blank4Label);
		tempPanel.add(blank5Label);
		tempPanel.add(blank6Label);

		// seventh row
		tempPanel.add(draftRoundLabel);
		tempPanel.add(draftRoundField);
		draftRoundField.setText("Optional");
		tempPanel.add(draftPickLabel);
		tempPanel.add(draftPickField);
		draftPickField.setText("Optional");

		// Eight row
		tempPanel.add(draftAtt1);
		tempPanel.add(firstAttBox);
		tempPanel.add(draftGrade1);
		tempPanel.add(firstGradeBox);

		// Ninth Row
		tempPanel.add(draftAtt2);
		tempPanel.add(secondAttBox);
		tempPanel.add(draftGrade2);
		tempPanel.add(secondGradeBox);

		// Tenth Row
		tempPanel.add(draftAtt3);
		tempPanel.add(thirdAttBox);
		tempPanel.add(draftGrade3);
		tempPanel.add(thirdGradeBox);

		JPanel buttonPanel = new JPanel();

		mainPanel.add(buttonPanel);
		addSaveButton = new JButton("Add Prospect");
		addResetButton = new JButton("Reset");
		addSaveButton.addActionListener(new buttonListener());
		addResetButton.addActionListener(new buttonListener());

		buttonPanel.add(addSaveButton, BorderLayout.WEST);
		buttonPanel.add(Box.createRigidArea(new Dimension(40, 0)), BorderLayout.CENTER);
		buttonPanel.add(addResetButton, BorderLayout.EAST);

		return mainPanel;
	}

	class buttonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			CardLayout c1 = (CardLayout) (cardPanel.getLayout());
			if (e.getSource() == viewProspectsButton && Main.draftBoardLoaded == true) {
				c1.show(cardPanel, "1");
				updateViewTable();
			} else if (e.getSource() == addProspectsButton && Main.draftBoardLoaded == true) {
				c1.show(cardPanel, "2");
				firstNameField.requestFocusInWindow();
			} else if (e.getSource() == editProspectsButton && Main.draftBoardLoaded == true) {
				c1.show(cardPanel, "3");
				updateEditTable();
			} else if (e.getSource() == deleteProspectsButton && Main.draftBoardLoaded == true) {
				c1.show(cardPanel, "4");
				updateDeleteTable();
			} else if (e.getSource() == homeButton) {
				c1.show(cardPanel, "0");
			} else if (e.getSource() == loadButton) {
				try {
					m.loadDraftBoard(cardPanel);
					updateViewTable();
					boardLoaded.setText("Draft Board Loaded:  " + m.getDraftBoard());
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(cardPanel,
							"Draft Board did not load correctly. \nFile may be corrupted.");
				}
			} else if (e.getSource() == createButton) {
				m.createDraftBoard(cardPanel);
				boardLoaded.setText("Draft Board Loaded:  " + m.getDraftBoard());
			} else if (e.getSource() == printButton) {
			} else if (e.getSource() == sqButton) {

				try {
					boolean saved = m.save();
					if (saved) {
						System.exit(0);
					} else {
						JOptionPane.showMessageDialog(cardPanel, "Cannot save an empty Draft Board");
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			} else if (e.getSource() == addSaveButton) {
				prospectEntryCheck();

			} else if (e.getSource() == addResetButton) {
				firstNameField.setText("");
				lastNameField.setText("");
				positionField.setText("");
				fortyField.setText("");
				threeConeField.setText("");
				shuttleField.setText("");
				benchField.setText("");
				vertField.setText("");
				draftRoundField.setText("");
				draftPickField.setText("");

			}
		}
	}

	public void deleteProspect(int index) {
		ArrayList<Prospects> p = m.getProspectList();
		p.remove(index);

	}

	public void prospectEntryCheck() {
		ArrayList<String> badEntries = new ArrayList<String>();
		String entryToFix = "";
		System.out.println(benchField.getText().equals("0"));
		System.out.println(benchField.getText().equals(""));
		//draftRoundField.getText().equals("")|| draftRoundField.getText().equals("Optional") &&
		if (!fortyField.getText().isEmpty() && !threeConeField.getText().equals("") && shuttleField.getText().equals("")
						&& !benchField.getText().equals("") && !vertField.getText().equals("")) {
			badEntries = hf.validateEntry(fortyField.getText(), threeConeField.getText(), shuttleField.getText(),
					benchField.getText(), vertField.getText(), firstNameField.getText(), lastNameField.getText(),
					firstAttBox.getSelectedItem().toString(), secondAttBox.getSelectedItem().toString(),
					thirdAttBox.getSelectedItem().toString());
			int goodToGo = -1;
			goodToGo = badEntries.size();
			if (goodToGo > 0) {
				for (String b : badEntries) {
					entryToFix += b + ", ";
				}
				entryToFix = entryToFix.substring(0, entryToFix.length() - 2);
				JOptionPane.showMessageDialog(cardPanel, "Correct the following information:\n" + entryToFix,
						"Invalid Entry", JOptionPane.ERROR_MESSAGE, null);
			} else if (goodToGo == 0) {
				ArrayList<Prospects> p = m.getProspectList();
				float ft = hf.checkFloatInput(fortyField.getText());
				float tct = hf.checkFloatInput(threeConeField.getText());
				float st = hf.checkFloatInput(shuttleField.getText());
				byte b = (byte) hf.checkIntInput(benchField.getText());
				float v = hf.checkFloatInput(vertField.getText());
				String pos = position.getSelectedItem().toString();
				Prospects temp = new Prospects(ft, tct, st, b, v, firstNameField.getText(), lastNameField.getText(),
						pos, firstAttBox.getSelectedItem().toString(), firstGradeBox.getSelectedItem().toString(),
						secondAttBox.getSelectedItem().toString(), secondGradeBox.getSelectedItem().toString(),
						thirdAttBox.getSelectedItem().toString(), thirdGradeBox.getSelectedItem().toString());
				p.add(temp);
				m.updateProspectList(p);
				try {
					m.save();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
				updateViewTable();
				JOptionPane.showMessageDialog(cardPanel,
						firstNameField.getText() + " " + lastNameField.getText() + " added to draft board");
				firstNameField.setText("");
				lastNameField.setText("");
				positionField.setText("");
				fortyField.setText("");
				threeConeField.setText("");
				shuttleField.setText("");
				benchField.setText("");
				vertField.setText("");
				draftRoundField.setText("");
				draftPickField.setText("");
				firstAttBox.setSelectedIndex(0);
				secondAttBox.setSelectedIndex(0);
				thirdAttBox.setSelectedIndex(0);
				goodToGo = -1;
			}

		} else if (fortyField.getText().equals("") || threeConeField.getText().equals("")
				|| shuttleField.getText().equals("") || benchField.getText().equals("")
				|| vertField.getText().equals("")) {
			badEntries = hf.validateEntry(firstNameField.getText(), lastNameField.getText(),
					firstAttBox.getSelectedItem().toString(), secondAttBox.getSelectedItem().toString(),
					thirdAttBox.getSelectedItem().toString());
			int goodToGo = -1;
			goodToGo = badEntries.size();
			if (goodToGo > 0) {
				for (String b : badEntries) {
					entryToFix += b + ", ";
				}
				entryToFix = entryToFix.substring(0, entryToFix.length() - 2);
				JOptionPane.showMessageDialog(cardPanel, "Correct the following information:\n" + entryToFix,
						"Invalid Entry", JOptionPane.ERROR_MESSAGE, null);
			} else if (goodToGo == 0) {
				ArrayList<Prospects> p = m.getProspectList();
				float ft = 0;
				float tct = 0;
				float st = 0;
				byte b = 0;
				float v = 0;
				String pos = position.getSelectedItem().toString();
				Prospects temp = new Prospects(ft, tct, st, b, v, firstNameField.getText(), lastNameField.getText(),
						pos, firstAttBox.getSelectedItem().toString(), firstGradeBox.getSelectedItem().toString(),
						secondAttBox.getSelectedItem().toString(), secondGradeBox.getSelectedItem().toString(),
						thirdAttBox.getSelectedItem().toString(), thirdGradeBox.getSelectedItem().toString());
				p.add(temp);
				m.updateProspectList(p);
				try {
					m.save();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
				updateViewTable();
				JOptionPane.showMessageDialog(cardPanel,
						firstNameField.getText() + " " + lastNameField.getText() + " added to draft board");
				firstNameField.setText("");
				lastNameField.setText("");
				positionField.setText("");
				fortyField.setText("");
				threeConeField.setText("");
				shuttleField.setText("");
				benchField.setText("");
				vertField.setText("");
				draftRoundField.setText("");
				draftPickField.setText("");
				firstAttBox.setSelectedIndex(0);
				secondAttBox.setSelectedIndex(0);
				thirdAttBox.setSelectedIndex(0);
				goodToGo = -1;
			}
		}

		else {
			badEntries = hf.validateEntry(fortyField.getText(), threeConeField.getText(), shuttleField.getText(),
					benchField.getText(), vertField.getText(), firstNameField.getText(), lastNameField.getText(),
					draftRoundField.getText(), draftPickField.getText(), firstAttBox.getSelectedItem().toString(),
					secondAttBox.getSelectedItem().toString(), thirdAttBox.getSelectedItem().toString());
			int goodToGo = -1;
			goodToGo = badEntries.size();
			if (goodToGo > 0) {
				for (String b : badEntries) {
					entryToFix += b + ", ";
				}
				entryToFix = entryToFix.substring(0, entryToFix.length() - 2);
				JOptionPane.showMessageDialog(cardPanel, "Correct the following information:\n" + entryToFix,
						"Invalid Entry", JOptionPane.ERROR_MESSAGE, null);
			} else if (goodToGo == 0) {
				ArrayList<Prospects> p = m.getProspectList();
				float ft = hf.checkFloatInput(fortyField.getText());
				float tct = hf.checkFloatInput(threeConeField.getText());
				float st = hf.checkFloatInput(shuttleField.getText());
				byte b = (byte) hf.checkIntInput(benchField.getText());
				float v = hf.checkFloatInput(vertField.getText());
				byte dr = (byte) hf.checkIntInput(draftRoundField.getText());
				byte dp = (byte) hf.checkIntInput(draftPickField.getText());

				String pos = position.getSelectedItem().toString();
				Prospects temp = new Prospects(ft, tct, st, b, v, firstNameField.getText(), lastNameField.getText(),
						pos, dr, dp, firstAttBox.getSelectedItem().toString(),
						firstGradeBox.getSelectedItem().toString(), secondAttBox.getSelectedItem().toString(),
						secondGradeBox.getSelectedItem().toString(), thirdAttBox.getSelectedItem().toString(),
						thirdGradeBox.getSelectedItem().toString());
				p.add(temp);
				m.updateProspectList(p);
				try {
					m.save();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
				updateViewTable();
				JOptionPane.showMessageDialog(cardPanel,
						firstNameField.getText() + " " + lastNameField.getText() + " added to draft board");
				firstNameField.setText("");
				lastNameField.setText("");
				positionField.setText("");
				fortyField.setText("");
				threeConeField.setText("");
				shuttleField.setText("");
				benchField.setText("");
				vertField.setText("");
				draftRoundField.setText("");
				draftPickField.setText("");
				firstAttBox.setSelectedIndex(0);
				secondAttBox.setSelectedIndex(0);
				thirdAttBox.setSelectedIndex(0);
				goodToGo = -1;

			}
		}
	}

	public void updateDeleteTable() {
		ArrayList<Prospects> prospectList = m.getProspectList();
		DefaultTableModel model = draftboard.getDeleteModel();
		model.setRowCount(0);

		for (Prospects p : prospectList) {
			String name = p.getFirstName() + " " + p.getLastName();
			if (p.getDraftRound() == -1) {
				String dr = "?";
				String dp = "?";
				Object[] data = { name, p.getPosition(), dr, dp, p.getFortyTime(), p.getThreeCone(), p.getShuttle(),
						p.getBench(), p.getVert() };
				model.addRow(data);
			} else {
				Object[] data = { name, p.getPosition(), p.getDraftRound(), p.getDraftPick(), p.getFortyTime(),
						p.getThreeCone(), p.getShuttle(), p.getBench(), p.getVert() };
				model.addRow(data);
			}
		}
	}

	public void updateEditTable() {
		ArrayList<Prospects> prospectList = m.getProspectList();
		DefaultTableModel model = draftboard.getEditModel();
		model.setRowCount(0);

		for (Prospects p : prospectList) {
			String name = p.getFirstName() + " " + p.getLastName();
			String fAtt = p.getFirstABV() + " - " + p.getFirstLetterGrade();
			String sAtt = p.getSecondABV() + " - " + p.getSecondLetterGrade();
			String tAtt = p.getThirdABV() + " - " + p.getThirdLetterGrade();
			if (p.getDraftRound() == -1) {
				String dr = "?";
				String dp = "?";

				Object[] data = { name, p.getPosition(), dr, dp, p.getFortyTime(), p.getThreeCone(), p.getShuttle(),
						p.getBench(), p.getVert(), fAtt, sAtt, tAtt };
				model.addRow(data);
			} else {
				Object[] data = { name, p.getPosition(), p.getDraftRound(), p.getDraftPick(), p.getFortyTime(),
						p.getThreeCone(), p.getShuttle(), p.getBench(), p.getVert(), fAtt, sAtt, tAtt };
				model.addRow(data);
			}
		}
	}

	public void updateViewTable() {
		ArrayList<Prospects> prospectList = m.getProspectList();
		DefaultTableModel model = draftboard.getViewModel();
		model.setRowCount(0);

		for (Prospects p : prospectList) {
			String name = p.getFirstName() + " " + p.getLastName();
			String fAtt = p.getFirstABV() + " - " + p.getFirstLetterGrade();
			String sAtt = p.getSecondABV() + " - " + p.getSecondLetterGrade();
			String tAtt = p.getThirdABV() + " - " + p.getThirdLetterGrade();
			if (p.getDraftRound() == -1) {
				String dr = "?";
				String dp = "?";
				Object[] data = { name, p.getPosition(), dr, dp, (byte) p.getOverall(), p.getSpeed(),
						p.getAcceleration(), p.getAgility(), p.getStrength(), p.getJumping(), fAtt, sAtt, tAtt };
				model.addRow(data);

			} else {
				Object[] data = { name, p.getPosition(), p.getDraftRound(), p.getDraftPick(), (byte) p.getOverall(),
						p.getSpeed(), p.getAcceleration(), p.getAgility(), p.getStrength(), p.getJumping(), fAtt, sAtt,
						tAtt };
				model.addRow(data);
			}
		}
	}
}
