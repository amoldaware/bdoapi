package com.cgtmse.bdo;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.CopyOption;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.stereotype.Service;

@Service
public class Utility
{
    public String readFile(final String filePath) throws IOException {
        final String content = new String(Files.readAllBytes(Paths.get(filePath, new String[0])));
        return content;
    }
    
    public String convertBlobToString(final Blob requestBlob) throws Exception {
        String responseString = null;
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final byte[] buf = new byte[1024];
        try {
            final InputStream in = requestBlob.getBinaryStream();
            Integer n = 0;
            while ((n = in.read(buf)) >= 0) {
                baos.write(buf, 0, n);
            }
            in.close();
            final byte[] bytes = baos.toByteArray();
            responseString = new String(bytes);
        }
        catch (Exception e) {
            throw e;
        }
        return responseString;
    }
    
    public boolean moveFile(final String oldFile, final String newFile) throws IOException {
        boolean status = false;
        final Path temp = Files.move(Paths.get(oldFile, new String[0]), Paths.get(newFile, new String[0]), new CopyOption[0]);
        if (temp != null) {
            status = true;
        }
        return status;
    }
    
    public void createDir(final String batchedPath) {
        final File file = new File(batchedPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
    
    public File renameFile(final String file, final String newFile) {
        final File oldfile = new File(file);
        final File newfile = new File(newFile);
        if (oldfile.renameTo(newfile)) {
            return newfile;
        }
        return null;
    }
}