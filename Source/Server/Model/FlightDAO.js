module.exports = function() { 

	this.FlightDAO = function() {
		this.collection = 'Flight';
		this.collectionFligtDetail = 'FlightDetail';
	} 

	FlightDAO.prototype.getDepartureAirports = function(callback) {
	    database.collection(this.collection).aggregate({ $group : { _id : "$departure" } }, function(err, reply) {
	    	try {
				if (err)
					throw err;

				var result = {
					depart : []
				};
				for (var i = 0; i < reply.length; i++)
					result.depart.push(reply[i]._id)

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
					arrive : []
				};
				for (var i = 0; i < reply.length; i++)
					if (reply[i]._id.departure == depart)
						result.arrive.push(reply[i]._id.destination);

	            callback(result);
            }
			catch(err) {
			    callback(-1);
			}
        });
	};

	FlightDAO.prototype.findFlight = function(depart, arrive, time, adult, child, callback) {
		var that = this;
	    database.collection(this.collection).find({ 
	    	departure : depart,
	    	destination : arrive,
	    	time : parseFloat(time)
	    }, { _id : false }).toArray(function(err, reply) {
	    	try {
				if (err)
					throw err;

				var result = [];	
				for (var i = 0; i < reply.length; i++) {
					var flights = {
						flightId : reply[i].flightId,
						departure : reply[i].departure,
						arrive : reply[i].destination,
						time : reply[i].time,
						flex : []
					};

					for (var j = 0; j < reply[i].flex.length; j++) {
						var flex = {
							grade : reply[i].flex[j].grade,
							number : reply[i].flex[j].number,
							price : reply[i].flex[j].price
						};
						setTimeout(function(){
							database.collection(that.collectionFligtDetail).find({ details : { $elemMatch: {
								flightId : flights.flightId,
								time : 1475725600000,
								grade : flex.grade,
								price : flex.price
							} } }, { passengers : true }).toArray(function(err, reply){
								try {
									if (err)
										throw err;

									var result = 0;
									for (var i = 0; i < reply.length; i++)
										result += reply[i].passengers.length;
									
									if (flex.number - result >= adult + child) 
										flights.flex.push(flex);
									console.log(flex.number, result);
									
								}
								catch (err) {
									callback(-1);
								}
							});
						}, 100);
					}
					if (flights.flex.length > 0)
						result.push(flights);
		            callback(result);
		        }
            }
			catch(err) {
				console.log(err)
			    callback(-1);
			}
        });
	};
}