import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class FileManagerAlarm {
    Calendar calNow;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static ArrayList <String> arrCalString = new ArrayList<>();//���� � �������� ����������� �� ��� calendars
    static ArrayList <String> arrStrComment = new ArrayList<>();//���� ������������
    static ArrayList <Calendar> calendars = new ArrayList<>();//���� ���������� � ������� ������� sdf

    public void FileWrite (String minut, String hour, String str){
            calNow = Calendar.getInstance();
            calNow.set(Calendar.MINUTE,Integer.parseInt(minut));
            calNow.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hour));
            calNow.set(Calendar.SECOND,0);
            calendars.add(calNow);
            arrStrComment.add(str);
            SortArrays sort = new SortArrays();
            sort.sortArrCal(calendars,arrStrComment);
            calendars = sort.getArrcalend();
            arrStrComment = sort.getArrComment();
            FileWrite(calendars,arrStrComment);
    }
    public void FileWrite(ArrayList<Calendar> calwrite,ArrayList<String> arrComWrite){
        String str;
        arrCalString.clear();
        for (Calendar calendar : calwrite) {
            str = sdf.format(calendar.getTime());
            arrCalString.add(str);
        }
        try{
            FileWriter file = new FileWriter("AlarmList.txt");
            BufferedWriter buf = new BufferedWriter(file);
            for(String s: arrCalString) {
                buf.write(s);
                buf.newLine();
            }
            buf.close();

            FileWriter fCom = new FileWriter("ComList.txt");
            BufferedWriter bCom = new BufferedWriter(fCom);
            for(String s: arrComWrite) {
                bCom.write(s);
                bCom.newLine();
            }
            bCom.close();
        }catch (IOException ioe){
            Framer.txtSetText("������ ������ ������� � ������������");
        }
    }

    public void FileRead() {
        Calendar calen,datenow;
        String str;
        ArrayList<Integer> milWait = new ArrayList<>();
        int milWaitint;

        try {
            FileReader file = new FileReader("AlarmList.txt");
            BufferedReader readStrCal = new BufferedReader(file);
            while ((str = readStrCal.readLine())!=null){
                    arrCalString.add(str);
                    calen = Calendar.getInstance();
                    calen.setTime(sdf.parse(str));
                    calendars.add(calen);
            }
            readStrCal.close();

            FileReader fCom = new FileReader("ComList.txt");
            BufferedReader bufCom = new BufferedReader(fCom);
            while ((str = bufCom.readLine())!=null){
                arrStrComment.add(str);//���������� �� ����� ��� ����������� � arrlist arrStrComment
            }
            bufCom.close();

            SortArrays sort = new SortArrays();
            sort.sortArrCal(calendars,arrStrComment);//���������� ��������� ��� ���������� (������ � �������������)
            calendars = sort.getArrcalend();
            arrStrComment = sort.getArrComment();
            FileWrite(calendars,arrStrComment);

            for (Calendar calendar : calendars) {
                datenow = Calendar.getInstance();
                milWaitint=(int)(calendar.getTimeInMillis() - datenow.getTimeInMillis());
                if (milWaitint>=0) {
                    milWait.add(milWaitint);
                }
            }
            for (int i=0;i<milWait.size();i++) {
                AlarmerLaunch al = new AlarmerLaunch(milWait.get(i),i);
                al.thr.start();
            }
        } catch (IOException | ParseException ioe) {
            Framer.txtAppendText("������ ������ ������� �� ����� ��� ������� ���");
        }
    }
}

