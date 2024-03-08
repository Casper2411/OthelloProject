/**
 * Class for a homemade tuple
 * @author Casper Frøding, Line Præstegaard, Nayla Hauerberg
 * @version 8.3.2024
 */
public class Tuple {
    static Position pos;
    static float num;

    public Tuple(Position pos, float num) {
        this.pos = pos;
        this.num = num;
    }

    // Getters and setters
    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public static float getNum() {
        return num;
    }

    public void setNum(float num) {
        this.num = num;
    }
}