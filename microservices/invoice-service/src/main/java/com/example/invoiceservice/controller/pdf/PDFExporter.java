package com.example.invoiceservice.controller.pdf;

import com.example.invoiceservice.domain.EventsOrderLineItems;
import com.example.invoiceservice.domain.OrderEvent;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Deprecated(since = "0.0.1")
public class PDFExporter {

    private OrderEvent orderEvent;

    private void writeTableHeader(PdfPTable pdfTable){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.blue);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.white);
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

    }

    private void writeTableData(PdfPTable pdfPTable){
        pdfPTable.addCell(orderEvent.getOrderId());
        pdfPTable.addCell(orderEvent.getUserName());
        pdfPTable.addCell(orderEvent.getCreatedDate().toString());
        pdfPTable.addCell(orderEvent.getOrderStatus().toString());
        for(EventsOrderLineItems orderLineItems : orderEvent.getOrderLineItems()){
            pdfPTable.addCell(orderLineItems.getSkuCode());
            pdfPTable.addCell(orderLineItems.getPrice().toString());
            pdfPTable.addCell(orderLineItems.getQuantity().toString());
            pdfPTable.addCell(orderLineItems.getTotalAmount().toString());
        }
    }

    public void exportPdf(HttpServletResponse response) throws IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);
        Paragraph p = new Paragraph("Invoice Order", font);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);

        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {2.2f, 2f, 2.2f,2.2f, 2f, 2.2f, 2f, 2.2f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);
        document.add(table);
        document.close();

    }

}
