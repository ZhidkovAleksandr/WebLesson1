package Task1_5.Entity.Helpers;

public enum Multiplying {
    LEAFS("leafs"),
    CUTTINGS("cuttings"),
    SEEDS("seeds");

    private String title;

    Multiplying(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
