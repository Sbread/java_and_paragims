package markup;

import java.util.List;

abstract public class AbstractListItem {
    private List<ListItemInterface> text;

    protected AbstractListItem(List<ListItemInterface> text) {
        this.text = text;
    }

    abstract String getOpenerTag();
    abstract String getCloserTag();

    public String toHtml(StringBuilder sb) {
        sb.append(getOpenerTag());
        for (ListItemInterface part : text) {
            part.toHtml(sb);
        }
        sb.append(getCloserTag());
        return sb.toString();
    }
}
