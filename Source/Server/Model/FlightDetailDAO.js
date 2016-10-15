module.exports = function() { 

	this.FlightDetailDAO = function() {
		this.collection = 'FlightDetail';
	} 

	FlightDetailDAO.prototype.getFlights = function(bookingId, callback) {
	    database.collection(this.collection).findOne({ bookingId : bookingId }, 
	    	{ _id : false, details : true }, function(err, reply) {
	    	try {
				if (err)
					throw err;
				
	            callback(reply.details);
            }
			catch(err) {
			    callback(-1);
			}
        });
	};

	FlightDetailDAO.prototype.getPassengers = function(bookingId, callback) {
	    database.collection(this.collection).findOne({ bookingId : bookingId }, 
	    	{ _id : false, passengers : true }, function(err, reply) {
	    	try {
				if (err)
					throw err;
				
	            callback(reply.passengers);
            }
			catch(err) {
			    callback(-1);
			}
        });
	};

	FlightDetailDAO.prototype.addFlight = function(bookingId, flightId, grade, price, callback) {
	    database.collection(this.collection).update({ bookingId : bookingId }, 
	    	{ $push: { details : {
	    		flightId : flightId,
	    		time : new Date().getTime(), 
	    		grade : grade, 
	    		price : price
	    	} } }, function(err, reply) {
	    	try {
				if (err)
					throw err;
				
	            if (reply.result.ok == 1)
	            	callback({
	            		success : true
	            	});
	            else
	            	callback(-1);
            }
			catch(err) {
			    callback(-1);
			}
        });
	};

	FlightDetailDAO.prototype.addPassenger = function(bookingId, passenger, callback) {
	    database.collection(this.collection).update({ bookingId : bookingId }, 
	    	{ $push: { passengers : passenger } }, function(err, reply) {
	    	try {
				if (err)
					throw err;
				
	            if (reply.result.ok == 1)
	            	callback({
	            		success : true
	            	});
	            else
	            	callback(-1);
            }
			catch(err) {
			    callback(-1);
			}
        });
	};
}