<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ attribute name="id" required="true" type="java.lang.String"%>
<%@ attribute name="label" required="false" type="java.lang.String"%>
<%@ attribute name="required" required="false" type="java.lang.Boolean"%>
<%@ attribute name="placeholder" required="false"
	type="java.lang.String"%>
<%@ attribute name="value" required="false" type="java.lang.String"%>
<%@ attribute name="type" required="false" type="java.lang.String"%>
<%@ attribute name="maxlength" required="false" type="java.lang.Integer"%>

<div class="input-group">
	<span class="input-group-addon" id="basic-addon-${id}">${label}</span>
	<input id="${id}" maxlength="${maxlength}" 
		data-trigger="hover" 
		data-toggle="popover" 
		data-placement="bottom"
		data-content="Enter to submit..."
		type="${type eq null ? 'text':type}" class="form-control"
		placeholder="${placeholder}" aria-describedby="basic-addon-${id}" />
</div>
<script type="text/javascript">
	if (window['INPUT'] == null) {
		window['INPUT'] = {};
	}
	INPUT['${id}'] = {
		setValue : function(value) {
			$('#${id}').val(value);
		},
		setLabel : function(label) {
			$('#basic-addon-${id}').text(label);
		},
		getValue : function() {
			return $('#${id}').val();
		},
		getLabel : function() {
			return $('#basic-addon-${id}').text();
		},
		focus : function() {
			$('#${id}').focus();
		},
		disable : function() {
			$('#${id}').attr('disabled','disabled');
		},
		enable : function() {
			$('#${id}').removeAttr('disabled');
		}
		
	};
	$(window).load(function(){
		$('#${id}').popover();
	})
</script>
