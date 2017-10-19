
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ attribute name="id" required="true" type="java.lang.String"%>
<%@ attribute name="url" required="true" type="java.lang.String"%>
<%@ attribute name="beforeSubmit" required="false"
	type="java.lang.String"%>
<%@ attribute name="ajaxSuccess" required="false"
	type="java.lang.String"%>
<%@ attribute name="dataCollector" required="false"
	type="java.lang.String"%>

<spring:url var="url" value="${url}"></spring:url>

<c:set var="beforeSubmit" value="${beforeSubmit eq null ? true:beforeSubmit}" />

<script type="text/javascript">
if(window['FORM']==null){
	window['FORM']={};
}

FORM['${id}']={
		dummy:function(){
			
		},
		submit:function(){
			if(${beforeSubmit}){
				$.ajax({
					url : '${url}',
					type : 'GET',
					data : {
						userPlay : ${dataCollector ne null ? dataCollector:null}
					},
					success : function(data) {
						return ${ajaxSuccess ne null ? ajaxSuccess : 'dummy'}(data);
					},
					error : function(data){
						alert('AJAX ERROR: '+data);
						console.debug('AJAX ERROR: ');
						console.debug(data);
					}
				});
			}
			return false;
		}
		}
</script>



<form role="form" action="${url}" method="get"
	onsubmit="return FORM.${id}.submit();">
	<div class="form-group">
		<jsp:doBody />
	</div>
</form>