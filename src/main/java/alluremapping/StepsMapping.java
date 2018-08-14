package alluremapping;

import allureDTO.StepsDTO;
import alluremodel.Steps;

import java.util.stream.Collectors;

public class StepsMapping {
    public static StepsDTO fromJson(Steps steps) {
        return new StepsDTO(
                steps.getName(),
                steps.getParameters().
                        stream().
                        map(ParametersMapping::fromJson)
                        .collect(Collectors.toList()),
                steps.getSteps().
                        stream()
                        .map(StepsMapping::fromJson)
                        .collect(Collectors.toList())
        );
    }
}
