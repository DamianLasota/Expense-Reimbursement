window.onload = function(){
	document.getElementById("signup").addEventListener('click', trySignUp);
}

function trySignUp(){
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
	
	let confirm = document.getElementById("confirm").value;
	if(!confirm){
		document.getElementById("response3").innerText =  "Please confirm password";
	} else if(password === confirm){
		document.getElementById("response3").innerText =  "";
	} else {
		document.getElementById("response3").innerText =  "Passwords do not match";
	}
	
	let fname = document.getElementById("fname").value;
	if(!fname){
		document.getElementById("response4").innerText =  "Please enter your first name";
	} else if(/^[A-Za-z]+$/.test(fname)){
		document.getElementById("response4").innerText =  "";
	} else {
		document.getElementById("response4").innerText =  "Please enter a valid first name";
	}
	
	let lname = document.getElementById("lname").value;
	if(!lname){
		document.getElementById("response5").innerText =  "Please enter your last name";
	} else if(/^[A-Za-z]+$/.test(lname)){
		document.getElementById("response5").innerText =  "";
	} else {
		document.getElementById("response5").innerText =  "Please enter a valid last name";
	}
	
	let email = document.getElementById("email").value;
	if(!email){
		document.getElementById("response6").innerText =  "Please enter an email";
	} else if(/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/.test(email)){
		document.getElementById("response6").innerText =  "";
	} else {
		document.getElementById("response6").innerText =  "Please enter a valid email";
	}
	
	if(username && password && password === confirm && /^[A-Za-z]+$/.test(fname) && /^[A-Za-z]+$/.test(lname) && email) {
		fname = fname.toLowerCase();
		fname = fname.charAt(0).toUpperCase() + fname.slice(1);
		lname = lname.toLowerCase();
		lname = lname.charAt(0).toUpperCase() + lname.slice(1);
		
		let user = '{"userId": 0, "username": "' + username + '", "password": "' + password + '", "fname": "' + fname + '", "lname": "' + lname + '", "email": "' + email + '", "type": "EMPLOYEE"}';
		
		let xhttp = new XMLHttpRequest();
		xhttp.open("POST", "http://localhost:8080/ReimbursementSystem/master/signup");
		xhttp.send(user);
		
		xhttp.onreadystatechange = function(){
			if(xhttp.readyState == 4){
				if(xhttp.status == 406){
					document.getElementById("response7").innerText =  xhttp.responseText;
				} else if(xhttp.status == 500) {
					document.getElementById("response7").innerText =  "Failed to create account";
				} else if(xhttp.status == 200) {
					window.location.replace(xhttp.responseURL);
				}
			}
		}
	}
}