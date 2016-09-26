package info.theh2o.rahul.medassist;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gigamole.library.ArcProgressStackView;

import java.util.ArrayList;

public class MyStatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_status);
        final ArrayList<ArcProgressStackView.Model> models = new ArrayList<>();
        models.add(new ArcProgressStackView.Model("Circle", 25, Color.argb(100, 125, 145, 0), Color.argb(0,201,222,85)));
        models.add(new ArcProgressStackView.Model("Progress", 50, Color.argb(100, 125, 145, 0), Color.argb(0,201,222,85)));
        models.add(new ArcProgressStackView.Model("Stack", 75, Color.argb(100, 125, 145, 0), Color.argb(0,201,222,85)));
        models.add(new ArcProgressStackView.Model("View", 100, Color.argb(100, 125, 145, 0), Color.argb(0,201,222,85)));

        final ArcProgressStackView arcProgressStackView = (ArcProgressStackView) findViewById(R.id.apsv);
        arcProgressStackView.setModels(models);
    }

}
