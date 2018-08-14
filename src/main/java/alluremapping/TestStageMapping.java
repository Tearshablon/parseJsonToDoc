package alluremapping;

import allureDTO.TestStageDTO;
import alluremodel.TestStage;

import java.util.stream.Collectors;

public class TestStageMapping {
    public static TestStageDTO fromJson(TestStage testStage) {
        return new TestStageDTO(
                testStage.getStatus(),
                testStage.getSteps()
                        .stream()
                        .map(StepsMapping::fromJson)
                        .collect(Collectors.toList()),
                testStage.getParameters()
                        .stream()
                        .map(ParametersMapping::fromJson)
                        .collect(Collectors.toList())

        );
    }
}
