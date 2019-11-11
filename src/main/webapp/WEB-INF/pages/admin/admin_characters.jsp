<%@include file="../includes/header.jsp" %>
<%
    java.util.Enumeration params = request.getParameterNames();
    StringBuilder parameters = new StringBuilder();
    while (params.hasMoreElements()) {
        String paramName = (String) params.nextElement();
        String paramValue = request.getParameter(paramName);
        if (!paramName.equals("page")) {
            parameters.append(paramName).append("=").append(paramValue).append("&");
        }
    }
%>
<div class="container-fluid">
    <div class="container text-center" style="margin-top: 10px; margin-bottom: 10px">
        <a href="${pageContext.request.contextPath}/admin/characters/add" class="btn btn-primary">Add new character</a>
    </div>

    <div class="container">

        <form class="form" method="get" style="margin: auto">
            <input class="form-control text-center" type="text" placeholder="Search" aria-label="Search"
                   name="searchBar">
        </form>
        <div class="text-center">
            <a href="${pageContext.request.contextPath}/admin/characters">All</a>
            <c:set var="alphabet" value="a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z"/>
            <c:forTokens var="letter" items="${alphabet}" delims=",">
                /<a href="${pageContext.request.contextPath}/admin/characters?letter=${letter}">${letter}</a>
            </c:forTokens>
        </div>

        <div class="text-center">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/characters?<%=parameters%>page=1">First
                page</a>
            <c:if test="${pageContext.request.getParameter(\"page\") > 1}">
                <a class="btn btn-primary"
                   href="${pageContext.request.contextPath}/admin/characters?<%=parameters%>page=${pageContext.request.getParameter("page")-1}">Previous
                    page</a>
            </c:if>

            <c:if test="${pageContext.request.getParameter(\"page\") < numberOfPage || pageContext.request.getParameter(\"page\") == null}">
                <c:choose>
                    <c:when test="${pageContext.request.getParameter(\"page\") == null}">
                        <a class="btn btn-primary"
                           href="${pageContext.request.contextPath}/admin/characters?<%=parameters%>page=2">Next
                            page</a>
                    </c:when>
                    <c:otherwise>
                        <a class="btn btn-primary"
                           href="${pageContext.request.contextPath}/admin/characters?<%=parameters%>page=${pageContext.request.getParameter("page")+1}">Next
                            page</a>
                    </c:otherwise>
                </c:choose>
            </c:if>

            <a class="btn btn-primary"
               href="${pageContext.request.contextPath}/admin/characters?<%=parameters%>page=${numberOfPage}">Last
                page</a>
        </div>

    </div>

    <table class="table" style="margin-top: 10px">
        <thead>
        <tr class="bg-danger">
            <th></th>
            <th><h3>Character name</h3></th>
            <th><h3>Is admin</h3></th>
            <th><h3>Actions</h3></th>
        </tr>
        </thead>
        <c:forEach items="${characters}" var="character" varStatus="loop">
            <tr>
                <td>${loop.index+1}</td>
                <td><a href="${pageContext.request.contextPath}/profile?id=${character.id}"><h5
                        style="color: white">${character.name}</h5></a></td>
                <td>${(character.isadmin ?  '<i class="fas fa-check"></i>' : "")}</td>
                <td>
                    <c:if test="${character.id != sessionScope.character.id}">
                        <a href="${pageContext.request.contextPath}/admin/characters/delete?id=${character.id}"><i
                                class="fas fa-trash-alt"></i></a>
                    </c:if>

                    <a href="${pageContext.request.contextPath}/admin/characters/update?id=${character.id}"><i
                            class="fas fa-pen"></i></a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <h5 class="text-center">Page <i id="pageNumberDisplayed">${(pageContext.request.getParameter("page") == null ? '1': pageContext.request.getParameter("page")) } / ${numberOfPage}</i></h5>
</div>
<c:if test="${sessionScope.error != null}">
    <script type="text/javascript">
        $(function() {
            toastr.${(sessionScope.error == "false" ? 'success': 'error')}("${(sessionScope.error == "false" ? 'User deleted': 'Unable to delete this user')}", "Delete status", {positionClass: "toast-bottom-full-width", timeout: 2});
        });

    </script>
    <c:remove var="error"/>
</c:if>


<%@include file="../includes/footer.jsp" %>
