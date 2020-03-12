import java.util.*;

public class Main {

    static void lisa(){
        Scanner input = new Scanner(System.in);
        System.out.println("Sisesta tunni nimi: ");
        String tunniNimi = input.nextLine();
        System.out.println("Sisesta kõik päevad üks haaval (päeva esimene täht), pärast igat sisestust vajuta enter. \n" +
                "Kui rohkem päevasid sisestada ei soovi, vajuta enter ilma midagi sisestamata.");
        Set<String> päevad = new HashSet<>();
        while(true){
            System.out.println("Sisesta päev, mil tund toimub: ");
            String sisend = input.nextLine();
            if(sisend.equals("")){
                break;
            }
            else if(Arrays.asList(new String[]{"E","T","K","N","R","L","P"}).contains(sisend)){
                päevad.add(sisend);
                System.out.println("Päev '"+sisend+"' on lisatud!");
            }
            else{
                System.out.println("Sisend on väär!");
            }
        }
        Set<String> esemed = new HashSet<>();

        while(true){
            System.out.println("Sisesta ese, mida kaasa võtma pead (kui rohkem midagi sisestada ei soovi, jäta lahter tühjaks): ");
            String sisend = input.nextLine();
            if(sisend.equals("")){
                break;
            }
            esemed.add(sisend);
        }
        boolean õnnestus = Tund.lisaTund(tunniNimi,päevad,esemed);
        if(õnnestus == false){
            System.out.println("Lisamine ei õnnestunud!\n" +
                    "Taolise nimega tund juba eksisteerib!");
        }
        else{
            System.out.println("Lisamine õnnestus!");
        }
    }


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while(true){
            System.out.println("\nMida soovid teha? (Sisesta number)\n" +
                    "1) Lisa tund\n" +
                    "2) Muuda tundi\n" +
                    "3) Eemalda tund\n" +
                    "4) Viska täringut\n" +
                    "5) Välju programmist");
            String sisend = input.nextLine();
            if(Arrays.asList(new String[]{"1","2","3","4","5"}).contains(sisend)){
                if (sisend.equals("1")){
                    lisa();
                }
                else if (sisend.equals("5")) {
                    break;
                }
                //TODO Iga optsioon viib eraldi meetodisse.
            }
            else{
                System.out.println("Väär sisend!");
            }
        }
    }
}
