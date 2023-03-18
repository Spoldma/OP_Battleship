public class MänguVäli {
    private boolean[][] väli;

    public MänguVäli(boolean[][] väli) {
        this.väli = väli;
    }

    public boolean pihtaMööda(int i, int j) {
        if (this.väli[i][j]) {
            xMeetod("X");
            this.väli[i][j] = false;
            return true;
        }
        xMeetod("O");
        return false;

    }
}
