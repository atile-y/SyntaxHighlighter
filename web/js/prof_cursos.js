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

function prof_cursos() {
    $( ".nomod-alumno" ).hover(
        function() {
            $(this).css("background-color", "#D4E6FF");
            $(this).find(".borrar-alumno").show();
            $(this).find(".modificar-alumno").show();
        }, function() {
            $(this).css("background-color", "#FFFFFF");
            $(this).find(".borrar-alumno").hide();
            $(this).find(".modificar-alumno").hide();
        }
    );
    
    $( ".modificar-alumno" ).click(function() {
        $(this).parent().hide();
        var name = $(this).siblings(".nombre-alumno").html();
        var apPat = $(this).siblings(".apPat-alumno").html();
        var apMat = $(this).siblings(".apMat-alumno").html();
        $(this).parent().siblings(".mod-alumno").children("input.nombre").val(name);
        $(this).parent().siblings(".mod-alumno").children("input.apPat").val(apPat);
        $(this).parent().siblings(".mod-alumno").children("input.apMat").val(apMat);
        $(this).parent().siblings(".mod-alumno").show();
    });
    
    $( ".mod-alumno > input.apPat" ).focusin(function() {
        $(this).css("border", "2px inset");
        $(this).siblings("label.apPat-label").removeClass("error").html("Apellido Paterno");
    });
    $( ".mod-alumno > input.apMat" ).focusin(function() {
        $(this).css("border", "2px inset");
        $(this).siblings("label.apMat-label").removeClass("error").html("Apellido Materno");
    });
    $( ".mod-alumno > input.nombre" ).focusin(function() {
        $(this).css("border", "2px inset");
        $(this).siblings("label.nombre-label").removeClass("error").html("Nombre(s)");
    });

    $( ".aceptar-alumno" ).click(function() {
        var valido = true;
        $apPat = $(this).siblings("input.apPat");
        $apMat = $(this).siblings("input.apMat");
        $nombre = $(this).siblings("input.nombre");
        if( $apPat.val() === "" ) {
            $apPat.css("border", "2px #CC0000 solid");
            $(this).siblings("label.apPat-label").html("Ingrese un apellido.").addClass("error");
            valido = false;
        }
        if( $apMat.val() === "" ) {
            $apMat.css("border", "2px #CC0000 solid");
            $(this).siblings("label.apMat-label").html("Ingrese un apellido.").addClass("error");
            valido = false;
        }
        if( $nombre.val() === "" ) {
            $nombre.css("border", "2px #CC0000 solid");
            $(this).siblings("label.nombre-label").html("Ingrese un nombre.").addClass("error");
            valido = false;
        }
        if( !valido )
            return;

        $nom = $(this).parent().siblings(".nomod-alumno").children("p.nombre-alumno");
        $pat = $(this).parent().siblings(".nomod-alumno").children("p.apPat-alumno");
        $mat = $(this).parent().siblings(".nomod-alumno").children("p.apMat-alumno");
        if( $nombre.val() === $nom.html() && $apPat.val() === $pat.html() && $apMat.val() === $mat.html() ) {
            $(this).parent().hide();
            $(this).siblings("input").val("").trigger("focusin");
            $(this).parent().siblings(".nomod-alumno").show();
            return;
        }
        $t = $(this);
        $.post("guardar_alumno.jsp", {
                id: $(this).parent().parent().attr("id").substring(6),
                nombres: $nombre.val(),
                apPat: $apPat.val(),
                apMat: $apMat.val()
            }, function($data) {
                $data = $.trim($data);
                if( $data === "OK" ) {
                    $nom.html($nombre.val());
                    $pat.html($apPat.val());
                    $mat.html($apMat.val());
                }
                else
                    alert("Error al guardar.");
                $t.parent().hide();
                $t.siblings("input").val("").trigger("focusin");
                $t.parent().siblings(".nomod-alumno").show();
            }, "text");
    });
    
    $( ".cancelar-alumno" ).click(function() {
        $(this).parent().hide();
        $(this).siblings("input").val("").trigger("focusin");
        $(this).parent().siblings(".nomod-alumno").show();
    });
    
    $( ".borrar-alumno" ).click(function() {
        var name = $(this).siblings("p.apPat-alumno").html() + " " +
                $(this).siblings("p.apMat-alumno").html() + " " +
                $(this).siblings("p.nombre-alumno").html();
        if( confirm("¿Desea borrar a "+name+"?") ) {
            $.post("borrar_alumno.jsp", {
                    id: $(this).parent().parent().attr("id").substring(6)
                }, function($data) {
                    $data = $.trim($data);
                    if( $data !== "OK" )
                        alert("Error eliminando al alumno.");
                    window.location.reload();
                }, "text");
        }
    });

    $( "#nuevo-apPat" ).focusin(function() {
        $(this).css("border", "2px inset");
        $(this).siblings("label#nuevo-apPat-label").removeClass("error").html("Apellido Paterno");
    });
    $( "#nuevo-apMat" ).focusin(function() {
        $(this).css("border", "2px inset");
        $(this).siblings("label#nuevo-apMat-label").removeClass("error").html("Apellido Materno");
    });
    $( "#nuevo-nombre" ).focusin(function() {
        $(this).css("border", "2px inset");
        $(this).siblings("label#nuevo-nombre-label").removeClass("error").html("Nombre(s)");
    });
    
    $( "#agregar-alumno" ).click(function() {
        var valido = true;
        $apPat = $(this).siblings("input#nuevo-apPat");
        $apMat = $(this).siblings("input#nuevo-apMat");
        $nombre = $(this).siblings("input#nuevo-nombre");
        if( $apPat.val() === "" ) {
            $apPat.css("border", "2px #CC0000 solid");
            $(this).siblings("label#nuevo-apPat-label").html("Ingrese un apellido.").addClass("error");
            valido = false;
        }
        if( $apMat.val() === "" ) {
            $apMat.css("border", "2px #CC0000 solid");
            $(this).siblings("label#nuevo-apMat-label").html("Ingrese un apellido.").addClass("error");
            valido = false;
        }
        if( $nombre.val() === "" ) {
            $nombre.css("border", "2px #CC0000 solid");
            $(this).siblings("label#nuevo-nombre-label").html("Ingrese un nombre.").addClass("error");
            valido = false;
        }
        if( !valido )
            return;

        $t = $(this);
        $.post("guardar_alumno.jsp", {
                nombres: $nombre.val(),
                apPat: $apPat.val(),
                apMat: $apMat.val()
            }, function($data) {
                $data = $.trim($data);
                if( $data === "Error" )
                    alert("Error al guardar.");
                else if( $data !== "OK" )
                    alert($data);
                window.location.reload();
            }, "text");
    });

    $( ".nombre-codigo" ).click(function() {
        $(this).toggleClass("codigo-unselected");
        $(this).toggleClass("codigo-selected");
        $cont = $(this).parent().children(".container-codigo");
        $divTag = $cont.children(".codigo");

        if( $divTag.html() === "" ) {
            $.get("prof_curso_codigo.jsp",
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
            $.get("prof_curso_codigo_comentarios.jsp", {
                    id: $ver.parent().parent().attr("id")
                }, function($data) {
                    $data = $.trim($data);
                    $coms.html($data);
                    $coms.slideToggle();
                    prof_comentarios();
                }, "html");
        }
        else {
            $coms.slideToggle(400, function() {
                $coms.html("");
                $ver.html("Mostrar Comentarios");
            });
        }
    });

    $( ".modificar-codigo" ).click(function() {
        var cod_id = $(this).parent().parent().attr("id").substring(6);
        $("#codigo-id").val(cod_id);
        $("#form-mod").submit();
    });
    
    $( ".borrar-codigo" ).click(function() {
        var cod_id = $(this).parent().parent().attr("id").substring(6);
        var tit = $(this).parent().parent().children("p.nombre-codigo").html();
        if( confirm("¿Seguro que quiere borrar el codigo \""+tit+"\"?") ) {
            $.post("borrar_codigo.jsp", {
                    codigo_id: cod_id
                }, function($data) {
                    $data = $.trim($data);
                    if( $data !== "OK" )
                        alert("Error borrando el codigo.");
                    window.location.reload();
                }, "text");
        }
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
