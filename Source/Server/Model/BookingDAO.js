module.exports = function() { 

	this.BookingDAO = function() {
		this.collection = 'Booking';
		this.collection2 = 'FlightDetail';
	}

	BookingDAO.prototype.generateID = function() {
		var id = '';
		var constID = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

		for (var i = 0; i < 6; i++) 
			id += constID.charAt(Math.floor(Math.random() * constID.length));

		return id;
	};

	BookingDAO.prototype.getBookingById = function(bookingId, callback) {
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

	BookingDAO.prototype.createBooking = function(callback) {
		var booking = {
			bookingId : this.generateID(),
			time : new Date().getTime(),
			totalCost : 0,
			status : 0
		};
		var that = this;
	    database.collection(this.collection).insertOne(booking, function(err, reply) {
	    	try {
				if (err)
					throw err;
				
				if (reply.result.ok == 1) {
	            	callback({
	            		bookingId : booking.bookingId
	            	});

	            	database.collection(that.collection2).insertOne({
	            		bookingId : booking.bookingId,
	            		details : [],
	            		passengers : []
	            	}, function(err, reply) {});
	            }
	            else
	            	callback(-1);
            }
			catch(err) {
				console.log(err);
			    callback(-1);
			}
        });

	};

	BookingDAO.prototype.updateStatus = function(bookingId, totalCost, callback) {
		var status = 0;
		if (totalCost > 0)
			status = 1;
		database.collection(this.collection).update({ bookingId : bookingId },
	    { $set : { 
	    	totalCost : totalCost,
	    	status : status 
	    } }, function(err, reply) {
	    	try {
				if (err)
					throw err;
				
				if (reply.result.ok == 1) 
		            callback({
		            	totalCost : totalCost
		            });
		        else
		        	throw err;
            }
			catch(err) {
				console.log(err);
			    callback(-1);
			}
        });
	};

	BookingDAO.prototype.getAll = function(callback) {
	    database.collection(this.collection).find({},{ _id : false }).toArray(function(err, reply) {
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