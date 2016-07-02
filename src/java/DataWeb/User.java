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

package DataWeb;

/**
 *
 * @author Alejandro Alberto Yescas Benitez
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Element;
import org.jdom2.Namespace;

public class User implements Serializable {
    private String id;
    private String usuario;
    private String password;
    private String nombres;
    private String apPaterno;
    private String apMaterno;
    private String category;
    private List<String> idCurso;
    
    public User(Element e, Namespace ns) {
        id = e.getAttributeValue("id");
        usuario = e.getChildText("usuario", ns);
        password = e.getChildText("password", ns);
        nombres = e.getChildText("nombres", ns);
        apPaterno = e.getChildText("apPat", ns);
        apMaterno = e.getChildText("apMat", ns);
        category = e.getChildText("category", ns);
        
        idCurso = new ArrayList<String>();
        for( Element curso : e.getChildren("idCurso", ns) )
            idCurso.add(curso.getText());
    }
    
    public User(String id, String user, String pass, String name,
                String pat, String mat, String cat, String idCurso) {
        this.id = id;
        usuario = user;
        password = pass;
        nombres = name;
        apPaterno = pat;
        apMaterno = mat;
        category = cat;
        this.idCurso = new ArrayList<String>();
        this.idCurso.add(idCurso);
    }
    
    public String getID(){ return id; }
    public String getUsuario(){ return usuario; }
    public String getPassword(){ return password; }
    public String getNombreCompleto() { return apPaterno+" "+apMaterno+" "+nombres; }
    public String getNombres(){ return nombres; }
    public String getApPaterno(){ return apPaterno; }
    public String getApMaterno(){ return apMaterno; }
    public String getCategory(){ return category; }
    public List<String> getIDCursos(){ return idCurso; }

    public void setNombres(String name){ nombres = name; }
    public void setApPaterno(String pat){ apPaterno = pat; }
    public void setApMaterno(String mat){ apMaterno = mat; }
}
