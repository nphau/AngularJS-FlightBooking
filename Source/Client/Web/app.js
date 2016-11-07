angular.module('sortApp', [])

.controller('ticketController', function($scope, $http, $window) {
  $scope.sortType     = 'id'; // set the default sort type
  $scope.sortReverse  = false;  // set the default sort order
  $scope.searchTicket   = '';     // set the default search/filter term
  
  // create the list of sushi rolls 
  $http({
      method: 'GET',
      url: 'http://flightbooking01.mycloud.by/api/admin/bookings',
      headers: {
         'x-access-token': document.cookie
      },
  })
  .then(function successCallback(response) {
      var bookings = response.data.bookings;
      $scope.tickets = [];

      for(var i = 0; i < bookings.length; i++) {
          var booking = {};

          booking.id = bookings[i].bookingId;
          booking.date = new Date(bookings[i].time).toLocaleString().slice(10, new Date(bookings[i].time).toLocaleString().length);
          booking.time = new Date(bookings[i].time).toLocaleString().slice(0, 5);
          booking.cost = bookings[i].totalCost + ' VNĐ';

          var details = bookings[i].details[0].flightId;
          for(var j = 1; j < bookings[i].details.length; j++)
              details += ', ' + bookings[i].details[j].flightId;
          booking.list = details;

          var passengers = bookings[i].passengers[0];
          for(var j = 1; j < bookings[i].passengers.length; j++)
              passengers += ', ' + bookings[i].passengers[j] ;
          booking.guest = passengers;

          booking.status = bookings[i].status;

          $scope.tickets.push(booking);
      }



    }, function errorCallback(response) {
     
    });

    $scope.goBooking = function () {
    $window.location.href = 'page1.html';
  }

   $scope.goFlight = function () {
    $window.location.href = 'page2.html';
  }
})

.controller('flightController', function($scope, $http, $window) {
  $scope.sortType     = 'id'; // set the default sort type
  $scope.sortReverse  = false;  // set the default sort order
  $scope.searchTicket   = '';     // set the default search/filter term
  $scope.selected = {};
  $scope.index = -1;

  $scope.flightData = {};

  $scope.addFlight = function() {
    var flightId = document.getElementById('flightId').value;
    var departure = document.getElementById('departure').value;
    var destination = document.getElementById('destination').value;
    var date = document.getElementById('date').value;
    var hour = document.getElementById('hour').value;
    var d = date.split("/");
    var h = hour.split(":")
    date = new Date(d[2],(d[1] - 1),d[0],h[0],h[1],0);
    var time = date.getTime();
    var grade = document.getElementById('grade').value;
    var number = document.getElementById('number').value;
    var price = document.getElementById('price').value;

    $http({
      method: 'POST',
      url: 'http://flightbooking01.mycloud.by/api/admin/flights',
      headers: {
         'x-access-token': document.cookie
      },
      data : {
        flightId : flightId,
        departure : {
          id : departure.split(" - ")[0],
          name : departure.split(" - ")[1]
        },
        destination : {
          id : destination.split(" - ")[0],
          name : destination.split(" - ")[1]
        },
        time : time,
        grade : grade,
        number : parseInt(number),
        price : parseInt(price)
      }
  })
  .then(function successCallback(response) {
       $scope.getFlights();

    }, function errorCallback(response) {
     
    });
  };

  $scope.getFlights = function(){
    $http({
      method: 'GET',
      url: 'http://flightbooking01.mycloud.by/api/admin/flights',
      headers: {
         'x-access-token': document.cookie
      },
  })
  .then(function successCallback(response) {
      var flights = response.data.flights;
      $scope.flights = [];

      var k = 0;
      for(var i = 0; i < flights.length; i++) {
        for(var j = 0; j < flights[i].flex.length; j++, k++) {
          var flight = {};

          flight.id = k + ' - ' + flights[i].flightId;
          flight.idShow = flights[i].flightId;
          flight.departure = flights[i].departure.name + ' - ' + flights[i].departure.id;
          flight.departureShow = flights[i].departure;
          flight.destination = flights[i].destination.name + ' - ' + flights[i].destination.id;
          flight.destinationShow = flights[i].destination;
          flight.longTime = flights[i].time;
          flight.date = new Date(flights[i].time).toLocaleString().slice(10, new Date(flights[i].time).toLocaleString().length);
          flight.time = new Date(flights[i].time).toLocaleString().slice(0, 5);
          
          flight.grade = flights[i].flex[j].grade;
          flight.price = flights[i].flex[j].price + ' VNĐ';
          flight.priceShow = flights[i].flex[j].price;
          flight.seat = flights[i].flex[j].number;
          
          $scope.flights.push(flight);
        }
      }

    }, function errorCallback(response) {
     
    });
  };
  
  $scope.deleteFlight = function(flight, callback){
    // var res = $http.post('pages/ngProcess.php', 
    // {
    //   action : 'deleteflight',
    //   data : flight
    // });
    // res.success(function(data, status, headers, config) {
    //   console.log(data);
    //   $scope.getflights();
    // });
    // res.error(function(data, status, headers, config) {
    //   console.log("Error deleting");
    // });

    $http({
          method: 'DELETE',
          url: 'http://flightbooking01.mycloud.by/api/admin/flights' +
              '?flightId='+ flight.idShow +
              '&time=' + flight.longTime +
              '&grade=' + flight.grade +
              '&price=' + flight.priceShow,
          headers: {
             'x-access-token': document.cookie
          }
        })
        .then(function successCallback(response) {
            $scope.getFlights();
        }, function errorCallback(response) {
        });

  };
  
  
  $scope.getTemplate = function (flight) {
        if (flight.id === $scope.selected.id){
      return 'edit';
    }
        else return 'display';
    };
  
  $scope.reset = function () {
    $scope.selected = {};
  };
  
  $scope.editFlight = function (flight) {
    $scope.selected = angular.copy(flight);
  };
  
  $scope.updateFlight = function(flight) {
    var flight_Old = flight;
    var flight_New = angular.copy($scope.selected);
    
    $http({
      method: 'PUT',
      url: 'http://flightbooking01.mycloud.by/api/admin/flights',
      headers: {
         'x-access-token': document.cookie
      },
      data : {
        flightId : flight_Old.idShow, 
        departure : flight_Old.departureShow, 
        destination : flight_Old.destinationShow, 
        time1 : flight_Old.longTime, 
        grade1 : flight_Old.grade, 
        price1 : flight_Old.priceShow, 
        time2 : flight_New.longTime, 
        grade2 : flight_New.grade, 
        number2 : flight_New.seat,
        price2 : flight_New.priceShow
      }
    })
    .then(function successCallback(response) {

    }, function errorCallback(response) {
    });

    $scope.flights[flight.id.split(" - ")[0]] = angular.copy($scope.selected);
    $scope.reset();
  };

  $scope.goBooking = function () {
    $window.location.href = 'page1.html';
  }

   $scope.goFlight = function () {
    $window.location.href = 'page2.html';
  }

});

