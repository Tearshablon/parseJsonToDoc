package docgenerator;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;

import java.math.BigInteger;

public class TextFormatting {

    public static void aroundTextBorders(XWPFParagraph paragraph) {
        paragraph.setBorderRight(Borders.BASIC_BLACK_DASHES);
        paragraph.setBorderBottom(Borders.BASIC_BLACK_DASHES);
        paragraph.setBorderLeft(Borders.BASIC_BLACK_DASHES);
        paragraph.setBorderTop(Borders.BASIC_BLACK_DASHES);
    }

    public static void setPageBreak(XWPFDocument document) {
        XWPFParagraph paragraph = document.getLastParagraph();
        XWPFRun run = paragraph.createRun();
        run.addBreak(BreakType.PAGE);
        TextFormatting.aroundTextBorders(paragraph);
    }

    public static void setSpacesAfter(int spaces, XWPFDocument document) {
        document.createParagraph().setSpacingAfter(spaces);
    }

    public static void setSpacingBetweenLines(XWPFParagraph paragraph) {
        CTPPr ppr = paragraph.getCTP().getPPr();
        if (ppr == null) ppr = paragraph.getCTP().addNewPPr();
        CTSpacing spacing = ppr.isSetSpacing() ? ppr.getSpacing() : ppr.addNewSpacing();
        spacing.setAfter(BigInteger.valueOf(10));
        spacing.setBefore(BigInteger.valueOf(0));
        spacing.setLineRule(STLineSpacingRule.AUTO);
        spacing.setLine(BigInteger.valueOf(240));
    }
}
