package markup;

import java.util.List;

public class OrderedList extends HTMLList {

    public OrderedList(List<ListItem> items) {
        super(items);
    }

    @Override
    String getOpenerTag() {
        return "<ol>";
    }

    @Override
    String getCloserTag() {
        return "</ol>";
    }
}
