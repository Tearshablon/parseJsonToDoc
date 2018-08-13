package model;

import java.util.List;

public class Steps {
    private String name;
    private Time time;
    private String status;
    private List<Steps> steps;
    private List<Attachments> attachments;
    private List<Parameters> parameters;
    private Long stepsCount;
    private Long attachmentsCount;
    private Boolean shouldDisplayMessage;
    private Boolean hasContent;

    public Steps() {
    }

    public Steps(String name, Time time, String status, List<Steps> steps, List<Attachments> attachments, List<Parameters> parameters, Long stepsCount, Long attachmentsCount, Boolean shouldDisplayMessage, Boolean hasContent) {
        this.name = name;
        this.time = time;
        this.status = status;
        this.steps = steps;
        this.attachments = attachments;
        this.parameters = parameters;
        this.stepsCount = stepsCount;
        this.attachmentsCount = attachmentsCount;
        this.shouldDisplayMessage = shouldDisplayMessage;
        this.hasContent = hasContent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Steps> getSteps() {
        return steps;
    }

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
    }

    public List<Attachments> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachments> attachments) {
        this.attachments = attachments;
    }

    public List<Parameters> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameters> parameters) {
        this.parameters = parameters;
    }

    public Long getStepsCount() {
        return stepsCount;
    }

    public void setStepsCount(Long stepsCount) {
        this.stepsCount = stepsCount;
    }

    public Long getAttachmentsCount() {
        return attachmentsCount;
    }

    public void setAttachmentsCount(Long attachmentsCount) {
        this.attachmentsCount = attachmentsCount;
    }

    public Boolean getShouldDisplayMessage() {
        return shouldDisplayMessage;
    }

    public void setShouldDisplayMessage(Boolean shouldDisplayMessage) {
        this.shouldDisplayMessage = shouldDisplayMessage;
    }

    public Boolean getHasContent() {
        return hasContent;
    }

    public void setHasContent(Boolean hasContent) {
        this.hasContent = hasContent;
    }
}
