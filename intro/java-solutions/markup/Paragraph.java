package markup;

import java.util.List;

public class Paragraph extends Markup implements ListItemInterface {

    public Paragraph(List<Markdown> text) {
        super(text);
    }

    @Override
    String getTag() {
        return "";
    }

    @Override
    String getOpenerTag() {
        return "";
    }

    @Override
    String getCloserTag() {
        return "";
    }

}
