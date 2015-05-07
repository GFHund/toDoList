import java.awt.*;
import java.awt.event.*;

class messageDialog extends Dialog implements ActionListener
{
  Panel mPanel;
  Label mMessage;
  Button mOk;
  public messageDialog(Dialog owner,String message)
  {
    super(owner);
    init(message);
  }
  public messageDialog(Dialog owner,String title,String message)
  {
    super(owner,title);
    init(message);
  }
  public messageDialog(Frame owner,String message)
  {
    super(owner);
    init(message);
  }
  public messageDialog(Frame owner, String title,String message)
  {
    super(owner,title);
    init(message);
  }

  public void init(String message)
  {
    setSize(500,200);
    GridLayout layout = new GridLayout(2,1);
    this.mPanel = new Panel(layout);
    this.mMessage = new Label(message);
    this.mOk = new Button("OK");
    this.mOk.addActionListener(this);

    add(mPanel);
    mPanel.add(mMessage);
    mPanel.add(mOk);
  }

  public void actionPerformed(ActionEvent event)
  {
    this.setVisible(false);
  }
}
