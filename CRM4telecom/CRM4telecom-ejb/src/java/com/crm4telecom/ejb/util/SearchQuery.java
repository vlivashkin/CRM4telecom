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

    public static String getSearchQuery(String what, Map<String, List<String>> parameters) {
        StringBuffer sqlQuery = new StringBuffer("SELECT ");
        sqlQuery.append(what);
        if (!parameters.isEmpty()) {
            sqlQuery.append(" WHERE");
            for (String paramProperty : parameters.keySet()) {
                List<String> val = (List<String>) parameters.get(paramProperty);
                if (val.size() > 1) {
                    sqlQuery.append(" (");
                    for (String val1 : val) {
                        sqlQuery.append(" LOWER(c.").append(paramProperty)
                                .append(") LIKE LOWER('").append(val1).append("') OR");
                    }
                    sqlQuery.append(sqlQuery.substring(0, sqlQuery.length() - " OR".length()));
                    sqlQuery.append(") AND");
                } else {
                    String check = (String) paramProperty;

                    if (check.equals("fromDate")) {
                        try {
                            Date date = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).parse(val.get(0));
                            sqlQuery.append(" c.orderDate > '").append(new Timestamp(date.getTime())).append("' AND");
                        } catch (ParseException e) {
                            System.err.println("shit");
                        }
                    } else if (check.equals("toDate")) {
                        try {
                            Date date = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).parse(val.get(0));
                            Calendar c = Calendar.getInstance();
                            c.setTime(date);
                            c.add(Calendar.DATE, 1);
                            date = c.getTime();
                            sqlQuery.append(" c.orderDate < '")
                                    .append(new Timestamp(date.getTime())).append("' AND");
                        } catch (ParseException e) {
                            System.err.println("shit");
                        }
                    } else if (check.equals("customer") || check.equals("employee")) {
                        sqlQuery.append(people(check, val.get(0)));
                    } else if (check.endsWith("Id")) {
                        sqlQuery.append(id(check, val.get(0)));
                    } else {
                        sqlQuery.append(" LOWER(c.").append(paramProperty)
                                .append(") LIKE LOWER('%").append(val.get(0))
                                .append("%') AND");
                    }

                }
            }
            sqlQuery.delete(sqlQuery.length() - " AND".length(), sqlQuery.length());
            if (sqlQuery.lastIndexOf(" WHERE") == sqlQuery.length() - " WHERE".length()) {
                sqlQuery = sqlQuery.delete(sqlQuery.length() - " WHERE".length(), sqlQuery.length());
            }
        }
        return sqlQuery.toString();
    }

    private static String people(String entity, String query) {
        query = query.trim();

        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(query);

        if (!m.matches()) {
            p = Pattern.compile("#[0-9]+");
            m = p.matcher(query);

            if (!m.matches()) {
                p = Pattern.compile("[a-zA-Z]+");
                m = p.matcher(query);

                if (!m.matches()) {
                    return " FALSE AND";
                } else {
                    return new StringBuffer(" (LOWER(c.").append(entity)
                            .append("Id.firstName) LIKE LOWER('%").append(query)
                            .append("%') OR LOWER(c.").append(entity)
                            .append("Id.lastName) LIKE LOWER('%").append(query)
                            .append("%')) AND").toString();
                }
            } else {
                query = query.substring(1);
            }
        }

        return new StringBuffer(" LOWER(c.").append(entity).append("Id.").append(entity)
                .append("Id) LIKE LOWER ('%").append(query).append("%') AND").toString();
    }

    private static String id(String entity, String query) {
        entity = entity.substring(0, entity.length() - "Id".length());

        String operator;

        if (entity.startsWith("from")) {
            operator = ">=";
            entity = entity.substring("from".length());
        } else if (entity.startsWith("to")) {
            operator = "<=";
            entity = entity.substring("to".length());
        } else {
            return " FALSE AND ";
        }

        return " c." + entity.toLowerCase() + "Id " + operator + " '" + Long.parseLong(query) + "' AND";
    }

    public static String getCompleteQuery(Class clazz, String rawString) {
        String raw = rawString.trim();

        String[] split = raw.split(" ");
        StringBuffer sqlQuery = new StringBuffer("SELECT c ").append("FROM ")
                .append(clazz.getName().substring(clazz.getName().lastIndexOf('.') + 1))
                .append(" c " + "WHERE (LOWER(c.firstName) LIKE LOWER('%")
                .append(split[0]).append("%') " + "or LOWER(c.lastName) LIKE LOWER('%")
                .append(split[0]).append("%'))");
        if (split.length > 1) {
            sqlQuery.append(" and (LOWER(c.firstName) LIKE LOWER('%").append(split[1])
                    .append("%') " + "or LOWER(c.lastName) LIKE LOWER('%")
                    .append(split[1]).append("%'))");
        }

        return sqlQuery.toString();
    }
}
