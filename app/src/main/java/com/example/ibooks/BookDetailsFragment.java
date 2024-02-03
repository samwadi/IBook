package com.example.ibooks;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class BookDetailsFragment extends Fragment {
    private static final String ARG_BOOK = "book";

    private Book book;

    public static BookDetailsFragment newInstance(Book book) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_BOOK, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            book = getArguments().getParcelable(ARG_BOOK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imageView = view.findViewById(R.id.book_image);
        TextView nameTextView = view.findViewById(R.id.book_name);
        TextView priceTextView = view.findViewById(R.id.book_price);
        TextView descriptionTextView = view.findViewById(R.id.book_description);
        TextView ownerNameTextView = view.findViewById(R.id.owner_name);
        TextView ownerEmailTextView = view.findViewById(R.id.owner_email);
        Button orderButton = view.findViewById(R.id.order_button);

        if (book != null) {
            Glide.with(requireContext()).load(book.getPictureUrl()).into(imageView);
            nameTextView.setText(book.getName());
            priceTextView.setText(book.getPrice()+"");
            descriptionTextView.setText(book.getDescription());
            ownerNameTextView.setText("Owner: " + book.getOwnerName());
            ownerEmailTextView.setText("Email: " + book.getOwnerEmail());
        }

        orderButton.setOnClickListener(v -> {
            // Get the signed-in user ID
            String requesterId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            // Create an Order object with the necessary data
            Order order = new Order(
                        (String) null,  // This will be automatically generated by Firebase
                        getBookId(book),
                        getOwnerId(book),
                        requesterId,
                     book.getPrice()+"",
                        book.getName()
            );

            // Push the order data to the "Orders" node in Firebase
            DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("Orders");
            ordersRef.push().setValue(order)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Order placed successfully, you can show a success message or perform other actions
                            Toast.makeText(getActivity(), "Order placed successfully!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Failed to place the order, you can show an error message or perform other actions
                            Toast.makeText(getActivity(), "Failed to place order: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

    }
    private String getBookId(Book book){
// Assuming bookName is stored in the book object
        String bookName = book.getName();

// Search the "Books" node to find the bookId based on bookName
        DatabaseReference booksRef = FirebaseDatabase.getInstance().getReference("Books");
        Query bookQuery = booksRef.orderByChild("name").equalTo(bookName);
        final String[] bookid = new String[1];

        bookQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot bookSnapshot : dataSnapshot.getChildren()) {
                    String bookId = bookSnapshot.getKey();

                    // Now you have the bookId, you can use it to create the Order object
                    bookid[0]=bookId;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });

        return bookid[0];
    }
    private String getOwnerId(Book book){
        // Assuming ownerEmail is stored in the book object
        String ownerEmail = book.getOwnerEmail();

// Search the "Users" node to find the ownerId based on ownerEmail
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");
        Query ownerQuery = usersRef.orderByChild("email").equalTo(ownerEmail);
        final String[] ownerid = new String[1];
        ownerQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String ownerId = userSnapshot.getKey();

                    // Now you have the ownerId, you can use it to create the Order object
                    ownerid[0] =ownerId;
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });

        return ownerid[0];
    }
}
