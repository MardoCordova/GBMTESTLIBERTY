<%-- 
    Document   : mq
    Created on : Jul 19, 2021, 4:05:10 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World! MQ</h1>
        <br/>
        <form action="servlet3" method="post">
            <textarea rows=\"5\" cols=\"30\" name="feedback"></textarea>
            <br/>
            <label>Persistencia</label>
            <textarea rows=\"5\" cols=\"30\" name="persistente"></textarea>
            <br/>
            <input type="submit" name="putMessage" value="Enviar mensaje"/>
        </form>
        <form action="servlet3" method="post">
            <br/>
            <input type="submit" name="getMessage" value="Obtener Mensaje"/>
        </form>
        <div id="result">
            <pre>
                <h1>${requestScope.utilOutput}</h1>
            </pre>
        </div>
    </body>
</html>
