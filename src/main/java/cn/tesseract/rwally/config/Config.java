package cn.tesseract.rwally.config;

import cn.tesseract.rwally.util.FileHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

public class Config<T> {
    public final String path;
    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    public T instance;

    public Config(String path, T instance) {
        this.path = path;
        this.instance = instance;
    }

    public void read() {
        String json = readFile();
        if (!json.isEmpty())
            instance = (T) GSON.fromJson(json, instance.getClass());
    }

    public void save() {
        saveFile(GSON.toJson(instance));
    }

    public String readFile() {
        try {
            return FileHelper.read(path);
        } catch (IOException ignored) {
            resetFile();
        }
        return "";
    }

    public void saveFile(String config) {
        try {
            FileHelper.write(path, config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void resetFile() {
        saveFile("");
    }
}
