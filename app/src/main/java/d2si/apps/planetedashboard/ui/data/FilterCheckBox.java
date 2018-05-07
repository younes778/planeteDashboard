package d2si.apps.planetedashboard.ui.data;

import java.util.ArrayList;
import java.util.List;

public class FilterCheckBox {
    private String text;
    private Boolean check;

    public FilterCheckBox(String text,boolean check) {
        this.text = text;
        this.check = check;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public static ArrayList<FilterCheckBox> getCheckBoxListFromText(List<String> strings){
        ArrayList<FilterCheckBox> filterCheckBoxes = new ArrayList<>();
        for (String string:strings){
            filterCheckBoxes.add(new FilterCheckBox(string,false));
        }
        return filterCheckBoxes;
    }

    public static ArrayList<Integer> getItemSelected(ArrayList<FilterCheckBox> checkBoxes){
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i=0;i<checkBoxes.size();i++)
            if (checkBoxes.get(i).getCheck()) integers.add(i);
        return integers;
    }

    public static void selectAll(ArrayList<FilterCheckBox> checkBoxes){
        for (FilterCheckBox checkBox:checkBoxes)
            checkBox.setCheck(true);
    }
}
