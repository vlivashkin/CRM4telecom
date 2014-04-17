package com.crm4telecom.ejb.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchQuery {

    public String getSqlQuery(String what, Map<String, List<String>> parameters) {
        String sqlQuery = "SELECT " + what;
        if (!parameters.isEmpty()) {
            sqlQuery += " WHERE";
            for (String paramProperty : parameters.keySet()) {
                List<String> val = (List<String>) parameters.get(paramProperty);
                if (val.size() > 1) {
                    sqlQuery += " (";
                    for (String val1 : val) {
                        sqlQuery += " LOWER(c." + paramProperty + ") LIKE LOWER('" + val1 + "') OR";
                    }
                    sqlQuery = sqlQuery.substring(0, sqlQuery.length() - " OR".length());
                    sqlQuery += ") AND";
                } else {
                    String check = (String) paramProperty;

                    if (check.compareTo("fromDate") == 0) {
                        try {
                            Date date = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).parse(val.get(0));
                            sqlQuery += " c.orderDate > '" + new Timestamp(date.getTime()) + "' AND";
                        } catch (ParseException e) {
                            System.err.println("shit");
                        }
                    } else if (check.compareTo("toDate") == 0) {
                        try {
                            Date date = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).parse(val.get(0));
                            Calendar c = Calendar.getInstance();
                            c.setTime(date);
                            c.add(Calendar.DATE, 1);
                            date = c.getTime();
                            sqlQuery += " c.orderDate < '" + new Timestamp(date.getTime()) + "' AND";
                        } catch (ParseException e) {
                            System.err.println("shit");
                        }
                    } else if (check.compareTo("customerId") == 0 || check.compareTo("employeeId") == 0) {
                        sqlQuery += people(check, val.get(0));
                    } else if (check.compareTo("fromCustomerId") == 0) {
                        sqlQuery += " c.customerId >= '" + Long.parseLong(val.get(0)) + "' AND";
                    } else if (check.compareTo("toCustomerId") == 0) {
                        sqlQuery += " c.customerId <= '" + Long.parseLong(val.get(0)) + "' AND";
                    } else if (check.compareTo("fromOrderId") == 0) {
                        sqlQuery += " c.orderId >= '" + Long.parseLong(val.get(0)) + "' AND";
                    } else if (check.compareTo("toOrderId") == 0) {
                        sqlQuery += " c.orderId <= '" + Long.parseLong(val.get(0)) + "' AND";
                    } else {
                        sqlQuery += " LOWER(c." + paramProperty + ") LIKE LOWER('%" + val.get(0) + "%') AND";
                    }

                }
            }
            sqlQuery = sqlQuery.substring(0, sqlQuery.length() - " AND".length());
            if (sqlQuery.endsWith(" WHERE")) {
                sqlQuery = sqlQuery.substring(0, sqlQuery.length() - " WHERE".length());
            }
        }
        return sqlQuery;
    }

    private String people(String entity, String query) {
        query = query.trim();

        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(query);

        if (!m.matches()) {
            p = Pattern.compile("#[0-9]+");
            m = p.matcher(query);

            if (!m.matches()) {
                p = Pattern.compile("[a-zA-Zа-яА-я]+");
                m = p.matcher(query);

                if (!m.matches()) {
                    return " AND";
                } else {
                    return " (LOWER(c." + entity + ".firstName) LIKE LOWER('%" + query + "%') OR LOWER(c." + entity + ".lastName) LIKE LOWER('%" + query + "%')) AND";
                }
            } else {
                query = query.substring(1);
            }
        }

        return " LOWER(c." + entity + ".employeeId) LIKE LOWER ('%" + query + "%') AND";
    }
}
