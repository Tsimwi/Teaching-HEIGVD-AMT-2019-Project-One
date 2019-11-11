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
            <h1><u>Guild members :</u></h1>
        </div>
        <c:if test="${currentCharMembership != true}">
            <h4>You need to join the guild to see rank of other characters</h4>
        </c:if>

        <c:if test="${numberOfPage > 1}">
            <div class="container">
                <div class="text-center">
                    <div>
                        <button id="buttonFirstPage" class="btn btn-primary" onclick="getPagination(setPageNumber(1))">
                            First
                            Page
                        </button>
                        <button id="buttonPreviousPage" class="btn btn-primary" onclick="getPagination(deincr())"
                                style="display: none">
                            Previous page
                        </button>
                        <button id="buttonNextPage" class="btn btn-primary" onclick="getPagination(inc())">Next
                            page
                        </button>
                        <button id="buttonLastPage" class="btn btn-primary"
                                onclick="getPagination(setPageNumber(${numberOfPage}))">Last page
                        </button>
                        <br/>
                    </div>
                </div>
            </div>
        </c:if>

        <table class="table" id="guildsTable" style="background-color: black; color: white">
            <thead>
            <tr>
                <th><h2>Character name</h2></th>
                <th><h2>Rank</h2></th>
            </tr>
            </thead>
            <tbody id="tableBody">
            <c:forEach items="${memberships}" var="membership" varStatus="loop">
                <tr style="background-color: black">
                    <td><h5><a
                            href="${pageContext.request.contextPath}/profile?id=${membership.character.id}">${membership.character.name}</a>
                    </h5></td>
                    <td class="rankColumn">
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
            <div>
                <h5 class="d-inline">Page number: <i id="pageNumberDisplayed">1 / ${numberOfPage}</i></h5>
            </div>
        </div>
    </div>
</div>
<script>
    let pageNumber = 1;

    if (${numberOfPage==1}) {
        hideButton('#buttonNextPage');
    }


    function hideButton(id) {
        $(id).hide();
    }

    function showButton(id) {
        $(id).show();
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
        if (page === 1) {
            hideButton('#buttonPreviousPage');
            showButton('#buttonNextPage');
        } else if (page === ${numberOfPage}) {
            hideButton('#buttonNextPage');
            showButton('#buttonPreviousPage');
        } else {
            showButton('#buttonNextPage');
            showButton('#buttonPreviousPage');
        }

        $('#pageNumberDisplayed').html(page + '/' +${numberOfPage});

        $.post("${pageContext.request.contextPath}/guilds/info?id=${guild.id}", {
            page: page,
            guildId:${guild.id}
        }, function (response) {
            $('#tableBody').html(response);

        }).done(function () {
            <c:if test="${currentCharMembership == false}">
            hideButton('.rankColumn');
            </c:if>
        });
    }

</script>
<%@include file="includes/footer.jsp" %>