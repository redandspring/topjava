<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal" />
<html>
<head>
    <title>Meal Form</title>
    <style>
        .form-group {
            padding: 6px;
        }
    </style>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>FORM</h2>

<div style="padding-left: 40px">
<form method="post" action="meals">

    <div class="form-group">
        <label for="formDateTime">Date and Time: </label>
        <br />
        <input id="formDateTime" type="datetime" value="${meal.dateTime}" name="dateTime"  />
    </div>

    <div class="form-group">
        <label for="formDescription">Description: </label>
        <br />
        <input id="formDescription" type="text" value="${meal.description}" name="description" />
    </div>

    <div class="form-group">
        <label for="formCalories">Calories: </label>
        <br />
        <input id="formCalories" type="text" value="${meal.calories}" name="calories" />
    </div>

    <div class="form-group">
        <c:if test="${meal.id > 0}">
            <input type="submit" value="Submit Edit Meal" class="btn btn-primary"/>
            <input type="hidden" name="id" value="${meal.id}">
        </c:if>
        <c:if test="${meal.id == 0}">
            <input type="submit" value="Submit Add Meal" class="btn btn-primary"/>
        </c:if>
    </div>
</form>
</div>

</body>
</html>
