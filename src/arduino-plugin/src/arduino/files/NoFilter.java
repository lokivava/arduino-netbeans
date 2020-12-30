/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package arduino.files;

import java.io.IOException;
import java.util.zip.ZipInputStream;
import org.openide.filesystems.FileObject;

/**
 *
 * @author lokivava<oxigenium16@yandex.ru>
 */
public final class NoFilter implements FileFilter
{

    @Override
    public void filter(FileObject fo, ZipInputStream str) throws IOException
    {
        FileFilter.writeFile(str, fo);
    }

}
