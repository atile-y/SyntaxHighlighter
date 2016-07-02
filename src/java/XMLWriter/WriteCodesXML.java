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

import DataWeb.Code;
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
public class WriteCodesXML {
    private static final Namespace ns = Namespace.getNamespace("nsCodes", "http://www.w3.org/codes");
    private static final SAXBuilder builder = new SAXBuilder(XMLReaders.XSDVALIDATING);;
    
    public static void writeCode(Code code, String xmlSource) {
        try {
            Document doc = builder.build(xmlSource);
            Element rootNode = doc.getRootElement();
            Element codigo = new Element("codigo", ns);
            codigo.setAttribute("id", code.getID());
            
            for(Element e : rootNode.getChildren("codigo", ns))
                if( e.getAttributeValue("id").equals(code.getID()) ) {
                    rootNode.removeContent(e);
                    break;
                }
            
            Element idProf = new Element("idProfesor", ns);
            idProf.setText(code.getIDProfesor());
            Element nombre = new Element("nombre", ns);
            nombre.setText(code.getNombre());
            Element lenguaje = new Element("lenguaje", ns);
            lenguaje.setText(code.getLenguaje());
            Element resaltar = new Element("resaltar", ns);
            resaltar.setText(code.getResaltar());
            List<Element> lineas = new ArrayList<Element>();
            for(String l : code.getLineas()) {
                Element linea = new Element("linea", ns);
                linea.setText(l);
                lineas.add(linea);
            }
            List<Element> comentarios = new ArrayList<Element>();
            if( code.getIDComentarios().size() > 0 ) {
                for(String c : code.getIDComentarios()) {
                    Element comentario = new Element("idComentario", ns);
                    comentario.setText(c);
                    comentarios.add(comentario);
                }
            }
            
            codigo.addContent(idProf);
            codigo.addContent(nombre);
            codigo.addContent(lenguaje);
            codigo.addContent(resaltar);
            codigo.addContent(lineas);
            if( comentarios.size() > 0 )
                codigo.addContent(comentarios);
            
            rootNode.addContent(codigo);

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

    public static boolean deleteCode(Code code, String xmlSource) {
        try {
            Document doc = builder.build(xmlSource);
            Element rootNode = doc.getRootElement();
            boolean find = false;
            
            for(Element e : rootNode.getChildren("codigo", ns))
                if( e.getAttributeValue("id").equals(code.getID()) ) {
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

    public static boolean addComment(String idCode, String idComment, String xmlSource) {
        try {
            Document doc = builder.build(xmlSource);
            Element rootNode = doc.getRootElement();
            boolean find = false;
            
            for(Element e : rootNode.getChildren("codigo", ns))
                if( e.getAttributeValue("id").equals(idCode) ) {
                    find = true;
                    Element idComentario = new Element("idComentario", ns);
                    idComentario.setText(idComment);
                    e.addContent(idComentario);
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
