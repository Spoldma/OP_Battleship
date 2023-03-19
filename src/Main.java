import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        //ns Väli isend
        Väli mäng = new Väli();

        //kui suur väli + arvuti genereeritud väljad
        int[] väärtused = küsiSuurusPaat();
        while (true) {
            if (kontrolliVäärtused(väärtused)) {
                System.out.println("Sisestati sobivad väärtused!");
                break;
            }
            System.out.println("Sisestati sobimatud väärtused! Proovi uuesti!");
            väärtused = küsiSuurusPaat();
        }
        int väljaSuurus = väärtused[0];
        int paatideArv = väärtused[1];
        MänguVäli pcBoolean = new MänguVäli(teeVäliBoolean(väljaSuurus, paatideArv));
        KuvaVäli pcString = new KuvaVäli(teeVäliString(väljaSuurus));

        //playeri väli
        MänguVäli playerBoolean = new MänguVäli(teeVäliBoolean(väljaSuurus, 0));
        paigutaLaevad(playerBoolean, paatideArv);
        KuvaVäli playerString = new KuvaVäli(teeVäliString(playerBoolean.getVäli()));

        //Mäng algab
        System.out.println("Mäng algab!");
        System.out.println("Vasakul: sinu laevad; Paremal: vastase laevad");
        mäng.prindiVäli(playerString.getVäli(), pcString.getVäli());
        küsiLasku(playerBoolean, paatideArv);
    }

    //genereerib suvalise n*n boolean maatriksi, milles etteantud arv ühtesi
    public static boolean[][] teeVäliBoolean(int n, int laevu) {
        boolean[][] väli = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                väli[i][j] = false;
            }
        }
        for (int i = 1; i <= laevu; i++) {
            while (true) {
                int random1 = (int) (Math.random() * (n));
                int random2 = (int) (Math.random() * (n));
                if (!väli[random1][random2]) {
                    väli[random1][random2] = true;
                    break;
                }

            }
        }
        return väli;
    }

    //teeb uue maatriksi vastavalt etteantud boolean maatriksile, kus true="+" ja false="-"
    public static String[][] teeVäliString(boolean[][] x) {
        int n = x.length;
        String[][] väli = new String[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (x[i][j])
                    väli[i][j] = "+";
                else
                    väli[i][j] = "-";
            }

        }
        return väli;
    }

    //loob n*n maatriksi mille kõikidel kohtadel "."
    public static String[][] teeVäliString(int n) {
        String[][] väli = new String[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                väli[i][j] = ".";
            }
        }
        return väli;
    }

    //küsib mängijalt välja suuruse ja paatide arvu
    public static int[] küsiSuurusPaat() throws IOException {
        int[] väärtused = new int[2];

        BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Sisesta välja suurus (2 - 10): ");
        String suurus = reader1.readLine();
        väärtused[0] = Integer.parseInt(suurus);
        System.out.println();

        BufferedReader reader2 = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Sisesta paatide arv (0 - suurus**2): ");
        String paatideArv = reader2.readLine();
        väärtused[1] = Integer.parseInt(paatideArv);
        System.out.println();

        return väärtused;
    }

    public static void küsiLasku(MänguVäli pcBoolean, int paate) throws IOException {
        int väljaSuurus = pcBoolean.getVäli().length;
        int paadid = paate;
        int rida = 0;
        int veerg = 0;
        while (paadid > 0) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Sisesta laskmiseks koordinaadid (rida,veerg): ");
            String[] koordinaadid = reader.readLine().split(",");
            rida = Integer.parseInt(koordinaadid[0]);
            veerg = Integer.parseInt(koordinaadid[1]);
            if (rida < väljaSuurus && veerg < väljaSuurus && pcBoolean.pihtaMööda(rida, veerg)) {
                pcBoolean.eemaldaPaat(rida, veerg);
                System.out.println("Pihtas");
                paadid--;
            } else if (rida < väljaSuurus && Integer.parseInt(koordinaadid[1]) < väljaSuurus) {
                System.out.println("Lasid mööda");
            } else {
                System.out.println("Sisestatud koordinaadid pole väljal! Proovi uuesti!");
            }
        }
    }

    //kontrollib kas mängija antud väärtused sobivad
    public static boolean kontrolliVäärtused(int[] väärtused) {
        return väärtused[0] >= 2 && väärtused[0] <= 10 && väärtused[1] > 0 && väärtused[1] < (väärtused[0] * väärtused[0]);
    }

    //laseb mängijal paigutada oma laevad
    public static void paigutaLaevad(MänguVäli playerBoolean, int paate) throws IOException {
        System.out.println("Peate väljale paigutama " + paate + " paati!");
        int väljaSuurus = playerBoolean.getVäli().length;
        int paateAlles = paate;
        while (paateAlles > 0) {
            System.out.println("Teie praegune väli näeb välja nii: ");
            playerBoolean.prindiVäli(teeVäliString(playerBoolean.getVäli()));
            System.out.println("Paate veel sisestada: " + paateAlles);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Sisesta paadi koordinaadid (kujul rida,veerg): ");
            String[] koordinaadid = reader.readLine().split(",");
            if (Integer.parseInt(koordinaadid[0]) < väljaSuurus && Integer.parseInt(koordinaadid[1]) < väljaSuurus) {
                playerBoolean.lisaPaat(Integer.parseInt(koordinaadid[0]), Integer.parseInt(koordinaadid[1]));
                System.out.println();
                paateAlles--;
            } else {
                System.out.println("Sisestatud koordinaadid pole väljal! Proovi uuesti!");
            }
        }
    }
}
