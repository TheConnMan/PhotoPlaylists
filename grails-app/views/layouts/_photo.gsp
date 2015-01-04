<div class="ui card">
	<div class="image" id="my-image">
		<img src="/photo/view/${ photo.id }" />
		<div class="ui dimmer">
			<div class="content">
				<div class="center"
					style="text-align: left; vertical-align: top; padding: 10px">
					<h2>
						${ photo.name }
					</h2>
					<p>
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


