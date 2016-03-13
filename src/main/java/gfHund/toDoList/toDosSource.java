package gfHund.toDoList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.awt.event.*;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.validation.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import java.io.*;


/*
This Class reads and Writes to a XML File that contains the toDo list
*/
class toDosSource// implements ActionListener
{
	private String mFile;
	private Transformer mTransformer;
	private DocumentBuilder mBuilder;
	private Document mXmlDocument;
	/*
	constructor
	*/
	public toDosSource() throws ParserConfigurationException, TransformerConfigurationException, SAXException
	{
		this.mFile = "";
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		mTransformer = transformerFactory.newTransformer();

		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		File schemaFile = new File("./toDo_Schema.xsd");
		Schema toDoSchema = schemaFactory.newSchema(schemaFile);
		documentBuilderFactory.setSchema(toDoSchema);
		/**/

		this.mBuilder = documentBuilderFactory.newDocumentBuilder();
	}
        //-----------------------------------------------
	// throws IOException,SAXException
	/*
    creates a xml File
     */
	public void createXMLFile(String file) throws TransformerConfigurationException, TransformerException
	{
		//System.out.println(file);
		this.mXmlDocument = this.mBuilder.newDocument();
		Node root = this.mXmlDocument.createElement("toDoList");
		this.mXmlDocument.appendChild(root);
		//Writes a xml File
		DOMSource source = new DOMSource(this.mXmlDocument);
		StreamResult result = new StreamResult(new File(file));
		this.mTransformer.transform(source,result);

		this.mFile = file;
		//File xmlFile = new File(file);
		//xmlFile.createNewFile();
		//FileWriter write = new FileWriter(xmlFile);
		//write.write("<toDoList></toDoList>");
		//write.close();


	}
        //-----------------------------------------------
	/*
	checks if the xml file is open
	*/
	public boolean isOpen()
	{
		return !this.mFile.isEmpty();
	}
        //-----------------------------------------------
	/*
	open a xml file
	*/
	public void open(String file) throws IOException,SAXException
	{
		this.mFile = file;
		File xmlFile = new File(file);
		mXmlDocument = mBuilder.parse(file);

		NodeList toDoEntryNodes = this.mXmlDocument.getElementsByTagName("toDoEntity");
		Element element;
		for(int i=0;i<toDoEntryNodes.getLength();i++)
		{
			try{
				element = (Element) toDoEntryNodes.item(i);
				element.setIdAttribute("id",true);
			}
			catch(DOMException e)
			{
				//System.out.println("Error: " + e.getMessage());
				logging.addLog("Error: "+e.getMessage());
			}
		}

	}
        //-----------------------------------------------
	/*
	save a xml File
	*/
	public void save() throws TransformerConfigurationException, TransformerException
	{
		DOMSource source = new DOMSource(this.mXmlDocument);
		StreamResult result = new StreamResult(new File(this.mFile));
		this.mTransformer.transform(source,result);
	}
        //-----------------------------------------------
	/*
	reads all datasets from xml File
	*/
	public toDoEntry[] getAllEntries()throws ParseException
	{
		NodeList children, toDoEntryNodes = this.mXmlDocument.getElementsByTagName("toDoEntity");
		toDoEntry[] entries = new toDoEntry[toDoEntryNodes.getLength()];
		String strActive,strName,strDesc,strEndDate,strCriticalDate;
		boolean active;
		Date endDate,criticalDate;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");//new SimpleDateFormat("dd.MM.yyyy HH:mm");

		for(int i = 0;i<toDoEntryNodes.getLength();i++)
		{
			children = toDoEntryNodes.item(i).getChildNodes();
			strActive = children.item(0).getFirstChild().getNodeValue();
			strName = children.item(1).getFirstChild().getNodeValue();
			strDesc = children.item(2).getFirstChild().getNodeValue();
			
                        if(children.getLength() > 3) {
                            strEndDate = children.item(3).getFirstChild().getNodeValue();
                            strCriticalDate = children.item(4).getFirstChild().getNodeValue();
                            
                            endDate = dateFormat.parse(strEndDate);
                            criticalDate = dateFormat.parse(strCriticalDate);
                            if(strActive.charAt(0) == '1')
				active = true;
                            else
                                    active = false;

                            entries[i] = new toDoEntry(active,strName,strDesc,endDate,criticalDate);
                        }
                        else{
                            if(strActive.charAt(0) == '1')
				active = true;
                            else
                                active = false;
                            entries[i] = new toDoEntry(active,strName,strDesc);
                        }
                        
			/*ToDo: strActive in true oder false unsetzen und EndDate sowie crititcalDate in Date umsetzen*/
			
		}
		return entries;
		/*
		NodeList toDoXMLEntitys = this.mXmlDocument.getChildNodes();
		NodeList infoEntitys;
		Node infoEntity;

		for(int i=0;i<toDoXMLEntitys.getLength();i++)
		{
			infoEntitys = toDoXMLEntitys.item(i).getChildNodes();
			for(int k=0;k<infoEntitys.getLength();k++)
			{
				System.out.println(infoEntitys.item(k).getNodeName());
				System.out.println(infoEntitys.item(k).getNodeValue());
			}
		}
		*/
	}
        //-----------------------------------------------
	/*
	creates a new dataset
	*/
	public void createEntry(toDoEntry data)
	{
		SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");//new SimpleDateFormat("dd.MM.yyyy HH:mm");
		Element dataNode = this.mXmlDocument.createElement("toDoEntity");

		Element active = this.mXmlDocument.createElement("active");
		Element name = this.mXmlDocument.createElement("name");
		Element desc = this.mXmlDocument.createElement("desc");
		Element endDate = this.mXmlDocument.createElement("endDate");
		Element criticalDate = this.mXmlDocument.createElement("criticalDate");

		Text activeText;
		if(data.getActive())
			activeText = this.mXmlDocument.createTextNode("1");
		else
			activeText = this.mXmlDocument.createTextNode("0");

		Text nameText = this.mXmlDocument.createTextNode( data.getName() );
		Text descText = this.mXmlDocument.createTextNode( data.getDesctiption() );
		
		this.mXmlDocument.getFirstChild().appendChild(dataNode);
		//insert childs to <toDoEntity>
		dataNode.appendChild(active);
		dataNode.appendChild(name);
		dataNode.appendChild(desc);
                
		active.appendChild(activeText);
		name.appendChild(nameText);
		desc.appendChild(descText);
                
                if(data.getEndDate() != null || data.getCriticalDate() != null)
                {
                    Text endDateText = this.mXmlDocument.createTextNode( dateParser.format( data.getEndDate() ) );
                    Text criticalDateText = this.mXmlDocument.createTextNode( dateParser.format( data.getCriticalDate() ) );
                    dataNode.appendChild(endDate);
                    dataNode.appendChild(criticalDate);
                    endDate.appendChild(endDateText);
                    criticalDate.appendChild(criticalDateText);
                }
                

		int id = data.getName().hashCode();

		dataNode.setAttribute( "id", String.valueOf(id) );
		try{
			dataNode.setIdAttribute("id",true);
		}
		catch(DOMException e)
		{
			//System.out.println(e.getMessage());
			logging.addLog("Error: "+e.getMessage());
		}
	}
        //-----------------------------------------------
	/*
	delete an Entry from the toDoList.
	Returns false if the entity could not deleted
	*/
	public boolean deleteEntry(int id)
	{
		String idString = String.valueOf(id);
		//System.out.println(idString + " " + id);
		Node child = this.mXmlDocument.getElementById(idString);
		Node parent = child.getParentNode();
		if(child == null)
		{
			return false;
		}
		try
		{
			parent.removeChild(child);
			//this.mXmlDocument.removeChild(child);
		}
		catch(DOMException e)
		{
			//System.out.println(e.getMessage());
			logging.addLog("Error: "+e.getMessage());
			return false;
		}
		return true;
	}
        //-----------------------------------------------
	/*
	gets one Entry of the xml file, which id equals hash
	*/
	public toDoEntry getEntry(int hash)
	{
		String idString = String.valueOf(hash);
		Node child = this.mXmlDocument.getElementById(idString);
		if(child == null)
		{
			return null;
		}
		boolean active=false;
		String title,desc,endDateStr,criticalDateStr;
		Element activeNode = (Element)child.getFirstChild();
		String strActive = ((Text)activeNode.getFirstChild()).getWholeText();
		if(strActive.charAt(0) == '1') active = true;
		Element titleNode = (Element) activeNode.getNextSibling();
		title = ((Text)titleNode.getFirstChild()).getWholeText();
		Element descNode = (Element) titleNode.getNextSibling();
		desc = ((Text)descNode.getFirstChild()).getWholeText();
		Element endDateNode = (Element) descNode.getNextSibling();
		endDateStr = ((Text)endDateNode.getFirstChild()).getWholeText();
		Element criticalDateNode = (Element) endDateNode.getNextSibling();
		criticalDateStr = ((Text)criticalDateNode.getFirstChild()).getWholeText();
		SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try
		{
			Date criticalDate = dateParser.parse(criticalDateStr);
			Date endDate =dateParser.parse(endDateStr);
			toDoEntry data = new toDoEntry(active,title,desc,endDate,criticalDate);
			return data;
		}
		catch(ParseException e)
		{
			return null;
		}

		//int intActive = Integer.valueOf();
	}
	/*
	public byte[] getHash(String text)
	{
		byte[] bytes = text.getBytes
	}
	*/

	/*
	public void actionPerformed(ActionEvent e)
	{
		Object temp = e.getSource();
		System.out.println(temp.toString());
	}
	*/
        //-----------------------------------------------
	/*
	gets the File Name of the opened XML File
		*/
	public String getFileName()
	{
		File file = new File(this.mFile);
		return file.getName();
	}
        //-----------------------------------------------
	/*
  gets the whole file path
   */
	public String getFilePath()
	{
		return this.mFile;
	}
}
