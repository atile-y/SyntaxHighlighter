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

function alum_cursos() {
    $( ".nombre-codigo" ).click(function() {
        $(this).toggleClass("codigo-unselected");
        $(this).toggleClass("codigo-selected");
        $cont = $(this).parent().children(".container-codigo");
        $divTag = $cont.children(".codigo");

        if( $divTag.html() === "" ) {
            $.get("alum_curso_codigo.jsp",
                  {id: $(this).parent().attr("id")},
                  getCodigo,
                  "html"
            );
        }
        else {
            $cont.slideToggle(400, function() {
                $divTag.html("");
                if( $cont.children(".comentarios-codigo").html() !== "" )
                    $cont.children(".ver-comentarios").trigger("click");
            });
        }
    });
    
    $( ".ver-comentarios" ).click(function() {
        $coms = $(this).parent().children(".comentarios-codigo");
        $ver = $(this);
        if( $ver.html() === "Mostrar Comentarios" ) {
            $ver.html("Ocultar Comentarios");
            $.get("alum_curso_codigo_comentarios.jsp", {
                    id: $ver.parent().parent().attr("id")
                }, function($data) {
                    $data = $.trim($data);
                    $coms.html($data);
                    $coms.slideToggle();
                    alum_comentarios();
                }, "html");
        }
        else {
            $coms.slideToggle(400, function() {
                $coms.html("");
                $ver.html("Mostrar Comentarios");
            });
        }
    });
    
    $( ".textarea-comentario" ).focusin(function() {
        $(this).css("border", "1px #A9A9A9 solid");
        $(this).siblings("label.error").hide();
    });
    
    $( ".agregar-comentario" ).click(function() {
        $txtarea = $(this).siblings(".textarea-comentario");
        if( $.trim($txtarea.val()) === "" ) {
            $txtarea.css("border", "1px #CC0000 solid");
            $(this).siblings("label.error").show();
            return;
        }
        $.post("guardar_comentario.jsp", {
                codigo_id: $(this).parent().parent().parent().attr("id").substring(6),
                text: $txtarea.val()
            }, function($data) {
                $data = $.trim($data);
                if( $data !== "OK" )
                    alert("Error guardando el comentario.");
                $(".curso-selected").trigger("click");
            }, "text");
    });
}

function getCodigo($data) {
    $data = $.trim($data);
    if( $divTag.hasClass("Bash") )
        $brush = new SyntaxHighlighter.brushes.Bash();
    else if( $divTag.hasClass("CSharp") )
        $brush = new SyntaxHighlighter.brushes.CSharp();
    else if( $divTag.hasClass("C")
          || $divTag.hasClass("Cpp") )
        $brush = new SyntaxHighlighter.brushes.Cpp();
    else if( $divTag.hasClass("CSS") )
        $brush = new SyntaxHighlighter.brushes.CSS();
    else if( $divTag.hasClass("JavaScript") )
        $brush = new SyntaxHighlighter.brushes.JScript();
    else if( $divTag.hasClass("Java") )
        $brush = new SyntaxHighlighter.brushes.Java();
    else if( $divTag.hasClass("PHP") )
        $brush = new SyntaxHighlighter.brushes.Php();
    else if( $divTag.hasClass("Python") )
        $brush = new SyntaxHighlighter.brushes.Python();
    else if( $divTag.hasClass("SQL") )
        $brush = new SyntaxHighlighter.brushes.Sql();
    else if( $divTag.hasClass("Visual Basic") )
        $brush = new SyntaxHighlighter.brushes.Vb();
    else if( $divTag.hasClass("XML")
          || $divTag.hasClass("XHTML")
          || $divTag.hasClass("HTML") )
        $brush = new SyntaxHighlighter.brushes.Xml();
    $brush.init({toolbar: false, highlight: $divTag.siblings("label").html().split(", ") });
    $divTag.html($brush.getHtml($data));
    $cont.slideToggle();
}
