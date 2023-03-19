public class KuvaVäli extends Väli {
    private final String[][] Väli;

    public KuvaVäli(String[][] Väli) {
        this.Väli = Väli;
    }

    public String[][] getVäli() {
        return Väli;
    }
    public void uuendaVäli(int i, int j, boolean pihtas) {
        for (int k = 0; k < getVäli().length; k++) {
            for (int l = 0; l < getVäli().length; l++) {
                if (pihtas)
                    getVäli()[i][j] = "X";
                else
                    getVäli()[i][j] = "O";
            }
        }
    }
}
