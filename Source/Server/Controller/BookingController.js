require('../Model/BookingDAO')();

module.exports = function(app) { 
    var bookingDAO = new BookingDAO();
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
        bookingDAO.getUpdateStatus(req.params.bookingId, function(result) {
            if (result == -1) {
                res.statusCode = 500;
                return res.json({
                    error : 'Error 500: Server error.'
                });
            }

            res.json(result); 
        });
    });
}
