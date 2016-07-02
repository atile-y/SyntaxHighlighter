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
import java.util.Collections;
import java.util.List;
import org.jdom2.Element;
import org.jdom2.Namespace;

public class Code implements Serializable {
    private String id;
    private String idProfesor;
    private String nombre;
    private String lenguaje;
    private String resaltar;
    private List<String> linea;
    private List<String> comentario;
    
    public Code(Element e, Namespace ns) {
        id = e.getAttributeValue("id");
        idProfesor = e.getChildText("idProfesor", ns);
        nombre = e.getChildText("nombre", ns);
        lenguaje = e.getChildText("lenguaje", ns);
        resaltar = e.getChildText("resaltar", ns);
        
        linea = new ArrayList<String>();
        comentario = new ArrayList<String>();
        for( Element line : e.getChildren("linea", ns) )
            linea.add(line.getText());
        for( Element line : e.getChildren("idComentario", ns) )
            comentario.add(line.getText());
    }
    
    public Code(String id, String idProf, String name,
                String lang, String res, List<String> line) {
        this.id = id;
        this.idProfesor = idProf;
        this.nombre = name;
        this.lenguaje = lang;
        this.resaltar = res;
        this.linea = line;
        this.comentario = new ArrayList<String>();
    }
    
    public String getID(){ return id; }
    public String getIDProfesor(){ return idProfesor; }
    public String getNombre(){ return nombre; }
    public String getLenguaje(){ return lenguaje; }
    public String getResaltar(){ return resaltar; }
    public List<String> getLineas(){ return linea; }
    public List<String> getIDComentarios() { Collections.sort(comentario); return comentario; }

    public void setNombre(String n){ nombre = n; }
    public void setLenguaje(String l){ lenguaje = l; }
    public void setResaltar(String r){ resaltar = r; }
    public void setLineas(List<String> ls){ linea = ls; }
}
