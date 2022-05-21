package com.SIG.GUI;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class LineDialog extends JDialog{
    private JTextField itemNameField;
    private JTextField itemCountField;
    private JTextField itemPriceField;
    private JLabel itemNameLabel;
    private JLabel itemCountLabel;
    private JLabel itemPriceLabel;
    private JButton confirmButton;
    private JButton cancelButton;
    
    public LineDialog(InvoiceDesign frame) {
        itemNameField = new JTextField(20);
        itemNameLabel = new JLabel("Item Name");
        
        itemCountField = new JTextField(20);
        itemCountLabel = new JLabel("Item Count");
        
        itemPriceField = new JTextField(20);
        itemPriceLabel = new JLabel("Item Price");
        
        confirmButton = new JButton("Confirm");
        cancelButton = new JButton("Cancel");
        
        confirmButton.setActionCommand("createLineConfirm");
        cancelButton.setActionCommand("createLineCancel");
        
        confirmButton.addActionListener(frame.getController());
        cancelButton.addActionListener(frame.getController());
        setLayout(new GridLayout(4, 2));
        
        add(itemNameLabel);
        add(itemNameField);
        add(itemCountLabel);
        add(itemCountField);
        add(itemPriceLabel);
        add(itemPriceField);
        add(confirmButton);
        add(cancelButton);
        
        pack();
    }

    public JTextField getItemNameField() {
        return itemNameField;
    }

    public JTextField getItemCountField() {
        return itemCountField;
    }

    public JTextField getItemPriceField() {
        return itemPriceField;
    }
}
