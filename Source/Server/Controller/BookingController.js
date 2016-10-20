require('../Model/BookingDAO')();

module.exports = function(app) { 
    var bookingDAO = new BookingDAO();
    var flightDetailDAO = new FlightDetailDAO();
    var url = '/api/booking';    

    app.get(url + '/:bookingId', function(req, res) {
        bookingDAO.getBookingById(req.params.bookingId, function(result) {
            if (result == -1) {
                res.statusCode = 500;
                return res.json({
                    error : 'Error 500: Server error.'
                });
            }

            res.json(result); 
        });
    });

    app.post(url, function(req, res) {
        bookingDAO.createBooking(function(result) {
            if (result == -1) {
                res.statusCode = 500;
                return res.json({
                    error : 'Error 500: Server error.'
                });
            }

            res.statusCode = 201;
            res.json(result); 
        });
    });

    app.put(url + '/:bookingId', function(req, res) {
        flightDetailDAO.getFlights(req.params.bookingId, function(flights) {
            flightDetailDAO.getPassengers(req.params.bookingId, function(passengers) {
                if (flights == -1 || passengers == -1) {
                    res.statusCode = 500;
                    return res.json({
                        error : 'Error 500: Server error.'
                    });
                }
                var totalCost = 0;
                for (var i = 0; i < flights.length; i++)
                    totalCost += flights[i].price * passengers.passengers.length;

                bookingDAO.updateStatus(req.params.bookingId, totalCost, function(result) {
                    if (result == -1) {
                        res.statusCode = 500;
                        return res.json({
                            error : 'Error 500: Server error.'
                        });
                    }

                    res.statusCode = 202;
                    res.json(result); 
                });  
            });
        });
    });
}
