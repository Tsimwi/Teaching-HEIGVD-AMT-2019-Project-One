<%@include file="../includes/header.jsp" %>

<div class="container-fluid">
    <div class="container-fluid">
        <form method="post">
            <a href="${pageContext.request.contextPath}/admin/guilds">
                <i class="btn btn-primary fas fa-arrow-left">Back</i>
            </a>
            <input type="hidden" name="id" value="${requestScope.guild.id}">
            <div class="form-group">
                <label for="nameField">Name</label>
                <input type="text" class="form-control" id="nameField" name="name"
                       value="${requestScope.guild.name}">
            </div>
            <div class="form-group">
                <label for="descriptionField">Description</label>
                <textarea rows="20" cols="50" class="form-control" id="descriptionField"
                          name="description">${requestScope.guild.description}
                </textarea>
            </div>
            <button type="submit" class="btn btn-primary" name="updateGuild">Update</button>
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
