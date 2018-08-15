package docgenerator;

import allureDTO.AllureModelDTO;
import allureDTO.StepsDTO;
import operationwithjson.JsonFilter;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static operationwithjson.JsonFilter.getStepsDTOWithoutDollarSign;

public class DocGenerator {

    private XWPFDocument document;
    private final int TEST_SCENARIO_NAME_FONT_SIZE = 15;
    private final int TEST_STEPS_BLOCK_NAME_FONT_SIZE = 11;
    private final int TEST_STEPS_BLOCK_NAME_STEPS_FONT_SIZE = 8;
    private final String TEST_STEPS_BLOCK_NAME_TEXT = "Шаги тестового сценария";
    private final String EPIC_NAME_FOR_DESCRIPTION_TABLE = "epic";
    private final String STORY_NAME_FOR_DESCRIPTION_TABLE = "story";
    private final String SEVERITY_NAME_FOR_DESCRIPTION_TABLE = "severity";
    private final String URL_TO_KB_NAME_FOR_DESCRIPTION_TABLE = "tms";

    public DocGenerator() {
        this.document = new XWPFDocument();
    }

    public void generateDocFiles(List<AllureModelDTO> allureModel) throws IOException {
        for (int i = 0; i < allureModel.size(); i++) {
            generateTextWithParameters(allureModel.get(i).getName(), TEST_SCENARIO_NAME_FONT_SIZE, ParagraphAlignment.LEFT, true);
            generateTestScenarioDescriptionBlock(allureModel.get(0));
            StepsDTO stepsDTO = getStepsDTOWithoutDollarSign(allureModel.get(0).getTestStage().getSteps().get(1));
            generateBeforeConditionBlock(stepsDTO);
            createDocFile(i);
        }
    }

    private XWPFParagraph generateTextWithParameters(String text, int fontSize, ParagraphAlignment paragraphAlignment, boolean bold) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(paragraphAlignment);
        XWPFRun xwpfRun = paragraph.createRun();
        xwpfRun.setText(text);
        xwpfRun.setBold(bold);
        xwpfRun.setFontSize(fontSize);

        return paragraph;

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

        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setSpacingAfter(200);

        table.getRow(1).getCell(0).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2000));
    }

    private void generateBeforeConditionBlock(StepsDTO stepsDTO) {
        XWPFParagraph paragraph = generateTextWithParameters(TEST_STEPS_BLOCK_NAME_TEXT, TEST_STEPS_BLOCK_NAME_FONT_SIZE, ParagraphAlignment.LEFT, true);
        List<Integer> numeric = new ArrayList<>();
        numeric.add(0);
        getTestStepsFromAllureMode2(stepsDTO);
    }

    //todo окружает тест рамкой
    private void aroundTextBorders(XWPFParagraph paragraph) {
        paragraph.setBorderRight(Borders.BASIC_BLACK_DASHES);
        paragraph.setBorderBottom(Borders.BASIC_BLACK_DASHES);
        paragraph.setBorderLeft(Borders.BASIC_BLACK_DASHES);
        paragraph.setBorderTop(Borders.BASIC_BLACK_DASHES);
    }

    private void getTestStepsFromAllureMode2(StepsDTO stepsDTO) {
        int stepnumber = 1;
        for (int i = 0; i < stepsDTO.getSteps().size(); i++) {
            generateTextWithParameters(stepnumber +". "+ stepsDTO.getSteps().get(i).getName(), TEST_STEPS_BLOCK_NAME_STEPS_FONT_SIZE, ParagraphAlignment.LEFT, false);
            String parentPrefix = "   "+ String.valueOf(stepnumber)+".";
            getTestStepsFromAllureMode2R(parentPrefix, stepsDTO.getSteps().get(i));
            stepnumber++;
        }
    }

    private void getTestStepsFromAllureMode2R(String parentStepNumber, StepsDTO stepsDTO) {
        int stepnumber = 1;
        for (int i = 0; i < stepsDTO.getSteps().size(); i++) {
            generateTextWithParameters(parentStepNumber + stepnumber +". "+ stepsDTO.getSteps().get(i).getName(), TEST_STEPS_BLOCK_NAME_STEPS_FONT_SIZE, ParagraphAlignment.LEFT, false);
            String parentPrefix = "   "+parentStepNumber + String.valueOf(stepnumber)+".";
            getTestStepsFromAllureMode2R(parentPrefix, stepsDTO.getSteps().get(i));
            stepnumber++;
        }
    }

    private String getSequence(List<Integer> numeric) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer count : numeric) {
            stringBuilder.append(count + ". ");
        }

        return stringBuilder.toString();
    }

    public void createDocFile(int i) throws IOException {
        FileOutputStream out = new FileOutputStream(new File("test_scenario_" + i + ".docx"));
        document.write(out);
        out.close();
    }
}