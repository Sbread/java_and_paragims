package markup;

import java.util.List;

abstract public class HTMLList implements ListItemInterface {
    protected List<ListItem> parts;

    protected HTMLList(List<ListItem> parts) {
        this.parts = parts;
    }

    abstract String getOpenerTag();

    abstract String getCloserTag();

    @Override
    public void toHtml(StringBuilder sb) {
        sb.append(getOpenerTag());
        for (ListItem part : parts) {
            part.toHtml(sb);
        }
        sb.append(getCloserTag());
    }
}
