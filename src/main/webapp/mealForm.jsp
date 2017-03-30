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
        <input id="formDateTime" type="datetime-local" value="${meal.dateTime}" name="dateTime"  />
    </div>

    <div class="form-group">
        <label for="formDescription">Description: </label>
        <br />
        <input id="formDescription" type="text" value="${meal.description}" name="description" />
    </div>

    <div class="form-group">
        <label for="formCalories">Calories: </label>
        <br />
        <input id="formCalories" type="number" value="${meal.calories}" name="calories" />
    </div>

    <div class="form-group">
        <input type="hidden" name="id" value="${meal.id}">
        <input type="submit" value="Submit ${meal.id ? 'Edit' : 'Add'} Meal" class="btn btn-primary"/>
    </div>
</form>
</div>

</body>
</html>
