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
import org.jdom2.Element;
import org.jdom2.Namespace;

public class Comment implements Serializable {
    private String id;
    private String idUsuario;
    private String texto;
    private Integer calificacion;
    private String fechaMod;
    
    public Comment(Element e, Namespace ns) {
        id = e.getAttributeValue("id");
        idUsuario = e.getChildText("idUsuario", ns);
        texto = e.getChildText("texto", ns);
        calificacion = Integer.parseInt(e.getChildText("calificacion", ns));
        fechaMod = e.getChildText("fechaMod", ns);
    }
    
    public Comment(String id, String idUser, String texto, int calf, String fecha) {
        this.id = id;
        this.idUsuario = idUser;
        this.texto = texto;
        this.calificacion = calf;
        this.fechaMod = fecha;
    }
    
    public String getID(){ return id; }
    public String getIDUsuario(){ return idUsuario; }
    public String getTexto(){ return texto; }
    public Integer getCalificacion(){ return calificacion; }
    public String getFechaMod(){ return fechaMod; }
}
