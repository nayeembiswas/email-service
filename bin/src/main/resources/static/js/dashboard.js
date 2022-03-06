$(document).ready(function() {
	var token = window.localStorage.getItem('access_token');
	if (token == null) {
		window.location.replace("/");
	}
	else {
		getDashboard();
	}

})

function getDashboard() {
	var token = window.localStorage.getItem('access_token');
	$.ajax({
		url: "/score/dashboard",
		method: "GET",
		headers: {
			'Authorization': `Bearer ${token}`,
		},
		dataType: "json",
		success: function(data) {
			console.log(data.data[0].channel);
			console.log(data.data[0].items[0]);
			var dashboardBody = $('#dashboardInfo');
			dashboardBody.empty();

			$(data.data).each(
				function(index, element) {
					dashboardBody.html(

						'<div class="container">'
						+ '<h3> ' + element.channel.title + '</h3>'
						+ '<h6> ' + element.channel.link + ' </h6>'
						+ '<h6> ' + element.channel.description + ' </h6>'
						+ '<h6> ' + element.channel.copyright + ' </h6>'
						+ '<h6> ' + element.channel.language + ' </h6>'
						+ '<h6> ' + element.channel.pubDate + ' </h6>'						
						+ '<div class="panel-group">'
						);


					
						
						/*'<div> <h3>' + element.channel.title + '</h3></div>') */

					$(element.items).each(
						function(idx, elmt) {
							dashboardBody.append(

								'<div class="col-md-4">'
								+ ' <div class="panel panel-primary" style="margin-right: 5px; margin-bottom: 5px;"> '
								+ '		<div class="panel-heading"> ' + elmt.title + '</div>'
								+ '		<div class="panel-body">' + elmt.link + '</div>'
								+ '		<div class="panel-body">' + elmt.description + '</div>'
								+ '		<div class="panel-body">' + elmt.guid + '</div>'
								+ '	</div>'
								+ '</div>'
								
								/*'<div> <h4> item </h4>' + elmt.title + '</div>'*/)
						})
						
						dashboardBody.append(
							'</div> </div>')
						
				})

		}
	})
}

function getSearch() {
	var keyword = $('#keyword').val();
	var token = window.localStorage.getItem('access_token');
	if (keyword != null) {

	}
	$.ajax({
		url: "/score/search/" + keyword,
		method: "GET",
		headers: {
			'Authorization': `Bearer ${token}`,
		},
		dataType: "json",
		success: function(data) {
			console.log(data.data);
			var dashboardBody = $('#dashboardInfo');
			dashboardBody.empty();

			dashboardBody.html(
				'<div class="container">'
				+ '<div class="panel-group">'
			)

			$(data.data).each(
				function(index, element) {
					dashboardBody.append(

								'<div class="col-md-4">'
								+ ' <div class="panel panel-primary" style="margin-right: 5px; margin-bottom: 5px;"> '
								+ '		<div class="panel-heading"> ' + element.title + '</div>'
								+ '		<div class="panel-body">' + element.link + '</div>'
								+ '		<div class="panel-body">' + element.description + '</div>'
								+ '		<div class="panel-body">' + element.guid + '</div>'
								+ '	</div>'
								+ '</div>'
								)

				})
				dashboardBody.append(
							'</div> </div>')

		}
	})
}

function resetbtn(){
	getDashboard();
}

function logout() {

	window.localStorage.removeItem("access_token");
	window.location.replace("/");
}
