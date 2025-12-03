package com.rgncompany.book;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class AppTranslator {
    public static String translate(String text, String sourceLanguageCode, String targetLanguageCode) {
        try {
            URL url = new URL("https://translate.api.cloud.yandex.net/translate/v2/translate");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Authorization", "Api-Key " + AppSettings.API_KEY);

            String lines[] = text.split("\\r?\\n");
            String dataText = "";
            int i = 0;
            for (String line : lines) {
                line = line.replace('"', '\'');
                if (i > 0) {
                    dataText = dataText + ", ";
                }
                dataText = dataText + "\"" + line + "\"";
                i++;
            }

            String source = "";
            if (!sourceLanguageCode.isEmpty()) {
                source = ", \"sourceLanguageCode\": \"" + sourceLanguageCode + "\"";
            }

            String data = "{\"texts\": [" + dataText + "]" + source + ", \"targetLanguageCode\": \"" + targetLanguageCode + "\"}";

            byte[] out = data.getBytes(StandardCharsets.UTF_8);

            http.connect();

            OutputStream stream = http.getOutputStream();
            stream.write(out);
            stream.close();

            int statusCode = http.getResponseCode();
            String statusMsg = http.getResponseMessage();

            if (statusCode == 200) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(http.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.indexOf("  \"text\": \"") == 1) {
                        line = line.replace("  \"text\": \"", "");
                        line = line.replace("\",", "");
                        line = line.replace("\"", "");
                        stringBuilder.append(line + '\n');
                    }
                    if (line.indexOf(" {},") == 1 || line.indexOf(" {}") == 1) {
                        stringBuilder.append('\n');
                    }
                }
                http.disconnect();
                return String.valueOf(stringBuilder);
            }

            http.disconnect();
            return "Error! " + statusCode + " " + statusMsg;
        } catch (IOException e) {
            return "Error!";
        }
    }
}
