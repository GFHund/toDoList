package gfHund.toDoList;
import java.awt.Component;
import java.awt.Color;
import java.awt.Graphics;
import java.lang.Override;
import javax.swing.*;

public class toDoListRenderer extends JLabel implements ListCellRenderer<Object>{

  //private ImageIcon mSelectedIcon;

  public toDoListRenderer(){
    setOpaque(true);
    java.net.URL imgURL = getClass().getResource("./dot.gif");
    /*
    if(imgURL != null){
      mSelectedIcon = new ImageIcon("./dot.gif");
    }
    else
    {
      mSelectedIcon = null;
    }
    */
  }

  @Override
  public Component getListCellRendererComponent(
                                  JList<?> list,
                                  Object value,
                                  int index,
                                  boolean isSelected,
                                  boolean cellHasFocus){
    setText(value.toString());
    toDoEntry entry = (toDoEntry)value;
    //if(isSelected && mSelectedIcon != null)
    if(isSelected)
    {
        //setIcon(mSelectedIcon);
        if(entry.getActive())
        {
          setBackground(new Color(0.0f,0.8f,0.0f));
          setForeground(new Color(0.0f,0.0f,0.0f));
        }
        else
        {
          setBackground(new Color(0.8f,0.0f,0.0f));
          setForeground(new Color(0.0f,0.0f,0.0f));
        }
    }
    else
    {
        if(entry.getActive())
        {
          setBackground(Color.GREEN);
          setForeground(Color.BLACK);
        }
        else
        {
          setBackground(Color.RED);
          setForeground(Color.BLACK);
        }
    }
    return this;
  }
}
