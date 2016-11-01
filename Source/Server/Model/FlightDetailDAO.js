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
				var result = {
					flights : []
				}
				result.flights = reply.details
	            callback(result);
            }
			catch(err) {
				console.log(err);
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
				
	            callback(reply);
            }
			catch(err) {
				console.log(err);
			    callback(-1);
			}
        });
	};

	FlightDetailDAO.prototype.addFlight = function(bookingId, flightId, time, grade, price, callback) {
	    database.collection(this.collection).update({ bookingId : bookingId }, 
	    	{ $push: { details : {
	    		flightId : flightId,
	    		time : time, 
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
				console.log(err);
			    callback(-1);
			}
        });
	};

	FlightDetailDAO.prototype.addPassenger = function(bookingId, passengers, callback) {
	    database.collection(this.collection).update({ bookingId : bookingId }, 
	    	{ $set: { passengers : passengers } }, function(err, reply) {
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
				console.log(err);
			    callback(-1);
			}
        });
	};

	FlightDetailDAO.prototype.getAll = function(callback) {
	    database.collection(this.collection).find({},{ _id : false }).toArray(function(err, reply) {
	    	try {
				if (err)
					throw err;

				var result = [];

				for(var i = 0; i < reply.length; i++) 
					result[reply[i].bookingId] = reply[i];

	            callback(result);
            }
			catch(err) {
				console.log(err);
			    callback(-1);
			}
        });
	};

	FlightDetailDAO.prototype.getFlightDetailById = function(bookingId, callback) {
	    database.collection(this.collection).findOne({ bookingId : bookingId },
	    { _id : false }, function(err, reply) {
	    	try {
				if (err)
					throw err;
				
	            callback(reply);
            }
			catch(err) {
				console.log(err);
			    callback(-1);
			}
        });
	};
}