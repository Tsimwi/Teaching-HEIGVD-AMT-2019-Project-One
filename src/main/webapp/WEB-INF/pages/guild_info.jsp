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

<div class="container">
    Number of page: ${numberOfPage}
    <table class="table">
        <thead>
        <tr>
            <th>
                <a href="${pageContext.request.contextPath}/guilds">
                    <i class="btn btn-primary fas fa-arrow-left">Back</i>
                </a>
            </th>
        </tr>
        <tr>
            <th>
                <h2>${guild.name}:</h2>
            </th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>
                ${guild.description}
            </td>
            <td>
                <img src="./images/${fn:replace(fn:toLowerCase(guild.name), ' ','')}.jpg"
                     onerror="this.onerror=null; this.src='./images/default.jpg'"
                     style="background-color: white; border-radius: 10px">
            </td>
        </tr>
        <tr>
            <td>
                <c:if test="${currentCharMembership != true}">
                    <a class="btn btn-primary"
                       href="${pageContext.request.contextPath}/guilds/join?id=${guild.id}">Join</a>
                </c:if>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="container">
        <div class="text-center">
            <h1>Guild memberships :</h1>
        </div>
        <table class="table" id="guildsTable" style="background-color: black; color: white">
            <thead>
            <tr>
                <th>Character name</th>
                <th>Rank</th>
            </tr>
            </thead>
            <tbody id="tableBody">
            <c:forEach items="${memberships}" var="membership" varStatus="loop">
                <tr style="background-color: black">
                    <td><h5><a
                            href="${pageContext.request.contextPath}/profile?id=${membership.character.id}">${membership.character.name}</a>
                    </h5></td>
                    <td>
                        <c:if test="${currentCharMembership == true}">
                            <h5>${membership.rank}</h5>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="container">
        <div class="text-center">

            <button class="btn btn-primary" onclick="getPagination(1)">First Page</button>
            <button class="btn btn-primary" onclick="getPagination(${page-1})">
                Previous page
            </button>
            <button class="btn btn-primary" onclick="getPagination(${page+1})">Next
                page
            </button>
            <button class="btn btn-primary" onclick="getPagination(${numberOfPage})">Last page</button>

            <%--            <a class="btn btn-primary" href="${pageContext.request.contextPath}/guilds/info?<%=parameters%>page=1">First--%>
            <%--                page</a>--%>
            <%--            --%>

            <%--            <c:if test="${pageContext.request.getParameter(\"page\") > 1}">--%>
            <%--                <a class="btn btn-primary"--%>
            <%--                   href="${pageContext.request.contextPath}/guilds/info?<%=parameters%>page=${pageContext.request.getParameter("page")-1}">Previous--%>
            <%--                    page</a>--%>
            <%--            </c:if>--%>

            <%--            <c:if test="${pageContext.request.getParameter(\"page\") < numberOfPage || pageContext.request.getParameter(\"page\") == null}">--%>
            <%--                <c:choose>--%>
            <%--                    <c:when test="${pageContext.request.getParameter(\"page\") == null}">--%>
            <%--                        <a class="btn btn-primary"--%>
            <%--                           href="${pageContext.request.contextPath}/guilds/info?<%=parameters%>page=2">Next--%>
            <%--                            page</a>--%>
            <%--                    </c:when>--%>
            <%--                    <c:otherwise>--%>
            <%--                        <a class="btn btn-primary"--%>
            <%--                           href="${pageContext.request.contextPath}/guilds/info?<%=parameters%>page=">Next--%>
            <%--                            page</a>--%>
            <%--                    </c:otherwise>--%>
            <%--                </c:choose>--%>
            <%--            </c:if>--%>

            <%--            <a class="btn btn-primary"--%>
            <%--               href="${pageContext.request.contextPath}/guilds/info?<%=parameters%>page=${numberOfPage}">Last page</a>--%>
        </div>
    </div>
</div>
<script>

    function getPagination(page) {
        $.post("${pageContext.request.contextPath}/guilds/info?id=${guild.id}", {page:page, guildId:${guild.id}}, function (response) {
            $('#tableBody').html(response)
        });
        console.log(page)
    }

</script>
<%@include file="includes/footer.jsp" %>