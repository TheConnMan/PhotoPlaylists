<div class="ui ${ rowSize } cards">
	<g:each in="${ photos }">
		<g:render template="/layouts/photo" model="[photo: it]" />
	</g:each>
</div>