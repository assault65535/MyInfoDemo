package Bean;

/**
 * Created by Nero on 2016/12/29.
 */
public class Container<T> {
    private T o;

    public Container() {}

    public Container(T o) {
        this.o = o;
    }

    public T getValue() {
        return o;
    }

    public void setValue(T o) {
        this.o = o;
    }
}
