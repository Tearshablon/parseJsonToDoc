package alluremapping;

import allureDTO.LabelsDTO;
import alluremodel.Labels;

public class LabelsMapping {
    public static LabelsDTO fromJson(Labels labels) {
        return new LabelsDTO(
                labels.getName(),
                labels.getValue()
        );
    }
}
