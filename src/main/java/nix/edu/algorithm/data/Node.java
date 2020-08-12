package nix.edu.algorithm.data;

public class Node {

    final private int id;
    final private String name;

    public Node(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == 0) ? 0 : Integer.hashCode(id));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Node other = (Node) obj;
        if (id == 0) {
            if (other.id != 0)
                return false;
        } else if (!Integer.valueOf(id).equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
}
