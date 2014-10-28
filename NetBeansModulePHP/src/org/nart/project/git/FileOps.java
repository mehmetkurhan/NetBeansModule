/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.nart.project.git;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.swing.JOptionPane;

/**
 *
 * @author Mehmet
 */
public class FileOps {
    
    public static File getTempFile()
    {
        return new File(System.getProperty("java.io.tmpdir") + "/Project");
    }
    
    public static void deleteTemporaryFile(File dest) {
       
        
       // JOptionPane.showMessageDialog(null, dest.getPath());
       if (dest.exists()) {
        if(dest.isDirectory()) {
            String[] children = dest.list();
            dest.setWritable(true);
            for(int i = 0; i < children.length ; i++) {
                deleteTemporaryFile(new File(dest,children[i]));
            }
            dest.delete();
        } else {
            dest.delete();
        }
       }
   }
     public static void copyFilesToActualFolder(File source,File dest) throws IOException {
             if (source.isDirectory()) {
        if (!dest.exists()) {
            dest.mkdir();
        }

        String[] children = source.list();
        for (int i=0; i<children.length; i++) {
            copyFilesToActualFolder(new File(source, children[i]),
                    new File(dest, children[i]));
        }
    } else {

        InputStream in = new FileInputStream(source);
        OutputStream out = new FileOutputStream(dest);

        // Copy the bits from instream to outstream
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }  
            
            
   }
    
    
    
}
