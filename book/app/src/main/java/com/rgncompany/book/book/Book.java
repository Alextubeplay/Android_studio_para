package com.rgncompany.book.book;

import android.net.Uri;

import com.rgncompany.book.activity.StandardActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Book {

    public StandardActivity activity;

    public File file;
    public String name;
    public int pageCount;
    public int currentPage;
    public List<Page> pages = new ArrayList<>();

    public static Book create(StandardActivity activity, File file, String name) {
        Book book = new Book();
        book.activity = activity;
        book.file = file;
        book.name = name;
        book.pageCount = 0;
        book.currentPage = 0;
        return book;
    }

    public static Book getFromFiles(StandardActivity activity, File file) {
        Book book = Book.create(activity, file, "");
        String bookTextPath = file.getPath() + "/book.txt";
        File bookTextFile = new File(bookTextPath);
        List<String> lines = StandardActivity.getStringLines(activity.getTextFromFile(Uri.fromFile(bookTextFile)));
        if (!lines.isEmpty()) book.name = lines.get(0);
        if (lines.size() > 1) book.pageCount = Integer.parseInt(lines.get(1));
        if (lines.size() > 2) book.currentPage = Integer.parseInt(lines.get(2));
        File pages = new File(file.getPath() + "/pages");
        if (!pages.exists()) pages.mkdirs();
        for (int i = 0; i < book.pageCount; i++) {
            File pageFile = new File(file.getPath() + "/pages/" + (i + 1));
            if (!pageFile.exists()) pages.mkdirs();
            Page page = Page.getFromFiles(activity, pageFile);
            book.getPages().add(page);
        }
        return book;
    }

    public void addPage(File file, String text) {
        Page page = Page.create(activity, file);
        page.saveText(text);
        getPages().add(page);
    }

    public void addPage(int number, String text) {
        File pageFile = new File(file.getPath() + "/pages/" + number);
        addPage(pageFile, text);
    }

    public void addNewPage(String text) {
        pageCount = pageCount + 1;
        if (currentPage == 0) currentPage++;
        saveToFiles();
        addPage(pageCount, text);
    }

    public void saveToFiles() {
        if (!file.exists()) file.mkdirs();
        List<String> lines = new ArrayList<>();
        lines.add(name);
        lines.add(String.valueOf(pageCount));
        lines.add(String.valueOf(currentPage));
        String bookText = StandardActivity.linesToString(lines);
        String bookTextPath = file.getPath() + "/book.txt";
        File bookTextFile = new File(bookTextPath);
        if (!bookTextFile.exists()) bookTextFile.getParentFile().mkdirs();
        activity.saveTextToFile(Uri.fromFile(bookTextFile), bookText);
        File pages = new File(file.getPath() + "/pages");
        if (!pages.exists()) pages.mkdirs();
    }

    public List<Page> getPages() {
        return pages;
    }

    public Page getCurrentPage() {
        return currentPage > 0 ? getPages().get(currentPage - 1) : null;
    }

    public void delete() {
        activity.deleteDirectory(file);
    }
}
