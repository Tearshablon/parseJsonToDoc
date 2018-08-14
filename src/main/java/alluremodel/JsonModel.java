package alluremodel;

import java.util.List;

public class JsonModel {
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
    private List<ParameterValues> parameterValues;

    public JsonModel() {
    }

    public JsonModel(String uid, String name, String historyId, Time time, String status, Boolean flaky, List<BeforeStages> beforeStages, TestStage testStage, List<AfterStages> afterStages, List<Labels> labels, List<Parameters> parameters, List<Links> links, Boolean hidden, Boolean retry, Extra extra, String source, List<ParameterValues> parameterValues) {
        this.uid = uid;
        this.name = name;
        this.historyId = historyId;
        this.time = time;
        this.status = status;
        this.flaky = flaky;
        this.beforeStages = beforeStages;
        this.testStage = testStage;
        this.afterStages = afterStages;
        this.labels = labels;
        this.parameters = parameters;
        this.links = links;
        this.hidden = hidden;
        this.retry = retry;
        this.extra = extra;
        this.source = source;
        this.parameterValues = parameterValues;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getFlaky() {
        return flaky;
    }

    public void setFlaky(Boolean flaky) {
        this.flaky = flaky;
    }

    public List<BeforeStages> getBeforeStages() {
        return beforeStages;
    }

    public void setBeforeStages(List<BeforeStages> beforeStages) {
        this.beforeStages = beforeStages;
    }

    public TestStage getTestStage() {
        return testStage;
    }

    public void setTestStage(TestStage testStage) {
        this.testStage = testStage;
    }

    public List<AfterStages> getAfterStages() {
        return afterStages;
    }

    public void setAfterStages(List<AfterStages> afterStages) {
        this.afterStages = afterStages;
    }

    public List<Labels> getLabels() {
        return labels;
    }

    public void setLabels(List<Labels> labels) {
        this.labels = labels;
    }

    public List<Parameters> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameters> parameters) {
        this.parameters = parameters;
    }

    public List<Links> getLinks() {
        return links;
    }

    public void setLinks(List<Links> links) {
        this.links = links;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Boolean getRetry() {
        return retry;
    }

    public void setRetry(Boolean retry) {
        this.retry = retry;
    }

    public Extra getExtra() {
        return extra;
    }

    public void setExtra(Extra extra) {
        this.extra = extra;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<ParameterValues> getParameterValues() {
        return parameterValues;
    }

    public void setParameterValues(List<ParameterValues> parameterValues) {
        this.parameterValues = parameterValues;
    }
}
