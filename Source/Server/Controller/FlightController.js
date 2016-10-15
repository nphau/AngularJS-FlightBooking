require('../Model/FlightDAO')();

module.exports = function(app) { 
    var flightDAO = new FlightDAO();
    var url = '/api/flights';    

    app.get(url + '/departure_airports', function(req, res) {
        flightDAO.getDepartureAirports(function(result) {
            if (result == -1) {
                res.statusCode = 500;
                return res.json({
                    error : 'Error 500: Server error.'
                });
            }

            res.json(result); 
        });
    });

    app.get(url + '/destination_airports', function(req, res) {
        flightDAO.getDestinationAirports(function(result) {
            if (result == -1) {
                res.statusCode = 500;
                return res.json({
                    error : 'Error 500: Server error.'
                });
            }

            res.json(result); 
        });
    });

    app.get(url, function(req, res) {
        flightDAO.findFlight(req.query.dep, req.query.dest, req.query.date, 
            req.query.number, function(result) {
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
