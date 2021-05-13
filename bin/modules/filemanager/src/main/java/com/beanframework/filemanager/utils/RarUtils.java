package com.beanframework.filemanager.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.impl.FileVolumeManager;
import com.github.junrar.rarfile.FileHeader;

public class RarUtils {

  public static void unRarFile(File srcRar, String dstDirectoryPath)
      throws IOException, RarException {

    Archive archive = new Archive(new FileVolumeManager(srcRar));
    FileHeader fh = archive.nextFileHeader();
    while (fh != null) {
      String path = fh.getFileNameString().replaceAll("\\\\", "/");
      File dirFile = new File(dstDirectoryPath + File.separator + path);
      if (fh.isDirectory()) {
        dirFile.mkdirs();
      } else {
        try {
          if (!dirFile.exists()) {
            if (!dirFile.getParentFile().exists()) {
              dirFile.getParentFile().mkdirs();
            }
            dirFile.createNewFile();
          }
          FileOutputStream os = new FileOutputStream(dirFile);
          archive.extractFile(fh, os);
          os.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
      fh = archive.nextFileHeader();
    }
    archive.close();

  }
}
