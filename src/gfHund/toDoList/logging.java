package gfHund.toDoList;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;

class logging
{
  public static void addLog(String message)
  {
    File logFile = new File("./log.txt");
    try
    {
      logFile.createNewFile();
      FileWriter logWriter = new FileWriter(logFile);
      Calendar timeCalendar = Calendar.getInstance();
      Date timeDate = timeCalendar.getTime();
      SimpleDateFormat parser = new SimpleDateFormat("dd.MM.yyyy HH:mm");
      String curDate = parser.format(timeDate);
      logWriter.write("[" + curDate + "] " + message+"\n");
    }
    catch(IOException e)
    {
      //aaaaa
    }
  }
}
