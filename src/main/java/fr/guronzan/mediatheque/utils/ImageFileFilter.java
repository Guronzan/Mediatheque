package fr.guronzan.mediatheque.utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.swing.filechooser.FileFilter;

public class ImageFileFilter extends FileFilter {
    private final List<String> extensions = Arrays.asList("jpg", "bmp", "png",
            "jpeg", "ico");

    @Override
    public String getDescription() {
        return "Sélectionnez une image...";
    }

    @Override
    public boolean accept(final File f) {
        if (f.isDirectory()) {
            return true;
        }
        final String absolutePath = f.getAbsolutePath();
        final String[] split = absolutePath.split("\\.");
        if (split == null) {
            return false;
        }
        return this.extensions.contains(split[split.length - 1]);
    }

}
