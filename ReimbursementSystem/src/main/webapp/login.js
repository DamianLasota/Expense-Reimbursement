window.onload = function(){
	checkSession();
	document.getElementById("login").addEventListener('click', checkLogin);
}

function checkSession(){
	let xhttp = new XMLHttpRequest();
	xhttp.open("POST", "http://localhost:8080/ReimbursementSystem/master/check");
	xhttp.send();
	
	xhttp.onreadystatechange = function(){
		if(xhttp.readyState == 4){
			if(xhttp.status == 200){
				if(window.location.href != xhttp.responseURL){
					window.location.replace(xhttp.responseURL);
				}
			}
		}
	}
}

function checkLogin(){
	let username = document.getElementById("username").value;
	if(username){
		document.getElementById("response1").innerText =  "";
	} else {
		document.getElementById("response1").innerText =  "Please enter a username";
	}
	
	let password = document.getElementById("password").value;
	if(password){
		document.getElementById("response2").innerText =  "";
	} else {
		document.getElementById("response2").innerText =  "Please enter a password";
	}
	
	if(username && password){
		let user = '{"userId": 0, "username": "' + username + '", "password": "' + password + '", "fname": "", "lname": "", "email": "", "type": ""}';
	
		let xhttp = new XMLHttpRequest();
		xhttp.open("POST", "http://localhost:8080/ReimbursementSystem/master/login");
		xhttp.send(user);
		
		xhttp.onreadystatechange = function(){
			if(xhttp.readyState == 4){
				if(xhttp.status == 403){
					document.getElementById("response3").innerText =  "Invalid username or password";
				} else if(xhttp.status == 200) {
					window.location.replace(xhttp.responseURL);
				}
			}
		}
	}
}