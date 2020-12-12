
public class Mane {
    public static void main(String[] args) {
        Framer framer = new Framer();
        FileManagerAlarm fm = new FileManagerAlarm();
        framer.setFm(fm);
        fm.setFramer(framer);
        fm.FileRead();
    }
}
