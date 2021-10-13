package com.yuhtin.imagemath.util;

import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import javax.script.ScriptEngineManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ResolveMath {

    private static final String OPTIIC = System.getenv("OPTIIC_API_KEY");

    @Nullable
    public static String resolveImageMath(@NotNull String imageUrl) {
        try {
            URL url = new URL("https://api.optiic.dev/process");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setRequestProperty("Content-Type", "application/json");

            String data = "{\"apiKey\": \"" + OPTIIC + "\", \"mode\": \"ocr\", \"url\": \"" + imageUrl + "\"}";

            byte[] out = data.getBytes(StandardCharsets.UTF_8);

            OutputStream stream = http.getOutputStream();
            stream.write(out);

            InputStream instream = http.getInputStream();
            String result = convertStreamToString(instream);

            http.disconnect();

            val jsonObject = new JSONObject(result);
            return jsonObject.getString("text");
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static double eval(@NotNull String str) {
        val manager = new ScriptEngineManager();
        val engine = manager.getEngineByName("JavaScript");
        try {
            return Double.parseDouble((String) engine.eval(str));
        } catch (Exception exception) {
            return 0;
        }
    }

}
