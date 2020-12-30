/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduino.files;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipInputStream;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

/**
 *
 * @author lokivava<oxigenium16@gmail.com>
 */
public interface FileFilter
{
    public void filter(FileObject fo, ZipInputStream str)  throws IOException;
    static void writeFile(ZipInputStream str, FileObject fo) throws IOException
    {
        try (OutputStream out = fo.getOutputStream())
        {
            FileUtil.copy(str, out);
        }
    }
}
