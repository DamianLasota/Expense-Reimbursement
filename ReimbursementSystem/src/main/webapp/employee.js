window.onload = function(){
	checkSession();
	welcome();
	getTickets();
	document.getElementById("submit").addEventListener('click', submitTicket);
	document.getElementById("logout").addEventListener('click', logout);
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

function welcome(){
	let xhttp = new XMLHttpRequest();
	xhttp.open("GET", "http://localhost:8080/ReimbursementSystem/master/check");
	xhttp.send();
	
	xhttp.onreadystatechange = function(){
		if(xhttp.readyState == 4){
			if(xhttp.status == 200){
				user = JSON.parse(xhttp.responseText);
				document.getElementById("welcome").innerHTML += " " + user.fname + " " + user.lname + " (" + user.username + ")";
			}
		}
	}
}

function getTickets(){
	let xhttp = new XMLHttpRequest();
	xhttp.open("GET", "http://localhost:8080/ReimbursementSystem/master/employee");
	xhttp.send();
	
	xhttp.onreadystatechange = function(){
		if(xhttp.readyState == 4){
			if(xhttp.status == 200){
				let ticketList = JSON.parse(xhttp.responseText);
				document.getElementById("ticketTable").innerHTML = "";
				for(let t in ticketList){
					document.getElementById("ticketTable").innerHTML += 
						"<tr>" + 
							"<td>" + ticketList[t]["ticketId"] + "</td>" + 
							"<td>$" + ticketList[t]["amount"] + "</td>" + 
							"<td>" + ticketList[t]["type"] + "</td>" + 
							"<td>" + ticketList[t]["description"] + "</td>" + 
							"<td>" + new Date(ticketList[t]["timeSubmitted"]) + "</td>" + 
							"<td>" + ticketList[t]["status"] + "</td>" + 
						"</tr>";
				}
			}
		}
	} 
}

function submitTicket(){
	let amount = document.getElementById("amount").value;
	if(!amount){
		document.getElementById("response1").innerText =  "Please enter amount spent";
	} else if(/^\d+\.?\d{0,2}$/.test(amount)){
		document.getElementById("response1").innerText =  "";
	} else {
		document.getElementById("response1").innerText =  "Please enter a valid amount";
	}
	
	let type = document.getElementById("type").value;
	if(type == "Choose a type"){
		document.getElementById("response2").innerText =  "Please select a type";
	} else {
		document.getElementById("response2").innerText =  "";
	}
	
	let description = document.getElementById("description").value;
	if(description){
		document.getElementById("response3").innerText =  "";
	} else {
		document.getElementById("response3").innerText =  "Please enter a description";
	}
	
	if(/^\d+\.?\d{0,2}$/.test(amount) && type != "Choose a type" && description){
		let ticket = '{"ticketId": 0, "amount": ' + amount + ', "type": "' + type + '", "description": "' + description + '", "timeSubmitted": "' + Date.now()  + '", "status": "PENDING", "userId": 0}';
		
		let xhttp = new XMLHttpRequest();
		xhttp.open("POST", "http://localhost:8080/ReimbursementSystem/master/employee");
		xhttp.send(ticket);
		
		xhttp.onreadystatechange = function(){
			if(xhttp.readyState == 4){
				if(xhttp.status == 200){
					document.getElementById("response4").innerText =  "";
					document.getElementById("amount").value =  "";
					document.getElementById("type").value =  "Choose a type";
					document.getElementById("description").value =  "";			
					getTickets();
				} else if(xhttp.status == 500) {
					document.getElementById("response4").innerText =  "Failed to create ticket";
				}
			}
		}
	}
}

function logout(){
	let xhttp = new XMLHttpRequest();
	xhttp.open("POST", "http://localhost:8080/ReimbursementSystem/master/logout");
	xhttp.send();
	
	xhttp.onreadystatechange = function(){
		if(xhttp.readyState == 4){
			if(xhttp.status == 200){
				window.location.replace(xhttp.responseURL);
			}
		}
	}	
}