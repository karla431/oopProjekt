import net.bytebuddy.asm.Advice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;


public class Main {
    static void muuda(){

    }
    static void eemalda(){
        Scanner input = new Scanner(System.in);
        System.out.println("Millist tundi eemaldada soovid?");
        String tunniNimi = input.nextLine();
        if (tunniNimi.isEmpty())
            return;
        Iterator<Tund> lugeja = Tund.olemasolevadTunnid.iterator();
        while(lugeja.hasNext()){
            if(!lugeja.next().getNimi().equals(tunniNimi)) {//Kontrollitakse, kas on tundi mida eemaldada
                System.out.println("Sellise nimega tundi pole sinu tunniplaanis!");
                break;//Kui tundi pole tunniplaanis või tehti typo, siis väljutakse tsüklist.
            }
            else {//Kui tunniplaanis on see tund olemas siis eemaldatakse see olemasolevatest.
                Tund.olemasolevadTunnid.removeIf(n -> (n.getNimi().equals(tunniNimi)));//Eemaldab sisendiga samanimelise tunni olemasolevatest tundidest.
                System.out.println("Tund \"" + tunniNimi + "\" eemaldatud!");
            }
        }
    }
    
    static void vaata() throws ParseException {
        if (Tund.olemasolevadTunnid.size() == 0) {//Kui tunde pole lisatud, siis teadvustame
            System.out.println("Hetkel pole tunde lisatud.");
            return;
        }
        System.out.println("Hetkel olemasolevad tunnid on: ");
        for (Tund tund:Tund.olemasolevadTunnid) {
            System.out.println(tund.toString());
        }
        Set<String> vajaminevadAsjad = new HashSet<>();
        String õhtuStr = "20:00:00";
        LocalTime õhtu = LocalTime.parse(õhtuStr);
        int päevaNr = DayOfWeek.from(LocalDate.now()).getValue();
        if(LocalTime.now().isAfter(õhtu)){ //Kui kell on hiljem kui 20:00, näidatakse sulle juba homset tunniplaani tänase asemel.
            päevaNr--; //Kuna tegu on ISO-8601 standardiga, siis esmaspäev = 1 ja pühapäev = 7. Lahutatakse üks, et päevad kattuksid tunniplaani maatriksi indeksitega.
            System.out.println("\nHomsed tunnid on: ");
            for (Tund tund: Tund.tunniplaan.get(päevaNr)) {
                System.out.println(tund.getNimi() + " - " + tund.getVajalikudAsjad());
                vajaminevadAsjad.addAll(tund.getVajalikudAsjad());
            }
        }
        else{
            System.out.println("\nTänased tunnid on:");
            for (Tund tund: Tund.tunniplaan.get(päevaNr)) {
                System.out.println(tund.getNimi() + " - " + tund.getVajalikudAsjad());
                vajaminevadAsjad.addAll(tund.getVajalikudAsjad());
            }
        }
        System.out.println("\nVajaminevad esemed on: ");
        for (String asi: vajaminevadAsjad) {
            System.out.println(asi);
        }
    }
    static void suvaline(){

    }


    static void lisa(){
        Scanner input = new Scanner(System.in);
        System.out.println("Sisesta tunni nimi: ");
        String tunniNimi = input.nextLine(); //Luuakse sõne tunniNimi, millele omistatakse sisendi väärtus.
        if (tunniNimi.isEmpty())
            return;
        System.out.println("Sisesta kõik päevad üks haaval (päeva esimene täht), pärast igat sisestust vajuta enter. \n" +
                "Kui rohkem päevasid sisestada ei soovi, vajuta enter ilma midagi sisestamata.");
        Set<String> päevad = new HashSet<>(); //Luuakse set päevade jaoks, mil tund toimub, selleks, et ühte päeva mitu korda lisada ei saaks.
        while(true){ //While tsükkel, millega saab sisestada nii palju päevi, kui isikul vaja on.
            //TODO Kui midagi on juba sisestatud ja sisestatakse uuesti, siis see hoopis eemaldab selle setist... Tglt see vist on ebavajalik.
            System.out.println("Sisesta päev, mil tund toimub: ");
            String sisend = input.nextLine().toUpperCase();
            if(sisend.equals("")){ //Kui sisend on tühi, väljutakse tsüklist.
                break;
            }
            else if(Arrays.asList(new String[]{"E","T","K","N","R","L","P"}).contains(sisend)){ //Luuakse List kõikidest kehtivatest sisenditest ning kontrollitakse, kas sisestatud sisend kuulub sobivate sisendite hulka.
                päevad.add(sisend);
                System.out.println("Päev '"+sisend+"' on lisatud!"); //Kui sisend on õige, lisatakse see setti.
            }
            else{
                System.out.println("Sisend on väär!");
            }
        }
        Set<String> esemed = new HashSet<>();

        while(true){ //While tsükkel esmete sisestamiseks.
            System.out.println("Sisesta ese, mida kaasa võtma pead (kui rohkem midagi sisestada ei soovi, jäta lahter tühjaks): ");
            String sisend = input.nextLine();
            if(sisend.equals("")){ //Kui sisend on tühi, välju tsüklist.
                break;
            }
            esemed.add(sisend);
        }
        boolean õnnestus = Tund.lisaTund(tunniNimi,päevad,esemed); //Antud tundi proovitakse lisada isendi staatilise meetodiga.
        if(õnnestus == false){ //Kui meetod tagastab väär, tähendab, et antud nimega tund juba eksisteerib.
            System.out.println("Lisamine ei õnnestunud!\n" +
                    "Taolise nimega tund juba eksisteerib!");
        }
        else{
            System.out.println("Lisamine õnnestus!"); //Kui tõene, siis järelikult tunni lisamine õnnestus.
        }
    }


    public static void main(String[] args) throws ParseException {
        for (int i = 0; i < 7; i++) { //Luuakse 7 ArrayListi tunniplaani sisse, mis tähistavad erinevaid päevasid.
            Tund.tunniplaan.add(new ArrayList<>());
        }
        Scanner input = new Scanner(System.in);
        while(true){
            System.out.println("\nMida soovid teha? (Sisesta number)\n" +
                    "1) Lisa tund\n" +
                    "2) Muuda tundi\n" +
                    "3) Eemalda tund\n" +
                    "4) Vaata tunniplaani\n" +
                    "5) Viska täringut\n" +
                    "6) Välju programmist");
            String sisend = input.nextLine();
            if(Arrays.asList(new String[]{"1","2","3","4","5","6"}).contains(sisend)){
                if (sisend.equals("1")){ //Siin vastavalt sisendile käivitatakse meetod.
                    lisa();
                }
                else if (sisend.equals("2")) {
                    muuda();
                }
                else if (sisend.equals("3")) {
                    eemalda();
                }
                else if (sisend.equals("4")) {
                    vaata();
                }
                else if (sisend.equals("5")) {
                    suvaline();
                }
                else if (sisend.equals("6")) {
                    break;
                }
            }
            else{
                System.out.println("Väär sisend!"); //Kui sisend ei ole 1,2,3,4,5 või 6, siis antakse vastuseks error.
            }
        }
    }
}
