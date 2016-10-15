module.exports = function() { 

	this.FlightDAO = function() {
		this.collection = 'Flight';
	} 

	FlightDAO.prototype.getDepartureAirports = function(callback) {
	    database.collection(this.collection).aggregate({ $group : { _id : "$departure" } }, function(err, reply) {
	    	try {
				if (err)
					throw err;

				var result = [];
				for (var i = 0; i < reply.length; i++)
					result.push(reply[i]._id)

	            callback(reply);
            }
			catch(err) {
			    callback(-1);
			}
        });
	};

	FlightDAO.prototype.getDestinationAirports = function(callback) {
	    database.collection(this.collection).aggregate({ $group : { _id : "$destination" }  }, function(err, reply) {
	    	try {
				if (err)
					throw err;

				var result = [];
				for (var i = 0; i < reply.length; i++)
					result.push(reply[i]._id)

	            callback(result);
            }
			catch(err) {
			    callback(-1);
			}
        });
	};

	FlightDAO.prototype.findFlight = function(dep, dest, date, number, callback) {
	    database.collection(this.collection).find({ 
	    	departure : dep,
	    	destination : dest,
	    	//time : date
	    	number : { $gt : parseInt(number) } 
	    }, { _id : false }).toArray(function(err, reply) {
	    	try {
				if (err)
					throw err;

	            callback(reply);
            }
			catch(err) {
			    callback(-1);
			}
        });
	};
}