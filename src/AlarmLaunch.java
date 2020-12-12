

public class AlarmLaunch implements Runnable {
    private final FileManagerAlarm fileManagerAlarm;
    private final Thread thr;
    private final int mW;
    private final int index;
    private final Framer framer;

    AlarmLaunch(int milWaits, int ind, Framer framer, FileManagerAlarm fileManagerAlarm) {
        this.fileManagerAlarm = fileManagerAlarm;
        this.framer = framer;
        thr = new Thread(this);
        mW = milWaits;
        index = ind;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(mW);
            framer.AudioStart();
            framer.txtSetText(fileManagerAlarm.getArrStrComment(index));
        } catch (InterruptedException ea) {
            framer.txtSetText("Ошибка с будильником");
        }
    }

    public void threadStart() {
        thr.start();
    }
}

