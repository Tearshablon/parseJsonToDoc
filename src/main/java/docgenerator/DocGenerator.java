package docgenerator;

import allureDTO.AllureModelDTO;
import allureDTO.ParametersDTO;
import allureDTO.StepsDTO;
import operationwithjson.JsonFilter;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static operationwithjson.JsonFilter.*;

public class DocGenerator {

    private XWPFDocument document;
    private final int TEST_SCENARIO_NAME_FONT_SIZE = 15;
    private final int TEST_STEPS_BLOCK_NAME_FONT_SIZE = 11;
    private final int TEST_STEPS_BLOCK_NAME_STEPS_FONT_SIZE = 11;
    private final int TEST_STEPS_BLOCK_PARAMETERS_STEPS_FONT_SIZE = 8;
    private final int LENGT_BETWEEN_DESCRIPTION_BLOCK_AND_BEFORE_CONDITION_BLOCK = 250;
    private final String TEST_STEPS_BLOCK_NAME_TEXT = "Шаги тестового сценария:";
    private final String BEFORE_CONDITION_BLOCK_NAME_TEXT = "Предусловия:";
    private final String EPIC_NAME_FOR_DESCRIPTION_TABLE = "epic";
    private final String STORY_NAME_FOR_DESCRIPTION_TABLE = "story";
    private final String SEVERITY_NAME_FOR_DESCRIPTION_TABLE = "severity";
    private final String URL_TO_KB_NAME_FOR_DESCRIPTION_TABLE = "tms";
    private final String RUSSIAN_VALUE_EPIC_NAME_FOR_DESCRIPTION_TABLE = "Эпик";
    private final String RUSSIAN_VALUE_STORY_NAME_FOR_DESCRIPTION_TABLE = "История";
    private final String RUSSIAN_VALUE_SEVERITY_NAME_FOR_DESCRIPTION_TABLE = "Приоритет";
    private final String RUSSIAN_VALUE_URL_TO_KB_NAME_FOR_DESCRIPTION_TABLE = "Ссылка на кб";
    private final String DOCUMENT_NAME = "test_scenario.docx";

    public DocGenerator() {
        this.document = new XWPFDocument();
    }

    public void generateDocFiles(List<AllureModelDTO> allureModel) throws IOException {
        for (int i = 0; i < allureModel.size(); i++) {
            generateTextWithParameters(allureModel.get(i).getName(), TEST_SCENARIO_NAME_FONT_SIZE, ParagraphAlignment.LEFT, true);
            generateTestScenarioDescriptionBlock(allureModel.get(i));
            generateBeforeConditionBlock(getPlainStepsWithoutSetupStep(getStepsDTOWithoutDollarSign(allureModel.get(i).getTestStage().getSteps().get(0))));

            if (i == allureModel.size() - 1) {
                generateTestStepsScenarioBlock(deleteParametersTestStepsDTOWhereLengthMoreThan50Symbols(getStepsDTOWithoutDollarSign(allureModel.get(i).getTestStage().getSteps().get(1))), true);
            } else {
                generateTestStepsScenarioBlock(deleteParametersTestStepsDTOWhereLengthMoreThan50Symbols(getStepsDTOWithoutDollarSign(allureModel.get(i).getTestStage().getSteps().get(1))), false);
            }
            writeInDocFile();
        }
    }

    private void generateBeforeConditionBlock(StepsDTO stepsDTO) {
        XWPFParagraph paragraph = generateTextWithParameters(BEFORE_CONDITION_BLOCK_NAME_TEXT, TEST_STEPS_BLOCK_NAME_FONT_SIZE, ParagraphAlignment.LEFT, true);
        paragraph.setSpacingBefore(LENGT_BETWEEN_DESCRIPTION_BLOCK_AND_BEFORE_CONDITION_BLOCK);
        TextFormatting.aroundTextBorders(paragraph);
        getBeforeConditionFromAllureModelParent(stepsDTO);
        TextFormatting.setSpacesAfter(20, document);
    }

    private XWPFParagraph generateTextWithParameters(String text, int fontSize, ParagraphAlignment paragraphAlignment, boolean bold) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(paragraphAlignment);
        XWPFRun xwpfRun = paragraph.createRun();
        xwpfRun.setText(text);
        xwpfRun.setBold(bold);
        xwpfRun.setFontSize(fontSize);

        TextFormatting.setSpacingBetweenLines(paragraph);
        return paragraph;
    }

    private XWPFParagraph generateTextWithParametersAndNumber(String number, String text, int fontSize, ParagraphAlignment paragraphAlignment, boolean bold) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(paragraphAlignment);
        XWPFRun xwpfRun = paragraph.createRun();
        xwpfRun.setText(number);
        xwpfRun.setBold(bold);
        xwpfRun.setFontSize(fontSize);

        XWPFRun xwpfRun2 = paragraph.createRun();
        xwpfRun2.setText(text);

        TextFormatting.setSpacingBetweenLines(paragraph);
        return paragraph;
    }

    private XWPFParagraph generateTextForDescriptionBlockWithParameters(String name, String text, int fontSize, ParagraphAlignment paragraphAlignment, boolean bold) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(paragraphAlignment);
        TextFormatting.aroundTextBorders(paragraph);

        XWPFRun xwpfRun1 = paragraph.createRun();
        xwpfRun1.setText(name + ": ");
        xwpfRun1.setBold(bold);
        xwpfRun1.setFontSize(fontSize);

        XWPFRun xwpfRun2 = paragraph.createRun();
        xwpfRun2.setText(text);

        TextFormatting.setSpacingBetweenLines(paragraph);

        return paragraph;
    }

    private void generateTestScenarioDescriptionBlock(AllureModelDTO allureModel) {
        generateTextForDescriptionBlockWithParameters(RUSSIAN_VALUE_EPIC_NAME_FOR_DESCRIPTION_TABLE,
                JsonFilter.getValueFromLabelByName(allureModel.getLabels(), EPIC_NAME_FOR_DESCRIPTION_TABLE),
                TEST_STEPS_BLOCK_NAME_FONT_SIZE,
                ParagraphAlignment.LEFT,
                true);
        generateTextForDescriptionBlockWithParameters(RUSSIAN_VALUE_STORY_NAME_FOR_DESCRIPTION_TABLE,
                JsonFilter.getValueFromLabelByName(allureModel.getLabels(), STORY_NAME_FOR_DESCRIPTION_TABLE),
                TEST_STEPS_BLOCK_NAME_FONT_SIZE,
                ParagraphAlignment.LEFT,
                true);
        generateTextForDescriptionBlockWithParameters(RUSSIAN_VALUE_SEVERITY_NAME_FOR_DESCRIPTION_TABLE,
                JsonFilter.getValueFromLabelByName(allureModel.getLabels(), SEVERITY_NAME_FOR_DESCRIPTION_TABLE),
                TEST_STEPS_BLOCK_NAME_FONT_SIZE,
                ParagraphAlignment.LEFT,
                true);
        generateTextForDescriptionBlockWithParameters(RUSSIAN_VALUE_URL_TO_KB_NAME_FOR_DESCRIPTION_TABLE,
                JsonFilter.getUrlToKbFromLinksByType(allureModel.getLinks(), URL_TO_KB_NAME_FOR_DESCRIPTION_TABLE),
                TEST_STEPS_BLOCK_NAME_FONT_SIZE,
                ParagraphAlignment.LEFT,
                true);

    }

    private void generateTestStepsScenarioBlock(StepsDTO stepsDTO, boolean isLastElement) {
        XWPFParagraph paragraph = generateTextWithParameters(TEST_STEPS_BLOCK_NAME_TEXT, TEST_STEPS_BLOCK_NAME_FONT_SIZE, ParagraphAlignment.LEFT, true);
        TextFormatting.aroundTextBorders(paragraph);
        getTestStepsFromAllureModelParent(stepsDTO);

        if (!isLastElement) {
            TextFormatting.setPageBreak(document);
        }
    }

    private void getTestStepsFromAllureModelParent(StepsDTO stepsDTO) {
        int stepnumber = 1;
        for (int i = 0; i < stepsDTO.getSteps().size(); i++) {

            XWPFParagraph paragraph1 = generateTextWithParametersAndNumber(stepnumber + ". ", stepsDTO.getSteps().get(i).getName(), TEST_STEPS_BLOCK_NAME_STEPS_FONT_SIZE, ParagraphAlignment.LEFT, true);

            TextFormatting.setSpacingBetweenLines(paragraph1);

            if (!stepsDTO.getSteps().get(i).getParameters().isEmpty()) {
                for (int j = 0; j < stepsDTO.getSteps().get(i).getParameters().size(); j++) {
                    XWPFParagraph paragraph = generateTextWithParameters(getStringWithSpaces((stepnumber + ". ").length()) + "   name: " + stepsDTO.getSteps().get(i).getParameters().get(j).getName() + "  value: " + stepsDTO.getSteps().get(i).getParameters().get(j).getValue(), TEST_STEPS_BLOCK_PARAMETERS_STEPS_FONT_SIZE, ParagraphAlignment.LEFT, false);

                    TextFormatting.setSpacingBetweenLines(paragraph);
                    TextFormatting.aroundTextBorders(paragraph);

                }
            }
            TextFormatting.aroundTextBorders(paragraph1);
            String parentPrefix = "   " + String.valueOf(stepnumber) + ".";
            getTestStepsFromAllureModelChild(parentPrefix, stepsDTO.getSteps().get(i));
            stepnumber++;
        }
    }

    private void getTestStepsFromAllureModelChild(String parentStepNumber, StepsDTO stepsDTO) {
        int stepnumber = 1;
        for (int i = 0; i < stepsDTO.getSteps().size(); i++) {
            XWPFParagraph paragraph1 = generateTextWithParametersAndNumber(parentStepNumber + stepnumber + ". ", stepsDTO.getSteps().get(i).getName(), TEST_STEPS_BLOCK_NAME_STEPS_FONT_SIZE, ParagraphAlignment.LEFT, true);

            TextFormatting.setSpacingBetweenLines(paragraph1);

            paragraph1.setSpacingBetween(1);
            if (!stepsDTO.getSteps().get(i).getParameters().isEmpty()) {
                for (int j = 0; j < stepsDTO.getSteps().get(i).getParameters().size(); j++) {
                    XWPFParagraph paragraph = generateTextWithParameters("name: " + stepsDTO.getSteps().get(i).getParameters().get(j).getName() + "  value: " + stepsDTO.getSteps().get(i).getParameters().get(j).getValue(), TEST_STEPS_BLOCK_PARAMETERS_STEPS_FONT_SIZE, ParagraphAlignment.LEFT, false);
                    TextFormatting.setSpacingBetweenLines(paragraph);
                    TextFormatting.aroundTextBorders(paragraph);

                }
            }
            TextFormatting.aroundTextBorders(paragraph1);
            String parentPrefix = "   " + parentStepNumber + String.valueOf(stepnumber) + ".";
            getTestStepsFromAllureModelChild(parentPrefix, stepsDTO.getSteps().get(i));
            stepnumber++;
        }
    }

    private void getBeforeConditionFromAllureModelParent(StepsDTO stepsDTO) {
        int stepnumber = 1;
        for (int i = 0; i < stepsDTO.getSteps().size(); i++) {
            XWPFParagraph paragraph = generateTextWithParametersAndNumber(stepnumber + ". ", stepsDTO.getSteps().get(i).getName(), TEST_STEPS_BLOCK_NAME_STEPS_FONT_SIZE, ParagraphAlignment.LEFT, true);

            TextFormatting.setSpacingBetweenLines(paragraph);

            TextFormatting.aroundTextBorders(paragraph);
            getBeforeConditionFromAllureModelChild(stepsDTO.getSteps().get(i).getParameters());
            stepnumber++;
        }
    }

    private void getBeforeConditionFromAllureModelChild(List<ParametersDTO> parameters) {
        for (int i = 0; i < parameters.size(); i++) {
            XWPFParagraph paragraph = generateTextWithParameters("name: " + parameters.get(i).getName() + "  value: " + parameters.get(i).getValue(), TEST_STEPS_BLOCK_PARAMETERS_STEPS_FONT_SIZE, ParagraphAlignment.LEFT, false);

            TextFormatting.setSpacingBetweenLines(paragraph);
            TextFormatting.aroundTextBorders(paragraph);

        }
    }

    private void writeInDocFile() throws IOException {
        FileOutputStream out = new FileOutputStream(new File(DOCUMENT_NAME));
        document.write(out);
        out.close();
    }


    private String getStringWithSpaces(int countSpace) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < countSpace; i++) {
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }


}