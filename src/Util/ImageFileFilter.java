package Util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ImageFileFilter extends FileFilter {  
    public String getDescription() {  
        return "*.jpg;*.jpeg;*.png";  
    }  
	@Override
	public boolean accept(File file) {
		String name = file.getName();  
        return file.isDirectory() || name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".jpeg")|| name.toLowerCase().endsWith(".png");
	}  
} 
