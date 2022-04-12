<%-- 
    Document   : opciones
    Created on : Jul 19, 2021, 11:54:46 AM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Opciones !!!</title>
    </head>
    <body>
        <h1>Seleccionar Opcion</h1>
        <br/>
        <br/>
        <br/>
        <br/>
        <table>
            <tr><td><form action="servlet2" method="post">
                        <input type="submit" value="Probar Base de Datos"/>
                    </form></td><td><form action="servlet3" method="post">
                        <input type="submit" name="mq" value="Probar Servicio MQ"/>
                    </form></td></tr>   
            <td><form action="servlet4" method="post">
                        <input type="submit" name="load" value="Listener MQ"/>
                    </form></td></tr>
            <tr></tr>
        </table>


    </body>
</html>
