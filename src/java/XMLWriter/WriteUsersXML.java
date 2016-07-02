/*
 * The MIT License
 *
 * Copyright 2014 Alejandro Alberto Yescas Benitez.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package XMLWriter;

import DataWeb.User;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Alejandro Alberto Yescas Benitez
 */
public class WriteUsersXML {
    private static final Namespace ns = Namespace.getNamespace("nsUsers", "http://www.w3.org/users");
    private static final SAXBuilder builder = new SAXBuilder(XMLReaders.XSDVALIDATING);;
    
    public static boolean addUser(User us, String xmlSource) {
        try {
            Document doc = builder.build(xmlSource);
            Element rootNode = doc.getRootElement();
            Element user = new Element("user", ns);
            user.setAttribute("id", us.getID());
            
            for(Element e : rootNode.getChildren("user", ns))
                if( e.getAttributeValue("id").equals(us.getID()) )
                    return false;

            Element usuario = new Element("usuario", ns);
            usuario.setText(us.getUsuario());
            Element password = new Element("password", ns);
            password.setText(us.getPassword());
            Element nombres = new Element("nombres", ns);
            nombres.setText(us.getNombres());
            Element apPat = new Element("apPat", ns);
            apPat.setText(us.getApPaterno());
            Element apMat = new Element("apMat", ns);
            apMat.setText(us.getApMaterno());
            Element category = new Element("category", ns);
            category.setText(us.getCategory());
            List<Element> idCursos = new ArrayList<Element>();
            for(String id : us.getIDCursos()) {
                Element idCurso = new Element("idCurso", ns);
                idCurso.setText(id);
                idCursos.add(idCurso);
            }
            
            user.addContent(usuario);
            user.addContent(password);
            user.addContent(nombres);
            user.addContent(apPat);
            user.addContent(apMat);
            user.addContent(category);
            user.addContent(idCursos);
            
            rootNode.addContent(user);
            
            Format form = Format.getPrettyFormat().clone();
            form.setTextMode(Format.TextMode.TRIM_FULL_WHITE);
            XMLOutputter xmlOut = new XMLOutputter(form);
            FileOutputStream file = new FileOutputStream(xmlSource);
            xmlOut.output(doc, file);
            file.close();
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch( JDOMException jdomex ) {
            System.out.println(jdomex.getMessage());
        }
        
        return true;
    }
    
    public static boolean changeName(User usuario, String xmlSource) {
        try {
            Document doc = builder.build(xmlSource);
            Element rootNode = doc.getRootElement();
            boolean find = false;
            
            for(Element e : rootNode.getChildren("user", ns))
                if( e.getAttributeValue("id").equals(usuario.getID()) ) {
                    e.getChild("nombres", ns).setText(usuario.getNombres());
                    e.getChild("apPat", ns).setText(usuario.getApPaterno());
                    e.getChild("apMat", ns).setText(usuario.getApMaterno());
                    find = true;
                    break;
                }
            
            if( !find )
                return false;

            Format form = Format.getPrettyFormat().clone();
            form.setTextMode(Format.TextMode.TRIM_FULL_WHITE);
            XMLOutputter xmlOut = new XMLOutputter(form);
            FileOutputStream file = new FileOutputStream(xmlSource);
            xmlOut.output(doc, file);
            file.close();
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch( JDOMException jdomex ) {
            System.out.println(jdomex.getMessage());
        }
        
        return true;
    }

    public static boolean addCourse(String idUser, String idCourse, String xmlSource) {
        try {
            Document doc = builder.build(xmlSource);
            Element rootNode = doc.getRootElement();
            boolean find = false;
            
            for(Element e : rootNode.getChildren("user", ns))
                if( e.getAttributeValue("id").equals(idUser) ) {
                    find = true;
                    Element idCu = new Element("idCurso", ns);
                    idCu.setText(idCourse);
                    e.setContent(e.indexOf(e.getChild("idCurso", ns)), idCu);
                    break;
                }
            if( !find )
                return false;

            Format form = Format.getPrettyFormat().clone();
            form.setTextMode(Format.TextMode.TRIM_FULL_WHITE);
            XMLOutputter xmlOut = new XMLOutputter(form);
            FileOutputStream file = new FileOutputStream(xmlSource);
            xmlOut.output(doc, file);
            file.close();
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch( JDOMException jdomex ) {
            System.out.println(jdomex.getMessage());
        }
        
        return true;
    }

    public static boolean removeCourse(String idUser, String idCourse, String xmlSource) {
        try {
            Document doc = builder.build(xmlSource);
            Element rootNode = doc.getRootElement();
            boolean find = false;
            
            for(Element e : rootNode.getChildren("user", ns))
                if( e.getAttributeValue("id").equals(idUser) )
                    for(Element c : e.getChildren("idCurso", ns))
                        if( c.getTextTrim().equals(idCourse) ) {
                            find = e.removeContent(c);
                            break;
                        }
            
            if( !find )
                return false;

            Format form = Format.getPrettyFormat().clone();
            form.setTextMode(Format.TextMode.TRIM_FULL_WHITE);
            XMLOutputter xmlOut = new XMLOutputter(form);
            FileOutputStream file = new FileOutputStream(xmlSource);
            xmlOut.output(doc, file);
            file.close();
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch( JDOMException jdomex ) {
            System.out.println(jdomex.getMessage());
        }
        
        return true;
    }
}
