<div class="ui black big launch right attached fixed button">
	<i class="content icon"></i>
	<span class="text">Menu</span>
</div>
<div class="ui left inverted vertical sidebar menu">
	<div class="header item">
		<i class="list icon"></i>
		My Playlists
	</div>
	<g:each in="${ menu.playlists }">
		<g:link controller="playlist" action="view" id="${ it.id }" class="item">
			${ it.name }
		</g:link>
	</g:each>
	<a class="item">
		<i class="plus icon"></i>
		Create A New Playlist
	</a>
	<div class="header item">
		<i class="photo icon"></i>
		My Photos
	</div>
	<a class="item">
		Browse
	</a>
	<a class="item">
		<i class="plus icon"></i>
		Add A New Photo
	</a>
</div>
<script>
	$(function() {
		$('.launch').click(function() {
			$('.left.sidebar').sidebar('toggle');
		});
	});
</script>