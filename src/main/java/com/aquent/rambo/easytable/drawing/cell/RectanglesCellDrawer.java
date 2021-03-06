package com.aquent.rambo.easytable.drawing.cell;

import java.awt.Color;
import java.awt.geom.Point2D;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.aquent.rambo.easytable.drawing.DrawingContext;
import com.aquent.rambo.easytable.drawing.DrawingUtil;
import com.aquent.rambo.easytable.drawing.PositionedRectangle;
import com.aquent.rambo.easytable.drawing.PositionedStyledText;
import com.aquent.rambo.easytable.structure.cell.RectangleCellDetails;
import com.aquent.rambo.easytable.structure.cell.RectanglesCell;

import lombok.SneakyThrows;
/**
 * 
 * @author vtambe
 *
 */
public class RectanglesCellDrawer extends AbstractCellDrawer<RectanglesCell> {
	
	public RectanglesCellDrawer(RectanglesCell rectanglesCell) {
		this.cell = rectanglesCell;
	}

	 @Override
     protected float calculateInnerHeight() {
         return cell.getRow().getHeight() * 80 / 100;
     }
	
	 @SneakyThrows
     @Override
     public void drawContent(DrawingContext drawingContext) {
         final PDPageContentStream contentStream = drawingContext.getContentStream();
         final Point2D.Float start = drawingContext.getStartingPoint();

         final float rowHeight = cell.getRow().getHeight();
         float startY = rowHeight < cell.getHeight() ? start.y + rowHeight - cell.getHeight() : start.y;
         final float calculatedRectangleHeight = cell.getHeight() * 80/ 100;
         float startX = start.x + cell.getPaddingLeft();
         float rectCellWidth = cell.getWidth() - cell.getHorizontalPadding();
         if(cell.isMultiColumn()) {
         	rectCellWidth = rectCellWidth/2;
         }
         for(RectangleCellDetails rectangleCellDetails:cell.getRectangleCellDetails()) {
         	float rectHeight = calculatedRectangleHeight * rectangleCellDetails.getColor1Percentage();
         	startY = startY + cell.getPaddingBottom();
         	int fontSize = rectangleCellDetails.getCellTextFontSize()>0 ? rectangleCellDetails.getCellTextFontSize() : 6; 
         	Color cellTextColor = rectangleCellDetails.getCellTextColor() != null ? rectangleCellDetails.getCellTextColor() : Color.BLACK;
         	PDFont cellTextFont = rectangleCellDetails.getCellTextFont() != null ? rectangleCellDetails.getCellTextFont() : PDType1Font.HELVETICA;
         	String cellText = rectangleCellDetails.getCellText() != null ? rectangleCellDetails.getCellText() : "";
         	float textStartX = startX + ((rectCellWidth/2)-cell.getPaddingLeft());
         	if(cell.isMultiColumn()) {
            	DrawingUtil.drawText(contentStream, PositionedStyledText.builder().x(textStartX).y(startY).font(cellTextFont).fontSize(fontSize).color(cellTextColor).text(cellText).build());
            }else {
            	textStartX = startX + ((rectCellWidth/2)-cell.getPaddingLeft());
            	DrawingUtil.drawText(contentStream, PositionedStyledText.builder().x(textStartX).y(startY).font(cellTextFont).fontSize(fontSize).color(cellTextColor).text(cellText).build());
            }
            startY = startY + 5f;
            DrawingUtil.drawRectangle(contentStream,
                     PositionedRectangle.builder()
                             .x(startX)
                             .y(startY)
                             .width(rectCellWidth)
                             .height(rectHeight)
                             .color(rectangleCellDetails.getColor1Color()).build()
             );
             
             if(rectangleCellDetails.getColor2Percentage() > 0f) {
             	startY = startY + rectHeight ; 
             	rectHeight = calculatedRectangleHeight * rectangleCellDetails.getColor2Percentage();
             	DrawingUtil.drawRectangle(contentStream,
                         PositionedRectangle.builder()
                                 .x(startX)
                                 .y(startY)
                                 .width(rectCellWidth)
                                 .height(rectHeight)
                                 .color(rectangleCellDetails.getColor2Color()).build()
                 );
             }
             
             if(rectangleCellDetails.getColor3Percentage() > 0f) {
             	startY = startY + rectHeight;
             	rectHeight = calculatedRectangleHeight * rectangleCellDetails.getColor3Percentage();
             	DrawingUtil.drawRectangle(contentStream,
                          PositionedRectangle.builder()
                                  .x(startX)
                                  .y(startY)
                                  .width(rectCellWidth)
                                  .height(rectHeight)
                                  .color(rectangleCellDetails.getColor3Color()).build()
                  );
             }
             startX = startX + rectCellWidth + 1;
             startY = rowHeight < cell.getHeight() ? start.y + rowHeight - cell.getHeight() : start.y;
             
         }
     }

	

}
