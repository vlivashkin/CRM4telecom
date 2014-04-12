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
        <jsp:useBean id="greeter" class="greeter.QueryExecutor">
            <%
                out.println(greeter.execute("select case when row_number() over (partition by region order by customer.street) = 1 then region else ' ' end region, customer.street, count(customer_id) as number_of_customers from customer inner join streets_regions on customer.street = streets_regions.street where status != 'Off' group by region, customer.street"));
            %>
        </jsp:useBean>
    </body>
</html>
