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
		
		(1..5).each {
			User admin = User.findByUsername('admin');
			new Playlist(
				name: 'Playlist ' + it,
				createdBy: admin,
				createdDate: new Date(),
				lastViewedDate: new Date(),
				lastEditedDate: new Date()
			).save()
		}
    }
    def destroy = {
		
    }
}
