/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfHund.toDoList.components;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.Override;
import javax.swing.JComponent;
import java.util.ArrayList;
import gfHund.toDoList.toDoEntry;
/**
 *
 * @author Philipp
 */


public class toDoView extends JComponent{

  private static final long serialVersionUID = 1L;
    private ArrayList<toDoEntry> mList = new ArrayList<toDoEntry>();

    public toDoView()
    {
      super();
    }

    public void add(toDoEntry entry)
    {
        mList.add(entry);
    }

    public toDoEntry getItem(int index)
    {
      return this.mList.get(index);
    }

    public void remove(int index)
    {
      mList.remove(index);
    }

    public void removeAll()
    {
      for(int i=mList.size()-1;i>=0;i--)
      {
        remove(i);
      }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        int height = getHeight();
        int width = getWidth();

        g.setColor(Color.WHITE);
        //g.drawRect(0,0,getWidth(),getHeight());
        g.fillRect(0,0,getWidth(),getHeight());

        g.setColor(Color.GRAY);
        g.fillRect(0,0,getWidth(),25);

        g.setColor(Color.BLACK);
        int posY = 25;
        for(toDoEntry s : this.mList)
        {
            String title = s.getName();
            g.drawString(title,10,posY);
            posY += 15;
        }
    }
}
