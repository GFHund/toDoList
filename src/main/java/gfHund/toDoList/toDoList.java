package gfHund.toDoList;

//import gfHund.toDoList.components.*;

import java.util.Date;
import java.util.Vector;
import java.text.ParseException;
import javax.xml.transform.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.*;
import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
import java.text.SimpleDateFormat;

//import javax.swing.BoxLayout;


/*
toDo:
 add Menu Items: save As
 add Panel for layouting GUI Objects
*/
public class toDoList extends JFrame implements ActionListener,ItemListener
{
	private List mToDos;
	//private toDoView mCustomToDo;
	private JList<toDoEntry> mCustomToDo;
	private Vector<toDoEntry> mToDoEntries;
	private JPanel mAppPanel;
	private JPanel mDetailsPanel;
	private JLabel mNameLabel = new JLabel("Name:\n");
	private JLabel mDescLabel = new JLabel("Description:\n");
	private JLabel mCriticalDateLabel = new JLabel("Critical Date:\n");
	private JLabel mEndDateLabel = new JLabel("End Date:\n");
	private JSeparator mSeparator[] = new JSeparator[3];

	private JMenuBar mMenuBar;
	private JMenu mFileMenu;
	private JMenu mEntries;
	private JMenu mNetwork;
	private JMenu mHelp;
	private JMenuItem mNewFile;
	private JMenuItem mFileOpen;
	private JMenuItem mSave;
	private JMenuItem mClose;
	private JMenuItem mAddEntity;
	private JMenuItem mDeleteEntity;
	private JMenuItem mServer;
	private JMenuItem mAbout;
	private toDosSource mSource;
	private int mCurIndex;

	private boolean mEnableNetwork;
	private boolean mEnableCustomListView;
	/*
	Constructor of toDoList
	*/
	public toDoList()
	{
		super("toDo List");

		this.mEnableNetwork = false;
		this.mEnableCustomListView = true;

		setSize(400,500);
		try
		{
			this.mSource = new toDosSource();
		}
		catch(ParserConfigurationException e)
		{
			messageDialog msgDlg = new messageDialog(this,"Error","Error: "+e.getMessage());
			msgDlg.setVisible(true);
		}
		catch(TransformerConfigurationException e)
		{
			messageDialog msgDlg = new messageDialog(this,"Error","Error: "+e.getMessage());
			msgDlg.setVisible(true);
		}
		catch(SAXException f)
		{
			messageDialog msgDlg = new messageDialog(this,"Error","Error: "+f.getMessage());
			msgDlg.setVisible(true);
		}

		this.mCurIndex = -1;

		this.mAppPanel = new JPanel();
		this.mAppPanel.setLayout(new BoxLayout(this.mAppPanel,BoxLayout.X_AXIS));
		this.mDetailsPanel = new JPanel();
		this.mDetailsPanel.setLayout(new BoxLayout(this.mDetailsPanel,BoxLayout.Y_AXIS));
		if(this.mEnableCustomListView)
		{
			//this.mCustomToDo = new toDoView();
			this.mToDoEntries = new Vector<toDoEntry>();
			this.mCustomToDo = new JList<toDoEntry>(mToDoEntries);
			//this.mCustomToDo.setCellRenderer(new toDoListRenderer());
		}
		else
		{
			this.mToDos = new List();
		}


		for(int i=0;i<3;i++)
		{
			this.mSeparator[i] = new JSeparator();
		}
		//private JSeperator mSeperator[3];
		if(this.mEnableCustomListView)
		{
			this.mAppPanel.add(this.mCustomToDo);
		}
		else
		{
			this.mAppPanel.add(this.mToDos);
		}
		this.mAppPanel.add(this.mDetailsPanel);

		this.mDetailsPanel.add(this.mNameLabel);
		this.mDetailsPanel.add(this.mSeparator[0]);
		this.mDetailsPanel.add(this.mDescLabel);
		this.mDetailsPanel.add(this.mSeparator[1]);
		this.mDetailsPanel.add(this.mCriticalDateLabel);
		this.mDetailsPanel.add(this.mSeparator[2]);
		this.mDetailsPanel.add(this.mEndDateLabel);

		this.mMenuBar = new JMenuBar();

		this.mFileMenu = new JMenu("File");
		this.mEntries = new JMenu("Entities");
		if(mEnableNetwork)
		{
			this.mNetwork = new JMenu("Network");
		}
		this.mHelp = new JMenu("Help");

		this.mNewFile = new JMenuItem("New");
		this.mFileOpen = new JMenuItem("Open");
		this.mSave = new JMenuItem("Save");
		this.mClose = new JMenuItem("Close");
		this.mAddEntity = new JMenuItem("Add Entity");
		this.mDeleteEntity = new JMenuItem("Delete Entity");
		this.mServer = new JMenuItem("Server");
		this.mAbout = new JMenuItem("About");

		this.mMenuBar.add(mFileMenu);
		this.mMenuBar.add(mEntries);
		if(this.mEnableNetwork)
			this.mMenuBar.add(mNetwork);
		this.mMenuBar.add(mHelp);

		this.mFileMenu.add(mNewFile);
		this.mFileMenu.add(mFileOpen);
		this.mFileMenu.add(mSave);
		this.mFileMenu.add(mClose);

		this.mEntries.add(mAddEntity);
		this.mEntries.add(mDeleteEntity);
		if(this.mEnableNetwork)
			this.mNetwork.add(mServer);

		this.mHelp.add(this.mAbout);

		this.mNewFile.addActionListener(this);
		this.mFileOpen.addActionListener(this);
		this.mSave.addActionListener(this);
		this.mClose.addActionListener(this);
		this.mAddEntity.addActionListener(this);
		this.mDeleteEntity.addActionListener(this);
		if(mEnableNetwork)
			this.mServer.addActionListener(this);

		//this.mToDos.addActionListener(mSource);
		if(!this.mEnableCustomListView)
		{
			this.mToDos.addItemListener(this);
		}
		else
		{
                    //aaaa
		}
		setJMenuBar(mMenuBar);
		add(this.mAppPanel);

		setVisible(true);
		addWindowListener( new WindowAdapter() {
		  @Override
		  public void windowClosing ( WindowEvent e ) { logging.addLog("Close Application"); System.exit( 0 ); }
		} );
	}

	/*
	call if any menu item is clicked
	*/
	@Override
	public void actionPerformed(ActionEvent event)
	{
		JMenuItem item = (JMenuItem)event.getSource();
		if(item == this.mNewFile)
		{
			FileDialog dialog = new FileDialog(this,"New toDo List",FileDialog.SAVE);
			dialog.setVisible(true);

			String file = dialog.getDirectory()+dialog.getFile();
			try
			{
				mSource.createXMLFile(file);
			}
			catch(TransformerConfigurationException e)
			{
				messageDialog msgDlg = new messageDialog(this,"Error","Error: "+e.getMessage());
				msgDlg.setVisible(true);
			}
			catch(TransformerException e)
			{
				messageDialog msgDlg = new messageDialog(this,"Error","Error: "+e.getMessage());
				msgDlg.setVisible(true);
			}
		}
		else if(item == this.mFileOpen)
		{
			//FileDialog dialog = new FileDialog(this,"open toDo List");
			//dialog.setVisible(true);
			JFileChooser fileChooser = new JFileChooser();
			//FileNameExtensionFilter filter = new FileNameExtensionFilter("XML files","xml");
			//fileChooser.setFileFilter(filter);
			fileChooser.setCurrentDirectory(new File("./"));
			int returnVal = fileChooser.showOpenDialog(this);
			if(returnVal == JFileChooser.CANCEL_OPTION)
			{
				return;
			}
			String file = fileChooser.getSelectedFile().getPath();
			System.out.println(file);
			//String file = dialog.getDirectory()+dialog.getFile();
			try
			{
				this.mSource.open(file);
			}
			catch(IOException e)
			{
				messageDialog msgDlg = new messageDialog(this,"Error","Error: "+e.getMessage());
				msgDlg.setVisible(true);
			}
			catch(SAXException e)
			{
				messageDialog msgDlg = new messageDialog(this,"Error","Error: "+e.getMessage());
				msgDlg.setVisible(true);
			}
			try
			{
				toDoEntry[] entries = this.mSource.getAllEntries();
				for(int i=0;i<entries.length;i++)
				{
					if(!this.mEnableCustomListView)
					{
						this.mToDos.add(entries[i].getName());
					}
					else
					{
						//this.mCustomToDo.add(entries[i]);
						this.mToDoEntries.add(entries[i]);
					}
				}
			}
			catch(ParseException e)
			{
				messageDialog msgDlg = new messageDialog(this,"Error","Error: "+e.getMessage());
				msgDlg.setVisible(true);
			}
		}
		else if(item == this.mSave)
		{
			try
			{
				this.mSource.save();
			}
			catch(TransformerConfigurationException e)
			{
				messageDialog msgDlg = new messageDialog(this,"Error","Error: "+e.getMessage());
				msgDlg.setVisible(true);
			}
			catch(TransformerException e)
			{
				messageDialog msgDlg = new messageDialog(this,"Error","Error: "+e.getMessage());
				msgDlg.setVisible(true);
			}
		}
		else if(item == this.mClose)
		{
			//System.out.println("close");
			logging.addLog("Close Application");
			System.exit(0);
		}
		else if(item == this.mAddEntity)
		{
			//System.out.println("add");
			if(!mSource.isOpen())
			{
				messageDialog msgDlg = new messageDialog(this,"Error","Error: You must open a File or create one to do this");
				msgDlg.setVisible(true);
				return;
			}
			addEntityDialog addEntityDlg=new addEntityDialog(this,"Add Entity",this.mSource);
			addEntityDlg.setVisible(true);

			/*
			toDoEntry data = addEntityDlg.getData();
			this.mToDos.add(data.getName());
			mSource.createEntry(data);
			*/
		}
		else if(item == this.mDeleteEntity)
		{
			//System.out.println("delete");
			if(mCurIndex != -1)
			{
				String listItem;
				if(!this.mEnableCustomListView)
				{
					listItem = this.mToDos.getItem(this.mCurIndex);
				}
				else
				{
					//listItem = this.mCustomToDo.getItem(this.mCurIndex).getName();
					listItem = this.mToDoEntries.get(this.mCurIndex).getName();
				}
				int hash = listItem.hashCode();
				boolean success = this.mSource.deleteEntry(hash);
				if(!success)
				{
					messageDialog msgDlg = new messageDialog(this,"Error","Error: Could not delete Entity. Please mark the item that you want to delete");
					msgDlg.setVisible(true);
				}
				else
				{
					if(!this.mEnableCustomListView)
					{
						this.mToDos.remove(this.mCurIndex);
					}
					else
					{
						//this.mCustomToDo.remove(this.mCurIndex);
						this.mToDoEntries.remove(this.mCurIndex);
					}
				}
			}
			else
			{
				messageDialog msgDlg = new messageDialog(this,"Error","Error: You must choose an entity");
				msgDlg.setVisible(true);
			}
		}
		else if(item == this.mServer)
		{
			if(!mSource.isOpen())
			{
				messageDialog msgDlg = new messageDialog(this,"Error","Error: You must open a File or create one to do this");
				msgDlg.setVisible(true);
				return;
			}
			serverDialog dlg = new serverDialog(this,"Network Dialog",this.mSource);
			dlg.setVisible(true);
		}
		else if(item == this.mAbout)
		{
			//aaaa
		}
		else
		{
			System.out.println("It is not an MenuItem");
		}
	}
	/*
	called if the market item of the list is changed.
	It shows all Informations on the right side
	*/
	@Override
	public void itemStateChanged(ItemEvent e)
	{
		//System.out.println(e.getItem().toString());
		this.mCurIndex = (Integer)e.getItem();

		//this.mCurIndex = (int)e.getItem();
		//System.out.println(name.toString());
		//String item = mToDos.getItem(index);
		String listItem;
		if(!this.mEnableCustomListView)
		{
			listItem = this.mToDos.getItem(this.mCurIndex);
		}
		else
		{
			//listItem = this.mCustomToDo.getItem(this.mCurIndex).getName();
			listItem = this.mToDoEntries.get(this.mCurIndex).getName();
		}
		int hash = listItem.hashCode();
		toDoEntry data =  this.mSource.getEntry(hash);
		this.mNameLabel.setText("Name:\n"+data.getName());
		this.mDescLabel.setText("Description:\n"+data.getDesctiption());
		SimpleDateFormat dateParser = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		this.mCriticalDateLabel.setText("Critical Date:\n"+dateParser.format(data.getCriticalDate()));
		this.mEndDateLabel.setText("End Date:\n"+dateParser.format(data.getEndDate()));

	}

	/*
	erase all items and initialize all new
	*/
	public void update()
	{
		//aaaaa
		try
		{
			toDoEntry[] entries = this.mSource.getAllEntries();
			for(int i = 0;i<entries.length;i++)
			{
				for(int k=0;k<entries.length-1;k++)
				{
					if( entries[k].getEndDate().after( entries[k+1].getEndDate() ) )
					{
						toDoEntry temp = entries[k];
						entries[k] = entries[k+1];
						entries[k+1] = temp;
					}
				}
			}
			if(!this.mEnableCustomListView)
			{
				this.mToDos.removeAll();
			}
			else
			{
				//this.mCustomToDo.removeAll();
				this.mToDoEntries.removeAllElements();
			}

			for(int i=0;i<entries.length;i++)
			{
				if(!this.mEnableCustomListView)
				{
					this.mToDos.add( entries[i].getName() );
				}
				else
				{
					//this.mCustomToDo.add( entries[i]);
					this.mToDoEntries.add(entries[i]);
				}
			}
		}
		catch(ParseException e)
		{
			messageDialog msgDlg = new messageDialog(this,"Error","Error: "+e.getMessage());
			msgDlg.setVisible(true);
		}
	}

	/*
	main
	*/
        /*
	public static void main(String[] args)
	{
		new toDoList();
	}
        */
}
