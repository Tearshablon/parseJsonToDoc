package alluremapping;

import allureDTO.ParametersDTO;
import alluremodel.Parameters;

public class ParametersMapping {
    public static ParametersDTO fromJson(Parameters parameters) {
        return new ParametersDTO(
                parameters.getName(),
                parameters.getValue()
        );
    }

    public static ParametersDTO getPlainParametersDTO(ParametersDTO parametersDTO) {
        return new ParametersDTO(
                parametersDTO.getName(),
                parametersDTO.getValue()
        );
    }
}
