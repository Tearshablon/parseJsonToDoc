import allureDTO.AllureModelDTO;
import allureDTO.StepsDTO;
import docgenerator.DocGenerator;
import operationwithjson.JsonReader;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        List<AllureModelDTO> allureModel = JsonReader.getModelFromJson();
        StepsDTO stepsDTO = getStepsDTOWithoutDollorSign(allureModel.get(0).getTestStage().getSteps().get(0));


        DocGenerator docGenerator = new DocGenerator();
        docGenerator.generateDocFiles(allureModel);
    }

    public static StepsDTO getStepsDTOWithoutDollorSign(StepsDTO steps) {
        StepsDTO stepsDTO = steps;
        for (int i = 0; i < stepsDTO.getSteps().size(); i++) {
            if (stepsDTO.getSteps().get(i).getSteps().size() != 0) {
                getStepsDTOWithoutDollorSign(stepsDTO.getSteps().get(i));
            } else {
                if (stepsDTO.getSteps().get(i).getName().contains("$")) {
                    stepsDTO.getSteps().remove(i);
                }
            }
        }
        return stepsDTO;
    }
}
