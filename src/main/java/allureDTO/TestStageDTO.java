package allureDTO;

import java.util.List;

public class TestStageDTO {
    private String status;
    private List<StepsDTO> steps;
    private List<ParametersDTO> parameters;

    public TestStageDTO() {
    }

    public TestStageDTO(String status, List<StepsDTO> steps, List<ParametersDTO> parameters) {
        this.status = status;
        this.steps = steps;
        this.parameters = parameters;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<StepsDTO> getSteps() {
        return steps;
    }

    public void setSteps(List<StepsDTO> steps) {
        this.steps = steps;
    }

    public List<ParametersDTO> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParametersDTO> parameters) {
        this.parameters = parameters;
    }
}
