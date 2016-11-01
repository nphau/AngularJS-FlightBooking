var config = require('./config');
var express = require('express');
var mongodb = require('mongodb');
    
var app = express();
var mongoClient = mongodb.MongoClient;
var PORT = 8080;

var allowCrossDomain = function(req, res, next) {
	res.header('Access-Control-Allow-Origin', '*');
	res.header('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE');
	res.header('Access-Control-Allow-Headers', 'Content-Type, x-access-token');

	next();
}

app.use(express.bodyParser());
app.use(allowCrossDomain);

database = null;
mongoClient.connect(config.MongoUrl, function (err, db) {
	if (err)
		console.log(err);
	else 
		database = db
});

require('./Controller/FlightController.js')(app);
require('./Controller/FlightDetailController.js')(app);
require('./Controller/BookingController.js')(app);
require('./Controller/AdminController.js')(app);

app.listen(PORT);  