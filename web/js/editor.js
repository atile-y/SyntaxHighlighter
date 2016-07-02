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

$( document ).ready( editor );

function getCursorPosition(item) {
    var input = item.get(0);
    if( !input ) return;
    if( "selectionStart" in input )
        return input.selectionStart;
    else if( document.selection ) {
        input.focus();
        var sel = document.selection.createRange();
        //var selLen = document.selection.createRange().text.length;
        sel.moveStart("character", -input.value.lenght);
        return sel.text.length; // - selLen;
    }
    return -1;
}

function setCursorPosition(item, pos) {
    var input = item.get(0);
    if( !input ) return;
    if( "selectionStart" in input ) {
        input.selectionStart = pos;
        input.selectionEnd = pos;
        input.focus();
    }
    else {
        input.focus();
        var sel = document.selection.createRange();
        sel.moveStart("character", -input.value.length);
        sel.moveStart("character", pos);
        sel.moveEnd("character", 0);
        sel.select();
    }
}

function getFullWordAtPosition(str, idx) {
    var ini, fin;
    
    for(ini=idx-1;ini>=0;ini--)
        if( str[ini].toLowerCase() < 'a' || str[ini].toLowerCase() > 'z' )
            break;
    for(fin=idx;fin<str.length;fin++)
        if( str[fin].toLowerCase() < 'a' || str[fin].toLowerCase() > 'z' )
            break;
    
    return str.substring(ini+1, fin);
}

function getWordAtPosition(str, idx) {
    var ini;
    
    for(ini=idx-1;ini>=0;ini--)
        if( str[ini].toLowerCase() < 'a' || str[ini].toLowerCase() > 'z' )
            break;
    
    return str.substring(ini+1, idx);
}

function getWordAtCursorPosition(jQitem) {
    return getWordAtPosition(jQitem.val(), getCursorPosition(jQitem));
}

function insertAtCursorPosition(jQitem, word) {
    var pos = getCursorPosition(jQitem);
    
    var str;
    if( $("select").val() === "SQL" ) {
        var text = jQitem.val();
        var ini;
        for(ini=pos-1;ini>=0;ini--)
            if( text[ini].toLowerCase() < 'a' || text[ini].toLowerCase() > 'z' )
                break;
        str = text.substring(0, ini);
        for(var i=ini;i<pos;i++) {
            if( word[0] >= 'A' && word[0] <= 'Z' )
                str += text[i].toUpperCase();
            else
                str += text[i].toLowerCase();
        }
        str += (word + jQitem.val().substring(pos));
    }
    else if( $("select").val() === "HTML" || $("select").val() === "XHTML" ) {
        str = jQitem.val().substring(0, pos);
        str += (word + '>' + jQitem.val().substring(pos));
    }
    else {
        str = jQitem.val().substring(0, pos);
        str += (word + jQitem.val().substring(pos));
    }

    jQitem.val(str);
    
    if( $("select").val() === "HTML" || $("select").val() === "XHTML" )
        setCursorPosition(jQitem, pos+word.length+1);
    else
        setCursorPosition(jQitem, pos+word.length);
}

function editor() {
    var escaped = false;
    $( "#codigo" ).keydown(function(event) {
        if( event.keyCode === 9 ) {
            event.preventDefault();
            if( $( "#sugerencias" ).css("display") === "none" )
                insertAtCursorPosition($(this), "    ");
            else {
                $hov = $("#sugerencias > p.hover");
                $ps = $("#sugerencias > p");
                $idx = $ps.index($hov);
                $hov.removeClass("hover");
                $($ps.get($idx+1===$ps.size()?0:$idx+1)).addClass("hover");
            }
        }
        else if( event.keyCode === 27 ) {
            $( "#sugerencias" ).hide().html("");
            escaped = true;
        }
        else if( event.keyCode === 13 ) {
            if( $( "#sugerencias" ).css("display") !== "none" ) {
                event.preventDefault();
                $keyword = $("p.hover").html();
                $textarea = $("#codigo");
                $word = getWordAtCursorPosition($textarea);

                insertAtCursorPosition($textarea, $keyword.substring($word.length));

                $( "#sugerencias" ).hide().html("");
            }
        }
    });

    $( "#codigo" ).on("keypress keyup click", function(event) {
        if( event.keyCode === 9 || escaped ) {
            escaped = false;
            return;
        }
        if( typeof $timeoutID !== "undefined" ) {
            window.clearTimeout($timeoutID);
            $( "#sugerencias" ).hide().html("");
        }
        $text = $(this).val();
        $pos = getCursorPosition($(this));
        $timeoutID = window.setTimeout(function() {
            $word = getWordAtPosition($text, $pos);
            $fword = getFullWordAtPosition($text, $pos);
            if( $word === "" ) {
                $( "#sugerencias" ).html("");
                return;
            }
            else if( $("select").val() === "HTML" || $("select").val() === "XHTML" ) {
                if( $pos-$word.lenght-1 < 0 ) {
                    $( "#sugerencias" ).html("");
                    return;
                }
                if( $text[$pos-$word.length-1] !== '<' ) {
                    if( $text[$pos-$word.length-2] !== '<' || ($text[$pos-$word.length-1] !== '!' && $text[$pos-$word.length-1] !== '/') ) {
                        $( "#sugerencias" ).html("");
                        return;
                    }
                }
            }
            $.get("keywords.jsp", {
                    lang: $("select").val(),
                    word: $word,
                    fword: $fword,
                    close: $text[$pos-$word.length-1] === '/'
                }, function($data) {
                    $data = $.trim($data);
                    $line = 1;
                    $col = 1;
                    for($i=0;$i<$pos;$i++) {
                        if( $text[$i] === '\n' ) {
                            $line++;
                            $col = 1;
                        }
                        else $col++;
                    }

                    if( $data !== "" ) {
                        $( "#sugerencias" ).html($data)
                            .css("top", 20*$line+14)
                            .css("left", 11*$col+16)
                            .show();
                        $($("#sugerencias > p").get(0)).addClass("hover");
                    }
                    else
                        $( "#sugerencias" ).hide().html("");
                }, "html");
        }, 100);
    });
    
    $( "#sugerencias" ).on("click", "p", function() {
        $keyword = $(this).html();
        $textarea = $("#code-codigo > textarea");
        $word = getWordAtCursorPosition($textarea);
        
        insertAtCursorPosition($textarea, $keyword.substring($word.length));

        $( "#sugerencias" ).hide().html("");
    });
    
    $( "#sugerencias" ).on("mouseenter", "p", function() {
        if( !$(this).hasClass("hover") ) {
            $("#sugerencias > p.hover").removeClass("hover");
            $(this).addClass("hover");
        }
    });
    
    $( "#guardar" ).click(function(event) {
        event.preventDefault();
        var valido = true;
        $titCod = $("#titulo-codigo");
        if( $titCod.children("input").val() === "" ) {
            $titCod.children("label").show();
            $titCod.css("border-color", "#CC0000");
            valido = false;
        }
        $codCod = $("#code-codigo");
        if( $codCod.children("textarea").val() === "" ) {
            $codCod.children("label").show();
            $codCod.css("border-color", "#CC0000");
            valido = false;
        }

        if( !valido )
            return;

        $.post("guardar_codigo.jsp", {
                titulo: $("#titulo").val(),
                lang: $("#lenguaje").val(),
                code: $("#codigo").val(),
                resaltar: $("#resaltar").val()
            }, function($data) {
                $data = $.trim($data);
                if( $data === "OK" )
                    alert("Codigo guardado correctamente.");
                else
                    alert("Error guardando el codigo.");
                window.location = "../profesor/";
            }, "text");
    });
    
    $( "#cancelar" ).click(function(event) {
        if( !confirm("Se perderan los cambios realizados.") )
            event.preventDefault();
    });
    
    $("#titulo").focusin(function() {
        $(this).parent().css("border-color", "#B8B8B8")
                .children("label").hide();
    });
    $("#codigo").focusin(function() {
        $(this).parent().css("border-color", "#B8B8B8")
                .children("label").hide();
    });
}
