import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class Framer extends JFrame implements ActionListener {
    static ClassLoader ldr = Framer.class.getClassLoader();
    static java.applet.AudioClip audio = JApplet.newAudioClip(ldr.getResource( "trubach.wav" ));
    Font mfont = new Font("Verdana",Font.PLAIN,20);
    Container cont = getContentPane();
    String col_Hour ="";
    String col_Minut ="";
    JPanel panup = new JPanel();
    JPanel pancenter = new JPanel();
    JButton inserTime = new JButton("Установить время");
    JButton jbnOk = new JButton("Напомнить");
    JButton jbnCancel = new JButton("Отключить");
    String[] hours = new String[24];
    String[] minutes = new String[60];
    JComboBox<String> hoursbox = new JComboBox<>(qwer(24,hours));
    JComboBox<String> minutesbox = new JComboBox<>(qwer(60,minutes));
    public static JTextArea txtof = new JTextArea(5,30);
    JLabel flbl = new JLabel("Когда напомнить: ");
    JLabel hlbl = new JLabel("Часы=");
    JLabel mlbl = new JLabel("Минуты=");
    JLabel comment = new JLabel("Комментарий:");

    public String[] qwer(int value, String[] strings){
        for (int h = 0; h < value; h++ ) strings[h] = "" + h;
        return strings;
    }

        public Framer() {
            super("Напоминалка");
            setSize(400,300);
            setResizable(false);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            hoursbox.setFont(mfont);
            minutesbox.setFont(mfont);
            add(panup);
            pancenter.add(inserTime);
            pancenter.add(jbnOk);
            pancenter.add(jbnCancel);
            pancenter.add(comment);
            inserTime.setOpaque(true);
            inserTime.setBackground(Color.orange);
            inserTime.addActionListener(this);
            jbnOk.setOpaque(true);
            jbnOk.setBackground(new Color(50,200,50));
            jbnOk.addActionListener( this);
            jbnCancel.setOpaque(true);
            jbnCancel.setBackground(new Color(255,100,100));
            jbnCancel.addActionListener( this);
            pancenter.add(txtof);
            panup.add(flbl);
            panup.add(hlbl);
            panup.add(hoursbox);
            panup.add(mlbl);
            panup.add(minutesbox);
            cont.add("Center", pancenter);
            //cont.add("South",panug);
            cont.add("North", panup);
            setVisible(true);
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        String str;
        if (e.getSource()==jbnOk) {
            col_Hour = (String)hoursbox.getSelectedItem();
            col_Minut = (String)minutesbox.getSelectedItem();
            str = txtof.getText();
            Alarmer al = new Alarmer(col_Minut,col_Hour,str);
            al.thr.start();
            txtof.setText("");
        }
        if (e.getSource()==jbnCancel){
            txtSetText("");
            AudioStop();
        }
        if(e.getSource()==inserTime){
            int minute,hour;
            Calendar cal = Calendar.getInstance();
            minute = cal.get(Calendar.MINUTE);
            hour = cal.get(Calendar.HOUR_OF_DAY);
            setHourbox(hour);
            setMinutesbox(minute);
        }
    }
    public static void txtSetText(String str){
        txtof.setText(str);
    }
    public static void txtAppendText(String str){
        txtof.append(str);
    }
    public static void AudioStart(){
        audio.loop();
    }
    public static void AudioStop(){
        audio.stop();
    }
    public void setHourbox (int hour){
        hoursbox.setSelectedIndex(hour);
    }
    public void setMinutesbox (int minutes){
        minutesbox.setSelectedIndex(minutes);
    }
}
