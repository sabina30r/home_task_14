package nix.edu.model;

public class Route {

    private int id;
    private int fromId;
    private int toId;
    private int cost;

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
