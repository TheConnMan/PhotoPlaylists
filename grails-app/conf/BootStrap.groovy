import java.util.Date;

import com.madconn.photoplaylists.Playlist
import com.madconn.photoplaylists.Role
import com.madconn.photoplaylists.User
import com.madconn.photoplaylists.UserRole

class BootStrap {
	
	def createUser(name, password, role) {
		def me = new User(username: name, password: password, enabled: true).save()
		UserRole.create(me, role, true)
	}

    def init = { servletContext ->
		println 'Bootstrapping'
		def adminRole = new Role(authority: "ROLE_ADMIN").save()
		def userRole = new Role(authority: "ROLE_USER").save()
		createUser('admin', 'theconnman', adminRole)
		createUser('user', 'theconnman', userRole)
		
		(1..5).each { i ->
			User admin = User.findByUsername('admin');
			Playlist p = new Playlist(
				name: 'Playlist ' + i,
				description: 'Description ' + i,
				createdBy: admin,
				createdDate: new Date(),
				lastViewedDate: new Date(),
				lastEditedDate: new Date()
			).save()
			(1..5).each {
				p.addToPhotos(
					name: 'Photo ' + it,
					description: 'Description ' + it,
					uploadedDate: new Date(),
					uploadedBy: admin,
					lastUpdated: new Date(),
					fileLocation: 'http://placehold.it/144x240'
				)
			}
		}
    }
    def destroy = {
		
    }
}
