package eg.edu.alexu.csd.oop.jdbc.cs09;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import eg.edu.alexu.csd.oop.db.cs09.DB;

public class JDBCgui extends JFrame {
	private Connection con;
	private Statement x;
	private ResultSet y;
	private ResultSetMetaData z;
	private Driver driver = new DriverClass();
	private Properties info = new Properties();

	public JDBCgui(Statement x) {
		this.x = x;
	}

	public JDBCgui(ResultSet y) {
		this.y = y;
	}

	private JPanel contentPane;
	private JTextField textField;
	private String fn = null;
	private String result = "";
	private boolean booldone = false;
	private JTextField textField_2;
	private JTextField textField_1;
	private JTextField textField_3;
	private String gettime = "";
	private static Logger log = Logger.getLogger(DB.class.getName());
	private static Logger logDriver = Logger.getLogger(DriverClass.class.getName());
	private static Logger logConnection = Logger.getLogger(Connection2.class.getName());
	private static Logger logStatement = Logger.getLogger(StatementClass.class.getName());
	private static Logger logResulSetMeta = Logger.getLogger(ResultSetMetaDataClass.class.getName());
	private static Logger logResultSet = Logger.getLogger(ResultsetClass.class.getName());

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					JDBCgui frame = new JDBCgui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JDBCgui() {
		FileHandler fileHandler = null;
		x = new StatementClass(con);
		try {
			fileHandler = new FileHandler("./javacodegeeks.txt");
			Properties preferences = new Properties();
			FileInputStream configFile = new FileInputStream("./m.properties");
			preferences.load(configFile);
			LogManager.getLogManager().readConfiguration(configFile);

			log.getLogger("File Logging");
			logDriver.getLogger("File Logging");
			logConnection.getLogger("File Logging");
			logStatement.getLogger("File Logging");
			logResulSetMeta.getLogger("File Logging");
			logResultSet.getLogger("File Logging");

			log.addHandler(fileHandler);
			logDriver.addHandler(fileHandler);
			logConnection.addHandler(fileHandler);
			logStatement.addHandler(fileHandler);
			logResulSetMeta.addHandler(fileHandler);
			logResultSet.addHandler(fileHandler);
			log.addHandler(new ConsoleHandler());
			logDriver.addHandler(new ConsoleHandler());
			logConnection.addHandler(new ConsoleHandler());
			logStatement.addHandler(new ConsoleHandler());
			logResulSetMeta.addHandler(new ConsoleHandler());
			logResultSet.addHandler(new ConsoleHandler());

		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 710, 426);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);

		JLabel lblWriteStatement = new JLabel("Write Statement :");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblWriteStatement, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblWriteStatement, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblWriteStatement, 44, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblWriteStatement, 157, SpringLayout.WEST, contentPane);
		lblWriteStatement.setFont(new Font("Tahoma", Font.BOLD, 15));
		contentPane.add(lblWriteStatement);

		textField = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, textField, 13, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, textField, 165, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, textField, 47, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, textField, 398, SpringLayout.WEST, contentPane);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel = new JLabel("");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblNewLabel, 200, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblNewLabel, 264, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblNewLabel, 234, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblNewLabel, 481, SpringLayout.WEST, contentPane);
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		contentPane.add(lblNewLabel);

		JLabel label_2 = new JLabel("");
		label_2.setForeground(Color.RED);
		label_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		contentPane.add(label_2);
		JButton btnExecute = new JButton("Execute");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnExecute, 19, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnExecute, 408, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnExecute, 40, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnExecute, 512, SpringLayout.WEST, contentPane);
		btnExecute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					boolean bol = x.execute(textField.getText());
					if (textField.getText().toLowerCase().contains("create")
							&& textField.getText().toLowerCase().contains("database") && bol) {
						try {
							con = driver.connect("jdbc:xmldb://localhost", info);
							x = con.createStatement();
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
					if (textField.getText().toLowerCase().contains("drop")
							&& textField.getText().toLowerCase().contains("database") && bol) {
						try {
							x.close();
							con.close();
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
					if (!bol) {
						result = "ERROR!!";
					}
					label_2.setText(result);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				textField.setText("");
			}
		});
		btnExecute.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(btnExecute);

		JButton btnAddBatch = new JButton("Execute Batch");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnAddBatch, 19, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnAddBatch, 519, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnAddBatch, 40, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnAddBatch, 660, SpringLayout.WEST, contentPane);
		btnAddBatch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					textField.setText("");
					x.executeBatch();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnAddBatch.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(btnAddBatch);

		JButton btnAddBatch_1 = new JButton("Add Batch");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnAddBatch_1, 50, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnAddBatch_1, 529, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnAddBatch_1, 71, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnAddBatch_1, 663, SpringLayout.WEST, contentPane);
		btnAddBatch_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					x.addBatch(textField.getText());
					textField.setText("");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnAddBatch_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(btnAddBatch_1);

		JComboBox comboBox = new JComboBox();
		sl_contentPane.putConstraint(SpringLayout.NORTH, comboBox, 147, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, comboBox, 146, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, comboBox, 181, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, comboBox, 322, SpringLayout.WEST, contentPane);
		int value = comboBox.getItemCount();
		comboBox.setFont(new Font("Tahoma", Font.BOLD, 15));
		comboBox.setModel(new DefaultComboBoxModel(
				new String[] { "find column", "get object(col index)", "get object(string colname)",
						"get string(String collabel)", "get string(col index)", "close", "next", "previous", "first",
						"last", "afterfirst", "beforelast", "get int(col index)", "get int(string colname)" }));
		comboBox.setForeground(Color.LIGHT_GRAY);
		MyActionListener actionListener = new MyActionListener();
		comboBox.addActionListener(actionListener);

		contentPane.add(comboBox);

		JLabel lblGetResultset = new JLabel("Get ResultSet:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblGetResultset, 147, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblGetResultset, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblGetResultset, 181, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblGetResultset, 136, SpringLayout.WEST, contentPane);
		lblGetResultset.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblGetResultset.setForeground(Color.BLACK);
		contentPane.add(lblGetResultset);

		JButton btnDone = new JButton("Done");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnDone, 151, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnDone, 605, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnDone, 181, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnDone, 690, SpringLayout.WEST, contentPane);
		btnDone.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDone.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String value = textField_2.getText();
				textField_2.setText("");
				if (fn != null) {
					if (fn.equals("find column")) {

						try {
							int n = y.findColumn(value);
							result = String.valueOf(n);
							fn = null;
						} catch (SQLException e) {
							result = "ERROR!!";
							e.printStackTrace();
						}
					}
					if (fn.equals("get object(col index)")) {
						int n = Integer.parseInt(value);
						try {
							Object op = y.getObject(n);
							op = op.toString();
							result = (String) op;
						} catch (SQLException e) {
							result = "ERROR!!";
							e.printStackTrace();
						}

					}
					if (fn.equals("get object(string colname)")) {
						try {
							Object op = y.getObject(value);
							op = op.toString();
							result = (String) op;
						} catch (SQLException e) {
							result = "ERROR!!";
							e.printStackTrace();
						}

					}
					if (fn.equals("get string(String collabel)")) {
						try {
							String op = y.getString(value);
							result = op;
						} catch (SQLException e) {
							result = "ERROR!!";
							e.printStackTrace();
						}

					}
					if (fn.equals("get string(col index)")) {
						int n = Integer.parseInt(value);
						try {
							String op = y.getString(n);
							result = op;
						} catch (SQLException e) {
							result = "ERROR!!";
							e.printStackTrace();
						}

					}
					if (fn.equals("close")) {
						try {
							y.close();
							result = "";
						} catch (SQLException e) {
							result = "ERROR!!";
							e.printStackTrace();
						}

					}
					if (fn.equals("next")) {
						try {
							Boolean bol = y.next();
							if (bol) {
								result = "TRUE";
							} else {
								result = "FALSE";
							}
						} catch (SQLException e) {
							result = "ERROR!!";
							e.printStackTrace();
						}

					}
					if (fn.equals("previous")) {
						try {
							Boolean bol = y.previous();
							if (bol) {
								result = "TRUE";
							} else {
								result = "FALSE";
							}
						} catch (SQLException e) {
							result = "ERROR!!";
							e.printStackTrace();
						}

					}
					if (fn.equals("first")) {
						try {
							Boolean bol = y.first();
							if (bol) {
								result = "TRUE";
							} else {
								result = "FALSE";
							}
						} catch (SQLException e) {
							result = "ERROR!!";
							e.printStackTrace();
						}

					}
					if (fn.equals("last")) {
						try {
							Boolean bol = y.last();
							if (bol) {
								result = "TRUE";
							} else {
								result = "FALSE";
							}
						} catch (SQLException e) {
							result = "ERROR!!";
							e.printStackTrace();
						}

					}
					if (fn.equals("afterlast")) {
						try {
							y.afterLast();
						} catch (SQLException e) {
							result = "ERROR!!";
							e.printStackTrace();
						}

					}
					if (fn.equals("beforefirst")) {
						try {
							y.beforeFirst();
						} catch (SQLException e) {
							result = "ERROR!!";
							e.printStackTrace();
						}

					}
					if (fn.equals("get int(col index)")) {
						int n = Integer.parseInt(value);
						try {
							int op = y.getInt(n);
							result = String.valueOf(op);
						} catch (SQLException e) {
							result = "ERROR!!";
							e.printStackTrace();
						}

					}
					if (fn.equals("get int(string colname)")) {
						try {
							int op = y.getInt(value);
							result = String.valueOf(op);
						} catch (SQLException e) {
							result = "ERROR!!";
							e.printStackTrace();
						}

					}
					fn = null;
					lblNewLabel.setText(result);
				} else {
					result = "ERROR!!";
					lblNewLabel.setText(result);
				}
			}
		});

		JLabel lblAnswerIs = new JLabel("Answer is :");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblAnswerIs, 200, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblAnswerIs, 146, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblAnswerIs, 234, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblAnswerIs, 293, SpringLayout.WEST, contentPane);
		lblAnswerIs.setFont(new Font("Tahoma", Font.BOLD, 15));
		contentPane.add(lblAnswerIs);
		;
		contentPane.add(btnDone);

		JButton btnResultset = new JButton("ResultSet");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnResultset, 50, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnResultset, 408, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnResultset, 71, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnResultset, 512, SpringLayout.WEST, contentPane);
		btnResultset.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnResultset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					y = new ResultsetClass();
					y = x.executeQuery(textField.getText());
					textField.setText("");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		contentPane.add(btnResultset);

		textField_2 = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, textField_2, 150, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, textField_2, 351, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, textField_2, 184, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, textField_2, 552, SpringLayout.WEST, contentPane);
		contentPane.add(textField_2);
		textField_2.setColumns(10);

		JButton btnGoToMetadata = new JButton("Get MetaData");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnGoToMetadata, 263, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnGoToMetadata, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnGoToMetadata, 308, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnGoToMetadata, 181, SpringLayout.WEST, contentPane);
		btnGoToMetadata.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				z = new ResultSetMetaDataClass();
				try {
					z = y.getMetaData();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnGoToMetadata.setFont(new Font("Tahoma", Font.BOLD, 15));
		contentPane.add(btnGoToMetadata);

		JComboBox comboBox_1 = new JComboBox();
		sl_contentPane.putConstraint(SpringLayout.NORTH, comboBox_1, 263, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, comboBox_1, 214, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, comboBox_1, 308, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, comboBox_1, 385, SpringLayout.WEST, contentPane);
		comboBox_1.setForeground(Color.LIGHT_GRAY);
		comboBox_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] { "getColumnCount", "getColumnLabel(int column)",
				"getColumnName(int column)", "getColumnType(int column)", "getTableName(int column)" }));
		comboBox_1.addActionListener(actionListener);
		contentPane.add(comboBox_1);

		JLabel label = new JLabel("Answer is :");
		sl_contentPane.putConstraint(SpringLayout.NORTH, label, 330, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, label, 165, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, label, 364, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, label, 312, SpringLayout.WEST, contentPane);
		label.setFont(new Font("Tahoma", Font.BOLD, 15));
		contentPane.add(label);

		JLabel label_1 = new JLabel("");
		sl_contentPane.putConstraint(SpringLayout.NORTH, label_1, 330, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, label_1, 295, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, label_1, 364, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, label_1, 512, SpringLayout.WEST, contentPane);
		label_1.setForeground(Color.RED);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		contentPane.add(label_1);
		textField_1 = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, textField_1, 271, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, textField_1, 394, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, textField_1, 305, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, textField_1, 595, SpringLayout.WEST, contentPane);
		textField_1.setColumns(10);
		contentPane.add(textField_1);

		JButton btnDone_1 = new JButton("Done");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnDone_1, 268, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnDone_1, 605, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnDone_1, 302, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnDone_1, 690, SpringLayout.WEST, contentPane);
		btnDone_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDone_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (fn != null) {
					if (fn.equals("getColumnCount")) {
						try {
							int n = z.getColumnCount();
							result = String.valueOf(n);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (fn.equals("getColumnLabel(int column)")) {
						String gettext = textField_1.getText();
						int a = Integer.parseInt(gettext);
						try {
							result = z.getColumnLabel(a);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (fn.equals("getColumnName(int column)")) {
						String gettext = textField_1.getText();
						int a = Integer.parseInt(gettext);
						try {
							result = z.getColumnName(a);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (fn.equals("getColumnType(int column)")) {
						String gettext = textField_1.getText();
						int a = Integer.parseInt(gettext);
						try {
							int s = z.getColumnType(a);
							if (s == 12) {
								result = String.valueOf(s) + " (VarChar)";
							} else {
								result = String.valueOf(s) + " (Int)";
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (fn.equals("getTableName(int column)")) {
						String gettext = textField_1.getText();
						int a = Integer.parseInt(gettext);
						try {
							result = z.getTableName(a);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					fn = null;
					label_1.setText(result);
				} else {
					result = "ERROR!!";
					label_1.setText(result);
				}
				textField_1.setText("");
			}
		});
		contentPane.add(btnDone_1);

		JLabel lblSetTime = new JLabel("Set Time:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblSetTime, 85, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblSetTime, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblSetTime, 119, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblSetTime, 114, SpringLayout.WEST, contentPane);
		lblSetTime.setFont(new Font("Tahoma", Font.BOLD, 15));
		contentPane.add(lblSetTime);

		textField_3 = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, textField_3, 85, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, textField_3, 89, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, textField_3, 119, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, textField_3, 265, SpringLayout.WEST, contentPane);
		textField_3.setColumns(10);
		contentPane.add(textField_3);

		JButton btnSet = new JButton("Set");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnSet, 85, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnSet, 313, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnSet, 115, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnSet, 398, SpringLayout.WEST, contentPane);
		btnSet.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gettime = textField_3.getText();
				int time = Integer.parseInt(textField_3.getText());
				try {
					x.setQueryTimeout(time);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		contentPane.add(btnSet);

	}

	class MyActionListener implements ActionListener {
		Object oldItem;
		int integ = 0;

		@Override
		public void actionPerformed(ActionEvent evt) {
			JComboBox comboBox = (JComboBox) evt.getSource();
			Object newItem = comboBox.getSelectedItem();
			fn = (String) newItem;
		}

	}
}
