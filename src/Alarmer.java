import java.util.Calendar;

public class Alarmer implements Runnable {
    private final Framer framer;
    private final Thread thr;
    private final String minut, hour, str;

    Alarmer(String minut, String hour, String str, Framer framer) {
        thr = new Thread(this);
        this.framer = framer;
        this.minut = minut;
        this.hour = hour;
        this.str = str;
    }

    public Thread getThr() {
        return thr;
    }

    @Override
    public void run() {
        int milWait = 0;
        Calendar datenow, dateAlarm;
        dateAlarm = Calendar.getInstance();
        dateAlarm.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        dateAlarm.set(Calendar.MINUTE, Integer.parseInt(minut));
        dateAlarm.set(Calendar.SECOND, 0);
        datenow = Calendar.getInstance();
        if ((datenow.before(dateAlarm)) || (datenow.equals(dateAlarm))) {
            milWait += (int) (dateAlarm.getTimeInMillis() - datenow.getTimeInMillis());
            FileManagerAlarm fm = framer.getFm();
            fm.FileWrite(minut, hour, str);

            if (milWait < 1000) milWait = 1000;

            try {
                Thread.sleep(milWait);
                framer.AudioStart();
                framer.txtSetText(str);
            } catch (InterruptedException ea) {
                framer.txtSetText("Ошибка с будильником");
            }
        } else framer.txtAppendText(" В прошлое будильник не звонит))))");
    }
}
