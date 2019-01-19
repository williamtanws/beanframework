package com.beanframework.filemanager.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;


public class ZipUtils {

    public static void zipFiles(ZipOutputStream out, String path, File... srcFiles) {
        path = path.replaceAll("\\*", "/");
        if (!path.endsWith("/")) {
            path += "/";
        }
        byte[] buf = new byte[1024];
        try {
            for (File srcFile : srcFiles) {
                if (srcFile.isDirectory()) {
                    File[] files = srcFile.listFiles();
                    String srcPath = srcFile.getName();
                    srcPath = srcPath.replaceAll("\\*", "/");
                    if (!srcPath.endsWith("/")) {
                        srcPath += "/";
                    }
                    out.putNextEntry(new ZipEntry(path + srcPath));
                    zipFiles(out, path + srcPath, files);
                } else {
                    try (FileInputStream in = new FileInputStream(srcFile)) {
                        out.putNextEntry(new ZipEntry(path + srcFile.getName()));
                        int len;
                        while ((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }
                        out.closeEntry();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unZipFiles(File zipFile, String descDir, String descFolder) throws IOException {
        if (!descDir.endsWith("/")) {
            descDir += "/";
        }

        File pathFile = new File(descDir);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        ZipFile zip = new ZipFile(zipFile);
        
        boolean isGetOriParentFolder = false;
        String oriFolder = "";
        for (Enumeration<?> entries = zip.entries(); entries.hasMoreElements(); ) {
            ZipEntry entry = (ZipEntry) entries.nextElement();

            String zipEntryName = entry.getName();
            if(!isGetOriParentFolder) {
            	oriFolder = zipEntryName.substring(1,zipEntryName.lastIndexOf("/"));
            	isGetOriParentFolder = true;
            }
            zipEntryName = zipEntryName.substring(zipEntryName.indexOf("/")+1);
            String outPath = "";

            //create with new folder name
            if(!descFolder.equals(oriFolder)) {
                if (!descFolder.endsWith("/")) {
                	descFolder += "/";
                }
            	outPath = (descDir + descFolder + zipEntryName).replaceAll("\\*", "/");
            } else {
                outPath = (descDir + zipEntryName).replaceAll("\\*", "/");
            }
            
            
            InputStream in = zip.getInputStream(entry);

            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
            if (!file.exists()) {
                file.mkdirs();
            }

            if (new File(outPath).isDirectory()) {
                continue;
            }

            OutputStream out = new FileOutputStream(outPath);
            byte[] buf1 = new byte[1024];
            int len;
            while ((len = in.read(buf1)) > 0) {
                out.write(buf1, 0, len);
            }
            in.close();
            out.close();
        }
        zip.close();
    }
}
