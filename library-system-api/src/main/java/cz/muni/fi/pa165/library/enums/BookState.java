package cz.muni.fi.pa165.library.enums;

/**
 * Status of the book condition.
 *
 * @author Bedrich Said
 */
public enum BookState {
    NEW("New",0),
    LIGHT_DAMAGE("Lightly damaged",1),
    MEDIUM_DAMAGE("Moderately damaged",2),
    HEAVY_DAMAGE("Heavily damaged",3);

    private final String name;
    private final int damageValue;

    BookState(String name, int damageValue) {
        this.name = name;
        this.damageValue = damageValue;
    }

    public boolean equalsName(String otherName) {
        return otherName != null && name.equals(otherName);
    }

    public boolean isLighter(BookState other) {return this.damageValue < other.damageValue;}

    @Override
    public String toString() {
        return this.name;
    }
}
