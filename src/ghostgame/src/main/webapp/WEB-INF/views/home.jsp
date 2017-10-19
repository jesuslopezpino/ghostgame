<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ taglib tagdir="/WEB-INF/tags/" prefix="util"%>
<%@ taglib tagdir="/WEB-INF/tags/templates/" prefix="template"%>

<%@ page session="false"%>
<template:default onLoad="initGame()">
	<jsp:attribute name="header">
    </jsp:attribute>
	<jsp:attribute name="content">
	    <script type="text/javascript">
	    
			function pintaResultado(resultado) {
				var mensaje = null;
				if (resultado.endPlay != null) {
					var inputValue = INPUT.letterInput.getValue()
					mensaje = resultado.endPlay + '\n' + resultado.endMessage;
					if (resultado.endPlay == '${notWordException}') {
						// you can also activate/deactivate instantly debug of the element from console
						MODAL.myModal.debug = true;
						MODAL.myModal.setTitle('You loose!!');
						MODAL.myModal.addTitleClass('btn-danger');
						MODAL.myModal.setBody('"' + playedValue() + '" is not a word');
						MODAL.myModal.addLine(' ');
						MODAL.myModal.addLine('Still playables previous words...');
						MODAL.myModal.addLine(' ');
						MODAL.myModal.addLine(resultado.endMessage);
						MODAL.myModal.show();
						// access to the javascript object of the component as you like...
						MODAL['myModal'].debug = false;
					} else if (resultado.endPlay == '${wordException}') {
						MODAL.myModal.setTitle('You loose!!');
						MODAL.myModal.addTitleClass('btn-danger');
						MODAL.myModal.setBody('"' + playedValue() + '" is a word');
						MODAL.myModal.show();
					} else if (resultado.endPlay == '${winException}') {
						MODAL.myModal.setTitle('YOU WIN');
						MODAL.myModal.addTitleClass('btn-success');
						MODAL.myModal.setBody('');
						MODAL.myModal.addLine('the computer has no available moves');
						MODAL.myModal.addLine('posible moves are words...');
						MODAL.myModal.addLine(resultado.endMessage);
						MODAL.myModal.show();
					}
				} else {
					PANEL.computerPanel.addBodyLine('COMPUTER RESPONDS: ' + resultado.ghostPlay)
					INPUT.letterInput.setLabel(resultado.ghostPlay)
					INPUT.letterInput.setValue('');
					INPUT.letterInput.focus();
				}
			}
			
			function playedValue() {
				return (INPUT.letterInput.getLabel() + INPUT.letterInput.getValue()).toLowerCase();
			}
			
			function initGame() {
				INPUT.letterInput.setLabel('');
				INPUT.letterInput.setValue('');
				INPUT.letterInput.focus();
				PANEL.playerPanel.setBody('');
				PANEL.computerPanel.setBody('');
				MODAL.myModal.removeTitleClass('btn-success');
				MODAL.myModal.removeTitleClass('btn-danger');
			}
			
			function checkPlay() {
				var currentValue = playedValue();
				if (currentValue == '' || currentValue != INPUT.letterInput.getLabel()) {
					writePlayerPlay();
					return true;
				} else {
					return false;
				}
			}
			function writePlayerPlay() {
				PANEL.playerPanel.addBodyLine('PLAYER PLAYS: ' + playedValue());
			}
		</script>

		<div>
			<h1>WELCOME TO GHOST GAME</h1>
			<util:panel id="description" title="Description" severity="info">
				<p>In the game of Ghost, two players take turns building up an English word 
				from left to right. Each player adds one letter per turn. The goal is to not 
				complete the spelling of a word: if you add a letter that completes a word (of ${minimunWordSize}+ letters), 
				or if you add a letter that produces a string that cannot be extended into a word, you lose. </p>
			</util:panel>
			<util:form url="/play" id="playForm" ajaxSuccess="pintaResultado"
				dataCollector="playedValue()" beforeSubmit="checkPlay()">
				<div class="row">
					<div class="col-md-3">
						<util:panel id="info" title="Current Word" severity="warning">
							<util:input id="letterInput" placeholder="Insert a letter..."
								maxlength="1" required="true">
							</util:input>
						</util:panel>
						
					</div>
					<div class="col-md-9">
						<util:panel id="gameHistory" title="Game History"
							severity="warning">
							<div class="row">
								<div class="col-md-6">
									<util:panel id="playerPanel" title="Player" severity="info" />
								</div>
								<div class="col-md-6">
									<util:panel id="computerPanel" title="Computer" severity="info" />
								</div>
							</div>
						</util:panel>
					</div>
				</div>
			</util:form>
		</div>
	
		<util:modal id="myModal" onClose="initGame()" />

  </jsp:attribute>
</template:default>