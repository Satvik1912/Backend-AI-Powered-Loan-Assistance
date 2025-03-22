package com.cars24.ai_loan_assistance.util;

import com.cars24.ai_loan_assistance.data.entities.EmiEntity;
import com.cars24.ai_loan_assistance.data.entities.LoanEntity;
import com.cars24.ai_loan_assistance.data.entities.enums.EmiStatus;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.net.URL;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class PdfGeneratorUtil {

    private static final String PDF_DIRECTORY = "src/main/resources/static/pdfs/";

    public static String generateEmiPdf(String userName, LoanEntity loan, List<EmiEntity> emis) {
        String sanitizedUserName = userName.replaceAll("\\s+", "_"); // Replace spaces with "_"
        String pdfFileName = sanitizedUserName + "_emi_schedule.pdf";
        String pdfFilePath = PDF_DIRECTORY + pdfFileName;


        try {
            // Ensure directory exists
            File directory = new File(PDF_DIRECTORY);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Create a PDF document
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
            document.open();
            Image logo = Image.getInstance("src/main/resources/images/loans.png");

            // Step 3: Resize the Image (Optional)
            logo.scaleToFit(100, 50); // Width = 100, Height = 50

            // Step 4: Set the Position (Top-Left)
            logo.setAbsolutePosition(30, document.getPageSize().getHeight() - 70); // X = 30, Y = top of the page

            // Step 5: Add Image to the Document
            document.add(logo);

            // Title
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("EMI Schedule", titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);



            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Loan Details:",titleFont));
            //document.add(new Paragraph("Loan Status: " + loan.getStatus()));
            String formattedLoanType = loan.getType().name().replace("_", " ").toLowerCase();
            formattedLoanType = Character.toUpperCase(formattedLoanType.charAt(0)) + formattedLoanType.substring(1);
            document.add(new Paragraph("Loan Type: " + formattedLoanType));
            document.add(new Paragraph("Disbursed Date: " + loan.getDisbursedDate()));
            document.add(new Paragraph("Principal Amount: ₹" + loan.getPrincipal()));
            document.add(new Paragraph("Tenure: " + loan.getTenure() + " months"));
            document.add(new Paragraph("Interest Rate: " + loan.getInterest() + "%"));
            document.add(new Paragraph("\n"));

            // EMI Table
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            //table.addCell("EMI ID");
            table.addCell("EMI Amount");
            table.addCell("Due Date");
            table.addCell("Status");

            // Iterate over EMI list and add to the table
            for (EmiEntity emi : emis) {
                //table.addCell(String.valueOf(emi.getEmiId()));
                table.addCell("₹" + emi.getEmiAmount());
                table.addCell(emi.getDueDate().toString());
                table.addCell(emi.getStatus().name()); // Convert enum to string
            }

            document.add(table);
            document.close();

            return "/pdfs/" + pdfFileName; // Return relative path for frontend access

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
