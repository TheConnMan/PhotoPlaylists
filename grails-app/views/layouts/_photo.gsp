<div class="ui card">
	<div class="image" id="photo-${ photo.id }">
		<img src="/photo/view/${ photo.id }" />
		<div class="ui dimmer">
			<div class="content">
				<div class="center"
					style="text-align: left; vertical-align: top; padding: 10px">
					<h2 class="photo-name">
						${ photo.name }
					</h2>
					<p class = "photo-description">
						${ photo.description }
					</p>
					<p class="date">
						Uploaded on ${ photo.uploadedDate.format('M/d/yyyy') } by ${ photo.uploadedBy }
					</p>
				</div>
			</div>
		</div>
	</div>
	<div class="ui bottom attached button" onclick="editPhoto(${ photo.id });">
		<i class="edit icon"></i> Edit
	</div>
</div>


