

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class AllureModel {
    private String uid;
    private String name;
    private String historyId;
    private Time time;
    private String status;
    private Boolean flaky;
    private List<BeforeStages> beforeStages;
    private TestStage testStage;
    private List<AfterStages> afterStages;
    private List<Labels> labels;
    private List<Parameters> parameters;
    private List<Links> links;
    private Boolean hidden;
    private Boolean retry;
    private Extra extra;
    private String source;
    private ParameterValues parameterValues;
}
