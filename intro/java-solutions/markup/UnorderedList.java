package markup;

import java.util.List;

public class UnorderedList extends HTMLList {

    public UnorderedList(List<ListItem> items) {
        super(items);
    }

    @Override
    String getOpenerTag() {
        return "<ul>";
    }

    @Override
    String getCloserTag() {
        return "</ul>";
    }
}
