// ReadyForZero Namespace
RFZ = {};

// Page level
RFZ.page = {};

// Places model
RFZ.page.Place = Backbone.Model.extend({
	id: 0,
	name: '',
	image_url: '',
	location: {},
	rating_url: '',
	rating: 0,
	review_count: 0,
	url: ''
});

// Places view
RFZ.page.PlaceView = Backbone.View.extend({
	events: {
		'click .jsCastVote' : 'castVote'
	},
	initialize: function() {
		// Render the view
		this.render();
	},
	render: function() {
		var templateData = {
			name: this.model.get('name'),
			address: this.model.get('location').address,
			image_url: this.model.get('image_url'),
			rating_url: this.model.get('rating_url'),
			rating: this.model.get('rating'),
			review_count: this.model.get('review_count')
		}
		
		var myLatlng = new google.maps.LatLng(this.model.get('location').coordinate.latitude, this.model.get('location').coordinate.longitude);
		
		var marker = new google.maps.Marker({
			position: myLatlng,
			map: RFZ.page.map,
			title: this.model.get('name')
		});
		
		$('#place-tmpl').tmpl(templateData).appendTo(this.el);
	},
	castVote: function(event) {
		event = jQuery.event.fix(event);
		
		var elem = this.el;
		
		if(elem.find('.jsCastVote').hasClass('app-button-voted')) {
			$.ajax({
				url: '/api/vote/del',
				data: {
					person_id: 1,
					place_id: this.model.get('id')
				},
				type: 'POST',
				success: function(result) {
					if(result === 0) {
						// Vote removed
						elem.find('.jsCastVote').html('<span>Add Vote</span>').removeClass('app-button-voted');
					} else {
						// TODO
					}
				}
			});
		} else {
			$.ajax({
				url: '/api/vote/add',
				data: {
					person_id: 1,
					place_id: this.model.get('id')
				},
				type: 'POST',
				success: function(result) {
					if(result === 0 || result === 1) {
						// Vote added or exists
						elem.find('.jsCastVote').html('<span>Remove Vote</span>').addClass('app-button-voted');
					} else {
						// TODO
					}
				}
			});
		}
	}
});

RFZ.page.googleMap = function() {
	// Set up map
	var myLatlng = new google.maps.LatLng(37.789607, -122.39984);
	var myOptions = {
		zoom: 14,
		zoomControl: true,
		center: myLatlng,
		mapTypeId: google.maps.MapTypeId.ROADMAP
	};
	
	RFZ.page.map = new google.maps.Map(document.getElementById('map'), myOptions);
}


RFZ.page.todaysVote = function() {
	$('#jsPickMyLunch, #jsPickAgain').click(function(event) {
		event.preventDefault();
		
		$.ajax({
			url: '/api/select',
			success: function(result) {
				console.log(result);
				if(result) {
					$('#jsVotedPlaceName').text(result.name);
					
					$('#modal').show();
				} else {
					alert('You need to vote on places before picking where to eat!')
				}
			}
		});
	});
	
	$('#jsCloseDialog').click(function() {
		$('#modal').hide();
	});
}

RFZ.page.init = function() {
	// Set up map
	RFZ.page.googleMap();
	
	// Grab Places
	$.ajax({
		url: '/api/places',
		success: function(result) {
			$.each(result, function(index, value) {
				var currentModel = new RFZ.page.Place({
					id: value.id,
					name: value.name,
					image_url: value.image_url,
					location: value.location,
					rating_url: value.rating_img_url,
					rating: value.rating,
					review_count: value.review_count,
					url: value.url
				});
				
				if(index === 0) {
					var currentEl = $('<li/>', { class: ' first clearfix' }).appendTo($('#places'));
				} else {
					var currentEl = $('<li/>', { class: 'clearfix' }).appendTo($('#places'));
				}
				
				new RFZ.page.PlaceView({
					el: currentEl,
					model: currentModel
				});
			});
		}
	});
	
	// Set up other events
	RFZ.page.todaysVote();
}

// No document.ready needed
RFZ.page.init();