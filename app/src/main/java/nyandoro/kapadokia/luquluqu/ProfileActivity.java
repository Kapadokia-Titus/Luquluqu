package nyandoro.kapadokia.luquluqu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jackandphantom.circularimageview.RoundedImage;

import java.util.HashMap;


public class ProfileActivity extends AppCompatActivity {

    //declarations

   private static final int GALLERY_INTENT=2;
   private ImageView imagevw;
   private static final int PICK_IMAGE=2;
   private ProgressDialog progressDialog;
   private StorageReference storageReference;
   private RoundedImage roundedImage;
   private Uri uri;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

      //inits
        roundedImage= findViewById(R.id.imageView);
        roundedImage.setRoundedRadius(100);
        storageReference = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(this);
        imagevw = findViewById(R.id.covarIv);


        //on clicking the image view
        roundedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "select image"), GALLERY_INTENT);
            }
        });
        //TODO 3:Create a method to display the image to image view using the link from your firebasedb

           // still under research




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        progressDialog.setMessage("uploading...");
        progressDialog.show();
        if(requestCode==GALLERY_INTENT && resultCode == RESULT_OK){

            uri = data.getData();

            Glide.with(this)
                    .load(uri)
                    .into(roundedImage);
            //send to storage
            final StorageReference filepath = storageReference.child("Photos").child(uri.getLastPathSegment());
            //send to database
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //TODO 1:GeT the link from firebase storage
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            progressDialog.dismiss();
                            //TODO 2:Put it in your firebase database
                            DatabaseReference sendImage = FirebaseDatabase.getInstance().getReference().child("users");
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("photo_url",String.valueOf(uri));
                            sendImage.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ProfileActivity.this, "upload complete", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });



                }
            });
        }
    }
}
