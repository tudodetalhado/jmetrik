/*
 * Copyright (c) 2012 Patrick Meyer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.itemanalysis.jmetrik.stats.irt.linking;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.itemanalysis.jmetrik.model.VariableNameListModel;
import com.itemanalysis.jmetrik.sql.DataTableName;
import com.itemanalysis.psychometrics.data.VariableName;
import com.itemanalysis.squiggle.base.SelectQuery;
import com.itemanalysis.squiggle.base.Table;
import org.apache.log4j.Logger;

import javax.swing.*;

public class IrtLinkingItemPairDialog extends JDialog{

    private Connection conn = null;

    static Logger logger = Logger.getLogger("jmetrik-logger");

    private DataTableName tableX = null;

    private DataTableName tableY = null;

    private VariableNameListModel xListModel = null;

    private VariableNameListModel yListModel = null;

    private DefaultListModel xyListModel = null;

    private boolean canRun = false;

    // Variables declaration - do not modify
    private JPanel bottomPanel;
    private JPanel buttonPanel;
    private JButton cancelButton;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JButton okButton;
    private JButton selectButton;
    private JButton unselectAllButton;
    private JButton unselectButton;
    private JList xList;
    private JList xyList;
    private JList yList;
    // End of variables declaration

    /** Creates new form IrtEquatingItemPairDialog */
    public IrtLinkingItemPairDialog(JDialog parent, Connection conn, DataTableName tableX, DataTableName tableY) {
        super(parent, "X-Y Item Pairs", true);
        this.conn = conn;
        this.tableX = tableX;
        this.tableY = tableY;
        initComponents();

        xListModel = new VariableNameListModel();
        xList.setModel(xListModel);
        populateList(tableX, xListModel);

        yListModel = new VariableNameListModel();
        yList.setModel(yListModel);
        populateList(tableY, yListModel);

        xyListModel = new DefaultListModel();
        xyList.setModel(xyListModel);

        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        setLocationRelativeTo(parent);
    }

    public boolean canRun(){
        return canRun;
    }

    public int numberOfPairs(){
        return xyListModel.getSize();
    }

    public Object[] getSelectedPairs(){
        return xyListModel.toArray();
    }

    private void populateList(DataTableName table, VariableNameListModel listModel){
        try{
            Table sqlTable = new Table(table.getNameForDatabase());
            SelectQuery query = new SelectQuery();
            VariableName name = new VariableName("name");//must be in item parameter table
            query.addColumn(sqlTable, name.nameForDatabase());
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(query.toString());

            VariableName tempName = null;
            int index = 0;
            while(rs.next()){
                tempName = new VariableName(rs.getString(name.nameForDatabase()));
                tempName.setIndex(index);
                index++;
                listModel.addElement(tempName);
            }
            rs.close();
            stmt.close();
        }catch(SQLException ex){
            logger.fatal(ex.getMessage(), ex);
            JOptionPane.showMessageDialog(IrtLinkingItemPairDialog.this,
                    "Problem reading database.",
                    "SQException",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
        GridBagConstraints gridBagConstraints;

        jPanel1 = new JPanel();
        jScrollPane1 = new JScrollPane();
        xList = new JList();
        jPanel2 = new JPanel();
        jScrollPane2 = new JScrollPane();
        yList = new JList();
        jPanel3 = new JPanel();
        jScrollPane3 = new JScrollPane();
        xyList = new JList();
        buttonPanel = new JPanel();
        selectButton = new JButton();
        unselectButton = new JButton();
        unselectAllButton = new JButton();
        bottomPanel = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new GridBagLayout());

        jPanel1.setBorder(BorderFactory.createTitledBorder("Form X Items"));
        jPanel1.setPreferredSize(new Dimension(125, 350));
        jPanel1.setLayout(new GridLayout(1, 0));

        xList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(xList);

        jPanel1.add(jScrollPane1);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 20;
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setBorder(BorderFactory.createTitledBorder("Form Y Items"));
        jPanel2.setPreferredSize(new Dimension(125, 350));
        jPanel2.setLayout(new GridLayout(1, 0));

        yList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(yList);

        jPanel2.add(jScrollPane2);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridheight = 20;
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new Insets(5, 0, 5, 5);
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel3.setBorder(BorderFactory.createTitledBorder("X-Y Item Pairs"));
        jPanel3.setPreferredSize(new Dimension(150, 350));
        jPanel3.setLayout(new GridLayout(1, 0));

        xyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(xyList);

        jPanel3.add(jScrollPane3);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 20;
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new Insets(5, 0, 5, 5);
        getContentPane().add(jPanel3, gridBagConstraints);

        buttonPanel.setPreferredSize(new Dimension(55, 350));
        buttonPanel.setLayout(new GridBagLayout());

        selectButton.setText(">");
        selectButton.setMaximumSize(new Dimension(52, 28));
        selectButton.setMinimumSize(new Dimension(50, 28));
        selectButton.setPreferredSize(new Dimension(50, 28));
        selectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                selectButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(0, 0, 5, 5);
        buttonPanel.add(selectButton, gridBagConstraints);

        unselectButton.setText("<");
        unselectButton.setMaximumSize(new Dimension(52, 28));
        unselectButton.setMinimumSize(new Dimension(50, 28));
        unselectButton.setPreferredSize(new Dimension(50, 28));
        unselectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                unselectButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new Insets(0, 0, 5, 5);
        buttonPanel.add(unselectButton, gridBagConstraints);

        unselectAllButton.setText("<<");
        unselectAllButton.setMaximumSize(new Dimension(52, 28));
        unselectAllButton.setMinimumSize(new Dimension(50, 28));
        unselectAllButton.setPreferredSize(new Dimension(50, 28));
        unselectAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                unselectAllButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new Insets(0, 0, 5, 5);
        buttonPanel.add(unselectAllButton, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 20;
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new Insets(5, 0, 5, 5);
        getContentPane().add(buttonPanel, gridBagConstraints);

        bottomPanel.setLayout(new GridBagLayout());

        okButton.setText("OK");
        okButton.setToolTipText("OK");
        okButton.setMaximumSize(new Dimension(65, 28));
        okButton.setMinimumSize(new Dimension(65, 28));
        okButton.setPreferredSize(new Dimension(65, 28));
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        bottomPanel.add(okButton, gridBagConstraints);

        cancelButton.setText("Cancel");
        cancelButton.setToolTipText("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(5, 0, 5, 5);
        bottomPanel.add(cancelButton, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        getContentPane().add(bottomPanel, gridBagConstraints);

        pack();
    }// </editor-fold>

    private void selectButtonActionPerformed(ActionEvent evt) {
        int selectedX = xList.getSelectedIndex();
        int selectedY = yList.getSelectedIndex();
        if(selectedX!=-1 && selectedY!=-1){
            VariableName xName = (VariableName)xListModel.getElementAt(selectedX);
            VariableName yName = (VariableName)yListModel.getElementAt(selectedY);
            LinkingItemPair pair = new LinkingItemPair(xName, yName);
            xyListModel.addElement(pair);
            xListModel.removeElement((VariableName)xListModel.getElementAt(selectedX));
            yListModel.removeElement((VariableName)yListModel.getElementAt(selectedY));
        }

    }

    private void unselectButtonActionPerformed(ActionEvent evt) {
        int selected = xyList.getSelectedIndex();
        if(selected!=-1){
            LinkingItemPair pair = (LinkingItemPair)xyListModel.getElementAt(selected);
            VariableName x = pair.getXVariable();
            VariableName y = pair.getYVariable();
            xListModel.addElement(x);
            yListModel.addElement(y);
            xyListModel.remove(selected);
        }
    }

    private void unselectAllButtonActionPerformed(ActionEvent evt) {
        int size = xyList.getModel().getSize();
        LinkingItemPair pair = null;
        if(size>0){
            for(int i=0;i<size;i++){
                pair = (LinkingItemPair)xyListModel.getElementAt(i);
                VariableName x = pair.getXVariable();
                VariableName y = pair.getYVariable();
                xListModel.addElement(x);
                yListModel.addElement(y);
            }
            xyListModel.clear();
        }
    }

    private void okButtonActionPerformed(ActionEvent evt) {
        if(!xyListModel.isEmpty()){
            canRun=true;
            setVisible(false);
        }else{
            JOptionPane.showMessageDialog(IrtLinkingItemPairDialog.this,
                    "You must select at least one item pair.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        setVisible(false);
    }

}
