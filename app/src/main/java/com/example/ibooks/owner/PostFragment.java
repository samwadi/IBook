package com.example.ibooks.owner;

import androidx.fragment.app.Fragment;
import android.app.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ibooks.Book;
import com.example.ibooks.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class PostFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView imageView;
    private Uri imageUri;
    private EditText nameEditText, priceEditText, descriptionEditText, pagesEditText, facultyEditText;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    public PostFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("books");

        imageView = view.findViewById(R.id.imageView);
        nameEditText = view.findViewById(R.id.editTextName);
        priceEditText = view.findViewById(R.id.editTextPrice);
        descriptionEditText = view.findViewById(R.id.editTextDescription);
        pagesEditText = view.findViewById(R.id.editTextPages);
        facultyEditText=view.findViewById(R.id.editTextFaculty);
        Button selectImageButton = view.findViewById(R.id.btnSelectImage);
        Button sendButton = view.findViewById(R.id.btnSend);

        selectImageButton.setOnClickListener(v -> openGallery());

        sendButton.setOnClickListener(v -> sendBookData());
    }
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       // super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            System.out.println(imageUri+" imageUri");
            imageView.setImageURI(imageUri);
        }
    }

    private void sendBookData() {
        String name = nameEditText.getText().toString().trim();
        String price = priceEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String pages = pagesEditText.getText().toString().trim();
        String faculty = facultyEditText.getText().toString().trim();

        if (!name.isEmpty() && !price.isEmpty() && !description.isEmpty() && !pages.isEmpty() && imageUri != null) {
            // Upload image to Firebase Storage
            String ownerId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
            String imageName = ownerId + "_" + System.currentTimeMillis() + ".jpg";
            FirebaseStorage storage= FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();
            final StorageReference ref = storageReference.child(imageName);
//            final ProgressDialog progressDialog = new ProgressDialog(requireContext());
//            progressDialog.setTitle("Uploading.....");
//            progressDialog.show();
            UploadTask uploadTask = ref.putFile(imageUri);
            String ownerName = mAuth.getCurrentUser().getDisplayName();
            String ownerEmail = mAuth.getCurrentUser().getEmail();
            uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                }
                return ref.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Get the download URL for the image
                    String imageDownloadUrl = task.getResult().toString();



                    Book book = new Book(name, imageDownloadUrl, description, Double.parseDouble(price), faculty, Integer.parseInt(pages), ownerName, ownerEmail);

                    DatabaseReference newBookRef = databaseReference.push();
                    newBookRef.setValue(book);

                    Toast.makeText(requireContext(), "Book data sent successfully!", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    Toast.makeText(requireContext(), "Failed to get download URL: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    Book book = new Book(name, null, description, Double.parseDouble(price), faculty, Integer.parseInt(pages), ownerName, ownerEmail);

                    DatabaseReference newBookRef = databaseReference.push();
                    newBookRef.setValue(book);
                    Toast.makeText(requireContext(), "Book data sent successfully!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(requireContext(), "Please fill in all fields and select an image", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        nameEditText.setText("");
        priceEditText.setText("");
        descriptionEditText.setText("");
        pagesEditText.setText("");
        imageView.setImageResource(0);
        imageUri = null;
    }
}
