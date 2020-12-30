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
import java.util.zip.ZipInputStream;
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
public final class ProjectFileFilter implements FileFilter
{
    private final String projectName;

    public ProjectFileFilter(String projectName)
    {
        this.projectName = projectName;
    }
    

    @Override
    public void filter(FileObject fo, ZipInputStream str) throws IOException
    {
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            FileUtil.copy(str, baos);
            Document doc = XMLUtil.parse(new InputSource(new ByteArrayInputStream(baos.toByteArray())), false, false, null, null);
            NodeList nl = doc.getDocumentElement().getElementsByTagName("name");
            if (nl != null)
            {
                for (int i = 0; i < nl.getLength(); i++)
                {
                    Element el = (Element) nl.item(i);
                    if (el.getParentNode() != null && "data".equals(el.getParentNode().getNodeName()))
                    {
                        NodeList nl2 = el.getChildNodes();
                        if (nl2.getLength() > 0)
                        {
                            nl2.item(0).setNodeValue(projectName);
                        }
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
