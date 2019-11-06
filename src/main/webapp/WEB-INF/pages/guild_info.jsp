<%@include file="includes/header.jsp" %>


<div class="container">
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
        <tbody>
        <c:forEach items="${memberships}" var="membership" varStatus="loop">
            <tr style="background-color: black">
                <td><h2><a
                        href="${pageContext.request.contextPath}/profile?id=${membership.character.id}">${membership.character.name}</a>
                </h2></td>
                <td>
                    <c:if test="${currentCharMembership == true}">
                        <h2>${membership.rank}</h2>
                    </c:if>
                </td>


            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>
<script>
    $(document).ready(function () {
        $('#guildsTable').DataTable();
    });
</script>
<%@include file="includes/footer.jsp" %>