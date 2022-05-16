import java.util.List;

public interface HistoryManager {

    void add(Object id);

    List<Object> getHistory();

}
