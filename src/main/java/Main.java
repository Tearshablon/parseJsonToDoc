import allureDTO.AllureModelDTO;
import docgenerator.DocGenerator;
import operationwithjson.JsonReader;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        List<AllureModelDTO> allureModel = JsonReader.getModelFromJson();

        DocGenerator docGenerator = new DocGenerator();
        docGenerator.generateDocFiles(allureModel);
    }


}
