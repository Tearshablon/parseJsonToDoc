package allureDTO;

import java.util.List;

public class JsonModelDTO {
    private String name;
    private String status;
    private List<LabelsDTO> labels;
    private List<ParametersDTO> parameters;
    private List<LinksDTO> links;

    public JsonModelDTO() {
    }

    public JsonModelDTO(String name, String status, List<LabelsDTO> labels, List<ParametersDTO> parameters, List<LinksDTO> links) {
        this.name = name;
        this.status = status;
        this.labels = labels;
        this.parameters = parameters;
        this.links = links;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<LabelsDTO> getLabels() {
        return labels;
    }

    public void setLabels(List<LabelsDTO> labels) {
        this.labels = labels;
    }

    public List<ParametersDTO> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParametersDTO> parameters) {
        this.parameters = parameters;
    }

    public List<LinksDTO> getLinks() {
        return links;
    }

    public void setLinks(List<LinksDTO> links) {
        this.links = links;
    }
}
