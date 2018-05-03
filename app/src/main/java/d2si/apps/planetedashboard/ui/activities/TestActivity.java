package d2si.apps.planetedashboard.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import d2si.apps.planetedashboard.AppUtils;
import d2si.apps.planetedashboard.R;
import d2si.apps.planetedashboard.database.data.Article;
import d2si.apps.planetedashboard.database.data.Document;
import d2si.apps.planetedashboard.database.data.Ligne;
import d2si.apps.planetedashboard.database.data.Representant;
import d2si.apps.planetedashboard.database.data.Tiers;
import d2si.apps.planetedashboard.webservice.httpgetter.ArticlesGetter;
import d2si.apps.planetedashboard.webservice.httpgetter.LignesGetter;
import d2si.apps.planetedashboard.webservice.httpgetter.RepresentantsGetter;
import d2si.apps.planetedashboard.webservice.httpgetter.SalesGetter;
import d2si.apps.planetedashboard.webservice.httpgetter.TiersGetter;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.tv_data)
    TextView tv_data;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // Bind resources
        ButterKnife.bind(this);

        //set the activity and actionBar font
        typeface(this);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH,0);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        Date dateFrom = new Date(calendar.getTimeInMillis());
        calendar.set(Calendar.MONTH,2);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        Date dateTo = new Date(calendar.getTimeInMillis());

        /*new SalesGetter(getBaseContext(),dateFrom,dateTo) {
            @Override
            public void onPost(ArrayList<Document> sales) {
                String builder="";
                for (Document document:sales){
                    builder+= document.getDoc_numero()+","+document.getDoc_type()+"\n";
                }
                tv_data.setText(builder);
            }
        }.execute();*/

        new RepresentantsGetter(getBaseContext(),dateFrom,dateTo) {
            @Override
            public void onPost(ArrayList<Representant> representants) {
                String builder="";
                for (Representant representant:representants){
                    builder+= representant.getRep_code()+","+representant.getRep_nom()+"\n";
                }
                tv_data.setText(builder);
            }
        }.execute();

    }
}
