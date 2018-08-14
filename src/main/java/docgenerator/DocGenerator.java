package docgenerator;

import allureDTO.AllureModelDTO;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class DocGenerator {

    private XWPFDocument document;
    private final int PARAGRAPH_FONT_SIZE = 15;

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
        XWPFRun xwpfRun = paragraph.createRun();
        xwpfRun.setText(testScenarioName);
        xwpfRun.setFontSize(PARAGRAPH_FONT_SIZE);
    }

    private void generateTestScenarioDescriptionBlock(AllureModelDTO allureModel) {
        XWPFTable table = document.createTable();

        XWPFTableRow tableRowOne = table.getRow(0);
        //todo вот здесь идут label
//        tableRowOne.getCell(0).setText();
//        tableRowOne.addNewTableCell().setText();
//        tableRowOne.addNewTableCell().setText();
    }

    public void createDocFile(int i) throws IOException {
        FileOutputStream out = new FileOutputStream(new File("test_scenario_" + i + ".docx"));
        document.write(out);
        out.close();
    }
}