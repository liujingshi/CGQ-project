package com.ljscode.component;

import com.ljscode.util.OutputUtil;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

public class FileSelect extends JFileChooser {

    private JComponent parent;

    public FileSelect(JComponent parent) {
        super();
        this.parent = parent;
    }

    public FileSelect(String title, String btn, String ext, String def, JComponent parent) {
        this(parent);
        this.setDialogTitle(title);
        this.setApproveButtonText(btn);
        this.setFileSelectionMode(JFileChooser.FILES_ONLY);
        this.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) return true;
                return f.getName().endsWith(ext);
            }

            @Override
            public String getDescription() {
                return ext;
            }
        });
        this.setSelectedFile(new File(def));
    }

    public String read() {
        int result = this.showOpenDialog(parent);
        if (JFileChooser.APPROVE_OPTION == result) {
            String path = this.getSelectedFile().getPath();
            if (!path.subSequence(path.length() - 5, path.length()).equals(".test"))
                path += ".test";
            return path;
        } else {
            return "";
        }
    }

}
