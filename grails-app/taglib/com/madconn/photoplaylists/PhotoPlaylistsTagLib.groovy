package com.madconn.photoplaylists

class PhotoPlaylistsTagLib {
	static namespace = 'pp'
	
	def springSecurityService
    
	/**
	 * Renders content for each of a user's playlists.
	 * 
	 * 
	 */
	def eachPlaylist = { attrs, body ->
		Collection<Playlist> playlists = Playlist.findAllByCreatedBy(springSecurityService.currentUser);
		playlists.each {
			out << body(it)
		}
	}
}
