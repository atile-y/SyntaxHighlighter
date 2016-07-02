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

$( document ).ready( profesor );

function profesor() {
    $("#cursos > p").click(function() {
        $("#cursos > p").removeClass("curso-selected");
        $(this).addClass("curso-selected");
        $.get("prof_cursos.jsp", {id: $(this).attr("id")}, function($data) {
            $( "#content" ).html($data);
            prof_cursos();
        }, "html");
    });
    
    $("#nuevo-curso").click(function() {
        $("ul#acciones").hide();
        $("#agregar-curso").show().children("#input-curso").val("");
    });
    
    $("#input-curso").focusin(function() {
        $(this).css("border", "2px inset");
        $(this).siblings("label.error").css("display", "none");
        $(this).siblings("#aceptar-curso").show();
        $(this).siblings("#cancelar-curso").show();
    });
    
    $("#aceptar-curso").click(function() {
        $curso = $("#input-curso");
        if( $curso.val() === "" ) {
            $curso.css("border", "2px #CC0000 solid");
            $(this).hide();
            $(this).siblings("#cancelar-curso").hide();
            $(this).siblings("label.error").css("display", "inline-block");
            return;
        }
        $.post("guardar_curso.jsp", {
                nombre: $curso.val()
            }, function($data) {
                $data = $.trim($data);
                if( $data !== "OK" )
                    alert("Error al guardar el curso.");
                window.location.reload();
            }, "text");
    });

    $("#cancelar-curso").click(function() {
        $("#agregar-curso").hide().children("#input-curso").val("").trigger("focusin");
        $("ul#acciones").show();
    });
    
    $("#borrar-curso").click(function() {
        if( typeof $(".curso-selected").html() !== "undefined" ) {
            $curso = $(".curso-selected");
            if( confirm("¿Seguro que quiere borrar el curso "+$curso.html()+"?") ) {
                $.post("borrar_curso.jsp", {
                        id: $curso.attr("id").substring(5)
                    }, function($data) {
                        $data = $.trim($data);
                        if( $data !== "OK" )
                            alert("Error al borrar el curso.");
                        window.location.reload();
                    }, "text");
            }
        }
    });
    
    $("#logout > img").click(function() {
        window.location = "../login.jsp";
    });
}
