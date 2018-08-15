package allureDTO;

import java.util.List;

public class StepsDTO {
    private String name;
    private List<ParametersDTO> parameters;
    private List<StepsDTO> steps;

    public StepsDTO() {
    }

    public StepsDTO(String name, List<ParametersDTO> parameters, List<StepsDTO> steps) {
        this.name = name;
        this.parameters = parameters;
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ParametersDTO> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParametersDTO> parameters) {
        this.parameters = parameters;
    }

    public List<StepsDTO> getSteps() {
        return steps;
    }

    public void  setSteps(List<StepsDTO> steps) {
        this.steps = steps;
    }
}
