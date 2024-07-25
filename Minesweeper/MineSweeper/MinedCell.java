package MineSweeper;

import java.util.Dictionary;
import java.util.Hashtable;

public class MinedCell extends Cell{

    private int damage;

    public MinedCell(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return this.damage;
    }

    public int reveal() {
        this.setRevealed(true);
        return this.damage;
    }

    public String toString() {
        if (this.isRevealed())
            if(damage == 1)
                return ("\uD83E\uDDE8");
            else if(damage == 2)
                return ("\uD83D\uDCA3");
            else if(damage == 3)
                return ("\uD83D\uDCA5");
        return super.toString();
    }
}
