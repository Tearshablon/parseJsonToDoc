package model;

import java.util.List;

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
