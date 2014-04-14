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
        <h2>Average price of services for different markets</h2>
        <jsp:useBean id="greeter" class="com.crm4telecom.report.QueryExecutor">
            <%
                out.println(greeter.execute("select case when row_number() over (partition by m.name order by p.name) = 1 then m.name else ' ' end market, p.name as product, avg(cp.price)||' RUB' as average_price from customer_products cp inner join product p on cp.product_id = p.product_id  inner join markets_customers mc on mc.customer_id = cp.customer_id  inner join market m on m.market_id = mc.market_id group by m.name, p.name"));
            %>
        </jsp:useBean>
    </body>
</html>
