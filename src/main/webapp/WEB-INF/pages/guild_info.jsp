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
            <div class="d-inline">
                <button id="buttonFirstPage" class="btn btn-primary" onclick="getPagination(setPageNumber(1))">First
                    Page
                </button>
                <button id="buttonPreviousPage" class="btn btn-primary" onclick="getPagination(deincr())" style="display: none">
                    Previous page
                </button>
                <h5 class="d-inline">Page number: <i id="pageNumberDisplayed">1/${numberOfPage}</i></h5>
                <button id="buttonNextPage" class="btn btn-primary" onclick="getPagination(inc())">Next
                    page
                </button>
                <button id="buttonLastPage" class="btn btn-primary"
                        onclick="getPagination(setPageNumber(${numberOfPage}))">Last page
                </button>
            </div>
        </div>
    </div>
</div>
<script>
    let pageNumber = 1;

    function hideButton(id) {
        console.log('#'+id);
        $('#'+id).hide();
    }
    function showButton(id) {
        console.log('#'+id);
        $('#'+id).show();
    }
    function setPageNumber(value) {
        pageNumber = value;
        return pageNumber;
    }

    function inc() {
        pageNumber = pageNumber + 1;
        return pageNumber;
    }

    function deincr() {
        pageNumber = pageNumber - 1;
        return pageNumber;
    }

    function getPagination(page) {
        if(page === 1){
            console.log("Page 1");
           hideButton('buttonPreviousPage');
           showButton('buttonNextPage');
        }else if (page === ${numberOfPage}){
            console.log("Derniere page");
            hideButton('buttonNextPage');
            showButton('buttonPreviousPage');
        }else {
            showButton('buttonNextPage');
            showButton('buttonPreviousPage');
        }
        $('#pageNumberDisplayed').html(page+'/'+${numberOfPage});

        $.post("${pageContext.request.contextPath}/guilds/info?id=${guild.id}", {
            page: page,
            guildId:${guild.id}
        }, function (response) {
            $('#tableBody').html(response)
        });
    }

</script>
<%@include file="includes/footer.jsp" %>