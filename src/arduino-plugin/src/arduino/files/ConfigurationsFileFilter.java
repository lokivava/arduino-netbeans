/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package arduino.files;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.xml.XMLUtil;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author lokivava<oxigenium16@yandex.ru>
 */
public class ConfigurationsFileFilter implements FileFilter
{
    private final WizardDescriptor wiz;
    private final List<String> libs;
    private static final String[] STANDARD_LIBS = {
        "/hardware/arduino/avr/cores/arduino",
        "/hardware/arduino/avr/variants/standard",
        "/hardware/arduino/avr/libraries/EEPROM/src",
        "/hardware/arduino/avr/libraries/HID/src",
        "/hardware/arduino/avr/libraries/SPI/src",
        "/hardware/arduino/avr/libraries/SoftwareSerial/src",
        "/hardware/arduino/avr/libraries/Wire/src"};

    public ConfigurationsFileFilter(WizardDescriptor wiz)
    {
        this.wiz = wiz;
        libs = new ArrayList<>();
        String basedir = wiz.getProperty("basedir").toString().trim().replaceAll("\\\\", "/");
        for(String lib: STANDARD_LIBS)
            this.libs.add(basedir + lib);
        String[] libs = wiz.getProperty("libraries").toString().trim().replaceAll("\r", "").replace("\n", "").split(";");
        for(String lib: libs)
            if(!lib.isEmpty())
                this.libs.add(basedir + "/libraries/" + lib);
    }
    

    @Override
    public void filter(FileObject fo, ZipInputStream str) throws IOException
    {
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            FileUtil.copy(str, baos);
            
            Document doc = XMLUtil.parse(new InputSource(new ByteArrayInputStream(baos.toByteArray())), false, false, null, null);
            NodeList incDirs = doc.getElementsByTagName("incDir");
            if(incDirs != null)
            {
                for (int i = 0; i < incDirs.getLength(); i++)
                {
                    Element incDir = (Element) incDirs.item(i);
                    if (incDir.getParentNode() != null && "ccTool".equals(incDir.getParentNode().getNodeName()))
                    {
                        libs.forEach(lib ->
                        {
                            Element e = doc.createElement("pElem");
                            e.appendChild(doc.createTextNode(lib));
                            incDir.appendChild(e);
                        });
                        break;
                    }
                }
            }
            try (OutputStream out = fo.getOutputStream())
            {
                XMLUtil.write(doc, out, "UTF-8");
            }
        } catch (IOException | DOMException | SAXException ex)
        {
            Exceptions.printStackTrace(ex);
            FileFilter.writeFile(str, fo);
        }
    }

}
