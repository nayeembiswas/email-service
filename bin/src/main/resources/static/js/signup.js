$(document).ready(function() {
	$('#message').html('');
	$('#message').hide();
})


function signUp(){
	var hiddenTime = 6000;
	var signupRequest = {};

	
	signupRequest.username = $('#username').val();
	if (signupRequest.username == "") {
		$('#message').html('Username is empty');
		$('#message').show();
		$('#message').hide(hiddenTime);
		return;
	}
	signupRequest.email = $('#email').val();
	if (signupRequest.email == "") {
		$('#message').html('Email is empty');
		$('#message').show();
		$('#message').hide(hiddenTime);
		return;
	}
	signupRequest.password = $('#password').val();
	if (signupRequest.password == "") {
		$('#message').html('Password is empty');
		$('#message').show();
		$('#message').hide(hiddenTime);
		return;
	}
	
	var reqObj = JSON.stringify(signupRequest);
	$.ajax({
		url : "/api/auth/signup",
		method : "POST",
		data : reqObj,
		contentType : 'application/json; charset=utf-8',
		success : function(res) {
			$('#message').html(res.message);
			$('#message').show();
			$('#message').hide(hiddenTime);
			resetEmployee();
		},
		error : function(error) {
			$('#message').html("User Registration failed");
			$('#message').show();
			$('#message').hide(hiddenTime);
		}
	})
}


// Reser Signup from data
function resetEmployee() {
	$('#username').val('');
	$('#email').val('');
	$('#password').val('');
}

