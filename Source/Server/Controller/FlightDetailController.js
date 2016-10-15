require('../Model/FlightDetailDAO')();

module.exports = function(app) { 
    var flightDetailDAO = new FlightDetailDAO();
    var url = '/api/flight_detail';    

    app.get(url + '/:bookingId/flights', function(req, res) {
        flightDetailDAO.getFlights(req.params.bookingId, function(result) {
            if (result == -1) {
                res.statusCode = 500;
                return res.json({
                    error : 'Error 500: Server error.'
                });
            }

            res.json(result); 
        });
    });

    app.get(url + '/:bookingId/passengers', function(req, res) {
        flightDetailDAO.getPassengers(req.params.bookingId, function(result) {
            if (result == -1) {
                res.statusCode = 500;
                return res.json({
                    error : 'Error 500: Server error.'
                });
            }

            res.json(result); 
        });
    });

    app.post(url + '/:bookingId/flight', function(req, res) {
        if(!req.body.hasOwnProperty('flightId') || !req.body.hasOwnProperty('grade') || 
           !req.body.hasOwnProperty('price')) {
            res.statusCode = 400;
            return res.json({
                error : 'Error 400: Syntax incorrect.'
            });
        }

        flightDetailDAO.addFlight(req.params.bookingId, req.body.flightId, req.body.grade, 
            req.body.price, function(result) {
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

    app.post(url + '/:bookingId/passenger', function(req, res) {
        if(!req.body.hasOwnProperty('passenger')) {
            res.statusCode = 400;
            return res.json({
                error : 'Error 400: Syntax incorrect.'
            });
        }

        flightDetailDAO.addPassenger(req.params.bookingId, req.body.passenger, function(result) {
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
}
