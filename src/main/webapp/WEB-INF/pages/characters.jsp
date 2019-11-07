<%@include file="includes/header.jsp" %>

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

    <div class="container">

        <form class="form-inline" method="get">
            <input class="form-control" type="text" placeholder="Search" aria-label="Search" name="searchBar">
        </form>

        <a href="${pageContext.request.contextPath}/characters">All</a>
        <c:set var="alphabet" value="a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z"/>
        <c:forTokens var="letter" items="${alphabet}" delims=",">
            /<a href="${pageContext.request.contextPath}/characters?letter=${letter}">${letter}</a>
        </c:forTokens>


    </div>
    <table class="table">
        <c:forEach items="${characters}" var="character" varStatus="loop">
            <c:if test="${loop.index % 5 == 0}">
                <tr>
            </c:if>
            <td>
                <a href="${pageContext.request.contextPath}/profile?id=${character.id}">
                    <button class="btn btn-outline-dark">
                        <h2 style="color: white">${character.name}</h2>
                        <h6 style="color: white">${character.myClass.name} level ${character.level}</h6>
                    </button>
                </a>

            </td>
            <c:if test="${loop.index % 5 == 4}">
                </tr>
            </c:if>
        </c:forEach>
    </table>
    <div class="container">
        <div class="text-center">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/characters?<%=parameters%>page=1">First
                page</a>
            <c:if test="${pageContext.request.getParameter(\"page\") > 1}">
                <a class="btn btn-primary"
                   href="${pageContext.request.contextPath}/characters?<%=parameters%>page=${pageContext.request.getParameter("page")-1}">Previous
                    page</a>
            </c:if>

            <c:if test="${pageContext.request.getParameter(\"page\") < numberOfPage || pageContext.request.getParameter(\"page\") == null}">
                <c:choose>
                    <c:when test="${pageContext.request.getParameter(\"page\") == null}">
                        <a class="btn btn-primary"
                           href="${pageContext.request.contextPath}/characters?<%=parameters%>page=2">Next
                            page</a>
                    </c:when>
                    <c:otherwise>
                        <a class="btn btn-primary"
                           href="${pageContext.request.contextPath}/characters?<%=parameters%>page=${pageContext.request.getParameter("page")+1}">Next
                            page</a>
                    </c:otherwise>
                </c:choose>
            </c:if>

            <a class="btn btn-primary"
               href="${pageContext.request.contextPath}/characters?<%=parameters%>page=${numberOfPage}">Last page</a>
        </div>
    </div>

</div>
<%@include file="includes/footer.jsp" %>