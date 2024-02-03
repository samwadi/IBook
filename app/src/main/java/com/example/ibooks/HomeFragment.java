package com.example.ibooks;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private static final String KEY_BOOK_LIST = "bookList";

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private List<Book> bookList;
    private View view;
    //private List<Book> bookList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(bookList, new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("book", book);

                BookDetailsFragment bookDetailsFragment = new BookDetailsFragment();
                bookDetailsFragment.setArguments(bundle);

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, bookDetailsFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        recyclerView.setAdapter(bookAdapter);

        // Load data from Firebase
        if (savedInstanceState != null) {
            // Restore the bookList from the saved state
            bookList =new ArrayList<>( savedInstanceState.getParcelableArrayList(KEY_BOOK_LIST));
            if (bookList == null) {
                bookList = new ArrayList<>();
            }
            // Update your RecyclerView or UI with bookList
            updateRecyclerView();

        } else {
            // If there is no saved state, load data from Firebase or other sources
            // and update your RecyclerView
            loadDataFromFirebase();
        }


        return view;
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        // Save the current bookList when the fragment is about to be destroyed
        super.onSaveInstanceState(outState);
        // Save your data to the bundle
        outState.putParcelableArrayList(KEY_BOOK_LIST, new ArrayList<>(bookList));
    }
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            // Retrieve your data from the bundle
            bookList = savedInstanceState.<Book>getParcelableArrayList(KEY_BOOK_LIST);
            // Ensure bookList is not null
            if (bookList == null) {
                bookList = new ArrayList<>();
            }
            // Update your RecyclerView or UI with bookList
            updateRecyclerView();
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private void updateRecyclerView() {
        if (bookAdapter == null) {
            // Initialize your RecyclerView and BookAdapter if not done already
            recyclerView = view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            bookAdapter = new BookAdapter(bookList, new BookAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Book book) {
                    // Handle item click
                }
            });
            recyclerView.setAdapter(bookAdapter);
        } else {
            // Update the existing data set in the adapter and notify it
            ArrayList<Book> arrayList = new ArrayList<>(bookList);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("bookList", (ArrayList<? extends Parcelable>) arrayList);
            bookAdapter.updateData(bookList);
            bookAdapter.notifyDataSetChanged();
        }
    }

    private void loadDataFromFirebase() {
        DatabaseReference booksRef = FirebaseDatabase.getInstance().getReference("books");

        booksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Book book = postSnapshot.getValue(Book.class);
                    if (book != null) {
                        bookList.add(book);
                    }
                }

                bookAdapter.notifyDataSetChanged();
               // return null;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
                Log.w("HomeFragment", "Failed to read value.", databaseError.toException());
            }
        });
    }

    private void addSampleBooksToFirebase() {
        DatabaseReference booksRef = FirebaseDatabase.getInstance().getReference("books");

        Book book1 = new Book("Book 1", "picture1.jpg", "Description 1", 19.99, "Faculty A", 200,"nameless","test@test.com");
        Book book2 = new Book("Book 2", "picture2.jpg", "Description 2", 24.99, "Faculty B", 300,"nameless","test@test.com");
        Book book3 = new Book("Book 3", "picture3.jpg", "Description 3", 14.99, "Faculty A", 150,"nameless","test@test.com");
        Book book4 = new Book("Book 4", "picture4.jpg", "Description 4", 29.99, "Faculty C", 250,"nameless","test@test.com");
        Book book5 = new Book("Book 5", "picture5.jpg", "Description 5", 17.99, "Faculty B", 180,"nameless","test@test.com");

        // Add books to Firebase
        booksRef.push().setValue(book1);
        booksRef.push().setValue(book2);
        booksRef.push().setValue(book3);
        booksRef.push().setValue(book4);
        booksRef.push().setValue(book5);
    }
}
