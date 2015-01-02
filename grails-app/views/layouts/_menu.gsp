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
	<a class="item" onclick="createPlaylist();">
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
<div class="ui modal create-playlist">
	<i class="close icon"></i>
	<div class="header">
		Create A New Playlist
	</div>
	<div class="content">
		<g:form>
			<div class="ui form fluid">
				<div class="ui warning message">
					<div class="header">Input Invalid</div>
					<p>Please enter a name</p>
				</div>
				<div class="ui error message">
					<div class="header">Playlist Already Exists</div>
					<p>That playlist already exists, please choose a new playlist name</p>
				</div>
				<div class="required field">
					<label>Name</label>
					<g:field type="text" name="playlist-name" />
				</div>
				<div class="field">
					<label>Description</label>
					<g:textArea name="playlist-description" />
				</div>
			</div>
		</g:form>
	</div>
	<div class="actions">
		<div class="ui negative button close">
			Cancel
		</div>
		<div class="ui positive right labeled icon button" onclick="submitCreatePlaylist();">
			Create
			<i class="checkmark icon"></i>
		</div>
	</div>
</div>
<script>
	$(function() {
		$('.launch').click(function() {
			$('.left.sidebar').sidebar('toggle');
		});
	});

	function createPlaylist() {
		$('.left.sidebar').sidebar('toggle');
		$('.ui.modal.create-playlist').modal({selector: {close: '.close'}}).modal('show');
	}

	function submitCreatePlaylist() {
		if ($('#playlist-name').val().length != 0) {
			$.ajax({
				url: '/playlist/createPlaylist',
				data: {
					name: $('#playlist-name').val(),
					description: $('#playlist-description').val()
				},
				success: function(data) {
					if (data.success) {
						window.location.href = '/playlist/view/' + data.id
					} else {
						$('#createPlaylist .form').removeClass('warning');
						$('#createPlaylist .form').addClass('error');
					}
				}
			});
		} else {
			$('#createPlaylist .form').removeClass('error');
			$('#createPlaylist .form').addClass('warning');
		}
	}
</script>