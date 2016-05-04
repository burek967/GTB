package gtb.model;

/**
 * Created by angela on 4/20/16.
 */
public abstract class GraphElement<T extends GraphElementData> {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public abstract void commitSeppuku(Graph g);
    public abstract void addYourself(Graph g);
}
