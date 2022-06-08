package com.gmail.fahiba228.untitled;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipper {

    private final String zipName;
    private final ZipOutputStream zout;
    Zipper(String zip_fileName) throws IOException
    {
        zipName = zip_fileName;
        File zipFile = new File(zip_fileName);
        // Cоздание объекта ZipOutputStream из FileOutputStream
        zout = new ZipOutputStream(new FileOutputStream(zipFile));
    }
    public void addDirectory(File fileSource)
            throws IOException
    {
        File[] files = fileSource.listFiles();
        //System.out.println("Добавление директории <" + fileSource.getName() + ">");
        for (File file : files) {
            // Если file является директорией, то рекурсивно вызываем
            // метод addDirectory
            if (file.isDirectory()) {
                addDirectory(file);
                continue;
            }
            //System.out.println("Добавление файла <" + file.getName() + ">");
            zout.putNextEntry(new ZipEntry(file.getPath()));
            if (!file.getName().equals("session.lock")) Files.copy(file.toPath(), zout);
            zout.closeEntry();

        }
    }
    public void close() throws IOException {
        zout.close();
    }
}

