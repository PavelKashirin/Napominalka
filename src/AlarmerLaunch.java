

public class AlarmerLaunch implements Runnable {
    Thread thr;
    int mW,index;

    AlarmerLaunch(int milWaits,int ind){
        thr = new Thread(this);
        mW = milWaits;
        index = ind;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(mW);
            Framer.AudioStart();
            Framer.txtSetText(FileManagerAlarm.arrStrComment.get(index));
        } catch (InterruptedException ea) {
                Framer.txtSetText("Ошибка с будильником");
        }
    }
}

