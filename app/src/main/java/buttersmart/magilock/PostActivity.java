package buttersmart.magilock;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class PostActivity extends AppCompatActivity {

    private ImageButton SelectImage;
    private EditText mPostTitle, mPostDesc;
    private Button mSubmitBtn;
    private Uri resultUri = null;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;

    private static final int GALLERY_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");

        SelectImage = (ImageButton) findViewById(R.id.AddImage);
        mPostTitle = (EditText) findViewById(R.id.editTxtTitle);
        mPostDesc = (EditText) findViewById(R.id.editTxtDesc);
        mSubmitBtn = (Button) findViewById(R.id.BtnSubmit);
        mProgress = new ProgressDialog(this);

        SelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPosting();
            }
        });
    }

    private void startPosting() {
        mProgress.setMessage("Posting in progress.");
        mProgress.show();

        final String title = mPostTitle.getText().toString().trim(); //final means that the value can no long be overriden.
        final String desc = mPostDesc.getText().toString().trim();

        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty((desc)) && resultUri != null)
        {
            StorageReference filepath = mStorage.child("Blog_Image").child(resultUri.getLastPathSegment()); //This creates a filepath to the image we have selected.
            filepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri DownloadUri = taskSnapshot.getDownloadUrl();

                    DatabaseReference newPost = mDatabase.push(); //Pushes a Post onto the database.
                    newPost.child("Title").setValue(title); //Creates a child called Title with the value of textbox. See line 77
                    newPost.child("Desc").setValue(desc);
                    newPost.child("Image").setValue(DownloadUri.toString());
                    mProgress.dismiss();
                    Toast.makeText(getApplicationContext(), "Successfully Submitted", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
        else
        {
            mProgress.dismiss();
            if (resultUri == null)
            {
                Toast.makeText(getApplicationContext(), "Please select an image.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK)
        {
            Uri imageUri = data.getData(); //Gets image filepath.
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(16,9)
                    .setMaxCropResultSize(1280, 720)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data); //
            if (resultCode == RESULT_OK)
            {
                resultUri = result.getUri();
                SelectImage.setImageURI(resultUri); //This will set the image to the picture that was cropped.
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
            {
                Exception error = result.getError(); //Throws as an error.
            }
        }
    }
}
