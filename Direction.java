package moulin;

public enum Direction {
	HAUT("Haut"),
	BAS("Bas"),
	GAUCHE("Gauche"),
	DROITE("Droite");

    private final String direction;
    private Direction(String direction){
    	this.direction = direction;
    }
    public String toString() {
        return direction;
    }
}
