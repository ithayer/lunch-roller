// ReadyForZero Namespace
RFZ = {};

// Page level
RFZ.page = {};

// Places model
RFZ.page.Place = Backbone.Model.extend({
	id: 0,
	categories: [],
	name: '',
	image_url: '',
	location: {},
	rating: 0,
	review_count: 0,
	rating_img_url_small: '',
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
		//console.log('Here');
		
		var templateData = {
			name: this.model.get('name'),
			category: this.model.get('categories')[0][0],
			address: this.model.get('location').address,
			city: this.model.get('location').city,
			state: this.model.get('location').state_code,
			postal_code: this.model.get('location').postal_code,
			image_url: this.model.get('image_url'),
			yelp_rating: this.model.get('rating_img_url_small')
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
		
		if($(event.target).hasClass('voted')) {
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
						$(event.target).text('Add vote').removeClass('voted');
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
						$(event.target).text('Remove vote').addClass('voted');
					} else {
						// TODO
					}
				}
			});
		}
	}
});

RFZ.page.todaysVote = function() {
	$('#jsGetVotedPlace').click(function(event) {
		event.preventDefault();
		
		$.ajax({
			url: '/api/select',
			success: function(result) {
				console.log(result);
			}
		});
	});
}

//api/places
RFZ.page.init = function() {
	// Set up map
	var myLatlng = new google.maps.LatLng(37.775, -122.4183333);
	var myOptions = {
		zoom: 14,
		zoomControl: true,
		zoomControlOptions: {
			position: google.maps.ControlPosition.RIGHT_CENTER:
		},
		center: myLatlng,
		mapTypeId: google.maps.MapTypeId.ROADMAP
	};
	
	RFZ.page.map = new google.maps.Map(document.getElementById("map"), myOptions);
	
	// Grab Places
	$.ajax({
		url: '/api/places',
		success: function(result) {
			$.each(result, function(index, value) {
				//console.log(value);
				
				var currentModel = new RFZ.page.Place({
					id: value.id,
					categories: value.categories,
					name: value.name,
					image_url: value.image_url,
					location: value.location,
					rating: value.rating,
					review_count: value.review_count,
					rating_img_url_small: value.rating_img_url_small,
					url: value.url
				});
				
				var currentEl = $('<li/>', { class: 'clearfix' }).appendTo($('#places'));
				
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