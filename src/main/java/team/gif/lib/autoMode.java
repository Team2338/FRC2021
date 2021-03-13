package team.gif.lib;

public enum autoMode {

    MOBILITY_FWD(0),
    BARREL_RACING(0),
    SLALOM (0),
    BOUNCE(0);

    private int value;
    autoMode(int value) {
        this.value = value;
    }
    public int getValue(){
        return this.value;
    }
}
