package operationwithjson;

import allureDTO.LabelsDTO;
import allureDTO.LinksDTO;

import java.util.List;

public class JsonFilter {
//    private static List<LabelsDTO> filterLabelByName(List<AllureModelDTO> jsonModelList) {
//        return jsonModelList
//                .stream()
//                .map(i -> i.getLabels()
//                        .stream()
//                        .filter(k -> !k.getName().equals("suite"))
//                        .filter(f -> !f.getName().equals("package"))
//                        .collect(Collectors.toList()))
//                .findAny().orElse(null);
//    }

    public static String getValueFromLabelByName(List<LabelsDTO> labels, String name) {
        return labels.stream().filter(i -> i.getName().equals(name)).findFirst().get().getValue();
    }

    public static String getUrlToKbFromLinksByType(List<LinksDTO> links, String type) {
        return links.stream().filter(i -> i.getType().equals(type)).findFirst().get().getUrl();
    }
}
