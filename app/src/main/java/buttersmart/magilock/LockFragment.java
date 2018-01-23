package buttersmart.magilock;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;


public class LockFragment extends Fragment implements View.OnClickListener {

    View myFragment;
    Button btnLock;
    TextView txtStatus;


    public static LockFragment newInstance() {
        LockFragment lockFragment = new LockFragment();
        return lockFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_lock, container, false);
        btnLock = (Button) myFragment.findViewById(R.id.btnLock);
        btnLock.setOnClickListener(this);

        txtStatus = (TextView) myFragment.findViewById(R.id.txtStatus);



        return myFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle("Lock");
    }

    @Override
    public void onClick(View view) {
            Log.i("TEST", " Making network request");
            OkHttpClient client = new OkHttpClient();
            // try running it now? wiat
            // GET request
            Request request = new Request.Builder()
                    .url("http://magilock.epizy.com/ChangeStatus.php?lock=0")
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Toast.makeText(getActivity(), response.body().toString(), Toast.LENGTH_SHORT).show();

                }
            });
    }
}