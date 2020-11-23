import javax.swing.*;

public class Mane {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Framer::new);
        FileManagerAlarm fm = new FileManagerAlarm();
        fm.FileRead();
    }
}
