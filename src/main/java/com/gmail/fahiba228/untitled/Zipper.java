package com.gmail.fahiba228.untitled;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipper {

    private final ZipOutputStream zout;
    Zipper(String zip_file) throws IOException
    {
        // Cоздание объекта ZipOutputStream из FileOutputStream
        FileOutputStream fout = new FileOutputStream(zip_file);
        zout = new ZipOutputStream(fout);
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

            FileInputStream fis = new FileInputStream(file);

            zout.putNextEntry(new ZipEntry(file.getPath()));

            byte[] buffer = new byte[4048];
            int length;
            while ((length = fis.read(buffer)) > 0)
                zout.write(buffer, 0, length);
            // Закрываем ZipOutputStream и InputStream
            zout.closeEntry();
            fis.close();
        }
    }
    public void close() throws IOException {
        zout.close();
    }
}

