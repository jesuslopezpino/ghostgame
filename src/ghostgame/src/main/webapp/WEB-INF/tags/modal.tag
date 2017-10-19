<%@ attribute name="id" required="true" type="java.lang.String"%>
<%@ attribute name="body" required="false" type="java.lang.String"%>
<%@ attribute name="onClose" required="false" type="java.lang.String"%>
<%@ attribute name="title" required="false" type="java.lang.String"%>
<%@ attribute name="modalClass" required="false" type="java.lang.String"%>

<script type="text/javascript">
	if (window['MODAL'] == null) {
		window['MODAL'] = {};
	}
	MODAL['${id}'] = {
		debug : false,
		debugline : function(line) {
			if (this.debug == true) {
				console.debug(line);
			}
		},
		show : function() {
			this.debugline('showing modal...');
			$('#modal_${id}').modal('show');
		},
		hide : function() {
			this.debugline('hide modal...');
			$('#modal_${id}').modal('hide');
		},
		setTitle : function(title){
			this.debugline('title: '+title);
			$('#modal_${id} .modal-title').text(title);
		},
		setBody : function(body){
			this.debugline('body: '+body);
			$('#modal_${id} .modal-body').text(body);
		},
		addLine : function(line){
			$('#modal_${id} .modal-body').append($('<div />').text(line));
		},
		addTitleClass: function(className){
			$('#modal_${id} .modal-title').addClass(className);
		},
		removeTitleClass: function(className){
			$('#modal_${id} .modal-title').removeClass(className);
		}
	};
	$(window).load(function() {
		$('#modal_${id}').on('hidden.bs.modal', function(event) {
			${onClose};
		});
	})
</script>
<div id="modal_${id}" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="${id}_Label">
	<div class="modal-dialog ${modalClass}">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h1 class="modal-title btn ghost-modal-title" id="${id}_Label">${title ne null ? title:''}</h1>
			</div>
			<div class="modal-body">${body ne null ? body:''}</div>
		</div>
	</div>
</div>