//package com.cars24.ai_loan_assistance.util;
//
//import com.cars24.ai_loan_assistance.data.entities.EmiEntity;
//import com.cars24.ai_loan_assistance.data.entities.LoanEntity;
//import com.lowagie.text.*;
//import com.lowagie.text.pdf.PdfPTable;
//import com.lowagie.text.pdf.PdfWriter;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.util.List;
//
//public class PdfGeneratorUtil {
//
//    private static final String PDF_DIRECTORY = "src/main/resources/static/pdfs/";
//
//    public static String generateLoanPdf(String userName, LoanEntity loan, List<EmiEntity> emis) {
//        String sanitizedUserName = userName.replaceAll("\\s+", "_"); // Replace spaces with _
//        String pdfFileName = sanitizedUserName + ".pdf";
//        String pdfFilePath = PDF_DIRECTORY + pdfFileName;
//
//        try {
//            // Ensure directory exists
//            File directory = new File(PDF_DIRECTORY);
//            if (!directory.exists()) {
//                directory.mkdirs();
//            }
//
//            Document document = new Document();
//            PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
//            document.open();
//
//            // Title
//            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
//            Paragraph title = new Paragraph("Loan Statement", titleFont);
//            title.setAlignment(Paragraph.ALIGN_CENTER);
//            document.add(title);
//            document.add(new Paragraph("\n"));
//
//            // Loan Details
//            document.add(new Paragraph("Loan Type: " + loan.getType()));
//            document.add(new Paragraph("Disbursed Date: " + loan.getDisbursedDate()));
//            document.add(new Paragraph("Principal Amount: ₹" + loan.getPrincipal()));
//            document.add(new Paragraph("Interest Rate: " + loan.getInterest() + "%"));
//            document.add(new Paragraph("Tenure: " + loan.getTenure() + " months"));
//
//            document.add(new Paragraph("\nEMI Details:\n"));
//
//            // EMI Table
//            PdfPTable table = new PdfPTable(5);
//            table.setWidthPercentage(100);
//            table.addCell("Due Date");
//            table.addCell("EMI Amount");
//            table.addCell("Status");
//
//
//
//            for (EmiEntity emi : emis) {
//                table.addCell(emi.getDueDate().toString());
//                table.addCell("₹" + emi.getEmiAmount().toString());
//                table.addCell(emi.getStatus().name());
//
//
//            }
//
//            document.add(table);
//            document.close();
//
//            return "/pdfs/" + pdfFileName;  // Return relative path for frontend access
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
