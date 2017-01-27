package quarantine;

import java.util.LinkedList;

/**
 * Перый буфер хранит заданное количество строк до тех пор, пока в него не начинают
 * добавляться новые строки. При добавлении новой строки, если буфер заполнен, самая
 * старая строка удаляется, а в конец очереди добавляется новая.
 */
public class FirstBuf<String> extends LinkedList<String> {
    private int limit;

    public FirstBuf(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean add(String o) {
        super.add(o);
        while (size() > limit) {
            super.remove();
        }
        return true;
    }
}
