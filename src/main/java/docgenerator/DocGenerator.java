package docgenerator;

import allureDTO.AllureModelDTO;
import allureDTO.ParametersDTO;
import allureDTO.StepsDTO;
import operationwithjson.JsonFilter;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static operationwithjson.JsonFilter.getPlainStepsWithoutSetupStep;
import static operationwithjson.JsonFilter.getStepsDTOWithoutDollarSign;

public class DocGenerator {

    private XWPFDocument document;
    private final int TEST_SCENARIO_NAME_FONT_SIZE = 15;
    private final int TEST_STEPS_BLOCK_NAME_FONT_SIZE = 11;
    private final int TEST_STEPS_BLOCK_NAME_STEPS_FONT_SIZE = 11;
    private final String TEST_STEPS_BLOCK_NAME_TEXT = "Шаги тестового сценария:";
    private final String BEFORE_CONDITION_BLOCK_NAME_TEXT = "Предусловия:";
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
            generateBeforeConditionBlock(getPlainStepsWithoutSetupStep(getStepsDTOWithoutDollarSign(allureModel.get(0).getTestStage().getSteps().get(0))));
            generateTestStepsScenarioBlock(getStepsDTOWithoutDollarSign(allureModel.get(0).getTestStage().getSteps().get(1)));
            createDocFile(i);
        }
    }

    private void generateBeforeConditionBlock(StepsDTO stepsDTO) {
        XWPFParagraph paragraph = generateTextWithParameters(BEFORE_CONDITION_BLOCK_NAME_TEXT, TEST_STEPS_BLOCK_NAME_FONT_SIZE, ParagraphAlignment.LEFT, true);
        aroundTextBorders(paragraph);
        getBeforeConditionFromAllureModelParent(stepsDTO);
        setSpasingAfter(500);
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

    private XWPFParagraph generateTextForDescriptionBlockWithParameters(String name, String text, int fontSize, ParagraphAlignment paragraphAlignment, boolean bold) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(paragraphAlignment);
        aroundTextBorders(paragraph);
        XWPFRun xwpfRun = paragraph.createRun();
        xwpfRun.setText(name.toUpperCase() + ": " + text);
        xwpfRun.setBold(bold);
        xwpfRun.setFontSize(fontSize);
        return paragraph;
    }

    private void generateTestScenarioDescriptionBlock(AllureModelDTO allureModel) {
        generateTextForDescriptionBlockWithParameters(EPIC_NAME_FOR_DESCRIPTION_TABLE,
                JsonFilter.getValueFromLabelByName(allureModel.getLabels(), EPIC_NAME_FOR_DESCRIPTION_TABLE),
                TEST_STEPS_BLOCK_NAME_FONT_SIZE,
                ParagraphAlignment.LEFT,
                false);
        generateTextForDescriptionBlockWithParameters(STORY_NAME_FOR_DESCRIPTION_TABLE,
                JsonFilter.getValueFromLabelByName(allureModel.getLabels(), STORY_NAME_FOR_DESCRIPTION_TABLE),
                TEST_STEPS_BLOCK_NAME_FONT_SIZE,
                ParagraphAlignment.LEFT,
                false);
        generateTextForDescriptionBlockWithParameters(SEVERITY_NAME_FOR_DESCRIPTION_TABLE,
                JsonFilter.getValueFromLabelByName(allureModel.getLabels(), SEVERITY_NAME_FOR_DESCRIPTION_TABLE),
                TEST_STEPS_BLOCK_NAME_FONT_SIZE,
                ParagraphAlignment.LEFT,
                false);
        generateTextForDescriptionBlockWithParameters(URL_TO_KB_NAME_FOR_DESCRIPTION_TABLE,
                JsonFilter.getUrlToKbFromLinksByType(allureModel.getLinks(), URL_TO_KB_NAME_FOR_DESCRIPTION_TABLE),
                TEST_STEPS_BLOCK_NAME_FONT_SIZE,
                ParagraphAlignment.LEFT,
                false);

        document.createParagraph().setSpacingAfter(500);
    }

    private void generateTestStepsScenarioBlock(StepsDTO stepsDTO) {
        XWPFParagraph paragraph = generateTextWithParameters(TEST_STEPS_BLOCK_NAME_TEXT, TEST_STEPS_BLOCK_NAME_FONT_SIZE, ParagraphAlignment.LEFT, true);
        aroundTextBorders(paragraph);
        getTestStepsFromAllureModelParent(stepsDTO);
    }

    private void aroundTextBorders(XWPFParagraph paragraph) {
        paragraph.setBorderRight(Borders.BASIC_BLACK_DASHES);
        paragraph.setBorderBottom(Borders.BASIC_BLACK_DASHES);
        paragraph.setBorderLeft(Borders.BASIC_BLACK_DASHES);
        paragraph.setBorderTop(Borders.BASIC_BLACK_DASHES);
    }

    private void getTestStepsFromAllureModelParent(StepsDTO stepsDTO) {
        int stepnumber = 1;
        for (int i = 0; i < stepsDTO.getSteps().size(); i++) {
            XWPFParagraph paragraph = generateTextWithParameters(stepnumber + ". " + stepsDTO.getSteps().get(i).getName(), TEST_STEPS_BLOCK_NAME_STEPS_FONT_SIZE, ParagraphAlignment.LEFT, false);
            aroundTextBorders(paragraph);
            String parentPrefix = "   " + String.valueOf(stepnumber) + ".";
            getTestStepsFromAllureModelChild(parentPrefix, stepsDTO.getSteps().get(i));
            stepnumber++;
        }
    }

    private void getTestStepsFromAllureModelChild(String parentStepNumber, StepsDTO stepsDTO) {
        int stepnumber = 1;
        for (int i = 0; i < stepsDTO.getSteps().size(); i++) {
            XWPFParagraph paragraph = generateTextWithParameters(parentStepNumber + stepnumber + ". " + stepsDTO.getSteps().get(i).getName(), TEST_STEPS_BLOCK_NAME_STEPS_FONT_SIZE, ParagraphAlignment.LEFT, false);
            aroundTextBorders(paragraph);
            String parentPrefix = "   " + parentStepNumber + String.valueOf(stepnumber) + ".";
            getTestStepsFromAllureModelChild(parentPrefix, stepsDTO.getSteps().get(i));
            stepnumber++;
        }
    }

    private void getBeforeConditionFromAllureModelParent(StepsDTO stepsDTO) {
        int stepnumber = 1;
        for (int i = 0; i < stepsDTO.getSteps().size(); i++) {
            XWPFParagraph paragraph = generateTextWithParameters(stepnumber + ". " + stepsDTO.getSteps().get(i).getName(), TEST_STEPS_BLOCK_NAME_STEPS_FONT_SIZE, ParagraphAlignment.LEFT, false);
            aroundTextBorders(paragraph);
            String parentPrefix = "   " + String.valueOf(stepnumber) + ".";
            getBeforeConditionFromAllureModelChild(parentPrefix, stepsDTO.getSteps().get(i).getParameters());
            stepnumber++;
        }
    }

    private void getBeforeConditionFromAllureModelChild(String parentStepNumber, List<ParametersDTO> parameters) {
        int stepnumber = 1;
        for (int i = 0; i < parameters.size(); i++) {
            XWPFParagraph paragraph = generateTextWithParameters(parentStepNumber + stepnumber + ". name: " + parameters.get(i).getName() + "  value: " + parameters.get(i).getValue(), TEST_STEPS_BLOCK_NAME_STEPS_FONT_SIZE, ParagraphAlignment.LEFT, false);
            aroundTextBorders(paragraph);
            String parentPrefix = "   " + parentStepNumber + String.valueOf(stepnumber) + ".";
            stepnumber++;
        }
    }

    private void createDocFile(int i) throws IOException {
        FileOutputStream out = new FileOutputStream(new File("test_scenario_" + i + ".docx"));
        document.write(out);
        out.close();
    }

    private void setSpasingAfter(int spaces) {
        document.createParagraph().setSpacingAfter(spaces);
    }
}