var ticketMap;

window.onload = function(){
	checkSession();
	welcome();
	getTickets();
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
	xhttp.open("GET", "http://localhost:8080/ReimbursementSystem/master/finance");
	xhttp.send();
	
	xhttp.onreadystatechange = function(){
		if(xhttp.readyState == 4){
			if(xhttp.status == 200){
				ticketMap = JSON.parse(xhttp.responseText);
				filter();
			}
		}
	} 
}

function filter() {
	if(document.getElementById("filter").value == "PENDING"){
		document.getElementById("ticketTable").innerHTML = "";
		for(let i in ticketMap["Ticket"]){
			if(ticketMap["Ticket"][i]["status"] == "PENDING"){
				document.getElementById("ticketTable").innerHTML += 
					"<tr>" + 
						"<td>" + ticketMap["User"][i]["userId"] + "</td>" +
						"<td>" + ticketMap["User"][i]["username"] + "</td>" +
						"<td>" + ticketMap["User"][i]["fname"] + "</td>" +
						"<td>" + ticketMap["User"][i]["lname"] + "</td>" +
						"<td>" + ticketMap["User"][i]["email"] + "</td>" + 
						"<td>" + ticketMap["Ticket"][i]["ticketId"] + "</td>" + 
						"<td>$" + ticketMap["Ticket"][i]["amount"] + "</td>" + 
						"<td>" + ticketMap["Ticket"][i]["type"] + "</td>" + 
						"<td>" + ticketMap["Ticket"][i]["description"] + "</td>" + 
						"<td>" + new Date(ticketMap["Ticket"][i]["timeSubmitted"]) + "</td>" + 
						"<td>" + ticketMap["Ticket"][i]["status"] + "</td>" + 
						'<td><button class="btn btn-primary" type="submit" onclick="approve(' + i + ')">Approve</button></td>' + 
						'<td><button class="btn btn-primary" type="submit" onclick="deny(' + i + ')">Deny</button></td>' + 
					"</tr>";
			}
		}
	} else {
		document.getElementById("ticketTable").innerHTML = "";
		for(let i in ticketMap["Ticket"]){
			if(ticketMap["Ticket"][i]["status"] == "PENDING"){
				document.getElementById("ticketTable").innerHTML += 
					"<tr>" + 
						"<td>" + ticketMap["User"][i]["userId"] + "</td>" +
						"<td>" + ticketMap["User"][i]["username"] + "</td>" +
						"<td>" + ticketMap["User"][i]["fname"] + "</td>" +
						"<td>" + ticketMap["User"][i]["lname"] + "</td>" +
						"<td>" + ticketMap["User"][i]["email"] + "</td>" + 
						"<td>" + ticketMap["Ticket"][i]["ticketId"] + "</td>" + 
						"<td>$" + ticketMap["Ticket"][i]["amount"] + "</td>" + 
						"<td>" + ticketMap["Ticket"][i]["type"] + "</td>" + 
						"<td>" + ticketMap["Ticket"][i]["description"] + "</td>" + 
						"<td>" + new Date(ticketMap["Ticket"][i]["timeSubmitted"]) + "</td>" + 
						"<td>" + ticketMap["Ticket"][i]["status"] + "</td>" + 
						'<td><button class="btn btn-primary" type="submit" onclick="approve(' + i + ')">Approve</button></td>' + 
						'<td><button class="btn btn-primary" type="submit" onclick="deny(' + i + ')">Deny</button></td>' + 
					"</tr>";
			} else {
				document.getElementById("ticketTable").innerHTML += 
					"<tr>" + 
						"<td>" + ticketMap["User"][i]["userId"] + "</td>" +
						"<td>" + ticketMap["User"][i]["username"] + "</td>" +
						"<td>" + ticketMap["User"][i]["fname"] + "</td>" +
						"<td>" + ticketMap["User"][i]["lname"] + "</td>" +
						"<td>" + ticketMap["User"][i]["email"] + "</td>" + 
						"<td>" + ticketMap["Ticket"][i]["ticketId"] + "</td>" + 
						"<td>$" + ticketMap["Ticket"][i]["amount"] + "</td>" + 
						"<td>" + ticketMap["Ticket"][i]["type"] + "</td>" + 
						"<td>" + ticketMap["Ticket"][i]["description"] + "</td>" + 
						"<td>" + new Date(ticketMap["Ticket"][i]["timeSubmitted"]) + "</td>" + 
						"<td>" + ticketMap["Ticket"][i]["status"] + "</td>" + 
						'<td><button class="btn btn-primary" type="submit" disabled>Approve</button></td>' + 
						'<td><button class="btn btn-primary" type="submit" disabled>Deny</button></td>' + 
					"</tr>";
			}
		}
	}
}

function approve(i){
	let ticket = ticketMap["Ticket"][i];
	ticket.status = "APPROVED";
	ticket = JSON.stringify(ticket);
	
	let xhttp = new XMLHttpRequest();
	xhttp.open("PUT", "http://localhost:8080/ReimbursementSystem/master/finance");
	xhttp.send(ticket);
	
	filter();
}

function deny(i){
	console.log("inside deny");
	let ticket = ticketMap["Ticket"][i];
	ticket.status = "DENIED";
	ticket = JSON.stringify(ticket);
	
	let xhttp = new XMLHttpRequest();
	xhttp.open("PUT", "http://localhost:8080/ReimbursementSystem/master/finance");
	xhttp.send(ticket);
	
	filter();
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