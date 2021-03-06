import net.bytebuddy.asm.Advice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;


public class Main {
    static void loeFailist() {
        try (java.util.Scanner sc = new java.util.Scanner(new File("db.txt"), "UTF-8")){
            while (sc.hasNextLine()) {
                String rida = sc.nextLine();
                String[] tükid = rida.split("; ");
                String[] päevad = tükid[1].split(", ");
                String[] asjad = tükid[2].split(", ");
                Set<String> asjadSet = new HashSet<>(Arrays.asList(asjad));
                Set<String> päevadSet = new HashSet<>(Arrays.asList(päevad));
                Tund.lisaTund(tükid[0],päevadSet,asjadSet);
                }
            } catch (FileNotFoundException e) {
            return;
        }
    }

        static void kirjutaFaili() throws IOException {
            File fail = new File("db.txt");
            fail.createNewFile();
            FileWriter kirjutaja = new FileWriter("db.txt");
            for (Tund tund: Tund.olemasolevadTunnid) {
                kirjutaja.write(tund.getNimi() + "; ");
                int lugeja = 0;
                for (String päev: tund.getPäevad()) {
                    kirjutaja.write(päev);
                    if (lugeja != tund.getPäevad().size()-1){
                        kirjutaja.write(", ");
                    }
                    lugeja++;
                }
                lugeja = 0;
                kirjutaja.write("; ");
                for (String asi: tund.getVajalikudAsjad()) {
                    kirjutaja.write(asi);
                    if (lugeja != tund.getVajalikudAsjad().size()-1){
                        kirjutaja.write(", ");
                    }
                    lugeja++;
                }
                kirjutaja.write("\n");
            }
            kirjutaja.close();
        }


    static void muuda(){
        Scanner input = new Scanner(System.in);
        ArrayList<Tund> tunnid = new ArrayList<>();
        tunnid.addAll(Tund.olemasolevadTunnid);
        System.out.println("Millist tundi sa muuta soovid? (Sisesta number)");
        for (int i = 0; i < tunnid.size(); i++) {
            System.out.println((i + 1) + ") " + tunnid.get(i).getNimi()); //Kõik olemasolevad tunnid väljastatakse ekraanile.
        }
        int tunniNumber = Integer.parseInt(input.nextLine()) - 1;
        if (tunniNumber < 0 || tunniNumber > tunnid.size()-1){ //Kui sisestatakse väär indeks, siis sellisel juhul väljutakse meetodist.
            System.out.println("Sisend on väär!\n");
        }
        else{ //Kui sisend on kehtiv, siis:
            System.out.println("Sisesta uus tunni nimi: ");
            String uusNimi = input.nextLine(); //Võetakse sisendina uus nimi.
            tunnid.get(tunniNumber).setNimi(uusNimi); //Omistatakse antud tunnile uus nimi.
            System.out.println("Tunni nimi on muudetud!\n");
        }
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
            System.out.println("\nHomsed tunnid on: ");
            for (Tund tund: Tund.tunniplaan.get(päevaNr)) {
                System.out.println(tund.getNimi() + " - " + tund.getVajalikudAsjad());
                vajaminevadAsjad.addAll(tund.getVajalikudAsjad());
            }
        }
        else{
            System.out.println("\nTänased tunnid on:");
            päevaNr--; //Kuna tegu on ISO-8601 standardiga, siis esmaspäev = 1 ja pühapäev = 7. Lahutatakse üks, et päevad kattuksid tunniplaani maatriksi indeksitega.
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
        System.out.println("Viskasid täringul " + Math.round(Math.random()*5+1)); //Genereeritakse suvaline number vahemikus 1-6 ja väljastatakse ekraanile
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
        while(true){ //While tsükkel, millega saab sisestada nii palju päevi, kui isikul vaja on.W
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


    public static void main(String[] args) throws ParseException, IOException {
        for (int i = 0; i < 7; i++) { //Luuakse 7 ArrayListi tunniplaani sisse, mis tähistavad erinevaid päevasid.
            Tund.tunniplaan.add(new ArrayList<>());
        }
        loeFailist();
        Scanner input = new Scanner(System.in);
        while(true){
            System.out.println("\nMida soovid teha? (Sisesta number)\n" +
                    "1) Lisa tund\n" +
                    "2) Muuda tunni nime\n" +
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
                    kirjutaFaili();
                    break;
                }
            }
            else{
                System.out.println("Väär sisend!"); //Kui sisend ei ole 1,2,3,4,5 või 6, siis antakse vastuseks error.
            }
        }
    }
}
