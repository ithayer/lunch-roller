// ReadyForZero Namespace
RFZ = {};

// Page level
RFZ.page = {};

// Places model
RFZ.page.Place = Backbone.Model.extend({
	id: 0,
	address: '',
	name: '',
	yelp: {}
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
			address: this.model.get('address')
		}
		
		$('#place-tmpl').tmpl(templateData).appendTo(this.el);
	},
	castVote: function(event) {
		event = jQuery.event.fix(event);
		
		if($(event.target).hasClass('voted')) {
			$.ajax({
				url: '/api/vote/del',
				type: 'POST',
				success: function(result) {
					console.log(result);
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
				type: 'POST',
				success: function(result) {
					console.log(result);
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

//api/places
RFZ.page.init = function() {
	$.ajax({
		url: '/api/places',
		success: function(result) {
			$.each(result, function(index, value) {
				console.log(value);
				
				var currentModel = new RFZ.page.Place({
					id: value.id,
					address: value.address,
					name: value.name,
					yelp: value.yelp
				});
				
				var currentEl = $('<li/>').appendTo($('#places'));
				
				new RFZ.page.PlaceView({
					el: currentEl,
					model: currentModel
				});
			});
		}
	});
}

// No document.ready needed
RFZ.page.init();