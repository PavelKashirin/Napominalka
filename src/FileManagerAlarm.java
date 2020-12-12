import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class FileManagerAlarm {
    private Framer framer;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final ArrayList<String> arrCalString = new ArrayList<>();//лист с строками полученными из дат calendars
    private ArrayList<String> arrStrComment = new ArrayList<>();//лист комментариев
    private ArrayList<Calendar> calendars = new ArrayList<>();//даты будильника в формате времени sdf

    public void FileWrite(String minut, String hour, String str) {
        Calendar calNow = Calendar.getInstance();
        calNow.set(Calendar.MINUTE, Integer.parseInt(minut));
        calNow.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        calNow.set(Calendar.SECOND, 0);
        calendars.add(calNow);
        arrStrComment.add(str);
        SortArrays sort = new SortArrays();
        sort.sortArrCal(calendars, arrStrComment);
        calendars = sort.getArrcalend();
        arrStrComment = sort.getArrComment();
        FileWrite(calendars, arrStrComment);
    }

    public void setFramer(Framer framer) {
        this.framer = framer;
    }

    public void FileWrite(ArrayList<Calendar> calwrite, ArrayList<String> arrComWrite) {
        String str;
        arrCalString.clear();
        for (Calendar calendar : calwrite) {
            str = simpleDateFormat.format(calendar.getTime());
            arrCalString.add(str);
        }
        try (FileWriter file = new FileWriter("AlarmList.txt");
             BufferedWriter buf = new BufferedWriter(file);
             FileOutputStream fCom = new FileOutputStream("ComList.txt");
             DataOutputStream bCom = new DataOutputStream(fCom)) {

            for (String s : arrCalString) {
                buf.write(s);
                buf.newLine();
            }
            for (String s : arrComWrite) {
                bCom.writeUTF(s);
            }
        } catch (IOException ioe) {
            framer.txtSetText("Ошибка записи звонков и комментариев");
        }
    }

    public String getArrStrComment(int index) {
        return arrStrComment.get(index);
    }

    public void FileRead() {
        Calendar calen, datenow;
        String str;
        ArrayList<Integer> milWait = new ArrayList<>();
        int milWaitint;

        try (FileReader file = new FileReader("AlarmList.txt");
             BufferedReader readStrCal = new BufferedReader(file);
             FileInputStream fCom = new FileInputStream("ComList.txt");
             DataInputStream bufCom = new DataInputStream(fCom)) {

            while ((str = readStrCal.readLine()) != null) {
                arrCalString.add(str);
                calen = Calendar.getInstance();
                calen.setTime(simpleDateFormat.parse(str));
                calendars.add(calen);
                str = bufCom.readUTF();
                arrStrComment.add(str);//записывает из файла все комментарии в arrlist arrStrComment
            }
        } catch (IOException | ParseException ioe) {
            framer.txtAppendText("Ошибка чтения записей из файла или записей нет");
        }

        SortArrays sort = new SortArrays();
        sort.sortArrCal(calendars, arrStrComment);//сортировка считанных дат будильника (вместе с комментариями)
        calendars = sort.getArrcalend();
        arrStrComment = sort.getArrComment();
        FileWrite(calendars, arrStrComment);

        for (Calendar calendar : calendars) {
            datenow = Calendar.getInstance();
            milWaitint = (int) (calendar.getTimeInMillis() - datenow.getTimeInMillis());
            if (milWaitint >= 0) {
                milWait.add(milWaitint);
            }
        }
        for (int i = 0; i < milWait.size(); i++) {
            AlarmLaunch al = new AlarmLaunch(milWait.get(i), i, framer, this);
            al.threadStart();
        }
    }
}

