package operationwithjson;

import allureDTO.AllureModelDTO;
import allureDTO.LabelsDTO;
import allureDTO.LinksDTO;

import java.util.List;
import java.util.stream.Collectors;

public class JsonFilter {
    private static List<LabelsDTO> filterLabelByName(List<AllureModelDTO> jsonModelList) {
        return jsonModelList
                .stream()
                .map(i -> i.getLabels()
                        .stream()
                        .filter(k -> !k.getName().equals("suite"))
                        .filter(f -> !f.getName().equals("package"))
                        .collect(Collectors.toList()))
                .findAny().orElse(null);
    }

    public static String getEpicFromLabelByName(List<LabelsDTO> labels) {
        return labels.stream().filter(i -> i.getName().equals("epic")).findFirst().get().getValue();
    }

    public static String getStoryFromLabelName(List<LabelsDTO> labels) {
        return labels.stream().filter(i -> i.getName().equals("story")).findFirst().get().getValue();
    }

    public static String getSeverityFromLabelName(List<LabelsDTO> labels) {
        return labels.stream().filter(i -> i.getName().equals("severity")).findFirst().get().getValue();
    }

    public static String getUrlToKbFromLinksByType(List<LinksDTO> links) {
        return links.stream().filter(i -> i.getType().equals("tms")).findFirst().get().getUrl();
    }
}
