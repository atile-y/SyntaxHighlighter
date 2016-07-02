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

import DataWeb.Course;
import java.io.FileOutputStream;
import java.io.IOException;
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
public class WriteCoursesXML {
    private static final Namespace ns = Namespace.getNamespace("nsCourses", "http://www.w3.org/courses");
    private static final SAXBuilder builder = new SAXBuilder(XMLReaders.XSDVALIDATING);;
    
    public static void addCourse(Course curso, String xmlSource) {
        try {
            Document doc = builder.build(xmlSource);
            Element rootNode = doc.getRootElement();
            Element c = new Element("curso", ns);
            c.setAttribute("id", curso.getID());
            
            Element idProfesor = new Element("idProfesor", ns);
            idProfesor.setText(curso.getIDProfesor());
            Element nombre = new Element("nombre", ns);
            nombre.setText(curso.getNombre());
            
            c.addContent(idProfesor);
            c.addContent(nombre);
            
            rootNode.addContent(c);

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
    }
    
    public static boolean deleteCourse(String idCurso, String xmlSource) {
        try {
            Document doc = builder.build(xmlSource);
            Element rootNode = doc.getRootElement();
            boolean find = false;

            for(Element e : rootNode.getChildren("curso", ns))
                if( e.getAttributeValue("id").equals(idCurso) ) {
                    find = rootNode.removeContent(e);
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
    
    public static boolean addCode(String idCourse, String idCode, String xmlSource) {
        try {
            Document doc = builder.build(xmlSource);
            Element rootNode = doc.getRootElement();
            List<Element> lista = rootNode.getChildren("curso", ns);
            boolean find = false;
            
            for(Element e : lista)
                if( e.getAttributeValue("id").equals(idCourse) ) {
                    find = true;
                    Element idCo = new Element("idCodigo", ns);
                    idCo.setText(idCode);
                    e.addContent(e.indexOf(e.getChild("nombre", ns)), idCo);
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

    public static boolean removeCode(String idCourse, String idCode, String xmlSource) {
        try {
            Document doc = builder.build(xmlSource);
            Element rootNode = doc.getRootElement();
            List<Element> lista = rootNode.getChildren("curso", ns);
            boolean find = false;
            
            for(Element e : lista)
                if( e.getAttributeValue("id").equals(idCourse) ) {
                    for(Element c : e.getChildren("idCodigo", ns))
                        if( c.getTextTrim().equals(idCode) ) {
                            find = e.removeContent(c);
                            break;
                        }
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

    public static boolean addUser(String idCourse, String idUser, String xmlSource) {
        try {
            Document doc = builder.build(xmlSource);
            Element rootNode = doc.getRootElement();
            List<Element> lista = rootNode.getChildren("curso", ns);
            boolean find = false;
            
            for(Element e : lista)
                if( e.getAttributeValue("id").equals(idCourse) ) {
                    find = true;
                    Element idUs = new Element("idAlumno", ns);
                    idUs.setText(idUser);
                    e.addContent(2, idUs);
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

    public static boolean removeUser(String idCourse, String idUser, String xmlSource) {
        try {
            Document doc = builder.build(xmlSource);
            Element rootNode = doc.getRootElement();
            List<Element> lista = rootNode.getChildren("curso", ns);
            boolean find = false;
            
            for(Element e : lista)
                if( e.getAttributeValue("id").equals(idCourse) ) {
                    for(Element c : e.getChildren("idAlumno", ns))
                        if( c.getTextTrim().equals(idUser) ) {
                            find = e.removeContent(c);
                            break;
                        }
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
