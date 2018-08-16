package docgenerator;

import allureDTO.AllureModelDTO;
import allureDTO.ParametersDTO;
import allureDTO.StepsDTO;
import operationwithjson.JsonFilter;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import static operationwithjson.JsonFilter.deleteParametersTestStepsDTOWhereLengthMoreThan50Symbols;
import static operationwithjson.JsonFilter.getPlainStepsWithoutSetupStep;
import static operationwithjson.JsonFilter.getStepsDTOWithoutDollarSign;

public class DocGenerator {

    private XWPFDocument document;
    private final int TEST_SCENARIO_NAME_FONT_SIZE = 15;
    private final int TEST_STEPS_BLOCK_NAME_FONT_SIZE = 11;
    private final int TEST_STEPS_BLOCK_NAME_STEPS_FONT_SIZE = 11;
    private final int TEST_STEPS_BLOCK_PARAMETERS_STEPS_FONT_SIZE = 8;
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
            generateTestScenarioDescriptionBlock(allureModel.get(i));
            generateBeforeConditionBlock(getPlainStepsWithoutSetupStep(getStepsDTOWithoutDollarSign(allureModel.get(i).getTestStage().getSteps().get(0))));
            generateTestStepsScenarioBlock(deleteParametersTestStepsDTOWhereLengthMoreThan50Symbols(getStepsDTOWithoutDollarSign(allureModel.get(i).getTestStage().getSteps().get(1))));
            writeInDocFile();
        }
    }

    private void generateBeforeConditionBlock(StepsDTO stepsDTO) {
        XWPFParagraph paragraph = generateTextWithParameters(BEFORE_CONDITION_BLOCK_NAME_TEXT, TEST_STEPS_BLOCK_NAME_FONT_SIZE, ParagraphAlignment.LEFT, true);
        aroundTextBorders(paragraph);
        getBeforeConditionFromAllureModelParent(stepsDTO);
        setSpacesAfter(500);
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

        XWPFRun xwpfRun1 = paragraph.createRun();
        xwpfRun1.setText(name + ": ");
        xwpfRun1.setBold(bold);
        xwpfRun1.setFontSize(fontSize);

        XWPFRun xwpfRun2 = paragraph.createRun();
        xwpfRun2.setText(text);

        setSpacingBeetwenLines(paragraph);

        return paragraph;
    }

    private void setSpacingBeetwenLines(XWPFParagraph paragraph) {
        CTPPr ppr = paragraph.getCTP().getPPr();
        if (ppr == null) ppr = paragraph.getCTP().addNewPPr();
        CTSpacing spacing = ppr.isSetSpacing()? ppr.getSpacing() : ppr.addNewSpacing();
        spacing.setAfter(BigInteger.valueOf(10));
        spacing.setBefore(BigInteger.valueOf(0));
        spacing.setLineRule(STLineSpacingRule.AUTO);
        spacing.setLine(BigInteger.valueOf(240));
    }

    private void generateTestScenarioDescriptionBlock(AllureModelDTO allureModel) {
        generateTextForDescriptionBlockWithParameters("Эпик",
                JsonFilter.getValueFromLabelByName(allureModel.getLabels(), EPIC_NAME_FOR_DESCRIPTION_TABLE),
                TEST_STEPS_BLOCK_NAME_FONT_SIZE,
                ParagraphAlignment.LEFT,
                true);
        generateTextForDescriptionBlockWithParameters("История",
                JsonFilter.getValueFromLabelByName(allureModel.getLabels(), STORY_NAME_FOR_DESCRIPTION_TABLE),
                TEST_STEPS_BLOCK_NAME_FONT_SIZE,
                ParagraphAlignment.LEFT,
                true);
        generateTextForDescriptionBlockWithParameters("Приоритет",
                JsonFilter.getValueFromLabelByName(allureModel.getLabels(), SEVERITY_NAME_FOR_DESCRIPTION_TABLE),
                TEST_STEPS_BLOCK_NAME_FONT_SIZE,
                ParagraphAlignment.LEFT,
                true);
        generateTextForDescriptionBlockWithParameters("Ссылка на кб",
                JsonFilter.getUrlToKbFromLinksByType(allureModel.getLinks(), URL_TO_KB_NAME_FOR_DESCRIPTION_TABLE),
                TEST_STEPS_BLOCK_NAME_FONT_SIZE,
                ParagraphAlignment.LEFT,
                true);

        document.createParagraph().setSpacingAfter(500);
    }

    private void generateTestStepsScenarioBlock(StepsDTO stepsDTO) {
        XWPFParagraph paragraph = generateTextWithParameters(TEST_STEPS_BLOCK_NAME_TEXT, TEST_STEPS_BLOCK_NAME_FONT_SIZE, ParagraphAlignment.LEFT, true);
        aroundTextBorders(paragraph);
        getTestStepsFromAllureModelParent(stepsDTO);

        setPageBreak();
    }

    private void setPageBreak() {
        XWPFParagraph paragraph = document.getLastParagraph();
        XWPFRun run = paragraph.createRun();
        run.addBreak(BreakType.PAGE);
        aroundTextBorders(paragraph);
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

            XWPFParagraph paragraph1 = generateTextWithParameters(stepnumber + ". " + stepsDTO.getSteps().get(i).getName(), TEST_STEPS_BLOCK_NAME_STEPS_FONT_SIZE, ParagraphAlignment.LEFT, false);

            setSpacingBeetwenLines(paragraph1);

            if (!stepsDTO.getSteps().get(i).getParameters().isEmpty()) {
                for (int j = 0; j < stepsDTO.getSteps().get(i).getParameters().size(); j++) {
                    XWPFParagraph paragraph = generateTextWithParameters(getStringWithSpaces((stepnumber + ". ").length()) + "   name: " + stepsDTO.getSteps().get(i).getParameters().get(j).getName() + "  value: " + stepsDTO.getSteps().get(i).getParameters().get(j).getValue(), TEST_STEPS_BLOCK_PARAMETERS_STEPS_FONT_SIZE, ParagraphAlignment.LEFT, false);

                    setSpacingBeetwenLines(paragraph);
                    aroundTextBorders(paragraph);

                }
            }
            aroundTextBorders(paragraph1);
            String parentPrefix = "   " + String.valueOf(stepnumber) + ".";
            getTestStepsFromAllureModelChild(parentPrefix, stepsDTO.getSteps().get(i));
            stepnumber++;
        }
    }

    private void getTestStepsFromAllureModelChild(String parentStepNumber, StepsDTO stepsDTO) {
        int stepnumber = 1;
        for (int i = 0; i < stepsDTO.getSteps().size(); i++) {
            XWPFParagraph paragraph1 = generateTextWithParameters(parentStepNumber + stepnumber + ". " + stepsDTO.getSteps().get(i).getName(), TEST_STEPS_BLOCK_NAME_STEPS_FONT_SIZE, ParagraphAlignment.LEFT, false);

            setSpacingBeetwenLines(paragraph1);

            paragraph1.setSpacingBetween(1);
            if (!stepsDTO.getSteps().get(i).getParameters().isEmpty()) {
                for (int j = 0; j < stepsDTO.getSteps().get(i).getParameters().size(); j++) {
                    XWPFParagraph paragraph = generateTextWithParameters(getStringWithSpaces((parentStepNumber + stepnumber + ". ").length()) + "   name: " + stepsDTO.getSteps().get(i).getParameters().get(j).getName() + "  value: " + stepsDTO.getSteps().get(i).getParameters().get(j).getValue(), TEST_STEPS_BLOCK_PARAMETERS_STEPS_FONT_SIZE, ParagraphAlignment.LEFT, false);
                    setSpacingBeetwenLines(paragraph);
                    aroundTextBorders(paragraph);

                }
            }
            aroundTextBorders(paragraph1);
            String parentPrefix = "   " + parentStepNumber + String.valueOf(stepnumber) + ".";
            getTestStepsFromAllureModelChild(parentPrefix, stepsDTO.getSteps().get(i));
            stepnumber++;
        }
    }

    private void getBeforeConditionFromAllureModelParent(StepsDTO stepsDTO) {
        int stepnumber = 1;
        for (int i = 0; i < stepsDTO.getSteps().size(); i++) {
            XWPFParagraph paragraph = generateTextWithParameters(stepnumber + ". " + stepsDTO.getSteps().get(i).getName(), TEST_STEPS_BLOCK_NAME_STEPS_FONT_SIZE, ParagraphAlignment.LEFT, false);

            setSpacingBeetwenLines(paragraph);

            aroundTextBorders(paragraph);
            getBeforeConditionFromAllureModelChild(stepsDTO.getSteps().get(i).getParameters());
            stepnumber++;
        }
    }

    private void getBeforeConditionFromAllureModelChild(List<ParametersDTO> parameters) {
        for (int i = 0; i < parameters.size(); i++) {
            XWPFParagraph paragraph = generateTextWithParameters("   name: " + parameters.get(i).getName() + "  value: " + parameters.get(i).getValue(), TEST_STEPS_BLOCK_PARAMETERS_STEPS_FONT_SIZE, ParagraphAlignment.LEFT, false);

            setSpacingBeetwenLines(paragraph);
            aroundTextBorders(paragraph);

        }
    }

    private void writeInDocFile() throws IOException {
        FileOutputStream out = new FileOutputStream(new File("test_scenario.docx"));
        document.write(out);
        out.close();
    }

    private void setSpacesAfter(int spaces) {
        document.createParagraph().setSpacingAfter(spaces);
    }

    private String getStringWithSpaces(int countSpace) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < countSpace; i++) {
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }
}