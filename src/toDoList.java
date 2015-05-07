import java.util.Date;
import java.text.ParseException;
import javax.xml.transform.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
/*
toDo:
 add Menu Items: save As
 add Panel for layouting GUI Objects
*/
public class toDoList extends Frame implements ActionListener
{
	private List mToDos;
	private MenuBar mMenuBar;
	private Menu mFileMenu;
	private Menu mEntries;
	private MenuItem mNewFile;
	private MenuItem mFileOpen;
	private MenuItem mSave;
	private MenuItem mClose;
	private MenuItem mAddEntity;
	private MenuItem mDeleteEntity;
	private toDosSource mSource;
	/*
	Constructor of toDoList
	*/
	public toDoList()
	{
		super("toDo List");
		setSize(200,500);
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

		this.mToDos = new List();
		this.mMenuBar = new MenuBar();

		this.mFileMenu = new Menu("File");
		this.mEntries = new Menu("Entities");

		this.mNewFile = new MenuItem("New");
		this.mFileOpen = new MenuItem("Open");
		this.mSave = new MenuItem("Save");
		this.mClose = new MenuItem("Close");
		this.mAddEntity = new MenuItem("Add Entity");
		this.mDeleteEntity = new MenuItem("Delete Entity");

		this.mMenuBar.add(mFileMenu);
		this.mMenuBar.add(mEntries);

		this.mFileMenu.add(mNewFile);
		this.mFileMenu.add(mFileOpen);
		this.mFileMenu.add(mSave);
		this.mFileMenu.add(mClose);

		this.mEntries.add(mAddEntity);
		this.mEntries.add(mDeleteEntity);

		this.mNewFile.addActionListener(this);
		this.mFileOpen.addActionListener(this);
		this.mSave.addActionListener(this);
		this.mClose.addActionListener(this);
		this.mAddEntity.addActionListener(this);
		this.mDeleteEntity.addActionListener(this);

		this.mToDos.addActionListener(mSource);

		setMenuBar(mMenuBar);
		add(this.mToDos);

		setVisible(true);
		addWindowListener( new WindowAdapter() {
		  @Override
		  public void windowClosing ( WindowEvent e ) { System.exit( 0 ); }
		} );
	}

	/*
	call if any menu item is clicked
	*/
	public void actionPerformed(ActionEvent event)
	{
		MenuItem item = (MenuItem)event.getSource();
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
			/*
			catch(IOException e)
			{
				messageDialog msgDlg = new messageDialog(this,"Error","IO Error: "+e.getMessage());
				msgDlg.setVisible(true);
			}
			catch(SAXException f)
			{
				messageDialog msgDlg = new messageDialog(this,"Error","Error: "+f.getMessage());
				msgDlg.setVisible(true);
			}
			*/
		}
		else if(item == this.mFileOpen)
		{
			FileDialog dialog = new FileDialog(this,"open toDo List");
			dialog.setVisible(true);

			String file = dialog.getDirectory()+dialog.getFile();
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
					this.mToDos.add(entries[i].getName());
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
			System.out.println("close");
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
			System.out.println("delete");
		}
		else
		{
			System.out.println("Nichts von alle dem");
		}
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
			this.mToDos.removeAll();
			for(int i=0;i<entries.length;i++)
			{
				this.mToDos.add( entries[i].getName() );
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
	public static void main(String[] args)
	{
		new toDoList();
	}
}
