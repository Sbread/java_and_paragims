package markup;

import java.util.List;

public class Emphasis extends Markup implements Markdown {

    private static final String tag = "*";
    private static final String openerTag = "<em>";
    private static final String closerTag = "</em>";

    public Emphasis(List<Markdown> text) {
        super(text);
    }

    @Override
    String getTag() {
        return "*";
    }

    @Override
    String getOpenerTag() {
        return "<em>";
    }

    @Override
    String getCloserTag() {
        return "</em>";
    }
}
