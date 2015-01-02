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
	<a class="item" onclick="createPhoto();">
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
<div class="ui modal create-photo">
	<i class="close icon"></i>
	<div class="header">
		Upload A New Photo
	</div>
	<div class="content">
		<g:form name="photo-form">
			<div class="ui form fluid">
				<div class="ui warning message">
					<div class="header">Input Invalid</div>
					<p>Please enter a name</p>
				</div>
				<div class="ui error message">
					<div class="header">Playlist Already Exists</div>
					<p>That photo already exists, please choose a new photo name</p>
				</div>
				<div class="required field">
					<label>Name</label>
					<g:field type="text" name="photo-name" />
				</div>
				<div class="required field">
					<label>Photo</label>
					<g:field type="file" name="photo-file" />
				</div>
				<div class="field">
					<label>Description</label>
					<g:textArea name="photo-description" />
				</div>
				<h2 class="ui header">Add To Playlists</h2>
				<div class="four fields">
					<g:each in="${ menu.playlists }">
						<div class="field">
							<div class="ui toggle checkbox">
								<g:checkBox name="playlist-${ it.id }" />
								<label>${ it.name }</label>
							</div>
						</div>
					</g:each>
				</div>
			</div>
		</g:form>
	</div>
	<div class="actions">
		<div class="ui negative button close">
			Cancel
		</div>
		<div class="ui positive right labeled icon button" onclick="submitCreatePhoto();">
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
		$('.ui.checkbox').checkbox();
	});

	function createPlaylist() {
		$('.left.sidebar').sidebar('toggle');
		$('.ui.modal.create-playlist').modal({selector: {close: '.close'}}).modal('show');
	}

	function createPhoto() {
		$('.left.sidebar').sidebar('toggle');
		$('.ui.modal.create-photo').modal({selector: {close: '.close'}}).modal('show');
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
						$('.create-playlist .form').removeClass('warning');
						$('.create-playlist .form').addClass('error');
					}
				}
			});
		} else {
			$('.create-playlist .form').removeClass('error');
			$('.create-playlist .form').addClass('warning');
		}
	}

	function submitCreatePhoto() {
		var fd = new FormData($('#photo-form')[0]);
		if ($('#photo-name').val().length != 0) {
			$('.create-photo .form').addClass('loading');
			$.ajax({
				url: '/playlist/createPhoto',
				type: 'POST',
				processData: false,
				contentType: false,
				data: fd,
				success: function(data) {
					if (data.success) {
						console.log('Success')
						$('.ui.modal.create-photo').modal('hide');
						swal('Success', 'Your photo was successfully uploaded', 'success');
					} else {
						$('.create-photo .form').removeClass('warning');
						$('.create-photo .form').addClass('error');
					}
				},
				complete: function() {
					$('.create-photo .form').removeClass('loading');
				}
			});
		} else {
			$('.create-photo .form').removeClass('error');
			$('.create-photo .form').addClass('warning');
		}
	}
</script>