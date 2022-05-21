package com.SIG.Control;

import com.SIG.GUI.InvoiceDesign;
import com.SIG.GUI.InvoiceDialog;
import com.SIG.GUI.LineDialog;
import com.SIG.model.Invoice;
import com.SIG.model.InvoicesTableModel;
import com.SIG.model.Line;
import com.SIG.model.LinesTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author Mohamed Youssef
 */
public class SIGcontroller implements ActionListener, ListSelectionListener {
    private InvoiceDesign frame;
    private InvoiceDialog invoiceDialog;
    private LineDialog lineDialog;

    public SIGcontroller(InvoiceDesign frame){
    this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand=e.getActionCommand();
        System.out.println("Action " + actionCommand);
        switch (actionCommand){
                case "Load File":
                    loadFile();
                break;
                case "Save File":
                    saveFile();
                break;
                case "Create New Invoice":
                    createNewInvoice();
                break;
                case "Delete Invoice":
                    deleteInvoice();
                break;
                case "Add New Item":
                    addNewItem();
                break;
                case "Delete Item":
                    deleteItem();
                break;
                case "createInvoiceConfirm":
                    createInvoiceConfirm();
                    break;
                case "createInvoiceCancel":
                    createInvoiceCancel();
                    break;
                case "createLineConfirm":
                    createLineConfirm();
                    break;
                case "createLineCancel":
                    createLineCancel();
                    break; 
    }
    }
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndex = frame.getInvoiceTable().getSelectedRow();
        if (selectedIndex != -1) {
        System.out.println("You have selection on raw No. "+ selectedIndex);
        Invoice currentInvoice = frame.getInvoices().get(selectedIndex);
        frame.getInvoiceNumLabel().setText(""+currentInvoice.getNum());
        frame.getInvoiceDateLabel().setText(currentInvoice.getDate());
        frame.getCustomerNameLabel().setText(currentInvoice.getCustomer());
        frame.getInvoiceTotalLabel().setText(""+currentInvoice.getInvoiceTotal());
        LinesTableModel linesTableModel = new LinesTableModel(currentInvoice.getLines());
        frame.getLineTable().setModel(linesTableModel);
        linesTableModel.fireTableDataChanged();
    }
    }
    private void loadFile(){
        JFileChooser fc = new JFileChooser();
        try {
        int result = fc.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
           File headerFile = fc.getSelectedFile();
           Path headerPath = Paths.get(headerFile.getAbsolutePath());
           List<String> headerLines = Files.readAllLines(headerPath);
           System.out.println("Invoices are successfully read");
           ArrayList<Invoice> invoicesArray = new ArrayList<> ();
           for (String headerLine : headerLines){
               String[] headerLineParts = headerLine.split(",");
               int invoiceNum = Integer.parseInt(headerLineParts[0]);
               String invoiceDate = (headerLineParts[1]);
               String customerName = (headerLineParts[2]);
               Invoice invoice= new Invoice(invoiceNum, invoiceDate, customerName);
               invoicesArray.add(invoice);
           }
           System.out.println("Invoices are comma separated successfully");
           result = fc.showOpenDialog(frame);
           if (result == JFileChooser.APPROVE_OPTION) {
           File lineFile = fc.getSelectedFile();
           Path linePath = Paths.get(lineFile.getAbsolutePath());
           List<String> lineLines = Files.readAllLines(linePath);
           System.out.println("Lines are successfully read");
           for (String lineLine : lineLines){
               String[] lineLineParts = lineLine.split(",");
               int invoiceNum = Integer.parseInt(lineLineParts[0]);
               String itemName = (lineLineParts[1]);
               double itemPrice = Double.parseDouble(lineLineParts[2]);
               int count = Integer.parseInt(lineLineParts[3]);
               Invoice inv = null;
               for (Invoice invoice : invoicesArray){
                   if (invoice.getNum() == invoiceNum){
                       inv = invoice;
                       break;
                   }
               }
               Line line = new Line(invoiceNum, itemName, itemPrice, count, inv);
               inv.getLines().add(line);
           }
           System.out.println("Lines are linked with invoices");
           }
           frame.setInvoices(invoicesArray);
           InvoicesTableModel invoicesTableModel = new InvoicesTableModel(invoicesArray);
           frame.setInvoicesTableModel(invoicesTableModel);
           frame.getInvoiceTable().setModel(invoicesTableModel);
           frame.getInvoicesTableModel().fireTableDataChanged();
        }
    } catch (IOException ex){
        ex.printStackTrace();
    }
    }

    private void saveFile() {
        ArrayList<Invoice> invoices = frame.getInvoices();
        String headers = "";
        String lines = "";
        for (Invoice invoice : invoices) {
            String invoiceCSV = invoice.getAsCSV();
            headers += invoiceCSV;
            headers += "\n";
            
            for (Line line : invoice.getLines()) {
            String lineCSV = line.getAsCSV();
            lines += lineCSV;
            lines += "\n";
            }
        }
        System.out.println("Changes Saved");
        try {
            JFileChooser fc = new JFileChooser();
            int result = fc.showSaveDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                FileWriter hfWriter = new FileWriter(headerFile);
                hfWriter.write(headers);
                hfWriter.flush();
                hfWriter.close();
                result = fc.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                File lineFile = fc.getSelectedFile();
                FileWriter lfWriter = new FileWriter(lineFile);
                lfWriter.write(lines);
                lfWriter.flush();
                lfWriter.close();
            }
            }
        } catch (Exception ex) {
                   }
    }

        
    private void createNewInvoice() { 
        invoiceDialog = new InvoiceDialog(frame);
        invoiceDialog.setVisible(true);
    }

    private void deleteInvoice(){
        int selectedRow = frame.getInvoiceTable().getSelectedRow();
        if (selectedRow != -1) {
            frame.getInvoices().remove(selectedRow);
            frame.getInvoicesTableModel().fireTableDataChanged();
        }
            }

    private void addNewItem(){
             lineDialog = new LineDialog(frame);
             lineDialog.setVisible(true);
    }

    private void deleteItem(){
        int selectedInv = frame.getInvoiceTable().getSelectedRow();
        int selectedRow = frame.getLineTable().getSelectedRow();
        if (selectedInv != -1 && selectedRow != -1) {
            Invoice invoice = frame.getInvoices().get(selectedInv);
            invoice.getLines().remove(selectedRow);
            LinesTableModel linesTableModel = new LinesTableModel(invoice.getLines());
            frame.getLineTable().setModel(linesTableModel);
            linesTableModel.fireTableDataChanged();
            frame.getInvoicesTableModel().fireTableDataChanged();
        }
            }

    private void createInvoiceConfirm() {
        String date = invoiceDialog.getInvoiceDateField().getText();
        String customer = invoiceDialog.getCustomerNameField().getText();
        int num = frame.getNextInvoiceNum();
        Invoice invoice = new Invoice(num, date, customer);
        frame.getInvoices().add(invoice);
        frame.getInvoicesTableModel().fireTableDataChanged();
        invoiceDialog.setVisible(false);
        invoiceDialog.dispose();
        invoiceDialog = null;
    }

    private void createInvoiceCancel() {
        invoiceDialog.setVisible(false);
        invoiceDialog.dispose();
        invoiceDialog = null;
    }

    private void createLineConfirm() {
        String item = lineDialog.getItemNameField().getText();
        String countStr = lineDialog.getItemCountField().getText();
        String priceStr = lineDialog.getItemPriceField().getText();
        int count = Integer.parseInt(countStr);
        double price = Double.parseDouble(priceStr);
        int selectedInvoice = frame.getInvoiceTable().getSelectedRow();
        if (selectedInvoice != -1) {
            Invoice invoice = frame.getInvoices().get(selectedInvoice);
            Line line = new Line(count, item, price, count, invoice);
            invoice.getLines().add(line);
            LinesTableModel linesTableModel = (LinesTableModel) frame.getLineTable().getModel();
            linesTableModel.fireTableDataChanged();
            frame.getInvoicesTableModel().fireTableDataChanged();
            
        }
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }

    private void createLineCancel() {
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }
    }
