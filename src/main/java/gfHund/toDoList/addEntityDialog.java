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
    setSize(750,250);
    //BoxLayout
    GridLayout grid = new GridLayout(5,2);
    grid.setHgap(0);
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

    this.mOk.addActionListener(this);
    this.mCancel.addActionListener(this);

    add(mPanel);
    
    JPanel[] panels = new JPanel[10];
    for(int i=0;i<panels.length;i++)
    {
      panels[i] = new JPanel();
      mPanel.add(panels[i]);
    }
    panels[0].add(mNameLabel);
    panels[1].add(mNameTextField);
    panels[2].add(mDescriptionLabel);
    panels[3].add(mDescriptionTextArea);
    panels[4].add(mCriticalDateLabel);
    panels[5].add(mCriticalDateTextField);
    panels[6].add(mEndDateLabel);
    panels[7].add(mEndDateTextField);
    panels[8].add(mOk);
    panels[9].add(mCancel);

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
