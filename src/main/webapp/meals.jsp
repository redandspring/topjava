<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="meals" scope="request" type="java.util.List<ru.javawebinar.topjava.model.MealWithExceed>"/>
<html>
<head>
    <title>Meals List</title>
    <style>
        table.table {
            background-color: darkred;
        }
        table.table th {
            background-color: khaki;
        }
        table.table tr.green td {
            background-color: #9de0b4;
        }
        table.table tr.red td {
            background-color: #ed8e9e;
        }

    </style>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meals List</h2>

<div style="padding-left: 40px">

    <a href="meals?action=EDIT">add new</a>
    <br />
    <br />
<table class="table" cellpadding="6px" cellspacing="1" border="0">
    <tr>
        <th>date time</th>
        <th>description</th>
        <th>calories</th>
        <th>exceed</th>
        <th colspan="2">action</th>
    </tr>
    <%--@elvariable id="meals" type="java.util.List"--%>
    <c:forEach items="${meals}" var="meal" >
        <tr class="${meal.exceed ? "red" : "green"}">
            <td>
                <c:set var="cleanedMealDateTime" value="${fn:replace(meal.dateTime, 'T', ' ')}" />
                <fmt:parseDate value="${cleanedMealDateTime}" pattern="yyyy-MM-dd HH:mm"  var="mealDateTime" type="date" />
                <fmt:formatDate pattern="dd MMM yyyy HH:mm" value="${mealDateTime}" /></td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>${meal.exceed ? "yes" : "no"}</td>
            <td><a href="meals?action=EDIT&id=${meal.id}">edit</a></td>
            <td><a href="meals?action=DELETE&id=${meal.id}">delete</a></td>
        </tr>
    </c:forEach>
</table>
</div>

</body>
</html>
