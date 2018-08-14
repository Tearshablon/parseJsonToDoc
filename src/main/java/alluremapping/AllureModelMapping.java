package alluremapping;

import allureDTO.AllureModelDTO;
import alluremodel.AllureModel;

import java.util.stream.Collectors;

public class AllureModelMapping {
    public static AllureModelDTO fromJson(AllureModel allureJsonModel) {
        return new AllureModelDTO(
                allureJsonModel.getName(),
                allureJsonModel.getStatus(),
                TestStageMapping.fromJson(allureJsonModel.getTestStage()),
                allureJsonModel.getLabels()
                        .stream()
                        .map(LabelsMapping::fromJson)
                        .collect(Collectors.toList()),
                allureJsonModel.getParameters()
                        .stream()
                        .map(ParametersMapping::fromJson)
                        .collect(Collectors.toList()),
                allureJsonModel.getLinks()
                        .stream()
                        .map(LinksMapping::fromJson)
                        .collect(Collectors.toList())

        );
    }
}
