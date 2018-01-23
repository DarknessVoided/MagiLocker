package buttersmart.magilock;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ShareFragment extends Fragment implements View.OnClickListener {

    View myFragment;
    Button btnShare;

    public static ShareFragment newInstance(){
        ShareFragment shareFragment = new ShareFragment();
        return shareFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle("Share the app!");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_share, container,false);

        btnShare = (Button) myFragment.findViewById(R.id.btnShare);
        btnShare.setOnClickListener(this);
        return  myFragment;
    }

    @Override
    public void onClick(View view) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Hey there! I'm using MagiLock to secure my house. Visit the site at magilock.epizy.com";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "MagiLock");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
}