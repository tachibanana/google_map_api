package com.googleapi
import groovyx.net.http.RESTClient
import static groovyx.net.http.ContentType.*

class Services {
	
	private def URL = ['https://maps.googleapis.com']
	private def path = ['/maps/api/']
	private def service = ['directions' , 'distancematrix' ,'elevation' , 'geocode']
	private def output = ['xml' , 'json']
	def params = []
	
	def directionAPI(){
		
		path[0] += "${service[0]}/${output[0]}"
		def client = new RESTClient(URL[0])
		def response = client.get(path:path[0] , query:params , contentType: TEXT)
		
		response.data.text
	}
	
	def geocodeAPI(){
		
		path[0] += "${service[3]}/${output[0]}"
		def client = new RESTClient(URL[0])
		def response = client.get(path:path[0] , query:params[0] , contentType: TEXT)
		
		response.data.text
	}
	
	
	
	
	
	
}
