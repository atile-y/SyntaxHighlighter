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
import DataWeb.Course;
import DataWeb.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;

public class ReadUsersXML {
    private static final Namespace ns = Namespace.getNamespace("nsUsers", "http://www.w3.org/users");
    private static final SAXBuilder builder = new SAXBuilder(XMLReaders.XSDVALIDATING);
    
    public static String getNextID(String xmlSource) {
        String id = "9999";
        
        try {
            Element rootNode = builder.build(xmlSource).getRootElement();
            List<Element> lista = rootNode.getChildren("user", ns);
            
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
    
    public static User findUserByFullName(User usuario, String xmlSource) {
        try {
            Element rootNode = builder.build(xmlSource).getRootElement();
            List<Element> lista = rootNode.getChildren("user", ns);
            
            for(Element e : lista)
                if( e.getChildTextTrim("nombres", ns).equals(usuario.getNombres())
                 && e.getChildTextTrim("apPat", ns).equals(usuario.getApPaterno())
                 && e.getChildTextTrim("apMat", ns).equals(usuario.getApMaterno()) )
                    return new User(e, ns);
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch( JDOMException jdomex ) {
            System.out.println(jdomex.getMessage());
        }
        
        return null;
    }
    
    public static User ReadUser(String usuario, String pass, String xmlSource) {
        User user = null;
        
        try {
            Element rootNode = builder.build(xmlSource).getRootElement();
            List<Element> usersList = rootNode.getChildren("user", ns);
            
            for(Element e : usersList) {
                if( e.getChildText("usuario", ns).equals(usuario)
                 && e.getChildText("password", ns).equals(pass) ) {
                    user = new User(e, ns);
                    break;
                }
            }
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch( JDOMException jdomex ) {
            System.out.println(jdomex.getMessage());
        }
        
        return user;
    }

    public static User ReadUser(String id, String xmlSource) {
        User user = null;

        try {
            Element rootNode = builder.build(xmlSource).getRootElement();
            List<Element> usersList = rootNode.getChildren("user", ns);
            
            user = null;
            for(Element e : usersList) {
                if( e.getAttributeValue("id").equals(id) ) {
                    user = new User(e, ns);
                    break;
                }
            }
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch( JDOMException jdomex ) {
            System.out.println(jdomex.getMessage());
        }
        
        return user;
    }
    
    public static String ReadUserFullName(String id, String xmlSource) {
        User user = null;
        
        try {
            Element rootNode = builder.build(xmlSource).getRootElement();
            List<Element> usersList = rootNode.getChildren("user", ns);
            
            for(Element e : usersList)
                if( e.getAttributeValue("id").equals(id) )
                    user = new User(e, ns);
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch( JDOMException jdomex ) {
            System.out.println(jdomex.getMessage());
        }
        
        if( user == null )
            return "?";
        return user.getNombreCompleto();
    }

    public static List<User> ReadUsers(Course curso, String xmlSource) {
        List<User> alumnos = new ArrayList<User>();

        try {
            Element rootNode = builder.build(xmlSource).getRootElement();
            List<Element> usersList = rootNode.getChildren("user", ns);
            
            for( String id : curso.getIDAlumnos() )
                for(Element e : usersList)
                    if( e.getAttributeValue("id").equals(id) ) {
                        User user = new User(e, ns);
                        alumnos.add(user);
                    }
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch( JDOMException jdomex ) {
            System.out.println(jdomex.getMessage());
        }
        Collections.sort(alumnos, new OrdenaUser());
        return alumnos;
    }
}

class OrdenaUser implements Comparator {
    @Override
    public int compare(Object a, Object b) {
        User u1 = (User)a;
        User u2 = (User)b;
        String s1 = u1.getApPaterno() + " " + u1.getApMaterno();
        s1 += (" " + u1.getNombres());
        String s2 = u2.getApPaterno() + " " + u2.getApMaterno();
        s2 += (" " + u2.getNombres());
        
        return s1.compareToIgnoreCase(s2);
    }
}
