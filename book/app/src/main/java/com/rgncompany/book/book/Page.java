package com.rgncompany.book.book;

import android.net.Uri;

import com.rgncompany.book.activity.StandardActivity;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page {

    public StandardActivity activity;

    public File file;
    public File textFile;
    public Map<String, File> langFiles = new HashMap<>();

    public static Page create(StandardActivity activity, File file) {
        Page page = new Page();
        page.activity = activity;
        page.file = file;
        page.textFile = new File(file.getPath() + "/text.txt");
        return page;
    }

    public static Page getFromFiles(StandardActivity activity, File file) {
        Page page = Page.create(activity, file);
        List<File> files = activity.listFiles(file);
        for (File transFile : files) {
            if (!transFile.getName().equals(page.textFile.getName())) {
                String fileName = transFile.getName();
                int index = fileName.indexOf(".");
                if (index != -1) fileName = fileName.substring(0, index);
                page.langFiles.put(fileName, transFile);
            }
        }
        return page;
    }

    public void saveText(String text) {
        if (!file.exists()) file.mkdirs();
        if (!textFile.exists()) textFile.getParentFile().mkdirs();
        activity.saveTextToFile(Uri.fromFile(textFile), text);
    }

    public void addTranslate(String lang, String text) {
        if (!file.exists()) file.mkdirs();
        File langFile = new File(file.getPath() + "/" + lang + ".txt");
        if (!langFile.exists()) langFile.getParentFile().mkdirs();
        activity.saveTextToFile(Uri.fromFile(langFile), text);
        langFiles.put(lang, langFile);
    }

    public String getText() {
        if (textFile.exists()) {
            return activity.getTextFromFile(Uri.fromFile(textFile));
        }
        return "";
    }

    public String getText(File file) {
        if (file.exists()) {
            return activity.getTextFromFile(Uri.fromFile(file));
        }
        return "";
    }
}
