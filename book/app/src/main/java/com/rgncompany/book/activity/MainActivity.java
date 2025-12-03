package com.rgncompany.book.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;

import com.rgncompany.book.AppSettings;
import com.rgncompany.book.R;
import com.rgncompany.book.book.Book;
import com.rgncompany.book.theme.AppTheme;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends StandardActivity {

    public static int CHOOSE_FILE_REQUEST_CODE = 1;

    public Button downloadButton;
    public ImageButton downloadImage;
    public TextView downloadText;
    public TextView searchText;
    public ImageView searchImage;

    public LinearLayout bookList;

    public static boolean booksFilled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        init();

        downloadButton = findViewById(R.id.download_button);
        downloadImage = findViewById(R.id.download_image);
        downloadText = findViewById(R.id.download_text);

        searchText = findViewById(R.id.search);
        searchImage = findViewById(R.id.search_image);

        bookList = findViewById(R.id.book_list);

        bookmarksButton.setOnClickListener(view -> openBookmarksPage());
        bookListButton.setOnClickListener(view -> openBookListPage());
        sidebarButton.setOnClickListener(view -> openDrawer());

        searchImage.setOnClickListener(view -> {
            clearRowBooks();
            addRowBooks();
        });

        if (!booksFilled) {
            fillBooks();
            booksFilled = true;
        }

        addRowBooks();

        setTheme();

        downloadButton.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, CHOOSE_FILE_REQUEST_CODE);
        });
    }

    @Override
    public void setTheme(AppTheme theme) {
        super.setTheme(theme);
        Context context = getApplicationContext();
        downloadButton.setBackgroundTintList(getColorStateList(context, theme.getButtonColor()));
        downloadImage.setImageTintList(getColorStateList(context, theme.getIconColor()));
        downloadText.setTextColor(getColorStateList(context, theme.getTextColor()));

        int count = bookList.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = bookList.getChildAt(i);
            Button button = view.findViewById(R.id.background);
            button.setBackgroundTintList(getColorStateList(context, theme.getButtonColor()));
            button.setTextColor(getColorStateList(context, theme.getTextColor()));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHOOSE_FILE_REQUEST_CODE) {
            Uri uri = data.getData();
            File file = new File(uri.getPath());
            String fileName = file.getName();
            int index = fileName.indexOf(".");
            if (index != -1) fileName = fileName.substring(0, index);
            Book book = createBook(fileName);
            book.addNewPage(getTextFromFile(uri));
            clearBooks();
            fillBooks();
            clearRowBooks();
            addRowBooks();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void clearRowBooks() {
        bookList.removeAllViews();
    }

    public void addRowBooks() {
        List<Book> sorted = new ArrayList<>(AppSettings.books);
        Collections.sort(sorted, new Comparator<>() {
            public int compare(Book o1, Book o2) {
                return o2.name.compareTo(o1.name);
            }
        });
        for (Book book : sorted) {
            if (searchText.getText().toString().isEmpty() || book.name.contains(searchText.getText() )) {
                addRowBook(book);
            }
        }
    }

    public void addRowBook(Book book) {
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View row = inflater.inflate(R.layout.activity_list_book, null);
        Button button = row.findViewById(R.id.background);
        button.setText(book.name);
        button.setOnClickListener(view -> {
            for (Book book1 : AppSettings.books) {
                if (book1.name.equals(button.getText().toString())) {
                    AppSettings.currentBook = book1;
                    openBookPage();
                    break;
                }
            }
        });
        bookList.addView(row);
    }
}