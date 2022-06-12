package com.example.invoiceservice.controller.pdf;

import com.example.invoiceservice.domain.EventsOrderLineItems;
import com.example.invoiceservice.domain.OrderEvent;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
@Data
@Builder
@Log4j2
public class GeneratePdfReport {
    public static ByteArrayInputStream invoiceGeneratePdf(OrderEvent invoice) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfPTable pdfTable = new PdfPTable(8);
            pdfTable.setWidthPercentage(100f);
            pdfTable.setWidths(new float[] {2.2f, 2f, 2.2f,2.2f, 2f, 2.2f, 2f, 2.2f});
            pdfTable.setSpacingBefore(10);


            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            PdfPCell cell = new PdfPCell();
            cell.setBackgroundColor(Color.white);
            cell.setPadding(5);

            cell.setPhrase(new com.lowagie.text.Phrase("Order Id", font));
            pdfTable.addCell(cell);
            cell.setPhrase(new com.lowagie.text.Phrase("User Name", font));
            pdfTable.addCell(cell);
            cell.setPhrase(new com.lowagie.text.Phrase("Order Date", font));
            pdfTable.addCell(cell);
            cell.setPhrase(new com.lowagie.text.Phrase("Order Status", font));
            pdfTable.addCell(cell);
            cell.setPhrase(new com.lowagie.text.Phrase("Sku Code", font));
            pdfTable.addCell(cell);
            cell.setPhrase(new com.lowagie.text.Phrase("Price", font));
            pdfTable.addCell(cell);
            cell.setPhrase(new com.lowagie.text.Phrase("Quantity", font));
            pdfTable.addCell(cell);
            cell.setPhrase(new com.lowagie.text.Phrase("Total Amount", font));
            pdfTable.addCell(cell);

            pdfTable.addCell(invoice.getOrderId());
            pdfTable.addCell(invoice.getUserName());
            pdfTable.addCell(invoice.getCreatedDate().toString());
            pdfTable.addCell(invoice.getOrderStatus().toString());
            for(EventsOrderLineItems orderLineItems : invoice.getOrderLineItems()){
                pdfTable.addCell(orderLineItems.getSkuCode());
                pdfTable.addCell(orderLineItems.getPrice().toString());
                pdfTable.addCell(orderLineItems.getQuantity().toString());
                pdfTable.addCell(orderLineItems.getTotalAmount().toString());
            }

            PdfWriter.getInstance(document, out);
            document.open();
            Paragraph p = new Paragraph("Invoice Order", font);
            p.setAlignment(Element.ALIGN_CENTER);
            Paragraph pSign = new Paragraph("Sign Order", font);
            pSign.setAlignment(Element.ALIGN_BOTTOM);
            Paragraph pSignDot = new Paragraph("................", font);
            pSignDot.setAlignment(Element.ALIGN_BASELINE);

            document.add(p);
            document.add(pdfTable);
            document.add(pSign);
            document.add(pSignDot);

            document.close();

        } catch (DocumentException ex) {

            log.error("Error occurred: {0}", ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
