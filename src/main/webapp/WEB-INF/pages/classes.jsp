<%@include file="includes/header.jsp" %>


<div class="container-fluid">
    <table class="table">
        <c:forEach items="${classes}" var="class" varStatus="loop">
            <c:if test="${loop.index % 2 == 0}">
                <tr>
            </c:if>
            <td class="text-center">
            <a href="${pageContext.request.contextPath}/classInfo?id=${class.id}">
                <button class="btn btn-outline-dark" style="width: 50%">
                    <div class="row">
                        <img src="./images/icons/${fn:toLowerCase(class.name)}.jpeg" style="background-color: white; border-radius: 10px; margin-left: 20px">
                        <h2 style="color: white; margin-left: 60px;margin-top: 35px "> ${class.name}</h2>
                    </div>

                </button>
            </a>

            </td>
            <c:if test="${loop.index % 2 == 1}">
                </tr>
            </c:if>


        </c:forEach>
    </table>

</div>
<%--<script>--%>
<%--    function redirect(id) {--%>
<%--        console.log(id)--%>
<%--        window.location.href = "${pageContext.request.contextPath}/classInfo?id="+id--%>
<%--    }--%>
<%--</script>--%>
<%@include file="includes/footer.jsp" %>