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

package XMLReader;

/**
 *
 * @author Alejandro Alberto Yescas Benitez
 */

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;

public class ReadXMLFile {
    public static List<String> ReadLanguages(String xmlSource) {
        Namespace ns = Namespace.getNamespace("xs", "http://www.w3.org/2001/XMLSchema");
        SAXBuilder builder = new SAXBuilder(XMLReaders.NONVALIDATING);
        List<String> lenguajes = new ArrayList<String>();
        
        try {
            Element rootNode = builder.build(xmlSource).getRootElement();
            Element type = rootNode.getChild("simpleType", ns);
            Element rest = type.getChild("restriction", ns);
            List<Element> lista = rest.getChildren("enumeration", ns);
            
            for(Element e : lista)
                lenguajes.add(e.getAttributeValue("value"));
        } catch( IOException io ) {
            System.out.println(io.getMessage());
        } catch( JDOMException jdomex ) {
            System.out.println(jdomex.getMessage());
        }
        return lenguajes;
    }

    public static List<String> ReadKeywords(String lang, String xmlSource) {
        Namespace ns = Namespace.getNamespace("nsKeywords", "http://www.w3.org/keywords");
        SAXBuilder builder = new SAXBuilder(XMLReaders.XSDVALIDATING);
        List<String> keywords = new ArrayList<String>();
        
        try {
            Element rootNode = builder.build(xmlSource).getRootElement();
            Element leng = rootNode.getChild(lang, ns);
            List<Element> lista = leng.getChildren("keyword", ns);
            
            for(Element e : lista)
                keywords.add(e.getTextNormalize());
        } catch( IOException io ) {
            System.out.println(io.getMessage());
        } catch( JDOMException jdomex ) {
            System.out.println(jdomex.getMessage());
        }
        return keywords;
    }
}
