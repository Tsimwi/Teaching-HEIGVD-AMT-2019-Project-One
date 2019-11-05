<%@include file="../includes/header.jsp" %>

<div class="container-fluid">
    <div class="container">
        <form method="post">
            <div class="form-group">
                <label for="nameField">Name</label>
                <input type="text" class="form-control" id="nameField" name="name">
            </div>
            <div class="form-group">
                <label for="descriptionField">Description</label>
                <textarea rows="20" cols="50" class="form-control" id="descriptionField"
                          name="description"></textarea>
            </div>
            <button type="submit" class="btn btn-primary">Create</button>
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