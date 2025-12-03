package com.rgncompany.book.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.rgncompany.book.AppSettings;
import com.rgncompany.book.R;
import com.rgncompany.book.book.Book;
import com.rgncompany.book.dialog.SettingsDialogFragment;
import com.rgncompany.book.theme.AppTheme;
import com.rgncompany.book.theme.AppThemes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StandardActivity extends AppCompatActivity {

    public DrawerLayout main;
    public ConstraintLayout backgroundLayout;
    public ImageButton bookmarksButton;
    public ImageButton mainButton;
    public ImageButton bookListButton;
    public ImageButton sidebarButton;

    public Button background1;
    public Button background2;
    public Button background3;
    public Button selectedBackground;
    public ImageButton pageImage;
    public ImageView searchImage;
    public TextView pageName;
    public TextView search;

    public NavigationView navigationView;
    public Menu navigationMenu;
    public MenuItem navigationHome;
    public MenuItem navigationTranslate;
    public MenuItem navigationTheme;

    public void init() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        main = findViewById(R.id.main);
        backgroundLayout = findViewById(R.id.background_layout);

        if (isMain()) {
            bookmarksButton = findViewById(R.id.bookmarks_button);
            mainButton = findViewById(R.id.main_button);
            bookListButton = findViewById(R.id.book_list_button);
            sidebarButton = findViewById(R.id.sidebar_button);

            background1 = findViewById(R.id.background1);
            background2 = findViewById(R.id.background2);
            background3 = findViewById(R.id.background3);
            selectedBackground = findViewById(R.id.selected_background);
            pageImage = findViewById(R.id.page_image);
            searchImage = findViewById(R.id.search_image);
            pageName = findViewById(R.id.page_name);
            search = findViewById(R.id.search);
        }

        navigationView = findViewById(R.id.navigation_view);
        navigationMenu = navigationView.getMenu();
        navigationHome = navigationMenu.getItem(0);
        navigationTranslate = navigationMenu.getItem(1);
        navigationTheme = navigationMenu.getItem(2);

        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {
                openMainPage();
            }
            if (item.getItemId() == R.id.navigation_translate) {
                translateText();
            }
            if (item.getItemId() == R.id.navigation_theme) {
                switchTheme();
            }
            if (item.getItemId() == R.id.navigation_settings) {
                openSettingsDialog();
            }
            return false;
        });

        initSettings();
    }

    public boolean isMain()  {
        return true;
    }

    public void initSettings() {
        AppSettings.init(getSharedPreferences());
    }

    public void openSettings() {
        AppSettings.openSettings(getSharedPreferences());
    }

    public void saveSettings() {
        AppSettings.saveSettings(getSharedPreferences());
    }

    public SharedPreferences getSharedPreferences() {
        return AppSettings.getSharedPreferences(this);
    }

    public void openBookmarksPage() {
        //startActivity(new Intent(this, BookmarksActivity.class));
        startActivity(new Intent(this, BookActivity.class));
    }

    public void openMainPage() {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void openBookListPage() {
        startActivity(new Intent(this, BookListActivity.class));
    }

    public void openDrawer() {
        main.openDrawer(GravityCompat.START);
    }

    public void openBookPage() {
        startActivity(new Intent(this, BookActivity.class));
    }

    public void translateText() {

    }

    public void openSettingsDialog() {
        FragmentManager manager = getSupportFragmentManager();
        SettingsDialogFragment settingsDialogFragment = new SettingsDialogFragment(this);
        settingsDialogFragment.show(manager, "settings");
    }

    public void switchTheme() {
        List<AppTheme> list = AppThemes.getThemeList();
        int i = list.indexOf(AppSettings.currentTheme);
        i = (i + 1) % list.size();
        AppSettings.currentTheme = list.get(i);
        setTheme();
        saveSettings();
    }

    public void setTheme() {
        setTheme(AppSettings.currentTheme);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void setTheme(AppTheme theme) {
        Context context = getApplicationContext();
        setLocale(this, AppSettings.appLanguage);
        main.setBackgroundTintList(getColorStateList(context, theme.getBackgroundColor()));
        backgroundLayout.setBackgroundTintList(getColorStateList(context, theme.getBackgroundColor()));

        if (isMain()) {
            background1.setBackgroundTintList(getColorStateList(context, theme.getRectangleColor()));
            background2.setBackgroundTintList(getColorStateList(context, theme.getSearchColor()));
            background3.setBackgroundTintList(getColorStateList(context, theme.getRectangleColor()));
            selectedBackground.setBackgroundTintList(getColorStateList(context, theme.getSelectionColor()));

            pageName.setTextColor(getColorStateList(context, theme.getTextColor()));

            pageImage.setImageTintList(getColorStateList(context, theme.getIconColor()));
            bookmarksButton.setImageTintList(getColorStateList(context, theme.getIconColor()));
            mainButton.setImageTintList(getColorStateList(context, theme.getIconColor()));
            bookListButton.setImageTintList(getColorStateList(context, theme.getIconColor()));
            sidebarButton.setImageTintList(getColorStateList(context, theme.getIconColor()));
        }

        navigationView.setBackgroundTintList(getColorStateList(context, theme.getBackgroundColor()));
        navigationView.setItemTextColor(getColorStateList(context, theme.getTextColor()));

        Drawable iconDrawable = getDrawable(theme.getIcon());
        navigationTheme.setIcon(iconDrawable);
    }

    public ColorStateList getColorStateList(Context context, int color) {
        return ColorStateList.valueOf(ContextCompat.getColor(context, color));
    }

    public String getTextFromFile(String fileName) {
        FileInputStream fin = null;
        try {
            fin = openFileInput(fileName);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String (bytes);
            return text;
        }
        catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally {
            try {
                if (fin != null) fin.close();
            }
            catch(IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return "";
    }

    public void saveTextToFile(String fileName, String text) {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(fileName, MODE_PRIVATE);
            fos.write(text.getBytes());
        }
        catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally {
            try {
                if (fos != null) fos.close();
            }
            catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String getTextFromFile(Uri uri) {
        InputStream fin = null;
        try {
            fin = getContentResolver().openInputStream(uri);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            return text;
        }
        catch (IOException e) {
            Log.e("BOOKLOG", e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally {
            try {
                if (fin != null) fin.close();
            }
            catch(IOException e) {
                Log.e("BOOKLOG", e.getMessage());
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return "";
    }

    public void saveTextToFile(Uri uri, String text) {
        OutputStream fos = null;
        try {
            fos = getContentResolver().openOutputStream(uri);
            fos.write(text.getBytes());
        }
        catch (IOException e) {
            Log.e("BOOKLOG", e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally {
            try {
                if (fos != null) fos.close();
            }
            catch (IOException e) {
                Log.e("BOOKLOG", e.getMessage());
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public List<File> listFilesWithSubFolders(File directory) {
        List<File> files = new ArrayList<>();
        if (!directory.exists()) directory.mkdirs();
        File[] f = directory.listFiles();
        if (directory.canRead() && f != null) {
            for (File file: f) {
                if (file.isDirectory()) {
                    files.addAll(listFilesWithSubFolders(file));
                } else {
                    files.add(file);
                }
            }
        }
        return files;
    }

    public List<File> listFiles(File directory) {
        List<File> files = new ArrayList<>();
        if (!directory.exists()) directory.mkdirs();
        File[] f = directory.listFiles();
        if (directory.canRead() && f != null) {
            for (File file: f) {
                if (!file.isDirectory()) {
                    files.add(file);
                }
            }
        }
        return files;
    }

    public List<File> listDirectories(File directory) {
        List<File> files = new ArrayList<>();
        if (!directory.exists()) directory.mkdirs();
        File[] f = directory.listFiles();
        if (directory.canRead() && f != null) {
            for (File file: f) {
                if (file.isDirectory()) {
                    files.add(file);
                }
            }
        }
        return files;
    }

    public static List<String> getStringLines(String text) {
        String[] lines = text.split("\n");
        return Arrays.asList(lines);
    }

    public static String linesToString(List<String> lines) {
        StringBuilder string = new StringBuilder();
        int i = 0;
        for (String line : lines) {
            if (i != 0) string.append("\n");
            string.append(line);
            i++;
        }
        return string.toString();
    }

    public File getBookFile(String name) {
        File file = new File(getFilesDir(), "books");
        if (!file.exists()) file.mkdirs();
        file = new File(getFilesDir(), "books/" + name);
        return file.exists() ? file : null;
    }

    public Book createBook(String name) {
        File file = new File(getFilesDir(), "books");
        if (!file.exists()) file.mkdirs();
        file = new File(getFilesDir(), "books/" + name);
        while (file.exists()) {
            Pattern pattern = Pattern.compile("\\d+$");
            Matcher matcher = pattern.matcher(name);
            if (matcher.find()) {
                String numbers = matcher.group();
                name = name.substring(0, name.lastIndexOf(numbers));
                name = name + (Integer.parseInt(numbers) + 1);
            } else {
                name = name + "1";
            }
            file = new File(getFilesDir(), "books/" + name);
        }
        Book book = Book.create(this, file, name);
        book.saveToFiles();
        return book;
    }

    public void clearBooks() {
        AppSettings.books.clear();
    }

    public void fillBooks() {
        File books = new File(getFilesDir(), "books");
        List<File> files = listDirectories(books);
        for (File file : files) {
            Book book = Book.getFromFiles(this, file);
            AppSettings.books.add(book);
        }
    }

    public static void setLocale(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    public int getResId(String resName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            Log.e("BOOKLOG", e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return -1;
        }
    }

    public void deleteDirectory(File directory) {
        deleteFiles(directory);
        if (directory.isDirectory()) {
            String[] children = directory.list();
            for (String child : children) {
                File file = new File(directory, child);
                deleteDirectory(file);
            }
            directory.delete();
        }
    }

    public void deleteFiles(File directory) {
        if (directory.isDirectory()) {
            String[] children = directory.list();
            for (String child : children) {
                File file = new File(directory, child);
                if (!file.isDirectory()) {
                    file.delete();
                } else {
                    deleteFiles(file);
                }
            }
        }
    }
}
