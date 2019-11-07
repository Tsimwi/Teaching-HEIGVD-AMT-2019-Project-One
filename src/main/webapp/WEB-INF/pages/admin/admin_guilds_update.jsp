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

    <div class="container">
        <div class="text-center">
            <h1>Guild memberships :</h1>
        </div>
        <table class="table" id="guildsTable" style="background-color: black; color: white">
            <thead>
            <tr>
                <th></th>
                <th>Character name</th>
                <th>Rank</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody id="tableBody">
            <c:forEach items="${memberships}" var="membership" varStatus="loop">
                <tr style="background-color: black">
                    <td>${loop.index+1}</td>
                    <td><h5><a
                            href="${pageContext.request.contextPath}/profile?id=${membership.character.id}">${membership.character.name}</a>
                    </h5></td>
                    <td>
                            <h5>${membership.rank}</h5>

                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/guilds/memberships/delete?id=${membership.id}&guildId=${guild.id}"><i class="fas fa-trash-alt"></i></a>
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

        $.post("${pageContext.request.contextPath}/admin/guilds/update?id=${guild.id}", {
            page: page,
            guildId:${guild.id}
        }, function (response) {
            console.log(response);
            $('#tableBody').html(response)
        });
    }

</script>

<%@include file="../includes/footer.jsp" %>
