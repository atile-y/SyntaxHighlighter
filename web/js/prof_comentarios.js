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

function prof_comentarios() {
    $( ".comentario > img" ).hover(
        function() {
            $rat = $(this).attr("class").substring(4);
            $imgs = $(this).parent().children("img");
            for($i=0;$i<5;$i++) {
                if( $i < $rat )
                    $imgs.eq($i).attr("src", "../img/rat_on.png");
                else
                    $imgs.eq($i).attr("src", "../img/rat_off.png");
            }
        }, function() {
            $imgs = $(this).parent().children("img");
            for($i=0;$i<5;$i++) {
                $alt = $imgs.eq($i).attr("alt");
                $imgs.eq($i).attr("src", "../img/rat_"+$alt+".png");
            }
    });
    $( ".rate0" ).hover(
        function() {
            $imgs = $(this).parent().children("img");
            for($i=0;$i<5;$i++)
                $imgs.eq($i).attr("src", "../img/rat_off.png");
        }, function() {
            $imgs = $(this).parent().children("img");
            for($i=0;$i<5;$i++) {
                $alt = $imgs.eq($i).attr("alt");
                $imgs.eq($i).attr("src", "../img/rat_"+$alt+".png");
            }
    });
    
    $( ".comentario > img" ).click(function() {
        $rat = $(this).attr("class").substring(4);
        $cod = $(this).parents(".container-codigo").parent().attr("id");
        $cod_id = $cod.substring(6);
        $com_id = $(this).parent().attr("id").substring(10);
        $coms = $(this).parent().parent();
        $.post("calificar_comentario.jsp", {
                codigo_id: $cod_id,
                comentario_id: $com_id,
                rating: $rat
            }, function($data) {
                $data = $.trim($data);
                if( $data === "OK" )
                    $.get("prof_curso_codigo_comentarios.jsp", {
                            id: $cod
                        }, function($data) {
                            $data = $.trim($data);
                            $coms.fadeToggle(function() {
                                $coms.html($data).fadeToggle();
                                prof_comentarios();
                            })
                        }, "html");
            }, "text");
    });
    $( ".rate0" ).click(function() {
        $cod = $(this).parents(".container-codigo").parent().attr("id");
        $cod_id = $cod.substring(6);
        $com_id = $(this).parent().attr("id").substring(10);
        $coms = $(this).parent().parent();
        $.post("calificar_comentario.jsp", {
                codigo_id: $cod_id,
                comentario_id: $com_id,
                rating: 0
            }, function($data) {
                $data = $.trim($data);
                if( $data === "OK" )
                    $.get("prof_curso_codigo_comentarios.jsp", {
                            id: $cod
                        }, function($data) {
                            $data = $.trim($data);
                            $coms.fadeToggle(function() {
                                $coms.html($data).fadeToggle();
                                prof_comentarios();
                            });
                        }, "html");
            }, "text");
    });
}
