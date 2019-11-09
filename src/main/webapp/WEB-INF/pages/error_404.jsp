<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8"/>

    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>

    <title>Dungeons & Unicorns</title>

    <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport'/>
    <meta name="viewport" content="width=device-width"/>

    <base href="${pageContext.request.contextPath}/"/>

    <link rel="icon" type="image/png" href="./images/favicon.ico">

    <link href="./bootstrap4/css/bootstrap.css" rel="stylesheet"/>
    <link href="./bootstrap4/css/bootstrap-grid.css" rel="stylesheet"/>
    <link href="./bootstrap4/css/bootstrap-reboot.css" rel="stylesheet"/>
    <link href="./bootstrap4/css/bootstrap-theme.css" rel="stylesheet"/>
    <link href="./bootstrap4/css/font-awesome.css" rel="stylesheet"/>
    <link href="./bootstrap4/css/custom.css" rel="stylesheet"/>

    <link href="./select2/css/select2.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">
    <link rel="stylesheet" type="text/css" href="./dataTables/datatables.min.css"/>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <style>
        @import url('https://fonts.googleapis.com/css?family=Risque&display=swap');
    </style>

</head>

<body>
<div class="wrapper">

    <div class="container-fluid">

        <img class="mySlides" src="./images/banner.jpg" style="width:100%">

        <div class="container text-center" style="margin-top: 50px">
            <h1>Unlike unicorns, the page you are looking for does not exist. Sorry !</h1>
            <div class="container text-center"><a href="${pageContext.request.contextPath}/home" class="btn btn-primary" style="margin-bottom: 10px">Go back home</a></div>

            <img class="text-center" src="./images/error404.png">
        </div>

    </div>
</div>
</body>

<script src="./bootstrap4/js/bootstrap.js" type="text/javascript"></script>
<script src="./bootstrap4/js/bootstrap.bundle.js" type="text/javascript"></script>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="./select2/js/select2.full.js"></script>
<script type="text/javascript" src="./dataTables/datatables.min.js"></script>
<script>
    $('.nav .nav-link').click(function(){
        $('.nav .nav-link').removeClass('active');
        $(this).addClass('active');
    })
</script>


</html>