package buttersmart.magilock;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import buttersmart.magilock.Model.Blog;



public class BlogFragment extends Fragment {

    View myFragment;

    RecyclerView mBlogList;
    LinearLayoutManager layoutManager;
    FirebaseRecyclerAdapter<Blog, BlogViewHolder> adapter;
    private DatabaseReference mDatabase;
    //LinearLayoutManager layoutManager = new LinearLayoutManager(this);

    public static BlogFragment newInstance() {
        BlogFragment blogFragment = new BlogFragment();
        return blogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
        mDatabase.keepSynced(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle("Blog");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_blog, container, false);

        mBlogList = (RecyclerView) myFragment.findViewById(R.id.Blog_List);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(layoutManager);
        layoutManager = new LinearLayoutManager(container.getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mBlogList.setLayoutManager(layoutManager);

        loadBlogPost();

        return myFragment;
    }

    private void loadBlogPost() {
        adapter = new FirebaseRecyclerAdapter<Blog, BlogViewHolder>(
                Blog.class,
                R.layout.blog_row,
                BlogViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, Blog model, int position) {
                viewHolder.SetTitle(model.getTitle());
                viewHolder.SetDesc(model.getDesc());
                viewHolder.SetImage(getActivity(), model.getImage());
            }
        };
        mBlogList.setAdapter(adapter);
    }


    public static class BlogViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public BlogViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void SetTitle(String title) {
            TextView post_title = (TextView) mView.findViewById(R.id.post_title);
            post_title.setText(title);
        }

        public void SetDesc(String desc) {
            TextView post_desc = (TextView) mView.findViewById(R.id.post_desc);
            post_desc.setText(desc);
        }

        public void SetImage(Context ctx, String image) {
            ImageView post_image = (ImageView) mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image); //Downloads the images So that it can pass the reference.
        }
    }
}