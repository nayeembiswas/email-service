$(document).ready(function() {
	$('#message').html('');
	$('#message').hide();
})


function signin() {
	var hiddenTime = 6000;
	var signinRequest = {};


	signinRequest.username = $('#username').val();
	if (signinRequest.username == "") {
		$('#message').html('Username is empty');
		$('#message').show();
		$('#message').hide(hiddenTime);
		return;
	}
	signinRequest.password = $('#password').val();
	if (signinRequest.password == "") {
		$('#message').html('Password is empty');
		$('#message').show();
		$('#message').hide(hiddenTime);
		return;
	}

	var reqObj = JSON.stringify(signinRequest);
	$.ajax({
		url: "/api/auth/signin",
		method: "POST",
		data: reqObj,
		contentType: 'application/json; charset=utf-8',
		success: function(res) {
			if (res.data == null) {
				$('#message').html(res.message);
				$('#message').show();
				$('#message').hide(hiddenTime);
			} else {
				/*$('#message').html(res.data.token);
				$('#message').show();*/
				/*$('#message').hide(hiddenTime);*/
				window.localStorage.setItem('access_token', res.data.token);
				window.location.replace("/dashboard");
			}

			resetEmployee();
		},
		error: function(error) {
			$('#message').html("Signin failed");
			$('#message').show();
			$('#message').hide(hiddenTime);
		}
	})
}


// Reser Signup from data
function resetEmployee() {
	$('#username').val('');
	$('#password').val('');
}


/*function getDashboard() {
	console.log('##############');
	var token = window.localStorage.getItem('access_token');
	console.log('##############');
	console.log(token);
	$.ajax({
		url: "/score/dashboard",
		method: "GET",
		headers: { Authorization: $`Bearer ${window.localStorage.getItem("access_token")}` },
		dataType: "json",
		success: function(data) {
			console.log(data.data[0].channel);
			console.log(data.data[0].items[0]);
			var dashboardBody = $('#dashboardInfo');
			dashboardBody.empty();

			$(data.data).each(
				function(index, element) {
					var today = new Date();
					var date = today.getDate() + '-' + (today.getMonth() + 1) + '-' + today.getFullYear();
					dashboardBody.html(
						'<div> <h1> dashboard </h1>' + element.channel.title + '</div>')

					$(element.items).each(
						function(idx, elmt) {
							dashboardBody.append(
								'<div> <h4> item </h4>' + elmt.title + '</div>')
						})
				})

		}
	})
}

function getSearch() {
	var keyword = $('#keyword').val();
	if (keyword != null) {

	}
	$.ajax({
		url: "/score/search/" + keyword,
		method: "GET",
		dataType: "json",
		success: function(data) {
			console.log(data.data);
			var dashboardBody = $('#dashboardInfo');
			dashboardBody.empty();
			
			dashboardBody.html()

			$(data.data).each(
				function(index, element) {
					dashboardBody.append(
						'<div> <h4> item </h4>' + element.title + '</div>')

				})

		}
	})
}

function onloadDoc(){
	getDashboard();
	console.log('Document load');
}*/

