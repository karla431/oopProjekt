import java.util.*;

public class Tund {
    private String nimi;
    private Set<String> päevad;
    private Set<String> vajalikudAsjad;
    private static Tund[][] tunniplaan;
    private static Set<Tund> olemasolevadTunnid = new HashSet<>();

    public Tund(String nimi, Set<String> päevad, Set<String> vajalikudAsjad) {
        this.nimi = nimi;
        this.päevad = päevad;
        this.vajalikudAsjad = vajalikudAsjad;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public Set<String> getPäevad() {
        return päevad;
    }

    public void setPäevad(Set<String> päevad) {
        this.päevad = päevad;
    }

    public Set<String> getVajalikudAsjad() {
        return vajalikudAsjad;
    }

    public void setVajalikudAsjad(Set<String> vajalikudAsjad) {
        this.vajalikudAsjad = vajalikudAsjad;
    }

    @Override
    public String toString() {
        return "Tund{" +
                "nimi='" + nimi + '\'' +
                ", päevad='" + päevad + '\'' +
                ", vajalikudAsjad=" + vajalikudAsjad +
                '}';
    }

    public static boolean lisaTund(String nimi, Set<String> päevad, Set<String> asjad){
        Iterator<Tund> lugeja = olemasolevadTunnid.iterator();
        while(lugeja.hasNext()){
            if(lugeja.next().getNimi() == nimi){
                return false;
            }
        }
        olemasolevadTunnid.add(new Tund(nimi,päevad,asjad));
        return true;
    }
}
