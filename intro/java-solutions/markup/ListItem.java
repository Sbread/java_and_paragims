package markup;

import java.util.List;

public class ListItem extends AbstractListItem {

    public ListItem(List<ListItemInterface> items) {
        super(items);
    }

    @Override
    String getOpenerTag() {
        return "<li>";
    }

    @Override
    String getCloserTag() {
        return "</li>";
    }
}
