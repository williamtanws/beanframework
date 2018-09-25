/*
 * Copyright (c) 2014 www.diligrp.com All rights reserved.
 * 本软件�?代�?版�?�归地利集团所有,未�?许�?��?得任�?�?制与传播.
 */
package com.beanframework.filemanager.utils;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * ZipUtils on spring-boot-filemanager
 *
 * @author <a href="mailto:akhuting@hotmail.com">Alex Yang</a>
 * @date 2016年08月25日 10:08
 */
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
            //判断路径是�?�存在,�?存在则创建文件路径
            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
            if (!file.exists()) {
                file.mkdirs();
            }
            //判断文件全路径是�?�为文件夹,如果是上�?�已�?上传,�?需�?解压
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
