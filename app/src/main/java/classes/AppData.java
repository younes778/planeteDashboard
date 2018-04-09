package classes;

import android.content.Context;
import android.graphics.Typeface;

// Class regrouping the app static data
public class AppData {
    public static Typeface fontApp;
    public static Typeface fontAppBold;

    public static void init(Context context){
        fontApp = Typeface.createFromAsset(context.getAssets(), "fonts/AppFont.ttf");
        fontAppBold = Typeface.createFromAsset(context.getAssets(), "fonts/AppFont_Bold.ttf");
    }


}
