<div class="ui inverted left vertical sidebar menu uncover visible">
	<div class="header item">
		<i class="user icon"></i>
		<sec:username />
	</div>
	<g:link controller="logout" class="item">
		Logout
	</g:link>
	<div class="header item">
		<i class="photo icon"></i>
		My Photos
	</div>
	<g:link controller="photo" action="browse" class="item">
		Browse
	</g:link>
	<a class="item" onclick="createPhoto();">
		<i class="plus teal icon"></i>
		Add a New Photo
	</a>
	<div class="header item">
		<i class="list icon"></i>
		My Playlists
	</div>
	<pp:eachPlaylist>
		<g:link controller="playlist" action="view" id="${ it.id }" class="item">
			${ it.name }
		</g:link>
	</pp:eachPlaylist>
	<a class="item" onclick="createPlaylist();">
		<i class="plus teal icon"></i>
		Create a New Playlist
	</a>
</div>
<div class="ui modal create-playlist">
	<i class="close icon"></i>
	<div class="header">
		Create a New Playlist
	</div>
	<div class="content">
		<div class="ui form fluid">
			<div class="ui warning message">
				<div class="header">Input Invalid</div>
				<p>Please enter a name</p>
			</div>
			<div class="ui error message">
				<div class="header">Playlist Already Exists</div>
				<p id="playlist-error"></p>
			</div>
			<div class="required field">
				<label>Name</label>
				<g:field type="text" name="playlist-name" class="text" />
			</div>
			<div class="field">
				<label>Description</label>
				<g:textArea name="playlist-description" class="text" />
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
					<div class="header">Error Creating Photo</div>
					<p id="photo-error"></p>
				</div>
				<div class="field">
					<div class="ui toggle checkbox">
						<g:checkBox name="multiple-photos-on" class="checkbox" />
						<label>Multiple Photos</label>
					</div>
				</div>
				<div id="multiple-photo" class="field" style="display: none;">
					<label>Photos</label>
					<g:field type="file" name="photo-file-multiple" class="text" multiple="true" />
				</div>
				<div id="single-photo">
					<div class="field">
						<label>Name</label>
						<g:field type="text" name="photo-name" class="text" />
					</div>
					<div class="field">
						<label>Photo</label>
						<g:field type="file" name="photo-file" class="text" />
					</div>
					<div class="field">
						<label>Description</label>
						<g:textArea name="photo-description" class="text" />
					</div>
				</div>
				<h2 class="ui header">Add to Playlists</h2>
				<div class="four fields">
					<pp:eachPlaylist>
						<div class="field">
							<div class="ui toggle checkbox">
								<g:checkBox name="playlist-${ it.id }" class="checkbox" />
								<label>${ it.name }</label>
							</div>
						</div>
					</pp:eachPlaylist>
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
		$('.ui.checkbox').checkbox();
		$('#multiple-photos-on').change(function() {
			var multipleOn = $('#multiple-photos-on').is(':checked');
			$('#' + (multipleOn ? 'multiple' : 'single') + '-photo').show();
			$('#' + (multipleOn ? 'single' : 'multiple') + '-photo').hide();
		});
	});

	function createPlaylist() {
		$('.create-playlist .form').removeClass('warning');
		$('.create-playlist .form').removeClass('error');
		$('.create-photo .text').val('');
		$('.ui.modal.create-playlist').modal({selector: {close: '.close'}}).modal('show');
	}

	function createPhoto() {
		$('.create-photo .form').removeClass('warning');
		$('.create-photo .form').removeClass('error');
		$('.create-photo .text').val('');
		$('.create-photo .checkbox').attr('checked', false);
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
						window.location.href = '/playlist/view/' + data.id;
					} else {
						$('#playlist-error').html(data.error);
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
		var files = document.getElementById('photo-file-multiple').files;
		for (var i = 0; i < files.length; i++) {
			fd.append('file-photo-' + i, files[i], files[i].name);
		}
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
					$('#photo-error').html(data.error);
					$('.create-photo .form').removeClass('warning');
					$('.create-photo .form').addClass('error');
				}
			},
			complete: function() {
				$('.create-photo .form').removeClass('loading');
			}
		});
	}
</script>