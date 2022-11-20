package markup;

import java.util.List;

public class Strikeout extends Markup implements Markdown {

    public Strikeout(List<Markdown> text) {
        super(text);
    }

    @Override
    String getTag() {
        return "~";
    }

    @Override
    String getOpenerTag() {
        return "<s>";
    }

    @Override
    String getCloserTag() {
        return "</s>";
    }
}
