package maddenDraftAdvisor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class DraftBoard {

	private String[] editTableColumns = { "Name", "Position", "Round", "Age", "40 Time", "Three Cone Time",
			"Shuttle Time", "Bench Reps", "Vertical Jump", "First Attribute", "Second Attribute", "Third Attribute" };
	// string array of viewTable column names
	private String[] viewTableColumns = { "Name", "Position", "Round", "Age", "Est. Overall", "Speed", "Acceleration",
			"Agility", "Strength", "Jumping", "First Attribute", "Second Attribute", "Third Attribute" };
	// string array of deleteTable column names
	private String[] deleteTableColumns = { "Name", "Position", "Round", "Age", "40 Time", "Three Cone Time",
			"Shuttle Time", "Bench Reps", "Vertical Jump" };
	// tables to be used
	private JTable deleteTable, viewTable, editTable;
	// reference to main to get prospectList
	Main m = new Main();
	HelperFunctions hf = new HelperFunctions();
	// tableModels to be used
	private DefaultTableModel deleteModel, viewModel, editModel;

	// create delete panel to return to GUI
	public JPanel createDeletePanel() {
		JPanel deletePanel = new JPanel(new GridLayout());
		deletePanel.setOpaque(true);
		deletePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		// initialize deleteModel and override to make uneditable
		deleteModel = new DefaultTableModel(deleteTableColumns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		deleteTable = new JTable(deleteModel);
		deleteTable.setFocusable(false);
		deleteTable.setRowSelectionAllowed(true);

		// set the width of each column
		deleteTable.getColumnModel().getColumn(0).setPreferredWidth(170);
		deleteTable.getColumnModel().getColumn(1).setPreferredWidth(155);
		deleteTable.getColumnModel().getColumn(2).setPreferredWidth(45);
		deleteTable.getColumnModel().getColumn(3).setPreferredWidth(40);
		deleteTable.getColumnModel().getColumn(4).setPreferredWidth(60);
		deleteTable.getColumnModel().getColumn(5).setPreferredWidth(110);
		deleteTable.getColumnModel().getColumn(6).setPreferredWidth(80);
		deleteTable.getColumnModel().getColumn(7).setPreferredWidth(80);
		deleteTable.getColumnModel().getColumn(8).setPreferredWidth(85);

		// custom renderer to center align in each column
		for (int i = 1; i < 9; i++) {
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);
			deleteTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		JScrollPane scroller = new JScrollPane(deleteTable);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		deletePanel.add(scroller, BorderLayout.CENTER);
		deletePanel.setPreferredSize(new Dimension(875, 500));
		deleteTable.setToolTipText("Double click a prospect to delete him");

		// mouse event listener to listen for doubleclicks on a row
		MouseListener mouseListener = new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				JTable tempTable = (JTable) me.getSource();
				if (me.getClickCount() == 2) {
					Point point = me.getPoint();
					int index = tempTable.rowAtPoint(point);
					// if doubleclick is detected prompts the user to make sure
					// they want to delete the prospect they clicked on
					if (index >= 0) {
						Object o = tempTable.getModel().getValueAt(index, 0);
						int reply = JOptionPane.showConfirmDialog(null,
								"Are you sure you want to delete " + o.toString() + "?\nThis cannot be undone.",
								"Delete Prospect", JOptionPane.YES_NO_OPTION);
						if (reply == JOptionPane.YES_OPTION) {
							ArrayList<Prospects> pList = m.getProspectList();
							System.out.println(
									pList.get(index).getFirstName() + pList.get(index).getLastName() + "Deleted");
							pList.remove(index);
							m.updateProspectList(pList);
							// after prospect is deleted from arraylist clears
							// and reloads the jtable
							deleteModel.setRowCount(0);
							for (Prospects p : pList) {
								String name = p.getFirstName() + " " + p.getLastName();
								if (p.getDraftRound() == -1) {
									String dr = "?";
									String dp = "?";
									Object[] data = { name, p.getPosition(), dr, dp, p.getFortyTime(), p.getThreeCone(),
											p.getShuttle(), p.getBench(), p.getVert() };
									deleteModel.addRow(data);
								} else {
									Object[] data = { name, p.getPosition(), p.getDraftRound(), p.getDraftPick(),
											p.getFortyTime(), p.getThreeCone(), p.getShuttle(), p.getBench(),
											p.getVert() };
									deleteModel.addRow(data);
								}
							}

						} else {
						}

					}
				}
			};

		};
		deleteTable.addMouseListener(mouseListener);
		return deletePanel;

	}

	public JPanel createEditPanel() {
		JPanel editPanel = new JPanel(new GridLayout());
		editPanel.setOpaque(true);
		editPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		editModel = new DefaultTableModel(editTableColumns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		editTable = new JTable(editModel);
		editTable.setFocusable(false);
		editTable.setRowSelectionAllowed(true);

		editTable.getColumnModel().getColumn(0).setPreferredWidth(170);
		editTable.getColumnModel().getColumn(1).setPreferredWidth(155);
		editTable.getColumnModel().getColumn(2).setPreferredWidth(45);
		editTable.getColumnModel().getColumn(3).setPreferredWidth(40);
		editTable.getColumnModel().getColumn(4).setPreferredWidth(60);
		editTable.getColumnModel().getColumn(5).setPreferredWidth(110);
		editTable.getColumnModel().getColumn(6).setPreferredWidth(80);
		editTable.getColumnModel().getColumn(7).setPreferredWidth(80);
		editTable.getColumnModel().getColumn(8).setPreferredWidth(85);
		editTable.getColumnModel().getColumn(9).setPreferredWidth(90);
		editTable.getColumnModel().getColumn(10).setPreferredWidth(90);
		editTable.getColumnModel().getColumn(11).setPreferredWidth(90);

		for (int i = 1; i < 12; i++) {
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);
			editTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		JScrollPane scroller = new JScrollPane(editTable);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		editPanel.add(scroller, BorderLayout.CENTER);
		editTable.setToolTipText("Double click an attribute to edit it");
		editTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		editPanel.setPreferredSize(new Dimension(1145, 500));
		MouseListener mouseListener = new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				JTable tempTable = (JTable) me.getSource();
				if (me.getClickCount() == 2) {
					Point point = me.getPoint();
					int rowIndex = tempTable.rowAtPoint(point);
					int colIndex = tempTable.columnAtPoint(point);

					if (rowIndex >= 0 && colIndex >= 0) {

						float floatEntry = 0;
						int intEntry = 0;
						boolean nameEntry = false;
						boolean comboEntry = false;
						if (colIndex < 7 && colIndex > 3 || colIndex == 8) {
							while (floatEntry == 0)
								floatEntry = checkFloatOptionPane(colIndex, rowIndex);

						} else if (colIndex == 2 || colIndex == 3 || colIndex == 7) {
							while (intEntry == 0)
								intEntry = checkIntOptionPane(colIndex, rowIndex);
						} else if (colIndex == 0) {
							while (!nameEntry)
								nameEntry = checkNameOptionPane(colIndex, rowIndex);
						} else if (colIndex == 1 || colIndex > 8) {
							while (!comboEntry)
								comboEntry = checkComboOptionPane(colIndex, rowIndex);
						}

						ArrayList<Prospects> pList = m.getProspectList();

						m.updateProspectList(pList);
						editModel.setRowCount(0);
						for (Prospects p : pList) {
							String name = p.getFirstName() + " " + p.getLastName();
							String fAtt = p.getFirstABV() + " - " + p.getFirstLetterGrade();
							String sAtt = p.getSecondABV() + " - " + p.getSecondLetterGrade();
							String tAtt = p.getThirdABV() + " - " + p.getThirdLetterGrade();
							if (p.getDraftRound() == -1) {
								String dr = "?";
								String dp = "?";
								Object[] data = { name, p.getPosition(), dr, dp, p.getFortyTime(), p.getThreeCone(),
										p.getShuttle(), p.getBench(), p.getVert(), fAtt, sAtt, tAtt };
								editModel.addRow(data);
							} else {
								Object[] data = { name, p.getPosition(), p.getDraftRound(), p.getDraftPick(),
										p.getFortyTime(), p.getThreeCone(), p.getShuttle(), p.getBench(), p.getVert(),
										fAtt, sAtt, tAtt };
								editModel.addRow(data);
							}
						}

					} else {
					}

				}
			}

		};

		editTable.addMouseListener(mouseListener);
		return editPanel;

	}

	public JPanel createViewPanel() {
		JPanel viewPanel = new JPanel(new GridLayout());
		viewPanel.setOpaque(true);
		viewPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		viewModel = new DefaultTableModel(viewTableColumns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};
		viewTable = new JTable(viewModel) {
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);

				ArrayList<Prospects> pList = m.getProspectList();

				if (!isRowSelected(row)) {
					c.setBackground(getBackground());
					int modelRow = convertRowIndexToModel(row);
					Boolean drafted = pList.get(modelRow).getDrafted();
					String type = (String) getModel().getValueAt(modelRow, 0);
					if (drafted)
						c.setBackground(Color.RED);

				}
				return c;
			}

		};

		viewTable.getColumnModel().getColumn(0).setPreferredWidth(170);
		viewTable.getColumnModel().getColumn(1).setPreferredWidth(155);
		viewTable.getColumnModel().getColumn(2).setPreferredWidth(50);
		viewTable.getColumnModel().getColumn(3).setPreferredWidth(40);
		viewTable.getColumnModel().getColumn(4).setPreferredWidth(70);
		viewTable.getColumnModel().getColumn(5).setPreferredWidth(50);
		viewTable.getColumnModel().getColumn(6).setPreferredWidth(80);
		viewTable.getColumnModel().getColumn(7).setPreferredWidth(50);
		viewTable.getColumnModel().getColumn(8).setPreferredWidth(65);
		viewTable.getColumnModel().getColumn(8).setPreferredWidth(65);
		viewTable.getColumnModel().getColumn(9).setPreferredWidth(65);
		viewTable.getColumnModel().getColumn(10).setPreferredWidth(90);
		viewTable.getColumnModel().getColumn(11).setPreferredWidth(90);
		viewTable.getColumnModel().getColumn(12).setPreferredWidth(90);

		for (int i = 1; i < 13; i++) {
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);
			viewTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		JScrollPane scroller = new JScrollPane(viewTable);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		viewPanel.add(scroller, BorderLayout.CENTER);
		viewPanel.setPreferredSize(new Dimension(1115, 500));

		viewTable.setAutoCreateRowSorter(true);
		viewTable.setToolTipText("Double click a prospect to mark him as drafted");
		MouseListener mouseListener = new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				JTable tempTable = (JTable) me.getSource();

				if (me.getClickCount() == 2) {
					Point point = me.getPoint();
					int index = tempTable.rowAtPoint(point);
					int selected = tempTable.convertRowIndexToModel(index);

					if (index >= 0) {

						ArrayList<Prospects> pList = m.getProspectList();
						pList.get(selected).setDrafted();
						m.updateProspectList(pList);
						viewModel.fireTableDataChanged();

					} else {
					}
				}

			}

		};
		viewTable.addMouseListener(mouseListener);
		return viewPanel;
	}

	public float checkFloatOptionPane(int colIndex, int pI) {
		ArrayList<Prospects> pList = m.getProspectList();
		int index = colIndex;
		int prospectIndex = pI;
		float floatEntry = 0;

		String inputValue = JOptionPane.showInputDialog(null, "Enter new data for " + editTableColumns[index] + ": ",
				"Edit Prospect Info", JOptionPane.DEFAULT_OPTION);

		try {
			floatEntry = Float.parseFloat(inputValue);

		} catch (NumberFormatException e) {
			floatEntry = 0;
			return floatEntry;
		}

		switch (colIndex) {
		case 4:
			if (floatEntry > 4.2 && floatEntry < 6) {
				pList.get(prospectIndex).setFortyTime(floatEntry);
				pList.get(prospectIndex).calculateAttributes();
				pList.get(prospectIndex).calculateOverall();
				return floatEntry;
			}
			break;
		case 5:
			if (floatEntry > 6.45 && floatEntry < 8.5) {
				pList.get(prospectIndex).setThreeCone(floatEntry);
				pList.get(prospectIndex).calculateAttributes();
				pList.get(prospectIndex).calculateOverall();
				return floatEntry;
			}
			break;
		case 6:
			if (floatEntry > 3.8 && floatEntry < 5.5) {
				pList.get(prospectIndex).setShuttle(floatEntry);
				pList.get(prospectIndex).calculateAttributes();
				pList.get(prospectIndex).calculateOverall();
				return floatEntry;
			}
			break;
		case 8:
			if (floatEntry > 10 && floatEntry < 43.9) {
				pList.get(prospectIndex).setVert(floatEntry);
				pList.get(prospectIndex).calculateAttributes();
				pList.get(prospectIndex).calculateOverall();
				return floatEntry;
			}
			break;
		}

		return 0;

	}

	public byte checkIntOptionPane(int colIndex, int pI) {
		ArrayList<Prospects> pList = m.getProspectList();
		int index = colIndex;
		int prospectIndex = pI;
		byte byteEntry = 0;

		String inputValue = JOptionPane.showInputDialog(null, "Enter new data for " + editTableColumns[index] + ": ",
				"Edit Prospect Info", JOptionPane.DEFAULT_OPTION);
		;
		int response = Integer.parseInt(inputValue);
		if (response == JOptionPane.CANCEL_OPTION) {
			return byteEntry;
		} else if (response == JOptionPane.CLOSED_OPTION) {
			return byteEntry;
		}
		try {
			byteEntry = (byte) Integer.parseInt(inputValue);

		} catch (NumberFormatException e) {
			byteEntry = 0;
			return byteEntry;
		}

		switch (colIndex) {
		case 2:
			if (byteEntry > 0 && byteEntry < 8) {
				pList.get(prospectIndex).setDraftRound(byteEntry);
				if (pList.get(prospectIndex).getDraftPick() == -1)
					pList.get(prospectIndex).setDraftPick((byte) 0);
				return byteEntry;
			}
			break;
		case 3:
			if (byteEntry > 18 && byteEntry < 28) {
				pList.get(prospectIndex).setDraftPick(byteEntry);
				if (pList.get(prospectIndex).getDraftRound() == -1)
					pList.get(prospectIndex).setDraftRound((byte) 0);
				return byteEntry;
			}
			break;

		case 7:
			if (byteEntry > 0 && byteEntry < 43) {
				pList.get(prospectIndex).setBench(byteEntry);
				pList.get(prospectIndex).calculateAttributes();
				pList.get(prospectIndex).calculateOverall();
				return byteEntry;
			}
			break;
		}
		return 0;
	}

	public boolean checkNameOptionPane(int colIndex, int pI) {
		ArrayList<Prospects> pList = m.getProspectList();
		int index = colIndex;
		int prospectIndex = pI;
		boolean fn = false, ln = false;

		JTextField firstNameField = new JTextField();
		JTextField lastNameField = new JTextField();

		firstNameField.setText(pList.get(prospectIndex).getFirstName());
		lastNameField.setText(pList.get(prospectIndex).getLastName());

		JPanel tempPanel = new JPanel();
		tempPanel.setLayout(new GridLayout(2, 2, 5, 5));
		tempPanel.add(new JLabel("First Name"));
		tempPanel.add(firstNameField);

		tempPanel.add(new JLabel("Last Name"));
		tempPanel.add(lastNameField);
		firstNameField.requestFocus();

		JOptionPane.showConfirmDialog(null, tempPanel, "Edit Prospect Name", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, null);
		fn = hf.checkStringInput(firstNameField.getText());
		ln = hf.checkStringInput(lastNameField.getText());
		if (ln == true && firstNameField.getText() != null && fn == true && lastNameField.getText() != null) {
			pList.get(prospectIndex).setFirstName(firstNameField.getText());
			pList.get(prospectIndex).setLastName(lastNameField.getText());
			return true;
		}

		return false;
	}

	public boolean checkComboOptionPane(int colIndex, int pI) {

		ArrayList<Prospects> pList = m.getProspectList();
		int index = colIndex;
		int prospectIndex = pI;
		String[] comboBox = new String[50];
		int attPanel1 = 0, attPanel2 = 0, attPanel3 = 0;

		JComboBox<String> attComboBox = new JComboBox<String>(Prospects.attributeChoices);
		JComboBox<String> gradeComboBox = new JComboBox<String>(Prospects.attributeGrade);

		JPanel tempPanel = new JPanel();
		tempPanel.setLayout(new GridLayout(2, 2, 0, 5));
		tempPanel.add(new JLabel("Attribute"));
		tempPanel.add(attComboBox);
		tempPanel.add(Box.createHorizontalStrut(5)); // a spacer
		tempPanel.add(new JLabel("Grade"));
		tempPanel.add(gradeComboBox);

		if (colIndex == 1) {
			comboBox = Prospects.positionChoices;
			String inputValue = (String) JOptionPane.showInputDialog(null, "Edit Prospect", "Edit Prospect",
					JOptionPane.DEFAULT_OPTION, null, comboBox, comboBox[0]);
			if (inputValue != null) {
				pList.get(prospectIndex).setPosition(inputValue);
				pList.get(prospectIndex).calculateOverall();
				return true;
			} else {
				return true;
			}

		} else if (colIndex == 9) {
			attComboBox.setSelectedItem(pList.get(prospectIndex).getFirstAttribute());
			gradeComboBox.setSelectedItem(pList.get(prospectIndex).getFirstLetterGrade());
			attPanel1 = JOptionPane.showConfirmDialog(null, tempPanel, "Change Attribute", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
			if (attPanel1 == JOptionPane.YES_OPTION) {
				if (attComboBox.getSelectedItem() != pList.get(prospectIndex).getSecondAttribute()
						&& attComboBox.getSelectedItem() != pList.get(prospectIndex).getThirdAttribute()) {
					pList.get(prospectIndex).setFirstAttribute((String) attComboBox.getSelectedItem());
					pList.get(prospectIndex).setFirstLetterGrade((String) gradeComboBox.getSelectedItem());
					double newRating = pList.get(prospectIndex)
							.registerScoutingGrades((String) gradeComboBox.getSelectedItem());
					pList.get(prospectIndex).setFirstConvertedGrade(newRating);
					pList.get(prospectIndex).assignScoutingGrade();
					pList.get(prospectIndex).calculateOverall();
					System.out.println(pList.get(prospectIndex).getFirstLetterGrade() + " - "
							+ pList.get(prospectIndex).getFirstConvertedGrade() + " / "
							+ pList.get(prospectIndex).getOverall());
					return true;

				} else
					return false;
			} else if (attPanel1 == JOptionPane.CANCEL_OPTION) {
				return true;

			} else if (attPanel1 == JOptionPane.CLOSED_OPTION) {
				return true;
			}

		} else if (colIndex == 10) {
			attComboBox.setSelectedItem(pList.get(prospectIndex).getSecondAttribute());
			gradeComboBox.setSelectedItem(pList.get(prospectIndex).getSecondLetterGrade());

			attPanel2 = JOptionPane.showConfirmDialog(null, tempPanel, "Change Attribute", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
			if (attPanel2 == JOptionPane.YES_OPTION) {
				if (attComboBox.getSelectedItem() != pList.get(prospectIndex).getFirstAttribute()
						&& attComboBox.getSelectedItem() != pList.get(prospectIndex).getThirdAttribute()) {
					pList.get(prospectIndex).setSecondAttribute((String) attComboBox.getSelectedItem());
					pList.get(prospectIndex).setSecondLetterGrade((String) gradeComboBox.getSelectedItem());
					double newRating = pList.get(prospectIndex)
							.registerScoutingGrades((String) gradeComboBox.getSelectedItem());
					pList.get(prospectIndex).setSecondConvertedGrade(newRating);
					pList.get(prospectIndex).assignScoutingGrade();
					pList.get(prospectIndex).calculateOverall();
					System.out.println(pList.get(prospectIndex).getSecondLetterGrade() + " - "
							+ pList.get(prospectIndex).getSecondConvertedGrade() + " / "
							+ pList.get(prospectIndex).getOverall());
					return true;

				} else
					return false;

			} else if (attPanel2 == JOptionPane.CANCEL_OPTION) {
				return true;

			} else if (attPanel2 == JOptionPane.CLOSED_OPTION) {
				return true;
			}

		} else if (colIndex == 11) {
			attComboBox.setSelectedItem(pList.get(prospectIndex).getThirdAttribute());
			gradeComboBox.setSelectedItem(pList.get(prospectIndex).getThirdLetterGrade());
			attPanel3 = JOptionPane.showConfirmDialog(null, tempPanel, "Change Attribute", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
			if (attPanel3 == JOptionPane.YES_OPTION) {
				if (attComboBox.getSelectedItem() != pList.get(prospectIndex).getFirstAttribute()
						&& attComboBox.getSelectedItem() != pList.get(prospectIndex).getSecondAttribute()) {
					pList.get(prospectIndex).setThirdAttribute((String) attComboBox.getSelectedItem());
					pList.get(prospectIndex).setThirdLetterGrade((String) gradeComboBox.getSelectedItem());
					double newRating = pList.get(prospectIndex)
							.registerScoutingGrades((String) gradeComboBox.getSelectedItem());
					pList.get(prospectIndex).setThirdConvertedGrade(newRating);
					pList.get(prospectIndex).assignScoutingGrade();
					pList.get(prospectIndex).calculateOverall();

					System.out.println(pList.get(prospectIndex).getThirdLetterGrade() + " - "
							+ pList.get(prospectIndex).getThirdConvertedGrade() + " / "
							+ pList.get(prospectIndex).getOverall());
					return true;

				} else
					return false;

			} else if (attPanel3 == JOptionPane.CANCEL_OPTION) {
				return true;

			} else if (attPanel3 == JOptionPane.CLOSED_OPTION) {
				return true;
			}
		}

		return false;
	}

	public DefaultTableModel getDeleteModel() {
		return deleteModel;
	}

	public DefaultTableModel getViewModel() {
		return viewModel;
	}

	public DefaultTableModel getEditModel() {
		return editModel;
	}

}
