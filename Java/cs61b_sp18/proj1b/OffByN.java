public class OffByN implements CharacterComparator {

    private int distanceN;

    public OffByN(int n){
        distanceN = n;
    }

    @Override
    public boolean equalChars(char x, char y) {
        return x - y == distanceN || y - x == distanceN;
    }
}
