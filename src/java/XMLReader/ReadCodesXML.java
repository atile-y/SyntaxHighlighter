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
import DataWeb.Code;
import DataWeb.Course;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.List;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;

public class ReadCodesXML {
    private static final Namespace ns = Namespace.getNamespace("nsCodes", "http://www.w3.org/codes");
    private static final SAXBuilder builder = new SAXBuilder(XMLReaders.XSDVALIDATING);;
    
    public static String getNextID(String xmlSource) {
        String id = "9999";
        
        try {
            Element rootNode = builder.build(xmlSource).getRootElement();
            List<Element> lista = rootNode.getChildren("codigo", ns);
            
            List<Integer> ids = new ArrayList<Integer>();
            for(Element e : lista)
                ids.add(Integer.parseInt(e.getAttributeValue("id")));
            Collections.sort(ids);
            Integer i=0;
            while( ids.get(i).equals(i) ) i++;
            id = i.toString();
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch( JDOMException jdomex ) {
            System.out.println(jdomex.getMessage());
        }
        
        return id;
    }
    
    public static List<Code> ReadCodes(Course curso, String xmlSource) {
        List<Code> code = new ArrayList<Code>();

        try {
            Element rootNode = builder.build(xmlSource).getRootElement();
            List<Element> codesList = rootNode.getChildren("codigo", ns);
            
            for( String id : curso.getIDCodigos() )
                for( Element e : codesList )
                    if( e.getAttributeValue("id").equals(id) ) {
                        Code c = new Code(e, ns);
                        code.add(c);
                    }
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch( JDOMException jdomex ) {
            System.out.println(jdomex.getMessage());
        }
        code.sort(new OrdenaCode());
        return code;
    }
}

class OrdenaCode implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        Code a = (Code)o1;
        Code b = (Code)o2;
        
        return a.getNombre().compareToIgnoreCase(b.getNombre());
    }
}
