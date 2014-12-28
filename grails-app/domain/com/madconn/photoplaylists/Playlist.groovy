package com.madconn.photoplaylists

class Playlist {
	
	String name
	User createdBy
	Date createdDate
	String description
	Date lastViewedDate
	Date lastEditedDate
	
	static hasMany = [photos: Photo]

    static constraints = {
		name()
		createdBy()
		createdDate()
		description nullable: true, maxSize: 1000
		lastViewedDate()
		lastEditedDate()
    }
	
	String toString() {
		name
	}
}
