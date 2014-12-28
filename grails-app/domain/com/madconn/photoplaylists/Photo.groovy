package com.madconn.photoplaylists

class Photo {
	
	String name
	String description
	Date uploadedDate
	User uploadedBy
	Date lastUpdated
	Date lastViewed
	String fileLocation

    static constraints = {
		name()
		description maxSize: 1000, nullable: true
		uploadedDate()
		uploadedBy()
		lastUpdated()
		lastViewed nullable: true
		fileLocation()
    }
	
	String toString() {
		name
	}
}
