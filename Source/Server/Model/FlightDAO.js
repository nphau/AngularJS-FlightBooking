module.exports = function() { 

	this.FlightDAO = function() {
		this.collection = 'Flight';
	} 

	FlightDAO.prototype.getDepartureAirports = function(callback) {
	    database.collection(this.collection).aggregate({ $group : { _id : "$departure" } }, function(err, reply) {
	    	try {
				if (err)
					throw err;

				var result = {
					airports : []
				};
				for (var i = 0; i < reply.length; i++)
					result.airports.push(reply[i]._id)

	            callback(result);
            }
			catch(err) {
			    callback(-1);
			}
        });
	};

	FlightDAO.prototype.getDestinationAirports = function(depart, callback) {
	    database.collection(this.collection).aggregate({ $group : { 
	    	_id : {
	    		departure : "$departure",
	    		destination : "$destination" 
	    	} } }, function(err, reply) {
	    	try {
				if (err)
					throw err;

				var result = {
					airports : []
				};
				for (var i = 0; i < reply.length; i++)
					if (reply[i]._id.departure.id == depart)
						result.airports.push(reply[i]._id.destination);

	            callback(result);
            }
			catch(err) {
			    callback(-1);
			}
        });
	};

	FlightDAO.prototype.findFlight = function(depart, arrive, time, adult, child, callback) {
		adult = parseInt(adult);
		child = parseInt(child);

	    database.collection(this.collection).find({ 
	    	'departure.id' : depart,
	    	'destination.id' : arrive
	    }, { _id : false }).toArray(function(err, reply) {
	    	try {
				if (err)
					throw err;

				var result = {
					flights : []
				};	
				for (var i = 0; i < reply.length; i++) 
					if (0 <= reply[i].time - parseInt(time) && reply[i].time - parseInt(time) <= 86400000) {
						var flights = {
							flightId : reply[i].flightId,
							time : reply[i].time,
							flex : []
						};

						for (var j = 0; j < reply[i].flex.length; j++) {
							if (reply[i].flex[j].remain >= adult + child) {
								var flex = {
									grade : reply[i].flex[j].grade,
									number : reply[i].flex[j].number,
									price : reply[i].flex[j].price
								};
								flights.flex.push(flex);
							}
						}

						if (flights.flex.length > 0)
							result.flights.push(flights);
		        	}
		        callback(result);
            }
			catch(err) {
				console.log(err)
			    callback(-1);
			}
        });
	};

	FlightDAO.prototype.updateSeatRemain = function(flights, number, callback) {
		var that = this;
		
		database.collection(this.collection).findOne({ 
    		flightId : flights[0].flightId,
    		time : flights[0].time
	    }, { _id : false, flex : true }, function(err, reply) {

			for (var j = 0; j < reply.flex.length; j++) 
				if (reply.flex[j].grade == flights[0].grade && reply.flex[j].price == flights[0].price) {
					reply.flex[j].remain -= number;
					break;
				}
			database.collection(that.collection).update({ 
	    		flightId : flights[0].flightId,
	    		time : flights[0].time
		    }, { $set: {flex : reply.flex }}, function(err, reply) {});
	    });

		if (flights.length == 2)
		database.collection(this.collection).findOne({ 
    		flightId : flights[1].flightId,
    		time : flights[1].time
	    }, { _id : false, flex : true }, function(err, reply) {

			for (var j = 0; j < reply.flex.length; j++) 
				if (reply.flex[j].grade == flights[1].grade && reply.flex[j].price == flights[1].price) {
					reply.flex[j].remain -= number;
					break;
				}
			database.collection(that.collection).update({ 
	    		flightId : flights[1].flightId,
	    		time : flights[1].time
		    }, { $set: {flex : reply.flex }}, function(err, reply) {});
	    });
		
		callback()
	};
}