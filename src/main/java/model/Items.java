package model;

public class Items {
    private String uid;
    private String reportUrl;
    private String status;
    private Time time;

    public Items() {
    }

    public Items(String uid, String reportUrl, String status, Time time) {
        this.uid = uid;
        this.reportUrl = reportUrl;
        this.status = status;
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
