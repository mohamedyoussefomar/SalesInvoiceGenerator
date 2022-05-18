package com.SIG.Control;

import com.SIG.GUI.Design;
import com.SIG.model.Invoice;
import com.SIG.model.InvoicesTableModel;
import com.SIG.model.Line;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;

/**
 * @author Mohamed Youssef
 */
public class SIGcontroller implements ActionListener {
    private Design frame;
    public SIGcontroller(Design frame){
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
                case "Save Changes":
                    saveChanges();
                break;
                case "Cancel Changes":
                    cancelChanges();
                break;
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
           frame.getMainTable().setModel(invoicesTableModel);
           frame.getInvoicesTableModel().fireTableDataChanged();
        }
    } catch (IOException ex){
        ex.printStackTrace();
    }
    }

    private void saveFile() {
    }

    private void createNewInvoice() {     
    }

    private void deleteInvoice(){
            }

    private void saveChanges(){
            }

    private void cancelChanges(){
            }
}
