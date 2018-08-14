package alluremodel;

public class Statistic {
    private Long failed;
    private Long broken;
    private Long skipped;
    private Long passed;
    private Long unknown;
    private Long total;

    public Statistic() {
    }

    public Statistic(Long failed, Long broken, Long skipped, Long passed, Long unknown, Long total) {
        this.failed = failed;
        this.broken = broken;
        this.skipped = skipped;
        this.passed = passed;
        this.unknown = unknown;
        this.total = total;
    }

    public Long getFailed() {
        return failed;
    }

    public void setFailed(Long failed) {
        this.failed = failed;
    }

    public Long getBroken() {
        return broken;
    }

    public void setBroken(Long broken) {
        this.broken = broken;
    }

    public Long getSkipped() {
        return skipped;
    }

    public void setSkipped(Long skipped) {
        this.skipped = skipped;
    }

    public Long getPassed() {
        return passed;
    }

    public void setPassed(Long passed) {
        this.passed = passed;
    }

    public Long getUnknown() {
        return unknown;
    }

    public void setUnknown(Long unknown) {
        this.unknown = unknown;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
