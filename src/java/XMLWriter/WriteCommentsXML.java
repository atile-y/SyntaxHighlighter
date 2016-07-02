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

import DataWeb.Comment;
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
public class WriteCommentsXML {
    private static final Namespace ns = Namespace.getNamespace("nsComments", "http://www.w3.org/comments");
    private static final SAXBuilder builder = new SAXBuilder(XMLReaders.XSDVALIDATING);;

    public static boolean addComment(Comment com, String xmlSource) {
        try {
            Document doc = builder.build(xmlSource);
            Element rootNode = doc.getRootElement();
            Element comentario = new Element("comentario", ns);
            comentario.setAttribute("id", com.getID());

            Element idUsuario = new Element("idUsuario", ns);
            idUsuario.setText(com.getIDUsuario());
            Element texto = new Element("texto", ns);
            texto.setText("\n            "+com.getTexto().replace("\n", "\n            ")+"\n        ");
            Element calificacion = new Element("calificacion", ns);
            calificacion.setText(com.getCalificacion().toString());
            Element fechaMod = new Element("fechaMod", ns);
            fechaMod.setText(com.getFechaMod());
            
            comentario.addContent(idUsuario);
            comentario.addContent(texto);
            comentario.addContent(calificacion);
            comentario.addContent(fechaMod);
            
            rootNode.addContent(comentario);
            
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

    public static boolean rateComment(String id, Integer calif, String xmlSource) {
        try {
            Document doc = builder.build(xmlSource);
            Element rootNode = doc.getRootElement();
            List<Element> lista = rootNode.getChildren("comentario", ns);
            boolean find = false;
            
            for(Element e : lista) {
                if( e.getAttributeValue("id").equals(id) ) {
                    find = true;
                    e.getChild("calificacion", ns).setText(calif.toString());
                    break;
                }
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

    public static boolean removeComments(List<String> idComments, String xmlSource) {
        if( idComments.isEmpty() )
            return true;

        try {
            Document doc = builder.build(xmlSource);
            Element rootNode = doc.getRootElement();
            List<Element> lista = rootNode.getChildren("comentario", ns);
            List<Element> rem = new ArrayList<Element>();
            boolean find = false, error = false;

            for(Element e : lista) {
                if( idComments.contains(e.getAttributeValue("id")) ) {
                    find = true;
                    rem.add(e);
                }
            }
            for(Element e : rem) {
                error = (error || !rootNode.removeContent(e));
            }
            if( !find || error )
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

    public static boolean removeComments(String idUser, List<String> idComments, String xmlSource) {
        try {
            Document doc = builder.build(xmlSource);
            Element rootNode = doc.getRootElement();
            List<Element> lista = rootNode.getChildren("comentario", ns);
            boolean error = false;
            
            for(Element e : lista)
                if( e.getChildTextTrim("idUsuario", ns).equals(idUser) && idComments.contains(e.getAttributeValue("id")) )
                    error = (error || !rootNode.removeContent(e));
            if( error )
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
