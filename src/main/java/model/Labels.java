package model;

public class Labels {
    private String name;
    private String value;

    public Labels(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Labels() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
