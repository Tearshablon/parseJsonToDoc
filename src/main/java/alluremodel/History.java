package alluremodel;

import java.util.List;

public class History {
    private Statistic statistic;
    private List<Items> items;

    public History() {
    }

    public History(Statistic statistic, List<Items> items) {
        this.statistic = statistic;
        this.items = items;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public void setStatistic(Statistic statistic) {
        this.statistic = statistic;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }
}
