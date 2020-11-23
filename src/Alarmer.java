import java.util.Calendar;

public class Alarmer implements Runnable {
    Thread thr;
    String minut,hour,str;
    Calendar datenow,dateAlarm;

    Alarmer(String minut, String hour,String str){
        thr = new Thread(this);
        this.minut = minut;
        this.hour = hour;
        this.str = str;
    }

    @Override
    public void run() {
        int milWait = 0;
        dateAlarm = Calendar.getInstance();
        dateAlarm.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hour));
        dateAlarm.set(Calendar.MINUTE,Integer.parseInt(minut));
        dateAlarm.set(Calendar.SECOND,0);
        datenow = Calendar.getInstance();
        if((datenow.before(dateAlarm))||(datenow.equals(dateAlarm))) {
            milWait += (int) (dateAlarm.getTimeInMillis() - datenow.getTimeInMillis());
            FileManagerAlarm fmA = new FileManagerAlarm();
            fmA.FileWrite(minut,hour,str);

            if (milWait < 1000) milWait = 1000;

            try {
                Thread.sleep(milWait);
                Framer.AudioStart();
                Framer.txtSetText(str);
            } catch (InterruptedException ea) {
                Framer.txtSetText("Ошибка с будильником");
            }
        }else Framer.txtAppendText(" В прошлое будильник не звонит))))");
    }
}
