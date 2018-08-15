package docgenerator;

import allureDTO.AllureModelDTO;
import operationwithjson.JsonFilter;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class DocGenerator {

    private XWPFDocument document;
    private final int PARAGRAPH_FONT_SIZE = 15;
    private final String EPIC_NAME_FOR_DESCRIPTION_TABLE = "epic";
    private final String STORY_NAME_FOR_DESCRIPTION_TABLE = "story";
    private final String SEVERITY_NAME_FOR_DESCRIPTION_TABLE = "severity";
    private final String URL_TO_KB_NAME_FOR_DESCRIPTION_TABLE = "tms";

    public DocGenerator() {
        this.document = new XWPFDocument();
    }

    public void generateDocFiles(List<AllureModelDTO> allureModel) throws IOException {
        for (int i = 0; i < allureModel.size(); i++) {
            generateTestScenarioName(allureModel.get(i).getName());
            generateTestScenarioDescriptionBlock(allureModel.get(0));
            createDocFile(i);
        }
    }

    private void generateTestScenarioName(String testScenarioName) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.getCTP().getPPr().getNumPr().addNewIlvl().setVal(BigInteger.valueOf(1));
        XWPFRun xwpfRun = paragraph.createRun();
        xwpfRun.setText(testScenarioName);
        xwpfRun.setFontSize(PARAGRAPH_FONT_SIZE);
    }

    private void generateTestScenarioDescriptionBlock(AllureModelDTO allureModel) {
        XWPFTable table = document.createTable();

        XWPFTableRow tableRowOne = table.getRow(0);
        tableRowOne.getCell(0).setText(EPIC_NAME_FOR_DESCRIPTION_TABLE.toUpperCase());
        tableRowOne.addNewTableCell().setText(JsonFilter.getValueFromLabelByName(allureModel.getLabels(), EPIC_NAME_FOR_DESCRIPTION_TABLE));
        tableRowOne.getCell(0).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);

        XWPFTableRow tableRowTwo = table.createRow();
        tableRowTwo.getCell(0).setText(STORY_NAME_FOR_DESCRIPTION_TABLE.toUpperCase());
        tableRowTwo.getCell(1).setText(JsonFilter.getValueFromLabelByName(allureModel.getLabels(), STORY_NAME_FOR_DESCRIPTION_TABLE));

        XWPFTableRow tableRowThree = table.createRow();
        tableRowThree.getCell(0).setText(SEVERITY_NAME_FOR_DESCRIPTION_TABLE.toUpperCase());
        tableRowThree.getCell(1).setText(JsonFilter.getValueFromLabelByName(allureModel.getLabels(), SEVERITY_NAME_FOR_DESCRIPTION_TABLE));


        XWPFTableRow tableRowFour = table.createRow();
        tableRowFour.getCell(0).setText(URL_TO_KB_NAME_FOR_DESCRIPTION_TABLE.toUpperCase());
        tableRowFour.getCell(1).setText(JsonFilter.getUrlToKbFromLinksByType(allureModel.getLinks(), URL_TO_KB_NAME_FOR_DESCRIPTION_TABLE));

//        String epic = JsonFilter.getValueFromLabelByName(allureModel.getLabels(), EPIC_NAME_FOR_DESCRIPTION_TABLE);
//        String story = JsonFilter.getValueFromLabelByName(allureModel.getLabels(), STORY_NAME_FOR_DESCRIPTION_TABLE);
//        String severity = JsonFilter.getValueFromLabelByName(allureModel.getLabels(), SEVERITY_NAME_FOR_DESCRIPTION_TABLE);
//        String urlToKb = JsonFilter.getUrlToKbFromLinksByType(allureModel.getLinks());
    }

    public void createDocFile(int i) throws IOException {
        FileOutputStream out = new FileOutputStream(new File("test_scenario_" + i + ".docx"));
        document.write(out);
        out.close();
    }
}