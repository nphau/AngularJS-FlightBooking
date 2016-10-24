require('../Model/FlightDAO')();

module.exports = function(app) { 
    var flightDAO = new FlightDAO();
    var url = '/api/flights';    

    app.get(url + '/depart_airports', function(req, res) {
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

    app.get(url + '/arrive_airports/:depart', function(req, res) {
        flightDAO.getDestinationAirports(req.params.depart, function(result) {
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
        if (req.query.adult * 2 < req.query.child || req.query.adult > 6) {
            res.statusCode = 412 ;
            return res.json({
                error : 'Error 412 : Condition failed.'
            });
        }
        flightDAO.findFlight(req.query.depart, req.query.arrive, req.query.time, 
            req.query.adult, req.query.child, function(result) {
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
