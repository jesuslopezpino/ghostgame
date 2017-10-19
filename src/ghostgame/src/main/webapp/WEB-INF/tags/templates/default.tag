<%@ attribute name="header" fragment="true"%>
<%@ attribute name="content" fragment="true"%>

<%@ attribute name="onLoad" required="false" type="java.lang.String"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="util"%>

<html>
<head>
<title>THE GHOST GAME</title>
<link href="resources-css/bootstrap.css" rel="stylesheet" />
<link href="resources-css/ghost.css" rel="stylesheet" />
<script type="text/javascript" src="resources-js/jquery-1.12.4.js"></script>
<jsp:invoke fragment="header" />
</head>
<body>
	<div class="container-fluid">
		<jsp:invoke fragment="content" />
		<c:if test="${onLoad ne null}">
			<script type="text/javascript">
				$(window).load(function() {
					${onLoad};
				})
			</script>
		</c:if>

		<footer class="footer">
			<div class="container alert alert-success" role="alert">
			<div class="row">
				<p>Developed By Jesús
					María López Pino for Piksel.com Company Job Application
				</p>
			</div>
			<div class="row">
				<a href="mailto:jesus_lopez_pino@hotmail.com">
					jesus_lopez_pino@hotmail.com </a>
			</div>
			</div>
		</footer>
	</div>
	<script src="resources-js/bootstrap.js"></script>
</body>
</html>



