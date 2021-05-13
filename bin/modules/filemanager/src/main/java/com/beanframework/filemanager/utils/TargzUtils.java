package com.beanframework.filemanager.utils;

import static com.beanframework.filemanager.utils.FileUtils.fileProber;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

/**
 * TargzUtils on spring-boot-filemanager
 */
public class TargzUtils {

  public static void unTarFile(File srcFile, String destPath) throws Exception {

    try (TarArchiveInputStream tais = new TarArchiveInputStream(new FileInputStream(srcFile))) {
      dearchive(new File(destPath), tais);
    }
  }

  private static void dearchive(File destFile, TarArchiveInputStream tais) throws Exception {

    TarArchiveEntry entry;
    while ((entry = tais.getNextTarEntry()) != null) {

      String dir = destFile.getPath() + File.separator + entry.getName();
      File dirFile = new File(dir);

      fileProber(dirFile);

      if (entry.isDirectory()) {
        dirFile.mkdirs();
      } else {
        dearchiveFile(dirFile, tais);
      }

    }
  }

  private static void dearchiveFile(File destFile, TarArchiveInputStream tais) throws Exception {

    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));

    int count;
    byte data[] = new byte[1024];
    while ((count = tais.read(data, 0, 1024)) != -1) {
      bos.write(data, 0, count);
    }

    bos.close();
  }

  public static void unTargzFile(File gzFile, String descDir) throws Exception {

    GZIPInputStream inputStream = new GZIPInputStream((new FileInputStream(gzFile)));
    TarArchiveInputStream tarArchiveInputStream = new TarArchiveInputStream(inputStream);
    dearchive(new File(descDir), tarArchiveInputStream);

  }
}
