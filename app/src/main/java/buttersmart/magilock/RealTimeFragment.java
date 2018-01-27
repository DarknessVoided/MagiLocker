package buttersmart.magilock;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.niqdev.mjpeg.DisplayMode;
import com.github.niqdev.mjpeg.Mjpeg;
import com.github.niqdev.mjpeg.MjpegView;

public class RealTimeFragment extends Fragment {

    View myFragment;
    private static final int TIMEOUT = 5;
    MjpegView mjpegView; // Currently this isnt being initialised

    public static RealTimeFragment newInstance(){
        RealTimeFragment realTimeFragment = new RealTimeFragment();
        return realTimeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_real_time, container,false);
        mjpegView = myFragment.findViewById(R.id.cameraView); // See now it has been initialised
        LoadIpCam();
        return  myFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle("Real-Time Monitering");
    }

    private void LoadIpCam(){
        Mjpeg.newInstance()
                .open("http://172.27.177.204:8081/video.mjpg", TIMEOUT)
                .subscribe(inputStream -> {
                    mjpegView.setSource(inputStream); // null object reference means that you were trying to call a method on something that didnt exist. Right forgot about that R thingy
                    mjpegView.setDisplayMode(calculateDisplayMode());
                    mjpegView.showFps(true);
                }, throwable -> {
                    //Handling exception here.
                    Log.e(getClass().getSimpleName(), "mjpeg error", throwable);
                    Toast.makeText(getActivity(), "An error occured. Probably internet issues.", Toast.LENGTH_SHORT).show();
                        });
    }

    private DisplayMode calculateDisplayMode() {
        int orientation = getResources().getConfiguration().orientation;
        return orientation == Configuration.ORIENTATION_PORTRAIT ?
                DisplayMode.FULLSCREEN : DisplayMode.BEST_FIT;
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadIpCam();
    }

    @Override
    public void onPause() {
        super.onPause();
        mjpegView.clearStream();
    }
}
