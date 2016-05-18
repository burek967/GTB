package gtb.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by angela on 4/20/16.
 */
public abstract class GraphElement<T extends GraphElementData> implements Serializable{
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public abstract List<? extends GraphElement> removeYourself(Graph g);

    public abstract void addYourself(Graph g);
}
