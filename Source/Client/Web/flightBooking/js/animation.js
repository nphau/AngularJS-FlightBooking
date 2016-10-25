var selector = '.nav li';

$(selector).on('click', function(){
    $(selector).removeClass('active');
    $(this).addClass('active');
});


$("#submitFlight").show();

$(window).scroll(function() {
    if ($(document).height() - ($(window).scrollTop() + $(window).height()) < 380) {
        if ($('#submitFlight').hasClass('fixed')) {
            $('#submitFlight').removeClass('fixed');
        }
    } else {
        if (!$('#submitFlight').hasClass('fixed')) {
            $('#submitFlight').addClass('fixed');
        }
    }
});

// $(document).ready(function() {
//     $('#validatorEnabledForm').formValidation({
//         framework: 'bootstrap',
//         icon: {
//             valid: 'glyphicon glyphicon-ok',
//             invalid: 'glyphicon glyphicon-remove',
//             validating: 'glyphicon glyphicon-refresh'
//         },
//         fields: {
//             fullName: {
//                 validators: {
//                     notEmpty: {
//                         // enabled is true, by default
//                         message: 'The full name is required and cannot be empty'
//                     },
//                     stringLength: {
//                         enabled: true,
//                         min: 8,
//                         max: 40,
//                         message: 'The full name must be more than 8 and less than 40 characters long'
//                     },
//                     regexp: {
//                         enabled: false,
//                         regexp: /^[a-zA-Z\s]+$/,
//                         message: 'The full name can only consist of alphabetical, number, and space'
//                     }
//                 }
//             }
//         }
//     });
// });


//datepicker

$(function () {
	'use strict';
	var nowTemp = new Date();
	var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);

	var checkin = $('#timeCheckIn').datepicker({
	    onRender: function (date) {
	        return date.valueOf() < now.valueOf() ? 'disabled' : '';
	    }
	}).on('changeDate', function (ev) {
	    if (ev.date.valueOf() > checkout.date.valueOf()) {
	        var newDate = new Date(ev.date)
	        newDate.setDate(newDate.getDate() + 1);
	        checkout.setValue(newDate);
	    }
	    checkin.hide();
	    $('#timeCheckOut')[0].focus();
	}).data('datepicker');
	var checkout = $('#timeCheckOut').datepicker({
	    onRender: function (date) {
	        return date.valueOf() <= checkin.date.valueOf() ? 'disabled' : '';
	    }
	}).on('changeDate', function (ev) {
	    checkout.hide();
	}).data('datepicker');
});
