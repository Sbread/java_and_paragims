package markup;

import java.util.List;

public class Strong extends Markup implements Markdown {

    public Strong(List<Markdown> text) {
        super(text);
    }

    @Override
    String getTag() {
        return "__";
    }

    @Override
    String getOpenerTag() {
        return "<strong>";
    }

    @Override
    String getCloserTag() {
        return "</strong>";
    }

}
