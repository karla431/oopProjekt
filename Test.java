import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        Set<String> Karma = new HashSet<>();
        Karma.add("Pidar");
        Karma.add("Pidar");
        System.out.println(Karma);

        // Pärdi testid 14/03
        Set<Tund> olemasolevadTunnid = new HashSet<>();
        Set<String> päevad = new HashSet<>();
        päevad.add("T");
        päevad.add("N");
        Set<String> esemed = new HashSet<>();
        esemed.add("Sülearvuti");
        esemed.add("Pastakas");

        Tund Mata = new Tund("Matemaatika", päevad, esemed);
        Tund Mata1 = new Tund("Matemaatika1", päevad, esemed);
        olemasolevadTunnid.add(Mata);
        olemasolevadTunnid.add(Mata1);
        olemasolevadTunnid.removeIf(n -> (n.getNimi().equals("Matemaatika")));
        System.out.println(olemasolevadTunnid);
    }
}
