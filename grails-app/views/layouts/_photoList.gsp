<div class="ui ${ rowSize } cards">
	<g:each in="${ photos }">
		<g:render template="/layouts/photo" model="[photo: it]" />
	</g:each>
</div>

<div class="ui modal edit-photo">
	<i class="close icon"></i>
	<div class="header">
		Edit This Photo
	</div>
	<div class="content">
		<g:form name="photo-edit-form">
			<div class="ui form fluid">
				<div class="ui warning message">
					<div class="header">Input Invalid</div>
					<p>Please enter a name</p>
				</div>
				<div class="ui error message">
					<div class="header">Photo Already Exists</div>
					<p>That photo already exists, please choose a new photo name</p>
				</div>
				<div class="required field">
					<label>Name</label>
					<g:field type="text" name="photo-edit-name" class="photo-name text" />
				</div>
				<div class="field">
					<label>Description</label>
					<g:textArea name="photo-edit-description" class="photo-description text" />
				</div>
				<g:field type="hidden" name="photo-edit-id" />
				<h2 class="ui header">Add to Playlists</h2>
				<div class="four fields">
					<pp:eachPlaylist>
						<div class="field">
							<div class="ui toggle checkbox">
								<g:checkBox name="playlist-edit-${ it.id }" class="checkbox" />
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
		<div class="ui positive right labeled icon button" onclick="submitEditPhoto();">
			Save
			<i class="checkmark icon"></i>
		</div>
	</div>
</div>

<script>
	var photoInfo = ${ raw((photos as grails.converters.JSON).toString()) };
	var photoMap = photoInfo.reduce(function(all, cur) {
			all[cur.id] = cur;
			return all;
		}, {});

	function editPhoto(id) {
		$('.edit-photo .photo-name').val(photoMap[id].name);
		$('.edit-photo .photo-description').val(photoMap[id].description);
		$('#photo-edit-id').val(id);
		$('.ui.modal.edit-photo').modal({selector: {close: '.close'}}).modal('show');
	}

	function submitEditPhoto() {
		if ($('#photo-edit-name').val().length != 0) {
			$('.edit-photo .form').addClass('loading');
			$.ajax({
				url: '/playlist/editPhoto',
				data: {
					name: $('#photo-edit-name').val(),
					description: $('#photo-edit-description').val(),
					id: $('#photo-edit-id').val()
				},
				success: function(data) {
					if (data.success) {
						console.log('Success')
						$('.ui.modal.edit-photo').modal('hide');
						swal('Success', 'Your photo was successfully updated', 'success');
					} else {
						$('.edit-photo .form').removeClass('warning');
						$('.edit-photo .form').addClass('error');
					}
				},
				complete: function() {
					$('.edit-photo .form').removeClass('loading');
				}
			});
		} else {
			$('.edit-photo .form').removeClass('error');
			$('.edit-photo .form').addClass('warning');
		}
	}

	$(function() {
		$('.ui.card .image').dimmer({
			on : 'hover'
		});
	})
</script>