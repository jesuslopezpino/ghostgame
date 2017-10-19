<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ attribute name="id" required="true" type="java.lang.String"%>
<%@ attribute name="title" required="false" type="java.lang.String"%>
<%@ attribute name="body" required="false" type="java.lang.String"%>
<%@ attribute name="severity" required="false" type="java.lang.String"%>

<div id="panel_${id}" class="panel panel-${severity ne null ? severity:'default'}">
	<div class="panel-heading">
		<c:if test="${title ne null}">
			<h3 class="panel-title">${title}</h3>
		</c:if>
	</div>
	<div class="panel-body">${body}
		<jsp:doBody />
	</div>
	<c:if test="${footer ne null}">
		<div class="panel-footer">${footer}</div>
	</c:if>
</div>
<script type="text/javascript">
	if (window['PANEL'] == null) {
		window['PANEL'] = {};
	}
	PANEL['${id}'] = {
		setTitle : function(title) {
			$('#panel_${id} .panel-title').text(title);
		},
		setBody : function(body) {
			$('#panel_${id} .panel-body').text(body);
		},
		addBodyLine : function(line) {
			$('#panel_${id} .panel-body').append($('<div />').text(line));
		},
		setFooter : function(footer) {
			$('#panel_${id} .panel-footer').text(footer);
		}
	}
</script>
