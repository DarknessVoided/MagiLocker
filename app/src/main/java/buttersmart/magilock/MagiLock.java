package buttersmart.magilock;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Xaiver on 24/12/2017.
 */

public class MagiLock extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
