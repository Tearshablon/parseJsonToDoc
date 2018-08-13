public class Time {
    private Long start;
    private Long stop;
    private Long duration;

    public Time(Long start, Long stop, Long duration) {
        this.start = start;
        this.stop = stop;
        this.duration = duration;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getStop() {
        return stop;
    }

    public void setStop(Long stop) {
        this.stop = stop;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
