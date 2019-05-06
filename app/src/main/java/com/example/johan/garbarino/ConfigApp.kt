package com.example.johan.garbarino

class ConfigApp {
	companion object {
		fun getUrlProductList    ()         :String = "http://garbarino-mock-api.s3-website-us-east-1.amazonaws.com/products/"
		fun getUrlProductDetails (id:String):String = "http://garbarino-mock-api.s3-website-us-east-1.amazonaws.com/products/" + id + "/"
		fun getUrlProductReviews (id:String):String = "http://garbarino-mock-api.s3-website-us-east-1.amazonaws.com/products/" + id + "/reviews/"

		//null to show all comments
		var commentsToShow: Int? = null

	}
}
