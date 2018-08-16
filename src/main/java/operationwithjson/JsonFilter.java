package operationwithjson;

import allureDTO.LabelsDTO;
import allureDTO.LinksDTO;
import allureDTO.StepsDTO;

import java.util.List;
import java.util.stream.Collectors;

public class JsonFilter {

    public static String getValueFromLabelByName(List<LabelsDTO> labels, String name) {
        return labels.stream().filter(i -> i.getName().equals(name)).findFirst().get().getValue();
    }

    public static String getUrlToKbFromLinksByType(List<LinksDTO> links, String type) {
        return links.stream().filter(i -> i.getType().equals(type)).findFirst().get().getUrl();
    }

    /**
     * Возвращает StepsDTO без шага setup
     * @param steps
     * @return
     */
    public static StepsDTO getStepsDTOWithoutSetupStep(StepsDTO steps) {
        StepsDTO stepsDTO = new StepsDTO();
        stepsDTO.setSteps(steps.getSteps().stream().skip(1).collect(Collectors.toList()));
        return stepsDTO;
    }


    /**
     * Возвращает StepsDTO без шага setup
     * @param parentSteps StepsDto c вложенными StepsDto
     * @return плоский StepsDto
     */
    public static StepsDTO getPlainStepsWithoutSetupStep(StepsDTO parentSteps) {
        for (StepsDTO childSteps : parentSteps.getSteps()) {
            childSteps.setSteps(null);
        }
        StepsDTO stepsDTO = new StepsDTO();
        stepsDTO.setSteps(parentSteps.getSteps().stream().skip(1).collect(Collectors.toList()));
        return stepsDTO;
    }

    public static StepsDTO getStepsDTOWithoutDollarSign(StepsDTO steps) {
        StepsDTO stepsDTO = steps;
        for (int i = 0; i < stepsDTO.getSteps().size(); i++) {
            if (stepsDTO.getSteps().get(i).getSteps().size() != 0) {
                stepsDTO.getSteps().get(i).getSteps().removeIf(stepsDTO1 -> stepsDTO1.getName().contains("$"));
                getStepsDTOWithoutDollarSign(stepsDTO.getSteps().get(i));
            }
        }
        return stepsDTO;
    }
}
