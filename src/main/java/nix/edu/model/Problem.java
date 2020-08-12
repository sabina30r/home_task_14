package nix.edu.model;

public class Problem {
    private int id;
    private int fromId;
    private int toId;

    public Problem(){}

    public Problem(int id, int fromId, int toId) {
        this.id = id;
        this.fromId = fromId;
        this.toId = toId;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }
}
