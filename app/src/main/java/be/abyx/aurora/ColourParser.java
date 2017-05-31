package be.abyx.aurora;

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
        byte[] buffer = new byte[1024];
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
            JSONObject rgbObj = obj.getJSONObject("rgb");

            int red = rgbObj.getInt("r");
            int green = rgbObj.getInt("g");
            int blue = rgbObj.getInt("b");

            output.add(Color.argb(255, red, green, blue));
        }

        return output;
    }
}
