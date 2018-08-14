package alluremodel;

import java.util.List;

public class Extra {
    private String severity;
    private List<Retries> retries;
    private List<Categories> categories;
    private History history;
    private List<Tags> tags;

    public Extra() {
    }

    public Extra(String severity, List<Retries> retries, List<Categories> categories, History history, List<Tags> tags) {
        this.severity = severity;
        this.retries = retries;
        this.categories = categories;
        this.history = history;
        this.tags = tags;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public List<Retries> getRetries() {
        return retries;
    }

    public void setRetries(List<Retries> retries) {
        this.retries = retries;
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }
}
