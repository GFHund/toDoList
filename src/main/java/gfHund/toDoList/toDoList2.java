/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfHund.toDoList;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 *
 * @author Philipp
 */
public class toDoList2 extends javax.swing.JFrame {

    private toDosSource mSource;
    //private Vector<toDoEntry> mToDoEntries;
    private DefaultListModel<toDoEntry> mToDoEntries;
    private int mCurIndex;
    private toDoListRenderer mToDoCellRenderer = new toDoListRenderer();
    private addEntityDialog mAddEntityDlg;
    /**
     * Creates new form toDoList2
     */
    public toDoList2() {
        this.mToDoEntries = new DefaultListModel();
        initComponents();
        //this.mToDoEntries = new Vector<toDoEntry>();
        try
        {
            this.mSource = new toDosSource();
        }
        catch(Exception e)
        {
            messageDialog msgDlg = new messageDialog(this,"Error","Error: "+e.getMessage());
            msgDlg.setVisible(true);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        mToDos = new javax.swing.JList();
        mNameLabel = new javax.swing.JLabel();
        mNameTextField = new javax.swing.JTextField();
        mDescLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        mDescTextArea = new javax.swing.JTextArea();
        mCriticalDateLabel = new javax.swing.JLabel();
        mCriticalDateTextField = new javax.swing.JTextField();
        mEnddateLabel = new javax.swing.JLabel();
        mEndDateTextField = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mNewFile = new javax.swing.JMenuItem();
        mFileOpen = new javax.swing.JMenuItem();
        mSave = new javax.swing.JMenuItem();
        mClose = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mAddEntity = new javax.swing.JMenuItem();
        mDeleteEntity = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mToDos.setModel(mToDoEntries);
        mToDos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        mToDos.setCellRenderer(mToDoCellRenderer);
        mToDos.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                mToDosValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(mToDos);

        mNameLabel.setText("Name");

        mNameTextField.setEditable(false);

        mDescLabel.setText("Description");

        mDescTextArea.setEditable(false);
        mDescTextArea.setColumns(20);
        mDescTextArea.setRows(5);
        jScrollPane2.setViewportView(mDescTextArea);

        mCriticalDateLabel.setText("Critical Date");

        mCriticalDateTextField.setEditable(false);

        mEnddateLabel.setText("End Date");

        mEndDateTextField.setEditable(false);

        jMenu1.setText("File");

        mNewFile.setText("New");
        mNewFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mNewFileActionPerformed(evt);
            }
        });
        jMenu1.add(mNewFile);

        mFileOpen.setText("Open");
        mFileOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mFileOpenActionPerformed(evt);
            }
        });
        jMenu1.add(mFileOpen);

        mSave.setText("Save");
        mSave.setToolTipText("");
        mSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mSaveActionPerformed(evt);
            }
        });
        jMenu1.add(mSave);

        mClose.setText("Close");
        mClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mCloseActionPerformed(evt);
            }
        });
        jMenu1.add(mClose);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Entities");

        mAddEntity.setText("add Entity");
        mAddEntity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mAddEntityActionPerformed(evt);
            }
        });
        jMenu2.add(mAddEntity);

        mDeleteEntity.setText("Delete Entity");
        mDeleteEntity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mDeleteEntityActionPerformed(evt);
            }
        });
        jMenu2.add(mDeleteEntity);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(mNameLabel)
                    .addComponent(mNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mDescLabel)
                    .addComponent(jScrollPane2)
                    .addComponent(mCriticalDateLabel)
                    .addComponent(mCriticalDateTextField)
                    .addComponent(mEnddateLabel)
                    .addComponent(mEndDateTextField))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(mNameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mDescLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mCriticalDateLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mCriticalDateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mEnddateLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mEndDateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 26, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void mFileOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mFileOpenActionPerformed
        // TODO add your handling code here:
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
                //this.mToDos.add(entries[i].getName());
                this.mToDoEntries.addElement(entries[i]);
            }
        }
        catch(ParseException e)
        {
            messageDialog msgDlg = new messageDialog(this,"Error","Error: "+e.getMessage());
            msgDlg.setVisible(true);
        }
        
        mAddEntityDlg = new addEntityDialog(this,"Add Entity",this.mSource);
        mAddEntityDlg.setVisible(false);
        mAddEntityDlg.setAddEntityDialogEventListener(new addEntityDialogEvent() {

            public void entityDlgListener() {
                updateList();
            }
        });
    }//GEN-LAST:event_mFileOpenActionPerformed

    private void mNewFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mNewFileActionPerformed
        // TODO add your handling code here:
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
    }//GEN-LAST:event_mNewFileActionPerformed

    private void mSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSaveActionPerformed
        // TODO add your handling code here:
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
    }//GEN-LAST:event_mSaveActionPerformed

    private void mCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mCloseActionPerformed
        // TODO add your handling code here:
        //System.out.println("close");
        logging.addLog("Close Application");
        System.exit(0);
    }//GEN-LAST:event_mCloseActionPerformed

    private void mAddEntityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mAddEntityActionPerformed
        // TODO add your handling code here:
        //System.out.println("add");
        if(!mSource.isOpen())
        {
            messageDialog msgDlg = new messageDialog(this,"Error","Error: You must open a File or create one to do this");
            msgDlg.setVisible(true);
            return;
        }
        mAddEntityDlg.setVisible(true);
        
        /*
        ActionListener listener = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                updateList();
            }
        };
        */
        //addEntityDlg.setOkActionListener(listener);
        //addEntityDlg.setVisible(true);

        /*
        toDoEntry data = addEntityDlg.getData();
        this.mToDos.add(data.getName());
        mSource.createEntry(data);
        */
    }//GEN-LAST:event_mAddEntityActionPerformed

    private void mDeleteEntityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mDeleteEntityActionPerformed
        // TODO add your handling code here:
        //System.out.println("delete");
        if(mCurIndex != -1)
        {
            /*
            String listItem;
            listItem = this.mToDos.getItem(this.mCurIndex);
            int hash = listItem.hashCode();
            */
            toDoEntry deleteItem = mToDoEntries.get(mCurIndex);
            int hash = deleteItem.getName().hashCode();
            boolean success = this.mSource.deleteEntry(hash);
            if(!success)
            {
                messageDialog msgDlg = new messageDialog(this,"Error","Error: Could not delete Entity. Please mark the item that you want to delete");
                msgDlg.setVisible(true);
            }
            else
            {  
                this.mToDos.remove(this.mCurIndex);  
            }
            
        }
        else
        {
            messageDialog msgDlg = new messageDialog(this,"Error","Error: You must choose an entity");
            msgDlg.setVisible(true);
        }
    }//GEN-LAST:event_mDeleteEntityActionPerformed

    private void updateList()
    {
        //aaaaa
        try
        {
            toDoEntry[] entries = this.mSource.getAllEntries();
            //Sort entries for date
            /*
            for(int i = 0;i<entries.length;i++)
            {
                
                for(int k=0;k<entries.length-1;k++)
                {
                    if(entries[k] == null){
                        continue;
                    }
                    if( entries[k].getEndDate().after( entries[k+1].getEndDate() ) )
                    {
                        toDoEntry temp = entries[k];
                        entries[k] = entries[k+1];
                        entries[k+1] = temp;
                    }
                }
            }
            */
            //this.mCustomToDo.removeAll();
            this.mToDoEntries.removeAllElements();
            

            for(int i=0;i<entries.length;i++)
            {
                //oDos.add( entries[i].getName() );
               
                //this.mCustomToDo.add( entries[i]);
                this.mToDoEntries.addElement(entries[i]);
                
            }
        }
        catch(ParseException e)
        {
                messageDialog msgDlg = new messageDialog(this,"Error","Error: "+e.getMessage());
                msgDlg.setVisible(true);
        }
        //mToDos.updateUI();
    }
    
    private void mToDosValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_mToDosValueChanged
        // TODO add your handling code here:
        this.mCurIndex = mToDos.getSelectedIndex();
        toDoEntry entry = this.mToDoEntries.get(mCurIndex);
        this.mNameTextField.setText(entry.getName());
        this.mDescTextArea.setText(entry.getDesctiption());
        if(entry.getCriticalDate() != null){
            this.mCriticalDateTextField.setText(entry.getCriticalDate().toString());
            this.mEndDateTextField.setText(entry.getEndDate().toString());
        }
    }//GEN-LAST:event_mToDosValueChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(toDoList2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(toDoList2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(toDoList2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(toDoList2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new toDoList2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem mAddEntity;
    private javax.swing.JMenuItem mClose;
    private javax.swing.JLabel mCriticalDateLabel;
    private javax.swing.JTextField mCriticalDateTextField;
    private javax.swing.JMenuItem mDeleteEntity;
    private javax.swing.JLabel mDescLabel;
    private javax.swing.JTextArea mDescTextArea;
    private javax.swing.JTextField mEndDateTextField;
    private javax.swing.JLabel mEnddateLabel;
    private javax.swing.JMenuItem mFileOpen;
    private javax.swing.JLabel mNameLabel;
    private javax.swing.JTextField mNameTextField;
    private javax.swing.JMenuItem mNewFile;
    private javax.swing.JMenuItem mSave;
    private javax.swing.JList mToDos;
    // End of variables declaration//GEN-END:variables
}
