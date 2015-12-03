package payrollgui;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


public class PayrollGUI extends JFrame  implements ActionListener, ItemListener {
  private JTextField empIDText = new JTextField(10);
  private JTextField hoursText = new JTextField(10);
  private JTextField rateText = new JTextField(10);
  private JCheckBox c1 = new JCheckBox("union member");
  private JRadioButton r1 = new JRadioButton("1.0");
  private JRadioButton r2 = new JRadioButton("1.5");
  private JRadioButton r3 = new JRadioButton("2.0");	
  private JTextArea output = new JTextArea(8, 30);
  private JButton viewData = new JButton("view data");
  private JButton addPayroll = new JButton("add to payroll");
  private JButton clearPayroll = new JButton("clear payroll");
  private double payroll;   // accumulating payroll amount
  NewEmployee programmer = new NewEmployee();

// Creates the initial payroll GUI
  public PayrollGUI() {
  // Define the layout manager for the frame.
  getContentPane().setLayout(new FlowLayout());

 // Fill idPanel and add it to frame.
  JPanel dataPanel = new JPanel();
 
  JPanel idPanel = new JPanel();
  JLabel askEmpID = new JLabel("employee id >>");
  idPanel.add(askEmpID);
  idPanel.add(empIDText);
  dataPanel.add(idPanel);

  // Fill hoursPanel and add it to frame.
  JPanel hoursPanel = new JPanel();
  JLabel askHours = new JLabel("hours worked >>");
  hoursPanel.add(askHours);
  hoursPanel.add(hoursText);
  dataPanel.add(hoursPanel);

  // Fill ratePanel and add it to frame.
  JPanel ratePanel = new JPanel();
  JLabel askRate = new JLabel("hourly rate >>");
  ratePanel.add(askRate);
  ratePanel.add(rateText);
  dataPanel.add(ratePanel);

// Fill unionPanel and add it to dataPanel.
  JPanel unionPanel = new JPanel();
  unionPanel.add(c1);
  dataPanel.add(unionPanel);
  getContentPane().add(dataPanel);

  // Fill overtimePanel and add it to frame.
  JPanel overtimePanel = new JPanel();
  overtimePanel.add(r1);
  overtimePanel.add(r2);
  overtimePanel.add(r3);
  overtimePanel.setBorder(
  BorderFactory.createTitledBorder("Select overtime rate"));
  getContentPane().add(overtimePanel);

  // Add each radio button to buttonGroup rates.
  ButtonGroup rates = new ButtonGroup();
  rates.add(r1);
  rates.add(r2);
  rates.add(r3);

  // Fill buttonPanel and add it to frame.
  JPanel buttonPanel = new JPanel();
  buttonPanel.add(viewData);
  buttonPanel.add(addPayroll);
  buttonPanel.add(clearPayroll);
  getContentPane().add(buttonPanel);

  // Add output text area to Frame().
  getContentPane().add(output);

  // Register applet as listener for action events
  //   on all buttons.
  viewData.addActionListener(this);
  addPayroll.addActionListener(this);
  clearPayroll.addActionListener(this);
  r1.addActionListener(this);
  r2.addActionListener(this);
  r3.addActionListener(this);

  // Register applet as listener for item events
  //   on checkbox
  c1.addItemListener(this);
  }

  public void actionPerformed(ActionEvent aE) {
  String empID;
  String hoursStr;
  String rateStr;
  double hours;
  double rate;
  double overtimeRate;

Object buttonPressed = aE.getSource();
  if (buttonPressed == viewData) {
  // Get data.
  empID = empIDText.getText();
  hoursStr = hoursText.getText();
  hours = Double.parseDouble(hoursStr);
  rateStr = rateText.getText();
  rate = Double.parseDouble(rateStr);

  // Store data in programmer.
  programmer.setPayData(empID, hours, rate);
  output.setText(programmer.toString() + "\n" +
  "Press 'Add to payroll' to process employee data\n");
  }
 else if (buttonPressed == addPayroll) {
  // Get data.
  empID = empIDText.getText();
  hoursStr = hoursText.getText().trim();
  hours = Double.parseDouble(hoursStr);
  rateStr = rateText.getText().trim();
  rate = Double.parseDouble(rateStr);

  // Store data and compute gross pay.
  programmer.setPayData(empID, hours, rate);
  double gross = programmer.computeGross();

  // Compute net pay, given gross pay.
  double net = programmer.computeNet(gross);

 // Add gross pay to payroll
  payroll = payroll + gross;

  // Display pay amounts and payroll so far.
  output.setText(programmer.toString() +
  "Gross pay is $" + gross +
  "\nNet pay is $" + net +
  "\n\nTotal gross payroll is $" + payroll);
  empIDText.setText("");
  rateText.setText("");
  hoursText.setText("");
  }
else if (buttonPressed == clearPayroll) {
  // Clear payroll
  output.setText("Final gross payroll is $" + payroll +
  "\nGross payroll reset to 0");
  payroll = 0;
 empIDText.setText("");
 rateText.setText("");
 hoursText.setText("");
  }
 else if (r1.isSelected()) {
  overtimeRate = Double.parseDouble(r1.getText());
  programmer.setOvertimeRate(overtimeRate);
 }

else if (r2.isSelected()) {
  overtimeRate = Double.parseDouble(r2.getText());
  programmer.setOvertimeRate(overtimeRate);
 }
 else if (r3.isSelected()) {
  overtimeRate = Double.parseDouble(r3.getText());
  programmer.setOvertimeRate(overtimeRate);
  }
 }  // end actionPerformed()

public void itemStateChanged(ItemEvent iE) {
  Object choice = iE.getItemSelectable();
  if (choice == c1)
  programmer.setInUnion(c1.isSelected());
  }

  // Creates the frame and closes the frame.
 public static void main(String[] args) {
  PayrollGUI pay = new PayrollGUI();
  pay.setSize(350, 430);
  pay.setVisible(true);
  pay.setTitle("Payroll with GUI");
  pay.addWindowListener(
  new WindowAdapter() {
  public void windowClosing(WindowEvent e) {
  System.exit(0);
  }
  }
 );
  }
}

