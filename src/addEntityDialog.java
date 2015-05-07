import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;

/*
Aus text Datum über SimpleDateFormat
*/
class addEntityDialog extends Dialog implements ActionListener
{
  Panel mPanel;
  Label mNameLabel;
  TextField mNameTextField;
  Label mDescriptionLabel;
  TextArea mDescriptionTextArea;
  Label mEndDateLabel;
  TextField mEndDateTextField;
  Label mCriticalDateLabel;
  TextField mCriticalDateTextField;
  Button mOk;
  Button mCancel;

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
    GridLayout grid = new GridLayout(5,2);
    this.mSource = source;
    this.mPanel = new Panel(grid);

    this.mOk = new Button("OK");
    this.mCancel = new Button("Cancel");

    this.mNameLabel = new Label("Name:");
    this.mDescriptionLabel = new Label("Description: ");
    this.mEndDateLabel = new Label("End Date(dd.MM.yyyy HH:mm:ss):");
    this.mCriticalDateLabel = new Label("Critical Date(dd.MM.yyyy HH:mm:ss): ");

    this.mNameTextField = new TextField(30);
    this.mDescriptionTextArea = new TextArea(3,30);
    this.mEndDateTextField = new TextField(30);
    this.mCriticalDateTextField = new TextField(30);

    this.mOk.addActionListener(this);
    this.mCancel.addActionListener(this);

    add(mPanel);

    mPanel.add(mNameLabel);
    mPanel.add(mNameTextField);
    mPanel.add(mDescriptionLabel);
    mPanel.add(mDescriptionTextArea);
    mPanel.add(mCriticalDateLabel);
    mPanel.add(mCriticalDateTextField);
    mPanel.add(mEndDateLabel);
    mPanel.add(mEndDateTextField);
    mPanel.add(mOk);
    mPanel.add(mCancel);
  }

  public toDoEntry getData()
  {
    return this.mData;
  }

  public void actionPerformed(ActionEvent e)
	{
    Button clickedButton = (Button)e.getSource();
    if(clickedButton == mOk)
    {
      String name = mNameTextField.getText();
      String description = mDescriptionTextArea.getText();
      String strEndDate = mEndDateTextField.getText();
      String strCriticalDate = mCriticalDateTextField.getText();
      SimpleDateFormat dateParser = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
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
