import java.util.ArrayList;
import java.util.Calendar;

public class SortArrays {
    private final ArrayList<Calendar> arrCal = new ArrayList<>();
    private final ArrayList<String> arrComment = new ArrayList<>();

    ArrayList<Calendar> getArrcalend() {
        return arrCal;
    }

    ArrayList<String> getArrComment() {
        return arrComment;
    }

    void sortArrCal(ArrayList<Calendar> arr, ArrayList<String> arrC) {
        Calendar cal;
        String str;
        for (int j = 0; j < arr.size(); j++) {
            for (int i = 0; i < (arr.size() - 1); i++) {
                if (arr.get(i).after(arr.get(i + 1))) {
                    str = arrC.get(i);
                    cal = arr.get(i);
                    arrC.set(i, arrC.get(i + 1));
                    arr.set(i, arr.get(i + 1));
                    arrC.set(i + 1, str);
                    arr.set(i + 1, cal);
                }
            }
        }
        cal = Calendar.getInstance();
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).after(cal)) {
                arrCal.add(arr.get(i));
                arrComment.add(arrC.get(i));
            }
        }
    }
}
