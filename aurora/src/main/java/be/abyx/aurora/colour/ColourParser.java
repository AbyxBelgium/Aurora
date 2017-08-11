package be.abyx.aurora.colour;

import android.graphics.Color;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Parse colour data that's stored inside of a JSON-file with a specific format.
 *
 * @author Pieter Verschaffelt
 */
public class ColourParser {
    public List<Integer> parseFile(InputStream data) throws IOException, JSONException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int length;

        while ((length = data.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }

        String JSON = result.toString("UTF-8");

        JSONObject parsedJSON = new JSONObject(JSON);
        JSONArray parsedColours = parsedJSON.getJSONArray("data");

        List<Integer> output = new ArrayList<>();

        for (int i = 0; i < parsedColours.length(); i++) {
            JSONObject obj = parsedColours.getJSONObject(i);
            String hex = obj.getString("hexString");
            output.add(Color.parseColor(hex));
        }

        return output;
    }
}