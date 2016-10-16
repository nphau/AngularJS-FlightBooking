module.exports = function() { 

	this.BookingDAO = function() {
		this.collection = 'Booking';
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

	    database.collection(this.collection).insertOne(booking, function(err, reply) {
	    	try {
				if (err)
					throw err;
				
				if (reply.result.ok == 1)
	            	callback({
	            		bookingId : booking.bookingId
	            	});
	            else
	            	callback(-1);
            }
			catch(err) {
			    callback(-1);
			}
        });
	};

	BookingDAO.prototype.updateStatus = function(bookingId, callback) {
	    database.collection(this.collection).update({ bookingId : bookingId },
	    { $set : { status : 1 } }, function(err, reply) {
	    	try {
				if (err)
					throw err;
				
	            callback({
	            	status : reply.result.ok
	            });
            }
			catch(err) {
			    callback(-1);
			}
        });
	};
}