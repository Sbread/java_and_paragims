package markup;

import java.util.List;

abstract public class Markup {
    private List<Markdown> text;

    protected Markup(List<Markdown> text) {
        this.text = text;
    }

    abstract String getTag();

    abstract String getOpenerTag();

    abstract String getCloserTag();

    public void toMarkdown(StringBuilder sb) {
        sb.append(getTag());
        for (Markdown part : text) {
            part.toHtml(sb);
        }
        sb.append(getTag());
    }

    public void toHtml(StringBuilder sb) {
        sb.append(getOpenerTag());
        for (Markdown part : text) {
            part.toHtml(sb);
        }
        sb.append(getCloserTag());
    }
}
