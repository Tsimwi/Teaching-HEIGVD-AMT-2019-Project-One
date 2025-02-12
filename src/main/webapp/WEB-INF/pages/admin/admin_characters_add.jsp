<%@include file="../includes/header.jsp" %>

<div class="container-fluid">
    <div class="container">
        <form method="post">
            <div class="form-group">
                <label for="nameField">Name</label>
                <input type="text" class="form-control" id="nameField" name="name" value="${name}">
            </div>
            <div class="form-group">
                <label for="passwordField">Password</label>
                <input type="password" class="form-control" id="passwordField" name="password">
            </div>
            <div class="form-group">
                <label for="passwordFieldVerify">Repeat password</label>
                <input type="password" class="form-control" id="passwordFieldVerify" name="passwordVerify">
            </div>
            <div class="form-group">
                <label for="isAdminCheckbox">Is admin</label>
                <input type="checkbox" id="isAdminCheckbox" name="isAdminCheckbox">
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
        <c:if test="${errors != null}">
            Errors:
            <ul>
                <c:forEach items="${errors}" var="error">
                    <li>${error}</li>
                </c:forEach>
            </ul>
        </c:if>
    </div>
</div>

<%@include file="../includes/footer.jsp" %>