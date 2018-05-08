package d2si.apps.planetedashboard.ui.data;

import java.util.ArrayList;
import java.util.List;

/**
 * class that represents checkbox with text and boolean of the state
 *
 * @author younessennadj
 */

public class FilterCheckBox {
    private String text;
    private Boolean check;

    /**
     * Constructor
     *
     * @param text  checkbox text
     * @param check checkbox state
     */
    public FilterCheckBox(String text, boolean check) {
        this.text = text;
        this.check = check;
    }

    // getters and setters
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

    /**
     * Method that get a list of checkboxes initialized with false from strings
     *
     * @param strings list of texts
     * @return list of filter checkboxes
     */
    public static ArrayList<FilterCheckBox> getCheckBoxListFromText(List<String> strings) {
        ArrayList<FilterCheckBox> filterCheckBoxes = new ArrayList<>();
        for (String string : strings) {
            filterCheckBoxes.add(new FilterCheckBox(string, false));
        }
        return filterCheckBoxes;
    }

    /**
     * Method that get all selected items
     *
     * @param checkBoxes list of checkboxes
     * @return list of indexes of the selected items
     */
    public static ArrayList<Integer> getItemSelected(ArrayList<FilterCheckBox> checkBoxes) {
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < checkBoxes.size(); i++)
            if (checkBoxes.get(i).getCheck()) integers.add(i);
        return integers;
    }

    /**
     * Method that select all checkboxes
     *
     * @param checkBoxes list of checkboxes
     */
    public static void selectAll(ArrayList<FilterCheckBox> checkBoxes) {
        for (FilterCheckBox checkBox : checkBoxes)
            checkBox.setCheck(true);
    }
}
