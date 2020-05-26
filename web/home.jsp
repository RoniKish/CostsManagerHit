<%@ page import="costsManagerHit.model.Expense" %>
<%@ page import="costsManagerHit.model.User" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <%
        if (session.getAttribute("user") == null) {
            response.sendRedirect("http://localhost:8010/CostsManagerHit/pageNotFound");
            return;
        }
    %>

    <link rel="stylesheet" type="text/css" href="/css/common.css">
    <link rel="stylesheet" type="text/css" href="/css/expenses.css">
    <title>Main Page</title>
</head>

<body>
    <h2>Last Three Expenses:</h2>
    <table>
        <tr>
            <td>Expense ID</td>
            <td>Type</td>
            <td>Price</td>
            <td>Description</td>
            <td>Month</td>
        </tr>

        <%
            Expense[] expenses = (Expense[])(request.getAttribute("expenses"));

            for (Expense currentExpense : expenses)
            {
                int id = currentExpense.getId();
                String type = currentExpense.getType();
                double amount = currentExpense.getAmount();
                String description = currentExpense.getDescription();
                String month = currentExpense.getMonth();
        %>
        <tr>
            <td><%= String.valueOf(id) %></td>
            <td><%= type %></td>
            <td><%= String.valueOf(amount) %></td>
            <td><%= description %></td>
            <td><%= month %></td>
        </tr>
        <%
            }
        %>
    </table>
    <br> <br>

    <br> <br>
    <h2>Add an Expense</h2>
    <form method="post" action="http://localhost:8010/CostsManagerHit/expenses/addExpense">
        <label for="expenseType">Choose a type: </label>
        <select id="expenseType" name="expenseType">
            <option value="general">general</option>
            <option value="car">car</option>
            <option value="house">house</option>
            <option value="food">food</option>
        </select>
        <br> <br>
        <label for="expenseMonth">Choose a month: </label>
        <select id="expenseMonth" name="expenseMonth">
            <option value="January">January</option>
            <option value="February">February</option>
            <option value="March">March</option>
            <option value="April">April</option>
            <option value="May">May</option>
            <option value="June">June</option>
            <option value="July">July</option>
            <option value="August">August</option>
            <option value="September">September</option>
            <option value="October">October</option>
            <option value="November">November</option>
            <option value="December">December</option>
        </select>

        <br> <br>
        Description: <input type="text" name="expenseDescription" />
        <br> <br>
        Amount: <input type="number" step="0.01" name="expenseAmount" />
        <br> <br>
        <input type="submit" value="Save"/>
    </form>
</body>

<footer>
    <nav>
        <ul>
            <li>
                <a href="http://localhost:8010/CostsManagerHit/home">Add Expense</a>
            </li>
            <li>
                <a href="http://localhost:8010/CostsManagerHit/expenses">Show All Expenses</a>
            </li>
            <li>
                <a href="http://localhost:8010/CostsManagerHit/login/logOut">Log Out</a>
            </li>
        </ul>
    </nav>
</footer>
</html>
