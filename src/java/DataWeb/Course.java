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

public class Course implements Serializable {
    private String id;
    private String idProfesor;
    private List<String> idAlumno;
    private List<String> idCodigo;
    private String nombre;

    public Course(Element e, Namespace ns) {
        id = e.getAttributeValue("id");
        idProfesor = e.getChildText("idProfesor", ns);
        nombre = e.getChildText("nombre", ns);
        
        idAlumno = new ArrayList<String>();
        idCodigo = new ArrayList<String>();
        for( Element alumno : e.getChildren("idAlumno", ns) )
            idAlumno.add(alumno.getText());
        for( Element codigo : e.getChildren("idCodigo", ns) )
            idCodigo.add(codigo.getText());
    }
    
    public Course(String id, String idProf, String name) {
        this.id = id;
        idProfesor = idProf;
        nombre = name;
        idAlumno = new ArrayList<String>();
        idCodigo = new ArrayList<String>();
    }
    
    public String getID(){ return id; }
    public String getIDProfesor(){ return idProfesor; }
    public List<String> getIDAlumnos(){ return idAlumno; }
    public List<String> getIDCodigos(){ return idCodigo; }
    public String getNombre(){ return nombre; }
}
