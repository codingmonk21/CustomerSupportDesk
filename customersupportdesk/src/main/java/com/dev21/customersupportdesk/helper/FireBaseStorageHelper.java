package com.dev21.customersupportdesk.helper;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.dev21.customersupportdesk.interfaces.ImageUploadListener;
import com.dev21.customersupportdesk.util.Logger;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

/**
 * Created by Prajwal on 17/07/17.
 */

public class FireBaseStorageHelper {
    private final String TAG = FireBaseStorageHelper.class.getSimpleName();
    private ImageUploadListener imageUploadListener;

    public void uploadImageToStorage(Uri imageUri, String folderName) {

        StorageReference rootStorageRef = FirebaseStorage.getInstance().getReference();

        UploadTask imgUploadTask = rootStorageRef.child(folderName + "/" + new File(imageUri.getLastPathSegment())).putFile(imageUri);

        imgUploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Logger.e(TAG, "Image upload failed");

                if (imageUploadListener != null) {
                    imageUploadListener.onUploaded(null);
                }
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                if (taskSnapshot.getMetadata() != null) {
                    Logger.d(TAG, "Image URL : " + taskSnapshot.getMetadata().getDownloadUrl());
                    if (imageUploadListener != null) {
                        imageUploadListener.onUploaded(taskSnapshot.getMetadata().getDownloadUrl());
                    }
                } else {
                    if (imageUploadListener != null) {
                        imageUploadListener.onUploaded(null);
                    }
                }
            }
        });
    }

    public void setImageUploadListener(ImageUploadListener imageUploadListener) {
        this.imageUploadListener = imageUploadListener;
    }
}
