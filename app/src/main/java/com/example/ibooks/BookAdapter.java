package com.example.ibooks;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private List<Book> bookList;
    private OnItemClickListener listener;



    public void updateData(List<Book> newBookList) {
        bookList.clear();
        bookList.addAll(newBookList);
    }

    public interface OnItemClickListener {
        void onItemClick(Book book);
    }

    public BookAdapter(List<Book> bookList, OnItemClickListener listener) {
        this.bookList = bookList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.bind(book, listener);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView nameTextView;
        private TextView descriptionTextView;
        private TextView priceTextView;
        private Button viewButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.bookImageView);
            nameTextView = itemView.findViewById(R.id.bookNameTextView);
            descriptionTextView = itemView.findViewById(R.id.bookDescriptionTextView);
            priceTextView = itemView.findViewById(R.id.bookPriceTextView);
            viewButton = itemView.findViewById(R.id.viewButton);
        }

        public void bind(final Book book, final OnItemClickListener listener) {
            String imageURL="https://th.bing.com/th/id/R.0d01329607aef3687f7311ff569f226e?rik=Kj8d8jzvDzQhOA&riu=http%3a%2f%2fww1.prweb.com%2fprfiles%2f2016%2f11%2f23%2f13876887%2fcover.jpg&ehk=YVaKYXWIjlPU8a50D8roKOw%2fCTxlXwX5DwvaQ41AQSc%3d&risl=&pid=ImgRaw&r=0";
           // Glide.with(itemView.getContext()).load(book.getPictureUrl()).into(imageView);
            Picasso.get().load(imageURL).into(imageView);
            nameTextView.setText(book.getName());
            descriptionTextView.setText(book.getDescription());
            priceTextView.setText(String.valueOf(book.getPrice()));

            viewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(book);
                }
            });
        }
    }
    private void addSampleBooksToFirebase() {
        DatabaseReference booksRef = FirebaseDatabase.getInstance().getReference("books");

        // Sample books
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

