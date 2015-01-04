<div class="ui ${ rowSize } cards">
	<g:each in="${ photos }">
		<g:render template="/layouts/photo" model="[photo: it]" />
	</g:each>
</div>
<script>
	$(function() {
		$('.ui.card .image').dimmer({
			on : 'hover'
		});
	})
</script>