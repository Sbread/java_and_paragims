package expression.parser;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface CharSource {
    boolean hasNext();
    boolean hasPrev();
    char next();
    char prev();
    IllegalArgumentException error(final String message);
}
