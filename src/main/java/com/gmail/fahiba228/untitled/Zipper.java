package com.gmail.fahiba228.untitled;

import java.io.*;
import java.nio.file.Files;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipper {

    private final Logger logger;
    private final ZipOutputStream zout;
    Zipper(String zip_fileName, Logger logger) throws IOException
    {
        File zipFile = new File(zip_fileName);
        // Cоздание объекта ZipOutputStream из FileOutputStream
        zout = new ZipOutputStream(new FileOutputStream(zipFile));
        this.logger = logger;
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
                logger.info( Main.localeRes.getString("adding_dir") +" <" + file.getName() + ">");
                addDirectory(file);
                continue;
            }
            //System.out.println("Добавление файла <" + file.getName() + ">");
            zout.putNextEntry(new ZipEntry(file.getPath()));
            if (!file.getName().equals("session.lock")) {
                logger.info(Main.localeRes.getString("adding_file") + " <" + file.getName() + ">");
                Files.copy(file.toPath(), zout);
            }
            zout.closeEntry();

        }
    }
    public void close() throws IOException {
        zout.close();
    }
}

