package org.vandeseer;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.vandeseer.easytable.TableDrawer;
import org.vandeseer.easytable.structure.Table;

import java.io.IOException;

public class TestUtils {

    private static final String TARGET_FOLDER = "target";
    private static final float PADDING = 50f;

    private TestUtils() {
    }

    public static void createAndSaveDocumentWithTable(String outputFileName, Table table) throws IOException {
        createAndSaveDocumentWithTables(outputFileName, table);
    }

    public static void createAndSaveDocumentWithTables(String outputFileName, Table... tables) throws IOException {

        final PDDocument document = new PDDocument();
        final PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        float startY = page.getMediaBox().getHeight() - PADDING;


        try (final PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

            for (Table table : tables) {

                TableDrawer.builder()
                        .contentStream(contentStream)
                        .table(table)
                        .startX(PADDING)
                        .startY(startY)
                        .build()
                        .draw();

                startY -= (table.getHeight() + PADDING);
            }

        }

        document.save(TARGET_FOLDER + "/" + outputFileName);
        document.close();

    }

}