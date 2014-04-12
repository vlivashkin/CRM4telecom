<%-- 
    Document   : home
    Created on : Apr 3, 2014, 9:59:25 AM
    Author     : Nastya
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Средняя продолжительность обработки шага "Выезд рабочего" для разных услуг</h1>
        <jsp:useBean id="greeter" class="greeter.QueryExecutor">
            <%
                out.println(greeter.execute("SELECT P.NAME PRODUCT, round(AVG(CAST(START_DATE AS DATE) - CAST(END_DATE AS DATE))) LEN_DAYS FROM ORDER_PROCESSING OP INNER JOIN ORDERS O ON OP.ORDER_ID = O.ORDER_ID INNER JOIN PRODUCT P ON O.PRODUCT_ID = P.PRODUCT_ID WHERE STEP_NAME LIKE 'ENGINEER_APPOINT' GROUP BY P.NAME"));
            %>
        </jsp:useBean>
    </body>
</html>
