/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfHund.toDoList;


import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import org.xml.sax.SAXException;
/**
 *
 * @author Philipp
 */
public class xmlReadTest {
    @BeforeClass
    public static void prepareXMLFile(){
        File rightXMLFile = new File("./rightXMLFile.xml");
        File wrongXMLFile = new File("./wrongXMLFile.xml");
        try{
            FileWriter rightXMLFileWriter = new FileWriter(rightXMLFile);
            FileWriter wrongXMLFileWriter = new FileWriter(wrongXMLFile);
            
            rightXMLFileWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><toDoList>"+
                                    "<toDoEntity id=\"92567585\"><active>1</active><name>aaaaa</name>"+
                                    "<desc>bbbbbb</desc><endDate>2016-03-20T15:30:00</endDate>"+
                                    "<criticalDate>2016-03-15T15:30:00</criticalDate></toDoEntity></toDoList>");
            wrongXMLFileWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><toDoList>"+
                                    "<toDoEntity id=\"92567585\"><name>wrongTest</name></toDoEntity>"+
                                    "<toDoEntity><title>wrongTitle</title><endDate>12.12.2015 15:30</endDate></toDoEntity></toDoList>");
            rightXMLFileWriter.close();
            wrongXMLFileWriter.close();
        }
        catch(IOException e){
            //Error
        }
        
    }
    
    @AfterClass
    public static void deleteFile(){
        File rightXMLFile = new File("./rightXMLFile.xml");
        File wrongXMLFile = new File("./wrongXMLFile.xml");
        rightXMLFile.delete();
        wrongXMLFile.delete();
    }
    
    @Test
    public void readData(){
        try {
            toDosSource rightSource = new toDosSource();
            rightSource.open("./rightXMLFile.xml");
            
            toDoEntry[] entries = rightSource.getAllEntries();
            GregorianCalendar expectedCriticalDate = new GregorianCalendar(2016, 2, 15, 15, 30);
            GregorianCalendar expectedEndDate = new GregorianCalendar(2016,2,20,15,30);
            GregorianCalendar xmlCriticalDate = new GregorianCalendar(),xmlEndDate=new GregorianCalendar();
            
            xmlCriticalDate.setTime(entries[0].getCriticalDate());
            xmlEndDate.setTime(entries[0].getEndDate());
            assertEquals("readed xml File should have one data set",entries.length, 1);
            assertEquals("get Name should be aaaaa",entries[0].getName().compareTo("aaaaa"), 0);
            assertEquals("expected: bbbbbb"+
                    " really: " +entries[0].getDesctiption().toString(),
                    entries[0].getDesctiption().compareTo("bbbbbb"), 0);
            assertTrue("expected: "+expectedCriticalDate.getTime().toString()+
                    " really: " +entries[0].getCriticalDate().toString(),
                    entries[0].getCriticalDate().equals(expectedCriticalDate.getTime()));
            assertTrue("expected:" +expectedEndDate.getTime().toString()+
                            " really: " +entries[0].getEndDate().toString()
                            ,entries[0].getEndDate().equals(expectedEndDate.getTime()));
            
            toDoEntry entry = rightSource.getEntry(92567585);
            assertEquals("4",entry.getName().compareTo("aaaaa"), 0);
            assertEquals("5",entry.getDesctiption().compareTo("bbbbbb"), 0);
            assertTrue("6",entry.getCriticalDate().equals(expectedCriticalDate.getTime()));
            assertTrue("7",entry.getEndDate().equals(expectedEndDate.getTime()));
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(xmlReadTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(xmlReadTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(xmlReadTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(xmlReadTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(xmlReadTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
