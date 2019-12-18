package com.epam.wilma.stubconfig.json.parser.helper;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.xml.transform.TransformerException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 * Class for transforming a {@link org.json.JSONObject} to byte array.
 *
 * @author Tamas_Kohegyi
 */
@Component
public class JsonBasedObjectTransformer {

    private static final int INDENT_FACTOR = 4;

    /**
     * Transforms a {@link org.json.JSONObject} to byte array.
     *
     * @param object will be transformed
     * @return with the json as byte array
     */
    public byte[] transform(final JSONObject object) {
        String objectString = object.toString(INDENT_FACTOR);
        byte[] b = objectString.getBytes(StandardCharsets.UTF_8);
        return b;
    }

    /**
     * Transforms a {@link JSONObject} into a new File.
     *
     * @param jsonObject   will be transformed
     * @param path         will be the path of the new file
     * @param actualStatus is the is the actual status of this stub descriptor
     * @throws TransformerException is thrown when unrecoverable error occurs during the course of the transformation
     */
    public void transformToFile(final JSONObject jsonObject, final String path, final boolean actualStatus) throws TransformerException {
        try {
            if (jsonObject.getJSONObject("muServiceConfiguration").has("active")) {
                jsonObject.getJSONObject("muServiceConfiguration").put("active", String.valueOf(actualStatus));
            }
            String text = jsonObject.toString(INDENT_FACTOR);
            File file = new File(path);
            try (Writer output = new BufferedWriter(new FileWriter(file))) {
                output.write(text);
            }
        } catch (IOException e) {
            throw new TransformerException(e);
        }
    }
}
