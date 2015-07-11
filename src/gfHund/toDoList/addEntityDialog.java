package gfHund.toDoList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import javax.swing.event.*;
import java.text.*;
import java.util.*;
//import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;


/*
Aus text Datum über SimpleDateFormat
*/
class addEntityDialog extends JDialog implements ActionListener
{
  JPanel mPanel;
  JLabel mNameLabel;
  JTextField mNameTextField;
  JLabel mDescriptionLabel;
  JTextArea mDescriptionTextArea;
  JLabel mEndDateLabel;
  JTextField mEndDateTextField;
  JLabel mCriticalDateLabel;
  JTextField mCriticalDateTextField;
  /*
  UtilDateModel mEndDateModel;
  JDatePanelImpl mEndDatePanel;
  JDatePickerImpl mEndDatePicker;

  UtilDateModel mCriticalDateModel;
  JDatePanelImpl mCriticalDatePanel;
  JDatePickerImpl mCriticalDatePicker;
  */
  JButton mOk;
  JButton mCancel;

  toDoEntry mData;
  toDosSource mSource;

  public addEntityDialog(Dialog owner,toDosSource source)
  {
    super(owner);
    init(source);
  }
  public addEntityDialog(Dialog owner,String title,toDosSource source)
  {
    super(owner,title);
    init(source);
  }
  public addEntityDialog(Frame owner,toDosSource source)
  {
    super(owner);
    init(source);
  }
  public addEntityDialog(Frame owner, String title,toDosSource source)
  {
    super(owner,title);
    init(source);
  }

  public void init(toDosSource source)
  {
    setSize(500,500);
    //BoxLayout
    GridLayout grid = new GridLayout(5,2);
    grid.setHgap(10);
    grid.setVgap(10);
    this.mSource = source;
    //this.mPanel = new JPanel();//
    this.mPanel = new JPanel(grid);

    this.mOk = new JButton("OK");
    this.mCancel = new JButton("Cancel");

    this.mNameLabel = new JLabel("Name:");
    this.mDescriptionLabel = new JLabel("Description: ");
    this.mEndDateLabel = new JLabel("End Date(dd.MM.yyyy HH:mm):");
    this.mCriticalDateLabel = new JLabel("Critical Date(dd.MM.yyyy HH:mm): ");

    this.mNameTextField = new JTextField(30);
    this.mDescriptionTextArea = new JTextArea(3,30);
    this.mEndDateTextField = new JTextField(30);
    this.mCriticalDateTextField = new JTextField(30);
    /*
    this.mEndDateModel = new UtilDateModel();
    Properties p = new Properties();
    p.put("text.today","Today");
    p.put("text.month","Month");
    p.put("text.year","Year");
    this.mEndDatePanel = new JDatePanelImpl(this.mEndDateModel,p);
    this.mEndDatePicker = new JDatePickerImpl(this.mEndDatePanel,new DateLabelFormatter());

    Properties p2 = new Properties();
    p2.put("text.today","Today");
    p2.put("text.month","Month");
    p2.put("text.year","Year");
    this.mCriticalDateModel = new UtilDateModel();
    this.mCriticalDatePanel = new JDatePanelImpl(this.mCriticalDateModel,p2);
    this.mCriticalDatePicker = new JDatePickerImpl(this.mCriticalDatePanel,new DateLabelFormatter());
    */
    this.mOk.addActionListener(this);
    this.mCancel.addActionListener(this);

    add(mPanel);

    mPanel.add(mNameLabel);
    mPanel.add(mNameTextField);
    mPanel.add(mDescriptionLabel);
    mPanel.add(mDescriptionTextArea);
    mPanel.add(mCriticalDateLabel);

    mPanel.add(mCriticalDateTextField);
    //mPanel.add(mCriticalDatePicker);

    mPanel.add(mEndDateLabel);

    mPanel.add(mEndDateTextField);
    //mPanel.add(mEndDatePicker);
    mPanel.add(mOk);
    mPanel.add(mCancel);
    /*
    mNameLabel.setBounds(10,10,100,50);
    mNameTextField.setBounds(220,10,200,50);
    mDescriptionLabel.setBounds(10,70,100,50);
    mDescriptionTextArea.setBounds(220,70,200,150);
    mCriticalDateLabel.setBounds(10,230,100,50);
    mCriticalDateTextField.setBounds(220,230,200,50);
    mEndDateLabel.setBounds(10,290,100,50);
    mEndDateTextField.setBounds(220,290,200,50);
    mOk.setBounds(100,350,100,50);
    mCancel.setBounds(300,350,100,50);
    */
  }

  public toDoEntry getData()
  {
    return this.mData;
  }

  public void actionPerformed(ActionEvent e)
	{
    JButton clickedButton = (JButton)e.getSource();
    if(clickedButton == mOk)
    {
      String name = mNameTextField.getText();
      String description = mDescriptionTextArea.getText();
      String strEndDate = mEndDateTextField.getText();
      String strCriticalDate = mCriticalDateTextField.getText();
      SimpleDateFormat dateParser = new SimpleDateFormat("dd.MM.yyyy HH:mm");
      try
      {
        Date endDate = dateParser.parse(strEndDate);
        Date criticalDate = dateParser.parse(strCriticalDate);
        if(mData == null)
        {
          mData = new toDoEntry(true,name,description,endDate,criticalDate);
        }
        else
        {
          mData.setActive(true);
          mData.setName(name);
          mData.setDescription(description);
          mData.setEndDate(endDate);
          mData.setCriticalDate(criticalDate);
        }
        mNameTextField.setText("");
        mDescriptionTextArea.setText("");
        mEndDateTextField.setText("");
        mCriticalDateTextField.setText("");
        this.setVisible(false);
        toDoList win = (toDoList) getOwner();
        mSource.createEntry(mData);
        win.update();
        //dispose();
      }
      catch(ParseException exp)
      {
        messageDialog msgDlg = new messageDialog(this,"Error","Error: "+exp.getMessage());
        msgDlg.setVisible(true);
      }
    }
    else
    {
      setVisible(false);
      this.mNameTextField.setText("");
      this.mDescriptionTextArea.setText("");
      this.mCriticalDateTextField.setText("");
      this.mEndDateTextField.setText("");
      //dispose();
    }
  }
}
