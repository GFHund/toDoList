package gfHund.toDoList;

import java.lang.Runnable;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;

import java.io.PrintWriter;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.DataOutputStream;




class serverDialog extends JDialog implements ActionListener,Runnable
{
  private JPanel mMainPanel=new JPanel();
  private JLabel mIPAdress = new JLabel("Host Name");
  private JLabel mStatus = new JLabel("Warte auf Server Start");
  private JButton mStartServer = new JButton("Start Server");
  private JButton mClose = new JButton("Close");

  private ServerSocket mServer;
  private boolean mServerStarted = false;
  private toDosSource mSource;

  public serverDialog(toDosSource source)
  {
    super();
    init(source);
  }
  public serverDialog(Dialog owner,toDosSource source)
  {
    super(owner);
    init(source);
  }
  public serverDialog(Dialog owner,String title,toDosSource source)
  {
    super(owner,title);
    init(source);
  }
  public serverDialog(Frame owner, toDosSource source)
  {
    super(owner);
    init(source);
  }
  public serverDialog(Frame owner, String title,toDosSource source)
  {
    super(owner,title);
    init(source);
  }
  /*
  Initialize all Components of this dialog
  */
  private void init(toDosSource source)
  {
    this.mSource = source;
    try{
    this.mServer = new ServerSocket(1423);
    InetAddress serverHostName = InetAddress.getLocalHost();
    mIPAdress.setText(serverHostName.getHostName());
    }
    catch(IOException e)
    {
      messageDialog msgDlg = new messageDialog(this,"Error","Error: "+e.getMessage());
      msgDlg.setVisible(true);
      logging.addLog("Error: "+e.getMessage());
    }
    this.mMainPanel.add(mIPAdress);
    this.mMainPanel.add(mStatus);
    this.mMainPanel.add(mStartServer);
    this.mMainPanel.add(mClose);

    this.mStartServer.addActionListener(this);
    this.mClose.addActionListener(this);

    add(this.mMainPanel);
  }
  /*
  if one of the butten is pressed than this function is called
  */
  public void actionPerformed(ActionEvent e)
  {
    JButton button = (JButton)e.getSource();
    if(button == this.mClose)
    {
      this.setVisible(false);
    }
    else if(button == this.mStartServer)
    {
      if(this.mServerStarted)
      {
        this.mStartServer.setText("Start Server");
        this.mServerStarted = false;
        try
        {
          this.mServer.close();
        }
        catch(IOException g)
        {
          messageDialog msgDlg = new messageDialog(this,"Error","Error: "+g.getMessage());
          msgDlg.setVisible(true);
          logging.addLog("Error: "+g.getMessage());
        }
      }
      else
      {
        this.mStartServer.setText("Stop Server");
        this.mServerStarted = true;
        try
        {
          mServer = new ServerSocket(1423);
        }
        catch(IOException f)
        {
          messageDialog msgDlg = new messageDialog(this,"Error","Error: "+f.getMessage());
          msgDlg.setVisible(true);
          logging.addLog("Error: "+f.getMessage());
        }
      }
    }
  }
  /*
   that Code runs on an seperate Thread
  */
  public void run()
  {
    try
    {
      Socket client = this.mServer.accept();
      this.mStatus.setText("Connected with: "+client.toString());

      String fileName = this.mSource.getFileName();
      String initialInfo = fileName.length()+";"+fileName;
      File xmlFile = new File(this.mSource.getFilePath());
      long fileSize = xmlFile.length();
      if(fileSize <= 0 && fileSize > Integer.MAX_VALUE)
      {
        messageDialog msgDlg = new messageDialog(this,"Error","Error: Size of File is 0 Byte or it is greater than 2^31 Bytes");
        msgDlg.setVisible(true);
        logging.addLog("Error: Size of File is 0 Byteor it is greater than 2^31 Bytes");
        return;
      }
      int fileSizeInteger = (int)fileSize;
      byte[] xmlFileContent = new byte[fileSizeInteger];
      FileInputStream fileStream = new FileInputStream(xmlFile);
      fileStream.read(xmlFileContent);

      DataOutputStream stream = new DataOutputStream(client.getOutputStream());
      stream.writeUTF(fileName);
      stream.writeInt(fileSizeInteger);
      stream.writeBytes(new String(xmlFileContent));
      client.close();
    }
    catch(IOException e)
    {
      messageDialog msgDlg = new messageDialog(this,"Error","Error: "+e.getMessage());
      msgDlg.setVisible(true);
      logging.addLog("Error: "+e.getMessage());
    }
  }
}
