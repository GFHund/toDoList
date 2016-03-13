package gfHund.toDoList;
//import java.awt.*;
import java.awt.Frame;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.JButton;
//import javax.swing.event.*;
import java.text.*;
import java.util.*;
//import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;

interface addEntityDialogEvent{
    void entityDlgListener();
}
/*
Aus text Datum über SimpleDateFormat
*/
class addEntityDialog extends JDialog implements ActionListener,ItemListener
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
  JCheckBox mTimeCheckBox;
  boolean mIsTimeCheckBox;
  java.util.ArrayList<addEntityDialogEvent> mDlgEvent;
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
  //--------------------------------------------
  public addEntityDialog(Dialog owner,toDosSource source)
  {
    super(owner);
    init(source);
  }
  //--------------------------------------------
  public addEntityDialog(Dialog owner,String title,toDosSource source)
  {
    super(owner,title);
    init(source);
  }
  //--------------------------------------------
  public addEntityDialog(Frame owner,toDosSource source)
  {
    super(owner);
    init(source);
  }
  //--------------------------------------------
  public addEntityDialog(Frame owner, String title,toDosSource source)
  {
    super(owner,title);
    init(source);
  }
  //--------------------------------------------
  public void init(toDosSource source)
  {
    setSize(750,250);
    //BoxLayout
    GridLayout grid = new GridLayout(6,2);
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

    this.mTimeCheckBox = new JCheckBox("With Time");
    this.mTimeCheckBox.addItemListener(this);
    this.mTimeCheckBox.setSelected(true);
    this.mIsTimeCheckBox = true;

    //this.mOk.addActionListener(this);
    this.mOk.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            okButton_Clicked();
        }
    });
    this.mCancel.addActionListener(this);

    add(mPanel);

    JPanel[] panels = new JPanel[12];
    for(int i=0;i<panels.length;i++)
    {
      panels[i] = new JPanel();
      mPanel.add(panels[i]);
    }
    panels[0].add(mNameLabel);
    panels[1].add(mNameTextField);
    panels[2].add(mDescriptionLabel);
    panels[3].add(mDescriptionTextArea);
    panels[4].add(mTimeCheckBox);

    panels[6].add(mCriticalDateLabel);
    panels[7].add(mCriticalDateTextField);
    panels[8].add(mEndDateLabel);
    panels[9].add(mEndDateTextField);
    panels[10].add(mOk);
    panels[11].add(mCancel);

    mDlgEvent = new ArrayList<addEntityDialogEvent>();
  }
  //--------------------------------------------
    public void setAddEntityDialogEventListener(addEntityDialogEvent e)
    {
        this.mDlgEvent.add(e);
    }
    //--------------------------------------------
  public toDoEntry getData()
  {
    return this.mData;
  }
    //--------------------------------------------
  public void actionPerformed(ActionEvent e)
	{
    JButton clickedButton = (JButton)e.getSource();
    if(clickedButton == mOk)
    {
      String name = mNameTextField.getText();
      String description = mDescriptionTextArea.getText();
      this.mIsTimeCheckBox = this.mTimeCheckBox.isSelected();
      if(this.mIsTimeCheckBox)
      {
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

          //dispose();
        }
        catch(ParseException exp)
        {
          messageDialog msgDlg = new messageDialog(this,"Error","Error: "+exp.getMessage());
          msgDlg.setVisible(true);
        }
      }
      mSource.createEntry(mData);
      //toDoList win = (toDoList) getOwner();
      //win.update();
      this.setVisible(false);
      mNameTextField.setText("");
      mDescriptionTextArea.setText("");
      mEndDateTextField.setText("");
      mCriticalDateTextField.setText("");
    }
    else
    {
        //mTimeCheckBox
      setVisible(false);
      this.mNameTextField.setText("");
      this.mDescriptionTextArea.setText("");
      this.mCriticalDateTextField.setText("");
      this.mEndDateTextField.setText("");
      //dispose();
    }
  }
  //--------------------------------------------
  void okButton_Clicked()
  {
      String name = mNameTextField.getText();
      String description = mDescriptionTextArea.getText();
      if(this.mTimeCheckBox.isSelected()){
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
          mData = new toDoEntry(true, name,description);
          
      }
      mSource.createEntry(mData);
      this.mTimeCheckBox.setSelected(true);
      this.setVisible(false);
      mNameTextField.setText("");
      mDescriptionTextArea.setText("");
      mEndDateTextField.setText("");
      mCriticalDateTextField.setText("");
      
    for(addEntityDialogEvent e : mDlgEvent){
        e.entityDlgListener();
    }
  }
  //--------------------------------------------
  public void itemStateChanged(ItemEvent e)
  {
    if(e.getStateChange() == ItemEvent.DESELECTED)
    {
      this.mIsTimeCheckBox = false;
      mCriticalDateTextField.setEditable(false);
      mEndDateTextField.setEditable(false);
    }
    else
    {
      this.mIsTimeCheckBox = true;
      mCriticalDateTextField.setEditable(true);
      mEndDateTextField.setEditable(true);
    }
  }
}
