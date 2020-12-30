/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package arduino.files;

import arduino.Boards;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.zip.ZipInputStream;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;

/**
 *
 * @author lokivava<oxigenium16@yandex.ru>
 */
public final class MakefileFilter implements FileFilter
{
    private final WizardDescriptor wiz;

    public MakefileFilter(WizardDescriptor wiz)
    {
        this.wiz = wiz;
    }
    @Override
    public void filter(FileObject fo, ZipInputStream str) throws IOException
    {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            FileUtil.copy(str, baos);
                           
            try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File(fo.getPath()),true))) {                
                pw.print("COM_PORT = ");
                pw.println(wiz.getProperty("comport"));
                                
                pw.print("ARDUINO_BASE_DIR = ");
                String basedir = wiz.getProperty("basedir").toString().trim().replaceAll("\\\\", "/");
                pw.println(basedir);  
                
                pw.print("INCLUDE_LIBS = ");
                String libraries = wiz.getProperty("libraries").toString().trim().replaceAll("\r", "").replace("\n", "");
                if (libraries.isEmpty()) {
                    pw.println("Firmata;");
                } else {
                    pw.println(libraries);
                }
                
                Boards.getByName((String) wiz.getProperty("board"))
                        .strings.forEach(pw::println);
                pw.println();
                pw.println(baos.toString());
            }            
            
        } catch (IOException ex)
        {
            Exceptions.printStackTrace(ex);
            FileFilter.writeFile(str, fo);
        }
    }

}
